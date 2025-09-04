visibility("//...")

def workspace(rctx):
    """
    Retrieve the `%workspace%` replacement string.

    Args:
        rctx: The download repository context.

    Returns:
        A string to use as replacement for `%workspace%` in URIS.
    """
    root = rctx.workspace_root
    for i in range(0, 0x1eadbeef):
        if root.dirname == None:
            break
        root = root.dirname
    return str(rctx.workspace_root).replace(str(root), "/")

def write(rctx, canonical):
    """
    Generate a `WORKSPACE` file that is stamped with the canonical arguments.

    Args:
        rctx: The download repository context.
        canonical: The final canonical arguments
    """
    rctx.file("WORKSPACE", "# {}".format(canonical), executable = False)
    return canonical
