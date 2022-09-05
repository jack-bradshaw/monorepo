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
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/omnixr/model:java_lite_proto",
        "//java/io/jackbradshaw/omnixr/model:proto_extensions",
    ],
)

kt_jvm_library(
    name = "version_1_0",
    srcs = [
        "StandardInput.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":standard",
        "//java/io/jackbradshaw/omnixr/model:java_lite_proto",
        "//java/io/jackbradshaw/omnixr/model:proto_extensions",
    ],
)
