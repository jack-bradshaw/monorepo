load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "standard",
    srcs = [
        "InputExt.kt",
        "OutputExt.kt",
        "StandardInputComponent.kt",
        "StandardInputIdentifier.kt",
        "StandardInputLocation.kt",
        "StandardInteractionProfile.kt",
        "StandardOutputIdentifier.kt",
        "StandardOutputLocation.kt",
        "StandardUser.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/model:kt_jvm_proto",
    ],
)

kt_jvm_test(
    name = "InputExtTest",
    srcs = [
        "InputExtTest.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/model:kt_jvm_proto",
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "OutputExtTest",
    srcs = [
        "OutputExtTest.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/model:kt_jvm_proto",
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "StandardInputComponentTest",
    srcs = [
        "StandardInputComponentTest.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "StandardInputIdentifierTest",
    srcs = [
        "StandardInputIdentifierTest.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "StandardInputLocationTest",
    srcs = [
        "StandardInputLocationTest.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "StandardInteractionProfileTest",
    srcs = [
        "StandardInteractionProfileTest.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "StandardOutputLocationTest",
    srcs = [
        "StandardOutputLocationTest.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "StandardUserTest",
    srcs = [
        "StandardUserTest.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
