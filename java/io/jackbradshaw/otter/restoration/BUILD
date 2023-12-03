#load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
#load("@dagger//:workspace_defs.bzl", "dagger_rules")
#
#dagger_rules()
#
#proto_library(
#    name = "proto",
#    srcs = [
#        "restoration_id.proto",
#    ],
#    visibility = [
#        "//java/io/jackbradshaw/otter:__subpackages__",
#        "//javatests:__subpackages__",
#    ],
#)
#
#java_proto_library(
#    name = "java_proto",
#    visibility = [
#        "//java/io/jackbradshaw/otter:__subpackages__",
#        "//javatests:__subpackages__",
#    ],
#    deps = [":proto"],
#)
#
#java_lite_proto_library(
#    name = "java_lite_protos",
#    visibility = [
#        "//java/io/jackbradshaw/otter:__subpackages__",
#        "//javatests:__subpackages__",
#    ],
#    deps = [":proto"],
#)
#
#kt_jvm_library(
#    name = "snapshot",
#    srcs = [
#        "SnapshotStore.kt",
#    ],
#    visibility = [
#        "//java/io/jackbradshaw/otter:__subpackages__",
#        "//javatests:__subpackages__",
#    ],
#    deps = [
#        ":java_lite_protos",
#        "//java/io/jackbradshaw/klu/flow",
#        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
#    ],
#)
