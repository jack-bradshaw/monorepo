load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = [
        "CoreScope.kt",
    ],
    visibility = ["//first_party/backstab:__subpackages__"],
)

kt_jvm_library_with_dagger(
    name = "component",
    srcs = [
        "CoreComponent.kt",
    ],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":scope",
        "//first_party/backstab/core/main",
        "//first_party/backstab/core/main:impl_module",
        "//first_party/backstab/core/repository",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = [
        "CoreComponentImpl.kt",
    ],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//first_party/backstab/core/generator",
        "//first_party/backstab/core/generator:impl_module",
        "//first_party/backstab/core/host",
        "//first_party/backstab/core/main",
        "//first_party/backstab/core/main:impl_module",
    ],
)
