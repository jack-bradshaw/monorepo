#!/usr/bin/env sh

# Strict shell
set -o errexit
set -o nounset

# Bazel substitutions
A="{{a}}"
B="{{b}}"
readonly A B

# Test environment
JUNIT="${XML_OUTPUT_FILE-junit.xml}"
readonly JUNIT

diff() (
  INDEX="${1}"
  FILEPATH="${2}"
  EXPECTED="${3}"

  while true; do
    FAILS=0
    IFS= read -r L <&3 || FAILS=$((FAILS + 1))
    IFS= read -r R <&4 || FAILS=$((FAILS + 1))
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
    elif test "${L}" != "${R}"; then
      printf >&2 'not ok %i: %s had different content to %s\n' "${INDEX}" "${FILEPATH}" "${EXPECTED}"
      printf '  <testcase name="%s">\n' "${FILEPATH}"
      printf '    <failure type="Difference">%s contained different content:\n' "${FILEPATH}"
      printf '%s %s\n' '---' "${FILEPATH}"
      printf '%s %s\n' '+++' "${EXPECTED}"
      printf '@@ -1 +1 @@\n'
      printf '%s%s\n' '-' "${L}"
      printf '%s%s\n' '+' "${R}"
      printf '</failure>\n'
      printf '  </testcase>\n'
      exit
    fi
  done 3<"${FILEPATH}" 4<"${EXPECTED}"

  printf >&2 'ok %s - %s was equal to %s\n' "${INDEX}" "${FILEPATH}" "${EXPECTED}"
  printf '  <testcase name="%s"/>\n' "${FILEPATH}"
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
    diff "${INDEX}" "${FILEPATH}" "${EXPECTED}"
    INDEX=$((INDEX + 1))
  done
  printf '</testsuite>\n'
)

junit "${A}" "${B}" >"${JUNIT}"

while IFS= read -r LINE; do
  if test -z "${LINE#*</failure>*}"; then
    exit 1
  fi
done <"${JUNIT}"
