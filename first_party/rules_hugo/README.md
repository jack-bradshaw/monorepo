# Rules Hugo

Patches the external [rules_hugo](https://github.com/bazelbuild/rules_hugo) ruleset for
compatibility with Bzlmod and the latest versions of Hugo.

## Release

Not released to any third party package managers.

## Usage

The external [rules_hugo](https://github.com/bazelbuild/rules_hugo) ruleset does not support Bzlmod;
therefore, loading it requires a two stage process: loading the ruleset itself into an external
repository to get access to its files, then using its repository rules to install the hugo tool.

Example MODULE.bazel:

```starlark
rules_hugo = use_extension("//first_party/rules_hugo:fetch_rules_hugo.bzl", "fetch_rules_hugo")
use_repo(rules_hugo, "rules_hugo")

hugo_executable = use_extension("//first_party/rules_hugo:fetch_hugo_executable.bzl", "fetch_hugo_executable")
use_repo(hugo_executable, "hugo")
```

Example BUILD:

```
load("@rules_hugl//hugo:rules.bzl", "hugo_site")

hugo_site(
    name = "site",
    config = "hugo.toml",
    content = glob(["content/**"]),
    data = glob(["data/**"]),
    layouts = glob(["layouts/**"]),
    static = glob(["static/**"]),
)
```

## Versions

Versions are hardcoded in [fetch_hugo_executable.bzl](fetch_hugo_executable.bzl) and
[fetch_rules_hugo.bzl](fetch_rules_hugo.bzl).

## Issues

Issues relating to this package and its subpackages are tagged with `rules_hugo`.

## Contributions

Contributions from third parties are accepted.
