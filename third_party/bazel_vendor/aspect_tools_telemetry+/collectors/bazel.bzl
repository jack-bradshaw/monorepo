"""
Some Bazel and repository oriented collectors.
"""

load("@bazel_skylib//lib:paths.bzl", "paths")


def _is_bazelisk(repository_ctx):
    """Detect if the build is using bazelisk; this persists into the repo env state."""

    return repository_ctx.os.environ.get("BAZELISK") != None or repository_ctx.os.environ.get("BAZELISK_SKIP_WRAPPER") != None

def _has_tools_bazel(repository_ctx):
    """Detect if the repository has a tools/bazel wrapper script."""

    return repository_ctx.path(paths.join(str(repository_ctx.workspace_root), "tools/bazel")).exists


def _has_bazel_prelude(repository_ctx):
    """Detect if the repository has a //tools/build_rules/prelude_bazel."""

    return repository_ctx.path(paths.join(str(repository_ctx.workspace_root), "tools/build_rules/prelude_bazel")).exists


def _has_workspace(repository_ctx):
    """Detect if the repository has a WORKSPACE file."""

    return repository_ctx.path(paths.join(str(repository_ctx.workspace_root), "WORKSPACE")).exists or repository_ctx.path(paths.join(str(repository_ctx.workspace_root), "WORKSPACE.bazel")).exists


def _has_module(repository_ctx):
    """Detect if the repository has a MODULE.bazel file."""

    return repository_ctx.path(paths.join(str(repository_ctx.workspace_root), "MODULE.bazel")).exists


def _bazel_version(repository_ctx):
    return native.bazel_version


def _repo_bzlmod(repository_ctx):
    return repository_ctx.attr.deps


def register():
    return {
        "bazelisk": _is_bazelisk,
        "has_bazel_tool": _has_tools_bazel,
        "has_bazel_prelude": _has_bazel_prelude,
        "has_bazel_workspace": _has_workspace,
        "has_bazel_module": _has_module,
        "bazel_version": _bazel_version,
        "deps": _repo_bzlmod,
    }
