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
bazel run //first_party/formatting:autoformat
```

To format a particularly file or all files in a directory (recursively):

```bash
bazel run //first_party/formatting:autoformat -- /path/to/file/or/directory
```

## Limitations

Upstream errors in the HTML formatter prevent it from handling HTML go-template files correctly. The
HTML formatter is disabled on all HTML go-template files via [.prettierignore](.prettierignore).

## Future Work

Prettier matches ignore patterns in `.prettierignore` files relative to the enclosing directory of
the file, which presents two opportunities for future work:

1. Ignore files cannot be placed in this package without arbitrary upward path references which is
   error prone.
1. Ignore files cannot be resolved from runfiles because all paths would resolve to locations in
   runfiles (not the actual repository).

A proposed future enhancement involves a synthetic file generation mechanism with:

1.  Distributed Ignore Configurations. Subpackages define standalone `.prettierignore` fragments.
1.  Runtime Synthesis. The runner script (`prettier_bin.sh`) reads these fragments from runfiles,
    stitches them together, and temporarily creates a `.prettierignore` file at the physical
    repository root while running (then deletes it post-completion).

This approach would enable package-scoped ignore logic while satisfying Prettier's expectations of a
root-level resolution base.

## Issues

Issues relating to this package and its subpackages are tagged with `formatting`.

## Contributions

Third-party contributions are accepted.
