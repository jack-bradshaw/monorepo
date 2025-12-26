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
external sources provided where appropriate. Contributors are expected to provide technically sound
information.

Positive Example: "The `FooSort` sorts a list of N items in O(NlogN) time (link to analysis)."

Negative Example: "The `FooSort` is beautiful and fast."

However, subjectivity in the form of hard-earned experience and sensible reasoning is invaluable.
Facts alone often lack the context to be useful, and eschewing the unique experiences/perspectives
of contributors does not create a supportive environment. By incorporating subjectivity,
contributors are empowered to do their best work, while avoiding the unnecessary overhead of dry
academic proofs for everything.

Positive Example: "`FooSort` provides better average case performance than `MergeSort` (link to
analysis); however, it has a higher worst case performance, and the risk of production failure is
difficult to justify given the stability requirements."

Negative Example: "Production deployment of `FooSort` is not scalable, but there is no paper to back
this claim; therefore, it remains in production."

The pitfall of subjectivity is expecting trust from others without justification. No-one can be
expected to place blind faith in others, and authority derives from openness to criticism, not
social status or history. In pursuit of a balance between objectivity and subjectivity, contributors
are encouraged to justify their claims by sharing the experience that led to their conclusions and
combining it with reasoning.

Positive Example: "`FooSort` was used in production at company X because it offers acceptable time
complexity on paper, but customer data was often unstructured in practice, thus leading to `O(N^2)`
performance when deployed at scale. Ultimately, the team at company X found that `FooSort` was
inferior to a standard `MergeSort`, and switched to the latter." (Explains the experience and
failure mode)

Negative Example: "`FooSort` is not as fast as `MergeSort` in practice, trust me on this." (No
context)

True wisdom comes from combining accurate facts with meaningful experience. Contributors should
strike a balance between objectivity (proof and evidence), subjectivity (perspective from
experience), and wisdom (their synthesis), such that both pure formal proof and blind faith are
unnecessary.

## Guideline: Balance Minimalism with Sufficiency

Contributors must include enough information to avoid the hidden costs of missing documentation. The
cost of storing a few extra paragraphs (kilobytes) is far lower than the cost of missing context
(e.g. hours of debugging, production failures, reinventing the wheel).

Positive Example: "The `delegate` method forwards the request to the upstream service and handles
any timeout exceptions by retrying at total of five times." (Sufficient context)

Negative Example: "Delegate handles requests." (Insufficient)

However, including unnecessary detail defeats the purpose of documentation by obscuring the truth
under unnecessary detail.

Negative Example: "The `FooProvider` internally utilizes a `ConcurrentHashMap` instantiated with an
initial capacity of 16 and a load factor of 0.75 to store the cached values, which are themselves
wrapped in a custom `CacheEntry` object containing a 64-bit timestamp derived from
`System.nanoTime()` for high-precision expiry calculations, and are evictable using a
least-recently-used policy implemented via a synchronized doubly-linked list that is traversed in
reverse order during cleanup cycles triggered by a `ScheduledExecutorService` running on a separate
daemon thread. Side note: It commonly fails on Android devices due to OOM errors." (Excessive
Detail, hides critical OOM failure note)

Furthermore, documentation should omit obvious information to avoid visual clutter and insults to
the reader's intelligence. If a component's name is perfectly self-explanatory, it may not need
documentation at all.

Positive Example: "`FooProvider` provides a unique `Foo` instance on every call." (new information)

Negative Example: "`NetworkScanner` scans the network." (Redundant, nothing new)

Contributors should strike a balance between minimalism and sufficiency by ensuring meaningful
information is documented, without burying the truth under excessive detail, and omitting
self-explanatory aspects. Ensure that every sentence adds value in the context of the broader
document.

## Guideline: Balance Past, Present, and Future

Documentation should primarily describe the present state of the repository. Describing future plans
can lead to inaccuracy when plans change, and describing past implementations adds irrelevant
details. Keeping the focus on the present state of the repository anchors documentation to the
subject.

Positive Example: "The framework has various concurrency issues." (Acknowledges limitation)

Negative Example: "In Q4 we plan to rewrite the parser." (Specific future plan)

However, historical context is often vital for explaining why the current state exists. Use it to
justify design decisions or warn against repeating past mistakes, but avoid documenting features
that no longer exist purely for posterity.

Positive Example: "A custom parser is used because the standard library parser caused performance
regressions in v1.2." (Useful)

Negative Example: "There were issues with the parser but they were fixed." (Vague/Unhelpful)

Negative Example: "Past implementations of this method used the standards math library."
(Irrelevant/Unhelpful)

