#!/bin/bash
# Tools for working with bash.

# Reloads the .bashrc file.
reinit() {
  clear
  source $HOME/.bashrc
}

SETUP_SCRIPT=https://raw.githubusercontent.com/matthewbradshaw-io/monorepo/main/shell/setup_local.sh

# Clears the entire local shell environment and rebuilds it from remote HEAD.
rebuild() {
  bash <(curl -s $SETUP_SCRIPT)
  reinit
}
