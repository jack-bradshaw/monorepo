# Journal Taxonomy

Directives for the assignment of individual manuscripts to the taxonomy registries.

Note: These directives could be automated with a test; however, migration to protobuf is planned,
and thus testing will eventually become unnecessary, as protobuf can encode them in the schema
directly with required/optional keywords and types.

### Standard: Topic Assignment Required

Every piece must be assigned to at least one topic.

This ensures every piece is discoverable and remains organised.

### Standard: Topic Assignment Repeatable

Assignment to multiple topics is allowed for pieces that encompass aspects of more than one topic.

This enables multidisciplinary pieces to be discovered through all relevant concrete vehicles.

### Standard: Format Assignment Required

Every piece must be assigned to a format.

This ensures the physical presentation metric is strictly decoupled from thematic material and
universally applied.

### Standard: Format Assignment Repeatable

Assignment to multiple formats is allowed exclusively for composite pieces that structurally weave
or compose them (e.g. a piece containing both an essay and a poem).

This structural flexibility is required to accurately map multi-format or experimental literature
without forcing arbitrary formatting constraints.

### Standard: Genre Assignment Conditional

Assignment to a genre is required if the format is Narrative, Screenplay, or Unconventional Fiction.
For all other formats, genre assignment is optional.

Positive example: An "Essay" assigned a genre (e.g. a fictional essay).

Negative example: A "Narrative" assigned to no genre.

This guarantees classification where narrative tropes are inherently present, while avoiding forced
classification on non-fictional or lyrical formats.

### Standard: Genre Assignment Repeatable

Assignment to multiple genres is allowed.

This accounts for fictional works that synthesize tropes from multiple traditionally disjoint
genres.

### Standard: Theme Assignment Optional

Assignment to a theme is optional. Pieces may exist without a theme when one does not apply or
cannot be determined.

This avoids classification for the sake of classification, explicitly permitting purely aesthetic or
factual pieces that lack implicit philosophical payloads.

### Standard: Theme Assignment Repeatable

Assignment to multiple themes is allowed.

This ensures the taxonomy correctly maps to complex literature, where multiple philosophical bridges
are often crossed simultaneously.

### Standard: Series Assignment Optional

Assignment to a series is optional. Pieces may exist without a series when one does not apply or
cannot be determined. This allows standalone pieces to exist without being forced into a broader
narrative or sequential system.

### Standard: Series Assignment Repeatable

Pieces may be assigned to multiple series. This allows pieces to form into broader universes.

Note: Previously this standard was the opposite and pieces were restricted to at most one series
explicitly to preserve sequential linearity. The standard was altered to allow pieces to form a
broader overarching literary universe (i.e. a directed graph of pieces). In this new system, a
shared universe is the emergent set of all transitively connected pieces.
