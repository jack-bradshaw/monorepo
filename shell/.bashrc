#!/bin/bash

REMOTE = http://github.com/matthewbradshaw-io/monorepo
move_to_head() {
  cd $HOME/src/HEAD
  nuke *
  git clone $REMOTE $HOME/src/HEAD >/dev/null
}

source_from_head() {
  source shell/setup_session.sh
}

run() {
  move_to_head
  source_from_head
}
run
