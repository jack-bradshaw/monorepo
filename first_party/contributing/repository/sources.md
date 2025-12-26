# Source File Directives

Directives for source files in this repository.

## Scope

All source files in this repository must conform to these directives; however, the contents of
[third_party](/third_party) are explicitly exempt, as they originate from external sources.

## Standard: Spaces not Tabs

Source files must use spaces for indentation instead of tabs. Exceptions apply where tabs are
required by the compiler; however, the conventions of other codebases and communities are
insufficient to override the requirement.

Example:

```kotlin
class Foo {
  fun bar() {
    // Indented with spaces
  }
}
```

While there are convincing arguments on both sides, it is better to have a single convention and
stick to it than debate the topic forever. The convention was chosen based on prior experience of
the main contributors at Google, where spaces are used instead of tabs.

Note: In many languages, use of tabs is automatically enforced by the
[formatter](/first_party/formatting); however, automatic enforcement is not universal.
