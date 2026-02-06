load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:defs.bzl", "quarantined")

PUBLICITY = quarantined("//first_party/otter/openxr/events")

kt_jvm_library(
    name = "events",
    srcs = ["Events.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/otter/openxr/manifest/encoder",
        "//first_party/otter/openxr/model:kt_jvm_proto",
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["EventsImpl.kt"],
    deps = [
        ":events",
        "//first_party/otter:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["EventsModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":events",
        ":impl",
    ],
)
