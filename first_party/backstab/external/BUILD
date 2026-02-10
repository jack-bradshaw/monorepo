load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "external",
    srcs = ["ExternalTypes.kt"],
    visibility = [
        "//first_party/backstab/processor:__subpackages__",
        "//first_party/backstab/external:__subpackages__",
    ],
    deps = [
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
