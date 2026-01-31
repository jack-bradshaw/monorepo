load("//first_party/releasing/maven:defs.bzl", "maven_release")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "kmonkey",
    visibility = [
        "//first_party:__subpackages__",
    ],
    exports = [
        "//first_party/kmonkey/coroutines",
    ],
)

maven_release(
    name = "release",
    coordinates = "com.jackbradshaw:kmonkey:1.0.0",
    deps = [
        ":kmonkey",
    ],
    project_description = "Kotlin support for the JMonkey video game engine.",
    project_name = "Kotlin JMonkey (KMonkey)",
)
