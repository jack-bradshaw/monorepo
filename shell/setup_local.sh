#!/bin/bash

# Configures the local machine for monorepo development.

REMOTE="https://github.com/matthewbradshaw-io/monorepo"
LOCAL=/tmp/monorepo

make_temp_dir() {
  rm -rf $LOCAL
  mkdir -p $LOCAL
  cd $LOCAL
}

delete_temp_dir() {
  rm -rf $LOCAL
}

clone_mono_repo() {
  git clone $REMOTE .
}

export_bashrc() {
  rm -rf $HOME/.bashrc
  cp shell/.bashrc $HOME
}

export_gitconfig() {
  rm -rf $HOME/.gitconfig
  cp shell/.gitconfig $HOME
}

export_vimrc() {
  rm -rf $HOME/.vimrc
  cp shell/.vimrc $HOME
}

export_ssh_keys() {
  rm -rf $HOME/.ssh
  mkdir $HOME/.ssh
  
  read -r -p "Decrypt SSH keys? (Y/N) " response
    if [ "$response" == "Y" ] || [ "$response" == "y" ]; then
      gpg --output $HOME/.ssh/matthewbradshaw --decrypt shell/.ssh/matthewbradshaw_private.gpg
      chmod 400 $HOME/.ssh/matthewbradshaw # self-read-only
    fi
}

export_gpg_keys() {
  rm -rf $HOME/.gpgkeys
  mkdir $HOME/.gpgkeys

  read -r -p "Decrypt GPG keys? (Y/N) " response 
  if [ "$response" == "Y" ] || [ "$response" == "y" ]; then
    gpg --output $HOME/.gpgkeys/matthewbradshaw --decrypt shell/.gpgkeys/matthewbradshaw_private.gpg
    chmod 400 $HOME/.gpgkeys/matthewbradshaw # self-read-only
  fi
}

inflate_codelab() {
  rm -rf $HOME/src/HEAD
  mkdir -p $HOME/src/HEAD
  git clone $REMOTE $HOME/src/HEAD

  rm -rf $HOME/src/FORGE
  mkdir -p $HOME/src/FORGE
  git clone $REMOTE $HOME/src/FORGE

  mkdir -p $HOME/src/WORKSPACES
}

run() {
  make_temp_dir
  clone_mono_repo

  inflate_codelab

  export_ssh_keys
  export_gpg_keys
  export_bashrc
  export_gitconfig
  export_vimrc

  delete_temp_dir
}
run
