load("@rules_hugo//hugo:rules.bzl", "hugo_site", "hugo_theme", "hugo_serve")
load("@rules_pkg//pkg:tar.bzl", "pkg_tar")

hugo_theme(
    name = "theme",
    theme_name = "xmin",
    srcs = [
        "@hugo_theme_hugo_xmin//:files",
    ],
)

# Declare a site. Config file is required.
my_site_name = "experimental"

hugo_site(
    name = my_site_name,
    config = "config.toml",
    content = glob(["content/**"]),
    layouts = glob(["layouts/**"]),
    theme = ":theme",
)

# Local development server.
# Run with `bazel run :local_experimental` and access at `http://localhost:1313`.
hugo_serve(
    name = "thing",
    dep = [":%s" % my_site_name],
)

# Remote deployment as a tar.
pkg_tar(
    name = "%s_tar" % my_site_name,
    srcs = [":%s" % my_site_name],
)