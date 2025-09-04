load("@local//:value.bzl", "VALUE")
load("//toolchain/triplet:TripletInfo.bzl", "TripletInfo")

visibility("//toolchain/...")

TRIPLET = TripletInfo(VALUE)
SELECTS = (
    "{}-{}-{}".format(TRIPLET.cpu, TRIPLET.os.value, TRIPLET.libc.value),
    "{}-{}-{}".format(TRIPLET.cpu, TRIPLET.os.value, TRIPLET.libc.kind),
    "{}-{}-{}".format(TRIPLET.cpu, TRIPLET.os.kind, TRIPLET.libc.value),
    "{}-{}-{}".format(TRIPLET.cpu, TRIPLET.os.kind, TRIPLET.libc.kind),
    "{}-{}".format(TRIPLET.cpu, TRIPLET.os.value),
    "{}-{}".format(TRIPLET.cpu, TRIPLET.os.kind),
    "{}-{}".format(TRIPLET.os.value, TRIPLET.libc.value),
    "{}-{}".format(TRIPLET.os.value, TRIPLET.libc.kind),
    "{}-{}".format(TRIPLET.os.kind, TRIPLET.libc.value),
    "{}-{}".format(TRIPLET.os.kind, TRIPLET.libc.kind),
    "{}-{}".format(TRIPLET.cpu, TRIPLET.libc.value),
    "{}-{}".format(TRIPLET.cpu, TRIPLET.libc.kind),
    "{}".format(TRIPLET.cpu),
    "{}".format(TRIPLET.os.value),
    "{}".format(TRIPLET.os.kind),
    "{}".format(TRIPLET.libc.value),
    "{}".format(TRIPLET.libc.kind),
    "//conditions:default",
)

def key(map, *, no_match_error = "No repository match found for `{triplet}`: {map}"):
    for select in SELECTS:
        if select in map:
            return map[select]

    fail(no_match_error.format(triplet = TRIPLET.value, map = map))

def value(map, *, no_match_error = "No repository match found for `{triplet}`: {map}"):
    return key({v: k for k, v in map.items()}, no_match_error = no_match_error)
