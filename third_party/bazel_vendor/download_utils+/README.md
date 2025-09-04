# `download_utils`

> A Bazel extension to download archives, files and packages for use within Bazel targets

## Getting Started

Add the following to `MODULE.bazel`:

```py
bazel_dep(name="download_utils", version="0.0.0")
```

Use the repository rules in `MODULE.bazel` to download artifacts:

```py
# Download an archive and unpack it
download_archive = use_repo_rule("@download_utils//download/archive:defs.bzl", "download_archive")
download_archive(
    name = "archive",
    urls = ["https://some.thing/archive.tar"],
)

# Download a single file and possibly make it executable
download_file = use_repo_rule("@download_utils//download/file:defs.bzl", "download_file")
download_file(
    name = "file",
    output = "executable",
    executable = True,
    urls = ["https://some.thing/executable-amd64-linux"],
)

# Download a Debian package, unpack it then unpack the `data.tar.{xz,zst}`
download_deb = use_repo_rule("@download_utils//download/deb:defs.bzl", "download_deb")
download_deb(
    name = "deb",
    integrity = "sha256-vMiq8kFBwoSrVEE+Tcs08RvaiNp6MsboWlXS7p1clO0=",
    urls = ["https://some.thing/test_1.0-1_all.deb"],
    commands = {
        "chmod": [
            "$(location @coreutils)",
            "chmod",
            "u+x",
            "some-script.sh",
        ],
    },
    links = {
        "etc/test/fixture.txt": "fixture.txt",
    },
    tools = [
        "@coreutils",
    ],
)
```

### Integrity

The [sub-resource integrity (SRI)][sri] is not required for secure URLs. For non-secure (`http`, `ftp`) it is. It is
recommended to _always_ add the `integrity` to allow reproducible builds and sharing of downloads. When the integrity is
omitted and the rule is resolved, the correct SRI is output to the terminal. The easiest way to download the artifact is
to query the targets within the repository: `bazelisk query @archive//...`.

### Patches

The rules accept `patches` to modify the content after download. Examples are provided in the [end to end][e2e] tests.

Patches are applied _after_ the `BUILD.bazel` file is written so can be used to customise the targets exposed to the
Bazel build. _Usually_, however, a custom `BUILD.bazel` file is provided to the `build` argument.

### Commands

Hermetic commands can be ran against the unpacked repository. Hermetic binaries can be provided via the `tools` argument and be used with `$(location <label>)` in the
`commands`. The [end to end][e2e] tests provided examples of this.

### Sources

By default, the `BUILD.bazel` file exports all files downloaded. This can be controlled with the `srcs` attribute to
customise the files that are exposed to the Bazel build.

### Links

Symlinks/hardlinks can be created in the download repositories by providing the `links` map of target files to link names.

[sri]: https://developer.mozilla.org/en-US/docs/Web/Security/Subresource_Integrity
[commands]: lib/commands.bzl
[e2e]: e2e/MODULE.bazel

## Hermeticity

This ruleset is entirely hermetic and does not require anything from the system.

The `commands` supports `$(location <label>)` for running hermetic commands on the downloaded data.

## Release Registry

The project publishes the relevant files to GitLab releases for use when a version has not been added to the upstream [BCR][bcr].

This is often the case for pre-release versions.

Add the following to `.bazelrc`:

```
# `bzlmod` pre-release registries
common --registry https://bcr.bazel.build
common --registry=https://gitlab.arm.com/bazel/download_utils/-/releases/v1.0.0-alpha.1/downloads
```

Then a GitLab release version can be used in `bazel_dep`.

[bcr]: https://registry.bazel.build/
