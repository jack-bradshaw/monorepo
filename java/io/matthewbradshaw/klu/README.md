# Kotlin Lightweight Upgrade (KLU)

Essential utilities and helpers to fill the gaps in the Kotlin standard library.

This guide is divided into:

1. Getting access.
2. Learning the concurrency utilities.

## Access

There are multiple ways to getting access to KLU in another project:

1. Include the pre-built binaries in the dependencies.
2. Build the binaries directly from the source.
3. Reference the source directly from another Bazel target.

### Pre-built binaries

The pre-built binaries for KLU are released to [Maven Central](https://repo1.maven.org/maven2). The latest release is available at:

- Group ID: io.matthewbradshaw
- Artifact ID: klu
- Version ID: 1.0.0

In short: io.matthewbradshaw:klu:1.0.0

[Previous versions](https://search.maven.org/artifact/io.matthewbradshaw/klu) remain available for posterity.

### Building from source

To build KLU from the source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the monorepo: `git clone https://github.com/matthewbradshaw/-io/monorepo`
3. Run the build command: `blaze build //java/io/matthewbradshaw/klu`

This will produce a jar containing the library in the bazel-out directory.

### Referencing from Bazel

To reference the library directly in another Bazel workspace:

1. Install the monorepo in the WORKSPACE:

```
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

git_repository(
    name = "io_matthewbradshaw",
    commit = "", # TODO(you): Pick a commit.
    remote = "https://github.com/matthewbradshaw/-io/monorepo",
)
```

2. Reference the library in the deps of another target:

```
kt_jvm_library(
    name = "hello_world",
    srcs = "HelloWorld.kt",
    deps = [
        "@io_matthewbradshaw//:java/io/matthewbradshaw/klu",
    ]
)
```

## Concurrency Utilities

The io.matthewbradshaw.klu.concurrency package contains various concurrency utilities.

### Once

The [once](https://github.com/matthewbradshaw-io/monorepo/blob/main/java/io/matthewbradshaw/klu/concurrency/Once.kt) utility simplifies running a block of code exactly one time. For example:

```kotlin
var x = 0
val setup = once {
  x += 1
}

once.runIfNeverRun()
once.runIfNeverRun()
once.runIfNeverRun()

println("$x") // Will print 1
```