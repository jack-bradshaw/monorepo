load(":repository.bzl", _which = "which")

visibility("public")

toolchain_local_which = _which
