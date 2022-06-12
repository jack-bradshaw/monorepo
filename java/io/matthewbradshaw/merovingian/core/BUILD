load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "core",
    srcs = [
        "Lifecycle.kt",
        "Restorable.java",
        "Stator.kt",
        "Sustainable.kt",
        "Usable.kt",
    ],
    deps = [
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:com_google_protobuf_protobuf_java",
    ],
)
