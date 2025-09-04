"""
Machinery for computing anonymous aggregation IDs.
"""

load("@bazel_skylib//lib:paths.bzl", "paths")
load(":utils.bzl", "hash")


def _repo_id(repository_ctx):
    """Try to extract an aggregation ID from the repo context.

    This strategy scans for a README-like file in some known locations and known
    formats and hashes the first few lines if we can find one. The intuition
    here is that README files in general are highly stable and in common README
    structures the first few lines especially contain an extremely stable title
    and summary only.

    As a fallback we go to the first few lines of the MODULE.bazel file. This is
    expected to be less stable than a README file generally because a
    MODULE.bazel could be a simple listing of dependencies with nothing else. In
    practice the first several lines are likely a comment or a `module()`
    invocation which will be highly stable.

    We consider other possible sources of stable identifiers such as a version
    control remote URL out of bounds because they may contain secrets and
    because accessing them without invoking commands is challenging.

    """

    readme_file = None

    for prefix in [
        "",
        "doc",
        "docs",
        "Doc",
        "Docs",
    ]:
        for base in [
            "README",
            "readme",
            "Readme",
            "index",
        ]:
            # Alphabetically
            for ext in [
                "",
                ".adoc",
                ".asc",
                ".asciidoc",
                ".markdown",
                ".md",
                ".mdown",
                ".mkdk",
                ".org",
                ".rdoc",
                ".rst",
                ".textile",
                ".txt",
                ".wiki",
            ]:
                dir = repository_ctx.workspace_root
                if prefix:
                    dir = paths.join(str(dir), prefix)
                file = repository_ctx.path(paths.join(str(dir), base + ext))
                if file.exists:
                    readme_file = file
                    break

            if readme_file:
                break

        if readme_file:
            break

    if not readme_file:
        readme_file = repository_ctx.path(paths.join(str(repository_ctx.workspace_root), "MODULE.bazel"))

    content = "\n".join(repository_ctx.read(readme_file).split("\n")[:4])
    return hash(repository_ctx, content)


def _repo_user(repository_ctx):
    """Try to extract a fingerprint for the user who initiated the build.

    Note that we salt the user IDs with the identified project ID to prevent
    correllation of user behavior across projects.

    """

    user = None
    for var in [
        "BUILDKITE_BUILD_AUTHOR_EMAIL", # Buildkite
        "GITHUB_ACTOR",                 # GH/Gitea/Forgejo
        "GITLAB_USER_EMAIL",            # GL
        "CIRCLE_USERNAME",              # Circle
        # TODO: Jenkins
        "DRONE_COMMIT_AUTHOR",          # Drone
        "DRONE_COMMIT_AUTHOR_EMAIL",    # Drone
        "CI_COMMIT_AUTHOR",             # Woodpecker
        "CI_COMMIT_AUTHOR_EMAIL",       # Woodpecker
        # TODO: Travis
        "LOGNAME",                      # Generic unix
        "USER",                         # Generic unix
    ]:
        user = repository_ctx.os.environ.get(var)
        if user:
            break

    if user:
        return hash(repository_ctx, str(_repo_id(repository_ctx)) + ";" + user)


def register():
    return {
        "id": _repo_id,
        "user": _repo_user,
    }
