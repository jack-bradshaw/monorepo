#!/bin/bash

BAZEL_OUT=$(bazel info bazel-bin)
VENDOR_DIR="third_party/bazel_vendor"

bazel fetch
bazel vendor

# TODO(jack-bradshaw): Recursively resolve all symlinks in VENDOR_DIR so they can be checked in.