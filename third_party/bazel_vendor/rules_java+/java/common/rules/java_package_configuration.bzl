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

"""Implementation for the java_package_configuration rule"""

load("//java/common/rules/impl:java_helper.bzl", "helper")
load("//java/private:boot_class_path_info.bzl", "BootClassPathInfo")
load("//java/private:native.bzl", "get_internal_java_common")

# copybara: default visibility

JavaPackageConfigurationInfo = provider(
    "A provider for Java per-package configuration",
    fields = [
        "data",
        "javac_opts",
        "matches",
        "package_specs",
        "system",
    ],
)

def _matches(package_specs, label):
    for spec in package_specs:
        if spec.contains(label):
            return True
    return False

def _rule_impl(ctx):
    javacopts = get_internal_java_common().expand_java_opts(ctx, "javacopts", tokenize = True)
    javacopts_depset = helper.detokenize_javacopts(javacopts)
    package_specs = [package[PackageSpecificationInfo] for package in ctx.attr.packages]
    system = ctx.attr.system[BootClassPathInfo] if ctx.attr.system else None
    return [
        DefaultInfo(),
        JavaPackageConfigurationInfo(
            data = depset(ctx.files.data),
            javac_opts = javacopts_depset,
            matches = _matches,
            package_specs = package_specs,
            system = system,
        ),
    ]

java_package_configuration = rule(
    implementation = _rule_impl,
    doc = """
<p>
Configuration to apply to a set of packages.
Configurations can be added to
<code><a href="${link java_toolchain.javacopts}">java_toolchain.javacopts</a></code>s.
</p>

<h4 id="java_package_configuration_example">Example:</h4>

<pre class="code">
<code class="lang-starlark">

java_package_configuration(
    name = "my_configuration",
    packages = [":my_packages"],
    javacopts = ["-Werror"],
)

package_group(
    name = "my_packages",
    packages = [
        "//com/my/project/...",
        "-//com/my/project/testing/...",
    ],
)

java_toolchain(
    ...,
    package_configuration = [
        ":my_configuration",
    ]
)

</code>
</pre>
    """,
    attrs = {
        "packages": attr.label_list(
            cfg = "exec",
            providers = [PackageSpecificationInfo],
            doc = """
The set of <code><a href="${link package_group}">package_group</a></code>s
the configuration should be applied to.
            """,
        ),
        "javacopts": attr.string_list(
            doc = """
Java compiler flags.
            """,
        ),
        "data": attr.label_list(
            cfg = "exec",
            allow_files = True,
            doc = """
The list of files needed by this configuration at runtime.
            """,
        ),
        "system": attr.label(
            providers = [BootClassPathInfo],
            doc = """
Corresponds to javac's --system flag.
""",
        ),
        # buildifier: disable=attr-licenses
        "output_licenses": attr.string_list(),
    },
)
