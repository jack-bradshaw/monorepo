load("@toolchain_utils//toolchain/triplet:defs.bzl", "ToolchainTripletInfo")

visibility("public")

TRIPLET = ToolchainTripletInfo("{{value}}")
