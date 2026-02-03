# Bazel Directives

Directives for [Bazel](https://bazel.build) build in this repository.

## Scope

All BUILD files in this repository must conform to these directives; however, the contents of
[third_party](/third_party) are explicitly exempt, as they originate from external sources.

## Build Targets

Directives for organizing code into build targets.

### Practice: Granular Targets

Each target should have a single source file.

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
- Resource files that represent a set of related values, not discrete sources, may be merged in a
  single target (e.g. Android resources), to avoid the unnecessary manintenance burden.

This makes file-level dependencies explicit, eliminates the possibility of overloaded God-object
targets, sets the codebase up for complex dependency management situations ahead of time, and
improves improves build cache usage.

### Standard: Documented Exports

All calls to `exports_files` must have an explicit documented reason in the BUILD file.

Example:

```starlark
# Required for reference by macro-generated build rules in other packages
exports_files(["pom_template.xml"])
```

This ensures that the reason for exposing raw files (which breaks encapsulation) is clear to future
maintainers.
