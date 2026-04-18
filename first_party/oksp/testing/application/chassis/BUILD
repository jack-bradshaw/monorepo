load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "chassis",
    testonly = True,
    srcs = [
        "ApplicationChassis.kt",
        "ApplicationChassisComponent.kt",
        "ApplicationChassisImpl.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/kale/model:result",
        "//first_party/kale/model:source",
        "//first_party/kale/provider:component",
        "//first_party/kale/provider:runner",
        "//first_party/oksp/application",
        "//first_party/oksp/application/passed",
        "//first_party/oksp/entrypoint:entrypoint_impl",
    ],
)
