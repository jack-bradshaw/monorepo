"""
Collectors designed to inspect the CI environment.
"""


def _is_ci(repository_ctx):
    """Detect if the build is happening in 'CI'. Pretty much all the vendors set this."""

    return repository_ctx.os.environ.get("CI") != None


def _build_counter(repository_ctx):
    """Try to get a counter for the build.

    This allows estimation of rate of builds.
    """

    # Note that on GHA run numbers may be reused and there's a retry count
    # subcounter. Since that's the only platform to do so, we're going to just
    # pretend it doesn't exist.
    for counter_var in [
        "BUILDKITE_BUILD_NUMBER",  # Buildkite
        "GITHUB_RUN_NUMBER",       # Github/forgejo/gitea
        "CI_PIPELINE_IID",         # Gitlab
        "CIRCLE_BUILD_NUM",        # CircleCI
        "DRONE_BUILD_NUMBER",      # Drone
        "BUILD_NUMBER",            # Jenkins
        "CI_PIPELINE_NUMBER",      # Woodpecker?
        "TRAVIS_BUILD_NUMBER",     # Travis
    ]:
        counter = repository_ctx.os.environ.get(counter_var)
        if counter:
            return counter


def _build_runner(repository_ctx):
    """Try to identify the CI/CD runner environment."""

    for var, platform in [
        ("BUILDKITE_BUILD_NUMBER", "buildkite"),
        ("FORGEJO_TOKEN", "forgejo"),  # FIXME: This value is a secret, avoid
        ("GITEA_ACTIONS", "gitea"),
        ("GITHUB_RUN_NUMBER", "github-actions"),
        ("GITLAB_CI", "gitlab"),
        ("CIRCLE_BUILD_NUM", "circleci"),
        ("DRONE_BUILD_NUMBER", "drone"),
        ("BUILD_NUMBER", "jenkins"),
        ("TRAVIS", "travis")
    ]:
        val = repository_ctx.os.environ.get(var)
        if val != None:
            return platform

    # Set on Woodpecker and in some other environments
    return repository_ctx.os.environ.get("CI_SYSTEM_NAME")


def _repo_org(repository_ctx):
    """Try to extract the organization name."""

    repo = None
    for var in [
        "BUILDKITE_ORGANIZATION_SLUG", # Buildkite
        "GITHUB_REPOSITORY_OWNER",     # GH/Gitea/Forgejo
        "CI_PROJECT_NAMESPACE",        # GL
        "CIRCLE_PROJECT_USERNAME",     # Circle
        # TODO: Jenkins only has the fetch URL which seems excessively sensitive
        "DRONE_REPO_NAMESPACE",        # Drone
        "CI_REPO_OWNER",               # Woodpecker
        "TRAVIS_REPO_SLUG",            # Travis
    ]:
        repo = repository_ctx.os.environ.get(var)
        if repo:
            return repo


def register():
    return {
        "ci": _is_ci,
        "counter": _build_counter,
        "runner": _build_runner,
        "org": _repo_org,
    }
