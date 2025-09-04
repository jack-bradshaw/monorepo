visibility("//download/...")

ATTRS = {
    "build": attr.label(
        doc = """The template for the `BUILD.bazel` file.

Receives a `{{srcs}}` substitution value for exposing the downloaded resources.
""",
        default = ":BUILD.tmpl.bazel",
    ),
}

def build(rctx):
    """
    A mixin for `download` repository rules that patches files after download.

    Args:
        rctx: The download repository context.

    Returns:
        A map of canonical arguments
    """
    substitutions = {}

    if hasattr(rctx.attr, "output"):
        substitutions["{{srcs}}"] = repr([rctx.attr.output])
    elif hasattr(rctx.attr, "srcs"):
        substitutions["{{srcs}}"] = repr(rctx.attr.srcs)
    else:
        fail("No supported attributes")

    rctx.template("BUILD.bazel", rctx.attr.build, substitutions, executable = False)

    return {}
