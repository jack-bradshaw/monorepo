#!/bin/bash
# Source this script to use Bazel via Bazelisk.
# 
# Usage:
#   source third_party/bazelisk/setup.sh
#   bazel <command>
#
# The exposed command is called Bazel for convenience but is actually backed by Bazelisk.

# Returns the directory this script.
get_file_self_path() {
  local script_path="${BASH_SOURCE[0]}"
  if [[ -z "$script_path" ]]; then
    # Zsh specific: (%):-%x gives the name of the file being sourced
    script_path="${(%):-%x}"
  fi

  if [[ -z "$script_path" ]]; then
    script_path="${0}"
  fi
  
  # Resolve symlinks (Mac/BSD compatible)
  if [[ -L "$script_path" ]]; then
    local target
    target=$(readlink "$script_path")
    if [[ $target == /* ]]; then
      script_path="$target"
    else
      script_path="$(dirname "$script_path")/$target"
    fi
  fi
  
  cd "$( dirname "$script_path" )" && pwd
}

# Runs Bazel via Bazelisk.
#
# Args:
#   $@: Arguments to pass to Bazelisk.
bazel() {
  local SETUP_DIR
  SETUP_DIR="$(get_file_self_path)"

  local OS ; OS=$(uname -s | tr '[:upper:]' '[:lower:]')
  
  local ARCH ; ARCH=$(uname -m)

  local BINARY=""
  if [[ "$OS" == "linux" ]]; then
    if [[ "$ARCH" == "x86_64" ]]; then
      BINARY="$SETUP_DIR/bin/bazelisk-linux-x86_64"
    elif [[ "$ARCH" == "aarch64" ]]; then
      BINARY="$SETUP_DIR/bin/bazelisk-linux-arm64"
    fi
  elif [[ "$OS" == "darwin" ]]; then
    if [[ "$ARCH" == "x86_64" ]]; then
      BINARY="$SETUP_DIR/bin/bazelisk-darwin-x86_64"
    elif [[ "$ARCH" == "arm64" ]]; then
      BINARY="$SETUP_DIR/bin/bazelisk-darwin-arm64"
    fi
  elif [[ "$OS" =~ ^(msys_nt|mingw|cygwin|win).* ]]; then
     BINARY="$SETUP_DIR/bin/bazelisk-windows-x86_64.exe"
  fi

  if [[ -n "$BINARY" && -f "$BINARY" ]]; then
    "$BINARY" "$@"
  else
    echo "Error: Bazelisk binary not found for platform: $OS-$ARCH" >&2
    echo "Expected location: $BINARY" >&2
    return 1
  fi
}

export -f bazel
