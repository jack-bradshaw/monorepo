load(":repository.bzl", _select = "select")

visibility("public")

toolchain_local_select = _select
