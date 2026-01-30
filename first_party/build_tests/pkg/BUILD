load("@rules_pkg//pkg:tar.bzl", "pkg_tar")
load("@rules_pkg//pkg:zip.bzl", "pkg_zip")

package(default_visibility = ["//visibility:private"])

pkg_tar(
    name = "pkg_tar_must_build",
    srcs = ["stub.txt"],
)

pkg_zip(
    name = "pkg_zip_must_build",
    srcs = ["stub.txt"],
)
