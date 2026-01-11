load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "root",
    srcs = ["Root.kt"],
    deps = [
        "//first_party/codestone/interaction/coordinator",
        "//first_party/codestone/interaction/destination",
        "//first_party/codestone/interaction/navigator",
        "//first_party/codestone/interaction/usable",
        "//first_party/codestone/lifecycle/worker",
        "//first_party/codestone/lifecycle/startstop",
    ],
)
