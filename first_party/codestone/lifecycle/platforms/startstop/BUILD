load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "startstop",
    srcs = ["StartStopWorker.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/worker",
        "//first_party/codestone/lifecycle/startstop",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect", 
    ],
)

kt_jvm_library(
    name = "platform",
    srcs = ["StartStopPlatform.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/orchestrator/factory:platform",
        "//first_party/codestone/lifecycle/platforms/startstop/converters:pass_through_converter",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
    ],
)
