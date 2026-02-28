# Scripts

This package contains scripts for converting large images to smaller WebP thumbnails. It exists
because serving full-size images in small interfaces uses excessive bandwidth and increases serving
costs. Different configurations exist for converting different image types.

## Usage

To convert a gallery content item to a thumbnail:

```shell
bazel run first_party/site/scripts:generate_gallery_item_webp -- absolute_path_to_image
```

To convert a decoration item to a thumbnail:

```shell
bazel run first_party/site/scripts:generate_decoration_webp -- absolute_path_to_image
```

The difference between the two scripts is the quality and size of the resulting thumbnail.
