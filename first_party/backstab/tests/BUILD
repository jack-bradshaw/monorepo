load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("@rules_java//java:defs.bzl", "java_plugin")

kt_jvm_library(
    name = "test_components",
    srcs = ["TestComponents.kt"],
    plugins = [
        "//first_party/backstab/processor:plugin",
    ],
    deps = [
        "//first_party/backstab/annotations",
        "//:dagger",
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

kt_jvm_library(
    name = "generator_test_components",
    srcs = ["GeneratorTestComponents.kt"],
    plugins = [
        "//first_party/backstab/processor:plugin",
        "//:dagger-compiler",
    ],
    deps = [
        "//first_party/backstab/annotations",
        "//:dagger",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
    visibility = ["//visibility:private"],
)

kt_jvm_test(
   name = "backstab_generator_test",
   srcs = ["BackstabGeneratorTest.kt"],
   test_class = "com.jackbradshaw.backstab.tests.BackstabGeneratorTest",
   deps = [
       "//first_party/backstab/annotations",
       "//first_party/backstab/processor",
       "@com_jackbradshaw_maven//:junit_junit",
       "@com_jackbradshaw_maven//:com_google_dagger_dagger",
       "@com_jackbradshaw_maven//:javax_inject_javax_inject",
       "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
   ],
)
