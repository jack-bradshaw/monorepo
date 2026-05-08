load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency/quinn:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "prod",
    srcs = ["Prod.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["ProdPassThroughComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":prod",
        "//first_party/concurrency/quinn",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ProdPassThroughComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":prod",
        "//first_party/concurrency/quinn",
        "//first_party/concurrency/quinn:component",
        "//first_party/concurrency/quinn:component_impl",
    ],
)
