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

# Sources all scripts from the HEAD workspace.
source_subscripts() {
  source $HOME/src/HEAD/shell/path.sh
  source $HOME/src/HEAD/shell/self_constants.sh
  source $HOME/src/HEAD/shell/ws_constants.sh
  
  source $HOME/src/HEAD/shell/dir_tweaks.sh
  source $HOME/src/HEAD/shell/tmux_tweaks.sh
  
  source $HOME/src/HEAD/shell/bash_tools.sh
  source $HOME/src/HEAD/shell/bazel_tools.sh
  source $HOME/src/HEAD/shell/java_tools.sh
  source $HOME/src/HEAD/shell/misc_prefs.sh
  source $HOME/src/HEAD/shell/secret_tools.sh
  source $HOME/src/HEAD/shell/sys_tools.sh
  source $HOME/src/HEAD/shell/vc_tools.sh
  source $HOME/src/HEAD/shell/ws_tools.sh
}

# Begins building all forge artifacts in the background.
preheat_forge() {
  nohup reforge > /dev/null 2>&1 &
}

# Main function. Run on source loaded.
run() {
  start_tmux
  source_subscripts
  preheat_forge
  system_report

  cd $HOME

  echo "-------------------------------------------------"
  echo "                Welcome, Matthew.                "
  echo "-------------------------------------------------"
}
run
