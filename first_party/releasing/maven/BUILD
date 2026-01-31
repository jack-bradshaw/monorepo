exports_files([
    "pom_template.xml",
    "release_launcher.sh",
])

sh_binary(
    name = "release",
    srcs = ["release.sh"],
    visibility = ["//visibility:public"],
)

sh_library_with_runfiles(
    name = "release_launcher_lib",
    srcs = ["release_launcher.sh"],
    visibility = ["//visibility:public"],
)

sh_binary(
    name = "release_launcher",
    srcs = ["release_launcher.sh"],
    visibility = ["//visibility:public"],
)
