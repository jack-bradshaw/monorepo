load("@aspect_rules_lint//format:defs.bzl", "format_multirun")
load("@rules_java//java:defs.bzl", "java_binary")
load(":publicity.bzl", "PUBLICITY")

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

# The select() function cannot be used directly from a format_multirun call to Bazel restrictions
# so it must be used via an alias.
alias(
    name = "swiftformat",
    actual = select({
        "@bazel_tools//src/conditions:linux": "@swiftformat",
        "@bazel_tools//src/conditions:darwin": "@swiftformat_mac",
    }),
)

format_multirun(
    name = "formatting",
    c = "@llvm_toolchain_llvm//:bin/clang-format",
    cc = "@llvm_toolchain_llvm//:bin/clang-format",
    css = "//first_party/formatting/prettier:binary",
    cuda = "@llvm_toolchain_llvm//:bin/clang-format",
    gherkin = "//first_party/formatting/prettier:binary",
    go = "@aspect_rules_lint//format:gofumpt",
    graphql = "//first_party/formatting/prettier:binary",
    html = "//first_party/formatting/prettier:binary",
    java = ":java-format",
    javascript = "//first_party/formatting/prettier:binary",
    jsonnet = "@aspect_rules_lint//format:jsonnetfmt",
    kotlin = "//first_party/formatting/ktfmtwrapper:bin",
    markdown = "//first_party/formatting/prettier:binary",
    protocol_buffer = "@rules_buf_toolchains//:buf",
    python = "@aspect_rules_lint//format:ruff",
    shell = "@aspect_rules_lint//format:shfmt",
    sql = "//first_party/formatting/prettier:binary",
    starlark = "@buildifier_prebuilt//:buildifier",
    swift = ":swiftformat",
    terraform = "@aspect_rules_lint//format:terraform",
    visibility = PUBLICITY,
    xml = "//first_party/formatting/prettier:binary",
    yaml = "@aspect_rules_lint//format:yamlfmt",
)
