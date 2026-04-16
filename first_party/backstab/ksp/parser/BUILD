load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "parser",
    srcs = ["Parser.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:target",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["ParserImpl.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":parser",
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/typeregistry:backstab",
        "//first_party/backstab/core/typeregistry:dagger",
        "//first_party/backstab/core/typeregistry:javax",
        "//first_party/backstab/ksp/adapters",
    ],
)

kt_jvm_library_with_dagger(
    name = "module_impl",
    srcs = ["ParserImplModule.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":impl",
        ":parser",
    ],
)
