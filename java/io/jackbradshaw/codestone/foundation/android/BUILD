load("@io_bazel_rules_kotlin//kotlin:android.bzl", "kt_android_library")

package(default_visibility = ["//visibility:public"])

kt_android_library(
    name = "android",
    srcs = [
        "AndroidFeather.kt",
        "AndroidFoundation.kt",
        "AndroidSystemHorizon.kt",
        "AndroidUserHorizon.kt",
        "AndroidWingsuit.kt",
    ],
    manifest = "AndroidManifest.xml",
    plugins = ["//:jetpack_compose_compiler_plugin"],
    resource_files = glob(["res/**/**"]),
    deps = [
        "//java/io/jackbradshaw/codestone/foundation",
        "//java/io/jackbradshaw/codestone/foundation/omniform",
        "//java/io/jackbradshaw/codestone/sustainment/platforms/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/ui/primitives",
        "//java/io/jackbradshaw/codestone/use/platforms/android:android_usable",
        "//java/io/jackbradshaw/codestone/use/platforms/android:compose_usable",
        "//java/io/jackbradshaw/codestone/use/platforms/android:view_usable",
        "@maven//:androidx_activity_activity_compose",
        "@maven//:androidx_appcompat_appcompat",
        "@maven//:androidx_compose_foundation_foundation",
        "@maven//:androidx_compose_foundation_foundation_layout",
        "@maven//:androidx_compose_material_material",
        "@maven//:androidx_compose_runtime_runtime",
        "@maven//:androidx_compose_ui_ui",
        "@maven//:androidx_compose_ui_ui_tooling",
        "@maven//:androidx_core_core",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
