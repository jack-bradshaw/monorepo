load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "lib",
    srcs = glob(["*.java", "*.kt"]),
    deps = ["//java/io/jackbradshaw/swigtest/wrappers", "@bazel_tools//tools/java/runfiles"],
    
)

java_binary(
    name="bin",
    runtime_deps = [":lib"],
    main_class = "io.jackbradshaw.swigtest.Main",
    data = ["//java/io/jackbradshaw/swigtest/libphonon:wrap"],
)
