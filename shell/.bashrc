#!/bin/bash

REMOTE="https://github.com/matthewbradshaw-io/monorepo"

# Syncs the HEAD workspace to remote HEAD and moves into the workspace. 
sync_head() {
  cd $HOME/src
  rm -rf HEAD
  git clone --depth 1 --quiet $REMOTE $HOME/src/HEAD >/dev/null
}

# Sources shell utilities from the HEAD workspace.
source_from_head() {
  cd $HOME/src/HEADZZ
  source shell/setup_session.sh
}

# Main operation. Run on source loaded. Exits early for non-interactive shells.
run() {
  # Abort if non-interactive.                                                   
  if [ -z "$PS1" ]; then                                                        
      return                                                                    
  fi  

  sync_head
  source_from_head
}
run
