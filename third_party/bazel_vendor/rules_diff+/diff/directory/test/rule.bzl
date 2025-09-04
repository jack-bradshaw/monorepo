visibility("//...")

DOC = """Performs a difference between two directories and fails if any are found.

```py
diff_directory_test(
    name = "test",
    a = ":some-directory",
    b = ":some-directory",
    size = "small",
)
```
"""

ATTRS = {
    "a": attr.label(
        doc = "A directory to compare.",
        allow_single_file = True,
        mandatory = True,
    ),
    "b": attr.label(
        doc = "Another directory to compare.",
        allow_single_file = True,
        mandatory = True,
    ),
    "template": attr.label(
        doc = "The script template to be rendered into the test executable.",
        allow_single_file = True,
        default = ":template",
    ),
}

def implementation(ctx):
    diff = ctx.toolchains["//diff/toolchain/diff:type"]

    rendered = ctx.actions.declare_file("{}.rendered.{}".format(ctx.label.name, ctx.file.template.extension))

    if not ctx.file.a.is_directory:
        fail("Must provide a directory to compare: {}".format(ctx.file.a))
    if not ctx.file.b.is_directory:
        fail("Must provide a directory to compare: {}".format(ctx.file.b))

    ctx.actions.expand_template(
        output = rendered,
        template = ctx.file.template,
        is_executable = True,
        substitutions = {
            "{{diff}}": diff.executable.short_path,
            "{{a}}": ctx.file.a.short_path,
            "{{b}}": ctx.file.b.short_path,
        },
    )

    files = depset([rendered])
    runfiles = ctx.runfiles(files = [ctx.file.a, ctx.file.b])
    runfiles = runfiles.merge(diff.default.default_runfiles)

    return DefaultInfo(
        executable = rendered,
        files = files,
        runfiles = runfiles,
    )

diff_directory_test = rule(
    doc = DOC,
    attrs = ATTRS,
    implementation = implementation,
    toolchains = ["//diff/toolchain/diff:type"],
    test = True,
)

test = diff_directory_test
