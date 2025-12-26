# Code Block Standard

The standard for code blocks in this repository.

## Definitions

A code block is a section of documentation that is written in a programming language or scripting
language.

## Scope

All code blocks in this repository must conform to this standard, and it applies regardless of the
enclosing language (e.g. Java code blocks in Markdown files must conform); however, exceptions to
individual requirements apply automatically when the enclosing language prevents conformance; 
furthermore, the contents of [third_party](/third_party) are exempt as they originate from
other repositories.

## Multiline

Multiline code blocks must adhere to the following requirements:

- Multiline code blocks must contain a language specification.
- Multiline code blocks must be syntactically correct.
- Multiline code blocks may contain comments.
- Multiline code blocks may elide imports.
- Multiline code blocks may elide package declarations.

For example:

```java
import java.io.InputStream;

class MyClass {
  private final InputStream input;

  public MyClass(final InputStream input) {
    this.input = input;
  }
}
```

These requirements ensure humans and machines can treat code blocks as authorative and correct
sources, while allowing the author to choose the appropriate level of detail.

## Inline

Inline code blocks must adhere to the following requirements:

- Inline code blocks must not contain a language specification.
- Inline code blocks must be part of a sentence.
- Inline code blocks may contain pseudocode and code fragments.

For example: The `MyClass` constructor accepts an `InputStream`.

These requirements ensure documentation remains legible, while allowing the author to 

## Shell

Shell-script code blocks (e.g. bash and zsh) must adhere to the following requirements:

- CLI line prefixes must be elided (e.g. `$` and `>`).
- Shebang lines must be elided unless the shebang is the subject of the example.

Example 1: Test data is available via `curl --output data.txt "https://example.com/data.txt"`.

Example 2:

```sh
echo "Hello, World!"
exit 0
```

## Starlark

Starlark code blocks must adhere to the following requirements:

- Bazel target labels must be fully-qualified.

For example: "The foo library can be imported from `//first_party/foo:foo`".
