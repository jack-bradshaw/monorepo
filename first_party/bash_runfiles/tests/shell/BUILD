load("@bazel_bats//:rules.bzl", "bats_test")
load("//first_party/bash_runfiles:shell.bzl", "sh_binary_with_runfiles", "sh_library_with_runfiles", "sh_test_with_runfiles")

# Tests for sh_library_with_runfiles

sh_library_with_runfiles(
    name = "lib_no_directive",
    testonly = True,
    srcs = ["input_no_directive.sh"],
)

sh_library_with_runfiles(
    name = "lib_one_directive",
    testonly = True,
    srcs = ["input_one_directive.sh"],
)

sh_library_with_runfiles(
    name = "lib_multiple_directives",
    testonly = True,
    srcs = ["input_multiple_directives.sh"],
)

sh_library_with_runfiles(
    name = "lib_malformed_directive",
    testonly = True,
    srcs = ["input_malformed_directive.sh"],
)

bats_test(
    name = "sh_library_with_runfiles_test",
    srcs = ["sh_library_with_runfiles_test.bats"],
    data = [
        "golden_malformed_directive.sh",
        "golden_multiple_directives.sh",
        "golden_no_directive.sh",
        "golden_one_directive.sh",
        ":lib_malformed_directive",
        ":lib_multiple_directives",
        ":lib_no_directive",
        ":lib_one_directive",
        "//first_party/bash_runfiles/tests:verify_matches_golden",
        "@bazel_tools//tools/bash/runfiles",
    ],
)

# Tests for sh_binary_with_runfiles

sh_binary_with_runfiles(
    name = "bin_no_directive",
    testonly = True,
    srcs = ["input_no_directive.sh"],
    suffix = "_binary",
)

sh_binary_with_runfiles(
    name = "bin_one_directive",
    testonly = True,
    srcs = ["input_one_directive.sh"],
    suffix = "_binary",
)

sh_binary_with_runfiles(
    name = "bin_multiple_directives",
    testonly = True,
    srcs = ["input_multiple_directives.sh"],
    suffix = "_binary",
)

sh_binary_with_runfiles(
    name = "bin_malformed_directive",
    testonly = True,
    srcs = ["input_malformed_directive.sh"],
    suffix = "_binary",
)

bats_test(
    name = "sh_binary_with_runfiles_test",
    srcs = ["sh_binary_with_runfiles_test.bats"],
    data = [
        "golden_malformed_directive.sh",
        "golden_multiple_directives.sh",
        "golden_no_directive.sh",
        "golden_one_directive.sh",
        ":bin_malformed_directive",
        ":bin_multiple_directives",
        ":bin_no_directive",
        ":bin_one_directive",
        "//first_party/bash_runfiles/tests:verify_matches_golden",
        "@bazel_tools//tools/bash/runfiles",
    ],
)

# Tests for sh_test_with_runfiles

sh_test_with_runfiles(
    name = "test_no_directive",
    testonly = True,
    srcs = ["input_no_directive.sh"],
    suffix = "_test",
    tags = ["manual"],
)

sh_test_with_runfiles(
    name = "test_one_directive",
    testonly = True,
    srcs = ["input_one_directive.sh"],
    suffix = "_test",
    tags = ["manual"],
)

sh_test_with_runfiles(
    name = "test_multiple_directives",
    testonly = True,
    srcs = ["input_multiple_directives.sh"],
    suffix = "_test",
    tags = ["manual"],
)

sh_test_with_runfiles(
    name = "test_malformed_directive",
    testonly = True,
    srcs = ["input_malformed_directive.sh"],
    suffix = "_test",
    tags = ["manual"],
)

bats_test(
    name = "sh_test_with_runfiles_test",
    srcs = ["sh_test_with_runfiles_test.bats"],
    data = [
        "golden_malformed_directive.sh",
        "golden_multiple_directives.sh",
        "golden_no_directive.sh",
        "golden_one_directive.sh",
        ":test_malformed_directive",
        ":test_multiple_directives",
        ":test_no_directive",
        ":test_one_directive",
        "//first_party/bash_runfiles/tests:verify_matches_golden",
        "@bazel_tools//tools/bash/runfiles",
    ],
)
