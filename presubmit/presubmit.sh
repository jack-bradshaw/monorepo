#!/bin/bash

run_presubmit() {
  # Fail the oveall presubmit if even one check fails.
  set -e

  repo_root=$(git rev-parse --show-toplevel)

  checks=(format_build.sh format_kotlin.sh build.sh test.sh)
  for check in "${checks[@]}";
  do
    bash $repo_root/presubmit/$check
  done

  echo "All presubmit checks passed! 🐥"
}
