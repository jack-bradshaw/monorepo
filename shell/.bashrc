#!/bin/bash

REMOTE="https://github.com/matthewbradshaw-io/monorepo"

# Clones HEAD and sources the shell from the cloned repo.
source_head_shell() {
  rm -rf $HOME/HEAD
  git clone --depth 1 --quiet $REMOTE $HOME/HEAD >/dev/null
  source $HOME/HEAD/shell/setup_session.sh
}

# Main operation. Run on source loaded. Exits early for non-interactive shells.
run() {
  # Abort if non-interactive.                                                   
  if [ -z "$PS1" ]; then                                                        
      return                                                                    
  fi  

  source_head_shell
}
run
