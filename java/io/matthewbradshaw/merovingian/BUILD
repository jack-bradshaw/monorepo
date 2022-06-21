load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "merovingianScope.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "merovingian",
    srcs = [
        "MerovingianComponent.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/matthewbradshaw/merovingian/config",
        "//java/io/matthewbradshaw/merovingian/engine",
        "//java/io/matthewbradshaw/merovingian/engine:module",
        "//java/io/matthewbradshaw/merovingian/engine:qualifiers",
        "//java/io/matthewbradshaw/merovingian/host",
        "//java/io/matthewbradshaw/merovingian/host:impl",
        "//java/io/matthewbradshaw/merovingian/ticker",
        "//java/io/matthewbradshaw/merovingian/ticker:impl",
        "//java/io/matthewbradshaw/merovingian/ticker:module",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)
