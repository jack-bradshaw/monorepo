# OmniXR

A passthrough system for using OpenXR hardware without compromising software architecture best practices.

Contents:

- Objective: What's covered this document and where to find other materials.
- Overview: High-level introduction to this library.
- Getting Access: How to include this library in your project.
- Usage Guide: How to use the library.

Please include [#omnixr](https://github.com/jack-bradshaw/monorepo/issues?q=is%3Aissue+is%3Aopen+%23omnixr+) in all
related bugs.

## Objective

The purpose of this document is to help engineers get started using the library. Deep technical decisions are
are not covered here and can be found in the [design document](https://todo.com) instead. For documentation
on the OpenXR standard, read the [specification](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf) document.

## Getting Access

There are three ways to access the library:

1. Download the pre-built binaries.
2. Build the binaries from source.
3. Reference the source directly.

The pre-built binaries are distributed via Maven. Include `io.jackbradshaw:omnixr:0.0.1` in your dependencies to use
the latest version. Visit the [Maven Central Repository](https://search.maven.org/artifact/io.jackbradshaw/omnixr)
for detailed instructions and previous versions.

To build the binary from source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository by running `git clone https://github.com/jack-bradshaw/monorepo && cd monorepo`
3. Invoke the build by running `bazel build //java/io/jackbradshaw/klu_full.deploy`
4. Collect the binary from the `monorepo/bazel-out` for inclusion in your project.

To reference the source directly:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html) and follow
   the [Introduction Guide](https://bazel.build/about/intro) to set up a workspace if you don't already have one.
2. Install this repository in your WORKSPACE file:

```
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
git_repository(
    name = "io_jackbradshaw",
    branch = "main",
    remote = "https://github.com/jack-bradshaw/monorepo",
)
```

3. Reference the library in the deps of your BUILD target:

```
kt_jvm_library(
    name = "my_hello_world",
    srcs = "MyHelloWorld.kt",
    deps = [
        "@io_jackbradshaw//:java/io/jackbradshaw/openxr",
    ]
)
```

4. Run `bazel sync` if you run into issues.

## Usage Guide

TODO