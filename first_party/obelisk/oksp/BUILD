load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/obelisk:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "component",
    srcs = ["ObeliskOkspComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/obelisk/oksp/service",
    ]
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ObeliskOkspComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        "//first_party/obelisk:scope",
        "//first_party/obelisk/oksp/service:module",
        "//first_party/coroutines:component",
        "//first_party/sluice:component",
        "//first_party/oksp/application:component",
    ]
)
