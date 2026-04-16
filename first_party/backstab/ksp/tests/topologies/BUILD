load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

package(default_visibility = ["//first_party/backstab/ksp/tests:__subpackages__"])

kt_jvm_library_with_dagger(
    name = "isolated",
    srcs = ["Isolated.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "shallow",
    srcs = ["Shallow.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "deep",
    srcs = ["Deep.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "diamond",
    srcs = ["Diamond.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "wide",
    srcs = ["Wide.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "triangle",
    srcs = ["Triangle.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "broad",
    srcs = ["Broad.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "disconnected",
    srcs = ["Disconnected.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "joinedatbase",
    srcs = ["JoinedAtBase.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "joineddiamond",
    srcs = ["JoinedDiamond.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "convergingchains",
    srcs = ["ConvergingChains.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_library_with_dagger(
    name = "asymmetricchains",
    srcs = ["AsymmetricChains.kt"],
    plugins = ["//first_party/backstab:plugin"],
    deps = [
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["TopologiesTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.tests.topologies.TopologiesTest",
    deps = [
        ":asymmetricchains",
        ":broad",
        ":convergingchains",
        ":deep",
        ":diamond",
        ":disconnected",
        ":isolated",
        ":joinedatbase",
        ":joineddiamond",
        ":shallow",
        ":triangle",
        ":wide",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
