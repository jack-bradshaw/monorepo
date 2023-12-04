#!/bin/bash

REPO_ROOT=$(git rev-parse --show-toplevel)
cd $REPO_ROOT

# Fail presubmit if even one check fails.
set -e

bash presubmit/format_build.sh
bash presubmit/format_kotlin.sh
bash presubmit/build.sh
bash presubmit/test.sh
