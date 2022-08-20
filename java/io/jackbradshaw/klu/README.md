# Kotlin Lightweight Upgrade (KLU)

Essential utilities and helpers to fill the gaps in the Kotlin standard library.

This guide covers:

- Dependency: How to include the library in another project.
- Concurrency: How to use the utilities in the KLU concurrency package.

## Dependency

There are multiple ways to include KLU in your project:

1. Include the pre-built binaries in your dependencies.
2. Build the binaries directly from the source.
3. Reference the source directly from another Bazel target.

### Pre-built binaries

To use the pre-build binaries, include `io.jackbradshaw:klu:2.0.0` in your Maven dependencies. See [Maven Central](https://search.maven.org/artifact/io.jackbradshaw/klu) for build-tool specific instructions and previous versions.

### Building from the source

To build the compiled binary from the source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository: `git clone https://github.com/matthewbradshaw-io/monorepo && cd monorepo`
3. Run the build command: `bazel build //java/io/jackbradshaw/klu_full.deploy`

This will produce a jar in the bazel-out directory which contains the library and all its dependencies. Include this in your project directly (exact steps depend on your choice of build tool).

### Referencing with Bazel

To reference the library directly in another Bazel workspace:

1. Install the repository in the WORKSPACE:

```
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

git_repository(
    name = "io_matthewbradshaw",
    branch = "main",
    remote = "https://github.com/matthewbradshaw-io/monorepo",
)
```

2. Reference the library in the deps of another target:

```
kt_jvm_library(
    name = "hello_world",
    srcs = "HelloWorld.kt",
    deps = [
        "@io_matthewbradshaw//:java/io/jackbradshaw/klu",
    ]
)
```

## Concurrency

The concurrency package (io.jackbradshaw.klu.concurrency) contains utilities to simplify concurrency in Kotlin.

### Once

[Once](https://github.com/matthewbradshaw-io/monorepo/blob/main/java/io/jackbradshaw/klu/concurrency/Once.kt) ensures a block of code runs exactly once even under asynchronous conditions. For example:

```kotlin
var x = 0
val setup = once {
  x += 1
}

println("$x") // Will print 0

for (i in 0..10) launch { setup.runIfNeverRun() }

println("$x") // Will print 1
```