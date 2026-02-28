#!/bin/bash

# This section is expanded by the bash_binary macro
{{RUNFILES_BOILERPLATE}}

# Parse arguments
API_KEY=""
PORT=""
while [[ $# -gt 0 ]]; do
  case $1 in
  --api_key=*)
    API_KEY="${1#*=}"
    shift # past argument=value
    ;;
  --port=*)
    PORT="${1#*=}"
    shift # past argument=value
    ;;
  *)
    echo "Unknown option $1"
    exit 1
    ;;
  esac
done

# if [[ -z "$API_KEY" ]]; then
#     echo "Error: --api_key argument is required."
#     echo "Usage: bazel run //first_party/experimental/council -- --api_key=YOUR_KEY [--port=PORT]"
#     exit 1
# fi

# Find available port if not specified
if [[ -z "$PORT" ]]; then
  echo "Finding available port..."
  # Try random ports between 8000 and 9000
  while true; do
    PORT=$((RANDOM % 1001 + 8000))
    if ! lsof -i:$PORT >/dev/null; then
      break
    fi
  done
  echo "Selected dynamic port: $PORT"
else
  echo "Using specified port: $PORT"
  # Kill anything running on the specified port
  lsof -ti:$PORT | xargs kill -9 2>/dev/null || true
fi

export GOOGLE_API_KEY="$API_KEY"

# Kill anything running on port 8085
lsof -ti:8085 | xargs kill -9 2>/dev/null || true

# Locate binaries
# Locate binaries
SERVER_BIN=$(rlocation "_main/first_party/experimental/council/server_bin")
SIMULATION_BIN=$(rlocation "_main/first_party/experimental/council/simulation_bin")

if [[ ! -f "$SERVER_BIN" ]]; then
  echo "Error: Server binary not found at $SERVER_BIN"
  exit 1
fi

if [[ ! -f "$SIMULATION_BIN" ]]; then
  echo "Error: Simulation binary not found at $SIMULATION_BIN"
  exit 1
fi

echo "Starting Unimatrix 01 Simulation..."

# Start Server in background
echo "Starting Server on port $PORT..."
"$SERVER_BIN" --port="$PORT" &
SERVER_PID=$!

# Wait for server to be ready (simple sleep for now, could be a health check)
sleep 5

# Open the webpage
echo "Opening UI at http://localhost:$PORT"
open "http://localhost:$PORT" || echo "Failed to auto-open. Please visit http://localhost:$PORT"

# Start Simulation
echo "Starting Simulation..."
"$SIMULATION_BIN" --port="$PORT" &
SIMULATION_PID=$!

# Trap cleanup
cleanup() {
  echo "Stopping processes..."
  kill "$SERVER_PID" "$SIMULATION_PID" 2>/dev/null
}
trap cleanup EXIT

# Wait for processes
wait "$SERVER_PID" "$SIMULATION_PID"
