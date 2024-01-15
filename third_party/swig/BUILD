package(
    default_visibility = ["//visibility:public"],
)

genrule(
    name = "macos",
    srcs = ["swig-macos-4.1.1"],
    outs = ["swig-macos"],
    cmd = "cp $(location swig-macos-4.1.1) $(@D)/swig-macos",
)

genrule(
    name = "linux",
    srcs = ["swig-linux-4.0.2"],
    outs = ["swig-linux"],
    cmd = "cp $(location swig-linux-4.0.2) $(@D)/swig-linux",
)

genrule(
    name = "windows",
    srcs = ["swig-windows-4.1.1.exe"],
    outs = ["swig-windows"],
    cmd = "cp $(location swig-windows-4.1.1.exe) $(@D)/swig-windows",
)