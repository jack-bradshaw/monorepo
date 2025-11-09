# Shell Code Block Standard

The standard for shell code blocks in this repository.

## Scope

All Shell code blocks in this repository must conform to this standard, and it applies regardless of
the enclosing language (e.g. Shell code blocks in Java files must conform); however, granular
exceptions to individual requirements apply automatically when the enclosing language prevents
conformance. The recursive contents of [third_party](/third_party) are excepted as the contents are
sourced from third parties.

## Requirements

Code blocks in shell-scripting languages (e.g. bash and zsh) must adhere to the following
requirements:

- CLI line prefixes must be elided (e.g. `$` and `>`).
- Shebang lines must be elided unless the shebang is the subject of the example.

For example: Test data is available via `curl --output data.txt "https://example.com/data.txt"`.
