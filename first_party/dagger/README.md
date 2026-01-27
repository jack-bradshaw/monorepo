# Dagger KSP Integration

This package enables Dagger KSP support.

## Usage

The `kt_jvm_library_with_dagger` macro is provided to ease the process of adding Dagger support to
Kotlin libraries. Example:

```starlark
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "test_component",
    srcs = ["TestComponent.kt"],
    deps = [
        "//:javax_inject", 
    ],
)
```

There is no need to add Dagger dependencies or plugins, they are added automatically by the macro.
An equivalent Java macro is not provided since this work exists solely to support KSP.

## Architecture

Running Dagger with KSP presents a challenge colloquially known as "Jar Hell". Essentially, the
Dagger compiler and KSP depend on different versions of [Guava](https://github.com/google/guava), which leads to
conflicts at build-time. The [implementation](/first_party/dagger/BUILD) compiles the Dagger
compiler and its dependencies into a single jar, then uses [Jar Jar](https://github.com/pantsbuild/jarjar) to rename all Guava classes for consistency (a process known as "shading"). This allows the Dagger compiler and KSP to coexist without conflict.

## Testing

Given the delicate
nature of this operation, the [tests](/first_party/dagger/tests) exercise a complex Dagger setup to verify this approach does
not interfere with Dagger's normal operation. The components in the tests do not comply with the
repository contributing guidelines, becuase their purpose is to exercise dagger, and should not be
used as dependencies beyond the test.