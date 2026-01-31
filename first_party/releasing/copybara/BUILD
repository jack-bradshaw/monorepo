load("@rules_java//java:defs.bzl", "java_binary")
load("//first_party/bash_runfiles:shell.bzl", "sh_library_with_runfiles")

exports_files(["launcher.sh"])

sh_library_with_runfiles(
    name = "launcher_lib",
    srcs = ["launcher.sh"],
    visibility = ["//visibility:public"],
)

java_binary(
    name = "copybara",
    main_class = "com.google.copybara.Main",
    runtime_deps = [
        "@copybara//jar",
    ],
    visibility = ["//visibility:public"],
)
