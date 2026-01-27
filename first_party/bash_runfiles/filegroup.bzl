load("//first_party/bash_runfiles:inject_runfiles.bzl", "inject_runfiles")
load("//first_party/bash_runfiles:validate_sources.bzl", "validate_sources")

def filegroup_with_runfiles(name, srcs = [], src = None, suffix = None, **kwargs):
    """Generates a filegroup target with runfiles boilerplate injected into the sources.

    Args:
        name: The name of the generated target, string, required.
        srcs: A list of files to inject into, list of labels, optional, defaults to [].
        src: A single file to inject into, label, optional, defaults to None.
        suffix: The suffix to append to each output file (before extension), string, optional, defaults to
            "_with_runfiles".
        **kwargs: Arbitrary arguments to forward to the generated rule, dictionary, optional.

    Exactly one of `srcs` and `src` must be provided. The macro fails when neither are provided and
    when both are provided. An empty `srcs` list counts as not being provided.
    """

    validate_sources(src, srcs)
    merged_srcs = srcs if srcs else [src]

    processed_files = inject_runfiles(
        name = name,
        srcs = merged_srcs,
        suffix = suffix,
        visibility = kwargs.get("visibility"),
    )

    native.filegroup(
        name = name,
        srcs = processed_files,
        **kwargs
    )
