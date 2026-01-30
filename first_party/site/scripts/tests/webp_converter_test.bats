setup() {
  {{RUNFILES_BOILERPLATE}}
  source "$(rlocation "_main/first_party/site/scripts/webp_converter.sh")"
}

@test "optimize_image__normal_conditions__generates_image_matching_golden" {
  # The input images are copied from runfiles to the temporary bats before being processed because
  # the output image path is inferred from the input image path, but runfiles are read-only.
  local input_image_rpath=$(rlocation "_main/first_party/site/scripts/tests/input_image.jpg")
  local input_image_path="${BATS_TMPDIR}/input_image.jpg"
  cp "$input_image_rpath" "$input_image_path"

  run optimize_image "$input_image_path" 100 80 "_thumb"

  local output_file_path="${BATS_TMPDIR}/input_image_thumb.webp"

  if [ "$status" -ne 0 ]; then
    echo "optimize_image failed with status $status"
    echo "Output: $output"
    return 1
  fi
  if [ ! -f "$output_file_path" ]; then
    echo "Output file was not created: $output_file_path"
    return 1
  fi

  local golden_file_path=$(rlocation \
    "_main/first_party/site/scripts/tests/goldens/webp_converter_test___optimize_image__normal_conditions__generates_image_matching_golden.webp")
  run cmp "$output_file_path" "$golden_file_path"

  if [ "$status" -ne 0 ]; then
    cp "$output_file_path" \
      "$TEST_UNDECLARED_OUTPUTS_DIR/webp_converter_test___optimize_image__normal_conditions__generates_image_matching_golden.webp"
    echo "Output image does not match golden."
    echo "New image saved to \
		    bazel-testlogs/first_party/site/scripts/tests/webp_converter_test/test.outputs"
    echo "Test failed."
    return 1
  fi
}

@test "optimize_image__empty_input__fails" {
  run optimize_image "" 100 80 ""

  if [ "$status" -ne 1 ]; then
    echo "Expected failure for empty input but commanded succeeded. Output: $output"
    return 1
  fi
}

@test "optimize_image__empty_width__fails" {
  local input_image_path=$(rlocation "_main/first_party/site/scripts/tests/input_image.jpg")
  run optimize_image "$input_image_path" "" 80 ""
  if [ "$status" -ne 1 ]; then
    echo "Expected failure for empty width but commanded succeeded. Output: $output"
    return 1
  fi
}

@test "optimize_image__empty_quality__fails" {
  local input_image_path=$(rlocation "_main/first_party/site/scripts/tests/input_image.jpg")
  run optimize_image "$input_image_path" 100 "" ""
  if [ "$status" -ne 1 ]; then
    echo "Expected failure for empty quality but commanded succeeded. Output: $output"
    return 1
  fi
}
