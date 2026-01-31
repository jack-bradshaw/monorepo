# Release Infrastructure Design Log

This document tracks the engineering problems encountered during the development of the release infrastructure and the solutions adopted.

## 1. Release Metadata Generalization (Solved)

### Problem

The initial `maven_release` infrastructure was hardcoded for a specific project `KLU`. Support was required for multiple independent libraries (`otter`, `concurrency`, etc.) from the same monorepo, each requiring unique metadata (`Artifact ID`, `Description`, `Project URL`, `License`).

### Solution

**Generic `pom_template.xml` and Parameterized Macro**

- `pom_template.xml` was converted to use placeholders (e.g. `{project_url}`, `{scm_url}`).
- `maven_release` was updated to accept these values as arguments and inject them via `sed` during the build process.
- _Outcome_: Any package in the monorepo can define a release target with its own identity while sharing the same underlying publishing logic.

### Rejected Solutions

**Hardcoded Metadata / Central Registry**

- _Rationale_: Storing all project metadata in a central `Starlark` file or hardcoding it in macros creates a maintenance bottleneck and prevents packages from owning their identity.

## 2. Automated Monorepo Segmentation (Solved)

### Problem

A mechanism was required to mirror specific directories (e.g. `first_party/klu`) to standalone `GitHub` repositories to allow for independent consumption and discovery, without manually maintaining multiple `Git` remotes.

### Solution

**Hermetic `Copybara` Integration**

- `Google Copybara` was integrated as a hermetic tool (fetched via `http_jar`).
- The execution toolchain was upgraded to `Java 21`.
- A `copybara_release` macro was implemented to dynamically generate the required `copy.bara.sky` config.
- A `launcher.sh` was implemented to resolve the monorepo `root` and `Git` refs at runtime, enabling local dry-runs and `CI` execution.

### Rejected Solutions

**Manual `Git` Remotes / Subtrees**

- _Rationale_: Manually managing `git remote add` and `git push` for dozens of libraries is impossible to scale and prone to "history drift" between the monorepo and mirrors.

## 3. Internal Versus External Dependency Model (Solved)

### Problem

The monorepo uses direct source dependencies (`//first_party/concurrency`), but external consumers need standard `Maven` coordinates (`com.jackbradshaw:concurrency:1.0.0`). Maintaining two parallel dependency graphs in `BUILD` files is error-prone and tedious.

### Solution

**Live-At-Head and Transform-On-Mirror**

- _Monorepo_: Continues to use `deps = ["//first_party/concurrency"]`. This ensures a single source of truth and atomic cross-project refactors.
- _Mirroring_: The `copybara_release` target applies `core.replace` transformations to rewrite these internal labels into external `Maven` coordinates _during the mirror process_.
- _Outcome_: Zero overhead for internal development; standard ecosystem compatibility for external consumers.

### Rejected Solutions

**Shadow `BUILD` Files**

- _Rationale_: Attempting to maintain a separate `BUILD.external` file within each package to represent the public dependency graph would double the maintenance cost of every change.

## 4. Transitive Dependency Versioning (Solved)

### Problem

When a library (e.g. `klu`) is published via `java_export`, it bundles its internal `Bazel` dependencies (e.g. `concurrency`) by default. Relying on `Semantic Versioning` (`SemVer`) for these sub-components requires perfect transitive awareness of the entire dependency graph to maintain accuracy, which is difficult to scale and can lead to "Jar Hell" if users mix versions.

### Solution

**Date-Based Versioning (CalVer)**

- All `Maven` releases are versioned using a date stamp (e.g. `YYYYMMDD.HHMMSS`).
- _Rationale_: Using date stamps avoids the illusion of semantic meaning or stability between independent releases. It treats the monorepo as a single unit of release where all components at a specific timestamp are guaranteed to work together.
- _Consequence: Atomic Snapshots_: To maintain this guarantee, `Maven` releases are not done in isolation. Instead, a branch is cut from `HEAD` and all relevant artifacts are released concurrently with the same date-based version. This ensures the transitive dependency graph remains perfectly consistent.
- _Transitive Handling_: Internal dependencies remain bundled in the `JAR` file for now. If external conflicts occur, the definitive version is always the latest timestamp.

### Rejected Solutions

**`maven_coordinates` Tags and `SemVer`**

- _Rationale_: While `maven_coordinates` tags can prevent shading, they require manual synchronization of version strings across dozens of `BUILD` files. Furthermore, `SemVer` creates a false sense of granular stability that is incompatible with the monorepo's "Live-at-Head" velocity.
