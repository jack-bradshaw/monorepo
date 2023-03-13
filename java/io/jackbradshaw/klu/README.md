# Kotlin Lightweight Upgrade (KLU)

General helpers and utilities for Kotlin.

## Getting Access

There are three ways to include this library in your project:

1. Use the pre-built package.
2. Build the package from source.
3. Reference the source directly.

### Pre-built Package

To use the pre-built packages, just add `io.jackbradshaw:klu:0.0.0` to your project's Maven dependencies. Older versions
are available in the [Maven Repository](https://search.maven.org/artifact/io.jackbradshaw/klu).

### Building From Source

To build the package from source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository: `git clone https://github.com/jack-bradshaw/monorepo`
3. Start the build: `bazel build //java/io/jackbradshaw/klu:binary.deploy`

This will produce a jar in the `monorepo/bazel-out/java/io/jackbradshaw/klu` directory. Copy this Jar into your
project as needed.

### Referencing Directly

To reference the package directly in another Bazel workspace:

1. Install this repository in your WORKSPACE.
2. Reference the package in your target deps.

For example:

```
# In your WORKSPACE file
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
git_repository(
    name = "io_jackbradshaw",
    branch = "main",
    remote = "https://github.com/jack-bradshaw/monorepo",
)

# In your BUILD file
kt_jvm_library(
    name = "hello_world",
    srcs = "HelloWorld.kt",
    deps = [
        "@io_jackbradshaw//:java/io/jackbradshaw/klu",
    ]
)
```

## Contents

KLU contains various packages:

- [Concurrency](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/concurrency)
- [Flows](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/flow)

Follow the links for more details and tutorials.