# Monorepo

This repository is structured as a tree of packages. It contains all code and content personally
authored by Jack Bradshaw from 2022 onwards, with contributions from third parties in some packages.
The various interconnected tools and systems it provides are documented in the READMEs distributed
throughout the repository.

## Release

The repository itself is not released to third party package managers; however, various subpackages
are available from an assortment of package managers, with release details provided in the READMEs
distributed throughout the repository. Furthermore, the repository itself is unversioned, and
individual packages are versioned independently.

## Legal

The contents of the repository are provided under the terms of various licenses. The LICENSE files
distributed throughout the repository apply recursively down the file tree, meaning each license
file applies to the content of the containing directory and the contents of its subdirectories;
however, only one license can apply to any given directory, and license files further down the
directory tree override and anul license files further up. For example, given a LICENSE in
`/example/foo/LICENSE` and a LICENSE in `/example/foo/bar/LICENSE`, all files in and under `foo` are
subject to the `foo` license, except for the recursive contents of `bar`, which are subject to the
`bar` LICENSE.

## Contents

The contents of the repository are divided into [first_party](/first_party), which contains all
materials originating from this repository, and [third_party](/third_party), which contains
everything else. First party contains the following top-level packages:

- [autofactory](/first_party/autofactory): AutoFactory Bazel integration.
- [build_tests](/first_party/build_tests): Build system tests.
- [concurrency](/first_party/concurrency): Kotlin concurrency infrastructure.
- [coroutines](/first_party/coroutines): Kotlin coroutine infrastructure.
- [formatting](/first_party/formatting): Automated formatting tools.
- [klu](/first_party/klu): General helpers and utilities for Kotlin.
- [kmonkey](/first_party/kmonkey): Kotlin support for the JMonkey video game engine.
- [universal](/first_party/universal): Universal models that apply across all domains.
- [otter](/first_party/otter): An experimental and incomplete video game engine based on JMonkey.
- [presubmit](/first_party/presubmit): Continuous integration infrastructure.
- [rules_hugo](/first_party/rules_hugo): Build rules for Hugo.
- [sasync](/first_party/sasync): Tools for working with Java STDIO in Kotlin.
- [tofu](/first_party/tofu): Tools for working with open-tofu.
- [contributing](/first_party/contributing): Contribution standards, practices, and guidelines.

Some files are contained in the root directory for integration with the build system and other
tooling.

## Build System

This repository uses [Bazel](https://bazel.build) extensively, and the
[Bazelisk](https://github.com/bazelbuild/bazelisk) binary is checked into third party for ease of
use. View the [instructions](/tools) to get setup.

## Continuous Integration

Submission to the main branch is guarded by the [presubmit](/first_party/presubmit) continuous
integration system. It runs automatically on GitHub and must pass before submission can proceed.
Furthermore, trunk-based development is exclusively used, meaning rebasing onto main is the only
acceptable method of submission, and all releases occur from main. GitFlow and other branch-based
models are not used.

## External Dependencies

Package managers are used to access external packages (details in
[external dependencies](/external_dependencies.md)).

## Licensing Agreement

There is no CLA.

## Contributions

The READMEs throughout the repository specify which packages accept third-party contributions. All
contributions must conform to the
[repository wide contribution directives](/first_party/contributing), and some packages provide
package-scoped contribution directives.
