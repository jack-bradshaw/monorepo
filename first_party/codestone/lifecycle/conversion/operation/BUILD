load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "pass_through_converter",
    srcs = ["PassThroughUniConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/work",
        ":work",
    ],
)


kt_jvm_library(
    name = "work",
    srcs = ["StartStopWork.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect", 
    ],
)   


java_library(
    name = "startstop",
    exports = [
        ":pass_through_converter",
    ],
    runtime_deps = [":pass_through_converter", ":work"],
)

alias(
    name = "operation",
    actual = ":startstop",
)