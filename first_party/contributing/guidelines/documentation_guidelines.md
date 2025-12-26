# Documentation Guidelines

Guidelines for writing effective documentation in this repository.

## Philosophy

Documentation should not just describe *what* the software does, but *why* it exists and *how* to use it effectively. While we maintain a professional standard, we value wisdom and context over sterile objectivity.

## Guidelines

### Provide Context and Purpose
Don't write in a vacuum. Explain the problem the code solves.
- **Good**: "The `UserAuth` service handles OAuth2 flows to ensure secure third-party integration."
- **Bad**: "`UserAuth` is a class that calls the OAuth2 endpoint."

### Content Scope
- **Stay Focused**: Documentation should focus on the package/directory it resides in. Avoid documenting parent or sibling packages.
- **Avoid Duplication**: Link to existing documentation rather than repeating it.

### Professional Tone
Maintain a professional, neutral tone. Avoid slang, but do not sacrifice clarity for the sake of excessive formality.
- **Voice**: Impersonal is still preferred for consistency (avoid "I think", "You should"), but "We recommend" or passive voice where appropriate is acceptable if it aids understanding.

### Wisdom and Insight
Objectivity is important for facts, but do not shy away from sharing professional judgment.
- It is acceptable to describe "best practices", "common pitfalls", or "recommended patterns" even if they are subjective, provided they are grounded in experience and are presented with appropriate context.

### State and Lifecycle
Software changes. It is acceptable to document the current status of a component (e.g., "Experimental", "Legacy", "Planned").
- While documentation should primarily describe the *current* state, references to future plans or past decisions are permitted when they provide necessary context for the user.

### Structure
- Start with a high-level summary.
- Use clear headings.
- Include examples where possible.
