# Monorepo

*One Repo to rule them all, One Repo to find them, One Repo to bring them all and in the darkness bind them.*

## Contents

This repository contains all public code written by [Jack Bradshaw](https://jackbradshaw.io) from 2022 onwards. It's structured
as a single monolithic codebase containing multiple packages, libraries, and applications.

The important locations are:

- [KLU](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/klu): General helpers and utilities for
  Kotlin.
- [KMonkey](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/kmonkey): Kotlin tools for the
  JMonkey engine.

Documentation is distributed throughout the repository to keep all documentation close to the relevant source.

## Building

This repository uses [Bazel](https://bazel.build) as the primary build system for all production code.

To build everything:

```
bazel build //...
```

To test everything:

```
bazel test //...
```

Granular build instructions are documentated throughout the repository.

## Presubmit

Presubmit is a set of automated tests that apply across the codebase. The GitHub CI system will
automatically run presubmit whenever a PR is opened on the main branch and presubmit must pass
before the code can be submitted.

To manually run presubmit:

```
bash presubmit/mainfest.sh
```

Presubmit may modify the working directory so any unstaged files should be staged first.

## LICENSE

The contents of this repository and all derivative artifacts are owned by Jack Bradshaw and provided under the terms of
the [MIT License](LICENSE), with all exceptions listed in the [third party manifest](THIRD_PARTY).
