#!/bin/bash

# Generates a WebP version an image.
# The size and quality of the output file is optimised for decoration items.
#
# Arguments:
# 1. The image to convert, as an absolute path.
#
# Host Dependencies:
# - cwebp: https://developers.google.com/speed/webp/docs/cwebp
#
# Library Dependencies:
# - webp_converter.sh

set -e

SIZE_PX=400
QUALITY_PERCENT=90

source "first_party/site/scripts/webp_converter.sh"
optimize_image "$1" "$SIZE_PX" "$QUALITY_PERCENT" ""
