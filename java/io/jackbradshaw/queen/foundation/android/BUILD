load("@io_bazel_rules_kotlin//kotlin:android.bzl", "kt_android_library")

package(default_visibility = ["//visibility:public"])

kt_android_library(
    name = "android",
    srcs = [
        "AndroidDestination.kt",
        "AndroidNavigator.kt",
        "AndroidRoot.kt",
        "MonoActivity.kt",
        "MonoReceiver.kt",
    ],
    manifest = "AndroidManifest.xml",
    plugins = ["//:jetpack_compose_compiler_plugin"],
    resource_files = glob(["res/**/**"]),
    deps = [
        "//java/io/jackbradshaw/queen/foundation:coordinator",
        "//java/io/jackbradshaw/queen/foundation:destination",
        "//java/io/jackbradshaw/queen/foundation:navigator",
        "//java/io/jackbradshaw/queen/foundation:root",
        "//java/io/jackbradshaw/queen/foundation/omniform",
        "//java/io/jackbradshaw/sustainment/primitives",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/queen/ui/platforms/android:android_usable",
        "//java/io/jackbradshaw/queen/ui/platforms/android:compose_usable",
        "//java/io/jackbradshaw/queen/ui/platforms/android:view_usable",
        "//java/io/jackbradshaw/queen/ui/primitives",
        "@maven//:androidx_activity_activity_compose",
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
