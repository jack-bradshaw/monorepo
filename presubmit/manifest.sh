#!/bin/bash
# Root manifest for other presubmits.
# Should be run from the root monorepo directory.

set -e # Fail if one check fails.

bash presubmit/all_build_files_formatted.sh
bash presubmit/all_kotlin_files_formatted.sh
bash presubmit/all_targets_build.sh
bash presubmit/all_tests_pass.sh
