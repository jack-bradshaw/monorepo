load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "navigator",
    srcs = ["Navigator.kt"],
    deps = [
        "//first_party/codestone/interaction/destination",
        "//first_party/codestone/interaction/usable",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/worker",
    ],
)
