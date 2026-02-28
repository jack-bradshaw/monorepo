load("@rules_shell//shell:sh_binary.bzl", "sh_binary")

package(default_visibility = ["//visibility:public"])

sh_binary(
    name = "bats",
    srcs = ["src/bin/bats"],
    data = glob([
        "src/libexec/bats-core/**",
        "src/lib/**",
    ]),
)
