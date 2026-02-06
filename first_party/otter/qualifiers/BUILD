load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:defs.bzl", "quarantined")

PUBLICITY = quarantined("//first_party/otter/qualifiers")

kt_jvm_library_with_dagger(
    name = "qualifiers",
    srcs = ["Qualifiers.kt"],
    visibility = PUBLICITY,
)
