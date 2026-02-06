# Directives Directives

Directives for directive documents in this repository.

## Scope

All documents containing directives in this repository, and the directives in those documents, must
conform to these directives.

## Structure

Directives for the structure of directive documents.

### Standard: Top-Level Heading

Directive documents must contain exactly one top-level heading.

Example: This document.

This provides clear document identity and prevents structural ambiguity.

### Practice: Scope Section

Directive documents must contain a Scope section that specifies where the directives apply (along
with any exceptions). It should be a level two heading section at the start of the document (after
the Terminology section if present).

Example: The Scope section at the top of this document.

This eliminates ambiguity and ensures clear guidance for contributors.

### Practice: One Directive Per Section

Each directive should be contained in its own section.

Example: See [shell.md](/first_party/contributing/code_blocks/shell.md) where "No Prefixes" and "No
Shebangs" are separate sections.

This ensures each directive has a single type, can be referenced unambiguously, and remains
independent of other directives.

### Standard: Type Headers

The type of directive (Standard, Practice, Guideline, or Automation) should be specified in the
section header.

Examples: `## Standard: Foo not Bar`, `## Practice: Foo Not Bar`, `## Guideline: Foo Not Bar`, and
`## Automation: Foo Not Bar`.

This enables contributors to quickly distinguish between absolute rules and conventional practices.

### Practice: Terminology Section

Directive documents may contain a Terminology section that defines any specialized terms that apply
across the document. It should be a level two heading section at the start of the document.

Example: The terminology section in [Style](/first_party/contributing/documentation/style.md).

This clarifies meaning and prevents ambiguity for readers unfamiliar with the domain.

### Guideline: Order by Importance

Directives should be ordered in a way that presents the most important information first, and there
is no need to group all standards together, all practices together, or all guidelines together;
however, all automation directives must be placed at the end of the containing section.

Example: "1. Standard: No murdering. 2. Practice: Do no harm. 3. Guideline: Have Fun. 4. Automation:
No GOTO statements."

This helps readers quickly understand the key requirements without reading through less critical
information, and allows the natural flow of information to take precedence over directive type;
furthermore, it retains context for automations.

NOTE: The above example is purely for instructional purposes, and the directives it contains are not
actual directives that need to be followed.

### Practice: Nesting

Directives are listed as level 2 headings when grouping them into sections offers no benefits, and
grouped when there are clear clusters of similar directives.

Example: This document.

Clustering aids comprehension by keeping related directives together.

## Content

Directives for the content of each directive.

### Practice: Phrasing

Standards are stated in terms of "must" and "must not"; whereas, practices are loose and verbiage is
not strongly specified, provided it is clear and unambiguous; furthermore, guidelines are freeform
text.

Example: "Code must compile" (standard) vs "Code should be formatted consistently" (practice) vs
"Documentation exists to help contributors work together" (guideline).

This enables contributors to quickly distinguish the level of enforcement for each directive.

### Practice: Examples Provided

Examples should be provided for every directive to clarify its meaning and application.

Example: This.

This ensures directives are concrete and actionable and aids reader comprehension.

### Practice: Rationale Provided

The rationale for each directive is provided when not immediately obvious.

Example: "This ensures code blocks can be copied into a terminal without modification."

This helps contributors understand the purpose/context and make informed decisions about exceptions.

### Practice: Linear Independence

Directives should be linearly independent such that each operates on a distinct dimension and can be
followed in isolation without depending on other directives. Avoid creating directives that overlap
in scope or contradict each other.

Positive example: "Use precise technical terms" (language complexity) and "Include sufficient
context" (information density) are independent dimensions.

Negative example: "Be concise" and "Avoid brevity" are contradictory and not independent.

This ensures contributors can apply individual directives without needing to reconcile conflicts or
understand the entire directive system, and prevents redundancy in the directive set.

### Guideline: Offer Options

Directives should generally frame guidance in terms of what contributors can or should do to achieve
a goal, rather than primarily listing prohibitions; however, there are circumstances when a negative
without an alternative is acceptable (e.g. the No Shebangs directive in
[Shell Code Blocks](/first_party/contributing/code_blocks/shell.md)).

Positive Example: "Use `Bar` instead of `Foo` for better performance."

Negative Example: "Do not use `Foo`."

While negative constraints are sometimes necessary (especially for Standards), offering alternatives
and positive paths forward empowers contributors and fosters a collaborative environment.

### Guideline: Presentation Order

Directives should generally be presented as directive, example, rationale; however, guidelines are
generally exempt from this structure, as they are often complex, free-form text that require
examples, exceptions, justifications, and other content to be interlaced.

Example: The directives in this file.

Ordering standards and practices this way aids legibility by allowing readers to comprehend what is
being asked of them before they are presented with the detailed reasoning to support it, while
leaving guidelines unconstrained and open ended.

### Practice: Example Terminology

When an example of what to do is paired with an example of what not to do, the former is called
"positive example" and the latter is called "negative example".

Example: See [markdown.md](/first_party/contributing/documentation/markdown.md) "Compact Format"
section.

This ensures consistency and clarity when contrasting correct and incorrect approaches.

### Practice: Example Quotations

Quotations should be used when a literal example is being provided; however, they are not required
when linking to an example elsewhere.

Example: "This is a literal example" and
[this is a link](/first_party/contributing/documentation/markdown.md).

This distinguishes literal text from references and improves clarity.

## Context

Directives for the context of directive documents.

### Standard: Markdown Format

Directive documents must be written in Markdown.

Positive example: `document.md`

Negative example: `document.txt` or `document.pdf`

This ensures consistency and aligns with general conventions for repository documentation.

### Practice: Location

Directives that apply broadly should be defined in the [contributing](/first_party/contributing)
package; whereas, directives that apply to specific packages should be located in the lowest common
ancestor of the packages they apply to.

Example: This document and the [build-tests directives](/first_party/build_tests/directives.md).

This keeps documentation close to the code it applies to, thereby improving discoverability.

### Practice: README Declaration

Directive documents should be declared in the nearest README up the file tree.

Positive example: `[Standard](standard.md)` included in the package README.

Negative example: A directive document that exists in the filesystem but is not referenced in any
README.

This ensures directives are discoverable and explicitly associated with their package.

### Practice: Decomposition

Each directive document should cover a single domain/topic/entity, and documents should be
decomposed into multiple documents when they grow too large. This keeps each document focused,
thereby improving comprehension and maintainability.