Furthermore, discussing future possibilities can explain current limitations. Include this context
when it clarifies design constraints known during development, but avoid unfounded speculation.
Including a link to an issue tracker when referencing future work helps readers learn about the
current status.

Positive Example: "This method is synchronous. Asynchronous support may be added in a future release
(tracked by issue #123)." (Explains current limitation with tracking reference)

Negative Example: "We will make this method asynchronous in v2.0." (Promise without context)

Contributors should strike a balance between stability (documenting what exists) and context
(providing necessary background). Use the past to justify the present, but avoid future speculation
that may never come to pass.

## Guideline: Balance Precision with Accessibility

Contributors should act as guides, helping readers understand complex systems by writing with
precision. Technical accuracy is the foundation of documentation, and statements must be technically
correct to prevent misunderstanding.

Positive Example: "The `FooProvider` creates a new instance of `Foo` on every call." (Precise)

Negative Example: "`FooProvider` makes objects when you need them." (Too vague)

However, precision should not come at the expense of accessibility. Documentation must remain
understandable to general engineers. Avoid overly academic phrasing, obscure jargon, or casual slang
that might alienate readers from different backgrounds.

Positive Example: "Kotlin compiles to the JVM and other platforms (e.g. JS, native)." (Accessible)

Negative Example: "Kotlin's compilation process culminates in the generation of bytecode for the
Java Virtual Machine and analogous artifacts for alternative execution substrates." (Overly
Academic)

Contributors should strike a balance between precision (being technically correct) and accessibility
(being easy to understand). Use standard industry terms where specific meaning is required, but
explain them if they are niche; be as simple as possible, but no simpler.

## Guideline: Collective Identity

Documentation should reflect the collective nature of the repository. Avoid the use of singular
pronouns like "I" or "my", which imply individual ownership of shared code. Instead, focus on the
artifact itself or the collective effort.

Positive Example: "Foo was created to make working with Bar easier." (Focus on artifact)

Negative Example: "I created Foo to make it easier for me to use Bar." (Individual focus)

However, avoid using the first-person plural "we" unless the group being referred to is strictly
defined by the context. "We" is often ambiguous (does it mean the authors, the team, the company, or
the industry?) and may imply greater agreement than is actually intended. Instead use explicit
nouns.

Positive Example: "The Kernel Team decided to remove the drivers." (Specific)

Negative Example: "We decided to remove the drivers." (Ambiguous)

Contributors should strike a balance between communal voice (acknowledging the team) and unambiguous
attribution (giving credit/responsibility where due). Use specific nouns (e.g., "The Maintainers",
"The Compiler Team") to reflect communal ownership without the ambiguity of vague pronouns.

## Guideline: Balance Declarative and Imperative Tone

Documentation about the system itself is often clearest when it describes the state and behaviors of
the system, or its requirements and expectations, rather than issuing commands to the reader. This
focuses attention on the artifact itself preventing the documentation from becoming a list of
chores.

Positive Example: "`FooProvider` must be configured prior to use." (Declarative requirement)

Negative Example: "You must configure the `FooProvider` before you use it." (Imperative command)

However, complex procedures, tutorials, and troubleshooting guides differ from reference
documentation in that their primary goal is user guidance. In these contexts, imperative
instructions are often clearer and more efficient than declarative descriptions.

Positive Example: "Generate the artefacts by running `bazel run //foo:bar`." (Direct Command)

Negative Example: "The `//foo:bar` target generates artefacts." (Burden on reader to interpret
actions)

Contributors should strike a balance between description (defining the system) and prescription
(assigning tasks to the reader) by considering the ultimate use of the information. Consider whether
the context calls for information about the system or guidance to the reader, and adjust the voice
accordingly. Usually, reference documentation (e.g. Javadoc etc.) benefits from a descriptive tone,
while guides and tutorials (e.g. READMEs etc.) benefit from imperative tone.

## Guideline: Balance Confidence and Humility

Contributors should write with confidence about facts they are sure of. Avoid non-committal hedging
(words like "probably", "possibly", "usually") when describing established system behaviors.

Positive Example: "`FooProvider` is not thread-safe." (Confident fact)

Negative Example: "`FooProvider` is probably not thread-safe and there is a small risk of runtime
failure." (Unnecessary hedging)

However, contributors should demonstrate humility by acknowledging the limits of their knowledge. Do
not feign certainty where it does not exist, and if a behavior is flaky, dependent on unknown
factors, or poorly understood, document that uncertainty.

Positive Example: "The behavior of `FooProvider` under high memory pressure is undefined and has not
been tested." (Honest uncertainty)

Negative Example: "`FooProvider` will definitely work under high memory pressure." (False certainty)

