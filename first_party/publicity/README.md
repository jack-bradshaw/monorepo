# Publicity Automation

Infrastructure for managing build visibility.

## Overview

First-party properties generally contain a mix of public targets and private targets. Usually, the
public targets are the APIs and implementations that other packages can use, while the private
targets are the internal implementation details that should not be shared broadly. There are varying
grades of publicity beyond the binary public/private, though, and not every first-party property
needs to be completely public or private, with some benefiting from more nuanced visibility. This
package recognizes four discrete levels:

1. Public Visibility: Available to the entire repository and the general public.
1. Internal Visibility: Available to the entire repository, but not the general public.
1. Restricted Visibility: Available to a specific allowlist of first-party packages.
1. Quarantined Visibility: Not available to other first-party packages (own subpackages only).

These levels are the publicity of a package, and the publicity framework supports consistent
publicity management across repositories via a standardized API and conformance system. It helps you
correctly define your desired publicity in a central location and reference it across your
subpackages, so it can be updated and managed in a central location. If some targets require
different visibility, you can still use a custom value for them while using the standard value
elsewhere. Overall, this system relieves you of the burden of manual visibility management while
clearly communicating to contributors and consumers how they should approach your code.

## Definitions

The term "First-party property" is used extensively in this framework with specific meaning.
Monorepos often contain many unrelated or loosely coupled packages, and a first-party property is
simply a top-level package in your repository. Publicity requires you to define the first-party
root, which is the point in your repository where your independent projects branch off. This point
is arbitrary and is up to you. It could be the literal root directory, it could be a deeply nested
location (e.g. `/src/java/com/foo/bar`), or it could be somewhere near the root (e.g.
`/first_party`). The framework is flexible and works so long as you can specify a single directory
as the branching point for your independent components/projects/layers.

## Usage

To use publicity in a first-party property:

1. Create a `publicity.bzl` file in the root package.
1. Define a `PUBLICITY` variable in the file by calling one of the publicity functions from
   [defs.bzl](/first_party/publicity/defs.bzl) (`public`, `internal`, `restricted`, or
   `quarantined`).
1. Reference this variable in `BUILD` files via a `load` statement.

An optional conformance test is available to automatically detect incorrect usages of publicity and
fail a CI/test run.

### Publicity Definition

Example in `//first_party/foo/publicity.bzl`:

```starlark
load("//first_party/publicity:defs.bzl", "public", "internal", "restricted", "quarantined")

# Option 1: Public Visibility
# Available to the general public and every package in your repository.
PUBLICITY = public()

# Option 2: Internal Visibility
# Available to every first-party property but unavailable to the general public.
PUBLICITY = internal()
# or PUBLICITY = internal("//some/other/first-party/root")

# Option 3: Restricted Visibility
# Available to a select set of first-party properties and unavailable to all others.
PUBLICITY = restricted(["foo", "bar"])
# or PUBLICITY = restricted(["foo", "bar"], first_party_root = "//some/other/first-party/root")

# Option 4: Quarantined Visibility
# Not available to other first-party packages (own subpackages only).
# Requires the current package name as an argument.
PUBLICITY = quarantined("//first_party/my_package")
```

Note: Indirectly calling these functions via other functions is not supported, but load aliases are
permitted (e.g. `load("//first_party/publicity:defs.bzl", foo="public"); PUBLICITY = foo()`).

### Publicity Usage

Example in `//first_party/foo/bar/baz/BUILD`:

```starlark
load("//first_party/foo:publicity.bzl", "PUBLICITY")

java_library(
    name = "baz",
    srcs = ["Baz.java"],
    visibility = PUBLICITY,
)
```

## Publicity Conformance

The conformance test can be set up by calling the `conformance_test` macro (from
[conformance.bzl](/first_party/publicity/conformance/conformance.bzl)) in the root `BUILD` file of
your repository, for example:

```starlark
load("//first_party/publicity/conformance:conformance.bzl", "conformance_test")

conformance_test(
    name = "conformance_test",
    # optionally: first_party_root = "//foo/bar",
)
```

It will check every first-party property for correct use of publicity. Adding the test to your CI
runner will prevent anyone from checking in invalid publicity usages.

Note: The conformance test presently bundles various checks into one, but some may not fit your use
case. It may be decomposed into smaller tests in the future for more granular control. Furthermore,
there are opportunities for stricter conformance which are not presently implemented.

## Caveats

A few caveats to be aware of.

### Quarantined Scoping

Every call to the `quarantined()` function requires the enclosing package to be passed in, so the
function can generate an appropriate visibility label. This is necessary because Bazel does not
provide a reliable way to know the package of the function which calls it. It provides a way to find
where the function is defined (this package) and where it is loaded into (the package that ends up
using `visibility = PUBLICITY` in a target), but not the intermediate package containing the
`publicity.bzl` file. The only known workaround is passing in the value manually. To prevent bugs,
the conformance test statically detects wrong packages and fails.

## Why "Quarantined"?

The term "quarantined" was chosen instead of "private" for two reasons:

1. "Private" in Bazel already means private to a package (no references from subpackages or ancestor
   packages), whereas "quarantined" allows references from subpackages and ancestor packages if they
   are within the same first-party property.
1. It was chosen at a time when the original author ([Jack Bradshaw](mailto:jack@jack-bradshaw.com))
   was contending with the need to isolate unrefined. experimental AI-generated code without storing
   it in a separate repository.

Quarantined effectively means the first-party property can interact with itself but not others.

## Enforcement

If your code is open source, anyone can download it and modify the visibility definitions to subvert
your settings. Publicity cannot change this, but it can help you communicate clearly so others can
make informed decisions.
