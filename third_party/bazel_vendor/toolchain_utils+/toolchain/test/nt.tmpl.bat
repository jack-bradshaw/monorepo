@echo off

:: Enable Batch extensions
verify other 2>nul
setlocal EnableExtensions
if errorlevel 1 (
  echo "Failed to enable extensions"
  exit /b 120
)

:: Check for delayed expansion of variables with `!VAR!`
verify other 2>nul
setlocal EnableDelayedExpansion
if errorlevel 1 (
  echo "Failed to enable extensions"
  exit /b 120
)
setlocal DisableDelayedExpansion

:: Bazel substitutions
set "EXECUTABLE={{executable}}"
set "STDOUT={{stdout}}"
set "STDERR={{stderr}}"
set "STATUS={{status}}"

:: Runfiles
if [%RUNFILES_MANIFEST_ONLY%] neq [1] (
  echo>&2.Only runfile manifests are supported
  exit /b 2
)
setlocal EnableDelayedExpansion
for %%v in (EXECUTABLE,STDOUT,STDERR) do (
    for /f "tokens=1,2* usebackq" %%a in ("%RUNFILES_MANIFEST_FILE%") do (
        if "_main/!%%v!" == "%%a" (
          set "%%v=%%~fb"
        )
        if "!%%v!" == "../%%a" (
          set "%%v=%%~fb"
        )
    )
)
setlocal DisableDelayedExpansion

:: Execute!
for /f %%a in ("%EXECUTABLE%") do set EXTENSION=%%~xa
if "%EXTENSION%" == ".bat" set LAUNCHER=call
%LAUNCHER% "%EXECUTABLE%" %* >stdout.txt 2>stderr.txt
set "CODE=%ERRORLEVEL%"
if %CODE% neq %STATUS% (
  >&2 echo.Failed to run ^(%STATUS^): %EXECUTABLE% %*
  >&2 echo.stdout:
  >&2 type stdout.txt
  >&2 echo.stderr:
  >&2 type stderr.txt
  exit /b %CODE%
)

:: Compare
set "JUNIT=junit.xml"
if not [%XML_OUTPUT_FILE%] == [] set "JUNIT=%XML_OUTPUT_FILE%"
call :junit CODE stdout.txt "%STDOUT%" stderr.txt "%STDERR%" >"%JUNIT%"
exit /b %CODE%

:junit - creates JUnit XML output from comparing files
:: %1 - return code variable
:: %* - pairs of files to compare
setlocal

:: Output TAP/JUnit headers
set COUNT=0
for %%a in (%*) do set /a "COUNT+=1"
set /a "TESTS=COUNT/2"
echo.^<testsuite tests="%TESTS%"^>
>&2 echo.1..%TESTS%
set SUM=0

:: Loop through each file pairing
set INDEX=0
:loop
set /a "INDEX+=1"
call :compare CODE %INDEX% "%~2" "%~3"
set /a "SUM+=CODE"
shift /2
shift /2
if exist "%~3" goto :loop

:: Output the JUnit footer
echo.^</testsuite^>

endlocal & set "%~1=%SUM%"
goto :eof

:compare - compare two files
:: %1 - return code variable
:: %2 - test index
:: %3 - file under test
:: %4 - expected output
setlocal
set "INDEX=%~2"
set "FILEPATH=%~3"
set "EXPECTED=%~4"
if "%EXPECTED:~-19%" == "\toolchain\test\any" (
    call :any CODE %INDEX% "%FILEPATH%" "%EXPECTED%"
) else if "%EXPECTED:~-21%" == "\toolchain\test\empty" (
    call :empty CODE %INDEX% "%FILEPATH%" "%EXPECTED%"
) else if "%EXPECTED:~-25%" == "\toolchain\test\non-empty" (
    call :non-empty CODE %INDEX% "%FILEPATH%" "%EXPECTED%"
) else (
    call :diff CODE %INDEX% "%FILEPATH%" "%EXPECTED%"
)
endlocal & set %~1=%CODE%
goto :eof

