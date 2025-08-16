load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "flow",
    srcs = [
        "Annotations.kt",
        "Operators.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
