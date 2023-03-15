# Monorepo

This repository contains all public code created by [Jack Bradshaw](https://jackbradshaw.io) from 2022 onwards.
The code is available for public and private use under the terms of the [MIT License](LICENSE). Please direct all
questions and concerns to [jack@jackbradshaw.io](mailto:jack@jackbradshaw.io) or file a bug in
the [issue tracker](https://github.com/jack-bradshaw/monorepo/issues).

## Contents

This repository is structured as a single monolithic codebase containing multiple libraries, applications, and
packages. The important locations are:

- [KLU](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/klu): General helpers and utilities for Kotlin.
- [KMonkey](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/kmonkey): Kotlin tools for the JMonkey engine.

Follow the links for detailed documentation including release information and tutorials.

## Building

This repository uses the Bazel build system for all code with a few exceptions.

To build everything run:

```
blaze build //...
```

To test everything run:

```
blaze test //...
```

## Presubmit

Any code submitted to the main branch must pass the presubmits. These checks
assess accuracy and correctness, and are integrated into the GitHub CI system.

To manually invoke presubmit run:

```
bash presubmit/mainfest.sh
```
