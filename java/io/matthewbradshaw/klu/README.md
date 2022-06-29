# Kotlin Lightweight Upgrade (KLU)

Essential utilities and helpers to fill the gaps in the Kotlin standard library.

## Releases

KLU is available from [Maven Central](https://repo1.maven.org/maven2):

Group ID: io.matthewbradshaw
Artifact ID: klu
Version ID: 

In short: io.matthewbradshaw:klu:1.0.0

Previous versions can be found in the [Maven repository](https://search.maven.org/artifact/io.matthewbradshaw/klu).

## Building from source

To build the library from source:

1. Install Bazel: Follow https://docs.bazel.build/versions/main/install.html.
2. Clone the monorepo: `git clone https://github.com/matthewbradshaw/-io/monorepo`
3. Run the build command: `blaze build //java/io/matthewbradshaw/klu`

## Referencing from Bazel

To reference the library directly in another Bazel workspace:

1. Install the monorepo in the WORKSPACE:

```
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

git_repository(
    name = "io_matthewbradshaw",
    commit = "", # TODO: Get the latest from head
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