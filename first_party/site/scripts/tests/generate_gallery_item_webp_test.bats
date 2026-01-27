@test "generate_gallery_item_webp__generated_image_matches_golden" {
  local input_image="first_party/site/scripts/tests/input_image.jpg"
  local golden_file="first_party/site/scripts/tests/goldens/generate_gallery_item_webp_test___generate_gallery_item_webp__generated_image_matches_golden.webp"
  local output_file="first_party/site/scripts/tests/input_image_thumbnail.webp"

  run ./first_party/site/scripts/generate_gallery_item_webp.sh "$input_image"

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
      "$TEST_UNDECLARED_OUTPUTS_DIR/generate_gallery_item_webp_test___generate_gallery_item_webp__generated_image_matches_golden.webp"
    echo "Output image does not match golden."
    echo "New image saved to \
		    bazel-testlogs/first_party/site/scripts/tests/generate_gallery_item_webp_test/test.outputs"
    echo "Test failed."
    return 1
  fi
}
