load("//bazel/swig:swig.bzl", "swig_toolchain")

toolchain_type(name = "toolchain_type")

swig_toolchain(
    name = "macos_swig_toolchain",
    executable = "//third_party/swig:macos",
)

swig_toolchain(
    name = "linux_swig_toolchain",
    executable = "//third_party/swig:linux",
)

swig_toolchain(
    name = "windows_swig_toolchain",
    executable = "//third_party/swig:windows",
)

toolchain(
    name = "linux_toolchain",
    exec_compatible_with = [
        "@platforms//os:linux",
    ],
    toolchain = ":linux_swig_toolchain",
    toolchain_type = ":toolchain_type",
)

toolchain(
    name = "windows_toolchain",
    exec_compatible_with = [
        "@platforms//os:windows",
    ],
    toolchain = ":windows_swig_toolchain",
    toolchain_type = ":toolchain_type",
)

toolchain(
    name = "macos_toolchain",
    exec_compatible_with = [
        "@platforms//os:macos",
    ],
    toolchain = ":macos_swig_toolchain",
    toolchain_type = ":toolchain_type",
)