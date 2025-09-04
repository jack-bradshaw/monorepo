#!/usr/bin/env bash
set -o errexit -o nounset -o pipefail
if [ ! -f $1 ]; then exit 42; fi
