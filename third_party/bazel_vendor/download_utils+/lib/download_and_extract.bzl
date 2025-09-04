load("@bazel_tools//tools/build_defs/repo:utils.bzl", "read_user_netrc", "use_netrc")
load(":workspace.bzl", "workspace")

visibility("//download/...")

ATTRS = {
    "urls": attr.string_list(
        doc = """URLs to download the file.

Multiple URLs can be provided to enable downloading from mirrors in the case of failure.
""",
        allow_empty = False,
        mandatory = True,
    ),
    "integrity": attr.string(
        doc = """The archive subresource integrity.

Not providing an integrity will output the calculated integrity to the terminal.

It is **strongly** recommended to provide an integrity to enable reproducible downloads.

Unsecure URLs **must** have an integrity provided.
""",
        mandatory = False,
    ),
    "strip_prefix": attr.string(
        doc = "A prefix to remove from each file in the archive.",
    ),
    "srcs": attr.string_list(
        doc = """Globs for source files in the unpacked repository.

Will be provided to the `build` template file as a `{{srcs}}` substitution.

The default template will expose the sources as a `:srcs` `filegroup` target.
""",
        default = ["**"],
    ),
}

def download_and_extract(rctx, *, extension, nested = ()):
    """
    A mixin for `download` repository rules.

    Args:
        rctx: The download repository context.
        nested: A mutually exclusive set of nested archives to unpack
        extension: The extension of the download

    Returns:
        A map of canonical arguments
    """
    netrc = read_user_netrc(rctx)
    auth = use_netrc(netrc, rctx.attr.urls, {})

    extension = extension or rctx.attr.extension

    temp = rctx.path(".extracted")

    root = workspace(rctx)
    chksum = rctx.download_and_extract(
        url = [u.replace("%workspace%", root) for u in rctx.attr.urls],
        integrity = rctx.attr.integrity,
        stripPrefix = rctx.attr.strip_prefix if not nested else "",
        auth = auth,
        output = "" if not nested else temp,
        type = extension[0:],
    )

    if not nested:
        return {"integrity": chksum.integrity}

    for child in nested:
        archive = temp.get_child(child)

        if not archive.exists:
            continue

        rctx.extract(
            archive = archive,
            stripPrefix = rctx.attr.strip_prefix,
        )

        rctx.delete(temp)

        return {"integrity": chksum.integrity}

    fail("Cannot find any `{}` archive(s) in `{}`: {}".format(", ".join(nested), temp, ", ".join(temp.readdir())))
