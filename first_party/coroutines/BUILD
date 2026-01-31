load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/releasing/maven:defs.bzl", "maven_release")
load("//first_party/releasing/copybara:defs.bzl", "copybara_release")

copybara_release(
    name = "mirror",
    destination_url = "https://github.com/jack-bradshaw/coroutines.git",
    source_dir = "first_party/coroutines",
)

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["CoroutinesScope.kt"],
    visibility = ["//first_party:__subpackages__"],
)

kt_jvm_library(
    name = "component",
    srcs = ["CoroutinesComponent.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["CoroutinesComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//first_party/coroutines/io:module",
    ],
)

maven_release(
    name = "release",
    coordinates = "com.jackbradshaw:coroutines:20260131.103100",
    deps = [
        ":component",
        ":component_impl",
    ],
    project_description = "Foundational coroutine tools for Kotlin.",
    project_name = "Kotlin Coroutine Infrastructure",
)
