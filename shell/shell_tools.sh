#!/bin/bash
# Tools for working with the shell itself.

# Constants
REMOTE_SETUP_SCRIPT=https://raw.githubusercontent.com/jack-bradshaw/monorepo/main/shell/setup_local.sh

# Reinitializes the current shell instance.
reinit() {
  clear
  source $HOME/.zshrc
}

# Re-runs the shell setup script.
reshell() {
  bash <(curl -s $REMOTE_SETUP_SCRIPT)
  reinit
}
