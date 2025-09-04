load("@bazel_skylib//lib:types.bzl", "types")
load(":split.bzl", "split")

visibility("//toolchain/...")

def init(value):
    """
    Initializes a `VersionInfo` provider.

    Processes `value` into a semantic version[1]

    [1]: semver.org

    Args:
      value: A semantic version such as `1.2.3`, `0.1.0-beta.1` or `1.0.0+aefc24ef`.

    Returns:
      A mapping of keywords for the `version_info` raw constructor.
    """
    if not types.is_string(value):
        fail("`VersionInfo.value` must be a `str`: {}".format(value))

    prefix, build = split(value, "+", {
        1: lambda a: (a, None),
        2: lambda a, b: (a, b),
    })

    prefix, pre = split(prefix, "-", {
        1: lambda a: (a, None),
        2: lambda a, b: (a, b),
    })

    major, minor, patch = split(prefix, ".", {
        1: lambda a: (a, 0, 0),
        2: lambda a, b: (a, b, 0),
        3: lambda a, b, c: (a, b, c),
    })

    return {
        "value": value,
        "major": int(major),
        "minor": int(minor),
        "patch": int(patch),
        "pre": pre,
        "build": build,
    }

VersionInfo, version_info = provider(
    "A semantic version.",
    fields = ["value", "major", "minor", "patch", "pre", "build"],
    init = init,
)
