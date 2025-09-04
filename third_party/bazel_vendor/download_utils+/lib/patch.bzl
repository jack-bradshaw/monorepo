visibility("//download/...")

ATTRS = {
    "patches": attr.label_list(
        doc = "Patches to apply to the repository after creation.",
        allow_files = [".patch"],
    ),
}

def patch(rctx):
    """
    A mixin for `download` repository rules that patches files after download.

    Args:
        rctx: The download repository context.

    Returns:
        A map of canonical arguments
    """
    for patch in rctx.attr.patches:
        rctx.patch(patch, strip = 1)

    return {}
