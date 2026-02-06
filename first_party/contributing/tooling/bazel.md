# Bazel Directives

Directives for [Bazel](https://bazel.build) build in this repository.

## Scope

All BUILD files in this repository must conform to these directives; however, the contents of
[third_party](/third_party) are explicitly exempt, as they originate from external sources.

## Guideline: Visibility

Targets should use the most restrictive visibility that satisfies the build, meaning private unless
otherwise required, and narrowly scoped to the packages that need them; however, some packages are
intended for broad consumption across the repository and consumption by the general public, and may
be exposed without granular control (view [publicity directive](#standard-centralized-publicity));
furthermore, targets that are effectively exposed via the API of another target must be at least as
visible as their dependents.

Too Permissive:

```starlark
# Used in this package only.
# Problem: It is marked public which is unnecessarily broad.
kt_jvm_library(
    name = "local_helper",
    srcs = ["Helper.kt"],
    visibility = ["//visibility:public"],
)
```

Too Restrictive:

```starlark
# Used in //other/package:consumer.
# Problem: It is private (by virtue of no visibility attribute) so the build fails.
kt_jvm_library(
    name = "shared_util",
    srcs = ["Util.kt"],
)

# Used in :dagger_component.
# Problem: Consumers of :dagger_component cannot reference the module to pass it to the builder.
kt_jvm_library(
    name = "dagger_module",
    srcs = ["DaggerModule.kt"],
)

# Used publicly.
# Problem: None, present for example purposes only.
kt_jvm_library_with_dagger(
    name = "dagger_component",
    srcs = ["DaggerComponent.kt"],
    deps = [":dagger_module"],
    visibility = ["//visibility:public"],
)
```

Just Right:

```starlark
# Used in this package only.
kt_jvm_library(
    name = "local_helper",
    srcs = ["Helper.kt"],
)

# Used in //feature/foo (recursively) and //feature/bar (specifically).
kt_jvm_library(
    name = "shared_util",
    srcs = ["Util.kt"],
    visibility = [
        "//feature/foo:__subpackages__",
        "//feature/bar:__pkg__",
    ],
)

# Used in many packages across the repo but not released to the general public.
kt_jvm_library(
    name = "public_api",
    srcs = ["Api.kt"],
    visibility = ["//first_party:__subpackages__"],
)

# Used in dagger_component (forms part of the API).
kt_jvm_library(
    name = "dagger_module",
    srcs = ["DaggerModule.kt"],
    visibility = ["//visibility:public"],
)

# Used publicly.
kt_jvm_library_with_dagger(
    name = "dagger_component",
    srcs = ["DaggerComponent.kt"],
    deps = [":dagger_module"],
    visibility = ["//visibility:public"],
)
```

This effectively communicates the intended use of each target, retains flexibility in packages not
intended for widespread/public consumption, and avoids accidental dependencies.

Note: Visibility choices should not be made on the basis of whether a package is an API or
implementation, as these terms are language-level, are not universal across languages, and have no
objectively verifiable meaning. Visibility is purely a system for conveying intended usage.

## Standard: Centralized Publicity

Every top-level first party package must contain a file called `publicity.bzl`. It must define a
`PUBLICITY` variable which specifies the visibility for all exposed targets in the property,
accompanied by a comment justifying the value. All exposed targets must reference this variable for
their visibility.

Examples: View [Autofactory](/first_party/autofactory/publicity.bzl),
[Otter](/first_party/otter/publicity.bzl), [Council](/first_party/council/publicity.bzl), and
[Universal](/first_party/universal/publicity.bzl).

This creates an explicit definition of the publicity of each first party property that documents the
contributors' intent and can be easily modified.

## Practice: Minimal Sources

Every build target should have at most one source file (in srcs, src, etc.).

Example:

```starlark
kt_jvm_library(
    name = "foo",
    srcs = ["Foo.kt"],
)

kt_jvm_library(
    name = "bar",
    srcs = ["Bar.kt"],
)
```

Exceptions:

- Rulesets that do not support decomposition into granular targets (e.g., `rules_hugo`) inherently
  force multiple sources into one target, and are therefore exempt.
- Supporting files may be merged in a single target when they represent a set of related values
  without discrete identities (e.g., Android resources). This avoids the unnecessary maintenance
  burden of excessive target count.

This makes file-level dependencies explicit, eliminates the possibility of overloaded God-object
targets, sets the codebase up for complex dependency management situations ahead of time, and
improves build cache usage.

## Standard: Documented Exports

All calls to `exports_files` must have an explicit documented reason in the BUILD file.

Example:

```starlark
# Required for reference by macro-generated build rules in other packages
exports_files(["pom_template.xml"])
```

Unrestricted exports break encapsulation by allowing any package to depend on the file. Documenting
the reason ensures the export is necessary and not just a workaround for proper target definition.

## Standard: Explicit Dependency Declaration

Targets must explicitly declare all dependencies they require instead of relying on their transitive
dependencies.

Example:

```starlark
kt_jvm_library(
    name = "foo",
    srcs = ["FooThatUsesGuava.kt"],
    deps = [
        "//:dep_that_also_uses_guava",
        # Explicitly listed, even though it is provided by the other dep
        "@maven//:com_google_guava_guava",
    ],
)
```

Explicit declaration ensures hermeticity and prevents downstream breakages when upstream targets or
macros change.

Note: In some languages this is enforced automatically (i.e. Java strict deps).

## Practice: Minimal Rule Principle

Targets should use the more basic rule where possible.

Example:

```starlark
# Uses base kt_jvm_library because Dagger is not needed.
kt_jvm_library(
    name = "foo",
    srcs = ["Foo.kt"],
)

# Uses the dagger extension because Dagger is needed.
kt_jvm_library_with_dagger(
    name = "bar",
    srcs = ["Bar.kt"],
)
```

Minimizing rule complexity reduces build graph size, compilation time, and simplifies the
repository.

## Standard: Test-Only Targets

Targets intended for exclusive use in tests must be marked as `testonly = True`.

Example:

```starlark
# Positive example
kt_jvm_library(
    name = "foo_test",
    srcs = ["FooTest.kt"],
    testonly = True,
)

# Negative example
kt_jvm_library(
    name = "foo_test",
    srcs = ["FooTest.kt"],
    # Missing testonly = True
)
```

This avoids polluting production with test code.

Exception: The test rules of most rulesets are inherently test-only (e.g. `java_test`); therefore,
test rules may omit the `testonly` attribute.
