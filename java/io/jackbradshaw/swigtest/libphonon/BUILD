load("@rules_cc//cc:defs.bzl", "cc_library", "cc_binary", "cc_import")

cc_import(
    name = "dylib",
    shared_library = "libphonon.dylib",
    alwayslink = True,
)

cc_library(
    name = "macos_jni",
    hdrs = ["jni.h", "jni_md.h"],
)

cc_library(
    name = "wrap",
    srcs = ["libphonon_wrap.c", "phonon.h", "phonon_version.h", "phonon_interfaces.h"],
    deps = [":dylib", ":macos_jni"],
    visibility = ["//visibility:public"]
)
