# Releasing

Standardized release scripts and tools for various package managers and export systems.

This package provides a unified interface for publishing monorepo artifacts to external
ecosystems, which ensures consistent metadata and authentication handling across all releasable
components.

## Contents

The following release types are supported:

- [`maven`](/first_party/releasing/maven): Tools for publishing JVM libraries to Maven Central.
- [`copybara`](/first_party/releasing/copybara): Tools for mirroring directories to standalone repositories.
