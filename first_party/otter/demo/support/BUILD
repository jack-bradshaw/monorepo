load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "module",
    testonly = True,
    srcs = [
        "SupportModule.kt",
    ],
    visibility = [
        "//first_party/otter/demo:__subpackages__",
    ],
    deps = [
        "//:dagger",
        "//first_party/otter/demo:scope",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
