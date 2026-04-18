load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/quinn:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "scope",
    srcs = ["QuinnScope.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
