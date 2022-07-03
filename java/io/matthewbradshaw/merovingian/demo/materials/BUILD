load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "materials",
    testonly = True,
    srcs = [
        "Materials.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "impl",
    testonly = True,
    srcs = [
        "MaterialsImpl.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":materials",
        "//java/io/matthewbradshaw/merovingian/demo:scope",
        "//java/io/matthewbradshaw/merovingian/demo/constants",
        "//java/io/matthewbradshaw/merovingian/engine",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "module",
    testonly = True,
    srcs = [
        "MaterialsModule.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":impl",
        ":materials",
        "@maven//:javax_inject_javax_inject",
    ],
)
