load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "game_lib",
    srcs = [
        "App.kt",
        "Main.java",
    ],
    deps = [
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_desktop",
        "@maven//:org_jmonkeyengine_jme3_lwjgl",
    ],
)

java_binary(
    name = "game_bin",
    main_class = "io.matthewbradshaw.experimental.jmonkeyengine.Main",
    runtime_deps = [":game_lib"],
)
