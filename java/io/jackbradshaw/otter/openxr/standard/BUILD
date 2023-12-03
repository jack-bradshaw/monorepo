load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "standard",
    srcs = [
        "InputExt.kt",
        "OutputExt.kt",
        "StandardInputComponent.kt",
        "StandardInputIdentifier.kt",
        "StandardInputLocation.kt",
        "StandardInteractionProfile.kt",
        "StandardOutputIdentifier.kt",
        "StandardOutputLocation.kt",
        "StandardUser.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/otter/openxr/model:java_lite_proto",
    ],
)
