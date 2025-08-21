# Monorepo

_One Repo to rule them all, One Repo to find them, One Repo to bring them all and in the darkness
bind them._

This codebase contains all code written by Jack Bradshaw from 2022 onwards. It is structured as a
monorepo and uses [Bazel](https://bazel.build) as the build tool. Everything is covered by the
[LICENSE](LICENSE) except the contents of [3P](third_party) which are explicitly exempt from the
license and are provided according to the terms set forth by their authors.

## Setup

After cloning the repository you must complete the following steps:

1. Install the JDK (version specifics in [bazelrc](.bazelrc)).
1. Install the Python SDK with C headers (version specifics in [MODULE.bazel](MODULE.bazel)).
1. Install the Android SDK (version specifics vary depending on which package is being built).
1. Copy [local bazelrc](local_bazelrc) to your home directory and configure it according to the
   instructions in the file.

Once these steps are complete you will be able to build and test code on Windows, macOS, and Linux.

## Structure

This codebase contains various projects with dependencies connecting them together, and it
generally follows a flat hierarchy with minimal nesting. There are three main locations: first
party (1P), third party (3P) and extraneous. All files and directories fit these three categories.

### 1P

All sources originating from this repository are contained in [first_party](first_paty). The
[merged language tree](https://www.jack-bradshaw.com/technical-docs/the-case-for-one-unified-language-branch)
model is used, meaning root-level branches are not used to divide code between languages or
prod/test.

1P contains the following top level projects:

1. [KLU](java/klu) (Kotlin Lightweight Upgrade): General helpers and utilities for Kotlin.
1. [KMonkey](java/kmonkey): Kotlin tools for the JMonkey game engine.
1. [Otter](java/otter): An experimental (and incomplete) game engine.

README files are distributed throughout the repository. View them for more granular information.

### 3P

All sources belonging to another person or organisation are contained in
[third_party](third_party) without exception. View the [third party README](third_party/README.md)
for details.

### Extraneous

A few extraneous files exist outside 1P and 3P to support core workflows (build,
VCS, formatting, etc).

The following files and directories enable core build workflows:

- [MODULE.bazel](MODULE.bazel) and [BUILD](BUILD): Core Bazel configuration.
- [.bazelrc](.bazelrc) and [local_bazelrc](local_bazelrc): Runtime Bazel configuration.
- [.bazelversion](.bazelversion): Bazel version configuration (set to last know to pass presubmit).
- [everything.bazelproject](everything.bazelproject): Used to access all 1P code via Intellij.
- [lock_maven_com_jackbradshaw.json](lock_maven_com_jackbradshaw.json),
[lock_maven_grpc_kotlin.json](lock_maven_grpc_kotlin.json),
[lock_pip_requirements.txt](lock_pip_requirements.txt), and
[MODULE.bazel.lock](MODULE.bazel.lock): Lock files for dependencies (prevents supply chain attacks).
- [WORKSPACE.bazel](WORKSPACE.bazel): An empty file, required for now, to be deleted in Bazel 9.
- [autofactory](autofactory): Integrates Google autofactory with Bazel.

The following files and directories enable other core workflows:

- [presubmit](presubmit): Automated checks that must pass before code submission.
- [formatting](formatting): Automated formatting tools.
- [.github](.github): Integration with GitHub CI and other GitHub services.
- [.gitattributes](.gitattributes) and [.gitignore](.gitignore): Standard git configuration.

The following files provide repository-level metadata:

- [LICENSE](LICENSE): The open-source license applied to all files in this repository (excludes 3P).

## Building/Testing

Build all code by running `bazel build //...`.

Test all code by running `bazel test //...`.

View granular package documentation for more more specific build/test instructions.

## Versioning

The repository is versioned by the current date, with the version defined in the
[MODULE.bazel](MODULE.bazel) file, and since the repository contains multiple projects, the version
is updated whenever any project is released in any way.

TODO(jack-bradshaw): Create an automatic version update system.

## Releasing

View the granular packages for further release information.

TODO(jack-bradshaw) create a repository wide release system.

## Contributing

Please read and follow these guidelines if you wish to make contributions.

### Priorities

Prioritise the following:

1. Code quality.
2. User experience.

Priorisiting code quality generally means providing documentation, optimising for maintainability
and readability, always writing tests, taking time to refactor when code has grown beyond its
original scope, following language/platform/framework conventions, and adhering to engineering
best practices. If you are unsure about a change, simply consider the experience of the
maintainer after you have moved on, and if you wouldn't want to be that person, improve the code
now.

TODO(jack-bradshaw): Explain what prioritising the user experience means.

### Presubmit

Submissions to main are automatically verified by a presubmit system which build all code and runs
all tests. On Github it runs automatically when code is pushed and when PRs are opened/updated, and
under normal circumstances it absolutely must pass before any code can be merged. Exceptions are
granted only when the presubmit system itself is broken. Presubmit is started by running
`bashpresubmit/presubmit.sh`.

WARNING: Presubmit may modify the working directory during execution, so commit/stash all changes
before running to avoid lost work.

### Documentation

Store all important documentation in the repository itself to prevent code becoming detached from
important documentation. This applies to design docs, one-pagers, and other ancillatory paperwork.
Use markdown where possible.

### Vendoring

All external dependencies are vendored for hermetic security and reliability, with the exception of
any dep which cannot be uploaded to GitHub due to the 100MB file size limit. Whenever you change the
deps (including Bazel deps and package-manager deps), revendor by:

1. Running `bazel vendor //...`
2. Ignoring any dependencies containing files exceeding 100MB with [.gitignore](.gitignore).

TODO(jack-bradshaw): Migrate off GitHub and delete the large file limit.

### Branching Strategy

This repository exclusively uses [trunk-based development](https://trunkbaseddevelopment.com) to the degree that all alternative forms of branch management are banned. This is essential for monorepo development and generally means:

1. No collaborative branches: You may create private branches off main for your own work, but do not collaborate on them with others.
1. Rebase don't merge: You should rebase onto HEAD locally to get updates from others, and when your code is ready for submission, rebase it onto HEAD (with a PR) instead of merging.
1. Make small, atomic commits: Each commit must change only the files required for that change and nothing more. If multiple commits serve a shared purpose, link them together by tagging an issue in the commit description.
1. Keep HEAD green: PRs may only be submitted when CI passes. This is enforced automatically.
1. Integrate frequently: Submit your changes as soon as they are ready, and locally rebase your branches onto HEAD multiple times a day to reduce the severity of merge conflicts.
1. Use feature flags: Incomplete features at HEAD are unavoidable with trunk-based development, so use feature flagging to guard production behavior.
1. One commit per PR: Each PR must have exactly one commit to ensure code-review is focused, simplify CI, and create a 1:1 mapping between changes and review. This is enforced automatically.

Overall this approach creates a single, shared, linear history in the main branch and creates an unambiguous shared HEAD.

### Large Files

All files must be stored directly in the repository and not in supplementary storage, meaning Git
LFS, Git Annex, and all other such tools are banned. So far this has only results in two large
vendored deps being ignored, and migration to a self-hosted Git repository is in progress to work
around GitHub's 100MB limit.

TODO(jack-bradshaw): Migrate off GitHub and delete the above comment.

### Code of Conduct

This repository does not have a code of conduct, but please do not behave in a way that warrants the
creation of one. Regulating human behavior rarely ends well, and nothing can be said that isn't
already covered by the principles of kindness and compassion. If you are unsure about an action or
inaction, ask before acting.

### Licensing Agreement

There is currently no CLA.

TODO(jack-bradshaw): Create a CLA.
