#!/bin/bash

# Tools for manipulating source control.

src() {
  cd $SRC_HOME
}

sync() {
  push
  pull
  push
}

push() {
  git push
}

pull() {
  git pull 
}

commit() {
  git commit
}

qcommit() {
  addremove
  cmt
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
