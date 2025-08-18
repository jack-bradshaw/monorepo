# Copyright 2020 The Bazel Authors. All rights reserved.
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
"""Bazel Bundletool Commands."""

load(
    "//rules:utils.bzl",
    "ANDROID_TOOLCHAIN_TYPE",
)
load("//rules:visibility.bzl", "PROJECT_VISIBILITY")
load("@rules_java//java/common:java_common.bzl", "java_common")
load("@bazel_skylib//lib:paths.bzl", "paths")
load(":common.bzl", _common = "common")
load(":java.bzl", _java = "java")

visibility(PROJECT_VISIBILITY)

_density_mapping = {
    "ldpi": 120,
    "mdpi": 160,
    "hdpi": 240,
    "xhdpi": 320,
    "xxhdpi": 480,
    "xxxhdpi": 640,
    "tvdpi": 213,
}

def _build(
        ctx,
        out = None,
        modules = [],
        config = None,
        metadata = dict(),
        bundletool = None,
        host_javabase = None):
    args = ctx.actions.args()
    args.add("build-bundle")
    args.add("--output", out)
    if modules:
        args.add_joined("--modules", modules, join_with = ",")
    if config:
        args.add("--config", config)
    for path, f in metadata.items():
        args.add("--metadata-file", "%s:%s" % (path, f.path))

    _java.run(
        ctx = ctx,
        host_javabase = host_javabase,
        executable = bundletool,
        arguments = [args],
        inputs = (
            modules +
            ([config] if config else []) +
            metadata.values()
        ),
        outputs = [out],
        mnemonic = "BuildBundle",
        progress_message = "Building bundle %s" % out.short_path,
    )

def _build_device_json(
        ctx,
        out,
        abis,
        locales,
        density,
        sdk_version):
    json_content = json.encode(struct(
        supportedAbis = abis,
        supportedLocales = locales,
        screenDensity = _density_mapping[density],
        sdkVersion = int(sdk_version),
    ))
    ctx.actions.write(out, json_content)

def _build_sdk_apks(
        ctx,
        out = None,
        aapt2 = None,
        sdk_archive = None,
        sdk_bundle = None,
        debug_key = None,
        bundletool = None,
        host_javabase = None):
    if bool(sdk_archive) == bool(sdk_bundle):
        fail("Exactly one of sdk_archive or sdk_bundle need to be set in %s." % ctx.label.name)
    apks_out = ctx.actions.declare_directory(
        "%s_apks_out" % paths.basename(out.path).replace(".", "_"),
        sibling = out,
    )
    inputs = [debug_key]
    args = ctx.actions.args()
    args.add("build-sdk-apks")
    args.add("--aapt2", aapt2.executable.path)
    if sdk_archive:
        args.add("--sdk-archive", sdk_archive)
        inputs.append(sdk_archive)
    if sdk_bundle:
        args.add("--sdk-bundle", sdk_bundle)
        inputs.append(sdk_bundle)
    args.add("--ks", debug_key)
    args.add("--ks-pass=pass:android")
    args.add("--ks-key-alias=androiddebugkey")
    args.add("--key-pass=pass:android")
    args.add("--output-format=DIRECTORY")
    args.add("--output", apks_out.path)
    _java.run(
        ctx = ctx,
        host_javabase = host_javabase,
        executable = bundletool,
        arguments = [args],
        inputs = inputs,
        tools = [aapt2],
        outputs = [apks_out],
        mnemonic = "BuildSdkApksDir",
        progress_message = "Building SDK APKs directory %s" % apks_out.short_path,
    )

    # Now move standalone APK out of bundletool output dir.
    ctx.actions.run_shell(
        command = """
set -e
APKS_OUT_DIR=%s
DEBUG_APK_PATH=%s

mv "${APKS_OUT_DIR}/standalones/standalone.apk" "${DEBUG_APK_PATH}"
""" % (
            apks_out.path,
            out.path,
        ),
        tools = [],
        arguments = [],
        inputs = [apks_out],
        outputs = [out],
        mnemonic = "ExtractDebugSdkApk",
        progress_message = "Extract debug SDK APK to %s" % out.short_path,
    )

