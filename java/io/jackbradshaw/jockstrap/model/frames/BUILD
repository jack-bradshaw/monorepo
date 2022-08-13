load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "frames",
    srcs = [
        "Hostable.kt",
        "Placeable.kt",
        "Restorable.kt",
        "Simulatable.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/jockstrap/physics:java_lite_proto",
        "//java/io/jackbradshaw/klu/flow",
        "@maven//:com_google_protobuf_protobuf_lite",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
