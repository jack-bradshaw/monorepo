@echo off
:: Setup script for Bazelisk on Windows.
::
:: Usage:
::   call third_party\bazelisk\setup.bat
::   bazel <command>
::
:: The exposed command is called Bazel for convenience but is actually backed by Bazelisk.

doskey bazel="%~dp0bin\bazelisk-windows-x86_64.exe" $*
