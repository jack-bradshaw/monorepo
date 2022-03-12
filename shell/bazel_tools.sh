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
