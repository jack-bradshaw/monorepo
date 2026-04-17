load("@rules_jvm_external//:defs.bzl", "java_export")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "scope",
    srcs = ["KluScope.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "com.jackbradshaw:klu:1.0.0",
    pom_template = "pom_template.xml",
    deps = [
        "//first_party/klu/concurrency",
        "//first_party/klu/flow",
    ],
)
