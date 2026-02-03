# Tools

This directory contains tools and configurations that are required for ecosystem integration.

## Bazel

The scripts in this directory are executable wrappers that intercept Bazel commands and delegate
them to the checked in [bazelisk scripts](/third_party/bazelisk/bazelisk). They exist to ensure that
IDEs and other automated systems use the hermetic toolchain, to provide a single entry point for
invoking, and to ensure the same version is always used.

### Usage

Users should add this directory to their path and ensure the bazel binary is not in their path in
any other way.

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

Bazel can then be invoked as usual (e.g. `bazel build //...`).

## Content Restrictions

This directory exists solely for ecosystem integration and nested Bazel invocation. It may contain
other tools in the future is they must be defined here, but it should only contain tools that
absolutely must be placed here, and it should never contain first or third party tools that can be
located in the respective packages.
