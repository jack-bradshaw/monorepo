load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")

def io_matthewbradshaw_git_repositories():
    maybe(
        git_repository,
        name = "rules_jvm_external",
        remote = "https://github.com/bazelbuild/rules_jvm_external",
        commit = "0ee1b520f8210e69b3eea9c5f8f495091107aa5f",
    )