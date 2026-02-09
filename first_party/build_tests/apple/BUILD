load("@rules_apple//apple:ios.bzl", "ios_application")
load("@rules_apple//apple:macos.bzl", "macos_application")
load("@rules_swift//swift:swift.bzl", "swift_library")

swift_library(
    name = "lib_macos",
    srcs = ["Library.swift"],
    module_name = "AppleLibMacOS",
    tags = ["manual"],
    target_compatible_with = ["@platforms//os:macos"],
)

swift_library(
    name = "lib_ios",
    srcs = ["Library.swift"],
    module_name = "AppleLibiOS",
    tags = ["manual"],
    target_compatible_with = ["@platforms//os:ios"],
)

macos_application(
    name = "macos_application_must_build",
    bundle_id = "com.example.macos-app",
    infoplists = [],
    minimum_os_version = "12.0",
    tags = ["manual"],
    target_compatible_with = ["@platforms//os:macos"],
    deps = [":lib_macos"],
)

ios_application(
    name = "ios_application_must_build",
    bundle_id = "com.example.ios-app",
    families = [
        "iphone",
        "ipad",
    ],
    infoplists = [],
    minimum_os_version = "15.0",
    tags = ["manual"],
    target_compatible_with = ["@platforms//os:ios"],
    deps = [":lib_ios"],
)
