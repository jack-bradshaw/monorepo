# Writing Voice

Directives for the voice and style of documentation in this repository.

## Terminology

The following definitions apply throughout this document:

- Documentation: Files that provide context to contributors.
- General Documents: Files that form the primary content of the repository (e.g. the contents of
  [site](/first_party/site/static/content/journal/)).

The subtle distinction is important when discerning the scope.

## Scope

All documentation in this repository must conform to these directives; however, the recursive
contents of [third_party](/third_party) are explicitly exempt, as they originate from external
sources; furthermore, the directives apply only to documentation, not general documents.

## Philosophy

Contributing to a shared space requires alignment, but writing is inherently subjective. A rule that
ensures clarity for one reader may feel restrictive to another, and strict writing standards can
stifle the creativity needed to explain complex concepts effectively; therefore, the directives in
this document are generally presented as guidelines (with only a few standards and practices), and
most work by acknowledging a common pitfall and finding a balance between extremes. By focusing on
guidance instead of rigid rules, and encouraging a middle ground, they provide ample room for
individual expression, while discouraging known antipatterns and failure modes. This creates an
inclusive space where contributors can speak in their natural voice without excessive discord and
disorder.

## Guideline: Reasoned Wisdom

Repository documentation must be objective and fact-based, with references to the codebase and
external sources where appropriate; however, subjectivity in the form of hard earned experience is
invaluable, as facts alone lack the context to be useful, and eschewing the unique
experiences/perspectives of contributors does not create a supportive environment; therefore,
documentation should contain a balance of objectivity with subjectivity.

Too Academic: "`FooSort` exhibits O(N log N) average-case computational complexity, as formally
proven by Henderson (1984) via amortized analysis utilizing potential functions and aggregate
methods. Empirical validation was conducted through Monte Carlo simulation across 10,000 uniformly
distributed datasets, yielding a 95% confidence interval of [0.98N log N, 1.02N log N]. The
algorithmic soundness has been peer-reviewed and published in ACM Transactions (DOI:
10.1145/12345)." (Unnecessarily dense academic language)

Too Poetic: "`FooSort` dances through data like a graceful ballet, elegantly weaving elements into
their rightful places. It whispers efficiency at every turn, a symphony of comparisons orchestrating
order from chaos. Fast as lightning, beautiful as a sunset, it transforms your messy lists into
pristine arrays of pure harmony." (Flowery metaphors obscure technical meaning)

Just Right: "`FooSort` sorts N items in O(N log N) average time, making it suitable for most use
cases. However, it degrades to O(N²) on nearly-sorted data, which caused production issues at
Company X when customer uploads were typically pre-sorted. For that reason, `MergeSort` is preferred
for production workloads despite slightly worse constant factors." (Combines objective facts with
subjective experience and reasoned conclusion).

Striking a balance between objectivity and subjectivity ensures documentation remains accurate and
accessible, while creating an inclusive and supportive space for contributors.

## Guideline: Balance Minimalism with Sufficiency

Contributors must include enough information to avoid the hidden costs of missing documentation, as
the cost of storing a few extra paragraphs (kilobytes) is far lower than the cost of missing context
(e.g. hours of debugging, production failures, reinventing the wheel); however, unnecessary detail
defeats the purpose by obscuring the truth under unnecessary detail; therefore, documentation should
omit superfluous information.

Too Minimal: "`FooProvider` caches values." (Insufficient context about behavior, expiry, or known
issues)

Too Verbose: "`FooProvider` internally utilizes a `ConcurrentHashMap` instantiated with an initial
capacity of 16 and a load factor of 0.75 to store the cached values, which are themselves wrapped in
a custom `CacheEntry` object containing a 64-bit timestamp derived from `System.nanoTime()` for
high-precision expiry calculations, and are evictable using a least-recently-used policy implemented
via a synchronized doubly-linked list that is traversed in reverse order during cleanup cycles
triggered by a `ScheduledExecutorService` running on a separate daemon thread. Side note: It
commonly fails on Android devices due to OOM errors." (Excessive implementation detail buries the
critical OOM failure)

