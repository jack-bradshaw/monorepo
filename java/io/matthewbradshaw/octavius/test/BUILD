load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "cubegame_lib",
    srcs = [
        "CubeGame.kt",
        "Main.java",
        "MainKt.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        "//java/io/matthewbradshaw/octavius/core",
        "//java/io/matthewbradshaw/octavius",
        "//java/io/matthewbradshaw/octavius/ticker",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

java_binary(
    name = "cubegame_app",
    main_class = "io.matthewbradshaw.octavius.test.Main",
    runtime_deps = [":cubegame_lib"],
)
