load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

package(default_visibility = ["//first_party/backstab/ksp/tests:__subpackages__"])

kt_jvm_library_with_dagger(
    name = "rootcomponent",
    srcs = ["RootComponent.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "transitivecomponent",
    srcs = ["TransitiveComponent.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "multiplecomponents",
    srcs = ["MultipleComponents.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["ExposuresTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.tests.exposures.ExposuresTest",
    deps = [
        ":multiplecomponents",
        ":rootcomponent",
        ":transitivecomponent",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
