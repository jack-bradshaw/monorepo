# README Standard

The standard for READMEs in this repository.

## Scope

This standard applies to all READMEs in this repository. The root README is exempt as it functions
as an introduction to the entire repository. The recursive contents of [third_party](/third_party)
are excepted as the contents are populated automatically from third party sources.

## Presence

Every immediate directory in [first_party](/first_party) must contain a README. Presence in other
directories is discretionary.

## Structure

READMEs consist of the following elements, in order:

- Introduction: A level-one heading matching the package, with a body introducing the package.
- Release: A level-two heading with a body containing information about the release status of the
  package, including its stability and where it can be sourced from.
- Purpose: A level-two heading with a body explaining why the package exists.
- Contents: A level-two heading with a body where any files and subpackages may be listed.
- Usage: A level-two heading with a body providing guidance to consumers (i.e. how to use the
  contents of the package).
- Maintenance: A level-two heading with a body providing guidance to contributors (e.g. release
  processes and manual presubmission checks).
- Issues: A level-two heading with a body containing the line "Issues relating to this package and
  its subpackages are tagged with `$tag`." where $tag must be unique within the entire repository.
- Contributions: A level-two heading with a body stating the package is either open or closed to
  contributions from third parties.

The Introduction section is mandatory and all other sections are optional. No other sections may
exist.

## Contents

READMEs may contain details relating to the enclosing package and its subpackages, but must omit
details relating to all other packages. For example, a readme in `/first_party/foo` may contain
details for `/first_party/foo` and `/first_party/foo/bar` but not `/first_party` or
`/first_party/baz`. Details from subpackage READMEs must not be duplicated in superpackage READMEs.
