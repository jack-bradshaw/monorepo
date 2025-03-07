load("@io_bazel_rules_kotlin//kotlin:android.bzl", "kt_android_library")

package(default_visibility = ["//visibility:public"])

kt_android_library(
    name = "android_usable",
    srcs = ["AndroidUi.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/ui/primitives",
    ],
)

kt_android_library(
    name = "compose_usable",
    srcs = ["AndroidComposeUi.kt"],
    deps = [
        ":android_usable",
        "@maven//:androidx_compose_foundation_foundation",
    ],
)

kt_android_library(
    name = "view_usable",
    srcs = ["AndroidViewUi.kt"],
    deps = [":android_usable"],
)
