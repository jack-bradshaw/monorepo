load("@io_bazel_rules_kotlin//kotlin:android.bzl", "kt_android_library")

package(default_visibility = ["//visibility:public"])

kt_android_library(
    name = "android_usable",
    srcs = ["AndroidUi.kt"],
    deps = [
        "//java/io/jackbradshaw/queen/ui/primitives",
    ],
)

kt_android_library(
    name = "compose_usable",
    srcs = ["AndroidComposeUi.kt"],
    plugins = ["//:jetpack_compose_compiler_plugin"],
    deps = [
        ":android_usable","@maven//:androidx_activity_activity_compose",
        "@maven//:androidx_appcompat_appcompat",
        "@maven//:androidx_compose_foundation_foundation",
        "@maven//:androidx_compose_foundation_foundation_layout",
        "@maven//:androidx_compose_material_material",
        "@maven//:androidx_compose_runtime_runtime",
        "@maven//:androidx_compose_compiler_compiler",
        "@maven//:androidx_compose_ui_ui",
        "@maven//:androidx_compose_ui_ui_tooling",
        "@maven//:androidx_core_core",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        
    ],
)

kt_android_library(
    name = "view_usable",
    srcs = ["AndroidViewUi.kt"],
    deps = [":android_usable"],
)
