#!/bin/bash

REMOTE="https://github.com/matthewbradshaw-io/monorepo"

source_from_head() {
  cd $HOME
  rm -rf shell
  git clone --depth 1 --quiet $REMOTE $HOME/src/HEAD >/dev/null
  source shell/setup_session.sh
}

# Main operation. Run on source loaded. Exits early for non-interactive shells.
run() {
  # Abort if non-interactive.                                                   
  if [ -z "$PS1" ]; then                                                        
      return                                                                    
  fi  

}
run
