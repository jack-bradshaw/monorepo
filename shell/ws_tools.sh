#!/bin/bash

# Tools for manipulating workspaces.

src() {
  cd $SRC_HOME
}

clean_head() {
  cd $SRC_HEAD

  git reset --hard
  git clean -fxd
  git pull
}

head() {
  cd $SRC_HEAD
  clean_head
}

clean_forge() {
  cd $SRC_FORGE
  git reset --hard
  git clean --fxd
  git pull
}

forge() {
  cd $SRC_FORGE
}

reforge() {
  cd $SRC_FORGE
  git pull
  blaze build //...
}

ws() {
  cd $SRC_WS
  cd $1
  export $BRANCHNAME=$(<.ws)
}

wss() {
  cd $SRC_WS
  ls
}

mkws() {
  cd $SRC_WS
  mkdir $1
  cd $1

  git clone --depth 1 $SRC_REMOTE_PUBLIC .
  git remote set-url origin git@github.com:matthewbradshaw-io/monorepo.git

  ws $1

  branchname=$1_working

  touch .ws
  echo $branchname > .ws
  
  git fetch
  git checkout -b $branchname
  git pull origin $branchname
  git push origin $branchname
  git push --set-upstream origin $branchname
  git switch $branchname
}

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
