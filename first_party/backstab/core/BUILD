load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "core",
    srcs = [
      "BackstabCoreComponent.kt",
      "BackstabCoreScope.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/backstab/core/generator",
        "//first_party/backstab/core/model",
        "//first_party/backstab/core/parser",
        "//first_party/backstab/core/processor",
        "//first_party/backstab/core/writer",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)
