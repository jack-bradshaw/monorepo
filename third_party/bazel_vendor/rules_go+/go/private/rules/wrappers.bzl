# Copyright 2014 The Bazel Authors. All rights reserved.
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

load(
    "//go/private:mode.bzl",
    "LINKMODES_EXECUTABLE",
    "LINKMODE_NORMAL",
)
load(
    "//go/private/rules:binary.bzl",
    "go_binary",
    "go_non_executable_binary",
)

_SELECT_TYPE = type(select({"//conditions:default": ""}))

def go_binary_macro(name, **kwargs):
    """See docs/go/core/rules.md#go_binary for full documentation."""
    if kwargs.get("goos") != None or kwargs.get("goarch") != None:
        for key, value in kwargs.items():
            if type(value) == _SELECT_TYPE:
                # In the long term, we should replace goos/goarch with Bazel-native platform
                # support, but while we have the mechanisms, we try to avoid people trying to use
                # _both_ goos/goarch _and_ native platform support.
                #
                # It's unclear to users whether the select should happen before or after the
                # goos/goarch is reconfigured, and we can't interpose code to force either
                # behaviour, so we forbid this.
                fail("Cannot use select for go_binary with goos/goarch set, but {} was a select".format(key))

    if kwargs.get("linkmode", LINKMODE_NORMAL) in LINKMODES_EXECUTABLE:
        go_binary(name = name, **kwargs)
    else:
        go_non_executable_binary(name = name, **kwargs)
