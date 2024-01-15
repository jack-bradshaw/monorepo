load("@rules_cc//cc:defs.bzl", "cc_library")
load("//bazel/swig:swig.bzl", "swig_java_wrappers")

#swig_jni_bridge(
#    name = "jni_bridge",
#    srcs = ["//third_party/valvesoftware/steamaudio/headers"],
#    interface = "libphonon.i",
#)

swig_java_wrappers(
    name = "java_wrappers",
    srcs = ["//third_party/valvesoftware/steamaudio/headers"],
    interface = "libphonon.i"
)

cc_library(
    name = "native_macos",
    deps = [":jni_bridge", "//third_party/jni/macos", "//third_party/valvesoftware/steamaudio/headers"],
    visibility = ["//visibility:public"]
)

java_library(
    name = "steamaudio4j",
    srcs = ["SteamAudio.java"],
    data = [":native_macos"],
    deps = ["@bazel_tools//tools/java/runfiles", ":java_wrappers"]
)