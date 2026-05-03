load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "sluice",
    srcs = ["Sluice.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/closet/observable",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["SluiceImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":sluice",
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "scope",
    srcs = ["SluiceScope.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
    ],
)

kt_jvm_library(
    name = "factory",
    srcs = ["SluiceFactory.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":sluice",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "factory_impl",
    srcs = ["SluiceFactoryImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":factory",
        ":impl",
        ":scope",
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["SluiceModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":factory",
        ":factory_impl",
    ],
)

kt_jvm_library_with_dagger(
    name = "component",
    srcs = ["SluiceComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":factory",
        ":module",
        ":scope",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
    ],
)
