load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

proto_library(
    name = "proto",
    srcs = [
        "input.proto",
        "input_component.proto",
        "input_identifier.proto",
        "input_location.proto",
        "interaction_profile.proto",
        "output.proto",
        "output_identifier.proto",
        "output_location.proto",
        "user.proto",
    ],
    visibility = [
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
)

java_proto_library(
    name = "java_proto",
    visibility = [
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

java_lite_proto_library(
    name = "java_lite_proto",
    visibility = [
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "InputComponentExt.kt",
        "InputExt.kt",
        "InputIdentifierExt.kt",
        "InputLocationExt.kt",
        "InteractionProfileExt.kt",
        "OutputExt.kt",
        "OutputIdentifier.kt",
        "OutputLocation.kt",
        "UserExt.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":java_lite_proto",
    ],
)
