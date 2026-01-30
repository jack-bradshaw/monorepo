load(
    "@rules_android//android:rules.bzl",
    "aar_import",
    "android_application",
    "android_binary",
    "android_library",
    "android_local_test",
)
load("@rules_kotlin//kotlin:android.bzl", "kt_android_library")

package(default_visibility = ["//visibility:private"])

aar_import(
    name = "aar_import_must_build",
    aar = "dummy.aar",
)

kt_android_library(
    name = "kt_android_library_must_build",
    srcs = ["MainKotlin.kt"],
    manifest = "Manifest.xml",
)

android_application(
    name = "android_application_must_build",
    srcs = ["MainJava.java"],
    manifest = "Manifest.xml",
)

android_library(
    name = "android_library_must_build",
    srcs = ["MainJava.java"],
    manifest = "Manifest.xml",
)

android_binary(
    name = "android_binary_must_build",
    srcs = ["MainJava.java"],
    manifest = "Manifest.xml",
)

android_local_test(
    name = "android_local_test_must_pass",
    srcs = ["Testing.java"],
    test_class = "com.jackbradshaw.build_tests.Testing",
    deps = [
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
