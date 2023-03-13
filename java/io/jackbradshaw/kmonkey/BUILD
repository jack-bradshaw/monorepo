load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@rules_jvm_external//:defs.bzl", "java_export")

kt_jvm_library(
    name = "kmonkey",
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    exports = [
        "//java/io/jackbradshaw/kmonkey/coroutines",
    ],
)

java_binary(
    name = "binary",
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    runtime_deps = [
        "//java/io/jackbradshaw/kmonkey",
    ],
)

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "io.jackbradshaw:kmonkey:1.0.0",
    pom_template = "pom_template.xml",
    deps = [
        ":kmonkey",
    ],
)
