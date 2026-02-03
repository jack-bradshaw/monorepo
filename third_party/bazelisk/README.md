# Bazelisk

This directory contains the [Bazelisk](https://github.com/bazelbuild/bazelisk) binary for various
platforms (Linux, macOS, Windows).

## Source

The [binaries](/third_party/bazelisk/bin) and the [LICENSE](/third_party/bazelisk/LICENSE) file were
downloaded from [GitHub](https://github.com/bazelbuild/bazelisk/releases/tag/v1.22.1). The following
platforms and architectures are available:

- `linux-x86_64`
- `linux-arm64`
- `darwin-x86_64`
- `darwin-arm64`
- `windows-x86_64.exe`

The scripts in this package delegate to the binaries and originate from this repository.

## Usage

The scripts in this directly should not be used directly. Instead, use the
[wrapper scripts](/tools).