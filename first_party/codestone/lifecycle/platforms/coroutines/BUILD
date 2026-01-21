load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "coroutines",
    srcs = ["CoroutineWorker.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/worker",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "platform",
    srcs = ["CoroutinePlatform.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/orchestrator/factory:platform",
        "//first_party/codestone/lifecycle/platforms/coroutines/converters:forwards_converter",
        "//first_party/codestone/lifecycle/platforms/coroutines/converters:backwards_converter",
        "//first_party/codestone/lifecycle/platforms/coroutines/converters:pass_through_converter",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
