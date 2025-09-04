load("@bazel_skylib//lib:types.bzl", "types")
load(":VersionInfo.bzl", "VersionInfo")

visibility("//toolchain/...")

def init(value):
    """
    Initializes a `VersionedInfo` provider.

    Processes `value` into a `kind` and semantic version.

    Args:
      value: A part of a full triplet which can be dot-separated such as `gnu.2.19`

    Returns:
      A mapping of keywords for the `versioned_info` raw constructor.
    """
    if not types.is_string(value):
        fail("`VersionedInfo.value` must be a `str`: {}".format(value))

    parts = value.split(".")
    kind = parts[0]
    version = ".".join(parts[1:]) or None

    if version:
        version = VersionInfo(version)

    return {
        "value": value,
        "kind": kind,
        "version": version,
    }

VersionedInfo, versioned_info = provider(
    "A versioned triplet part.",
    fields = ["value", "kind", "version"],
    init = init,
)
