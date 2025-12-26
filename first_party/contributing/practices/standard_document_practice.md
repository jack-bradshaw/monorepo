# Practice for Standard Documents

The practice for standards in this repository.

## Terminology

The following terms apply with specific meaning:

- Requirement: An individual rule.
- Standard: An individual standard document in [standards](/first_party/contributing/standards).
- Standards: The collective set of standards in [standards](/first_party/contributing/standards).

## Scope

All standards in this repository should follow this practice.

## Location

Standards that apply to all packages must be defined in the
[contributing/standards](/first_party/contributing/standards/) package; whereas, standards that
apply to a subset of packages must be located in the lowest common ancestor of the packages
they apply to (e.g. the [build-tests standard](/first_party/build_tests/build_tests_standard.md)).
Exceptions (such as excluding third party) do not

## Decomposition

Each standard should cover a single domain/topic/entity, and standards should be decomposed into
multiple standards when they grow too large; hence, overlap between standards is possible,
and conflicts between them must be documented.

## Verbiage

Requirements must be expressed in terms of "must" and "may", where the former indicates a limitation
and the latter indicates an option. For example, "All packages must contain at least one file" is a
limitation, whereas "Packages may contain a single file" is an option.

## Examples

Examples should be documented as close to relevant requirement as possible.

## Exceptions

Examples should be documented as close to relevant requirement as possible, and exceptions that
apply to the entire standard should be documented in the Scope section.