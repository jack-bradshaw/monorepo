load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_test(
    name = "DispatcherTest",
    srcs = [
        "DispatcherTest.kt",
    ],
    jvm_flags = ["-Djava.security.manager=allow"],  # TODO(https://github.com/bazelbuild/bazel/issues/14502): Remove
    deps = [
        "//java/io/matthewbradshaw/kmonkey/coroutines:dispatcher",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_desktop",
        "@maven//:org_jmonkeyengine_jme3_lwjgl",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "lib",
    srcs = [
        "Main.java",
        "Test.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/gmonkey/octavius:__subpackages__"],
    deps = [
        "//java/io/matthewbradshaw/gmonkey/octavius",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

java_binary(
    name = "app",
    main_class = "gmonkey",
    runtime_deps = [":lib"],
)
