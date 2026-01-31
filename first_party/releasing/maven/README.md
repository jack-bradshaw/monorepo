Standardized infrastructure for publishing JVM (Kotlin/Java) libraries to Maven Central.

The following tools are provided:

1. [`maven_release`](/first_party/releasing/maven/defs.bzl): A Starlark macro to define export targets.
1. [`release_launcher.sh`](/first_party/releasing/maven/release_launcher.sh): A shell script to invoke the publication process via `bazel`.

## Usage

To release a library, run the following command:

```bash
bazel run //path/to:target.release
```

This command prompts for the `Sonatype` password and performs the publication.
