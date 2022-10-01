load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

filegroup(
    name = "manifest_files",
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
)

kt_jvm_library(
    name = "goldens",
    srcs = [
        "Goldens.kt",
    ],
    data = [":manifest_files"],
    visibility = [
        "//javatests/io/jackbradshaw/otter/openxr/manifest:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/otter/openxr/manifest/encoder",
        "//java/io/jackbradshaw/otter/openxr/manifest/installer",
        "//java/io/jackbradshaw/otter/openxr/standard",
        "@bazel_tools//tools/java/runfiles",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
