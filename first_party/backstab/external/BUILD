load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/backstab:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "external",
    srcs = ["ExternalTypes.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