def _build_sdk_apks_for_app(
        ctx,
        out = None,
        aapt2 = None,
        sdk_archive = None,
        sdk_bundle = None,
        sdk_split_properties_inherited_from_app = None,
        debug_key = None,
        bundletool = None,
        host_javabase = None):
    if bool(sdk_archive) == bool(sdk_bundle):
        fail("Exactly one of sdk_archive or sdk_bundle need to be set in %s." % ctx.label.name)

    split_out_dir = ctx.actions.declare_directory(
        "%s_split_out" % paths.basename(out.path).replace(".", "_"),
        sibling = out,
    )
    inputs = [debug_key, sdk_split_properties_inherited_from_app]
    args = ctx.actions.args()
    args.add("build-sdk-apks-for-app")
    args.add("--app-properties", sdk_split_properties_inherited_from_app)
    args.add("--aapt2", aapt2.executable.path)
    if sdk_archive:
        args.add("--sdk-archive", sdk_archive)
        inputs.append(sdk_archive)
    if sdk_bundle:
        args.add("--sdk-bundle", sdk_bundle)
        inputs.append(sdk_bundle)
    args.add("--ks", debug_key)
    args.add("--ks-pass=pass:android")
    args.add("--ks-key-alias=androiddebugkey")
    args.add("--key-pass=pass:android")
    args.add("--output-format=DIRECTORY")
    args.add("--output", split_out_dir.path)
    _java.run(
        ctx = ctx,
        host_javabase = host_javabase,
        executable = bundletool,
        arguments = [args],
        inputs = inputs,
        tools = [aapt2],
        outputs = [split_out_dir],
        mnemonic = "BuildSdkSplit",
        progress_message = "Building SDK split %s" % out.short_path,
    )

    # Now move split out of bundletool output dir.
    ctx.actions.run_shell(
        command = """
set -e
SPLIT_APKS=(%s/splits/*.apk)
OUTPUT_SPLIT=%s

if [[ "${#SPLIT_APKS[@]}" -ne 1 ]]
then
    echo "Expected a single APK split but got ${#SPLIT_APKS[@]}"
    exit 1
fi

mv "${SPLIT_APKS[0]}" "${OUTPUT_SPLIT}"
""" % (
            split_out_dir.path,
            out.path,
        ),
        tools = [],
        arguments = [],
        inputs = [split_out_dir],
        outputs = [out],
        mnemonic = "MoveSplitApk",
        progress_message = "Move SDK split APK from Bundletool output: %s" % out.short_path,
    )

def _build_sdk_bundle(
        ctx,
        out = None,
        module = None,
        sdk_api_descriptors = None,
        sdk_modules_config = None,
        bundletool = None,
        host_javabase = None):
    args = ctx.actions.args()
    args.add("build-sdk-bundle")

    args.add("--sdk-modules-config", sdk_modules_config)
    args.add("--sdk-interface-descriptors", sdk_api_descriptors)
    args.add("--modules", module)
    args.add("--output", out)
    _java.run(
        ctx = ctx,
        host_javabase = host_javabase,
        executable = bundletool,
        arguments = [args],
        inputs = [
            module,
            sdk_api_descriptors,
            sdk_modules_config,
        ],
        outputs = [out],
        mnemonic = "BuildASB",
        progress_message = "Building SDK bundle %s" % out.short_path,
    )

def _build_sdk_module(
        ctx,
        out = None,
        internal_apk = None,
        bundletool_module_builder = None,
        host_javabase = None):
    args = ctx.actions.args()
    args.add("--internal_apk_path", internal_apk)
    args.add("--output_module_path", out)
    ctx.actions.run(
        inputs = [internal_apk],
        outputs = [out],
        executable = bundletool_module_builder,
        arguments = [args],
        mnemonic = "BuildSdkModule",
        progress_message = "Building ASB zip module %s" % out.short_path,
        toolchain = ANDROID_TOOLCHAIN_TYPE,
    )

