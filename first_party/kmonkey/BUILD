load("@rules_jvm_external//:defs.bzl", "java_export")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "com.jackbradshaw:kmonkey:1.0.0",
    pom_template = "pom_template.xml",
    deps = ["//first_party/kmonkey/coroutines"],
)
