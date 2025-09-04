# Copyright 2023 The Bazel Authors. All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
"""Aspect that transitively build .dex archives and desugar jars."""

load("//providers:providers.bzl", "AndroidIdeInfo", "StarlarkAndroidDexInfo")
load("//rules:visibility.bzl", "PROJECT_VISIBILITY")
load("@rules_java//java/common:java_info.bzl", "JavaInfo")
load(":acls.bzl", "acls")
load(":attrs.bzl", _attrs = "attrs")
load(":desugar.bzl", _desugar = "desugar")
load(":dex.bzl", _dex = "dex")
load(":dex_toolchains.bzl", "dex_toolchains")
load(":min_sdk_version.bzl", _min_sdk_version = "min_sdk_version")
load(":utils.bzl", "ANDROID_SDK_TOOLCHAIN_TYPE", _get_android_sdk = "get_android_sdk", _utils = "utils")

visibility(PROJECT_VISIBILITY)

_tristate = _attrs.tristate

# Keep sorted
_ATTR_ASPECTS = [
    "_aidl_lib",  # for the aidl runtime on android_library
    "_aspect_proto_toolchain_for_javalite",  # To get from proto_library through proto_lang_toolchain rule to proto runtime library.
    "_build_stamp_deps",  # for build stamp runtime class deps
    "_build_stamp_mergee_manifest_lib",  # for empty build stamp Service class implementation
    "_desugared_lib_config",  # For java8 desugaring config file
    "_toolchain",  # For _java_lite_grpc_library
    "deps",
    "exports",
    "runtime",
    "runtime_deps",
]

# Also used by the android_binary rule
def get_aspect_deps(ctx):
    """Get all the deps of the dex_desugar_aspect that requires traversal.

    Args:
        ctx: The context.

    Returns:
        deps_list: List of all deps of the dex_desugar_aspect that requires traversal.
    """
    deps_list = []
    for attr in _ATTR_ASPECTS:
        # android_binary's deps attr has a split transition, so when
        # this is called from android_binary, deps should be accessed
        # via ctx.split_attr instead of ctx.attr to ensure that the same
        # branch of the split is used throughout the build. An aspect's
        # ctx doesn't have split_attr, so access that via ctx.attr when
        # this is called from the aspect impl.
        if attr == "deps" and hasattr(ctx, "split_attr"):
            deps = _utils.dedupe_split_attr(ctx.split_attr.deps)
        elif attr == "_toolchain" and getattr(ctx, "kind", "") == "kt_jvm_toolchain":
            pass  # TODO(b/370300302): Prevent double-deps on Kotlin toolchain. Delete when fixed.
        else:
            deps = getattr(ctx.attr, attr, [])

        if str(type(deps)) == "list":
            deps_list += deps
        elif str(type(deps)) == "Target":
            deps_list.append(deps)

    deps_list.extend([
        ctx.toolchains[toolchain_type]
        for toolchain_type in dex_toolchains
        if (toolchain_type in ctx.toolchains)
    ])

    return deps_list

def _get_desugar_classpath(java_info):
    if acls.in_desugaring_runtime_jar_classpath_rollout():
        return java_info._transitive_full_compile_time_jars
    return java_info.transitive_compile_time_jars

def _aspect_impl(target, ctx):
    """Adapts the rule and target data.

    Args:
      target: The target.
      ctx: The context.

    Returns:
      A list of providers.
    """

    incremental_dexing = getattr(ctx.rule.attr, "incremental_dexing", _tristate.auto)

    min_sdk_version = _min_sdk_version.get(ctx)

    if incremental_dexing == _tristate.no or \
       (not ctx.fragments.android.use_incremental_dexing and
        incremental_dexing == _tristate.auto):
        return []

    extra_toolchain_jars = _get_platform_based_toolchain_jars(ctx)

    if hasattr(ctx.rule.attr, "neverlink") and ctx.rule.attr.neverlink:
        return []

    dex_archives_dict = {}
    runtime_jars = _get_produced_runtime_jars(target, ctx, extra_toolchain_jars)
    bootclasspath = _get_boot_classpath(target, ctx)
    desugar_classpath = _get_desugar_classpath(target[JavaInfo]) if JavaInfo in target else depset([])
    if runtime_jars:
        basename_clash = _check_basename_clash(runtime_jars)
        aspect_dexopts = _get_aspect_dexopts(ctx)
        for jar in runtime_jars:
            if ctx.fragments.android.desugar_java8:
                unique_desugar_filename = (jar.path if basename_clash else jar.basename) + "_desugared.jar"
                desugared_jar = _dex.get_dx_artifact(ctx, unique_desugar_filename, min_sdk_version)
                _desugar.desugar(
                    ctx,
                    input = jar,
                    output = desugared_jar,
                    bootclasspath = bootclasspath,
                    classpath = desugar_classpath,
                    min_sdk_version = min_sdk_version,
                    desugar_exec = ctx.executable._desugar_java8,
                    desugared_lib_config = ctx.file._desugared_lib_config,
                )
            else:
                desugared_jar = None

            for incremental_dexopts_list in aspect_dexopts:
                incremental_dexopts = "".join(incremental_dexopts_list)

                unique_dx_filename = (jar.short_path if basename_clash else jar.basename) + \
                                     incremental_dexopts + ".dex.zip"
                dex = _dex.get_dx_artifact(ctx, unique_dx_filename, min_sdk_version)
                _dex.dex(
                    ctx,
                    input = desugared_jar if desugared_jar else jar,
                    output = dex,
                    incremental_dexopts = incremental_dexopts_list,
                    min_sdk_version = min_sdk_version,
                    dex_exec = ctx.executable._dexbuilder,
                )

                dex_archive = struct(
                    jar = jar,
                    desugared_jar = desugared_jar,
                    dex = dex,
                )

                if incremental_dexopts not in dex_archives_dict:
                    dex_archives_dict[incremental_dexopts] = []
                dex_archives_dict[incremental_dexopts].append(dex_archive)

    infos = _utils.collect_providers(StarlarkAndroidDexInfo, get_aspect_deps(ctx.rule))
    merged_info = _dex.merge_infos(infos)

    for dexopts in dex_archives_dict:
        if dexopts in merged_info.dex_archives_dict:
            merged_info.dex_archives_dict[dexopts] = depset(dex_archives_dict[dexopts], transitive = [merged_info.dex_archives_dict[dexopts]])
        else:
            merged_info.dex_archives_dict[dexopts] = depset(dex_archives_dict[dexopts])

    return [
        StarlarkAndroidDexInfo(
            dex_archives_dict = merged_info.dex_archives_dict,
        ),
    ]

