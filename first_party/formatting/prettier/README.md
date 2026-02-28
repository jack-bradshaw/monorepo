# Prettier Toolchain

Bazel integration for [Prettier](https://prettier.io/).

## Overview

The `binary` target wraps the prettier binary sourced from npm. It executes in the workspace root
directory, uses the [prettier.config.cjs](/first_party/formatting/prettier/prettier.config.cjs)
configuration file, and respects the root [.prettierignore](.prettierignore)file.

## Usage

The binary is intended for use in the [formatter](/first_party/formatting); however, it can be
invoked manually via `bazel run //first_party/formatting/prettier:binary -- <args>`. All arguments
are forwarded to the prettier binary.
