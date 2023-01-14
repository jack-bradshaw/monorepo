#!/bin/bash
# Configures the local shell environment.

# Constants.
REMOTE_HEAD="https://github.com/jackxbradshaw/monorepo"
LOCAL_HEAD=$HOME/HEAD

# Creates a shallow clone of HEAD locally.
update_head() {
  rm -rf $LOCAL_HEAD
  mkdir -p $LOCAL_HEAD
  git clone --depth 1 $REMOTE_HEAD $LOCAL_HEAD
}

# Configures the shell to use ZSH.
setup_zsh() {
  rm -rf $HOME/.zshrc
  cp $LOCAL/shell/.zshrc $HOME

  touch $HOME/.zshlocal

  rm -rf $HOME/.zsh/puretheme
  git clone https://github.com/sindresorhus/pure $HOME/.zsh/puretheme
}

# Exports the .gitconfig file to local home.
export_gitconfig() {
  rm -rf $HOME/.gitconfig
  cp $LOCAL/shell/.gitconfig $HOME
}

# Exports the .vimrc file to local home
export_vimrc() {
  rm -rf $HOME/.vimrc
  cp $LOCAL/shell/.vimrc $HOME
}

# Decrypts and exports ssh keys to local home.
export_ssh_keys() {
  rm -rf $HOME/.ssh
  mkdir $HOME/.ssh
  
  read -r -p "Decrypt SSH keys? (Y/N) " response
    if [ "$response" == "Y" ] || [ "$response" == "y" ]; then
      gpg --output $HOME/.ssh/jackbradshaw --decrypt \
          $LOCAL/shell/.ssh/jackbradshaw_private.gpg
      chmod 400 $HOME/.ssh/jackbradshaw # self-read-only
    fi
}

# Decrypts and exports gpg keys to local home.
export_gpg_keys() {
  rm -rf $HOME/.gpgkeys
  mkdir $HOME/.gpgkeys

  read -r -p "Decrypt GPG keys? (Y/N) " response 
  if [ "$response" == "Y" ] || [ "$response" == "y" ]; then
    gpg --output $HOME/.gpgkeys/jackbradshaw --decrypt \
        $LOCAL/shell/.gpgkeys/jackbradshaw_private.gpg
    chmod 400 $HOME/.gpgkeys/jackbradshaw # self-read-only
  fi
}

# Main function. Run on source loaded.
run() {
  update_head

  setup_zsh

  export_gitconfig
  export_vimrc
  export_ssh_keys
  export_gpg_keys
}
run
