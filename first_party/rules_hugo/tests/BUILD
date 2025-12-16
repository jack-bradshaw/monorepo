load("@rules_hugo//hugo:rules.bzl", "hugo_site")

hugo_site(
    name = "hugo_site_must_build",
    config = "hugo.toml",
    content = glob(["content/**"]),
    layouts = glob(["layouts/**"]),
)
