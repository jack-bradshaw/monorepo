visibility("//download/...")

ATTRS = {
    "links": attr.string_dict(
        doc = """Creates links in the downloaded repository as `{"<target>": "<link>"}`

```py
download_archive(
    links = {
        "etc/test/fixture.txt": "fixture.txt",
    },
)
```

Depending on the platform, either symbolic or hard links are created.
""",
    ),
}

def links(rctx):
    """
    A mixin for `download` repository rules that creates links.

    Args:
        rctx: The download repository context.

    Returns:
        A map of canonical arguments
    """
    for target, link in rctx.attr.links.items():
        rctx.symlink(target, link)

    return {}
