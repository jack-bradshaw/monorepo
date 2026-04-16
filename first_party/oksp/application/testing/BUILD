load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "testing",
    testonly = True,
    srcs = ["BoundInstanceApplicationComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/oksp/application",
    ],
)
