load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/obelisk:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "service",
    srcs = ["Service.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/obelisk/core/adapters:inflow_adapter",
        "//first_party/obelisk/core/adapters:outflow_adapter",
        "//first_party/obelisk/core/services:control_service",
        "//first_party/obelisk/core/services:data_service",
        "//first_party/obelisk/core/services:error_service",
        "//first_party/obelisk/core/services:logging",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["ServiceImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":service",
        "//first_party/obelisk/core/adapters:inflow_adapter",
        "//first_party/obelisk/core/adapters:outflow_adapter",
        "//first_party/obelisk/core/model:log_level",
        "//first_party/obelisk/core/model:source",
        "//first_party/oksp/model:log_level",
        "//first_party/oksp/model:source",
        "//first_party/oksp/service",
        "//first_party/coroutines:component_impl",
        "//first_party/coroutines/io",
        "//first_party/sluice:sluice",
        "//first_party/sluice:factory",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["ServiceModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":service",
        ":impl",
    ]
)
