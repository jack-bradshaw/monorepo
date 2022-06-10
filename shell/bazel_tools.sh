#!/bin/bash
# Tools for working with Google Blaze/Bazel.

# Blaze is the internal name for Bazel. This makes muscle memory more useful.
alias blaze=bazel

# Builds all targets in a directory.
# Arg 1: The absolute or local path to the BUILD file containing the targets
# to build.
bba() {
  bazel build $1:all
}

# Builds all targets in a directory and the recursively contained directories.
# Arg 1: The absolute path of the directory to begin the resursive build at.
bbr() {
  bazel build $1...
}

# Builds buildifier and invokes it in the current directory.
buildifier() {
  bazel run //:buildifier
}
