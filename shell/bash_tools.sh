#!/bin/bash
# Tools for working with bash.

# Constants
REMOTE_SETUP_SCRIPT=https://raw.githubusercontent.com/matthewbradshaw-io/monorepo/main/shell/setup_local.sh

# Reloads the .bashrc file.
reinit_shell() {
  clear
  source $HOME/.bashrc
}

# Clears the entire local shell environment and rebuilds it from remote HEAD.
rebuild_shell() {
  bash <(curl -s $REMOTE_SETUP_SCRIPT)
  reinit_shell
}
