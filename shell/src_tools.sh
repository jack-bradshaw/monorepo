#!/bin/bash
# Tools for manipulating source code.

install_kt_fmt_for_linux() {
  cd /usr/bin
  wget https://repo1.maven.org/maven2/com/facebook/ktfmt/0.38/ktfmt-0.38-jar-with-dependencies.jar
  mv ktgmt-0.38-jar-with-dependencies.jar ktfmt.jar
}

ktfmt() {
  java -jar /usr/bin/ktfmt.jar "$@"
}
