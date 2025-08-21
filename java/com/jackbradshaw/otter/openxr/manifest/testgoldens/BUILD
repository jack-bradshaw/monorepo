load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

filegroup(
    name = "manifest_files",
    testonly = True,
    srcs = [
        "google_daydream_controller.json",
        "htc_vive_controller.json",
        "htc_vive_pro.json",
        "khr_simple_controller.json",
        "microsoft_motion_controller.json",
        "microsoft_xbox_controller.json",
        "occulus_go_controller.json",
        "occulus_touch_controller.json",
        "primary_manifest.json",
        "valve_index_controller.json",
    ],
    visibility = ["//visibility:private"],
)

kt_jvm_library(
    name = "testgoldens",
    testonly = True,
    srcs = [
        "Goldens.kt",
    ],
    data = [":manifest_files"],
    visibility = [
        "//java/com/jackbradshaw/otter/openxr/manifest:__subpackages__",
    ],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/manifest/encoder",
        "//java/com/jackbradshaw/otter/openxr/manifest/installer",
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@bazel_tools//tools/java/runfiles",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
