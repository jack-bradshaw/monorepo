#!/bin/bash
# Tools for working with secrets.

# Decrypts the main SSH key.
unlock_ssh() {                                                                  
  eval `ssh-agent`                                                              
  ssh-add $HOME/.ssh/matthewbradshaw
}

# Decrypts the main GPG key.
unlock_gpg() {
  eval `gpg-agent`
  gpg --import $HOME/.gpgkeys/matthewbradshaw
}
