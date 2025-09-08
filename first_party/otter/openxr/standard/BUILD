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
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        "//first_party/otter/openxr/model:kt_jvm_proto",
    ],
)

kt_jvm_test(
    name = "input_ext_test",
    srcs = [
        "InputExtTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.standard.InputExtTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter/openxr/model:kt_jvm_proto",
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "output_ext_test",
    srcs = [
        "OutputExtTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.standard.OutputExtTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter/openxr/model:kt_jvm_proto",
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_input_component_test",
    srcs = [
        "StandardInputComponentTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardInputComponentTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_input_identifier_test",
    srcs = [
        "StandardInputIdentifierTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardInputIdentifierTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_input_location_test",
    srcs = [
        "StandardInputLocationTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardInputLocationTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_interaction_profile_test",
    srcs = [
        "StandardInteractionProfileTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardInteractionProfileTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_output_location_test",
    srcs = [
        "StandardOutputLocationTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardOutputLocationTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_user_test",
    srcs = [
        "StandardUserTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardUserTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
