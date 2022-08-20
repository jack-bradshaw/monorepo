load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_test(
    name = "DispatcherTest",
    srcs = [
        "DispatcherTest.kt",
    ],
    jvm_flags = ["-Djava.security.manager=allow"],  # TODO(https://github.com/bazelbuild/bazel/issues/14502): Remove
    deps = [
        "//java/io/jackbradshaw/gmonkey",
        "//java/io/jackbradshaw/gmonkey/coroutines:dispatcher",
        "//java/io/jackbradshaw/gmonkey/testing:cubegame_lib",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_desktop",
        "@maven//:org_jmonkeyengine_jme3_lwjgl3",
        "@maven//:org_jmonkeyengine_jme3_vr",
        "@maven//:org_lwjgl_lwjgl",
    ],
)
