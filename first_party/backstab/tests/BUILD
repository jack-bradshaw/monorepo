load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("@rules_java//java:defs.bzl", "java_plugin")

java_plugin(
    name = "dagger_compiler_plugin",
    deps = [
        "@com_jackbradshaw_maven//:com_google_dagger_dagger_compiler",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
    generates_api = True,
    processor_class = "dagger.internal.codegen.ComponentProcessor",
)

kt_jvm_library(
    name = "test_components",
    srcs = ["TestComponents.kt"],
    plugins = [
        "//first_party/backstab/processor:plugin",
        ":dagger_compiler_plugin",
    ],
    deps = [
        "//first_party/backstab/annotations",
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
    visibility = ["//visibility:private"],
)

kt_jvm_test(
    name = "backstab_integration_test",
    srcs = ["BackstabIntegrationTest.kt"],
    test_class = "com.jackbradshaw.backstab.tests.BackstabIntegrationTest",
    deps = [
        ":test_components",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect", 
    ],
)
