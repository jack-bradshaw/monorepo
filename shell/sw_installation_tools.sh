#!/bin/bash
# Tools for working with Java.

# Downloads and installs JDK11 using apt.
apt_get_jdk11() {
  sudo apt install openjdk-11-jdk
}

# Downloads and install bazel using apt.
apt_get_bazel() {
  sudo apt install apt-transport-https curl gnupg
  curl -fsSL https://bazel.build/bazel-release.pub.gpg | \
      gpg --dearmor > bazel.gpg
  sudo mv bazel.gpg /etc/apt/trusted.gpg.d/
  echo "deb [arch=amd64] \
      https://storage.googleapis.com/bazel-apt stable jdk1.8" | \
      sudo tee /etc/apt/sources.list.d/bazel.list

  sudo apt update && sudo apt install bazel
  sudo apt update && sudo apt full-upgrade
}
