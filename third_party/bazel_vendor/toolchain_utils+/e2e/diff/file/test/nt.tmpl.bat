@echo off

:: Enable Batch extensions
verify other 2>nul
setlocal EnableExtensions
if errorlevel 1 (
  echo>&2.Failed to enable extensions
  exit /b 120
)

:: Bazel substitutions
call :rlocation A "{{a}}"
if errorlevel 1 (
  echo>&2.Failed to resolve runfile
  exit /b 2
)
call :rlocation B "{{b}}"
if errorlevel 1 (
  echo>&2.Failed to resolve runfile
  exit /b 2
)

:: Provided on all Windows installations
set "DIFF=%SYSTEMROOT%\\system32\\fc.exe"

:: Perform the difference
"%DIFF%" "%A%" "%B%"
exit /b %ERRORLEVEL%

:rlocation - resolve run files
:: %1 - return code variable
:: %2 - runfile path
setlocal
set "FILEPATH=%2%"
for /f usebackq %%a in ('%FILEPATH%') do set "FILEPATH=%%~a"
if [%RUNFILES_MANIFEST_ONLY%] neq [1] (
  echo>&2.Only runfile manifests are supported
  exit /b 2
)
for /f "tokens=1,2* usebackq" %%a in ("%RUNFILES_MANIFEST_FILE%") do (
    if "_main/%FILEPATH%" == "%%a" (
      set "FILEPATH=%%~fb"
    )
    if "%FILEPATH%" == "../%%a" (
      set "FILEPATH=%%~fb"
    )
)
if not exist "%FILEPATH%" (
  echo>&2.Failed to resolve runfile: %FILEPATH%
  exit /b 2
)
endlocal & set "%~1=%FILEPATH%"
goto :eof
