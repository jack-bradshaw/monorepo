setup() {
  source "./first_party/site/scripts/webp_converter.sh"
}

@test "optimize_image__normal_conditions__generates_image_matching_golden" {
  local input_image="first_party/site/scripts/tests/input_image.jpg"
  local golden_file="first_party/site/scripts/tests/goldens/webp_converter_test___optimize_image__normal_conditions__generates_image_matching_golden.webp"
  local output_file="first_party/site/scripts/tests/input_image_thumb.webp"

  run optimize_image "$input_image" 100 80 "_thumb"

  if [ "$status" -ne 0 ]; then
    echo "$output"
    return 1
  fi
  if [ ! -f "$output_file" ]; then
    echo "Output file was not created: $output_file"
    return 1
  fi

  run cmp "$output_file" "$golden_file"
  if [ "$status" -ne 0 ]; then
    cp "$output_file" \
      "$TEST_UNDECLARED_OUTPUTS_DIR/webp_converter_test___optimize_image__normal_conditions__generates_image_matching_golden.webp"
    echo "Output image does not match golden."
    echo "New image saved to \
		    bazel-testlogs/first_party/site/scripts/tests/webp_converter_test/test.outputs"
    echo "Test failed."
    return 1
  fi
}

@test "optimize_image__empty_input__fails" {
  run optimize_image "" 100 80
  if [ "$status" -ne 1 ]; then
    echo "Expected failure for empty input but commanded succeeded. Output: $output"
    return 1
  fi
}

@test "optimize_image__empty_width__fails" {
  run optimize_image "first_party/site/scripts/tests/input_image.jpg" "" 80
  if [ "$status" -ne 1 ]; then
    echo "Expected failure for empty width but commanded succeeded. Output: $output"
    return 1
  fi
}

@test "optimize_image__empty_quality__fails" {
  run optimize_image "first_party/site/scripts/tests/input_image.jpg" 100 ""
  if [ "$status" -ne 1 ]; then
    echo "Expected failure for empty quality but commanded succeeded. Output: $output"
    return 1
  fi
}
