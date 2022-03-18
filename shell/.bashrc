#!/bin/bash

REMOTE="https://github.com/matthewbradshaw-io/monorepo"
move_to_head() {
  cd $HOME/src
  rm -rf HEAD
  git clone --quiet $REMOTE $HOME/src/HEAD >/dev/null
  cd HEAD
}

source_from_head() {
  source shell/setup_session.sh
}

run() {
  move_to_head
  source_from_head
}
run
