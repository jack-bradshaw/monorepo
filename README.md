# Monorepo

*One Repo to rule them all, One Repo to find them, One Repo to bring them all and in the darkness
bind them.*

## Contents

This codebase is structured as a single monolithic repository. It contains all code written by
[Jack Bradshaw](https://jackbradshaw.io) from 2022 onwards along with some third party code
(isolated to [third_party](third_party)).

The top-level projects are:

- [KLU](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/klu): General
  helpers and utilities for Kotlin.
- [KMonkey](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/kmonkey):
  Kotlin tools for the JMonkey engine.
- [Otter](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/otter): An
  experimental video game framework.

Granular project documentation is distributed throughout the repository.

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

Presubmit may modify the working directory, so committing/stashing all changes before running
presubmit is recommended to avoid lost work.

## LICENSE

The contents of this repository are subject to the terms of the [LICENSE](LICENSE), except for
files in [third_party](third_party) which are subject to the terms supplied by third parties.
View the [third party README](/third_party/README.md) for more details.
