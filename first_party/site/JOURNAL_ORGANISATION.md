# Journal Organisation

This repository relies on a highly restricted, sparse implementation of a theoretical structural
metadata system known as the 16-Dimensional Matrix. For the complete theoretical paper detailing the
rationale, epistemological decoupling, and the Pure vs. Inherited partition, read the
[Literature Architectonics](/journal/literature-architectonics) manuscript in the journal.

## The Active Schema

This journal organizes its contents exclusively via rigid taxonomies. The metadata is divided across
fifteen distinct arrays, but because this corpus serves as an internal mind rather than a public
broadcast, it is a **limited implementation**.

### 1. The Active Taxonomies

The following namespaces form the functional core of the literature mapping.

1. **Characters**: Explicit personas and psychological fragments.
2. **Composition**: Physical data format.
3. **Style**: Geometrical layout of language.
4. **Veracity**: The 7-point Epistemological gradient.
5. **Tropes**: Narrative structural gears.
6. **Settings**: Physical bounding coordinates.
7. **Series**: Sovereign sequential arcs.
8. **Genres**: Orthogonal narrative arcs.
9. **Subjects**: Academic institutions.
10. **Themes**: Emergent emotional Subtext.
11. **Topics**: Explicit, literal conceptual nouns.

### 2. The Excluded & Irrelevant Taxonomies

The remaining axes of the 16-Dimensional Matrix are structurally maintained by the system but
deliberately left functionally empty in this implementation. They represent external, societal
legacy arrays that provide zero value to the internal self-organization of this corpus:

- **Movements:** Retrospective historical academic eras.
- **Tone:** Subjective mood interpretation.
- **Audience:** Demographic marketing brackets.
- **Ratings:** Contemporary moral thresholds and content warnings.

Note: Operational arrays such as `items.json`, `highlights.json`, and `behaviour.json` are
maintained alongside the taxonomies for UI and manifesting data, not literature cross-association.

## Structural Validation (CI/CD)

To guarantee the arrays never degrade or overlap, the schema is strictly enforced by an automated
Kotlin test suite (`JournalMetadataTest.kt`).

These tests act as the final defensive boundary, ensuring:

- **Absolute Schematic Adherence** across all 15 JSON registries.
- **Key Validity** through explicit mapping between the arrays and the master `items.json` manifest.
- **Physical Existence** of the referenced markdown manuscripts within the `content/` bundle.
