# Journal Editing Directives

Editorial rules for the [journal documents](/first_party/site/static/content/journal) in this
package.

## Terminology

The following definitions are used throughout this document:

- Dialogue tag: A phrase attributing speech using a verb (e.g. "he said", "she asked").
- Action beat: A narrative description of a character's physical movement or expression that
  accompanies their speech (e.g. "he smiled", "she gestured", "they turned away").

## Scope

The directives in this document apply to all documents in
[journal documents](/first_party/site/static/content/journal); however, as grammar is often
ambiguous and some pieces intentionally use non-standard grammar as a system for expression,
exceptions are allowed on demand.

The following documents are explicitly exempt from all but the basic linguistic directives:

- [10 Words](/first_party/site/static/content/journal/10_words.md) due to its unconventional
  staccato style.
- [Self Aware Antagonist](/first_party/site/static/content/journal/self_aware_antagonist.md) due to
  its unconventional screenplay-like style.

## Basic Linguistic Directives

Directives applying to the fundamental language of the pieces.

### Standard: British English

All documents must use British English spelling and formatting conventions.

Positive example (British English): `"colour"`

Negative example (American English): `"color"`

This ensures a consistent authorial voice across the entire repository.

### Standard: Spelling and Grammar

All spelling and grammar must be correct, with exceptions for intentional stylistic deviations.

Positive example (correct spelling in unquoted prose): `He sat on the chair.`

Positive example (incorrect spelling in quoted dialogue):
`"Whaa da ya want?", he said with a drunken slur.`

Negative example (incorrect spelling in unquoted prose): `He satt on the chair.`

This upholds the readability and professional standard of the journal.

## Quotations and Dialogue Directives

Directives for punctuation relating to quotations and dialogue.

### Standard: Logical Quotation

Punctuation must go inside the quotation marks only if it is part of the original quoted material;
whereas, any punctuation used to structure the surrounding sentence must always go outside.

Positive example: `"Did he say that?", they asked.`

This standard strictly enforces the logic of punctuation ownership, eliminating ambiguity between
quoted text and structural prose. It treats quotations as a logical equivalent of Java string
literals, where all characters they contain are escaped from the program, while all elements they
exclude are processed as program logic. In the context of a document, this ensures the reader can
unambiguously associate punctuation within a quoted section as belonging to the source (e.g. the
speaking character, the quoted individual, an external publication, etc.), while associating
punctuation outside the quoted section as the intention of the document author.

### Practice: Standalone Dialogue

Standalone dialogue (quoted sections that form a standalone paragraph without any extraneous
content) should embed their terminal punctuation elements inside the quotation, and a trailing full
stop must not be appended after the closing quotation.

Positive example:

```text
Other text.

"No. Desire is human."

Other text.
```

Negative example (extraneous full stop):

```text
Other text.

"No. Desire is human.".

Other text.
```

Given that standalone dialogue is often multiple sentences and there is no tag immediately following
the dialogue to provide context, this ensures standalone dialogue does not appear incomplete (e.g.
"He said hello. She said hello back").

### Standard: Dialogue Introductions

Action beats or narrative lines that explicitly introduce dialogue must end with a colon (`:`).

Positive example (introductory narrative statement):

```text
The therapist smiled wryly and spoke directly:

"The office is a rental, it's meant to be a throwback to Freud, the books aren't mine."
```

Positive example (introducing written text or an internal thought):

```text
He found a letter addressed to his brothers. It read:

"Dear Brothers, I hope you're well, just kidding, I still hate you."
```

Negative example (missing colon before dialogue break):

```text
The therapist smiled wryly and spoke directly

"The office is a rental..."
```

Negative example (using a comma instead of a colon for a block introduction):

```text
The therapist smiled wryly and spoke directly,

"The office is a rental..."
```

This ensures a consistent and easily understood visual and grammatical break that links dialogue to
context (e.g. speaker, environment, tone, etc.).

### Standard: Dialogue Tags and Action Disruptions

When dialogue is followed by a dialogue tag or action beat in the same sentence, a comma must be
placed outside the closing quotation mark, and the dialogue itself must not end with punctuation
merely to maintain sentence flow; however, punctuation which modifies the presentation/meaning of
the dialogue is permitted (e.g. question marks, exclamation marks, ellipses, etc.). Regardless of
the presence or absence of final punctuation in the quoted dialogue, the comma after the closing
quotation must be present.

Positive example (punctuation inside quotations absent):

```text
"Say more", he said with curiosity.
```

Positive example (question mark present):

