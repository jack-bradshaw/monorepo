workspace(name = "io_matthewbradshaw")

load("//:repositories-bazel.bzl", "io_matthewbradshaw_bazel_repositories")

io_matthewbradshaw_bazel_repositories()

load("//:repositories-maven.bzl", "io_matthewbradshaw_maven_repositories")

io_matthewbradshaw_maven_repositories()

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_rules_dependencies()

go_register_toolchains()

load(
    "@io_bazel_rules_kotlin//kotlin:repositories.bzl",
    "kotlin_repositories",
)

kotlin_repositories()

load(
    "@io_bazel_rules_kotlin//kotlin:core.bzl",
    "kt_register_toolchains",
)

kt_register_toolchains()
