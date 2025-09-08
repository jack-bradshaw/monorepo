load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "qualifiers",
    srcs = [
        "Qualifiers.kt",
    ],
    visibility = ["//first_party/otter:__subpackages__"],
    deps = [
        ":dagger",
    ],
)
