@echo off
setlocal

set SCRIPT_DIR=%~dp0
"%SCRIPT_DIR%bin\bazelisk-windows-x86_64.exe" %*
