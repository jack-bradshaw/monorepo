load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "lifecycle",
    srcs = [
        "Preparable.kt",
        "Restorable.kt",
        "Suspendable.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/gmonkey/octavius:__subpackages__"],
    deps = [
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
