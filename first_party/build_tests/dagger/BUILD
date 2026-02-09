load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "dagger_library_must_build",
    srcs = ["TestComponent.java"],
    deps = [
        "//:dagger",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
