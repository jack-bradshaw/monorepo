visibility("//...")

DOC = """Performs a difference between two files and fails if any are found.

```py
diff_file_test(
    name = "test",
    a = ":some-file.txt",
    b = ":some-file.txt",
    size = "small",
)
```
"""

ATTRS = {
    "a": attr.label(
        doc = "A file to compare.",
        allow_single_file = True,
        mandatory = True,
    ),
    "b": attr.label(
        doc = "Another file to compare.",
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
    rendered = ctx.actions.declare_file("{}.rendered.{}".format(ctx.label.name, ctx.file.template.extension))

    ctx.actions.expand_template(
        output = rendered,
        template = ctx.file.template,
        is_executable = True,
        substitutions = {
            "{{a}}": ctx.file.a.short_path,
            "{{b}}": ctx.file.b.short_path,
        },
    )

    files = depset([rendered])
    runfiles = ctx.runfiles(files = [ctx.file.a, ctx.file.b])

    return DefaultInfo(
        executable = rendered,
        files = files,
        runfiles = runfiles,
    )

diff_file_test = rule(
    doc = DOC,
    attrs = ATTRS,
    implementation = implementation,
    test = True,
)

test = diff_file_test
