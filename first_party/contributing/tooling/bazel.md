# Bazel Directives

Directives for [Bazel](https://bazel.build) build in this repository.

## Scope

All BUILD files in this repository must conform to these directives; however, the contents of
[third_party](/third_party) are explicitly exempt, as they originate from external sources.

## Guideline: Visibility

Targets should use the most restrictive visibility that satisfies the build, meaning private unless
otherwise required, and narrowly scoped to the packages that need them; however, some packages are
intended for broad consumption across the repository and by the general public, and may be exposed
without granular control (view [publicity directive](#automation-centralized-publicity)); therefore,
visibility should distinguish between the public exposure and internal details.

Too Permissive:

```starlark
# An implementation detail of :bar.
# Problem: It is marked public but intended for private use in :bar.
kt_jvm_library(
    name = "foo",
    srcs = ["Foo.kt"],
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "bar",
    srcs = ["Bar.kt"],
    deps = [":foo"],
    visibility = ["//visibility:public"],
)
```

Too Restrictive:

```starlark
# A common utility for general reuse.
# Problem: Cannot be referenced outside the containing package.
kt_jvm_library(
    name = "shared_util",
    srcs = ["Util.kt"],
    # Private by virtue of no `visibility` attribute
)

# A type instantiated and exposed by :dagger_component
# Problem: Downstream consumers of :dagger_component can get instances from :dagger_component but
# cannot import the type declaration without violating strict deps.
kt_jvm_library(
    name = "foo",
    srcs = ["Foo.kt"],
    # Private by virtue of no `visibility` attribute
)

# Used publicly.
# Problem: None, target present for example purposes only.
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
    # Private by virtue of no `visibility` attribute.
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

# Used in dagger_component.
kt_jvm_library(
    name = "foo",
    srcs = ["Foo.kt"],
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

Caveats and nuance:

- Visibility choices should not be made on the basis of whether a package is an "API" or
  "implementation", as these terms are not universal across languages, and the line is not always
  clear. Visibility is purely a system for conveying intended usage, so if an implementation is
  intended for consumption, it should be visible to the consumers.
- Targets that are effectively exposed via the API of another should be at least as visible as their
  dependents (i.e. if foo provides instances of bar, ensure the bar target is as accessible as the
  foo target). This prevents consumers from being unable to reference the symbols they consume.
  Reflective access to runtime types that are usually hidden at build-time does not constitute a
  valid reason for increased visibility, though, as that would effectively require the entire build
  graph to be public regardless of encapsulation and abstraction (i.e. if a public API exposes `Foo`
  but internally uses `FooImpl` at runtime, then `FooImpl` may remain private).
- Subpackage references (`__subpackages__`) and package references (`__pkg__`) are not
  interchangeable. The former should only be used when the entire subpackage tree should be able to
  use the target, and not as a shorthand for specific packages.
- The outlined examples use `visibility = ["//visibility:public"]` to illustrate the point. In
  reality, the publicity system would be used instead of a direct public reference (see
  [centralized publicity](#automation-centralized-publicity)).

## Automation: Centralized Publicity

The [publicity system](/first_party/publicity) must be used in all first party packages. This is
enforced by the publicity [conformance checker](/first_party/publicity/conformance). This explicitly
documents and communicates the publicity of first party properties while providing a central point
of control.

## Standard: Target-Scoped Visibility

Visibility must be defined on a per-target basis (i.e. not with the `package` function).

Positive example:

```starlark
kt_jvm_library(
    name = "foo",
    srcs = ["Foo.kt"],
    visibility = ["//visibility:public"],
)
```

Negative example:

```starlark
package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "foo",
    srcs = ["Foo.kt"],
)
```

Package defaults make it easy to unintentionally expose internal targets. Explicit visibility forces
a conscious decision for every target.

Note: Omitting the visibility attribute to mark a targets as private is acceptable.

## Practice: Private By Default

Targets intended for exclusive consumption within the enclosing package should be marked as private.

Example:

```starlark
# No explicit visibility attribute since private is the default.
kt_jvm_library(
    name = "foo",
    srcs = ["Foo.kt"],
)
```

This ensures targets are not accidentally used more broadly than intended.

## Practice: Restrictive Visibility for Implementations

Targets intended to form the implementation details of a multi-package system should be marked with
the most restrictive visibility that passes compilation.

Examples:

```starlark
kt_jvm_library(
    name = "used_by_foo",
    srcs = ["MyThing.kt"],
    visibility = ["//first_party/foo:__pkg__"],
)

kt_jvm_library(
    name = "used_by_bar_and_subpackages",
    srcs = ["MyOtherThing.kt"],
    visibility = ["//first_party/bar:__subpackages__"],
)
```

This ensures targets are not accidentally used more broadly than intended.

## Standard: Internal Visibility for Private APIs

Targets that constitute an API that is intended for broad use within the repository (but not by the
general public) should be marked with visibility `//first_party:__subpackages__`.

Example:

```starlark
kt_jvm_library(
    name = "internal_util",
    srcs = ["InternalUtil.kt"],
    visibility = ["//first_party:__subpackages__"],
)
```

This communicates clearly about reusable code while retaining the opportunity to refactor on demand.

## Practice: Public Visibility for Public APIs

Targets that constitute an API that is intended for broad use within the repository and is released
to the general public should be marked with visibility `//visibility:public`.

Example:

```starlark
kt_jvm_library(
    name = "public_api",
    srcs = ["Api.kt"],
    visibility = ["//visibility:public"],
)
```

This communicates clearly about reusable code, and allows external consumers to reference targets
directly (via bzlmod deps).

## Practice: Minimal Sources

Every build target should have at most one source file (defined in `srcs`, `src` etc.).

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

- Rulesets that do not support decomposition into granular targets (e.g. `rules_hugo`) inherently
  force multiple sources into one target, and are therefore exempt.
- Supporting files may be merged in a single target when they represent a set of related values
  without discrete identities (e.g. Android resources). This avoids the unnecessary maintenance
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
dependencies. In some languages this is enforced automatically (i.e. Java
strict deps).

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
