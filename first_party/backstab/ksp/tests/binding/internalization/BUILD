load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

package(default_visibility = ["//first_party/backstab/ksp/tests:__subpackages__"])

kt_jvm_library_with_dagger(
    name = "unqualified",
    srcs = ["Unqualified.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "named",
    srcs = ["Named.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "qualified",
    srcs = ["Qualified.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "typeArguments_invariant",
    srcs = ["TypeArgumentsInvariant.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "typeArguments_wide",
    srcs = ["TypeArgumentsWide.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "typeArguments_deep",
    srcs = ["TypeArgumentsDeep.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "typeArguments_covariant",
    srcs = ["TypeArgumentsCovariant.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "typeArguments_contravariant",
    srcs = ["TypeArgumentsContravariant.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "typealias",
    srcs = ["TypeAlias.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["BindingInternalizationTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.tests.binding.internalization.BindingInternalizationTest",
    deps = [
        ":named",
        ":qualified",
        ":typeArguments_contravariant",
        ":typeArguments_covariant",
        ":typeArguments_deep",
        ":typeArguments_invariant",
        ":typeArguments_wide",
        ":typealias",
        ":unqualified",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
