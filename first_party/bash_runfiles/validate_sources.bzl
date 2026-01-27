def validate_sources(src = None, srcs = [], extension = None):
    """Validates that exactly one of src or srcs is provided, and validates all files have the
    expected extension if specified.

    Args:
        src: A single source label, optional, string, defaults to None.
        srcs: A list of source labels, optional, list of strings, defaults to an empty list.
        extension: The expected extension (without leading dot), string, optional.

    Exactly one of `srcs` and `src` must be provided. Fails when neither are provided, and when both
    are provided. An empty `srcs` list counts as not being provided.
    """

    if srcs and src:
        fail("Only one of 'srcs' or 'src' can be provided")
    if not srcs and not src:
        fail("One of 'srcs' or 'src' must be provided")

    if extension:
        all_sources = srcs + ([src] if src else [])
        for s in all_sources:
            if not s.endswith("." + extension):
                fail("Every source file must end with .{}, but received {}".format(extension, s))
