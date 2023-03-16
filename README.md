# Monorepo

*One Repo to rule them all, One Repo to find them, One Repo to bring them all and in the darkness bind them.*

## Contents

This repository contains all public code written by [Jack Bradshaw](https://jackbradshaw.io) after 2021. It's structured
as a single monolithic codebase containing multiple packages, libraries, and applications.

The notable locations are:

- [KLU](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/klu): General helpers and utilities for
  Kotlin.
- [KMonkey](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/kmonkey): Kotlin tools for the
  JMonkey engine.

Follow the links above for package-specific documentation including release information and tutorials.

## Building

This repository uses [Bazel](https://bazel.build) as the primary build system for all production code.

To build everything run:

```
bazel build //...
```

To test everything run:

```
bazel test //...
```

## Presubmit

Presubmit is a set of automated tests that run across the codebase. All code must pass presubmit before being submitted
to the main branch. The GitHub CI system will automatically run presubmit whenever PR is opened on the main branch

To manually start presubmit run:

```
bash presubmit/mainfest.sh
```

Presubmit may modify the working directory so any unstaged files should be staged first.

## LICENSE

The contents of this repository and all derivative artifacts are owned by Jack Bradshaw and provided under the terms of
the [MIT License](LICENSE), with all exceptions listed in the [third party manifest](THIRD_PARTY).