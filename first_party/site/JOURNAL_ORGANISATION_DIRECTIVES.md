# Journal Organisation Directives

Directives for the classification and metadata structure of all journal pieces.

## Scope

All journal pieces and the JSON registries located in
[first_party/site/data/journal/](/first_party/site/data/journal/) must conform to these directives.

## Architecture

All journal documents are stored in a [single directory](/first_party/site/static/content/journal)
and a taxonomy system groups them into logical collections. The taxonomy is defined by four JSON
registries:

1. Themes ([themes.json](/first_party/site/data/journal/themes.json)): The abstract philosophical
   payload or universal subject matter explored in a piece.
2. Formats ([formats.json](/first_party/site/data/journal/formats.json)): The structural
   presentation of a piece, independent of its conceptual themes or subject matter.
3. Genres ([genres.json](/first_party/site/data/journal/genres.json)): The narrative tropes utilized
   within fiction.
4. Series ([series.json](/first_party/site/data/journal/series.json)): The categorization of
   connected pieces into an ordered continuum intended for sequential consumption.

These registries are linearly independent with one exception: Genres may only contain pieces where
the format is narrative, screenplay, or unconventional narrative (view #standard-Genre-Legality).

### Composition over Inheritance

The taxonomy system uses composition over inheritance; the taxonomic registries themselves capture
independent aspects of each piece without definitional overlap, and each registry is structured as a
flat set of collections without overlap in their definitions. For example:

- Themes: "Dominion", "Creativity", and "Engineering".
- Formats: "Essay", "Meditation", and "Narrative".

Themes and formats both describe linearly independent aspects of a document, and within each, the
options are linearly independent. Consequently, new collection can be added within registries
without rebalancing or rearranging a hierarchical tree; however, structural modifications are
limited to appending new collections and splitting an existing collection, which is acceptance since
content is rarely deleted from the journal. This system ensures adding new journal entries only
requires one of:

- Splitting a collection because it has become too large.
- Adding a new collection because none of the existing colletions fit.

The use of mutually exclusive sets without hierarchical nesting ensures insertion never requires
rebalancing of parent-child relationships, which entirely eliminates the administrative overhead of
organising the journal. Manual organisation requires human-level ontological reasoning and a full
understanding of every piece, so eliminating this requirement improves scalability and creates more
time for content creation.

Note: Linear independence can be verified either through human ontological reasoning or by computing
the cosine similarity of the Term Frequency-Inverse Document Frequency (TF-IDF) vectors of the
registry descriptions to mathematically guarantee a 0.000 programmatic intersect.

## Taxonomy Design

Directives for the global architecture of the taxonomy.

### Standard: Theme Definitions

All themes must have mutually exclusive definitions.

Positive example: "Veganism" existing alongside "Automobiles".

Negative example: "Veganism" existing alongside "Morality", as the former has significant overlap
with the latter.

This eliminates placement ambiguity and improves reader experience by simplifying theme discovery.

### Standard: Flat Theme Distribution

Themes must operate as a flat distribution of overlapping sets with zero parent-child inheritance.

Negative example: "Society" existing alongside "Dominion", as the former is a supertype of the
latter.

This eliminates placement ambiguity while eliminating the need for time-intensive tree rebalancing
as the journal grows.

Note: By extension, broad umbrella themes, such as "Human Nature", "Psychology", and "Existence",
must not be used.

### Practice: Thematic Intersection

Each piece may be assigned to multiple themes without restriction.

Example: An essay that explores the intersection of artificial intelligence and faith may be
assigned to both themes.

Rule of thumb: Pieces must only be assigned to multiple themes because the piece itself inherently
covers multiple disparate concepts, never because there is overlap between the definitions of the
themes themselves.

This ensures the journal works for multidisciplinary pieces while preserving strict lexical
orthogonality in the registry.

## Piece Assignment

Directives for the assignment of a single piece to the registries.

### Standard: Minimum Theme

A piece must be assigned to at least one theme.

Positive example: Assigning a piece exclusively to "Creativity".

This enforces that every piece anchors itself to an abstract philosophical payload.

### Standard: Format Exclusivity

A piece must be assigned to exactly one format.

Negative example: A piece configured as both a "Narrative" and an "Essay".

This ensures the physical presentation metric is strictly decoupled from thematic material and
uniformly applied.

### Standard: Genre Legality

A piece must only be assigned to a genre if its format natively supports fiction.

Positive example: A "Screenplay" assigned to "Horror". Negative example: An "Essay" assigned to
"Sci-Fi".

Genres govern narrative tropes, which are structurally incompatible with non-fictional or lyrical
presentation formats. TODO(jack@jack-bradshaw): Write a test for this requirement.

### Standard: Series Linearity

A piece must be assigned to at most one series.

Positive example: A piece with zero series, or a piece assigned solely to the "Journal of the Tao"
series. Negative example: A piece assigned to two different series simultaneously.

Series define strict linear curation for sequential storylines; branching a single piece into
multiple intersecting continuums explicitly breaks the sequential linearity.
