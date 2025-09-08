# Kotlin Lightweight Upgrade (KLU)

General helpers and utilities for Kotlin.

## Getting Access

There are three ways to include this library in your project:

1. Use the pre-built package.
2. Build the package from source.
3. Reference the source directly.

### Pre-built Package

To use the pre-built packages, add `com.jackbradshaw:klu:1.0.0` to your project's Maven
dependencies. Older versions are available in the
[Maven Repository](https://search.maven.org/artifact/com.jackbradshaw/klu).

### Building From Source

To build the package from source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository: `git clone https://github.com/jack-bradshaw/monorepo`
3. Start the build: `bazel build //first_party/klu:binary.deploy`

This will produce a jar in the `monorepo/bazel-out/first_party/klu` directory. Copy this Jar into
your project as needed.

### Referencing Directly

To reference the package directly in another Bazel workspace:

1. Install this repository in your WORKSPACE.
2. Reference the library target in your deps.

For example:

```
# In your WORKSPACE file
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
git_repository(
    name = "com_jackbradshaw",
    branch = "main",
    remote = "https://github.com/jack-bradshaw/monorepo",
)

# In your BUILD file
kt_jvm_library(
    name = "hello_world",
    srcs = "HelloWorld.kt",
    deps = [
        "@com_jackbradshaw//:first_party/klu",
    ]
)
```

## Contents

KLU contains various packages:

- [Concurrency](https://github.com/jack-bradshaw/monorepo/blob/main/first_party/klu/concurrency)
- [Flows](https://github.com/jack-bradshaw/monorepo/blob/main/first_party/klu/flow)

Follow the links for more details and tutorials.

## Building

To build the library with dependencies excluded:

```
bazel build :klu
```

To build the library with dependencies included:

```
bazel build :binary
```

To release the library with dependencies included to
[sonatype](https://s01.oss.sonatype.org/#welcome):

```
bash release.sh
```
