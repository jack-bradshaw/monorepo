load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# The commit and checksum to use when fetching the external rules_hugo repository.
COMMIT = "294a8ec626a394011d35397108c930be631ab9fa"
SHA256 = "8df370f374dc72701b65b7c8a8add8ccb8423a845e973993fa9c68f8b516c9be"

# Implementation of fetch_rules_hugo.
def _fetch_rules_hugo_impl(module_ctx):
    # The patch is required because the repo uses an outdated URL when fetching the Hugo binary.
    http_archive(
        name = "rules_hugo",
        url = "https://github.com/stackb/rules_hugo/archive/%s.zip" % COMMIT,
        sha256 = SHA256,
        strip_prefix = "rules_hugo-%s" % COMMIT,
        patch_cmds = [
            "if [[ \"$(uname)\" == \"Darwin\" ]];\
            then find . -name hugo_repository.bzl\
            -exec sed -i '' 's/macOS-64bit/darwin-universal/g' {} +; fi",
        ],
    )

# Downloads rules_hugo from https://github.com/stackb/rules_hugo and stores it in an external repo
# called `rules_hugo`. Patches are applied to fix broken download URLs.
fetch_rules_hugo = module_extension(
    implementation = _fetch_rules_hugo_impl,
)
