load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/kale:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "source",
    srcs = [
        "Source.kt",
    ],
    visibility = PUBLICITY,
)

kt_jvm_library(
    name = "resource",
    srcs = [
        "Resource.kt",
    ],
    visibility = PUBLICITY,
)

kt_jvm_library(
    name = "versions",
    srcs = [
        "Versions.kt",
    ],
    visibility = PUBLICITY,
)

kt_jvm_library(
    name = "artifacts",
    srcs = [
        "Artifacts.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":resource",
        ":source",
    ],
)

kt_jvm_library(
    name = "log",
    srcs = [
        "Log.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "result",
    srcs = [
        "Result.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":artifacts",
        ":log",
    ],
)

kt_jvm_library(
    name = "artefacts",
    srcs = [
        "KspArtefacts.kt",
        "JvmSource.kt",
        "JvmClass.kt",
        "KspVersions.kt",
    ],
    visibility = PUBLICITY,
)
