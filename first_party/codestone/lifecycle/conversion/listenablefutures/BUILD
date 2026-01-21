load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "backwards_converter",
    srcs = ["BackwardsUniConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/conversion/operation:work",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "forwards_converter",
    srcs = ["ForwardsUniConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/conversion/operation:work",
        ":work",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "pass_through_converter",
    srcs = ["PassThroughUniConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/work",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "work",
    srcs = ["ListenableFutureWork.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
    ],
)

java_library(
    name = "listenablefutures",
    exports = [
        ":backwards_converter",
        ":forwards_converter",
        ":pass_through_converter",
        ":work",
    ],
)
