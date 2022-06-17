#!/bin/bash
# Tools for manipulating source control.

# Reverts a modified file.
# Arg 1: The path of the file to revert, local or absolute.
revert() {
  git checkout HEAD -- $1
}

# Creates a shallow clone of the remote repository in the current directory.
clone_shallow() {
  git clone --depth 1 $SRC_REMOTE_PUBLIC
}

# Creates a deep clone of the remote repository in the current directory.
clone_deep() {
  git clone $SRC_REMOTE_PUBLIC
}
