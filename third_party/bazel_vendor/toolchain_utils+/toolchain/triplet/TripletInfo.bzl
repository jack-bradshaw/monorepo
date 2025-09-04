load("@bazel_skylib//lib:types.bzl", "types")
load(":VersionedInfo.bzl", "VersionedInfo")
load(":split.bzl", "split")

visibility("//toolchain/...")

def init(value):
    """
    Initializes a `TripletInfo` provider.

    Processes `value` into the constituent parts.

    Args:
      value: A machine triplet which can be kebab-separated such as `aarch64-linux-gnu.2.19`

    Returns:
      A mapping of keywords for the `triplet_info` raw constructor.
    """
    if not types.is_string(value):
        fail("`TripletInfo.value` must be a `str`: {}".format(value))

    cpu, vendor, os, libc = split(value, "-", {
        3: lambda c, o, l: (c, None, o, l),
        4: lambda c, v, o, l: (c, v, o, l),
    })

    os = VersionedInfo(os)
    libc = VersionedInfo(libc)

    constraints = [
        "cpu:{}".format(cpu),
        "os:{}".format(os.kind),
        "libc:{}".format(libc.kind),
    ]

    if os.version:
        constraints.append("os/{}:{}".format(os.kind, os.version.value))
    if libc.version:
        constraints.append("libc/{}:{}".format(libc.kind, libc.version.value))

    return {
        "value": value,
        "cpu": cpu,
        "vendor": vendor,
        "os": os,
        "libc": libc,
        "constraints": tuple([
            Label("//toolchain/constraint/{}".format(c))
            for c in constraints
        ]),
    }

TripletInfo, triplet_info = provider(
    "A machine triplet. Has the associated compatible Bazel constraints.",
    fields = ("value", "cpu", "vendor", "os", "libc", "constraints"),
    init = init,
)
