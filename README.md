# Monorepo

This repository contains all public code written by Jack Bradshaw from 2022 onwards. It is a
monorepo which uses [Bazel](https://bazel.build) as the build tool. Everything is covered by the
[LICENSE](LICENSE), except the contents of [third_party](third_party) which are explicitly exempt.

## Structure

All code is divided into two root directories, [first_party](first_party) and
[third_party](third_party). All code originating from this repository lives in the former, and all
code belonging to another repository, person, or organisation belongs in the latter. This maintains
a clear and unambiguous division for licensing and maintenance purposes. A few files exist outside
these directories for core repository and build setup.

First party contains the following packages:

- [autofactory](/first_party/autofactory): AutoFactory Bazel integration.
- [build_tests](/first_party/build_tests): Build system tests.
- [concurrency](/first_party/concurrency): Kotlin concurrency infrastructure.
- [coroutines](/first_party/coroutines): Kotlin coroutine infrastructure.
- [formatting](/first_party/formatting): Automated formatting tools.
- [klu](/first_party/klu): General helpers and utilities for Kotlin.
- [kmonkey](/first_party/kmonkey): Kotlin support for the JMonkey video game engine.
- [model](/first_party/model): Universal models that apply across all domains.
- [otter](/first_party/otter): An experimental and incomplete video game engine based on JMonkey.
- [presubmit](/first_party/presubmit): Continuous integration infrastructure.
- [sasync](/first_party/sasync): Tools for working with Java STDIO in Kotlin.
- [tofu](/first_party/tofu): Tools for working with open-tofu.
- [standards](/first_party/standards): Standards for contribution to this repository.
- [writing](/first_party/writing): Creative and technical writing.

## Building and Testing

Bazel is used as the build tool across the repository.

- All code is built by running `bazel build //...`.
- All code is tested by running `bazel test //...`.

Granular package documentation contains more more focused build and test instructions.

## Presubmit

Submissions to the main branch are automatically checked by a presubmit system which builds all
code, runs all tests, and ensures all code is formatted correctly. On Github it automatically runs
on each pull request, and it must pass before code can be submitted. Exceptions are granted only
when the presubmit system itself is broken.

Presubmit is run locally with `source first_party/presubmit/presubmit.sh; run_presubmit`.

WARNING: Presubmit may modify the working directory during execution. Commit or stash all changes
before running to avoid lost work.

## Formatting

Auto-formatting is available for all supported languages and file types with a single command.

- All files are formatted by running `bazel run //first_party/formatting:autoformat`.
- Specific files are formatted by running `bazel run //first_party/formatting:autoformat -- $paths`.

All files must be in their formatted state before submission, which is enforced by presubmit.

## Versioning

The repository is versioned by the current date, with the version defined in the
[MODULE.bazel](MODULE.bazel) file. Since the repository contains multiple projects, the version is
updated whenever any project is released in any way.

## Releasing

Granular packages contain release information.

## Package Managers

Package managers are used to manage external dependencies without building them from source. Various
package managers and indexes are used across the repo, including Maven, NPM, and PyPI. They all
follow a general pattern for integration and use, with minor differences in the exact details.

### General

Each package managers generally has:

- A registry where dependencies are declared once for the entire repository.
- A lock file where dependencies are secured against supply chain attacks.
- A syntax for referencing dependencies across the entire repository.

To use a new dependency:

1. Declare it in the registry.
2. Regenerate the lock file.
3. Reference it in build targets.

To remove an existing dependency:

1. Delete references in build targets.
2. Remove the declaration from the registry.
3. Regenerate the lock file.

To update an existing dependency:

1. Update the version in the registry.
2. Regenerate the lock file.

The exact details for each package manger are provided below.

### Maven

To manage Maven dependencies:

- Declare in the JVM section of [MODULES.bazel](MODULES.bazel).
- Lock by running `REPIN=1 bazel run @com_jackbradshaw_maven//:pin`.
- Reference as `@com_jackbradshaw_maven//:$depGroupId_depArtefactId`, with all non-alphanumeric
  characters in the identifiers replaced with `_`.

Example: [Google Flogger](https://mvnrepository.com/artifact/com.google.flogger/flogger) is
registered as `com.google.flogger:flogger:1.0.0` and referenced as
`@com_jackbradshaw_maven//:com_google_flogger_flogger`.

### NPM

To manage NPM dependencies:

- Declare in [package.json](package.json).
- Lock by running `bazel run -- @pnpm//:pnpm --dir $(pwd) install --lockfile-only` (pwd must be the
  repo root).
- Reference as `//:node_modules/$packageName`.

Example: [babel-plugin-minify-infinity](https://www.npmjs.com/package/babel-plugin-minify-infinity)
is registered as `"babel-plugin-minify-infinity": "0.4.3"` and referenced as
`//:node_modules/babel-plugin-minify-infinity`.

### PIP

To manage PIP dependencies:

- Declare in [pip_requirements.in](pip_requirements.in).
- Lock by running `bazel run :requirements.update &> /dev/null`.
- Reference as `@pypi//$packageName`.

Example: [mdformat](https://pypi.org/project/mdformat/0.7.22/) is registered as `mdformat==0.7.22`
and referenced as `@pypi//mdformat`.

### Crate

To manage crate dependencies:

- Declare in the Rust section of [MODULES.bazel](MODULES.bazel).
- There is no locking.
- Reference as `@crate//:$packageName`.

Example: [serde](https://crates.io/crates/serde) is registered as:

```starlark
crate.spec(
    package = "serde",
    version = "1.0",
)
```

and referenced as `@crate//:serde`

## Vendoring

All required external dependencies are vendored for hermetic security and reliability, with the
exception of all that cannot be uploaded to GitHub due to the 100MB file size limit. Whenever the
dependencies change (including Bazel dependencies and package-manager dependencies), revendor by
running `bazel vendor //...`. Any oversized dependencies are ignored via [.gitignore](.gitignore),
and large files can be found by running `find third_party/bazel_vendor -type f -size +100M`.

## Documentation

All documentation is stored in the repository itself, including design docs, one-pagers, guides, and
all other ancillatory paperwork. This ensures the codebase itself is the authorative source of truth
for all related materials, and ensures code does not become separted from related documentation.

## Branching Strategy

This repository exclusively uses trunk-based development.

## Large Files

All files must be stored directly in the repository and not in supplementary storage, meaning Git
LFS, Git Annex, and all other such tools are banned.

## Licensing Agreement

There is currently no CLA.

## Standards

Code quality and user experience are prioritised.
