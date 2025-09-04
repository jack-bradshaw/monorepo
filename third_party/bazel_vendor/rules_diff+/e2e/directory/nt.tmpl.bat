@echo off

:: Enable Batch extensions
verify other 2>nul
setlocal EnableExtensions
if errorlevel 1 (
  echo>&2.Failed to enable extensions
  exit /b 120
)

:: Bazel substitutions
for /f usebackq %%a in ('{{cp}}') do set "CP=%%~fa"
for /f usebackq %%a in ('{{mkdir}}') do set "MKDIR=%%~fa"

:: Copy files
setlocal EnableDelayedExpansion
for %%f in (%*) do (
  if not defined DST (
    set "DST=%%~f"
  ) else (
    set "SRC=%%~f"
    "%MKDIR%" -p "!DST!/!SRC!/.."
    if errorlevel 1 (
      echo>&2.Failed to create directory: !DST!/!SRC!
      exit /b 2
    )
    "%CP%" "!SRC!" "!DST!/!SRC!"
    if errorlevel 1 (
      echo>&2.Failed to copy file: !SRC!
      exit /b 2
    )
  )
)
setlocal DisableDelayedExpansion

:: Complete!
exit /b 0