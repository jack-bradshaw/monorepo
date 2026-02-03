# Bazel Directives

Directives for [Bazel](https://bazel.build) build in this repository.

## Scope

All BUILD files in this repository must conform to these directives; however, the contents of
[third_party](/third_party) are explicitly exempt, as they originate from external sources.

## Documentation

Directives for documentation related to the build system.

## Build Files

Directives for BUILD files.

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
