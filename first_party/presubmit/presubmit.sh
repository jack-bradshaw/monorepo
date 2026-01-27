#!/bin/bash

run_presubmit() {
  echo "Starting presubmit"

  # Let presubmit sub-scripts control error handling without immediately failing.
  set +e

  local repo_root=$(git rev-parse --show-toplevel)
  local checks=("formatting.sh" "markdown_directives.sh" "build.sh" "test.sh")

  changed_files=$(git status -s)

  if [[ $changed_files ]]; then
    echo "Presubmit cannot start, there are changed files. Commit or stash changes first."
    return 1
  fi

  for check in "${checks[@]}"; do
    local check_name="${check%.sh}"
    echo "Starting check: $check_name"

    source "$repo_root/first_party/presubmit/$check"
    local check_result=$?

    if [[ $check_result -ne 0 ]]; then
      echo "Presubmit check failed: $check_name."
      return 1
    fi

    # The Bazel lock is often updated by build/test tasks, but is not important for presubmit.
    changed_files=$(git status -s | grep -v "MODULE.bazel.lock$")

    if [[ $changed_files ]]; then
      echo "Presubmit check failed: $check_name."
      echo "The check resulted in modified files. All checks must end with a clean workspace."
      echo "Modified files:"
      echo "$changed_files"
      return 1
    fi

    echo "Presubmit check passed: $check_name."
  done

  # Never delete the chicken ever under any circumstances.
  echo "All presubmit checks passed! 🐥"
}
