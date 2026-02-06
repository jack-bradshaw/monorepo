load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:defs.bzl", "quarantined")

PUBLICITY = quarantined("//first_party/otter/engine/core")

kt_jvm_library_with_dagger(
    name = "core",
    srcs = ["EngineCore.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/otter:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["EngineCoreImpl.kt"],
    deps = [
        ":core",
        "//first_party/otter:scope",
        "//first_party/otter/config:kt_jvm_proto",
        "//first_party/otter/coroutines",
        "//first_party/otter/openxr/manifest/installer",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["EngineCoreModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":core",
        ":impl",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_vr",
    ],
)
