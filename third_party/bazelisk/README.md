# Bazelisk

This directory contains [Bazelisk](https://github.com/bazelbuild/bazelisk) binaries and setup
scripts for various platforms (Linux, macOS, Windows).

## Source

The [binaries](/third_party/bazelisk/bin) and the [LICENSE](/third_party/bazelisk/LICENSE) file were
downloaded from [GitHub](https://github.com/bazelbuild/bazelisk/releases/tag/v1.22.1). The scripts
were added to integrate them into the developer environment.

## Usage

Direct usage depends on which platform you are building on, and nested Bazel builds are an option.

### Unix

Source the setup script:

```bash
source third_party/bazelisk/setup.sh
```

Then run `bazel <command>` as usual.

### Windows

Call the setup script:

```bat
third_party\bazelisk\setup.bat
```

Then run `bazel <command>` as usual.

### Nested Builds

Bazel can be invoked from Bazel for a nested Bazel build.

In a shell script:

```bash
cd /path/to/repo
source third_party/bazelisk/setup.sh
bazel build //path/to/foo
```

In a BUILD file:

```starlark
sh_binary(
    name = "release",
    srcs = ["release.sh"],
    data = ["//third_party/bazelisk:unix_setup"],
)
```

Then run `bazel run :release`.

Note:

- Nested Bazel builds are not supported on Windows.
- A production grade script to run Bazel from Bazel is more complex than the above example suggests.
  It is provided as a proof of concept.

## Rationale

Using Bazelisk provides several benefits:

- Zero Dependencies: Developers do not need to install Bazel or Bazelisk manually.
- Reproducibility: Everyone uses the exact same version of Bazel (improves reliability and
  reproducibility).
- Hermeticity: The build tool is part of the repository state, ensuring self-contained builds and
  capturing tool upgrades in history.
- Ease of Update: Upgrading Bazel is as simple as updating the [.bazelversion](/.bazelversion)
  file.