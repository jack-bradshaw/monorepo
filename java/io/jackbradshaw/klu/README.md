# Kotlin Lightweight Upgrade (KLU)

Essential Kotlin helpers and utilities for:

- [Collections](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/collections)
- [Concurrency](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/concurrency)
- [Flows](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/flow)

Follow the links for detailed documentation.

## Dependency

There are multiple ways to include this library in your project:

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
3. Invoke the build: `bazel build //java/io/jackbradshaw/klu:binary.deploy`

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