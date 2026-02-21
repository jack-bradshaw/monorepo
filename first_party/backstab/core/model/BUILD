load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "source_header",
    srcs = ["SourceHeader.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
)

kt_jvm_library(
    name = "type",
    srcs = ["Type.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
)

kt_jvm_library(
    name = "target",
    srcs = ["BackstabTarget.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":source_header",
        ":type",
        "//first_party/backstab/core/annotations:backstab",
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["BackstabModule.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":source_header",
    ],
)
