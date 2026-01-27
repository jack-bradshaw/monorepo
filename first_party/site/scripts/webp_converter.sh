#!/bin/bash

# Optimizes an image to WebP format.
#
# Arguments:
# 1. The image to convert, as an absolute path, required.
# 2. The width of the output image, in pixels, required.
# 3. The quality of the output image, as a percentage, required.
# 4. The suffix to append to the output file (before the extension), optional.
#
# Host Dependencies:
# - cwebp: https://developers.google.com/speed/webp/docs/cwebp
#
# Usage: optimize_image <input_file> <width> <quality> <suffix>
#
# Example: optimize_image "foo.jpg" 600 85 "_thumbnail"
optimize_image() {
  if ! command -v cwebp &>/dev/null; then
    echo "Error: cwebp is not installed or not in PATH."
    exit 1
  fi

  local input_file="$1"
  local width="$2"
  local quality="$3"
  local suffix="$4"

  if [[ -z "$input_file" ]]; then
    echo "Error: No input file provided."
    return 1
  fi

  if [[ ! -f "$input_file" ]]; then
    echo "Error: File not found: $input_file"
    return 1
  fi

  if [[ -z "$width" ]]; then
    echo "Error: Width not provided."
    return 1
  fi

  if [[ -z "$quality" ]]; then
    echo "Error: Quality not provided."
    return 1
  fi

  local dir=$(dirname "$input_file")
  local filename=$(basename "$input_file")
  local filename_no_ext="${filename%.*}"
  local output_file="$dir/${filename_no_ext}${suffix}.webp"

  echo "Converting $input_file and writing result to $output_file"
  cwebp -q "$quality" "$input_file" -resize "$width" 0 -o "$output_file"
  echo "Conversion complete."
}
