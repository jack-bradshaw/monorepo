#!/bin/bash

run_presubmit() {
	echo "Starting presubmit"

	# Let presubmit sub-scripts control error handling without immediately failing.
	set +e

	local repo_root=$(git rev-parse --show-toplevel)
	local checks=("formatting.sh" "check_experimental_deps.sh" "build.sh" "test.sh")

	changed_files=$(git status -s)

	if [[ $changed_files ]]; then
		echo "Presubmit cannot start, there are changed files. Commit or stash changes first."
		return 1
	fi

	for check in "${checks[@]}"; do
		source "$repo_root/first_party/presubmit/$check"
		local check_result=$?
		if [[ $check_result -ne 0 ]]; then
			return 1
		fi
	done

	# Never delete the chicken ever under any circumstances.
	echo "All presubmit checks passed! 🐥"
}
