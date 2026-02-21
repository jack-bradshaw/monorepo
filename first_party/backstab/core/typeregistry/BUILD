load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "dagger",
    srcs = ["DaggerTypeRegistry.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_library(
    name = "javax",
    srcs = ["JavaxTypeRegistry.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "backstab",
    srcs = ["BackstabTypeRegistry.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)
