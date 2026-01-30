package(default_visibility = ["//visibility:public"])

sh_binary(
    name = "bats",
    srcs = ["src/bin/bats"],
    data = glob([
        "src/libexec/bats-core/**",
        "src/lib/**",
    ]),
)
