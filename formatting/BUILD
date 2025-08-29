load("@aspect_bazel_lib//lib:expand_template.bzl", "expand_template")
load("@aspect_rules_js//js:defs.bzl", "js_library")
load("@aspect_rules_lint//format:defs.bzl", "format_multirun", "format_test")
load("@npm//:prettier/package_json.bzl", prettier = "bin")
load("@rules_java//java:defs.bzl", "java_binary")

package(default_visibility = ["//:__subpackages__"])

js_library(
    name = "prettierrc",
    srcs = ["prettier.config.cjs"],
    deps = [
        "//:node_modules/@prettier/plugin-xml",
        "//:node_modules/prettier-plugin-gherkin",
        "//:node_modules/prettier-plugin-sql",
    ],
)

prettier.prettier_binary(
    name = "prettier",
    data = [":prettierrc"],
    # Allow the binary to be run outside bazel
    env = {"BAZEL_BINDIR": "."},
    fixed_args = [
        # `require` statements in the config file will be resolved relative to its location
        # Therefore to make it hermetic, prettier must be pointed at the copy of the config file
        # in the runfiles folder rather than the one in the source folder.
        "--config=\"$$JS_BINARY__RUNFILES\"/$(rlocationpath :prettierrc)",
        # default log level is "log" which spams on success
        # https://prettier.io/docs/en/cli.html#--log-level
        # NB: prettier 2 names this loglevel, in prettier 3 it's renamed log-level, see
        # https://prettier.io/blog/2023/07/05/3.0.0.html#cli-1
        "--loglevel=warn",
    ],
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

java_binary(
    name = "ktfmt",
    main_class = "com.facebook.ktfmt.cli.Main",
    runtime_deps = ["@ktfmt//jar"],
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
    kotlin = ":ktfmt",
    markdown = ":prettier",
    #protocol_buffer = "//tools/lint:buf",
    python = "@aspect_rules_lint//format:ruff",
    shell = "@aspect_rules_lint//format:shfmt",
    sql = ":prettier",
    starlark = "@buildifier_prebuilt//:buildifier",
    swift = ":swiftformat",
    terraform = "@aspect_rules_lint//format:terraform",
    visibility = ["//:__subpackages__"],
    xml = ":prettier",
    yaml = "@aspect_rules_lint//format:yamlfmt",
)
