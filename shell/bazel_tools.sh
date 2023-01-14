#!/bin/bash
# Tools for working with Google Blaze/Bazel.

alias blaze=bazel

# Builds all targets in a directory.
# Arg 1: The directory to build, as an absolute or local path from with respect
#     to the workspace root.
bba() {
  bazel build $1:all
}

# Builds all targets in a directory and all recursively contained directories.
# Arg 1: The root directory for recursion, as an absolute path with respect to
#     the workspace root
bbr() {
  bazel build $1...
}

# Builds buildifier and invokes it. Must be run from within the monorepo.
buildifier() {
  bazel run //:buildifier
}
