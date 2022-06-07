load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "app_lib",
    srcs = [
        "App.kt",
        "Main.java",
    ],
    deps = [
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_desktop",
        "@maven//:org_jmonkeyengine_jme3_lwjgl",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

java_binary(
    name = "app_bin",
    main_class = "io.matthewbradshaw.paracosm.core.Main",
    runtime_deps = [":app_lib"],
)
