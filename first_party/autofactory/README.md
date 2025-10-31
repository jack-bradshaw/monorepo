# AutoFactory

[Autofactory](https://github.com/google/auto/tree/main/factory) Bazel integration.

## Release

Not released to third party package managers.

## Usage

The `//first_party/autofactory` target installs the AutoFactory annotation processor and exports the
AutoFactory runtime dependencies. It can be referenced in the `deps` attribute of any Java/Kotlin
target to integrate AutoFactory, for example:

```starlark
java_library(
    name = "foo",
    srcs = ["Foo.java"],
    deps = [
      "//first_party/autofactory",
    ]
)
```

After installation, AutoFactory is available in the sources.

```kotlin
@AutoFactory
class Foo(
  @Provided private val dep: Dep,
)
```

Further details are available in the
[AutoFactory docs](https://github.com/google/auto/tree/main/factory).

## Issues

Issues relating to this package are identified by the `autofactory` tag.
