#!/bin/bash

# Constants.
REMOTE_HEAD="https://github.com/jack-bradshaw/monorepo"
LOCAL_HEAD=$HOME/HEAD

# Clones HEAD and sources the shell from the cloned repo.
source_head_shell() {
  rm -rf $LOCAL_HEAD
  git clone --depth 1 --quiet $REMOTE_HEAD $LOCAL_HEAD >/dev/null
  source $LOCAL_HEAD/shell/setup_session.sh
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
