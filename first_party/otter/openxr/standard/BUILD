load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/otter:publicity.bzl", "PUBLICITY")

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
    visibility = PUBLICITY,
    deps = ["//first_party/otter/openxr/model:kt_jvm_proto"],
)

kt_jvm_test(
    name = "input_ext_test",
    srcs = ["InputExtTest.kt"],
    test_class = "com.jackbradshaw.otter.openxr.standard.InputExtTest",
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
    srcs = ["OutputExtTest.kt"],
    test_class = "com.jackbradshaw.otter.openxr.standard.OutputExtTest",
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
    srcs = ["StandardInputComponentTest.kt"],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardInputComponentTest",
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_input_identifier_test",
    srcs = ["StandardInputIdentifierTest.kt"],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardInputIdentifierTest",
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_input_location_test",
    srcs = ["StandardInputLocationTest.kt"],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardInputLocationTest",
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_interaction_profile_test",
    srcs = ["StandardInteractionProfileTest.kt"],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardInteractionProfileTest",
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_output_location_test",
    srcs = ["StandardOutputLocationTest.kt"],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardOutputLocationTest",
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "standard_user_test",
    srcs = ["StandardUserTest.kt"],
    test_class = "com.jackbradshaw.otter.openxr.standard.StandardUserTest",
    deps = [
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
