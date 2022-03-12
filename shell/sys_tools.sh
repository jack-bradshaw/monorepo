#!/bin/bash

# Tools for interacting with the host system.

system_report() {
  echo "System Report"
  echo "Connections:"
  echo "$(who)"
  echo "Last reboot: $(who -b)"
}
