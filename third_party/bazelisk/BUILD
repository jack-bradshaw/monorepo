package(default_visibility = ["//visibility:public"])

filegroup(
    name = "unix_binaries",
    srcs = glob([
        "bin/bazelisk-darwin-x86_64",
        "bin/bazelisk-linux-x86_64",
        "bin/bazelisk-darwin-arm64",
        "bin/bazelisk-linux-arm64",
    ]),
)

filegroup(
    name = "windows_binary",
    srcs = ["bin/bazelisk-windows-x86_64.exe"],
)

sh_binary(
    name = "unix_setup",
    srcs = ["setup.sh"],
    data = ["//third_party/bazelisk:unix_binaries"],
)

# Uses a filegroup because rules_bat has not been integrated yet.
filegroup(
    name = "windows_setup",
    srcs = ["setup.bat"],
    data = ["//third_party/bazelisk:windows_binary"],
)