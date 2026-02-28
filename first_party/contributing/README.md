# Contributing

This package contains directives for contributing to this repository.

## Release

The directives in this package are generic and transferable to other repositories. Other
organizations are free to adapt the directives to their own needs within the terms of the
[Creative Commons Attribution 4.0 International License](LICENSE). Since they are markdown files and
do not compile in any way, they are not released to package managers, and can be used by linking to
them or copy-pasting them into other systems.

## Definitions

Throughout the documents in this package, the following terms are used with specific meaning:

1. Contributor: A human, agent, or other system that modifies the contents of this repository.
1. Maintainer: A person who consumes the content of the repository directly (i.e. API users).
1. User: A person or organization which consumes the artefacts produced by the repository (i.e. app
   users).
1. Directive: A standard, practice, guideline, or automation.
1. Standard: A directive that is an absolute requirement. Standards must all be satisfied prior to
   submission and are strictly enforced during code review (by automation where possible).
1. Automation: A directive that is enforced by presubmit.
1. Practice: A directive that is a widely accepted convention across the codebase. Practices should
   be followed to maintain consistency, but deviation is acceptable when justified by the context.
1. Guideline: A directive that offers a perspective, mental model, or qualitative advice. Guidelines
   aid decision-making and align contributors without prescribing specific solutions and approaches.
1. Directive document: A document that lists one or more directives for contributors.
1. Documentation: Any human-readable file that exists to provide context to contributors. Documents
   are not necessarily documentation (e.g. the contents of [site](/first_party/site/)).
1. First Party Property: A top-level package in [first_party](/first_party) (and all of its
   subpackages).

The main distinction between standards, practices, guidelines, and automation is their ambiguity.
Standards are unambiguous and could be automated with enough time. Practices are ambiguous enough to
require interpretation, and generally cannot be automated. Guidelines are highly subjective, and
exist to align contributors on a common approach. Automations require no manual work to evaluate as
they are enforced by presubmit.

## Subpackages

The directives in this package are organized into the following subpackages:

- [Documentation](documentation) - Directives for writing documentation.
- [Code Blocks](code_blocks) - Directives for writing code blocks.
- [Tooling](tooling) - Directives for using tooling.
- [Repository](repository) - Directives for using the repository in general.

This component-based architecture colocates similar directives.

## Additions/Modifications

The [Directives](/first_party/contributing/documentation/directives.md) document contains directives
for writing directives.

## Historical Context

The [Writing Voice Evolution](/first_party/contributing/writing_voice_evolution.md) document details
the evolution of the [Style](/first_party/contributing/documentation/style.md) directives from rigid
standards to flexible guidelines, and is retained for historical reference and context.

## Issues

Issues relating to this package and its subpackages can be identified by the `contributing` tag.
