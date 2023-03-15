# Monorepo

*One Repo to rule them all, One Repo to find them, One Repo to bring them all and in the darkness bind them.*

This repository contains all public code written by [Jack Bradshaw](https://jackbradshaw.io) after 2021.

## Contents

This repository is structured as a single monolithic codebase containing multiple packages, libraries and applications.

The notable locations are:

- [KLU](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/klu): General helpers and utilities for
  Kotlin.
- [KMonkey](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/kmonkey): Kotlin tools for the
  JMonkey engine.

Follow the links above for package-specific documentation.

## Building

This repository uses the [Bazel build system](https://bazel.build) for all production code. Only release scripting and
shell scripting is exempt.

To build everything run:

```
bazel build //...
```

To test everything run:

```
bazel test //...
```

The [Bazel docs](https://bazel.build/docs) contain further instructions.

## Presubmit

Presubmit is a series of automated tests which check the code for correctness and formatting. All code submitted to the
main branch must pass presubmit before being merged. The GitHub CI system will automatically run presubmits whenever
a main branch PR is made.

To manually invoke presubmit run:

```
bash presubmit/mainfest.sh
```

Any unstaged files should be staged first since presubmit may modify the working directory.

## LICENSE

The contents of this repository and all derivative artifacts are owned by Jack Bradshaw and provided under the terms of
the [MIT License](LICENSE), with the only exceptions being listed in the [third party manifest](THIRD_PARTY).