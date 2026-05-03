load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "BackstabApplication.kt",
    ],
    resource_strip_prefix = "first_party/backstab/oksp/application/resources",
    resources = glob(["resources/**"]),
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/backstab/core:component_impl",
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/oksp/adapters",
        "//first_party/obelisk/core/services:control_service",
        "//first_party/obelisk/core/services:data_service",
        "//first_party/obelisk/core/services:error_service",
        "//first_party/obelisk/core/services:logging",
        "//first_party/obelisk/core/model:log_level",
        "//first_party/obelisk/core/model:source",
        "//first_party/obelisk/core/adapters:inflow_adapter",
        "//first_party/obelisk/core/adapters:outflow_adapter",
        "//first_party/obelisk:scope",

        "//first_party/obelisk/oksp:component",
        "//first_party/obelisk/oksp:component_impl",
        "//first_party/obelisk/oksp/service:service",
        "//first_party/coroutines:component_impl",
        "//first_party/oksp/application",
        "//first_party/oksp/application:component",
        "//first_party/sluice",
        "//first_party/sluice:component",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

