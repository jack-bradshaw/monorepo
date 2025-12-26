# Starlark Code Block Directives

Directives for Starlark code blocks in this repository.

## Definitions

A Starlark code block is a code block written in the Starlark language.

## Scope

All Starlark code blocks in this repository must conform to these directives, regardless of the
enclosing language (e.g. Starlark code blocks in Java files must conform); however, granular
exceptions to individual requirements apply automatically when the enclosing language prevents
conformance; furthermore, the contents of [third_party](/third_party) are explicitly exempt, as they
originate from external sources.

## Standard: Fully-Qualified Labels

Bazel target references must be fully-qualified.

Example: "The foo library can be imported from `//first_party/foo:foo`"

This allows targets to be copied into a terminal and evaluated from any working directory, and it
allows automated systems to parse and validate documentation (thereby preventing documentation rot).
