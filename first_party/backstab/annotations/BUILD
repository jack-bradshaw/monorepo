load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/backstab:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "aggregate",
    srcs = ["AggregateScope.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "backstab",
    srcs = ["Backstab.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
