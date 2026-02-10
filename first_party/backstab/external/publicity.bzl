load("//first_party/publicity:defs.bzl", "restricted")

# Reason: Implementation detail of the backstab processor.
PUBLICITY = restricted([
    "backstab/processor",
    "backstab/external",
])
