#!/usr/bin/env bash

set -o pipefail -o errexit -o nounset

exec "$JS_BINARY__NPM_BINARY" "$@"
