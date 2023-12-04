# Monorepo

*One Repo to rule them all, One Repo to find them, One Repo to bring them all and in the darkness
bind them.*

## Contents

This codebase is structured as a single monolithic repository. It contains:

- All code written by [Jack Bradshaw](https://jackbradshaw.io) from 2022 onwards.
- Third party artifacts (isolated to [third_party](third_party)).

View the README files distributed throughout the repository for package-specific documentation.

## Build System

This repository uses [Bazel](https://bazel.build) as the build system for all code.

To build everything:

```
bazel build //...
```

To test everything:

```
bazel test //...
```

View the [Bazel docs](https://bazel.build) for more information.

## Presubmit

Presubmit is a set of automated checks that must pass before code can be merged into main.

To run presubmit:

```
bash presubmit/presubmit.sh
```

Presubmit can modify the working directory, so committing/stashing all changes before running it is
recommended to avoid lost work.

## LICENSE

The contents of this repository are subject to the terms of the [LICENSE](LICENSE), except for
files in [third_party](third_party) which are subject to the licensing terms supplied by third
parties. View the [third party README](/third_party/README.md) for more details.
