# Bats-core

This package contains a minified clone of [bats-core](https://github.com/bats-core/bats-core) with Bazel build files added for integration with Bazel. The associated [rules_bazel](/first_party/bazel_rules) package provides the associated Bazel integration infrastructure.

## Source

Cloned into [src](/third_party/bats-core/src) from https://github.com/bats-core/bats-core at commit
`2c17b48afd87a2237ea2794df0e621abc8cc5bce` 

## Modifications

The downloaded files have been reduced to retain only the essential runtime files:

- `bin/`: The bats executable
- `lib/`: Core library files
- `libexec/`: Core execution files
- `LICENSE.md`: Original license
- `AUTHORS`: Original authors

All other directories and files were removed.
