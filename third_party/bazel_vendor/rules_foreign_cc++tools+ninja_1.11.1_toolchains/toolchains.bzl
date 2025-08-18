def register_toolchains():
    native.register_toolchain(
    "@ninja_1.11.1_linux//:ninja_tool",
    "@ninja_1.11.1_mac//:ninja_tool",
    "@ninja_1.11.1_win//:ninja_tool",
    )