def _bundle_to_apks(
        ctx,
        out = None,
        bundle = None,
        mode = None,
        system_apk_options = None,
        device_spec = None,
        keystore = None,
        oldest_signer = None,
        lineage = None,
        rotation_min_sdk = None,
        modules = None,
        aapt2 = None,
        bundletool = None,
        host_javabase = None):
    inputs = [bundle]
    args = ctx.actions.args()
    args.add("build-apks")
    args.add("--output", out)
    args.add("--bundle", bundle)
    args.add("--aapt2", aapt2.executable.path)

    if mode:
        args.add("--mode", mode)

    if system_apk_options:
        if mode != "SYSTEM":
            fail("Unexpected system_apk_options specified, requires SYSTEM mode but got %s" % mode)
        args.add_joined("--system-apk-options", system_apk_options, join_with = ",")

    if keystore:
        args.add("--ks", keystore.path)
        args.add("--ks-pass", "pass:android")
        args.add("--ks-key-alias", "AndroidDebugKey")
        inputs.append(keystore)

    if lineage:
        if not oldest_signer:
            fail("Key rotation requires oldest_signer in %s" % ctx.label)
        oldest_signer_properties = _common.create_signer_properties(ctx, oldest_signer)
        args.add("--oldest-signer", oldest_signer_properties.path)
        args.add("--lineage", lineage.short_path)
        inputs.append(oldest_signer_properties)
        inputs.append(oldest_signer)
        inputs.append(lineage)

    if rotation_min_sdk:
        args.add("--rotation-min-sdk-version", rotation_min_sdk)

    if device_spec:
        args.add("--device-spec", device_spec)
        inputs.append(device_spec)

    if modules:
        args.add_joined("--modules", modules, join_with = ",")

    _java.run(
        ctx = ctx,
        host_javabase = host_javabase,
        executable = bundletool,
        arguments = [args],
        inputs = inputs,
        outputs = [out],
        tools = [aapt2],
        mnemonic = "BundleToApks",
        progress_message = "Converting bundle to .apks: %s" % out.short_path,
    )

def _extract_config(
        ctx,
        out = None,
        aab = None,
        bundletool = None,
        host_javabase = None):
    # Need to execute as a shell script as the tool outputs to stdout
    cmd = """
set -e
contents=`%s -jar %s dump config --bundle %s`
echo "$contents" > %s
""" % (
        host_javabase[java_common.JavaRuntimeInfo].java_executable_exec_path,
        bundletool.executable.path,
        aab.path,
        out.path,
    )

    ctx.actions.run_shell(
        inputs = [aab],
        outputs = [out],
        tools = depset([bundletool.executable], transitive = [host_javabase[java_common.JavaRuntimeInfo].files]),
        mnemonic = "ExtractBundleConfig",
        progress_message = "Extract bundle config to %s" % out.short_path,
        command = cmd,
        exec_group = "android_and_java",
    )

def _extract_manifest(
        ctx,
        out = None,
        aab = None,
        module = None,
        xpath = None,
        bundletool = None,
        host_javabase = None):
    # Need to execute as a shell script as the tool outputs to stdout
    extra_flags = []
    if module:
        extra_flags.append("--module " + module)
    if xpath:
        extra_flags.append("--xpath " + xpath)
    cmd = """
set -e
contents=`%s -jar %s dump manifest --bundle %s %s`
echo "$contents" > %s
""" % (
        host_javabase[java_common.JavaRuntimeInfo].java_executable_exec_path,
        bundletool.executable.path,
        aab.path,
        " ".join(extra_flags),
        out.path,
    )

    ctx.actions.run_shell(
        inputs = [aab],
        outputs = [out],
        tools = depset([bundletool.executable], transitive = [host_javabase[java_common.JavaRuntimeInfo].files]),
        mnemonic = "ExtractBundleManifest",
        progress_message = "Extract bundle manifest to %s" % out.short_path,
        command = cmd,
        exec_group = "android_and_java",
    )

def _proto_apk_to_module(
        ctx,
        out = None,
        proto_apk = None,
        runtime_enabled_sdk_config = None,
        bundletool_module_builder = None):
    inputs = [proto_apk]
    args = ctx.actions.args()
    args.add("--internal_apk_path", proto_apk)
    if runtime_enabled_sdk_config:
        args.add("--runtime_enabled_sdk_config_path", runtime_enabled_sdk_config)
        inputs.append(runtime_enabled_sdk_config)

    args.add("--output_module_path", out)
    ctx.actions.run(
        inputs = inputs,
        outputs = [out],
        executable = bundletool_module_builder,
        arguments = [args],
        mnemonic = "BuildAppModule",
        progress_message = "Building AAB zip module %s" % out.short_path,
        toolchain = ANDROID_TOOLCHAIN_TYPE,
    )

bundletool = struct(
    build = _build,
    build_device_json = _build_device_json,
    build_sdk_apks = _build_sdk_apks,
    build_sdk_apks_for_app = _build_sdk_apks_for_app,
    build_sdk_bundle = _build_sdk_bundle,
    build_sdk_module = _build_sdk_module,
    bundle_to_apks = _bundle_to_apks,
    extract_config = _extract_config,
    extract_manifest = _extract_manifest,
    proto_apk_to_module = _proto_apk_to_module,
)
