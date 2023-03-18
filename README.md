# Monorepo

*One Repo to rule them all, One Repo to find them, One Repo to bring them all and in the darkness bind them.*

## Contents

This repository contains all public code written by [Jack Bradshaw](https://jackbradshaw.io) from 2022 onwards. It's
structured as a single monolithic codebase containing multiple packages, libraries, and applications.

The top-level projects are:

- [KLU](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/klu): General helpers and utilities for
  Kotlin.
- [KMonkey](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/kmonkey): Kotlin tools for the
  JMonkey engine.
- [Otter](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/otter): An experimental video game
  framework.

Granular project documentation is distributed throughout the repository.

## Building

This repository uses [Bazel](https://bazel.build) as the build system for all production code.

To build everything:

```
bazel build //...
```

To test everything:

```
bazel test //...
```

Granular build instructions are distributed throughout the repository.

## Presubmit

Presubmit is a set of automated tests that apply across the codebase. The GitHub CI system will
automatically run presubmit whenever a PR is opened on the main branch and presubmit must pass
before the code can be submitted.

To manually run presubmit:

```
bash presubmit/mainfest.sh
```

Presubmit may modify the working directory so stage any changes before execution.

## LICENSE

The contents of this repository and all derivative artifacts are owned by Jack Bradshaw and provided under the terms of
the [MIT License](LICENSE), with all exceptions listed in the [third party manifest](THIRD_PARTY).
