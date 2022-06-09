#!/bin/bash
# Tools for interacting with the host system.

# Prints a report on the system.
system_report() {
  echo "System Report"
  echo "Connections:"
  echo "$(who)"
  echo "Last reboot: $(who -b)"
}
