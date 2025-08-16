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
"""Rule adapter for proto_library."""

load("//mobile_install:providers.bzl", "MIAndroidDexInfo", "providers")
load("//mobile_install:transform.bzl", "dex")
load("//rules:visibility.bzl", "PROJECT_VISIBILITY")
load("@rules_java//java/common:java_info.bzl", "JavaInfo")
load(":base.bzl", "make_adapter")
load(":desugar.bzl", "get_desugar_classpath")

visibility(PROJECT_VISIBILITY)

def _aspect_attrs():
    """Attrs of the rule requiring traversal by the aspect."""
    return ["deps"]

def _adapt(target, ctx):
    """Adapts the rule and target data.

    Args:
      target: The target.
      ctx: The context.

    Returns:
      A list of providers.
    """
    if not JavaInfo in target:
        return []
    return [
        providers.make_mi_android_dex_info(
            dex_shards = dex(
                ctx,
                [j.class_jar for j in target[JavaInfo].outputs.jars],
                get_desugar_classpath(target[JavaInfo]),
            ),
            deps = providers.collect(MIAndroidDexInfo, ctx.rule.attr.deps),
        ),
    ]

proto_library = make_adapter(_aspect_attrs, _adapt)