Just Right: "`FooProvider` caches values with a 60-second TTL using an LRU eviction policy. The
cache runs on a background thread and may cause OOM errors on memory-constrained Android devices."
(Sufficient context about behavior and known issues without unnecessary implementation detail).

Striking a balance between minimalism (avoiding unnecessary detail) and sufficiency (providing
adequate context) ensures documentation remains useful and accessible without obscuring critical
information under excessive implementation detail.

## Guideline: Balance Past, Present, and Future

Documentation should relate to the present state of the repository, as documenting the future risks
inaccuracy as plans change, and documenting the past can clutter the repository with obsolete
information; however, historical context often justifies the present while guarding against history
repeating itself, and acknowledging opportunities for future works highlights known deficiencies;
therefore, the past and future should be referenced where helpful.

Too Past-Focused: "Past implementations of this method used the standard math library. There were
issues with the parser but they were fixed." (Irrelevant details without actionable context)

Too Future-Focused: "In Q4 we plan to rewrite the parser. We will make this method asynchronous in
v2.0." (Promises without context about current state)

Just Right: "A custom parser is used because the standard library parser caused performance
regressions in v1.2. This method is synchronous, but asynchronous support may be added in a future
release (tracked by issue #123)." (Present state justified by historical context and known
limitations acknowledged with tracking reference).

Striking a balance between stability (documenting what exists) and context (providing necessary
background) ensures documentation remains accurate and helpful while preserving useful history and
acknowledging known constraints.

Note: Linking future plans to external tracking references gives readers a way to follow and get
updates.

## Guideline: Balance Precision with Simplicity

Documentation must be technically accurate and precise, as vague descriptions lead to
misunderstanding and incorrect usage; however, excessive technical detail can obscure the core
concept and overwhelm readers; therefore, contributors should provide precise explanations without
unnecessary complexity.

Too Vague: "`FooProvider` makes objects when you need them." (Lacks technical precision about
behavior)

Too Detailed: "The `FooProvider` utilizes a factory pattern instantiation mechanism wherein each
invocation of the accessor method triggers the allocation of heap memory via the `new` operator,
resulting in the construction of a distinct `Foo` instance with its own memory address." (Excessive
technical detail obscures simple behavior)

Just Right: "The `FooProvider` creates a new instance of `Foo` on every call." (Precise technical
description without unnecessary complexity).

Striking a balance between precision (being technically correct) and simplicity (being
understandable) ensures documentation conveys accurate information without overwhelming readers with
implementation minutiae.

## Guideline: Balance Formality with Informality

Documentation should use professional language that maintains credibility and clarity, as informal
slang can alienate readers and reduce perceived authority; however, overly academic or formal
phrasing creates cognitive barriers and distances readers; therefore, contributors should use clear,
standard technical English.

Too Informal: "Kotlin's compiler is pretty cool and just turns your code into bytecode or whatever
for different platforms like JVM and stuff." (Casual slang undermines credibility)

Too Formal: "Kotlin's compilation process culminates in the generation of bytecode for the Java
Virtual Machine and analogous artifacts for alternative execution substrates." (Overly academic
phrasing obscures meaning)

Just Right: "Kotlin compiles to the JVM and other platforms (e.g. JS, native)." (Professional tone
with clear, standard terminology).

Striking a balance between formality (maintaining professionalism) and informality (being
approachable) ensures documentation remains credible and authoritative while being accessible to
general engineers.

## Guideline: Specific Group References

Documentation often requires referencing the people or teams behind decisions and actions, as
attribution provides accountability and context; however, the personal pronouns ("we", "I", "us",
etc.) are ambiguous; therefore, contributors should use specific individual/group names where
possible.

Too Vague: "The FooProvider is recommended." (Recommended by who?)

Too Vague: "We decided to remove the drivers." (Unclear who "we" refers to)

