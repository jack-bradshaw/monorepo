:: Enable Batch extensions
@verify other 2>nul
@setlocal EnableExtensions
@if errorlevel 1 (
  echo "Failed to enable extensions"
  exit /b 120
)

:: Enable delayed expansion of variables with `!VAR!`
@verify other 2>nul
@setlocal EnableDelayedExpansion
@if errorlevel 1 (
  echo "Failed to enable extensions"
  exit /b 120
)

:: Bazel substitutions
@set "TRIPLET={{triplet}}"

:: Execute!
@echo.%TRIPLET%
