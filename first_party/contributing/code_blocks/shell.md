# Shell Code Block Directives

Directives for shell code blocks in this repository.

## Definitions

A shell code block is a code block written in a shell scripting language (e.g. bash, zsh).

## Scope

All Shell code blocks in this repository must conform to these directives, and they apply regardless
of the enclosing language (e.g. Shell code blocks in Java files must conform); however, granular
exceptions to individual requirements apply automatically when the enclosing language prevents
conformance; furthermore, the contents of [third_party](/third_party) are explicitly exempt, as they
originate from external sources.

## Standard: No Prefixes

CLI line prefixes must be elided (e.g. `$` and `>`).

Example: "Test data is available via `curl --output data.txt "https://example.com/data.txt"`."

This ensures code blocks can be copied into a terminal without modification.

## Practice: No Shebangs

Shebang lines should be omitted unless absolutely necessary.

Example:

```sh
echo "Hello, World!"
exit 0
```

This reduces unnecessary documentation clutter.
