load(":rule.bzl", _target = "target")

visibility("public")

toolchain_symlink_target = _target
