load(
    "@rules_android//android:rules.bzl",
    "android_binary",
    "android_library",
    "android_local_test",
)

package(default_visibility = ["//visibility:private"])

android_library(
    name = "android_library_must_build",
    srcs = ["Main.java"],
    manifest = "Manifest.xml",
)

android_binary(
    name = "android_binary_must_build",
    srcs = ["Main.java"],
    manifest = "Manifest.xml",
)

android_local_test(
    name = "android_local_test_must_pass",
    srcs = ["Testing.java"],
    test_class = "com.jackbradshaw.build_tests.Testing",
    deps = [
        ":android_library_must_build",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
