#!/bin/bash
# Per-session setup.

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

SCRIPT_PATH=$HOME/HEAD/shell

# Sources all scripts from the HEAD workspace.
source_subscripts() {
  source $SCRIPT_PATH/path.sh

  source $SCRIPT_PATH/dir_tweaks.sh
  source $SCRIPT_PATH/tmux_tweaks.sh
  
  source $SCRIPT_PATH/bash_tools.sh
  source $SCRIPT_PATH/bazel_tools.sh
  source $SCRIPT_PATH/java_tools.sh
  source $SCRIPT_PATH/misc_prefs.sh
  source $SCRIPT_PATH/secret_tools.sh
  source $SCRIPT_PATH/sys_tools.sh
}

# Main function. Run on source loaded.
run() {
  export BASH_SILENCE_DEPRECATION_WARNING=1

  start_tmux
  source_subscripts
  system_report

  cd $HOME

  echo "-----------------------------------------------------"
  echo "                    Welcome, Sir.                    "
  echo "-----------------------------------------------------"
}
run
