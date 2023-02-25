#!/bin/bash
# Presubmit to make sure all targets build.
# Success condition: All targets build successfully.
# Failure condition: At least one target fails to build.

bazel build //...
