# `rules_diff`

> A Bazel ruleset for creating and testing differences between files and directories.

## Getting Started

Add the following to `MODULE.bazel`:

```py
bazel_dep(name="rules_diff", version="0.0.0")
```

Use the differencing rules:

```py
load("@rules_diff//diff/file/test:defs.bzl", "diff_file_test")

diff_file_test(
    name = "test",
    size = "small",
    a = ":some-file.txt",
    b = ":some-file.txt",
)
```

## Hermeticity

The rules use a hermetic `diff` provided by the [`ape`][ape] module.

Rules have Batch implementations so do not require Bash on Windows.

[ape]: https://registry.bazel.build/modules/ape

## Release Registry

The project publishes the relevant files to GitLab releases for use when a version has not been added to the upstream [BCR][bcr].

This is often the case for pre-release versions.

Add the following to `.bazelrc`:

```
# `bzlmod` pre-release registries
common --registry https://bcr.bazel.build
common --registry=https://gitlab.arm.com/bazel/rules_diff/-/releases/v1.0.0-alpha.1/downloads
```

Then a GitLab release version can be used in `bazel_dep`.

[bcr]: https://registry.bazel.build/