def _get_produced_runtime_jars(target, ctx, extra_toolchain_jars):
    if ctx.rule.kind == "proto_library":
        if getattr(ctx.rule.attr, "srcs", []):
            if JavaInfo in target:
                return [java_output.class_jar for java_output in target[JavaInfo].java_outputs]
        return []
    else:
        jars = []
        if JavaInfo in target:
            jars.extend(target[JavaInfo].runtime_output_jars)

        # TODO(b/124540821): Disable R.jar desugaring (with a flag).
        if AndroidIdeInfo in target and target[AndroidIdeInfo].resource_jar:
            jars.append(target[AndroidIdeInfo].resource_jar.class_jar)

        jars.extend(extra_toolchain_jars)
        return jars

def _get_platform_based_toolchain_jars(ctx):
    if hasattr(ctx.rule.attr, "_aidl_lib"):
        return ctx.rule.attr._aidl_lib[JavaInfo].runtime_output_jars
    return []

def _get_aspect_dexopts(ctx):
    return _power_set(_dex.normalize_dexopts(ctx.fragments.android.get_dexopts_supported_in_incremental_dexing))

def _get_boot_classpath(target, ctx):
    if JavaInfo in target:
        compilation_info = target[JavaInfo].compilation_info
        if compilation_info and compilation_info.boot_classpath:
            return compilation_info.boot_classpath

    android_jar = _get_android_sdk(ctx).android_jar
    if android_jar:
        return [android_jar]

    # This shouldn't ever be reached, but if it is, we should be clear about the error.
    fail("No compilation info or android jar!")

def _check_basename_clash(artifacts):
    seen = {}
    for artifact in artifacts:
        basename = artifact.basename
        if basename not in seen:
            seen[basename] = True
        else:
            return True
    return False

def _power_set(items):
    """Calculates the power set of the given items.
    """

    def _exp(base, n):
        """ Calculates base ** n."""
        res = 1
        for _ in range(n):
            res *= base
        return res

    power_set = []
    size = len(items)

    for i in range(_exp(2, size)):
        element = [items[j] for j in range(size) if (i // _exp(2, j) % 2) != 0]
        power_set.append(element)

    return power_set

def _opt_kwargs():
    """Optional args to pass to the aspect definition if Bazel supports them."""
    result = {}
    if dex_toolchains:
        result["toolchains_aspects"] = dex_toolchains
    return result

dex_desugar_aspect = aspect(
    implementation = _aspect_impl,
    attr_aspects = _ATTR_ASPECTS,
    attrs = _attrs.add(
        {
            "_desugar_java8": attr.label(
                default = Label("//tools/android:desugar_java8"),
                allow_files = True,
                cfg = "exec",
                executable = True,
            ),
            "_desugared_lib_config": attr.label(
                allow_single_file = True,
                default = Label("//tools/android:full_desugar_jdk_libs_config_json"),
            ),
            "_dexbuilder": attr.label(
                default = Label("//tools/android:dexbuilder"),
                allow_files = True,
                cfg = "exec",
                executable = True,
            ),
        },
        _attrs.ANDROID_SDK,
        _min_sdk_version.attrs,
    ),
    fragments = ["android"],
    toolchains = [
        ANDROID_SDK_TOOLCHAIN_TYPE,
    ],
    required_aspect_providers = [[JavaInfo]],
    **_opt_kwargs()
)
