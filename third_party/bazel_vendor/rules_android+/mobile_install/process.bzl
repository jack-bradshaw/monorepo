# Copyright 2018 The Bazel Authors. All rights reserved.
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
"""Processes the target or collected data."""

load("//rules:utils.bzl", "get_android_toolchain", "utils")
load("//rules:visibility.bzl", "PROJECT_VISIBILITY")
load(":apks.bzl", "make_split_apks")
load(":native_libs.bzl", "make_native_zips", "make_swigdeps_file")
load(":providers.bzl", "MIAppInfo")
load(":r_java.bzl", "make_r")
load(":transform.bzl", "merge_dex_shards")

visibility(PROJECT_VISIBILITY)

def process(
        ctx,
        merged_manifest,
        native_libs,
        package,
        resource_apk,
        resource_src_jar,
        aar_native_libs,
        android_dex_info,
        android_resources_info,
        java_resources_info,
        debug_key = None,
        debug_signing_keys = [],
        debug_signing_lineage_file = None,
        key_rotation_min_sdk = None,
        apk = None,
        sibling = None):
    """Processes the data in the mi and return data to pass up the graph.

    Args:
      ctx: The context.
      merged_manifest: A file representing the final merged manifest, a textual manifest.
      native_libs: A list of native libs.
      package: A string representing the package name of the dep.
      resource_apk: The resource apk.
      resource_src_jar: The resource source jar.
      aar_native_libs: The information about collected native libs provided by an aar.
      android_dex_info: The collected Android Dex shards.
      android_resources_info: The collected Android compiled resourses.
      java_resources_info: Java resources info.
      debug_key: A file containing the debug key (deprecated: use debug_signing_keys).
      debug_signing_keys: Debug keystores to be used to sign the apk.
      debug_signing_lineage_file: File containing the signing lineage.
      key_rotation_min_sdk: String of the minimum API level to rotate signing keys for.
      apk: The generated apk for the app.
      sibling: The path to the launcher file.

    Returns:
      A struct to pass up the build graph. The struct will contain outputs_groups
      when rule kind of the current context is a "root node".
    """
    native_zips = make_native_zips(
        ctx,
        native_libs,
        aar_native_libs,
        sibling = sibling,
    )

    swigdeps_file = None
    if native_zips:
        swigdeps_file = make_swigdeps_file(ctx, sibling)

    # Merges the dex shards, to create a final set of dexes for the app.
    merged_dex_shards = merge_dex_shards(
        ctx,
        android_dex_info.transitive_dex_shards,
        sibling,
    )

    if hasattr(get_android_toolchain(ctx), "desugar_globals_dex_archive"):
        merged_dex_shards.append(utils.only(get_android_toolchain(ctx).desugar_globals_dex_archive.files.to_list()))

    # Creates the custom R.
    r_dex = make_r(
        ctx,
        resource_src_jar,
        package,
        android_resources_info.transitive_packages,
        sibling,
    )

    if not debug_signing_keys:
        debug_signing_keys = [debug_key]

    manifest_package_name, splits = make_split_apks(
        ctx,
        merged_manifest,
        r_dex,
        merged_dex_shards,
        resource_apk,
        java_resources_info.transitive_java_resources.to_list(),
        native_zips,
        swigdeps_file,
        debug_signing_keys,
        debug_signing_lineage_file,
        key_rotation_min_sdk,
        sibling,
    )

    return MIAppInfo(
        manifest_package_name = manifest_package_name,
        merged_manifest = merged_manifest,
        r_dex = r_dex,
        merged_dex_shards = merged_dex_shards,
        native_zip = native_zips,
        splits = splits,
        apk = apk,
    )
