#!/bin/bash
# Per-session setup.

# Constants
SCRIPT_PATH=$HOME/HEAD/shell

# Initializes a new tmux session.
start_tmux() {
  if command -v tmux &> /dev/null && \
      [ -n "$PS1" ] && \
      [[ ! "$TERM" =~ screen ]] && \
      [[ ! "$TERM" =~ tmux ]] && \
      [ -z "$TMUX" ]; then
    exec tmux
  fi
}

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
  source $SCRIPT_PATH/sys_tools.sh
  source $SCRIPT_PATH/tmux_tools.sh
}

source_local_zshrc() {
  source $HOME/.zshrclocal
}

use_pure_theme() {
  fpath+=($HOME/.zsh/pure)
  autoload -U promptinit; promptinit
  prompt pure
}

# Main function. Run on source loaded.
run() {
  start_tmux
  source_subscripts
  source_local_zshrc
  system_report

  cd $HOME

  echo "--------------------------------------------------------------"
  echo "                        Welcome, Jack.                        "
  echo "--------------------------------------------------------------"
}
run
