#!/bin/bash

# Trims the leading spaces from every line of the first argument.
trim_spaces() {
  echo "$1" | sed 's/^[   ]*//'
}
