#!/bin/bash

REMOTE="https://github.com/matthewbradshaw-io/monorepo"

move_to_head() {
  echo "Refreshing HEAD"
  cd $HOME/src
  rm -rf HEAD
  git clone --quiet $REMOTE $HOME/src/HEAD >/dev/null
  cd HEAD
}

source_from_head() {
  source shell/setup_session.sh
}

run() {
  # Abort if non-interactive.                                                   
  if [ -z "$PS1" ]; then                                                        
      return                                                                    
  fi  

  move_to_head
  source_from_head
}
run
