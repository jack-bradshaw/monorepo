load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "lifecycle",
    srcs = [
        "Pausable.kt",
        "Preparable.kt",
        "Restorable.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/merovingian:__subpackages__"],
    deps = [
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
