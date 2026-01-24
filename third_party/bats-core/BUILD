package(default_visibility = ["//visibility:public"])

sh_binary(
    name = "bats",
    srcs = ["bin/bats"],
    data = glob([
        "libexec/bats-core/**",
        "lib/**",
    ]),
)
