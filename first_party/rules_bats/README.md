# Rules Bats

Infrastructure for running [Bats](https://github.com/bats-core/bats-core) tests with Bazel.

## Usage

This package provides the `bats_test` macro for defining Bats test targets.

Step 1: Create a `.bats` file with tests.

```bash
#!/usr/bin/env bats

setup() {
    source "path/to/script_to_test.sh"
}

@test "function returns expected value" {
    run my_function "input"
    [ "$status" -eq 0 ]
    [ "$output" = "expected output" ]
}

@test "function fails on invalid input" {
    run my_function ""
    [ "$status" -eq 1 ]
}
```

Step 2: Define the test target in a BUILD file.

```starlark
load("//first_party/rules_bats:defs.bzl", "bats_test")

sh_library(
    name = "my_script",
    srcs = ["my_script.sh"],
)

bats_test(
    name = "my_script_test",
    srcs = ["my_script_test.bats"],
    data = [
        ":my_script",
    ],
)
```

Step 3: Run the test with `bazel test //path/to/package:my_script_test`.

View [script tests](/first_party/site/scripts/tests) for a real production example.

## Architecture

The infrastructure is comprised of two parts: the `defs.bzl` file which provides a macro for
defining `bats_test` targets, and the `runner.sh` script which invokes bats at runtime and passes
the result to Bazel. The macro packages the test sources into runfiles with the core bats script (
and arbitrary test data files), then selects the `runner.sh` as the entry point to hook the tests
into Bazel. The [core bats infrastructure](/third_party/bats-core) is contained in third party to
separate it from these first party components.
