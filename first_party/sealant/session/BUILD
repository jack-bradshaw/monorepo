load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/sealant:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "session",
    srcs = ["SealedSession.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/observable",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["SealedSessionImpl.kt"],
    deps = [
        ":session",
        "//first_party/closet/observable/helpers",
        "//first_party/closet/observable/standard",
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["SealedSessionModule.kt"],
    visibility = ["//first_party/sealant:__subpackages__"],
    deps = [
        ":impl",
        ":session",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["SealedSessionTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":session",
        "//first_party/chronosphere/testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
