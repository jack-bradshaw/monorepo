load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# Sourced from https://github.com/stackb/rules_hugo
RULES_HUGO_COMMIT = "294a8ec626a394011d35397108c930be631ab9fa"
RULES_HUGO_SHA256 = "8df370f374dc72701b65b7c8a8add8ccb8423a845e973993fa9c68f8b516c9be"

# Patch required since repo uses outdated URL for fetching Hugo binary.
def _rules_hugo_impl(module_ctx):
    http_archive(
        name = "rules_hugo",
        url = "https://github.com/stackb/rules_hugo/archive/%s.zip" % RULES_HUGO_COMMIT,
        sha256 = RULES_HUGO_SHA256,
        strip_prefix = "rules_hugo-%s" % RULES_HUGO_COMMIT,
        patch_cmds = [
            "find . -name hugo_repository.bzl -exec sed -i '' 's/macOS-64bit/darwin-universal/g' {} +",
        ],
    )

rules_hugo = module_extension(
    implementation = _rules_hugo_impl,
)
