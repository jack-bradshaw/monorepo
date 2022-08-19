load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@rules_jvm_external//:defs.bzl", "java_export")

kt_jvm_library(
    name = "klu",
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    exports = [
        "//java/io/jackbradshaw/klu/concurrency",
    ],
)

java_binary(
    name = "klu_binary",
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    runtime_deps = [
        "//java/io/jackbradshaw/klu/concurrency",
    ],
)

java_export(
    name = "klu_release",
    srcs = ["Stub.java"],
    maven_coordinates = "io.jackbradshaw:klu:2.1.0",
    pom_template = "pom_template",
    deps = [
        ":klu",
    ],
)
