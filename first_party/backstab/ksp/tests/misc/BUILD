load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

package(default_visibility = ["//first_party/backstab/ksp/tests:__subpackages__"])

kt_jvm_library_with_dagger(
    name = "nestedcomponent",
    srcs = ["NestedComponent.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "nestedaggregate",
    srcs = ["NestedAggregate.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "moduleprovidedleaf",
    srcs = ["ModuleProvidedLeaf.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "moduleprovidedmiddle",
    srcs = ["ModuleProvidedMiddle.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "moduleprovidedroot",
    srcs = ["ModuleProvidedRoot.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["MiscTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.tests.misc.MiscTest",
    deps = [
        ":moduleprovidedleaf",
        ":moduleprovidedmiddle",
        ":moduleprovidedroot",
        ":nestedaggregate",
        ":nestedcomponent",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
