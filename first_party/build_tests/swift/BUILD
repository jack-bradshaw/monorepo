load("@rules_swift//swift:swift.bzl", "swift_binary", "swift_library", "swift_test")

package(
    default_visibility = ["//visibility:private"],
)

swift_library(
    name = "swift_library_must_build",
    srcs = ["Library.swift"],
    module_name = "Library",
    tags = ["manual"],
    target_compatible_with = ["@platforms//os:macos"],
)

swift_binary(
    name = "swift_binary_must_build",
    srcs = ["Binary.swift"],
    tags = ["manual"],
    target_compatible_with = ["@platforms//os:macos"],
)

swift_test(
    name = "swift_test_must_pass",
    srcs = ["Test.swift"],
    tags = ["manual"],
    target_compatible_with = ["@platforms//os:macos"],
)
