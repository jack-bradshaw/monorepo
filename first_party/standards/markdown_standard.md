# Markdown Standard

The standard for Markdown files in this repository.

## Scope

This standard applies to all Markdown files in this repository. The recursive contents of
[third_party](/third_party) are excepted as the contents are populated automatically from third
party sources.

## Terminology

The following terms apply with specific meaning:

- Heading Line: Any line in a Markdown file that begins with one or more hash (#) symbols.
- Body Lines: Any line in a Markdown file that does is not a heading line.
- Links: A Markdown file reference using square brackets (e.g. `[somefile](somereference)`).

## Heading Lines

Heading lines adhere to the following requirements:

- Heading lines must use title case.
- Heading lines may be sentence fragments.
- Heading lines must fit on one line.
- Heading lines must not exceed level four.
- Heading lines must have at least one body line between them.
- Heading lines may decrease by multiple levels in one step (e.g. a level four heading may be
  followed by level two heading).
- Heading lines must not increase by multiple levels in one step (e.g. a level two heading may be
  directly followed by level three heading but not level four heading).

## Lists

Lists adhere to the following requirements:

- Lists must be preceeded by an introductory body line.
- Lists must not contain blank lines between items.
- Lists must not be empty but may contain only a single element if necessary.
- Lists omit trailing punctuation when the items are sentence fragments.

This section contains an example of a standard-conformant list.

## References

References adhere to the following requirements:

- Files and directories within the repository must be referenced with links (not backticks); Bazel
  targets are exempt from this requirement.
- Links must use absolute references (relative to the repository root).
- Links must use semantic names (i.e. identify the content not the literal destination).
