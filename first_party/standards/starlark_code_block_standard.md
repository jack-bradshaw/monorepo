# Starlark Code Block Standard

The standard for Starlark code blocks in this repository.

## Scope

This standard applies to all Starlark code blocks in this repository, including in-code
documentation (e.g. Javadoc) and all ancillary documentation (e.g. READMEs and standards). The
recursive contents of [third_party](/third_party) are excepted as the contents are populated
automatically from third parties. The requirements apply regardless of the enclosing file's language
(e.g. a Starlark code block in a Java file must conform to the Starlark requirements).

## Requirements

Code blocks in Starlark adhere to the following requirements:

- Bazel targets are specified as fully-qualified targets.

For example: The foo library is imported from `//first_party/foo:foo`.
