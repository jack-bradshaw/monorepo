#!/bin/bash

# Per-session setup.

start_tmux() {
  if command -v tmux &> /dev/null && \
      [ -n "$PS1" ] && \
      [[ ! "$TERM" =~ screen ]] && \
      [[ ! "$TERM" =~ tmux ]] && \
      [ -z "$TMUX" ]; then
    exec tmux
  fi
}

clean_head() {
  cd $SRC_HEAD 
  
  git reset --hard
  git clean -fxd
  git pull
}

source_subscripts() {
  source $HOME/src/HEAD/shell/self_constants.sh
  source $HOME/src/HEAD/shell/src_constants.sh
  
  source $HOME/src/HEAD/shell/dir_tweaks.sh
  source $HOME/src/HEAD/shell/tmux_tweaks.sh
  
  source $HOME/src/HEAD/shell/bazel_tools.sh
  source $HOME/src/HEAD/shell/misc_prefs.sh
  source $HOME/src/HEAD/shell/secret_tools.sh
  source $HOME/src/HEAD/shell/shell_tools.sh
  source $HOME/src/HEAD/shell/sys_tools.sh
  source $HOME/src/HEAD/shell/vc_tools.sh
  source $HOME/src/HEAD/shell/ws_tools.sh
}

preheat_forge() {
  nohup reforge > /dev/null 2>&1 &
}

run() {
  start_tmux

  clean_head
  source_subscripts

  preheat_forge

  system_report

  cd $HOME

  echo "-------------------------------------------------"
  echo "                Welcome, Matthew.                "
  echo "-------------------------------------------------"
}
run
