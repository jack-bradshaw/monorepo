# Kotlin Lightweight Upgrade (KLU)

Essential Kotlin helpers and utilities.

This guide covers:

- Dependency: How to include the library in your project.
- Usage guide: How to use the utilities.

## Dependency

There are multiple ways to include KLU in your project:

1. Download the pre-built binaries.
2. Build the binaries from source.
3. Reference the source directly using Bazel.

### Pre-built binaries

To use the pre-build binaries, include `io.jackbradshaw:klu:0.0.1` in your Maven dependencies. Visit
[Maven Central](https://search.maven.org/artifact/io.jackbradshaw/klu) for build-tool specific instructions and previous
versions.

### Building from source

To build the compiled binary from source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository: `git clone https://github.com/jack-bradshaw/monorepo && cd monorepo`
3. Run the build command: `bazel build //java/io/jackbradshaw/klu_full.deploy`

This will produce a jar in the `monorepo/bazel-out` containing the library and all its dependencies. The exact steps
for using this jar in your project will vary depending on your choice of build tool and project stucture.

### Referencing with Bazel

To reference the library directly in another Bazel workspace:

1. Install this repository. In your WORKSPACE file add:

```
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

git_repository(
    name = "io_jackbradshaw",
    branch = "main",
    remote = "https://github.com/jack-bradshaw/monorepo",
)
```

2. Reference the library. In your BUILD file:

```
kt_jvm_library(
    name = "hello_world",
    srcs = "HelloWorld.kt",
    deps = [
        "@io_jackbradshaw//:java/io/jackbradshaw/klu",
    ]
)
```

## Usage Guide

TODO: This section needs work.

The concurrency package contains various concurrency utilities.

### Once

[Once](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/concurrency/Once.kt) ensures a block
of code runs exactly once even under asynchronous conditions. For example:

```kotlin
var x = 0
val setup = once {
  x += 1
}

println("$x") // Will print 0

for (i in 0..10) launch { setup.runIfNeverRun() }

println("$x") // Will print 1
```