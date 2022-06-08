#!/bin/bash

# Tools for manipulating source control.

src() {
  cd $SRC_HOME
}

sync() {
  pull
  push

  git switch main
  pull
  git switch $BRANCHNAME

  git rebase main $BRANCHNAME
}

push() {
  git push
}

pull() {
  git fetch
  git pull origin $BRANCHNAME --depth=1 --allow-unrelated-histories
}

commit() {
  git commit
}

qcommit() {
  addremove
  commit
  push
}

amend() {
  git commit --amend
}

zap() {
  git checkout HEAD -- $1
}

addremove() {
  git add -A
}

clone() {
  git clone --depth 1 $SRC_REMOTE_PUBLIC
}

clone_deep() {
  git clone $SRC_REMOTE__PUBLIC
}
