#!/bin/bash
# Configures the local shell environment.

# Constants.
REMOTE="https://github.com/matthewbradshaw-io/monorepo"
LOCAL=$HOME/HEAD

# Creates a local directory to cache the remote files.
make_local_cache() {
  rm -rf $LOCAL
  mkdir -p $LOCAL
}

# Downloads the monorepo from the remote into the local cache.
clone_remote() {
  git clone --depth 1 $REMOTE $LOCAL
}

# Exports the .bashrc file from the local cache into the local home.
export_bashrc() {
  rm -rf $HOME/.bashrc
  cp $LOCAL/shell/.bashrc $HOME
}

# Exports the .gitconfig file from the local cache into the local home.
export_gitconfig() {
  rm -rf $HOME/.gitconfig
  cp $LOCAL/shell/.gitconfig $HOME
}

# Exports the .vimrc file from the local cache into the local home.
export_vimrc() {
  rm -rf $HOME/.vimrc
  cp $LOCAL/shell/.vimrc $HOME
}

# Decrypts and exports ssh keys from the local cache into the local home.
export_ssh_keys() {
  rm -rf $HOME/.ssh
  mkdir $HOME/.ssh
  
  read -r -p "Decrypt SSH keys? (Y/N) " response
    if [ "$response" == "Y" ] || [ "$response" == "y" ]; then
      gpg --output $HOME/.ssh/matthewbradshaw --decrypt \
          $LOCAL/shell/.ssh/matthewbradshaw_private.gpg
      chmod 400 $HOME/.ssh/matthewbradshaw # self-read-only
    fi
}

# Decrypts and exports gpg keys from the local cache into the local home.
export_gpg_keys() {
  rm -rf $HOME/.gpgkeys
  mkdir $HOME/.gpgkeys

  read -r -p "Decrypt GPG keys? (Y/N) " response 
  if [ "$response" == "Y" ] || [ "$response" == "y" ]; then
    gpg --output $HOME/.gpgkeys/matthewbradshaw --decrypt \
        $LOCAL/shell/.gpgkeys/matthewbradshaw_private.gpg
    chmod 400 $HOME/.gpgkeys/matthewbradshaw # self-read-only
  fi
}

# Main function. Run on source loaded.
run() {
  make_local_cache
  clone_remote

  export_ssh_keys
  export_gpg_keys
  export_bashrc
  export_gitconfig
  export_vimrc
}
run
