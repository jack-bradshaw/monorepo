# Dagger KSP Integration

This package enables Dagger KSP support.

## Usage

There are two ways to use this package: The macros and the plugin.

### Macros

The `kt_jvm_library_with_dagger`, `kt_jvm_test_with_dagger`, and `kt_jvm_binary_with_dagger` macros
are provided to ease the process of using Dagger with KSP. They run the Dagger compiler on their
sources using KSP and otherwise function like their non-Dagger counterparts. Examples:

```starlark
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "test_component",
    srcs = ["TestComponent.kt"],
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_test_with_dagger(
    name = "test_component_test",
    srcs = ["TestComponentTest.kt"],
    deps = [
        ":test_component",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_jvm_binary_with_dagger(
    name = "test_component_binary",
    srcs = ["TestComponentBinary.kt"],
    main_class = "first_party.dagger.tests.TestComponentBinary",
    deps = [
        ":test_component",
    ],
)
```

There is no need to declare Dagger dependencies or plugins, they are added automatically by the
macros. Simply compile Dagger Kotlin sources with the macros and they will work.

### Plugin

For cases where the Dagger plugin is required directly, the `dagger_ksp_plugin` target can be
referenced directly in Kotlin targets. KSP will not trigger Java compilation though unless a Java
source is provided, therefore the target using the plugin will need to add a stub Java source.
Example:

In `Stub.java`:

```java
class Stub {}
```

In `BUILD`:

```starlark
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "my_lib",
    srcs = ["MyLib.kt", "Stub.java"],
    plugins = ["//first_party/dagger:dagger_ksp_plugin"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
```

To avoid defining a stub files repeatedly, the exported `Stub.java` file can be referenced. Example:

```starlark
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "my_lib",
    srcs = ["MyLib.kt", "//first_party/dagger:Stub.java"],
    plugins = ["//first_party/dagger:dagger_ksp_plugin"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
```

## Rationale

Running Dagger with KSP presents a challenge colloquially known as "Jar Hell", meaning the Dagger
compiler and KSP depend on different versions of [Guava](https://github.com/google/guava), which
leads to conflicts at build-time. The [implementation](/first_party/dagger/BUILD) compiles the
Dagger compiler and its dependencies into a single jar, then uses
[JarJar](https://github.com/pantsbuild/jarjar) to rename all Guava classes for consistency (a
process known as "shading"). This allows the Dagger compiler and KSP to coexist without conflict.

## Testing

Given the delicate nature of this operation, the [tests](/first_party/dagger/tests) exercise a
complex Dagger graph to verify this approach does not interfere with Dagger's normal operation. The
Dagger code in the tests do not comply with the
[repository contributing guidelines](/first_party/contributing/tooling/dagger.md) because their sole
purpose is exercising the dagger compiler, and they should not be used as dependencies beyond the
test. Furthermore, the tests themselves are split across multiple targets: macro invocations
exercise the macro, and test targets check the Dagger-generated code is correct.
