# Concurrency

General purpose concurrency infrastructure for Kotlin.

## Release

Not released to third party package managers.

## Contents

This package contains the following general purpose concurrency tools:

- [Pulsar](/first_party/concurrency/pulsar): A clock that emits pulses periodically.
- [Quinn](/first_party/concurrency/quinn): A multi-producer single-consumer actor-object.

All utilities are designed to be generic/reusable general-purpose components that can be used in any
Kotlin program. They are all independent of each other, so documention is distributed across various
README to avoid overloading this one.

## Contributing

Contributions from third parties are accepted.

## Issues

Tag all issues related to this package with `concurrency`.
