# Standard for Standard Documents

The standard for standards in this repository.

## Terminology

The following terms apply with specific meaning:

- Requirement: The individual rules and expectations that constitute the standard.
- Standard: An individual standard document in [standards](/first_party/standards).
- Standards: The collective set of standards in [standards](/first_party/standards).

## Scope

All standards in this repository must conform to this standard. The recursive contents of
[third_party](/third_party) are excepted as the contents are sourced from third parties.

## Format

Standards must be Markdown files.

## Composition

Standards must consist of the following elements, in order:

- A level-one heading matching the name of the standard.
- A body introducing the standard.
- A level-two heading named "Terminology".
- A body that defines all custom terms used throughout the standard.
- A level-two heading named "Scope".
- A body that explains where the standard does and does not apply.
- An arbitrary number of custom level-two headings with bodies and level-three/four subsections.

## Decomposition

Each standard must cover a single domain/topic/entity, and standards must be decomposed into
multiple standards upon violation of this requirement; hence, overlap between standards is possible,
and conflicts between them must be documented.

## Contents

The content of standards must adhere to the following requirements:

- Standards must not contain requirements that are enforced using automation (e.g. presubmit).

## Verbiage

Requirements must be expressed in terms of "must" and "may", where the former indicates a limitation
and the latter indicates an option. For example, "All packages must contain at least one file" is a
limitation, whereas "Packages may contain a single file" is an option.

## Examples

Examples must be documented in proximity to the relevant requirement.

## Exceptions

General exceptions to the entire standard must be documented in the Scope section, whereas granular
exceptions must be documented in proximity to the relevant requirement.
