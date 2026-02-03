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
bazel run //first_party/formatting:formatting
```

To format a particular file or all files in a directory (recursively):

```bash
bazel run //first_party/formatting:formatting -- /path/to/file/or/directory
```

To format only the files that have been modified (relative to main):

```bash
bazel run //first_party/formatting:formatting -- $(git diff --name-only main)
```

To format only the files in the present commit:

```bash
bazel run //first_party/formatting:formatting -- $(git diff-tree --no-commit-id --name-only -r HEAD)
```

To format only the files with uncommitted changes (index or working tree):

```bash
bazel run //first_party/formatting:formatting -- $(git diff --name-only)
```

## Limitations

The HTML formatter does not handle HTML go-template files properly due to an upstream error. It is
presently disabled on such files via [.prettierignore](.prettierignore).

## Future Work

The [ignore file](.prettierignore) must exist in the root directory because prettier interprets its
contents as relative file paths. Future work may involve synthesizing the ignore file at runtime
from various smaller ignore files distributed throughout the repository, so that individual packages
can define their rules without overloading the main ignore file. The repository is presently small
enough for this to be a non-issue.

## Issues

Issues relating to this package and its subpackages are tagged with `formatting`.

## Contributions

Third-party contributions are accepted.
