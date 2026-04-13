load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "adapters",
    srcs = [
        "ksAnnotation.kt",
        "ksDeclaration.kt",
        "ksType.kt",
        "ksTypeArgument.kt",
    ],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/typeregistry:javax",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

alias(
    name = "ksp_extensions",
    actual = ":adapters",
    visibility = ["//first_party/backstab:__subpackages__"],
)

alias(
    name = "backstab_target",
    actual = ":adapters",
    visibility = ["//first_party/backstab:__subpackages__"],
)

alias(
    name = "type",
    actual = ":adapters",
    visibility = ["//first_party/backstab:__subpackages__"],
)
