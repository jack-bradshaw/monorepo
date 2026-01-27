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

## Usage

To format all files in the repository:

```bash
bazelisk run //first_party/formatting:autoformat
```

To format all files in a particular file/directory:

```bash
bazelisk run //first_party/formatting:autoformat -- /path/to/directory
```

## Issues

Issues relating to this package and its subpackages are tagged with `formatting`.

## Contributions

Third-party contributions are accepted.
