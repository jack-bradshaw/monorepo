load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "OctaviusScope.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "octavius",
    srcs = [
        "Octavius.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
        ":scope",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
        "//java/io/matthewbradshaw/octavius/jmonkey:module",
        "//java/io/matthewbradshaw/octavius/heartbeat:module",
        "//java/io/matthewbradshaw/octavius/core:core",
    ],
)
