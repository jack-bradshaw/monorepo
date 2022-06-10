#!/bin/bash
# Tools for manipulating source control.

# Syncs the current branch with the remote branch.
sync() {
  pull
  push
}

# Pushes the current branch to the remote.
push() {
  git push
}

# Pulls updates on the current branch in the remote to the local branch.
pull() {
  echo $BRANCHNAME
  git fetch
  git pull origin $BRANCHNAME --depth=1 --allow-unrelated-histories
}

# Commits all stagad changes.
commit() {
  git commit
}

# Includes all local changes in staging, commits them, and pushes to remote.
qcommit() {
  addremove
  commit
  push
}

# Amends the current commit with the staged changes.
amend() {
  git commit --amend
}

# Reverts a file to local HEAD.
zap() {
  git checkout HEAD -- $1
}

# Stages all changes.
addremove() {
  git add -A
}

# Creates a shallow clone of the remote repository in the current directory.
clone() {
  git clone --depth 1 $SRC_REMOTE_PUBLIC
}

# Creates a deep clone of the remote repository in the current directory.
clone_deep() {
  git clone $SRC_REMOTE__PUBLIC
}
