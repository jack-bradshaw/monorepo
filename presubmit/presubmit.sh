#!/bin/bash

REPO_ROOT=$(git rev-parse --show-toplevel)
bash $REPO_ROOT/presubmit/manifest.sh
