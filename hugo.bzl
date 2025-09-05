load("@rules_hugo//hugo:rules.bzl", "hugo_repository", "github_hugo_theme")

# Creates
def _hugo_impl(module_ctx):
    hugo_repository(
        name = "hugo",
    )

    for mod in module_ctx.modules:
      for theme in mod.tags.theme:
        if mod.tags.theme:
          github_hugo_theme(
            name = "hugo_theme_%s" % theme.name,
            owner = theme.repo_owner,
            repo = theme.repo_name,
            commit = theme.commit_id,
          )

    

# A hugo theme hosted in a GitHub repository.
# name: a repository will be created at hugo_theme_$name
# github_account: the GitHub account hosting the theme
# github_repository: the GitHub repository in the github_account hosting the theme
# commit_id: the commit ID to retrieve from the github_repository in github_account
_theme = tag_class(attrs = {
    "name": attr.string(),
    "repo_owner": attr.string(),
    "repo_name": attr.string(),
    "commit_id": attr.string(),
})

hugo = module_extension(
    implementation = _hugo_impl,
    tag_classes = {"theme": _theme},
)
