load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/releasing/maven:defs.bzl", "maven_release")
load("//first_party/releasing/copybara:defs.bzl", "copybara_release")

copybara_release(
    name = "mirror",
    destination_url = "https://github.com/jack-bradshaw/concurrency.git",
    source_dir = "first_party/concurrency",
)

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["ConcurrencyScope.kt"],
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "component",
    srcs = ["ConcurrencyComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/concurrency/pulsar",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ConcurrencyComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//first_party/concurrency/pulsar:module",
    ],
)

maven_release(
    name = "release",
    coordinates = "com.jackbradshaw:concurrency:20260131.103100",
    deps = [
        ":component",
        ":component_impl",
    ],
    project_description = "Foundational concurrency tools for Kotlin.",
    project_name = "Kotlin Concurrency Infrastructure",
)
