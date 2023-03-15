#!/bin/bash
# Tools for working with the shell itself.

# Constants
REMOTE_SETUP_SCRIPT=https://raw.githubusercontent.com/jack-bradshaw/monorepo/main/shell/setup_local.sh

# Restarts the current shell instance.
reload_shell() {
  clear
  source $HOME/.zshrc
}

# Clears the local shell setup and installs it again.
reinstall_shell() {
  bash <(curl -s $REMOTE_SETUP_SCRIPT)
  reload_shell
}
