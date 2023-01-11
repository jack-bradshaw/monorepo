#!/bin/bash
# Tools for working with git.

# Reverts a modified file.
# Arg 1: The file to revert, as a local or absolute path.
git_revert() {
  git checkout HEAD -- $1
}

# Configures the monorepo for SSH.
git_setup_ssh() {
  git remote set-url origin https://github.com/jackxbradshaw/monorepo.git
}
