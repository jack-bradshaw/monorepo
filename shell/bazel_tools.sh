#!/bin/bash

# Tools for working with Google Blaze/Bazel.

alias blaze=bazel

bba() {
  bazel build $1:all
}

bbr() {
  bazel build $1...
}

buildifier() {
  bazel run //:buildifier
}

install_bazel() {
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
