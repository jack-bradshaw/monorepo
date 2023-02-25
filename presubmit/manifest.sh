#!/bin/bash
# Root manifest for other presubmits.
# Should be run from the root directory.

bash presubmit/all_targets_build.sh
bash presubmit/all_tests_pass.sh
bash presubmit/all_kotlin_files_formatted.sh
