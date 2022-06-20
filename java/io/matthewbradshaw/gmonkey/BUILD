load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "GMonkeyScope.kt",
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
    name = "gmonkey",
    srcs = [
        "GMonkey.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/gmonkey:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/matthewbradshaw/gmonkey/coroutines:qualifier",
        "//java/io/matthewbradshaw/gmonkey/engine",
        "//java/io/matthewbradshaw/gmonkey/engine:module",
        "//java/io/matthewbradshaw/gmonkey/ticker",
        "//java/io/matthewbradshaw/gmonkey/ticker:impl",
        "//java/io/matthewbradshaw/gmonkey/ticker:module",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)
