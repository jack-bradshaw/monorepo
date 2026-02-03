setup() {
  {{RUNFILES_BOILERPLATE}}
  source "$(rlocation "_main/first_party/dr_bashir/strings/trim_indent_with_runfiles.sh")"
}

@test "generate_decoration_webp__generated_image_matches_golden" {
  local script_path=$(rlocation "_main/first_party/site/scripts/generate_decoration_webp_with_runfiles.sh")

  # The input images are copied from runfiles to the temporary bats before being processed because
  # the output image path is inferred from the input image path, but runfiles are read-only.
  local input_image_rpath=$(rlocation "_main/first_party/site/scripts/tests/input_image.jpg")
  local input_image_path="${BATS_TMPDIR}/input_image.jpg"
  cp "$input_image_rpath" "$input_image_path"

  run "$script_path" "$input_image_path"

  # Script uses the same name as the input image with a different extension for the output.
  local output_file_path="${BATS_TMPDIR}/input_image.webp"

  if [ "$status" -ne 0 ]; then
    echo "generate_decoration_webp.sh failed with status $status"
    echo "Output: $output"
    return 1
  fi

  if [ ! -f "$output_file_path" ]; then
    echo "Output file was not created: $output_file_path"
    return 1
  fi

  local golden_file_path=$(rlocation \
    "_main/first_party/site/scripts/tests/goldens/generate_decoration_webp_test___generate_decoration_webp__generated_image_matches_golden.webp")
  run cmp "$output_file_path" "$golden_file_path"

  if [ "$status" -ne 0 ]; then
    cp "$output_file_path" "$TEST_UNDECLARED_OUTPUTS_DIR/actual.webp"
    cp "$golden_file_path" "$TEST_UNDECLARED_OUTPUTS_DIR/expected.webp"
    echo "Output image does not match golden."
    echo "Actual image:   $TEST_UNDECLARED_OUTPUTS_DIR/actual.webp"
    echo "Expected image: $TEST_UNDECLARED_OUTPUTS_DIR/expected.webp"
    return 1
  fi

  return 0
}
