load("@aspect_rules_js//js:defs.bzl", "js_library")
load("//first_party/bash_runfiles:bats_test.bzl", "bats_test_with_runfiles")
load(
    "//first_party/bash_runfiles:shell.bzl",
    "sh_binary_with_runfiles",
)
load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

js_library(
    name = "config",
    srcs = ["prettier.config.cjs"],
)

sh_binary_with_runfiles(
    name = "binary",
    srcs = ["prettier_bin.sh"],
    data = [
        ":config",
        "//:node_modules/@prettier/plugin-xml",
        "//:node_modules/prettier",
        "//:node_modules/prettier-plugin-gherkin",
        "//:node_modules/prettier-plugin-sql",
        "//:prettierignore",
    ],
    visibility = PUBLICITY,
)

bats_test_with_runfiles(
    name = "prettier_integration_test",
    srcs = ["prettier_integration_test.bats"],
    data = [
        ":binary",
        ":config",
        "//:node_modules/@prettier/plugin-xml",
        "//:node_modules/prettier",
        "//:node_modules/prettier-plugin-gherkin",
        "//:node_modules/prettier-plugin-sql",
        "//:prettierignore",
        "//first_party/dr_bashir/strings:trim_indent",
    ],
)
