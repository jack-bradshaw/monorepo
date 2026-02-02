load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("@rules_shell//shell:sh_library.bzl", "sh_library")
load("//first_party/bash_runfiles:bats_test.bzl", "bats_test_with_runfiles")
load(
    "//first_party/bash_runfiles:shell.bzl",
    "sh_binary_with_runfiles",
    "sh_library_with_runfiles",
)

exports_files(["prettier.config.cjs"])

sh_binary_with_runfiles(
    name = "prettier_binary",
    srcs = ["prettier_bin.sh"],
    data = [
        "prettier.config.cjs",
        "//:.prettierignore",
        "//:node_modules/@prettier/plugin-xml",
        "//:node_modules/prettier",
        "//:node_modules/prettier-plugin-gherkin",
        "//:node_modules/prettier-plugin-sql",
    ],
    visibility = ["//visibility:public"],
)

bats_test_with_runfiles(
    name = "prettier_integration_test",
    srcs = ["prettier_integration_test.bats"],
    data = [
        "prettier.config.cjs",
        ":prettier_binary",
        "//:.prettierignore",
        "//:node_modules/@prettier/plugin-xml",
        "//:node_modules/prettier",
        "//:node_modules/prettier-plugin-gherkin",
        "//:node_modules/prettier-plugin-sql",
        "//first_party/dr_bashir/strings",
    ],
)
