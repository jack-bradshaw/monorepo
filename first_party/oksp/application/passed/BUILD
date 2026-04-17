load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "passed",
    testonly = True,
    srcs = ["PassedApplicationComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/oksp/application",
    ],
)
