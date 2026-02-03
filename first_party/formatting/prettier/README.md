# Prettier Toolchain

Bazel integration for [Prettier](https://prettier.io/).

## Overview

The `prettier_binary` target wraps the prettier binary (sourced from npm). It runs in the workspace
root directory, uses the [prettier.config.cjs](/first_party/formatting/prettier/prettier.config.cjs)
configuration file, and respects the root [.prettierignore](.prettierignore)file.

## Usage

The formatter is intended for use in the [autoformatter](/first_party/formatting); however, it can
be invoked manually via `bazel run //first_party/formatting/prettier:prettier_binary -- <args>`. All
passed in arguments are forwarded to the prettier binary, including the file/dir to format.
