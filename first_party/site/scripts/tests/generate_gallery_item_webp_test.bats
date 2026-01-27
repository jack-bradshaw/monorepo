setup() {
  {{RUNFILES_BOILERPLATE}}
}

@test "generate_gallery_item_webp__generated_image_matches_golden" {
  local script_path=$(rlocation "_main/first_party/site/scripts/generate_gallery_item_webp_with_runfiles.sh")

  # The input images are copied from runfiles to the temporary bats before being processed because
  # the output image path is inferred from the input image path, but runfiles are read-only.
  local input_image_rpath=$(rlocation "_main/first_party/site/scripts/tests/input_image.jpg")
  local input_image_path="${BATS_TMPDIR}/input_image.jpg"
  cp "$input_image_rpath" "$input_image_path"

  run "$script_path" "$input_image_path"

  # Script uses the same name as the input image with a different extension for the output.
  local output_file_path="${BATS_TMPDIR}/input_image_thumbnail.webp"

  if [ "$status" -ne 0 ]; then
    echo "generate_gallery_item_webp.sh failed with status $status"
    echo "Output: $output"
    return 1
  fi
  if [ ! -f "$output_file_path" ]; then
    echo "Output file was not created: $output_file_path"
    return 1
  fi

  local golden_file_path=$(rlocation \
    "_main/first_party/site/scripts/tests/goldens/generate_gallery_item_webp_test___generate_gallery_item_webp__generated_image_matches_golden.webp")
  run cmp "$output_file_path" "$golden_file_path"

  if [ "$status" -ne 0 ]; then
    cp "$output_file_path" \
      "$TEST_UNDECLARED_OUTPUTS_DIR/generate_gallery_item_webp_test___generate_gallery_item_webp__generated_image_matches_golden.webp"
    echo "Output image does not match golden."
    echo "New image saved to \
		    bazel-testlogs/first_party/site/scripts/tests/generate_gallery_item_webp_test/test.outputs"
    echo "Test failed."
    return 1
  fi
}