Just Right: "The Kernel Team decided to remove the drivers for runtime performance." (Specific
attribution with clear reasoning).

Striking a balance between attribution (providing accountability and context) and clarity (avoiding
ambiguous pronouns) ensures clear communication by using specific group references (e.g., "The
Maintainers", "The Compiler Team", "The Security Workgroup") rather than vague pronouns.

Exception: Pronouns that refer to the user (e.g. "you") are acceptable (related to Balance
Declarative and Imperative Tone).

## Guideline: Balance Declarative and Imperative Tone

Documentation should describe the content of the repository by stating its behaviors and properties,
as this keeps the focus on the artifacts it contains; however, procedures, tutorials, and guides can
be clearer when written directly to the reader with imperative instructions; therefore, contributors
should match tone to context.

Too Imperative: "You must configure the `FooProvider` before you use it. You should call the
`init()` method first." (Commands in reference documentation)

Too Declarative: "The `//foo:bar` target generates artifacts when executed." (Passive description in
a tutorial where step-by-step guidance is needed)

Just Right: "Reference documentation: `FooProvider` must be configured prior to use by calling
`init()`. Tutorial: Generate the artifacts by running `bazelisk run //foo:bar`." (Declarative tone
for system descriptions, imperative tone for procedural guidance).

Striking a balance between declarative (defining the system) and imperative (guiding the reader)
ensures documentation provides appropriate information for its context, with reference documentation
focusing on artifacts and tutorials providing clear actionable steps.

## Guideline: Balance Confidence and Humility

Documentation should be written with confidence when the content is well-understood, as unnecessary
hedging undermines authority and creates doubt where none should exist; however, speaking with
excessive conviction when the truth is uncertain can detract from credibility; therefore,
contributors should balance confidence with humility by acknowledging the limitations of knowledge
and being transparent about their uncertainty.

Too Hesitant: "`FooProvider` is probably not thread-safe and there is a small risk of runtime
failure." (Unnecessary hedging about known behavior)

Too Confident: "`FooProvider` will definitely work under high memory pressure." (False certainty
about untested behavior)

Just Right: "`FooProvider` is not thread-safe. The behavior under high memory pressure is undefined
and has not been tested." (Confident about known facts, honest about unknowns).

Striking a balance between confidence (establishing authority) and humility (acknowledging limits)
ensures documentation maintains credibility without creating unnecessary doubt.

## Guideline: Balance Judgment with Neutrality

Contributors should exercise judgment to identify real issues, limitations, and constraints;
however, judgmental language that attacks systems, platforms, or contributors is unhelpful;
therefore, contributors should focus on factual descriptions without assigning blame or making
sweeping negative characterizations.

Too Neutral: "This implementation works everywhere." (Lacks judgment, ignores real constraints)

Too Judgmental: "Android is such a crap operating system it cannot even run this, also who wrote
this shit?" (Personal attack on platform and people)

Just Right: "Android devices circa 2012 have memory limitations that prevent this implementation
from working as intended. The `Foo` implementation does not work on Android." (Sound judgment with
specific, informative, and factual descriptions).

Striking a balance between judgment (identifying real issues and constraints) and neutrality
(avoiding personal attacks and blame) ensures documentation provides accurate and useful information
without resorting to judgmentalism.

## Guideline: Balance Consideration with Respect

Contributors should show consideration for the reader by acknowledging potential difficulties and
offering supportive guidance, with softening language used appropriately; however, assertions and
assumptions about the reader's mental or physical capabilities can be counterproductive, as they
cross a critical interpersonal boundary; therefore, documentation should offer options and advice,
while giving the reader the benefit of the doubt and avoiding personal assessments.

Too Dismissive: "This behavior is obvious and should be easy to understand." (Lacks consideration
for reader's potential challenges)

Too Presumptuous: "You will probably find this object confusing, and you should probably read the
troubleshooting guide when you get stuck." (Makes assertions about the reader's capabilities and
experience)

