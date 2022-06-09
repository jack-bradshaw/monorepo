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

# Downloads and installs Bazel from apt.
install_bazel_for_linux() {
  sudo apt install apt-transport-https curl gnupg
  curl -fsSL https://bazel.build/bazel-release.pub.gpg \
      | gpg --dearmor > bazel.gpg
  sudo mv bazel.gpg /etc/apt/trusted.gpg.d/
  echo "deb [arch=amd64] \
      https://storage.googleapis.com/bazel-apt stable jdk1.8" \
      | sudo tee /etc/apt/sources.list.d/bazel.list

  sudo apt update && sudo apt install bazel
  sudo apt update && sudo apt full-upgrade
}
