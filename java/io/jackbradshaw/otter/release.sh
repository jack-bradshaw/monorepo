#!/bin/bash
# Script to publish the Otter library to Maven Central.

echo "Enter password for Sonatype."
read -s password

bazel run --stamp \
    --define "maven_repo=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2" \
    --define "maven_user=jackbradshaw" \
    --define "maven_password=$password" \
    --define gpg_sign=true \
    //java/io/jackbradshaw/otter:release.publish

