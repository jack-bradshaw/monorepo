load("@rules_kotlin//kotlin:android.bzl", "kt_android_library", "kt_android_local_test")

package(default_visibility = ["//visibility:public"])

kt_android_library(
    name = "android_usable",
    srcs = ["AndroidUi.kt"],
    deps = [
        "//first_party/codestone/interaction/usable",
    ],
)

kt_android_library(
    name = "compose_usable",
    srcs = ["AndroidComposeUi.kt"],
    plugins = ["//:jetpack_compose_compiler_plugin"],
    deps = [
        ":android_usable",
        "@com_jackbradshaw_maven//:androidx_appcompat_appcompat",
        "@com_jackbradshaw_maven//:androidx_compose_foundation_foundation",
        "@com_jackbradshaw_maven//:androidx_compose_foundation_foundation_layout",
        "@com_jackbradshaw_maven//:androidx_compose_material_material",
        "@com_jackbradshaw_maven//:androidx_compose_runtime_runtime",
        "@com_jackbradshaw_maven//:androidx_compose_ui_ui",
        "@com_jackbradshaw_maven//:androidx_core_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_android_library(
    name = "view_usable",
    srcs = ["AndroidViewUi.kt"],
    deps = [":android_usable"],
)

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
        ":android_usable",
        ":compose_usable",
        ":view_usable",
        "//first_party/codestone/interaction/coordinator",
        "//first_party/codestone/interaction/coordinator:impl",
        "//first_party/codestone/interaction/destination",
        "//first_party/codestone/interaction/navigator",
        "//first_party/codestone/interaction/root",
        "//first_party/codestone/interaction/usable",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/startstop",
        "@com_jackbradshaw_maven//:androidx_appcompat_appcompat",
        "@com_jackbradshaw_maven//:androidx_compose_foundation_foundation",
        "@com_jackbradshaw_maven//:androidx_compose_foundation_foundation_layout",
        "@com_jackbradshaw_maven//:androidx_compose_material_material",
        "@com_jackbradshaw_maven//:androidx_compose_runtime_runtime",
        "@com_jackbradshaw_maven//:androidx_compose_ui_ui",
        "@com_jackbradshaw_maven//:androidx_core_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_android_local_test(
    name = "RoboTest",
    srcs = ["RoboTest.kt"],
    manifest = "AndroidManifest.xml",
    plugins = ["//:jetpack_compose_compiler_plugin"],
    test_class = "com.jackbradshaw.codestone.interaction.platforms.android.RoboTest",
    deps = [
        ":android",
        ":android_usable",
        ":compose_usable",
        ":view_usable",
        "@com_jackbradshaw_maven//:org_robolectric_robolectric",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "//first_party/codestone/interaction/coordinator:impl",
        "@com_jackbradshaw_maven//:androidx_appcompat_appcompat",
        "@com_jackbradshaw_maven//:androidx_compose_foundation_foundation",
        "@com_jackbradshaw_maven//:androidx_compose_foundation_foundation_layout",
        "@com_jackbradshaw_maven//:androidx_compose_material_material",
        "@com_jackbradshaw_maven//:androidx_compose_runtime_runtime",
        "@com_jackbradshaw_maven//:androidx_compose_ui_ui",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:androidx_test_core",
        "@com_jackbradshaw_maven//:androidx_test_espresso_espresso_core",
        "@com_jackbradshaw_maven//:androidx_compose_ui_ui_test_junit4",
        "@com_jackbradshaw_maven//:androidx_compose_ui_ui_test_manifest",
        "//first_party/codestone/lifecycle/platforms/coroutines",
        "//first_party/codestone/lifecycle/platforms/futures",
        "//first_party/codestone/lifecycle/orchestrator/factory:workorchestrators",
        "//first_party/codestone/lifecycle/platforms/startstop",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
    ],
)
