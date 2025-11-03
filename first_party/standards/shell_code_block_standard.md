# Shell Code Block Standard

The standard for shell code blocks in this repository.

## Scope

This standard applies to all shell code blocks in this repository, including in-code documentation
(e.g. Javadoc) and all ancillary documentation (e.g. READMEs and standards). The recursive contents
of [third_party](/third_party) are excepted as the contents are populated automatically from third
parties. The requirements apply regardless of the enclosing file's language (e.g. a Shell code block
in a Java file must conform to the Shell requirements).

## Requirements

Code blocks in shell-scripting languages (e.g. bash and zsh) adhere to the following requirements:

- CLI line prefixes must be elided (e.g. `$` and `>`).
- Shebang lines must be elided unless the shebang is the subject of the example.

For example: Test data is available via `curl --output data.txt "https://example.com/data.txt"`.
