load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library_with_dagger(
    name = "error",
    srcs = ["StandardError.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["StandardErrorModule.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":error",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
