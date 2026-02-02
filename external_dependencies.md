# External Dependencies

External dependencies are managed using package managers. Various package managers and indexes are
used across the repository, including Maven, NPM, PyPI, and Cargo. Each package manager provides a
registry where external dependencies are declared, a lock file for pinning versions (not always),
and a system for referencing resolved dependencies in build targets. Resolved dependencies are
vendored to decouple the repository from external changes. Details are provided for each package
manager and the vendoring system.

## Maven

Maven dependencies are managed as follows:

- Dependencies are declared in the JVM section of [MODULE.bazel](/MODULE.bazel).
- Dependencies are locked via the `REPIN=1 bazelisk run @com_jackbradshaw_maven//:pin` shell
  command.
- Dependencies are referenced via `@com_jackbradshaw_maven//:${groupId}_{artefactId}`, where
  `groupId` and `artefactId` identifies the dependency, after all non-alphanumeric characters have
  been replaced with `_`.

For example, [Google Flogger](https://mvnrepository.com/artifact/com.google.flogger/flogger) is
registered as `com.google.flogger:flogger:1.0.0`, and referenced as
`@com_jackbradshaw_maven//:com_google_flogger_flogger`.

### NPM

NPM dependencies are managed as follows:

- Dependencies are declared in [`package.json`](/package.json).
- Dependencies are locked via the
  `bazelisk run -- @pnpm//:pnpm --dir $(pwd) install --lockfile-only` shell command (the working
  directory must be the repository root when run).
- Dependencies are referenced via `//:node_modules/$packageName`, where `packageName` identifies the
  dependency.

For example,
[babel-plugin-minify-infinity](https://www.npmjs.com/package/babel-plugin-minify-infinity) is
registered as `"babel-plugin-minify-infinity": "0.4.3"`, and referenced as
`//:node_modules/babel-plugin-minify-infinity`.

### PIP

PIP dependencies are managed as follows:

- Dependencies are declared in [`pip_requirements.in`](/pip_requirements.in).
- Dependencies are locked via the `bazelisk run :requirements.update` shell command.
- Dependencies are referenced via `@pypi//$packageName`, where `packageName` identifies the
  dependency.

For example, [mdformat](https://pypi.org/project/mdformat/0.7.22/) is registered as
`mdformat==0.7.22`, and referenced as `@pypi//mdformat`.

### Crate

Crate dependencies are managed as follows:

- Dependencies are declared in the Rust section of [`MODULE.bazel`](/MODULE.bazel).
- Dependencies are not locked as locking not supported by the package manager.
- Dependencies are referenced via `@crates//:$packageName`.

For example, [serde](https://crates.io/crates/serde) is registered as:

```starlark
crate.spec(
    package = "serde",
    version = "1.0",
)
```

After registration it is referenced as `@crates//:serde`.

## Vendoring Dependencies

External dependencies that are used in the build are vendored for hermeticity; however, every
dependency that contains at least one file over 100MB is excluded, as the GitHub file size limit
prevents the dependency from being pushed. Pushing the smaller files of the oversized dependency is
not possible as that interferes with Bazel operations, so the entire dependency must be excluded.
The shell command to vendor such dependencies is `bazelisk vendor //...`, and the shell command to
identify oversized dependencies is `find third_party/bazel_vendor -type f -size +100M`. Oversized
dependencies are ignored via [`/.gitignore`](/.gitignore).

## Issues

Issues relating to external dependencies must be reported to their respective authors.

## Contributions

Contributions to external dependencies must be contributed to their respective upstream sources
(i.e. not this repository.)