:any - a file can have any content
:: %1 - return code variable
:: %2 - test index
:: %3 - file under test
:: %4 - expected output
setlocal
set "INDEX=%~2"
set "FILEPATH=%~3"
set "EXPECTED=%~4"
>&2 echo.ok %INDEX% - %FILEPATH% contained any content
echo.^<testcase name="%FILEPATH%"/^>
endlocal & set %~1=0
goto :eof

:empty - a file must have zero content
:: %1 - return code variable
:: %2 - test index
:: %3 - file under test
:: %4 - expected output
setlocal
set "INDEX=%~2"
set "FILEPATH=%~3"
set "EXPECTED=%~4"
for /f %%a in ("%FILEPATH%") do set "SIZE=%%~Za"
if %SIZE% equ 0 (
    >&2 echo.ok %INDEX% - %FILEPATH% was an empty file
    echo.^<testcase name="%FILEPATH%"/^>
    set "CODE=0"
) else (
    >&2 echo.not ok %INDEX% - %FILEPATH% contained content when an empty file was expected
    echo.  ^<testcase name="%FILEPATH%"^>
    echo.    ^<failure type="NonEmptyFile"^>%FILEPATH% contained unexpected content:
    type "%FILEPATH%"
    echo.    ^</failure^>
    echo.  ^</testcase^>
    set "CODE=1"
)
endlocal & set %~1=%CODE%
goto :eof

:non-empty - a file must have some content
:: %1 - return code variable
:: %2 - test index
:: %3 - file under test
:: %4 - expected output
setlocal
set "INDEX=%~2"
set "FILEPATH=%~3"
set "EXPECTED=%~4"
for /f %%a in ("%FILEPATH%") do set "SIZE=%%~Za"
if %SIZE% neq 0 (
    >&2 echo.ok %INDEX% - %FILEPATH% was a non-empty file
    echo.  ^<testcase name="%FILEPATH%"/^>
    set "CODE=0"
) else (
    >&2 echo.not ok %INDEX% - %FILEPATH% was an empty file when content was expected
    echo.  ^<testcase name="%FILEPATH%"^>
    echo.    ^<failure type="NonEmptyFile"^>%FILEPATH% was an empty file when content was expected^</failure^>
    echo.  ^</testcase^>
    set "CODE=1"
)
endlocal & set %~1=%CODE%
goto :eof

:diff - compare the two passed files for content equivalence
:: %1 - return code variable
:: %2 - test index
:: %3 - file under test
:: %4 - expected output
setlocal
set "INDEX=%~2"
set "FILEPATH=%~3"
set "EXPECTED=%~4"
set "DIFF=%SYSTEMROOT%\\system32\\fc.exe"
if not exist "%DIFF%" (
    >&2 echo.not ok %INDEX% - missing file compare executable: %DIFF%
    echo.  ^<testcase name="%FILEPATH%"^>
    echo.    ^<failure type="MissingExecutable"^>Missing file compare executable: %DIFF%^</failure^>
    echo.  ^</testcase^>
    set CODE=1
    goto :fail
)
set "CODE=%ERRORLEVEL%"
if %CODE% equ 0 (
    >&2 echo.ok %INDEX% - %FILEPATH% was identical
    echo.^<testcase name="%FILEPATH%"/^>
) else if %CODE% equ 1 (
    >&2 echo.not ok %INDEX% - %FILEPATH% had different content to %EXPECTED%
    echo.  ^<testcase name="%FILEPATH%"^>
    echo.    ^<failure type="Difference"^>%FILEPATH% contained different content:
    "%DIFF%" "%FILEPATH%" "%EXPECTED%"
    echo.    ^</failure^>
    echo.  ^</testcase^>
) else (
    >&2 echo.not ok %INDEX% - unknown exit code from %DIFF%: %CODE%
    echo.  ^<testcase name="%FILEPATH%"^>
    echo.    ^<failure type="UnknownExitCode"^>Unknown exit code from %DIFF%: %CODE%^</failure^>
    echo.  ^</testcase^>
    set CODE=1
)
:fail
endlocal & set %~1=%CODE%
goto :eof
