load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library_with_dagger(
    name = "input",
    srcs = ["StandardInput.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["StandardInputModule.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":input",
        "//first_party/sasync/inbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