Contributors should strike a balance between confidence (establishing authority) and humility
(acknowledging limits) by prioritizing honesty. Do not minimize risks or benefits in an attempt to
soften the message, but do not understate known issues or advantages. Determine your actual level of
certainty, and match your tone to it.

## Guideline: Balance Judgment with Neutrality

Contributors should avoid judgmental language that attacks systems, platforms, or contributors.
Focus on factual descriptions of limitations or issues without assigning blame or making sweeping
negative characterizations.

Positive Example: "The `Foo` implementation does not work on Android." (Neutral, factual)

Negative Example: "Android is such a crap operating system it cannot even run this, also who wrote
this shit?" (Judgmental, personal attack on platform and people)

However, judgmentalism is distinct from judgment. The latter involves identifying real issues,
limitations, and constraints based on evidence, reasoning and experience. Contributors should
exercise judgment to provide accurate/useful information and improve quality.

Positive Example: "Android devices circa 2012 have memory limitations that prevent this
implementation from working as intended." (Sound judgment, specific, informative)

Negative Example: "This implementation works everywhere." (Lacks judgment, ignores real constraints)

Contributors should strike a balance between neutrality (avoiding personal attacks and blame) and
judgment (identifying real issues and constraints). Use sound reasoning to assess systems and
implementations without resorting to judgmentalism.

## Guideline: Balance Consideration with Respect

Contributors should demonstrate consideration for the reader by acknowledging potential difficulties
and offering supportive guidance. Use softening language to recognize that certain concepts or
behaviors might be challenging without presuming the reader's experience.

Positive Example: "This behavior might be unclear at first." (Acknowledges possibility)

Positive Example: "If the configuration seems complex, consider starting with the default settings."
(Offers option)

Negative Example: "This behavior is obvious." (Dismissive, lacks consideration)

However, avoid making assertions about the reader's mental, physical, or other capabilities. This
respects psychological boundaries, gives the reader the benefit of the doubt, and creates an
inclusive environment.

Positive Example: "This object does not completely implement the interface contract and may throw
errors in unexpected ways. Further documentation is provided in the troubleshooting guide."
(Neutral, respectful)

Negative Example: "You will probably find this object confusing, and you should probably read the
troubleshooting guide when you get stuck." (Makes assertions about the reader's capabilities and
experience)

Contributors should strike a balance between consideration (acknowledging potential challenges) and
respect (avoiding presumptions about the reader) by offering possibilities and options rather than
making definitive statements about the reader.

## Guideline: Balance Abstraction with Clarity

Documentation should omit narrative and colorful language that obscures the truth under layers of
conceptual indirection and abstraction. Focus on clear, direct descriptions of system behavior.

Positive Example: "`Foo` processes `Bar` objects using a FIFO queue, and idles while the queue is
empty." (Clear, direct)

Negative Example: "`Foo` works like a conveyor belt, the first `Bar` on the belt gets processed,
then the next, and so forth, until the belt is empty, and the operator goes to lunch when there's
nothing to do." (Narrative metaphor obscures meaning)

However, abstraction is a meaningful and useful part of software design, and should be documented
when it hides unnecessary implementation details. Contributors should use appropriate abstractions
that match the system's design.

Positive Example: "`Foo` uses the pub-sub pattern to process `Bar` objects asynchronously."
(Appropriate abstraction)

Negative Example: "`Foo` wraps each `Bar` object as a Node, each with a link to the next/previous
node, and holds a reference to a node (marked root node), then recursively follows the links between
nodes to process them in the order they were added. All this happens on a background thread for
performance." (Too much implementation detail)

Contributors should strike a balance between abstraction (hiding implementation details) and clarity
(avoiding narrative weight). Use abstraction when it explains the implementation clearly and matches
system abstractions, but avoid abstractions that detract from the meaning and add unnecessary
narrative or conceptual weight.

## Guideline: Balance Documentation Proximity With Size

Documentation should be distributed close to the artifacts it relates to, so that readers are not
overwhelmed by monolithic documents, and can easily find the information they need without searching
through large volumes of text.

Positive Example: A README in a package describing that package's functionality. (Colocated)

Negative Example: The root repository README containing documentation for the entire monorepo.
(Centralized, overwhelming)

However, spreading documentation between too many documents obscures meaning and leaves readers
without a cohesive picture. Some separation between documents and artifacts is acceptable to provide
context and overview.

Positive Example: A root README that introduces the repository structure and philosophy, with
smaller granular READMEs for individual packages. (Balanced distribution)

Negative Example: A README in every package with details of that package only, with no overview or
context about how packages relate. (Too fragmented)

Contributors should strike a balance between colocation (keeping information close to where it
matters) and cohesion (providing a unified picture). Avoid large centralized documents that contain
everything, but also avoid excessive decomposition that fragments understanding.

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
