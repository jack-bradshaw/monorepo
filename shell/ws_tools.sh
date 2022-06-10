#!/bin/bash
# Tools for manipulating workspaces.

# Moves to the source directory.
src() {
  cd $SRC_HOME
}

# Updates the HEAD workspace to match the remote HEAD.
clean_head() {
  cd $SRC_HEAD

  git reset --hard
  git clean -fxd
  git pull
}

# Moves to the HEAD workspace.
head() {
  cd $SRC_HEAD
  clean_head
}

# Updates the forge workspace to match the remote HEAD.
clean_forge() {
  cd $SRC_FORGE
  git reset --hard
  git clean --fxd
  git pull
}

# Moves to the forge workspace.
forge() {
  cd $SRC_FORGE
}

# Updates the forge workspace to match the remote HEAD and builds the contents.
reforge() {
  cd $SRC_FORGE
  git pull
  blaze build //...
}

# Moves to a workspace.
# Arg 1: The workspace name.
ws() {
  cd $SRC_WS
  cd $1
  export BRANCHNAME=$(<.ws)
}

# Lists all workspaces.
wss() {
  cd $SRC_WS
  ls
}

# Makes a new workspace. If a workspace with this name already exists in the
# remote, use pullws instead.
# Arg 1: The workspace name.
mkws() {
  mkws_at_main $1
  populate_ws_file $1
  branchname=$1_working
  git fetch
  git checkout -b $branchname
  git push origin $branchname
  git push --set-upstream origin $branchname
}

# Makes a new workspace. If a workspace with this name does not already exist
# in the remote, use mkws instead.
# Arg 1: The workspace name.
pullws() {
  mkws_at_main $1
  populate_ws_file $1
  branchname=$1_working
  git fetch origin $branchname:$branchname
}

# Makes a new workspace and syncs to main at HEAD. Moves into the workspace.
# Arg 1: The workspace name.
mkws_at_main() {
  cd $SRC_WS
  mkdir $1
  cd $1

  git clone --depth=1 $SRC_REMOTE_PUBLIC .
  git remote set-url origin git@github.com:matthewbradshaw-io/monorepo.git

  cd $SRC_WS
  cd $1
}

# Creates a .ws file with the contents set to the working branch name.
# Arg 1: The workspace name.
populate_ws_file() {
  touch .ws
  echo $1_working > .ws
}

# Deletes the workspace locally and deletes the working branch in the remote.
# Arg 1: The workspace name.
rmws() {
  ws $1
  git status
  read -r -p "Are you sure you want to delete? (Y/N) " response
  if [ "$response" == "Y" ] || [ "$response" == "y" ]; then
    git push origin --delete $1_working
    nuke $HOME/src/WORKSPACES/$1
    wss
  fi
}
