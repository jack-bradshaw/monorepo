load("@npm//:defs.bzl", "npm_link_all_packages")
load("@rules_kotlin//kotlin:core.bzl", "define_kt_toolchain")
load("@rules_python//python:pip.bzl", "compile_pip_requirements")

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
