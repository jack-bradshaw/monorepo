load(":TripletInfo.bzl", "TripletInfo")

visibility("//toolchain/...")

DOC = """Provides a machine triplet.

A simple rule that provides a `ToolchainTripletInfo` provider.

The resulting provider can be used in other rules to understand triplet values.

Running the target with `bazel run` will result in the triplet being printed.

The triplet runnable output is particularly useful for the resolved host triplet at `@toolchain_utils//toolchain/triplet:host`
"""

ATTRS = {
    "value": attr.string(
        doc = "A triplet value that overrides `name`.",
    ),
    "template": attr.label(
        doc = "The executable script template.",
        default = ":template",
        allow_single_file = True,
        executable = False,
        cfg = "exec",
    ),
}

def implementation(ctx):
    value = ctx.attr.value or ctx.label.name
    triplet = TripletInfo(value)

    output = ctx.actions.declare_file("{}/{}.txt".format(ctx.label.name, value))
    ctx.actions.write(
        output = output,
        content = value,
    )

    substitutions = ctx.actions.template_dict()
    substitutions.add("{{triplet}}", value)

    executable = ctx.actions.declare_file("{}/{}.{}".format(ctx.label.name, value, ctx.file.template.extension))
    ctx.actions.expand_template(
        output = executable,
        template = ctx.file.template,
        computed_substitutions = substitutions,
        is_executable = True,
    )

    default = DefaultInfo(
        executable = executable,
        files = depset([output]),
        runfiles = ctx.runfiles([output]),
    )

    return [triplet, default]

toolchain_triplet = rule(
    doc = DOC,
    attrs = ATTRS,
    implementation = implementation,
    provides = [
        DefaultInfo,
        TripletInfo,
    ],
    executable = True,
)

triplet = toolchain_triplet
