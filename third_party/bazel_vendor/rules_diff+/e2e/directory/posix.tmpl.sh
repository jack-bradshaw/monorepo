#! /usr/bin/env sh

# Strict shell
set -o errexit
set -o nounset

# Bazel substitutions
CP="{{cp}}"
MKDIR="{{mkdir}}"
readonly CP MKDIR

# Parse arguments
DST="${1?Must provide a destination directory}"
readonly DST
shift

# Copy the files
for SRC in "${@}"; do
  test "${SRC#*/}" != "${SRC}"
  "${MKDIR}" -p "${DST}/${SRC%/*}"
  "${CP}" "${SRC}" "${DST}/${SRC}"
done
