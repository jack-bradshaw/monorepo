#!/bin/bash
# Script to publish a maven artifact to the public.

echo "Enter username for Sonatype"
read username
echo "Enter password for Sonatype."
read -s password
echo "Enter Bazel target to build."
read target

bazel run --stamp \
    --define "maven_repo=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2" \
    --define "maven_user=$username" \
    --define "maven_password=$password" \
    --define gpg_sign=true \
    $target.publish

