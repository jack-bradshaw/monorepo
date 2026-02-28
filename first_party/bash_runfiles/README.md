# Bash Runfiles

Macros for injecting the Bazel runfiles setup logic into shell scripts (and other files) at build
time.

## Overview

Setting up Bazel runfiles in shell scripts (and related files) is a common task; however, it
requires copy-pasting the standard Bazel runfiles bootstrapping logic into every script, and calling
a common library is not possible because the library would need to be loaded using runfiles, which
creates a circular problem. This package provides a viable alternative: build-time preprocessing to
inject the setup boilerplate into the source file directly.

## Usage

The macros provided by this package replace every instance of `{{RUNFILES_BOILERPLATE}}` with the
standard Bazel runfiles boilerplate. Consumers can use this to inject the runfiles setup boilerplate
into scripts without copy-pasting it manually, and then safely use `runfiles`, `RUNFILES_DIR`, and
`RUNFILES_MANIFEST_FILE` variables as usual. Macros are provided for shell rules, bats test rules,
and filegroups. Examples are provided below for each.

### Shell Rules

In `my_script.sh`:

```bash
#!/bin/bash
{{RUNFILES_BOILERPLATE}}

MY_BIN=$(rlocation "_main/path/to/binary")
ls "$RUNFILES_DIR"
echo "$RUNFILES_MANIFEST_FILE"
```

In `BUILD`:

```starlark
load("//first_party/bash_runfiles:shell.bzl", "sh_library_with_runfiles")

sh_library_with_runfiles(
    name = "my_script",
    srcs = ["my_script.sh"],
)

sh_binary(
    name = "my_bin",
    deps = [":my_script"],
)
```

The `my_script` macro generates a target named `my_script`, which provides the processed file as
`my_script_with_runfiles.sh`. The `sh_binary_with_runfiles` and `sh_test_with_runfiles` macros
follow this pattern and work similarly.

### Bats Test Rules

In `my_test.bats`:

```bash
#!/bin/bash

setup() {
  {{RUNFILES_BOILERPLATE}}
}

@test "test_my_script" {
  $(rlocation "_main/path/to/binary")
  [ "$output" = "expected_output" ]
}
```

In `BUILD`:

```starlark
load("//first_party/bash_runfiles:bats_test.bzl", "bats_test_with_runfiles")

bats_test_with_runfiles(
    name = "my_test",
    srcs = ["my_test.bats"],
)
```

The `my_test` macro generates a target named `my_test`, which provides the processed file as
`my_test_with_runfiles.bats`, and can be run with `bazel test :my_test`.

### Generic Rules

In `my_file.txt`:

```text
{{RUNFILES_BOILERPLATE}}

I'm a regular text file short and stout. Here is my handle here is my stdout.
```

In `BUILD`:

```starlark
load("//first_party/bash_runfiles:filegroup.bzl", "filegroup_with_runfiles")

filegroup_with_runfiles(
    name = "my_file",
    srcs = ["my_file.txt"],
)
```

The `my_file` macro generates a target named `my_file`, which provides the processed file as
`my_file_with_runfiles.txt`.

## Caveats

The following constraints apply to these macros:

1. All occurrences of the preprocessor directive (`{{RUNFILES_BOILERPLATE}}`) are replaced,
   including repeated occurrences; however, the directive must match exactly (i.e. no whitespace or
   other characters between the `{{` and `}}` bounds); furtheremore, characters outside the
   directive are ignored (e.g. `foo{{RUNFILES_BOILERPLATE}}bar` results in the boilerplate being
   placed directly between `foo` and `bar` without additional spaces or newlines).
1. The macros generate targets with names that match the macro's `name` argument; whereas, the names
   of the generated files inherit from their `srcs` with a suffix (e.g. `foo.sh` becomes
   `foo_with_runfiles.sh`). The exact suffix can be controlled with the macro's `suffix` argument,
   and is appended before file extensions.
1. The generated targets include the Bash runfiles dependency (`@bazel_tools//tools/bash/runfiles`)
   to ensure the artifact executes without errors. For cases where the downstream consumer must
   control the dependencies, this can be disabled by passing `False` to the macro's
   `include_runfiles_dep` argument.

## Implementation

The macros substitute the Bash runfiles initialization boilerplate located at
[boilerplate.sh](/third_party/bazel_bash_runfiles/boilerplate.sh). It was adapted from the
[official Bazel Bash runfiles library](https://github.com/bazelbuild/bazel/blob/master/tools/bash/runfiles/runfiles.bash).
