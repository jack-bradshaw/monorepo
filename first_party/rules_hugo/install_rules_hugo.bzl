load("@external_rules_hugo//hugo:rules.bzl", "hugo_repository")

# Implementation for the hugo module extension.
def _install_rules_hugo_impl(module_ctx):
    hugo_repository(
        name = "rules_hugo",
        version = "0.152.2",
    )

# Installs the ruleset in `rules_hugo`.
install_rules_hugo = module_extension(
    implementation = _install_rules_hugo_impl,
)
