load("@rules_shell//shell:sh_binary.bzl", "sh_binary")
load("@rules_shell//shell:sh_library.bzl", "sh_library")
load("@rules_shell//shell:sh_test.bzl", "sh_test")
load("//first_party/bash_runfiles:inject_runfiles.bzl", "inject_runfiles")
load("//first_party/bash_runfiles:validate_sources.bzl", "validate_sources")

def sh_library_with_runfiles(name, srcs = [], src = None, suffix = None, include_runfiles_dep = True, **kwargs):
    """Generates a sh_library target with runfiles boilerplate injected into the sources.

    Args:
        name: The name of the generated target, string, required.
        srcs: A list of .sh files to inject into, list of labels, optional, must be .sh files, defaults to [].
        src: A single .sh file to inject into, label, optional, defaults to None.
        suffix: The suffix to append to each output file (before extension), string, optional, defaults to
            "_with_runfiles".
        include_runfiles_dep: Whether to include the standard Bazel runfiles dependency, bool, optional,
            defaults to True.
        **kwargs: Arbitrary arguments to forward to the generated rule, dictionary, optional.

    Exactly one of `srcs` and `src` must be provided. The macro fails when neither are provided and
    when both are provided. An empty `srcs` list counts as not being provided.
    """

    validate_sources(src, srcs, "sh")
    merged_srcs = srcs if srcs else [src]

    processed_files = inject_runfiles(name, merged_srcs, suffix, visibility = kwargs.get("visibility"))

    deps = kwargs.pop("deps", [])
    if include_runfiles_dep:
        deps = deps + ["@bazel_tools//tools/bash/runfiles"]

    sh_library(
        name = name,
        srcs = processed_files,
        deps = deps,
        data = kwargs.pop("data", []) + processed_files,
        **kwargs
    )

def sh_binary_with_runfiles(name, srcs = [], src = None, suffix = None, include_runfiles_dep = True, **kwargs):
    """Generates a sh_binary target with runfiles boilerplate injected into the sources.

    Args:
        name: The name of the generated target, string, required.
        srcs: A list of .sh files to inject into, list of labels, optional, must be .sh files, defaults to [].
        src: A single .sh file to inject into, label, optional, defaults to None.
        suffix: The suffix to append to each output file (before extension), string, optional, defaults to
            "_with_runfiles".
        include_runfiles_dep: Whether to include the standard Bazel runfiles dependency, bool, optional,
            defaults to True.
        **kwargs: Arbitrary arguments to forward to the generated rule, dictionary, optional.

    Exactly one of `srcs` and `src` must be provided. The macro fails when neither are provided and
    when both are provided. An empty `srcs` list counts as not being provided.
    """

    validate_sources(src, srcs, "sh")
    merged_srcs = srcs if srcs else [src]

    processed_files = inject_runfiles(name, merged_srcs, suffix, visibility = kwargs.get("visibility"))

    deps = kwargs.pop("deps", [])
    if include_runfiles_dep:
        deps = deps + ["@bazel_tools//tools/bash/runfiles"]

    sh_binary(
        name = name,
        srcs = processed_files,
        deps = deps,
        data = kwargs.pop("data", []) + processed_files,
        **kwargs
    )

def sh_test_with_runfiles(name, srcs = [], src = None, suffix = None, include_runfiles_dep = True, **kwargs):
    """Generates a sh_test target with runfiles boilerplate injected into the sources.

    Args:
        name: The name of the generated target, string, required.
        srcs: A list of .sh files to inject into, list of labels, optional, must be .sh files, defaults to [].
        src: A single .sh file to inject into, label, optional, defaults to None.
        suffix: The suffix to append to each output file (before extension), string, optional, defaults to
            "_with_runfiles".
        include_runfiles_dep: Whether to include the standard Bazel runfiles dependency, bool, optional,
            defaults to True.
        **kwargs: Arbitrary arguments to forward to the generated rule, dictionary, optional.

    Exactly one of `srcs` and `src` must be provided. The macro fails when neither are provided and
    when both are provided. An empty `srcs` list counts as not being provided.
    """

    validate_sources(src, srcs, "sh")
    merged_srcs = srcs if srcs else [src]

    processed_files = inject_runfiles(name, merged_srcs, suffix, visibility = kwargs.get("visibility"))

    deps = kwargs.pop("deps", [])
    if include_runfiles_dep:
        deps = deps + ["@bazel_tools//tools/bash/runfiles"]

    sh_test(
        name = name,
        srcs = processed_files,
        deps = deps,
        data = kwargs.pop("data", []) + processed_files,
        **kwargs
    )
