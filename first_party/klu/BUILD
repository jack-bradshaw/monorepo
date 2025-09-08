load("@rules_jvm_external//:defs.bzl", "java_export")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "klu",
    visibility = [
        "//first_party:__subpackages__",
    ],
    exports = [
        "//first_party/klu/concurrency",
        "//first_party/klu/flow",
    ],
)

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "com.jackbradshaw:klu:1.0.0",
    pom_template = "pom_template.xml",
    deps = [
        ":klu",
    ],
)
