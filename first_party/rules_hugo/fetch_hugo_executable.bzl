load("@rules_hugo//hugo:rules.bzl", "hugo_repository")

# Implementation of fetch_hugo_executable.
def _fetch_hugo_executable(module_ctx):
    hugo_repository(
        name = "hugo",
        version = "0.152.2",
    )

# Downloads the hugo executable and stores it in an external repo called `hugo`. The version is hardcoded.
fetch_hugo_executable = module_extension(
    implementation = _fetch_hugo_executable,
)
