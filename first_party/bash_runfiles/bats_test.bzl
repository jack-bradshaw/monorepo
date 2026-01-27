load("@bazel_bats//:rules.bzl", "bats_test")
load("//first_party/bash_runfiles:inject_runfiles.bzl", "inject_runfiles")
load("//first_party/bash_runfiles:validate_sources.bzl", "validate_sources")

def bats_test_with_runfiles(name, srcs = [], src = None, suffix = None, include_runfiles_dep = True, **kwargs):
    """Generates a bats_test target with runfiles boilerplate injected into the sources.

    Args:
        name: The name of the generated target, string, required.
        srcs: A list of .bats files to inject into, list of label strings, optional, must be .bats files, defaults to [].
        src: A single .bats file to inject into, label string, optional, defaults to None.
        suffix: The suffix to append to each output file (before extension), string, optional, defaults to
            "_with_runfiles".
        include_runfiles_dep: Whether to include the standard Bazel runfiles dependency, bool, optional,
            defaults to True.
        **kwargs: Arbitrary arguments to forward to the generated rule, dictionary, optional.

    Exactly one of `srcs` and `src` must be provided. The macro fails when neither are provided and
    when both are provided. An empty `srcs` list counts as not being provided.
    """

    validate_sources(src, srcs, "bats")
    merged_srcs = srcs if srcs else [src]

    processed_files = inject_runfiles(name, merged_srcs, suffix)

    bats_test(
        name = name,
        srcs = processed_files,
        data = kwargs.pop("data", []) +
               (["@bazel_tools//tools/bash/runfiles"] if include_runfiles_dep else []) +
               processed_files,
        **kwargs
    )
