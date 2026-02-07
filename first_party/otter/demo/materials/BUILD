load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/otter/demo:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "materials",
    testonly = True,
    srcs = ["Materials.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "impl",
    testonly = True,
    srcs = ["MaterialsImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":materials",
        "//first_party/otter/demo:scope",
        "//first_party/otter/demo/constants",
        "//first_party/otter/engine/core",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "module",
    testonly = True,
    srcs = ["MaterialsModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":impl",
        ":materials",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
