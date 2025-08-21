load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "materials",
    testonly = True,
    srcs = [
        "Materials.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
    ],
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "impl",
    testonly = True,
    srcs = [
        "MaterialsImpl.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
    ],
    deps = [
        ":materials",
        "//first_party/com/jackbradshaw/otter/demo:scope",
        "//first_party/com/jackbradshaw/otter/demo/constants",
        "//first_party/com/jackbradshaw/otter/engine/core",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "module",
    testonly = True,
    srcs = [
        "MaterialsModule.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
    ],
    deps = [
        ":impl",
        ":materials",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
