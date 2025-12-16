load("@rules_hugo//hugo:rules.bzl", "hugo_serve", "hugo_site")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("@rules_pkg//pkg:tar.bzl", "pkg_tar")

hugo_site(
    name = "site",
    config = "hugo.toml",
    content = glob(["content/**"]),
    data = glob(["data/**"]),
    layouts = glob(["layouts/**"]),
    quiet = False,
    static = glob(["static/**"]),
    visibility = ["//first_party/site:__subpackages__"],
)

hugo_serve(
    name = "local",
    dep = [":site"],
    quiet = False,
    tags = ["no-sandbox"],
)

pkg_tar(
    name = "packaged",
    srcs = [":site"],
)

sh_binary(
    name = "release",
    srcs = ["release.sh"],
    data = [
        "firebase.json",
        ":packaged",
    ],
    deps = ["@bazel_tools//tools/bash/runfiles"],
)
