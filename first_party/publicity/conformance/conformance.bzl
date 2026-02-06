load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

def conformance_test(name, first_party_root = "//first_party"):
    """Generates the repository-wide publicity conformance test target.

    Args:
        name: The name of the test target, string, required.
        **kwargs: Additional arguments to pass to the test target, dict, optional, defaults to {}.
    """

    kt_jvm_test(
        name = name,
        srcs = [],  # Test class is included in deps and selected with test_class.
        jvm_flags = ["-DFIRST_PARTY_ROOT=%s" % first_party_root],
        test_class = "com.jackbradshaw.publicity.conformance.ConformanceTest",
        deps = ["//first_party/publicity/conformance:conformance_impl"],
    )
