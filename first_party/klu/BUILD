load("//first_party/releasing/maven:defs.bzl", "maven_release")
load("//first_party/releasing/copybara:defs.bzl", "copybara_release")

copybara_release(
    name = "mirror",
    destination_url = "https://github.com/jack-bradshaw/klu.git",
    source_dir = "first_party/klu",
)
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "klu",
    visibility = [
        "//first_party:__subpackages__",
    ],
    exports = [
        "//first_party/klu/concurrency",
        "//first_party/klu/flow",
    ],
)

maven_release(
    name = "release",
    coordinates = "com.jackbradshaw:klu:20260131.103100",
    project_description = "A experimental and incomplete lisp implementation.",
    project_name = "Kotlin Lisp (KLU)",
)
