#!/bin/bash

# Tools for working with secrets.

unlock_ssh() {                                                                  
  eval `ssh-agent`                                                              
  ssh-add $HOME/.ssh/matthewbradshaw
}

unlock_gpg() {
  eval `gpg-agent`
  gpg --import $HOME/.gpgkeys/matthewbradshaw
}
