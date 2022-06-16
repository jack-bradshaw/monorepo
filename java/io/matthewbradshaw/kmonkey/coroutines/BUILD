load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "dispatcher",
    srcs = [
        "Dispatcher.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/kmonkey3/coroutines:__subpackages__",
        "//java/io/matthewbradshaw/octavius:__subpackages__",
        "//javatests:__subpackages__"
    ],
    deps = [
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
