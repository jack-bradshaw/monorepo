load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "standard",
    srcs = [
        #"StandardInput.kt",
        "StandardInputComponent.kt",
        "StandardInputIdentifier.kt",
        "StandardInputLocation.kt",
        "StandardInteractionProfile.kt",
        #"StandardOutput.kt",
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
        "//java/io/jackbradshaw/otter/openxr/model:proto_extensions",
    ],
)

kt_jvm_library(
    name = "version_1_0",
    srcs = [
        "StandardInput.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":standard",
        "//java/io/jackbradshaw/otter/openxr/model:java_lite_proto",
        "//java/io/jackbradshaw/otter/openxr/model:proto_extensions",
    ],
)
