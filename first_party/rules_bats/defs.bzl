def bats_test(name, srcs, data = [], args = [], **kwargs):
    """Generates a `sh_test` target that runs Bats tests.

    Details of Bats: https://github.com/bats-core/bats-core.

    Args:
        name: The name of the generated target.
        srcs: The tests to run (list of labels, must be `.bats` files).
        data: Arbitrary files required at test runtime (list of labels, may be any file type).
        args: Arguments passed to Bats at runtime.
        **kwargs: Arguments passed to the generated target at build time.
    """

    for src in srcs:
        if not src.endswith(".bats"):
            fail("All sources must end with .bats, but found: %s" % src)

    # The args are configured to match the expectations of the `runner.sh` script.
    native.sh_test(
        name = name,
        srcs = ["//first_party/rules_bats:runner.sh"],
        # Args are configured to satisfy the expectations of runner.sh (bats script, then test
        # files, then arbitrary args).
        args = [
            "$(location //third_party/bats-core:bats)",
        ] + [
            "$(location {})".format(src)
            for src in srcs
        ] + args,
        data = srcs + data + [
            "//third_party/bats-core:bats",
        ],
        **kwargs
    )
