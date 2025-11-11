# README Standard

The standard for READMEs in this repository.

## Scope

All READMEs in this repository must conform to this standard. The [root README](/README.md) and the
[first party README](/first_party/README.md) are exempt as they function as introductory pieces to
the entire repository. The recursive contents of [third_party](/third_party) are excepted as the
contents are sourced from third parties.

## Presence

Any package may contain a README.

## Contents

READMEs may contain details relating to the enclosing package and its subpackages, but they must
omit details relating to superpackages and parallel packages. Furthermore, they must omit details
covered in other READMEs. For example, a readme in `/first_party/foo` may contain details for
`/first_party/foo` and `/first_party/foo/bar`, but not `/first_party` or `/first_party/baz`, and any
details covered in the `/first_party/foo/bar` README must not be repeated.

## Top Level First Party

Every directory in [first_party](/first_party) must contain a READMEs consisting of the following
elements, in order:

- A level-one heading matching the name of the package.
- A body introducing the contents of the package.
- A level-two heading named "Release".
- A body that provides release information, including API stability status, remote coordinates, and
  any other details required for third party access.
- An arbitrary number of custom level-two headings with bodies and level-three/four subsections.
- A level-two heading named "Issues".
- A body that specifies the tag for issues relating to the package/subpackages.
- A level-two heading named "Contributions".
- A body that specifies whether contributions from third parties are accepted in the package.
