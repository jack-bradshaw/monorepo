#! /usr/bin/env sh

# e: quit on command errors
# u: quit on undefined variables
set -eu

# Bazel substitutions
EXECUTABLE="{{executable}}"
STDOUT="{{stdout}}"
STDERR="{{stderr}}"
STATUS="{{status}}"
readonly EXECUTABLE STDOUT STDERR STATUS

# Test environment
JUNIT="${XML_OUTPUT_FILE-junit.xml}"
readonly JUNIT

# Run the toolchain executable and validate the output
"${EXECUTABLE}" "$@" >stdout.txt 2>stderr.txt && CODE=$? || CODE=$?
readonly CODE
if test "${STATUS}" != "${CODE}"; then
  echo >&2 "Failed to run (${CODE}): ${EXECUTABLE} ${*}"
  echo >&2 "stdout:"
  while IFS= read -r LINE; do
    echo >&2 "${LINE}"
  done <stdout.txt
  echo >&2 "stderr:"
  while IFS= read -r LINE; do
    echo >&2 "${LINE}"
  done <stderr.txt
  exit 2
fi

any() (
  INDEX="${1}"
  FILEPATH="${2}"

  printf >&2 'ok %i - %s contained any content\n' "${INDEX}" "${FILEPATH}"
  printf '  <testcase name="%s"/>\n' "${FILEPATH}"
)

non_empty() (
  INDEX="${1}"
  FILEPATH="${2}"

  if ! test -s "${FILEPATH}"; then
    printf >&2 'not ok %i - %s was an empty file when content was expected\n' "${INDEX}" "${FILEPATH}"
    printf '  <testcase name="%s">\n' "${FILEPATH}"
    printf '    <failure type="EmptyFile">%s was an empty file when content was expected</failure>\n' "${FILEPATH}"
    printf '  </testcase>\n'
  else
    printf >&2 'ok %i - %s was an empty file\n' "${INDEX}" "${FILEPATH}"
    printf '  <testcase name="%s"/>\n' "${FILEPATH}"
  fi
)

empty() (
  INDEX="${1}"
  FILEPATH="${2}"

  if test -s "${FILEPATH}"; then
    printf >&2 'not ok %i - %s contained content when an empty file was expected\n' "${INDEX}" "${FILEPATH}"
    printf '  <testcase name="%s">\n' "${FILEPATH}"
    printf '    <failure type="NonEmptyFile">%s contained unexpected content:\n' "${FILEPATH}"
    while IFS= read -r LINE; do
      printf '%s\n' "${LINE}"
    done <"${FILEPATH}"
    printf '%s</failure>\n' "${LINE}"
    printf '  </testcase>\n'
  else
    printf >&2 'ok %i - %s was a non-empty file\n' "${INDEX}" "${FILEPATH}"
    printf '  <testcase name="%s"/>\n' "${FILEPATH}"
  fi
)

diff() (
  INDEX="${1}"
  FILEPATH="${2}"
  EXPECTED="${3}"

  while true; do
    FAILS=0
    IFS= read -r A <&3 || FAILS=$((FAILS + 1))
    IFS= read -r B <&4 || FAILS=$((FAILS + 1))
    if test "${FAILS}" -eq 1; then
      printf >&2 'not ok %i - %s had a different number of lines to %s\n' "${INDEX}" "${FILEPATH}" "${EXPECTED}"
      printf '  <testcase name="%s">\n' "${FILEPATH}"
      printf '    <failure type="Difference">%s contained different line counts:\n' "${FILEPATH}"
      printf '%s %s\n' '---' "${FILEPATH}"
      printf '%s %s\n' '+++' "${EXPECTED}"
      printf '@@ -1 +1 @@\n'
      printf '%s%s\n' '-' "${A-}"
      printf '%s%s\n' '+' "${B-}"
      printf '</failure>\n'
      printf '  </testcase>\n'
      exit
    elif test "${FAILS}" -eq 2; then
      break
    elif test "${A}" != "${B}"; then
      printf >&2 'not ok %i: %s had different content to %s\n' "${INDEX}" "${FILEPATH}" "${EXPECTED}"
      printf '  <testcase name="%s">\n' "${FILEPATH}"
      printf '    <failure type="Difference">%s contained different content:\n' "${FILEPATH}"
      printf '%s %s\n' '---' "${FILEPATH}"
      printf '%s %s\n' '+++' "${EXPECTED}"
      printf '@@ -1 +1 @@\n'
      printf '%s%s\n' '-' "${A}"
      printf '%s%s\n' '+' "${B}"
      printf '</failure>\n'
      printf '  </testcase>\n'
      exit
    fi
  done 3<"${FILEPATH}" 4<"${EXPECTED}"

  printf >&2 'ok %s - %s was equal to %s\n' "${INDEX}" "${FILEPATH}" "${EXPECTED}"
  printf '  <testcase name="%s"/>\n' "${FILEPATH}"
)

validate() (
  INDEX="${1}"
  FILEPATH="${2}"
  EXPECTED="${3}"

  if ! test -f "${FILEPATH}"; then
    printf >&2 'not ok %i - %s not found\n' "${INDEX}" "${FILEPATH}"
    printf '  <testcase name="%s">\n' "${FILEPATH}"
    printf '    <failure type="NotFoundFile">%s was not found</failure>\n' "${FILEPATH}"
    printf '  </testcase>\n'
    exit
  elif ! test -f "${EXPECTED}"; then
    printf >&2 'not ok %i - %s not found\n' "${INDEX}" "${EXPECTED}"
    printf '  <testcase name="%s">\n' "${FILEPATH}"
    printf '    <failure type="NotFoundFile">%s was not found</failure>\n' "${EXPECTED}"
    printf '  </testcase>\n'
    exit
  fi

  case "${EXPECTED}" in
  *"/toolchain/test/any")
    any "${INDEX}" "${FILEPATH}"
    ;;
  *"/toolchain/test/non-empty")
    non_empty "${INDEX}" "${FILEPATH}"
    ;;
  *"/toolchain/test/empty")
    empty "${INDEX}" "${FILEPATH}"
    ;;
  *)
    diff "${INDEX}" "${FILEPATH}" "${EXPECTED}"
    ;;
  esac
)

junit() (
  COUNT="${#}"
  TESTS=$((COUNT / 2))
  readonly COUNT TESTS
  printf '<testsuite tests="%s">\n' "${TESTS}"
  printf >&2 '1..%i\n' "${TESTS}"
  INDEX=1
  while ! test -z ${2+x}; do
    FILEPATH="${1}"
    EXPECTED="${2}"
    shift 2
    validate "${INDEX}" "${FILEPATH}" "${EXPECTED}"
    INDEX=$((INDEX + 1))
  done
  printf '</testsuite>\n'
)

junit \
  stdout.txt "${STDOUT}" \
  stderr.txt "${STDERR}" \
  >"${JUNIT}"

while IFS= read -r LINE; do
  if test -z "${LINE#*</failure>*}"; then
    exit 1
  fi
done <"${JUNIT}"
