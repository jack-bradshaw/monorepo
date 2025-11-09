# Starlark Code Block Standard

The standard for Starlark code blocks in this repository.

## Scope

All Starlark code blocks in this repository must conform to this standard, and it applies regardless
of the enclosing language (e.g. Starlark code blocks in Java files must conform); however, granular
exceptions to individual requirements apply automatically when the enclosing language prevents
conformance. The recursive contents of [third_party](/third_party) are excepted as the contents are
sourced from third parties.

## Requirements

Code blocks in Starlark must adhere to the following requirements:

- Bazel targets must be specified as fully-qualified targets.

For example: "The foo library is imported from `//first_party/foo:foo`".
