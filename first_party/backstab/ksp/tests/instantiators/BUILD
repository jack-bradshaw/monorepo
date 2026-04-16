load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

package(default_visibility = ["//first_party/backstab/ksp/tests:__subpackages__"])

kt_jvm_library_with_dagger(
    name = "builder",
    srcs = ["Builder.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "custombuildfunctionname",
    srcs = ["CustomBuildFunctionName.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "customfactoryfunctionname",
    srcs = ["CustomFactoryFunctionName.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "factory",
    srcs = ["Factory.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "implicit",
    srcs = ["Implicit.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "customcreationfunctionname",
    srcs = ["CustomCreationFunctionName.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["InstantiatorsTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.tests.instantiators.InstantiatorsTest",
    deps = [
        ":builder",
        ":custombuildfunctionname",
        ":customcreationfunctionname",
        ":customfactoryfunctionname",
        ":factory",
        ":implicit",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
