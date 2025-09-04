load("@bazel_tools//tools/build_defs/repo:utils.bzl", "read_user_netrc", "use_netrc")
load(":workspace.bzl", "workspace")

visibility("//download/...")

ATTRS = {
    "urls": attr.string_list(
        doc = "URLs to download the file.",
        allow_empty = False,
        mandatory = True,
    ),
    "integrity": attr.string(
        doc = "The archive subresource integrity.",
        mandatory = False,
    ),
    "output": attr.string(
        doc = "The output filename for the downloaded file",
        mandatory = True,
    ),
    "executable": attr.bool(
        doc = "Mark the downloaded file as executable.",
        default = False,
    ),
}

def download(rctx):
    """
    A mixin for `download` repository rules.

    Args:
        rctx: The download repository context.

    Returns:
        A map of canonical arguments
    """
    netrc = read_user_netrc(rctx)
    auth = use_netrc(netrc, rctx.attr.urls, {})

    root = workspace(rctx)
    chksum = rctx.download(
        url = [u.replace("%workspace%", root) for u in rctx.attr.urls],
        integrity = rctx.attr.integrity,
        auth = auth,
        output = rctx.attr.output,
        executable = rctx.attr.executable,
    )

    return {"integrity": chksum.integrity}
