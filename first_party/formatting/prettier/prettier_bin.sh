#!/usr/bin/env bash

# Prettier runner script for Bazel
#
# Locates the Prettier binary in runfiles and executes it using node. All arguments provided to this
# script are forwarded directly to the Prettier CLI (after the hardcoded authoritative repository
# configuration).

{{RUNFILES_BOILERPLATE}}

# Runs prettier with plugins.
#
# Args:
#   $@: Arguments to pass to the Prettier CLI.
main() {
  runfiles_export_envvars

  # Prettier modules (plugins) are intalled as BUILD deps and located in the runfiles tree at
  # runtime, but the next command (cd) moves to the workspace root, so they become inaccesible;
  # therefore, the runfiles tree must be explicitly added to the node path.
  export NODE_PATH="$(cd "${RUNFILES_DIR:-.}" && pwd)/_main/node_modules"

  # To ensure prettier operates on the workspace itself, the pwd needs to be the workspace root.
  cd "${BUILD_WORKSPACE_DIRECTORY:-.}"

  node "$(rlocation "_main/node_modules/prettier/bin-prettier.js")" \
    --config "$(rlocation "_main/first_party/formatting/prettier/prettier.config.cjs")" \
    "$@"
}

main "$@"
