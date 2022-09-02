load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

proto_library(
    name = "proto",
    srcs = [
        "input_component.proto",
        "input_identifier.proto",
        "input_location.proto",
        "input_spec.proto",
        "interaction_profile.proto",
        "output_identifier.proto",
        "output_location.proto",
        "output_spec.proto",
        "user.proto",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
)

java_proto_library(
    name = "java_proto",
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

java_lite_proto_library(
    name = "java_lite_proto",
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "InputComponentExt.kt",
        "InputIdentifierExt.kt",
        "InputLocationExt.kt",
        "InputSpecExt.kt",
        "InteractionProfileExt.kt",
        "OutputIdentifier.kt",
        "OutputLocation.kt",
        "OutputSpecExt.kt",
        "UserExt.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":java_lite_proto",
    ],
)
