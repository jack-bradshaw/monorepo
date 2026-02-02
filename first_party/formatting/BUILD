load("@aspect_bazel_lib//lib:expand_template.bzl", "expand_template")
load("@aspect_rules_js//js:defs.bzl", "js_binary", "js_library")
load("@aspect_rules_lint//format:defs.bzl", "format_multirun", "format_test")
load("@rules_java//java:defs.bzl", "java_binary")

package(default_visibility = ["//:__subpackages__"])

js_library(
    name = "prettierrc",
    srcs = ["//first_party/formatting/prettier:prettier.config.cjs"],
    deps = [
        "//:node_modules/@prettier/plugin-xml",
        "//:node_modules/prettier-plugin-gherkin",
        "//:node_modules/prettier-plugin-sql",
    ],
)

alias(
    name = "prettier",
    actual = "//first_party/formatting/prettier:prettier_binary",
)

java_binary(
    name = "java-format",
    jvm_flags = [
        "--add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
        "--add-exports jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
        "--add-exports jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED",
        "--add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
        "--add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
    ],
    main_class = "com.google.googlejavaformat.java.Main",
    runtime_deps = ["@google-java-format//jar"],
)

alias(
    name = "swiftformat",
    actual = select({
        "@bazel_tools//src/conditions:linux": "@swiftformat",
        "@bazel_tools//src/conditions:darwin": "@swiftformat_mac",
    }),
)

format_multirun(
    name = "autoformat",
    c = "@llvm_toolchain_llvm//:bin/clang-format",
    cc = "@llvm_toolchain_llvm//:bin/clang-format",
    css = ":prettier",
    cuda = "@llvm_toolchain_llvm//:bin/clang-format",
    gherkin = ":prettier",
    go = "@aspect_rules_lint//format:gofumpt",
    graphql = ":prettier",
    html = ":prettier",
    java = ":java-format",
    javascript = ":prettier",
    jsonnet = "@aspect_rules_lint//format:jsonnetfmt",
    kotlin = "//first_party/formatting/ktfmtwrapper:bin",
    markdown = ":prettier",
    protocol_buffer = "@rules_buf_toolchains//:buf",
    python = "@aspect_rules_lint//format:ruff",
    shell = "@aspect_rules_lint//format:shfmt",
    sql = ":prettier",
    starlark = "@buildifier_prebuilt//:buildifier",
    swift = ":swiftformat",
    terraform = "@aspect_rules_lint//format:terraform",
    visibility = ["//visibility:private"],
    xml = ":prettier",
    yaml = "@aspect_rules_lint//format:yamlfmt",
)
