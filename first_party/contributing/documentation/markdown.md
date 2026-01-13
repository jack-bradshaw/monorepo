# Markdown Directives

Directives for Markdown files in this repository.

## Scope

All Markdown files in this repository must conform to these directives; however, the contents of
[third_party](/third_party) are explicitly exempt, as they originate from external sources.

## Practice: Title Case Headings

Headings must use title case.

Positive example: "## Writing Style Standard"

Negative example: "## Writing style standard"

This ensures consistency and professionalism across all documentation.

## Standard: Single Line Headings

Headings must fit on one line.

Positive example: "## Code Block Standard"

Negative example: A heading that wraps to multiple lines.

This maintains readability and allows automated tools to parse heading structure.

## Standard: Maximum Heading Depth

Headings must not exceed level four.

Positive example: Use `####` as the deepest heading level.

Negative example: Use `#####` or deeper.

This prevents excessive nesting and maintains document scannability.

## Standard: Heading Separation

Headings must have at least one body line between them.

Positive example:

```markdown
## Foo

Foo always contains Bar.

### Bar
```

Negative example:

```markdown
## Foo

### Bar
```

This ensures each heading introduces actual content rather than just creating hierarchy.

## Standard: Sequential Heading Levels Upward

Headings must not increase by multiple levels in one step, but they may decrease by multiple levels
in one step.

Positive example:

```markdown
# Foo

Foo.

## Bar

Bar.

### Baz

Baz.

#### Qux

Qux.

## Quux

Quux.
```

Negative example:

```markdown
## Foo

Foo.

#### Baz
```

This aids accessibility and eliminates ambiguity about missing information.

## Standard: Backticks for Code Elements

References to code elements must be enclosed in backticks.

Positive example: "The `Foo` class"

Negative example: "The Foo class"

This distinguishes code references from prose and enables syntax highlighting in rendered
documentation.

## Standard: Introductory List Lines

Lists must be preceded by an introductory body line.

Example:

```markdown
The following items are required:

- Item one
- Item two
```

This provides context for the list and improves readability.

## Standard: Compact Lists

Lists must not contain blank lines between items.

Positive example:

```markdown
- Item one
- Item two
```

Negative example:

```markdown
- Item one

- Item two
```

This maintains visual cohesion and follows standard Markdown conventions.

## Standard: Unformatted List Items

List items must not be bolded.

Positive example: `- List Item`

Negative example: `- **List Item**`

This prevents visual clutter and improves accessibility.

## Practice: Avoid Bolding

Bolding should be avoided where possible. Use structure (e.g., headings, lists) or italics (for
emphasis) instead.

Positive Example: "_Important_: Do not delete the database."

Negative Example: "**Important**: Do not delete the database."

This improves readability for screen reader users and those with cognitive impairments.

## Standard: List Item Marker

Unordered lists must use hyphens (`-`) as list markers.

Positive example:

```markdown
- Item one
- Item two
```

Negative example:

```markdown
- Item one
- Item two
```

This ensures consistency across all documentation and reduces visual clutter.

## Automation: Ordered List Numbering

Ordered lists must use `1.` for all items.

Positive example:

```markdown
1. First item
1. Second item
1. Third item
```

Negative example:

```markdown
1. First item
1. Second item
1. Third item
```

This saves time when inserting new items or reordering the list, as renumbering is not required.

## Standard: Square Bracket Links for Files

Filesystem references must use square-bracket links.

Positive example: `[file.md](/first_party/contributing/file.md)`

Negative example: `` `file.md` ``

This clearly distinguishes filesystem references from code elements and ensures each link has an
address.

## Standard: Absolute Link References

Link addresses must be absolute references.

Positive example: `[file.md](/first_party/contributing/file.md)`

Negative example: `[file.md](../file.md)`

This ensures links can be evaluated from any location in the repository and improves tooling
integration (e.g. large scale find/replace).
