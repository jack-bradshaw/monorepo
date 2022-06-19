load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "CoreScope.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/gmonkey:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "octavius",
    srcs = [
        "Core.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/gmonkey:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/matthewbradshaw/gmonkey/core/engine",
        "//java/io/matthewbradshaw/gmonkey/core/engine:module",
        "//java/io/matthewbradshaw/gmonkey/core/ignition",
        "//java/io/matthewbradshaw/gmonkey/core/ignition:impl",
        "//java/io/matthewbradshaw/gmonkey/core/ignition:module",
        "//java/io/matthewbradshaw/gmonkey/core/ticker",
        "//java/io/matthewbradshaw/gmonkey/core/ticker:impl",
        "//java/io/matthewbradshaw/gmonkey/core/ticker:module",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)
