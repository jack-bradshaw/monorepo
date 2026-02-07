load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@npm//:defs.bzl", "npm_link_all_packages")
load("@rules_kotlin//kotlin:core.bzl", "define_kt_toolchain")
load("@rules_python//python:pip.bzl", "compile_pip_requirements")
load("//first_party/publicity/conformance:conformance.bzl", "conformance_test")

# For compat with Jetpack Compose use Kotlin 1.7 and Java 1.8
define_kt_toolchain(
    name = "kotlin_toolchain",
    api_version = "1.7",
    jvm_target = "1.8",
    language_version = "1.7",
)

npm_link_all_packages(name = "node_modules")

compile_pip_requirements(
    name = "requirements",
    src = "pip_requirements.in",
    requirements_txt = "lock_pip_requirements.txt",
)

dagger_rules(repo_name = "@com_jackbradshaw_maven")

filegroup(
    name = "prettierignore",
    srcs = [".prettierignore"],
    visibility = ["//visibility:public"],
)

conformance_test(
    name = "publicity_conformance_test",
    first_party_root = "//first_party",
)
