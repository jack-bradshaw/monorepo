# Markdown Standard

The standard for Markdown files in this repository.

## Scope

All Markdown files in this repository must conform to this standard. The recursive contents of
[third_party](/third_party) are excepted as the contents are sourced from third parties.

## Headings

Headings must adhere to the following requirements:

- Headings must use title case.
- Headings may be sentence fragments.
- Headings must fit on one line.
- Headings must not exceed level four.
- Headings must have at least one body line between them.
- Headings may decrease by multiple levels in one step (e.g. a level four heading may be followed by
  level two heading).
- Headings must not increase by multiple levels in one step (e.g. a level two heading may be
  directly followed by level-three heading but not level four heading).

## Body Content

Body content must adhere to the following requirements:

- References to code elements (e.g. classes, functions, interfaces, macros, etc) must be enclosed in
  backticks (e.g. `Foo` not "Foo" or Foo).

## Lists

Lists must adhere to the following requirements:

- Lists must be preceded by an introductory body line.
- Lists must not contain blank lines between items.
- Lists must not be empty but may contain only a single element if necessary.
- Lists must omit trailing punctuation when the items are sentence fragments.

This section contains an example of a standard-conformant list.

## References

References must adhere to the following requirements:

- Filesystem references must be referenced with square-bracket links (not backticks).
- Links addresses must be absolute references (relative to the repository root).
