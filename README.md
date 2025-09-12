# Monorepo

_One Repo to rule them all, One Repo to find them, One Repo to bring them all and in the darkness
bind them._

This codebase contains all public code written by Jack Bradshaw from 2022 onwards. It is structured
as a monorepo and uses [Bazel](https://bazel.build) as the build tool. Everything is covered by the
[LICENSE](LICENSE), except the contents of [3P](third_party) which are explicitly exempt.

## Setup

After cloning the repository you must complete the following steps:

1. Install the latest version of the Android SDK and set `ANDROID_HOME` to point at it. More
   detailed instructions are available in the [rules_android] repository.
2. TODO(jack-bradshaw): Understand and document xcode setup requirements.

Once these steps are complete you will be able to build and test code on macOS, Linux, and Windows.

## Structure

All code is divided into two root directories, [1P](first_party) and [3P](third_party). All code
originating from this repository lives in 1P, and all code belonging to another
repository/person/organisation belongs in 3P. This maintains a clear and unambiguous division for
licensing and maintenance purposes. A few files exist outside these directories for core
repository/build setup.

## Build/Test

Bazel is used as the build tool across the repository.

- Build all code by running `bazel build //...`.
- Test all code by running `bazel test //...`.

View the granular package documentation for more more focused build/test instructions.

## Presubmit

Submissions to main are automatically checked by a presubmit system which build all code, runs all
tests, and ensures all code is formatted correctly. On Github it automatically runs on each PR, and
it must pass before code can be submitted. Exceptions are granted only when the presubmit system
itself is broken.

Presubmit is run locally with `source first_party/presubmit/presubmit.sh; run_presubmit`.

WARNING: Presubmit may modify the working directory during execution, so commit/stash all changes
before running to avoid lost work.

## Formatting

Autoformatting is available for all supported languages and file types with a single command.

- Format all files by running `bazel run //first_party/formatting:autoformat`.
- Format specific files by running `bazel run //first_party/formatting:autoformat -- $paths`.

All files must be in their formatted state before submission (enforced by presubmit).

TODO(jack-bradshaw): Add instructions for formatting only the files in the last commit and staging
area.

## Versioning

The repository is versioned by the current date, with the version defined in the
[MODULE.bazel](MODULE.bazel) file, and since the repository contains multiple projects, the version
is updated whenever any project is released in any way.

TODO(jack-bradshaw): Create an automatic version update system.

## Releasing

View the granular packages for release information.

TODO(jack-bradshaw) create a repository wide release system.

## Package Managers

Package managers are used to manage external deps without building them from source. Various package
managers and indexes are used across the repo, including Maven, NPM, and PyPI. They all follow a
general pattern for integration and use, with minor differences in the exact details.

### General

Each package managers generally has:

- A registry where deps are declared once for the entire repository.
- A lock file where deps are secured against supply chain attacks.
- A syntax for referencing deps across the entire repository.

To use a new dep:

1. Declare it in the registry.
2. Regenerate the lock file.
3. Reference it in build targets.

To remove an existing dep:

1. Delete references in build targets.
2. Remove the declaration from the registry.
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
- Lock by running `bazel run :requirements.update &> /dev/null`.
- Reference as `@pypi//$packageName`.

Example: [mdformat](https://pypi.org/project/mdformat/0.7.22/) is registered as `mdformat==0.7.22`
and referenced as `@pypi//mdformat`.

### Crate

To manage crate deps:

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
exception of all that cannot be uploaded to GitHub due to the 100MB file size limit. Whenever you
change the deps (including Bazel deps and package-manager deps), revendor by running
`bazel vendor //...`. Any oversized deps are ignored via [.gitignore](.gitignore), and large files
can be found by running `find third_party/bazel_vendor -type f -size +100M`.

TODO(jack-bradshaw): Migrate off GitHub and delete the large file limit.

## Documentation

All documentation is stored in the repository itself, including design docs, one-pagers, guides, and
all other ancillatory paperwork. This ensures the codebase itself is the authorative source of truth
for all related materials, and ensures code does not become separted from related documentation.

## Branching Strategy

This repository exclusively uses [trunk-based development](https://trunkbaseddevelopment.com) to the
degree that all alternative forms of branch management are banned. This is essential for monorepo
development and generally means:

1. No collaborative branches: You may create private branches off main for your own work, but do not
   collaborate on them with others.
1. Rebase, don't merge: You should rebase onto HEAD locally to get updates from others, and when
   your code is ready for submission, rebase it onto HEAD (with a PR) instead of merging.
1. Make small, atomic commits: Each commit must change only the files required for that change and
   nothing more. If multiple commits serve a shared purpose, link them together by tagging an issue
   in the commit description.
1. Keep HEAD green: PRs may only be submitted when CI passes. This is enforced automatically.
1. Integrate frequently: Submit your changes as soon as they are ready, and locally rebase your
   branches onto HEAD multiple times a day to reduce the severity of merge conflicts.
1. Use feature flags: Incomplete features at HEAD are unavoidable with trunk-based development, so
   use feature flagging to guard production behavior.
1. One commit per PR: Each PR must have exactly one commit to ensure code-review is focused,
   simplify CI, and create a 1:1 mapping between changes and review. This is enforced automatically.

Overall this approach creates a single, shared, linear history in the main branch and creates an
unambiguous shared HEAD.

## Large Files

All files must be stored directly in the repository and not in supplementary storage, meaning Git
LFS, Git Annex, and all other such tools are banned. So far this has only results in two large
vendored deps being ignored, and migration to a self-hosted Git repository is in progress to work
around GitHub's 100MB limit.

TODO(jack-bradshaw): Migrate off GitHub and delete the above comment.

## Licensing Agreement

There is currently no CLA.

TODO(jack-bradshaw): Create a CLA.

## Standards

Bugs? Look mate, you know who has a lot of bugs? Blokes who bludgeon their products to death with
vibe coding. Professionals have standards. Be polite. Be efficient. Have a plan to kill every bug
you meet.

TODO(jack-bradshaw): Formalize high level engineering standards.

Prioritise the following:

1. Code quality.
2. User experience.

Prioritizing code quality generally means providing documentation, optimising for maintainability
and readability, always writing tests, taking time to refactor when code has grown beyond its
original scope, following language/platform/framework conventions, and adhering to engineering best
practices. If you are unsure about a change, simply consider the experience of the maintainer after
you have moved on, and if you wouldn't want to be that person, improve the code now.

TODO(jack-bradshaw): Explain what prioritising the user experience means.
