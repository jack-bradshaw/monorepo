load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@rules_jvm_external//:defs.bzl", "java_export")

kt_jvm_library(
    name = "klu",
    visibility = [
        "//java/io/matthewbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    exports = [
        "//java/io/matthewbradshaw/klu/concurrency",
    ],
)

java_binary(
    name = "klu_full",
    visibility = [
        "//java/io/matthewbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    runtime_deps = [
        "//java/io/matthewbradshaw/klu/concurrency",
    ],
)

java_export(
    name = "klu_release",
    srcs = ["Stub.java"],
    maven_coordinates = "io.matthewbradshaw:klu:2.1.0",
    pom_template = "pom_template",
    deps = [
        ":klu",
    ],
)
