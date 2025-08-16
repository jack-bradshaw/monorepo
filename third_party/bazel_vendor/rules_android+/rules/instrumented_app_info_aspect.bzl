# Copyright 2021 The Bazel Authors. All rights reserved.
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
"""This aspect is used to collect providers from an instrumented android_binary."""

load("//providers:providers.bzl", "AndroidIdeInfo", "InstrumentedAppInfo")
load("//rules:visibility.bzl", "PROJECT_VISIBILITY")

visibility(PROJECT_VISIBILITY)

def _impl(unused_target, ctx):
    if hasattr(ctx.rule.attr, "instruments") and ctx.rule.attr.instruments and AndroidIdeInfo in ctx.rule.attr.instruments:
        return [InstrumentedAppInfo(android_ide_info = ctx.rule.attr.instruments[AndroidIdeInfo])]
    return []

instrumented_app_info_aspect = aspect(
    implementation = _impl,
)
