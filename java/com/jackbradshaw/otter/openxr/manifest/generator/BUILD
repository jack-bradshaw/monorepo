load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "generator",
    srcs = [
        "ManifestGenerator.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/com/jackbradshaw/klu/flow",
        "//java/com/jackbradshaw/otter/openxr/model:kt_jvm_proto",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "ManifestGeneratorImpl.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":generator",
        "//java/com/jackbradshaw/klu/flow",
        "//java/com/jackbradshaw/otter:scope",
        "//java/com/jackbradshaw/otter/config:kt_jvm_proto",
        "//java/com/jackbradshaw/otter/openxr/manifest/encoder",
        "//java/com/jackbradshaw/otter/openxr/model:kt_jvm_proto",
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_code_gson_gson",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "ManifestGeneratorModule.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":generator",
        ":impl",
    ],
)
