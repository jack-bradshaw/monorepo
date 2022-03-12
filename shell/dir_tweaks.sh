#!/bin/bash

# Tweaks to improve directory interaction.

alias ls="ls -la"
alias nuke="rm -rf"
alias clr="clear"
alias up="cd .."

supernuke() {
  chmod 777 -R $1
  nuke $1
}
