SwigInfo = provider(
    doc = "Details for invoking swig via the swig toolchain.",
    fields = ["executable"],
)

def _swig_toolchain_impl(ctx):
    toolchain_info = platform_common.ToolchainInfo(
        swiginfo = SwigInfo(
            executable = ctx.attr.executable,
        ),
    )
    return [toolchain_info]

swig_toolchain = rule(
    implementation=_swig_toolchain_impl,
    attrs= {
        "executable": attr.label(mandatory = True, allow_single_file = True)
    },
)

def _swig_java_wrappers_impl(ctx):
    input_files = []
    input_files.append(ctx.file.interface)
    input_files.extend([src for src in ctx.files.srcs])
    input_files.extend([dep.files for dep in ctx.attr.deps])

    output_dir = ctx.actions.declare_directory("generated_files")
   
    ctx.actions.run(
        inputs=input_files,
        outputs=[output_dir],
        arguments = ["-java", ctx.file.interface.path],
        executable = ctx.toolchains[":toolchain_type"].swiginfo.executable,
    )

    return DefaultInfo(files = output_dir.files.to_list())

swig_java_wrappers = rule(
    implementation = _swig_java_wrappers_impl,
    attrs = {
        "interface": attr.label(
            allow_single_file=[".i"],
            mandatory = True,
        ),
        "srcs": attr.label_list(
            default = [],
            allow_files=True
        ),
        "deps": attr.label_list(
            default = []
        ),
    },
    toolchains = [
        "//bazel/swig:toolchain_type",
    ],
)