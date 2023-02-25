#!/bin/bash
# Presubmit to make sure all targets build.
# Success condition: All targets build.
# Failure condition: At least one target fails to build.

bazel build //...
