#!/bin/bash
# Configures the local shell environment.

REMOTE="https://github.com/matthewbradshaw-io/monorepo"
LOCAL=/tmp/monorepo

# Creates a temporary directory that can be abandonned when no longer required.
make_temp_dir() {
  rm -rf $LOCAL
  mkdir -p $LOCAL
  cd $LOCAL
}

# Deletes the temporary directory if one exists.
delete_temp_dir() {
  rm -rf $LOCAL
}

# Downloads a copy of the monorepo from the remote into the current directory.
clone_mono_repo() {
  git clone --depth 1 $REMOTE .
}

# Exports the .bashrc file from the monoreop into the local home directory.
export_bashrc() {
  rm -rf $HOME/.bashrc
  cp shell/.bashrc $HOME
}

# Exports the .gitconfig file from the monorepo into the local home directory.
export_gitconfig() {
  rm -rf $HOME/.gitconfig
  cp shell/.gitconfig $HOME
}

# Exports the .vimrc file from the monorepo into the local home directory.
export_vimrc() {
  rm -rf $HOME/.vimrc
  cp shell/.vimrc $HOME
}

# Decrypts and exports ssh keys from the monorepo into the local home directory.
export_ssh_keys() {
  rm -rf $HOME/.ssh
  mkdir $HOME/.ssh
  
  read -r -p "Decrypt SSH keys? (Y/N) " response
    if [ "$response" == "Y" ] || [ "$response" == "y" ]; then
      gpg --output $HOME/.ssh/matthewbradshaw --decrypt shell/.ssh/matthewbradshaw_private.gpg
      chmod 400 $HOME/.ssh/matthewbradshaw # self-read-only
    fi
}

# Decrypts and exports gpg keys from the monorepo into the local home directory.
export_gpg_keys() {
  rm -rf $HOME/.gpgkeys
  mkdir $HOME/.gpgkeys

  read -r -p "Decrypt GPG keys? (Y/N) " response 
  if [ "$response" == "Y" ] || [ "$response" == "y" ]; then
    gpg --output $HOME/.gpgkeys/matthewbradshaw --decrypt shell/.gpgkeys/matthewbradshaw_private.gpg
    chmod 400 $HOME/.gpgkeys/matthewbradshaw # self-read-only
  fi
}

# Creates the necessary directories for local source control.
inflate_codelab() {
  rm -rf $HOME/src/HEAD
  mkdir -p $HOME/src/HEAD
  git clone --depth 1 $REMOTE $HOME/src/HEAD

  rm -rf $HOME/src/FORGE
  mkdir -p $HOME/src/FORGE
  git clone --depth 1 $REMOTE $HOME/src/FORGE

  mkdir -p $HOME/src/WORKSPACES
}

# Main function. Run on source loaded.
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
