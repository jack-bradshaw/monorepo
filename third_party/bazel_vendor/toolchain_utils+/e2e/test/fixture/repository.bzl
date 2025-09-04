DOC = "Creates a test fixture repository."

visibility("//...")

ATTRS = {
    "srcs": attr.label_list(
        doc = "Files that should be put into the repository.",
        default = [":hello-world.txt"],
    ),
    "template": attr.label(
        doc = "The template that is expanded into the `BUILD.bazel`.",
        default = ":BUILD.tmpl.bazel",
        allow_single_file = True,
    ),
}

def implementation(rctx):
    version = tuple([int(v) for v in native.bazel_version.split(".")[:2]])
    if rctx.name.endswith("never") and version >= (7, 4):
        fail("The `{}` repository should not be fetched. This is a case where Bazel is over-fetching.")

    map = {l: "{}/{}".format(l.package, l.name) for l in rctx.attr.srcs}

    for label, path in map.items():
        rctx.file(path, content = rctx.read(label), executable = False)

    rctx.template("BUILD.bazel", rctx.attr.template, {
        "{{srcs}}": repr(map.values()),
    }, executable = False)

fixture = repository_rule(
    doc = DOC,
    implementation = implementation,
    attrs = ATTRS,
    configure = True,
)
