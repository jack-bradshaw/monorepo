# Tools

This directory contains tools and configurations that are required for ecosystem integration.

## Bazel

The scripts in this directory are executable wrappers that intercept Bazel commands and delegate
them to the checked in `bazelisk` binary. They exist to ensure that IDEs and other automated systems
use the hermetic toolchain, to provide a single entry point to Bazel that can be used by tools and
targets (for nested Bazel invocations), and to ensure the same version is always used.

### Usage

Users should ensure bazel is not already in their path then add this directory to their path.

Example (Unix-like systems):

```bash
export PATH=$PWD/tools:$PATH
bazel build //...
```

Exmaple (Windows):

```cmd
SET PATH=%CD%\tools;%PATH%
bazel build //...
```

Afterwards, Bazel can be invoked as usual (e.g. `bazel build //...`).

## Content Restrictions

This directory exists solely for ecosystem integration purposes. It should only contain tools that
must be placed here for broader ecosystem integration, and should not contain first party tools or
third party sources/binaries that can be located in their respective packages.
