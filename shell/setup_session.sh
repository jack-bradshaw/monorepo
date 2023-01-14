#!/bin/bash
# Per-session setup.

# Constants
SCRIPT_PATH=$HOME/HEAD/shell

# Sources all scripts from the HEAD workspace.
source_subscripts() {
  source $SCRIPT_PATH/path.sh
  
  source $SCRIPT_PATH/bash_tools.sh
  source $SCRIPT_PATH/bazel_tools.sh
  source $SCRIPT_PATH/dir_tools.sh
  source $SCRIPT_PATH/git_tools.sh
  source $SCRIPT_PATH/misc_prefs.sh
  source $SCRIPT_PATH/secret_tools.sh
  source $SCRIPT_PATH/sw_installation_tools.sh
  source $SCRIPT_PATH/tmux_tools.sh
}

source_local_zshrc() {
  source $HOME/.zshrclocalonly
}

use_pure_theme() {
  fpath+=($HOME/.zsh/puretheme)
  autoload -U promptinit; promptinit
  prompt pure
}

# Main function. Run on source loaded.
run() {
  use_pure_theme
  source_subscripts
  source_local_zshrc

  cd $HOME

  echo "Shell initialized."
}
run
