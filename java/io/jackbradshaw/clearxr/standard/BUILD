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
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/clearxr/model:java_lite_proto",
        "//java/io/jackbradshaw/clearxr/model:proto_extensions",
    ],
)
