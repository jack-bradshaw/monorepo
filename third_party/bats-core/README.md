# Bats-core

This package contains a minified clone of [bats-core](https://github.com/bats-core/bats-core) with Bazel build files added for integration with Bazel.

## Source

Cloned from https://github.com/bats-core/bats-core at commit
`2c17b48afd87a2237ea2794df0e621abc8cc5bce`

## Modifications

The original repository has been minified to include only the essential runtime files:

- `bin/`: The bats executable
- `lib/`: Core library files
- `libexec/`: Core execution files
- `LICENSE.md`: Original license
- `AUTHORS`: Original authors

All other directories and files were removed.

## Additions

A `BUILD` file was added to expose the `bats` binary as a Bazel `sh_binary` target.

## Bazel Integration

For the Bazel integration layer (including the `bats_test` macro), see `//first_party/rules_bats`.
