# Bazel Bash Runfiles

This package contains modified files from from the
[Bazel](https://github.com/bazelbuild/bazel) repository. The [boilerplate logic](/third_party/bazel_bash_runfiles/src/runfiles.bash) was extracted from
[runfiles.bash](https://github.com/bazelbuild/bazel/blob/master/tools/bash/runfiles/runfiles.bash) (lines 1-20), with `set -uo pipefail` and `set +e` added locally for robustness. The
[LICENSE](/third_party/bazel_bash_runfiles/LICENSE) was retained from original source verbatim.

