@echo off

:: Enable Batch extensions
verify other 2>nul
setlocal EnableExtensions
if errorlevel 1 (
  echo "Failed to enable extensions"
  exit /b 120
)

:: Bazel substitutions
set "EXECUTABLE={{path}}"

:: Resolve path
for /f %%a in ("%EXECUTABLE%") do set "EXECUTABLE=%%~fa"

:: Execute!
for /f %%a in ("%EXECUTABLE%") do set EXTENSION=%%~xa
if "%EXTENSION%" == ".bat" set LAUNCHER=call
%LAUNCHER% "%EXECUTABLE%" %*