load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "dagger",
    srcs = ["TypeRegistry.kt"],
    visibility = [
        "//first_party/backstab/processor:__subpackages__",
    ],
    deps = [
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "javax",
    srcs = ["TypeRegistry.kt"],
    visibility = [
        "//first_party/backstab/processor:__subpackages__",
    ],
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
    ],
)
