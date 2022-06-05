#!/bin/bash

# Tools for working with bash.

reinit() {
  clear
  source $HOME/.bashrc
}

SETUP_SCRIPT=https://raw.githubusercontent.com/matthewbradshaw-io/monorepo/main/shell/setup_local.sh

rebuild() {
  bash <(curl -s $SETUP_SCRIPT)
  reload_shell
}
