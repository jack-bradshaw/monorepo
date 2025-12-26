# General Code Block Directives

Directives for general code blocks in this repository.

## Definitions

A code block is a section of documentation that is written in a programming language or scripting
language.

## Scope

All code blocks in this repository must conform to these directives, including code blocks in pure
documentation (e.g. README files) and code blocks in source files (e.g. code blocks in Javadoc);
however, exceptions to individual requirements apply automatically when the enclosing language
prevents conformance; furthermore, the contents of [third_party](/third_party) are explicitly
exempt, as they originate from external sources.

## Standard: Language Specification Required

Multiline code blocks must contain a language specification.

Example:

```java
System.out.println("Hello, world!")
```

The explicit language specification simplifies automation and tooling, thereby enabling automated
modification (e.g. refactoring) and enhanced user interfaces (e.g. syntax highlighting).

Note: Plain text code blocks use `text` as the language specification.

## Practice: Syntactically Correct

Multiline code blocks should be syntactically correct.

Example:

```java
class Main {
  public static void main(String[] args) {
    System.out.println("Hello, world!")
  }
}
```

This ensures multiline code blocks remain authoritative sources for humans and machines alike.

Note: Syntactically correct does not mean compilable; it is acceptable for a code block to elide
imports or package declarations.

## Practice: Elide Ancillary Code

Multiline code blocks should elide imports, package declarations, and other ancillary code when not
relevant to the subject.

Example:

```java
class MyClass {
  private final InputStream input;

  public MyClass(final InputStream input) {
    this.input = input;
  }
}
```

This allows the author to choose the appropriate level of detail and keeps examples focused on the
relevant concepts.

## Standard: Inline Blocks Unlabeled

Inline code blocks must not contain a language specification.

Example: "The `MyClass` constructor accepts an `InputStream`."

This ensures inline code blocks integrate naturally into prose.

## Practice: Inline Blocks in Sentences

Inline code blocks should be part of a complete sentence rather than a standalone fragment.

Example: "The `MyClass` constructor accepts an `InputStream`."

This gives inline code blocks meaning and context.
