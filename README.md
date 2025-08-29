# Monorepo

_One Repo to rule them all, One Repo to find them, One Repo to bring them all and in the darkness
bind them._

This codebase contains all public code written by Jack Bradshaw from 2022 onwards. It is structured
as a monorepo and uses [Bazel](https://bazel.build) as the build tool. Everything is covered by the
[LICENSE](LICENSE), except the contents of [3P](third_party) which are explicitly exempt.

## Setup

After cloning the repository you must complete the following steps:

1. Install the JDK (version specifics in [bazelrc](.bazelrc)).
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
prod/test. View the [1P README](first_party/README.md) for details.

### 3P

All sources belonging to another person or organisation are contained in
[third_party](third_party) without exception. View the [3P README](third_party/README.md)
for details.

### Extraneous

A few extraneous files exist outside 1P and 3P to support core workflows (build,
VCS, formatting, etc). A few worth mentioning:

- [everything.bazelproject](everything.bazelproject): Used to develop in Intellij.
- [autofactory](autofactory): Integrates Google autofactory into Bazel.
- [presubmit](presubmit): Automated checks that must pass before code submission.
- [formatting](formatting): Automated formatting tools.

## Building/Testing

Build all code by running `bazel build //...`.

Test all code by running `bazel test //...`.

View granular package documentation for more more specific build/test instructions.

## Presubmit

Submissions to main are automatically verified by a presubmit system which build all code and runs
all tests. On Github it runs automatically when code is pushed and when PRs are opened/updated, and
under normal circumstances it absolutely must pass before any code can be merged. Exceptions are
granted only when the presubmit system itself is broken. Presubmit is started by running
`bashpresubmit/presubmit.sh`.

WARNING: Presubmit may modify the working directory during execution, so commit/stash all changes
before running to avoid lost work.

## Versioning

The repository is versioned by the current date, with the version defined in the
[MODULE.bazel](MODULE.bazel) file, and since the repository contains multiple projects, the version
is updated whenever any project is released in any way.

TODO(jack-bradshaw): Create an automatic version update system.

## Releasing

View the granular packages for further release information.

TODO(jack-bradshaw) create a repository wide release system.

## Package Managers

Package managers make external deps available without building them from source (presently
infeasible due to maintenance overhead). Various package managers and indexes are used in this repo,
including Maven, NPM, and PyPI. All follow a general pattern for integration and use, with minor
differences in the exact details.

### Common

Each package manager has:

- A registry where deps are declared.
- A lock file where deps are secured.
- A way to reference deps in targets.

To add a new dep:

1. Declare it in the registry.
2. Regenerate the lock file.
3. Reference it in build targets.

To remove an existing dep:

1. Delete build target usages.
2. Remove the declaration in the registry.
3. Regenerate the lock file.

To update an existing dep:

1. Update the version in the registry.
2. Regenerate the lock file.

The exact details for each package manger are provided below.

### Maven

To manage Maven deps:

- Declare in the JVM section of [MODULES.bazel](MODULES.bazel).
- Lock by running `REPIN=1 bazel run @com_jackbradshaw_maven//:pin`.
- Reference as `@com_jackbradshaw_maven//:$depGroupId_depArtefactId`, with all non-alphanumeric
characters in the identifiers replaced with `_`.

Example: [Google Flogger](https://mvnrepository.com/artifact/com.google.flogger/flogger) is
registered as `com.google.flogger:flogger:1.0.0` and referenced as
`@com_jackbradshaw_maven//:com_google_flogger_flogger`.

### NPM

To manage NPM deps:

- Declare in [package.json](package.json).
- Lock by running `bazel run -- @pnpm//:pnpm --dir $(pwd) install --lockfile-only` (pwd must be the
repo root).
- Reference as `//:node_modules/$packageName`.

Example: [babel-plugin-minify-infinity](https://www.npmjs.com/package/babel-plugin-minify-infinity)
is registered as `"babel-plugin-minify-infinity": "0.4.3"` and referenced as
`//:node_modules/babel-plugin-minify-infinity`.

### PIP

To manage PIP deps:

- Declare in [pip_requirements.in](pip_requirements.in).
- Lock by running `bazel run :requirements.update`.
- Reference as `@pypi//$packageName`.

Example: [mdformat](https://pypi.org/project/mdformat/0.7.22/) is registered as `mdformat==0.7.22`
and referenced as `@pypi//mdformat`.

## Vendoring

All external dependencies are vendored for hermetic security and reliability, with the exception of
any dep which cannot be uploaded to GitHub due to the 100MB file size limit. Whenever you change the
deps (including Bazel deps and package-manager deps), revendor by running `bazel vendor //...`. Any
oversized deps are ignored via [.gitignore](.gitignore).

TODO(jack-bradshaw): Migrate off GitHub and delete the large file limit.

## Documentation

All documentation is stored in the repository itself, including design docs, one-pagers, guides,
and all other ancillatory paperwork. This ensures the codebase itself is the authorative source of
truth for all related materials, and ensures code does not become separted from related
documentation.

## Branching Strategy

This repository exclusively uses [trunk-based development](https://trunkbaseddevelopment.com) to the degree that all alternative forms of branch management are banned. This is essential for monorepo development and generally means:

1. No collaborative branches: You may create private branches off main for your own work, but do not collaborate on them with others.
1. Rebase don't merge: You should rebase onto HEAD locally to get updates from others, and when your code is ready for submission, rebase it onto HEAD (with a PR) instead of merging.
1. Make small, atomic commits: Each commit must change only the files required for that change and nothing more. If multiple commits serve a shared purpose, link them together by tagging an issue in the commit description.
1. Keep HEAD green: PRs may only be submitted when CI passes. This is enforced automatically.
1. Integrate frequently: Submit your changes as soon as they are ready, and locally rebase your branches onto HEAD multiple times a day to reduce the severity of merge conflicts.
1. Use feature flags: Incomplete features at HEAD are unavoidable with trunk-based development, so use feature flagging to guard production behavior.
1. One commit per PR: Each PR must have exactly one commit to ensure code-review is focused, simplify CI, and create a 1:1 mapping between changes and review. This is enforced automatically.

Overall this approach creates a single, shared, linear history in the main branch and creates an unambiguous shared HEAD.

## Large Files

All files must be stored directly in the repository and not in supplementary storage, meaning Git
LFS, Git Annex, and all other such tools are banned. So far this has only results in two large
vendored deps being ignored, and migration to a self-hosted Git repository is in progress to work
around GitHub's 100MB limit.

TODO(jack-bradshaw): Migrate off GitHub and delete the above comment.

## Code of Conduct

This repository does not have a code of conduct, but please do not behave in a way that warrants the
creation of one. Regulating human behavior rarely ends well, and nothing can be said that isn't
already covered by the principles of kindness and compassion. If you are unsure about an action or
inaction, ask before acting.

## Licensing Agreement

There is currently no CLA.

TODO(jack-bradshaw): Create a CLA.

## Priorities

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