load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "omniconverter",
    srcs = ["OmniConverter.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
    ],
)

kt_jvm_library(
    name = "simplex",
    srcs = ["OmniConverterSimplex.kt"],
    deps = [
        ":omniconverter",
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
    ],
)
