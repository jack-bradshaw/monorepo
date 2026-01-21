load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "multiconverter",
    srcs = ["MultiConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/startstop",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["MultiConverterImpl.kt"],
    deps = [
        ":multiconverter",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
    ],
)
