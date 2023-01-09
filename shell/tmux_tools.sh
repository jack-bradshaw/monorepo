#!/bin/bash
# Tweaks to make tmux more convenient.

# Ends all other tmux sessions.
kill_other_tmux() {
  THIS_ID=$(tmux display-message -p '#S')
  tmux kill-session -at $THIS_ID
}
