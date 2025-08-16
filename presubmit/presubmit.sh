#!/bin/bash

run_presubmit() {
  echo "Starting presubmit"

  local repo_root=$(git rev-parse --show-toplevel)
  local checks=("build.sh" "test.sh")

  for check in "${checks[@]}"; do
    source "$repo_root/presubmit/$check"
    local check_result=$?
    if [[ $check_result -ne 0 ]]; then
      return 1
    fi
  done

  echo "All presubmit checks passed! 🐥"
}
