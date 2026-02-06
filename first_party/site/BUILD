load("@rules_hugo//hugo:rules.bzl", "hugo_serve", "hugo_site")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("@rules_pkg//pkg:tar.bzl", "pkg_tar")
load("//first_party/bash_runfiles:shell.bzl", "sh_binary_with_runfiles")
load(":publicity.bzl", "PUBLICITY")

hugo_site(
    name = "site",
    config = "hugo.toml",
    content = glob(["content/**"]),
    data = glob(["data/**"]),
    layouts = glob(["layouts/**"]),
    quiet = False,
    static = glob(["static/**"]),
    visibility = PUBLICITY,
)

hugo_serve(
    name = "local",
    dep = [":site"],
    quiet = False,
    tags = ["no-sandbox"],
    visibility = PUBLICITY,
)

pkg_tar(
    name = "packaged",
    srcs = [":site"],
    visibility = PUBLICITY,
)

sh_binary_with_runfiles(
    name = "release",
    src = "release.sh",
    data = [
        "firebase.json",
        ":packaged",
    ],
    visibility = PUBLICITY,
)
