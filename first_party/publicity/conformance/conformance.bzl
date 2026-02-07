load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_binary")

def conformance_test(name, first_party_root = "first_party", **kwargs):
    """Generates the repository-wide publicity conformance test target.

    The target is not actually a test, but rather a binary, which returns 1 or 0 and writes
    success/failure details to STDIO.

    Args:
        name: The name of the test target, string, required.
        first_party_root: The relative path to the first party root within the workspace.
        **kwargs: Additional arguments to pass to the underlying kt_jvm_binary rule.
    """

    # No need to set BUILD_WORKSPACE_DIRECTORY because Bazel sets it automatically.
    jvm_flags = kwargs.pop("jvm_flags", [])
    jvm_flags.append("-DFIRST_PARTY_ROOT=%s" % first_party_root)

    runtime_deps = kwargs.pop("runtime_deps", [])
    runtime_deps.append("//first_party/publicity/conformance/entrypoint:entrypoint")

    kt_jvm_binary(
        name = name,
        main_class = "com.jackbradshaw.publicity.conformance.entrypoint.EntryPoint",
        jvm_flags = jvm_flags,
        runtime_deps = runtime_deps,
        **kwargs
    )
