@echo off
setlocal

REM Wrapper script for Bazelisk on Windows
REM This ensures that all bazel invocations use the hermetic bazelisk binary
call "%~dp0..\third_party\bazelisk\bazelisk.bat" %*
