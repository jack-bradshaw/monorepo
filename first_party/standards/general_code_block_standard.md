# General Code Block Standard

The standard for code blocks in this repository.

## Scope

All code blocks in this repository must conform to this standard, and it applies regardless of the
enclosing language (e.g. Java code blocks in Markdown files must conform); however, granular
exceptions to individual requirements apply automatically when the enclosing language prevents
conformance. The recursive contents of [third_party](/third_party) are excepted as the contents are
sourced from third parties.

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

## Inline

Inline code blocks must adhere to the following requirements:

- Inline code blocks must not contain a language specification.
- Inline code blocks must be part of a sentence.
- Inline code blocks may contain pseudocode and incomplete code fragments.

For example: The `MyClass` constructor accepts an `InputStream`.
