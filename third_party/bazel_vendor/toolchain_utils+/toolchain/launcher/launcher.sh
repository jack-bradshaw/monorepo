#! /usr/bin/env sh

# Strict shell
set -o errexit -o nounset

# Replacement
DIRECTORY="${0%/*}"
BASENAME="${0##*/}"
STEM="${BASENAME%.*}"

# Execute!
"${DIRECTORY}/${STEM}.sh" "${@}"
