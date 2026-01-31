def copybara_release(name, source_dir, destination_url, transformations = [], **kwargs):
    """Defines a Copybara mirroring target for repository segmentation.

    This macro facilitates mirroring a monorepo directory to a standalone repository.

    Args:
        name: The name of the target, string, required.
        source_dir: The directory within the monorepo to mirror, string (path), required.
        destination_url: The URL of the destination repository, string (URL), required.
        transformations: A list of Copybara transformations to apply, list of strings, optional, defaults to [].
        **kwargs: Arbitrary arguments to forward to the underlying rule, dictionary, optional.
    """

    config_name = name + "_config"
    config_file = "copy.bara.sky"

    config_content = [
        "core.workflow(",
        "    name = \"default\",",
        "    origin = git.origin(",
        "        url = \"file://\" + \"@@MONOREPO_ROOT@@\",",
        "        ref = \"@@REF@@\",",
        "    ),",
        "    destination = git.github_destination(",
        "        url = \"%s\"," % destination_url,
        "        push = \"main\",",
        "    ),",
        "    origin_files = glob([\"%s/**\"])," % source_dir,
        "    authoring = authoring.pass_thru(\"Jack Bradshaw <jack@jackbradshaw.io>\"),",
        "    transformations = [",
        "        core.move(\"%s\", \"\")," % source_dir,
    ]

    for t in transformations:
        config_content.append("        %s," % t)

    config_content.append("    ],")
    config_content.append(")")

    # Join the lines with newlines
    config_str = "\n".join(config_content)

    # Generate the Copybara config file
    native.genrule(
        name = config_name,
        outs = [config_file],
        cmd = "echo '%s' > $@" % config_str,
    )

    native.sh_binary(
        name = name,
        srcs = ["//first_party/releasing/copybara:launcher_with_runfiles.sh"],
        args = ["$(location :" + config_file + ")"],
        data = [":" + config_file],
        deps = ["//first_party/releasing/copybara:launcher_lib"],
        **kwargs
    )
