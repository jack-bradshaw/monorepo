load(":rule.bzl", _test = "test")

visibility("public")

toolchain_test = _test