```text
"Say more?", he said with curiosity.
```

Negative example (full stop present):

```text
"Say more.", he said with curiosity.
```

Negative example (internal punctuation present without external punctuation):

```text
"Say more?" he said with curiosity.
```

This ensures sentence structure logically delegates commas to the enclosing sentence while
preserving meaning-altering punctuation within the quote and excluding meaning-neutral punctuation.

Exception: Fundamentally the sentence determines whether the punctuation is meaning-altering, and
there are circumstances where a full stop is valid, for example `"Enough.", he said forcefully.`.

### Standard: Interruptions

Dialogue that is abruptly cut off must be defined by a hyphen (`-`) inside the quotation marks at
the break point. The comma after the dialogue tag must still be present to comply with the Dialogue
Tags and Action Disruptions standard.

Positive example:

```text
"I- I-", they began.
```

Negative example (no hyphen despite text indicating interruption):

```text
"I", he was cut off abruptly by a loud bang nearby.
```

Negative example (no comma after quotation closure):

```text
"I-" he was cut off abruptly by a loud bang nearby.
```

This provides a uniform visual cue for interrupted speech without violating structural commas.

### Standard: Dialogue Continuation

When dialogue is suspended to provide additional context before immediately resuming, the suspension
must not use punctuation within the quotations, and the continuation must begin exactly as if the
sentence had not been interrupted (e.g. maintaining lowercase letters for the continuation).

Positive example (interruption within a continuous thought):

```text
"The rigid walls of thought, walls you and I have spent many hours
exploring, and deconstructing", he gestured between them with his hand, "hold in the truth."
```

Negative example (ellipsis implies a trailing off of thought):

```text
"The rigid walls of thought...", he gestured between them with his hand, "hold in the truth."
```

Negative example (hyphen implies an interrupted thought):

```text
"The rigid walls of thought-", he gestured between them with his hand, "hold in the truth."
```

This correctly enforces continuous grammatical logic across split quotations. The absence of a
hyphen distinguishes dialogue that is suspended to provide context from dialogue that itself
contains an interruption. It's the difference between a narrator adding information while the
character continues uninterrupted, and a character actively stopping speaking due to the narrative.

### Practice: Dialogue Punctuation Flexibility

Standard English grammar rules may be relaxed in quotations to capture the speaker's voice, provided
the sentence itself remains readable/comprehensible, with exceptions for sentences that are
explicitly incomprehensible as part of the dialogue itself.

Positive example (comma splices to indicate speaking style):

```text
"I want chocolate, I want chocolate, I want chocolate!", he said.
```

Positive example (sensible nonsense in context):

```text
"I a-. aaldsj;  xx hello world! ERROR he-- aosdj2-...,,;", the computer said with sizzling circuits.
```

Negative example (nonsensical full stops without meaning or purpose):

```text
"I want, chocol.ate, I want chocolate . ... . ."
```

This grants the author stylistic freedom to emulate natural cadence and introduce narrative nonsense
while prohibiting non-functional linguistic breakdown.

## Formatting Directives

Directives controlling document rendering and spacing.

### Standard: Hard Wrap Backslashes

Authors must use a trailing `\` on lines that need an explicit hard wrap before the next one. This
is only necessary on hard wraps between sequential lines, and it must be omitted when the following
line is entirely blank.

Positive example (slashes in all the necessary places and none of the unnecessary places):

```markdown
"Line1\
Line2

Line3"
```

Negative example (slash missing after line 1 and unnecessarily present after line 2):

```markdown
"Line1 Line2\

Line3"
```

This is necessary to prevent the autoformatter from automatically joining sequential lines and
destroying poetic or stylistic spacing.

## Structure Directives

Directives governing the high-level structural components of the files.

### Standard: Level 1 Headings Prohibition

Journal pieces must never include a level 1 heading (`#`). This ensures the rendered page displays
correctly, as the level 1 heading is dynamically inserted into HTML by the site generator based on
the JSON metadata.

## Tense Directives

Directives controlling the passage of time in the journal.

### Practice: Sectional Tense Consistency

Pieces may use any tense so long as each piece is consistent internally and with all other pieces in
the same series.

Positive example: All pieces within _The Sagely Sage_ series use past tense.

This maintains atmospheric continuity without locking the entire journal into a single unified
style.

Note: Tense is highly complex and there are circumstances where it legitimately varies within a
piece, such as when stating universal truths (often referred to as the timeless or universal
present) or when characters speak within quoted dialogue, as their speech operates independently of
the narrative's overarching tense.
