visibility("//...")

DOC = "Creates a declared directory of fixture files."

ATTRS = {
    "srcs": attr.label_list(
        doc = "Fixture files to copy into the directory.",
        allow_empty = False,
        allow_files = True,
        mandatory = True,
    ),
    "template": attr.label(
        doc = "Directory creation script template.",
        allow_single_file = True,
        default = ":template",
    ),
}

def _path(file):
    return file.path

def implementation(ctx):
    dir = ctx.actions.declare_directory(ctx.label.name)

    cp = ctx.toolchains["@rules_coreutils//coreutils/toolchain/cp:type"]
    mkdir = ctx.toolchains["@rules_coreutils//coreutils/toolchain/mkdir:type"]

    template = ctx.file.template
    rendered = ctx.actions.declare_file("{}.rendered.{}".format(ctx.label.name, template.extension))
    ctx.actions.expand_template(
        template = template,
        output = rendered,
        is_executable = True,
        substitutions = {
            "{{cp}}": cp.executable.path,
            "{{mkdir}}": mkdir.executable.path,
        },
    )

    args = ctx.actions.args()
    args.add(dir.path)
    args.add_all(ctx.files.srcs, map_each = _path)

    ctx.actions.run(
        outputs = [dir],
        inputs = ctx.files.srcs,
        executable = rendered,
        tools = [cp.executable, mkdir.executable],
        arguments = [args],
        mnemonic = "FixtureDirectory",
        progress_message = "Fixture directory %{output}",
    )

    return DefaultInfo(files = depset([dir]))

directory = rule(
    doc = DOC,
    attrs = ATTRS,
    implementation = implementation,
    toolchains = [
        "@rules_coreutils//coreutils/toolchain/cp:type",
        "@rules_coreutils//coreutils/toolchain/mkdir:type",
    ],
)
