load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@npm//:defs.bzl", "npm_link_all_packages")
load("@rules_kotlin//kotlin:core.bzl", "define_kt_toolchain", "kt_compiler_plugin")
load("@rules_python//python:pip.bzl", "compile_pip_requirements")

define_kt_toolchain(
    name = "kotlin_toolchain",
    api_version = "2.3",
    jvm_target = "1.8",
    language_version = "2.3",
)

kt_compiler_plugin(
    name = "jetpack_compose_compiler_plugin",
    id = "androidx.compose.compiler.plugins.kotlin",
    visibility = ["//visibility:public"],
    deps = ["@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_compose_compiler_plugin"],
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
