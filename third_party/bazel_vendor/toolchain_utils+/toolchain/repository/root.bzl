visibility("//...")

def root(rctx, label):
    workspace = label.relative("WORKSPACE")
    path = rctx.path(workspace)
    if path.exists:
        return path.dirname

    repo = label.relative("REPO.bazel")
    path = rctx.path(repo)
    if path.exists:
        return path.dirname

    return None
