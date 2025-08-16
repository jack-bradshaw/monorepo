#load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
#load("@dagger//:workspace_defs.bzl", "dagger_rules")
#
#dagger_rules(repo_name="@com_jackbradshaw_maven")
#
#proto_library(
#    name = "proto",
#    srcs = [
#        "restoration_id.proto",
#    ],
#    visibility = [
#        "//java/com/jackbradshaw/otter:__subpackages__",
#        "//javatests:__subpackages__",
#    ],
#)
#
#java_proto_library(
#    name = "java_proto",
#    visibility = [
#        "//java/com/jackbradshaw/otter:__subpackages__",
#        "//javatests:__subpackages__",
#    ],
#    deps = [":proto"],
#)
#
#kt_jvm_proto_library(
#    name = "kt_jvm_protos",
#    visibility = [
#        "//java/com/jackbradshaw/otter:__subpackages__",
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
#        "//java/com/jackbradshaw/otter:__subpackages__",
#        "//javatests:__subpackages__",
#    ],
#    deps = [
#        ":kt_jvm_protos",
#        "//java/com/jackbradshaw/klu/flow",
#        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
#    ],
#)
