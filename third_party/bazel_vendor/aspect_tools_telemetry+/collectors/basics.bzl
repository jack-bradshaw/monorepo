"""
Some basic collectors.
"""


def _shell(repository_ctx):
    """Detect the shell."""

    return repository_ctx.os.environ.get("SHELL")

def _os(repository_ctx):
    return repository_ctx.os.name


def _arch(repository_ctx):
    return repository_ctx.os.arch


def register():
    return {
        "shell": _shell,
        "os": _os,
        "arch": _arch,
    }
