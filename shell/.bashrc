#!/bin/bash

move_to_head() {
  cd $HOME/src/HEAD/
  git reset --hard HEAD
  git pull
}

source_from_head() {
  source shell/setup_session.sh
}

run() {
  move_to_head
  source_from_head
}
run
