load("@aspect_rules_js//js:defs.bzl", "js_binary", "js_library", "js_test")

package(default_visibility = ["//visibility:private"])

js_library(
    name = "js_library_must_build",
    srcs = ["main.js"],
)

js_binary(
    name = "js_binary_must_build",
    entry_point = "main.js",
)

js_test(
    name = "js_test_must_pass",
    entry_point = "test.js",
)
