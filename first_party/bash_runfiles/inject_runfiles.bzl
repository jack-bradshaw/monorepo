def _boilerplate():
    """Returns the label of the file containing the Bash runfiles initialization boilerplate."""
    return "//third_party/bazel_bash_runfiles:boilerplate.sh"

def inject_runfiles(name, srcs, suffix, visibility = None):
    """Creates a genrule that injects the runfiles boilerplate into the source files.

    Args:
        name: The name of the parent target (for scoping genrules), string, required.
        srcs: A list of source files, list of strings, required.
        suffix: The suffix to append to output filename, string, required.
        visibility: The visibility to pass to the generated genrules, list of strings, optional, defaults to None.

    Returns:
        List of generated output files.
    """

    if not srcs:
        fail("'srcs' must be provided")

    if not suffix:
        suffix = "_with_runfiles"

    processed_files = []
    for src in srcs:
        if "." in src:
            base, ext = src.rsplit(".", 1)
            output_file = "{}{}.{}".format(base, suffix, ext)
        else:
            output_file = src + suffix

        processed_files.append(output_file)

        # Sanitize the source label for use in a target name
        sanitized_src = src.replace("//", "").replace("/", "_").replace(":", "_").replace(".", "_")

        native.genrule(
            name = "{}_{}_generator".format(name, sanitized_src),
            srcs = [src, _boilerplate()],
            outs = [output_file],
            cmd = """
                source_path=$(location {src})
                boilerplate_path=$(location {boilerplate})
                
                # The quad {{{{ and }}}} escapes Starlark string interpolation to results in
                # {{RUNFILES_BOILERPLATE}} in the shell script
                marker='{{{{RUNFILES_BOILERPLATE}}}}'

                # This sed command replaces the preprocessor directive with the boilerplate. It
                # has two parts:
                #
                # 1. '/marker/r file' : When the marker is matched, read the contents 
                #    of 'file' and append it to the output stream.
                # 2. '/marker/d' : Match the marker line again and delete the marker from the output
                #    stream.
                sed -e "/$$marker/r $$boilerplate_path" \
                    -e "/$$marker/d" \
                    "$$source_path" > $@
            """.format(src = src, boilerplate = _boilerplate()),
            visibility = visibility,
        )
    return processed_files
