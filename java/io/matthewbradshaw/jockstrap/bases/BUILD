load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "BaseComponent.kt",
        #"BaseEntity.kt",
        #  "BaseGame.kt",
        #  "BaseLevel.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        "//java/io/matthewbradshaw/jockstrap/elements:java_lite_protos",
        "//java/io/matthewbradshaw/jockstrap/elements:proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/math:jmonkey_conversion",
        "//java/io/matthewbradshaw/jockstrap/math:proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/physics:java_lite_proto",
        "//java/io/matthewbradshaw/jockstrap/physics:jmonkey_conversion",
        "//java/io/matthewbradshaw/jockstrap/physics:proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/sensation:java_lite_proto",
        "//java/io/matthewbradshaw/jockstrap/sensation:jmonkey_conversion",
        "//java/io/matthewbradshaw/klu/flow",
    ],
)
