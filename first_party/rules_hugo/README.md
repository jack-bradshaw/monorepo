# Rules Hugo

Patches the external [rules_hugo](https://github.com/bazelbuild/rules_hugo) ruleset for
compatibility with Bzlmod and the latest versions of Hugo.

## Release

Not released to any third party package managers.

## Usage

The external [rules_hugo](https://github.com/bazelbuild/rules_hugo) ruleset does not support Bzlmod;
therefore, loading it requires a two stage process: loading the ruleset itself into an external
repository to get access to its files, then using its repository rules to install the toolchain. For
example:

```starlark
fetch_rules_hugo = use_extension("//first_party/rules_hugo:fetch_rules_hugo.bzl", "fetch_rules_hugo")
use_repo(fetch_rules_hugo, "external_rules_hugo")

install_rules_hugo = use_extension("//first_party/rules_hugo:install_rules_hugo.bzl", "install_rules_hugo")
use_repo(install_rules_hugo, "rules_hugo")
```

Version information is hardcoded in [install_rules_hugo.bzl](install_rules_hugo.bzl) and
[fetch_rules_hugo.bzl](fetch_rules_hugo.bzl).

## Issues

Issues relating to this package and its subpackages are tagged with `rules_hugo`.

## Contributions

Contributions from third parties are accepted.
