load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "output",
    srcs = ["StandardOutput.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["StandardOutputModule.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":output",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
