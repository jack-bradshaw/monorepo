#!/bin/bash

# Tools for working with bash.

reload_shell() {
  clear
  source $HOME/.bashrc
}

SETUP_SCRIPT=https://raw.githubusercontent.com/matthewbradshaw-io/monorepo/main/shell/setup_local.sh
revolve_shell() {
  bash <(curl -s $SETUP_SCRIPT)
  reload_shell
}
