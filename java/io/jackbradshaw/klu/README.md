# Kotlin Lightweight Upgrade (KLU)

Essential Kotlin helpers and utilities.

## Dependency

There are multiple options for including this library in your project:

- Download the pre-built binaries.
- Build the binaries from source.
- Reference the source directly using Bazel.

### Pre-built Binaries

To use the pre-build binaries, include `io.jackbradshaw:klu:0.0.1` in your Maven dependencies. Visit
[Maven Central](https://search.maven.org/artifact/io.jackbradshaw/klu) for build-tool specific instructions and links to
previous versions.

### Building From Source

To build the binary from source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository: `git clone https://github.com/jack-bradshaw/monorepo`
3. Invoke the build: `bazel build //java/io/jackbradshaw/klu_full.deploy`

This will produce a jar in the `monorepo/bazel-out` directory. The exact steps for including this jar in your project
will vary depending on your setup.

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

## Contents

This library contains:

- Collections utilities
  in [io.jackbradshaw.klu.collections](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/collections)
- Concurrency utilities
  in [io.jackbradshaw.klu.concurrency](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/concurrency)

The available collections utilities are:

- [DoubleListBuffer](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/collections/DoubleListBuffer.kt):
  Simplifies buffering with concurrent read/write support.

The available concurrency utilities are:

- [Once](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/concurrency/Once.kt): Ensures
  blocks of code are executed at most once.

Implementations are available for the interfaces with a Nice* prefix on the name. For example, DoubleListBuffer is
implemented by NiceDoubleListBuffer. 
