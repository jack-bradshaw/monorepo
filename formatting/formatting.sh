#!/bin/bash
# Convenience functions for invoking formatting tools.

# Runs ktfmt.
# The first argument is the directory to target (recursively). If no arg is provided then the entire
# repository is targeted.
ktfmt() {
  REPO_ROOT=$(git rev-parse --show-toplevel)
  KTFMT_BIN=$REPO_ROOT/third_party/facebook/ktfmt/ktfmt-0.46-jar-with-dependencies.jar
  java -jar $KTFMT_BIN "${1:-$REPO_ROOT}"
}

# Runs buildifier across the entire repository with check mode.
buildifier() {
  bazel run //formatting:buildifier_fix
}
