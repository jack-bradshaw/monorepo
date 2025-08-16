load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "coroutines",
    srcs = [
        "Dispatchers.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)
