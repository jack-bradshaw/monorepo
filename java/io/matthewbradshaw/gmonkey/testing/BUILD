load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "cubegame_lib",
    testonly = True,
    srcs = [
        "CubeGame.kt",
        "Main.java",
        "MainKt.kt",
        "Test.kt",
    ],
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/matthewbradshaw/gmonkey/octavius",
        "//java/io/matthewbradshaw/gmonkey/octavius/engine",
        "//java/io/matthewbradshaw/gmonkey/octavius/ticker",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

java_binary(
    name = "cubegame_app",
    testonly = True,
    main_class = "io.matthewbradshaw.gmonkey.testing.Main",
    visibility = [
        "//javatests:__subpackages__",
    ],
    runtime_deps = [":cubegame_lib"],
)
