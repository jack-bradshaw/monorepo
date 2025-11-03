# Standard for Standard Documents

The standard for standards in this repository.

## Terminology

The following terms apply with specific meaning:

- Requirement: The individual rules and expectations that constitute the standard.
- Standard: An individual standard document in [standards](/first_party/standards).
- Standards: The collective set of standards in [standards](/first_party/standards).

## Scope

This standard applies to all standards in this repository. The recursive contents of
[third_party](/third_party) are excepted as the contents are populated automatically from third
party sources.

## Format

Standards are written in Markdown.

## Structure

Standards consist of the following elements, in order:

- Introduction: A level-one heading matching the standard's name, with a body introducing the
  standard.
- Terminology: A level-two heading, with a body describing terminology used throughout the standard.
- Scope: A level-two heading, with a body explaining where the standard applies.
- Custom Sections: An arbitrary number of level-two headings, with a body describing the
  requirements of the standard.

The Introduction and Scope sections are mandatory, whereas the Terminology section is optional. No
other sections may exist between or before them. Level 3 and 4 headings may be used in the Custom
Sections only. Custom sections must not be grouped under a single level two section unless there is
only one section.

## Contents

Standards include requirements that must be satisfied before submission but are not presently
enforced via presubmit. Standards must be deleted upon successful automation or redundancy.
Artificial intelligence is not sufficient grounds for automation due to the inherent
non-determinism.

## Decomposition

Each standard covers a single domain/topic, and standards are decomposed into multiple standards
upon violation of this requirement. Overlap between standards is acceptable, but conflicts between
them must be documented.

## Verbiage

Requirements are expressed in terms of "must" and "may" and the former implies mandatory conditions
that block submission, whereas the latter implies discretion and flexibility. For example, "All
packages must contain at least one file" sets a lower limit, whereas "Packages may contain a single
file" permits packages with no additional files. The terms "must" and "may" are not strictly
required outside of bullet-point lists, but must be used where possible.

## Examples

Standards may contain examples throughout to clarify requirements.

## Exceptions

General exceptions to the entire standard must be documented in the Scope section, whereas granular
exceptions must be documented in proximity to the relevant requirement.
