load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "installer",
    srcs = [
        "ManifestInstaller.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        "//first_party/klu/flow",
        "//first_party/otter/openxr/model:kt_jvm_proto",
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "ManifestInstallerImpl.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":installer",
        "//:dagger",
        "//first_party/otter:scope",
        "//first_party/otter/config:kt_jvm_proto",
        "//first_party/otter/openxr/manifest/generator",
        "@com_jackbradshaw_maven//:com_google_code_gson_gson",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "ManifestInstallerModule.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":impl",
        ":installer",
        "//:dagger",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = [
        "ManifestInstallerTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.manifest.installer.ManifestInstallerTest",
    visibility = ["//visibility:private"],
    deps = [
        "//:dagger",
        "//first_party/otter:component",
        "//first_party/otter/config:proto_extensions",
        "//first_party/otter/openxr/config:proto_extensions",
        "//first_party/otter/openxr/manifest/encoder",
        "//first_party/otter/openxr/manifest/installer",
        "//first_party/otter/openxr/manifest/testgoldens",
        "//first_party/otter/openxr/standard",
        "@bazel_tools//tools/java/runfiles",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_kotlin_lite",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_skyscreamer_jsonassert",
    ],
)
