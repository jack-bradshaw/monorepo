# Formatting

Tools for formatting source code.

## Release

Not released to any third party package managers.

## Tools

This package uses the following formatting tools:

- [Prettier](https://prettier.io/)
- [ktfmt](https://github.com/facebook/ktfmt)
- [buildifier](https://github.com/bazelbuild/buildtools/tree/master/buildifier)

The configurations are distributed throughout the package in various files/subpackages.

## Functionality

The `//first_party/formatting:autoformat` target formats all files in the repository. Execution can
be confined to a particular file/directory by passing it as an argument (e.g.
`bazel run //first_party/formatting:autoformat -- /first_party/concurrency` will format all
files/directories contained under `concurrency` recursively, but leave all other files unchanged).

## Issues

Issues relating to this package and its subpackages are tagged with `formatting`.

## Contributions

Third-party contributions are accepted.
