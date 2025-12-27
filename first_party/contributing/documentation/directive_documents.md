# Directive Document Directives

Directives for writing directive documents in this repository.

## Terminology

The following definitions apply throughout this document:

- Directive: A standard, practice, or guideline.
- Directive document: A document that lists one or more directives for contributors.
- Standard: A directive that is an absolute requirement that must be satisfied prior to submission
  and can be deterministically enforced with automation.
- Practice: A directive that is a repository-wide convention that should be followed to maintain
  consistency and quality, but can be ignored when context dictates otherwise, and cannot
  necessarily be deterministically enforced with automation.
- Guideline: A directive that is a suggestion, perspective, or general recommendation based on
  experience, wisdom and common sense, but does not necessarily apply universally, and cannot be
  deterministically enforced with automation.
- Contributor: A human, agent, or other system that modifies the contents of this repository.

## Scope

All directive documents in this repository must conform to these directives.

## Structure

Directives for the structure of directive documents.

### Standard: Top-Level Heading

Directive documents must contain exactly one top-level heading.

Positive example: A document with a single `# Title` heading.

Negative example: A document with multiple `# Title` headings or no top-level heading.

This provides clear document identity and prevents structural ambiguity.

### Standard: Scope Section

Directive documents must contain a Scope section that specifies where the directive applies and any
exemptions.

Example: The Scope section at the top of this document.

This eliminates ambiguity and ensures clear guidance for contributors.

### Practice: One Directive Per Section

Each section should contain a single directive.

Example: See [shell.md](/first_party/contributing/code_blocks/shell.md) where "No Prefixes" and "No
Shebangs" are separate sections.

This ensures each directive has a single type, can be referenced unambiguously, and remains
independent of other directives.

### Practice: Nesting

Directives are listed as level 2 headings when grouping them into sections offers no benefits, and
grouped when there are clear clusters of similar directives.

Example: This document.

Clustering aids comprehension by keeping related directives together.

### Guideline: Order by Importance

Directives should be ordered in a way that presents the most important information first, and there
is no need to group all standards together, all practices together, or all guidelines together.

Example: "1. Standard: Kotlin must be used (no Java). 2. Practice: Jetpack Compose should be used
instead of Android Views. 3. Guideline: UI code tends to change rapidly so design for flexibility."

This helps readers quickly understand the key requirements without reading through less critical
details, and allows the natural flow of information to take precedence over directive type.

NOTE: The above example is purely for instructional purposes, and the directives it contains are not
actual directives that need to be followed.

## Content

Directives for the content of each directive.

### Practice: Phrasing

Standards are stated in terms of "must" and "must not", practices are stated in terms of "should"
and "should not", and guidelines are freeform text.

Example: "CLI line prefixes must be elided" (standard) vs "Shebang lines should be omitted"
(practice) vs "Documentation exists to help contributors work together" (guideline).

This enables contributors to quickly distinguish the level of enforcement for each directive.

### Practice: Rationale Provided

The rationale for each directive is provided when not immediately obvious.

Example: "This ensures code blocks can be copied into a terminal without modification."

This helps contributors understand the purpose/context and make informed decisions about exceptions.

### Practice: Examples Included

Examples should be provided for every directive to clarify its meaning and application.

Example: This.

This ensures directives are concrete and actionable and aids reader comprehension.

### Practice: Example Terminology

When an example of what to do is paired with an example of what not to do, the former is called
"positive example" and the latter is called "negative example".

Example: See [markdown.md](/first_party/contributing/documentation/markdown.md) "Compact Format"
section.

This ensures consistency and clarity when contrasting correct and incorrect approaches.

### Practice: Presentation Order

Directives should be presented as directive, example, rationale.

Example: The directives in this file.

This aids legibility by allowing readers to comprehend what is being asked of them before they are
presented with the detailed reasoning to support it.

### Standard: Type Headers

The type of directive (Standard, Practice, or Guideline) should be specified in the section header.

Examples: `## Standard: Spaces not Tabs`, `## Practice: Should be Kotlin instead of Java`, and
`## Guideline: Consider the surrounding context when making decisions.`

This enables contributors to quickly distinguish between absolute rules and conventional practices.

NOTE: The above example is purely for instructional purposes, and the directives it contains are not
actual directives that need to be followed.

## Context

Directives for the context of directive documents.

### Practice: Location

Directives that apply broadly should be defined in the [contributing](/first_party/contributing)
package; whereas, directives that apply to specific packages should be located in the lowest common
ancestor of the packages they apply to.

Example: This document and the
[build-tests standard](/first_party/build_tests/build_tests_standard.md).

This keeps documentation close to the code it applies to, thereby improving discoverability.

### Practice: Decomposition

Each directive document should cover a single domain/topic/entity, and documents should be
decomposed into multiple documents when they grow too large. This keeps each document focused,
thereby improving comprehension and maintainability.

### Standard: Markdown Format

Directive documents must be written in Markdown.

Positive example: `document.md`

Negative example: `document.txt` or `document.pdf`

This ensures consistency and aligns with general conventions for repository documentation.
