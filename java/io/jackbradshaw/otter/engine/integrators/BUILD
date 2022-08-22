load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

kt_jvm_library(
    name = "integrator",
    srcs = [
        "Integrator.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/math:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/math:proto_extensions",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/structure/bases",
        "//java/io/jackbradshaw/otter/structure/controllers",
        "//java/io/jackbradshaw/otter/structure/controllers:java_lite_protos",
        "@maven//:com_google_guava_guava",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "LightIntegrator.kt",
        "ParticleEffectIntegrator.kt",
        "PhysicsCharacterIntegrator.kt",
        "PhysicsColliderIntegrator.kt",
        "PhysicsJointIntegrator.kt",
        "SceneProcessorIntegrator.kt",
        "SpatialIntegrator.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":integrator",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/math:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/math:proto_extensions",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/structure/bases",
        "//java/io/jackbradshaw/otter/structure/controllers",
        "//java/io/jackbradshaw/otter/structure/controllers:java_lite_protos",
        "@maven//:com_google_guava_guava",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
