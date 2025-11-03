# General Code Block Standard

The standard for code blocks in this repository.

## Scope

This standard applies to all code blocks in this repository, including in-code documentation (e.g.
Javadoc) and all ancillary documentation (e.g. READMEs and standards). The recursive contents of
[third_party](/third_party) are excepted as the contents are populated automatically from third
parties. Requirements may be ignored if they are infeasible in a particular language (e.g. in
Javadoc the language specification may be omitted).

## Multiline

Multiline code blocks adhere to the following requirements:

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

Inline code blocks adhere to the following requirements:

- Inline code blocks must not contain a language specification.
- Inline code blocks must be part of a sentence.
- Inline code blocks may contain pseudocode and incomplete code fragments.

For example: The `MyClass` constructor accepts any `InputStream` but it must be STDIN.
