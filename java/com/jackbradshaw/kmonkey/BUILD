load("@rules_java//java:java_binary.bzl", "java_binary")
load("@rules_jvm_external//:defs.bzl", "java_export")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "kmonkey",
    visibility = [
        "//java/com/jackbradshaw:__subpackages__",
    ],
    exports = [
        "//java/com/jackbradshaw/kmonkey/coroutines",
    ],
)

java_binary(
    name = "binary",
    visibility = [
        "//java/com/jackbradshaw:__subpackages__",
    ],
    runtime_deps = [
        "//java/com/jackbradshaw/kmonkey",
    ],
)

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "com.jackbradshaw:kmonkey:1.0.0",
    pom_template = "pom_template.xml",
    deps = [
        ":kmonkey",
    ],
)