Just Right: "This object does not completely implement the interface contract, and may throw errors
in unexpected ways. Callers may wish to use `Bar` instead for a production ready service. Full
documentation is provided in the README." (Acknowledges limitations, offers options, remains neutral
and respectful).

Striking a balance between consideration (acknowledging potential challenges) and respect (avoiding
presumptions about the reader) ensures documentation provides supportive guidance without crossing
interpersonal boundaries or making assumptions about capabilities.

## Guideline: Balance Abstraction with Clarity

Documentation should use appropriate abstractions that match the system's design, as abstraction
hides unnecessary implementation details and aids understanding; however, narrative and colorful
language can obscure the truth under layers of conceptual indirection; therefore, contributors
should focus on clear, direct descriptions of system behavior.

Too Narrative: "`Foo` works like a conveyor belt, the first `Bar` on the belt gets processed, then
the next, and so forth, until the belt is empty, and the operator goes to lunch when there's nothing
to do." (Metaphor obscures meaning)

Too Imperative: "`Foo` wraps each `Bar` object as a Node, each with a link to the next/previous
node, and holds a reference to a node (marked root node), then recursively follows the links between
nodes to process them in the order they were added. All this happens on a background thread for
performance." (Excessive implementation detail)

Just Right: "`Foo` processes `Bar` objects using a FIFO queue, and idles while the queue is empty.
`Foo` uses the pub-sub pattern to process `Bar` objects asynchronously." (Clear, direct descriptions
with appropriate abstraction).

Striking a balance between abstraction (hiding implementation details) and clarity (avoiding
narrative weight) ensures documentation explains the implementation clearly without unnecessary
conceptual indirection.

## Guideline: Balance Documentation Proximity With Size

Documentation should be distributed close to the artifacts it relates to, as this prevents readers
from being overwhelmed by monolithic documents and helps them find information easily; however,
spreading information across too many documents obscures meaning and leaves readers without a
cohesive picture; therefore, contributors should balance colocation with cohesion.

Too Centralized: The root repository README containing documentation for the entire monorepo.
(Overwhelming, difficult to navigate)

Too Fragmented: A README in every package with details of that package only, with no overview or
context about how packages relate. (Lacks cohesion)

Just Right: A root README that introduces the repository structure and philosophy, with smaller
granular READMEs for large packages. (Balanced distribution with both overview and detail).

Striking a balance between colocation (keeping information close to where it matters) and cohesion
(providing a unified picture) ensures documentation is accessible without being overwhelming or
fragmented.

Note: Splitting a document in a directory into multiple files can help when the information is in
the right place but the document is becoming too large.

## Practice: Scope-Appropriate Documentation

Documentation should match its scope to its context. Granular documentation (e.g. Javadoc, inline
comments) should focus on the component they relate to; whereas, high-level documentation (e.g.,
READMEs, architecture docs) should focus on how components integrate and compose together, and how
they fit into the broader system.

Positive Example: Javadoc explaining a function's parameters, return value, and edge cases.

Negative Example: Javadoc explaining the entire system architecture.

Positive Example: README explaining how packages interact and the overall design philosophy.

Negative Example: README explaining the internal implementation of every function.

This ensures readers can find appropriate information at the appropriate level of abstraction
without redundancy or scope mismatch.

## Standard: American English

American English must be used, except where domain conventions dictate otherwise (e.g. referencing a
`Colours` object from a third party package).

Positive example: "color"

Negative example: "colour"

This ensures consistency across the codebase and aligns with general software engineering
conventions.

## Standard: Correct Spelling

All spelling must be correct.

Positive example: "requirements"

Negative example: "requirments"

This maintains professionalism and prevents miscommunication.

## Standard: No Comma After Abbreviations

Commas must be omitted after abbreviations.

Positive example: "etc."

Negative example: "etc.,"

This reduces visual clutter.

## Standard: No Ampersands

The word "and" must be used instead of the ampersand symbol "&".

Positive example: "Standards and Practices"

Negative example: "Standards & Practices"

This adheres to formal writing conventions and improves accessibility.
