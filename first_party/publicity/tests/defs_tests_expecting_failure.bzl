load("@rules_testing//lib:analysis_test.bzl", "analysis_test")
load("@rules_testing//lib:truth.bzl", "matching")
load("//first_party/publicity:defs.bzl", "internal", "quarantined", "restricted")

# Test 1: Quarantined fails when `package_name` does not have a leading "//"

def _quarantined__package_without_slash_subject_impl(ctx):
    quarantined("no_slash_causes_failure")
    return []

quarantined__package_without_slash_subject_rule = rule(implementation = _quarantined__package_without_slash_subject_impl)

def _quarantined__package_without_slash_verifier_impl(env, target):
    env.expect.that_target(target).failures().contains_predicate(matching.contains("Publicity.quarantined: package_name must start with"))

def test_quarantined__package_without_slash():
    quarantined__package_without_slash_subject_rule(name = "quarantined__package_without_slash_target", tags = ["manual"])
    analysis_test(
        name = "quarantined__package_without_slash_test",
        target = ":quarantined__package_without_slash_target",
        impl = _quarantined__package_without_slash_verifier_impl,
        expect_failure = True,
    )

# Test 2: Restricted fails when any `package_name` has a leading "/"

def _restricted__package_with_slash_subject_impl(ctx):
    restricted(["/has_slash_causes_failure"])
    return []

restricted__package_with_slash_subject_rule = rule(implementation = _restricted__package_with_slash_subject_impl)

def _restricted__package_with_slash_verifier_impl(env, target):
    env.expect.that_target(target).failures().contains_predicate(matching.contains("Publicity.restricted: package names must not start with"))

def test_restricted__package_with_slash():
    restricted__package_with_slash_subject_rule(name = "restricted__package_with_slash_target", tags = ["manual"])
    analysis_test(
        name = "restricted__package_with_slash_test",
        target = ":restricted__package_with_slash_target",
        impl = _restricted__package_with_slash_verifier_impl,
        expect_failure = True,
    )

# Test 3: Restricted fails when any `package_name` has a leading "//"

def _restricted__package_with_double_slash_subject_impl(ctx):
    restricted(["//has_double_slash_causes_failure"])
    return []

restricted__package_with_double_slash_subject_rule = rule(implementation = _restricted__package_with_double_slash_subject_impl)

def _restricted__package_with_double_slash_verifier_impl(env, target):
    env.expect.that_target(target).failures().contains_predicate(matching.contains("Publicity.restricted: package names must not start with"))

def test_restricted__package_with_double_slash():
    restricted__package_with_double_slash_subject_rule(name = "restricted__package_with_double_slash_target", tags = ["manual"])
    analysis_test(
        name = "restricted__package_with_double_slash_test",
        target = ":restricted__package_with_double_slash_target",
        impl = _restricted__package_with_double_slash_verifier_impl,
        expect_failure = True,
    )

# Test 4: Restricted fails when `first_party_root` does not have a leading "//"

def _restricted__root_without_slash_subject_impl(ctx):
    restricted(["foo"], first_party_root = "no_slash")
    return []

restricted__root_without_slash_subject_rule = rule(implementation = _restricted__root_without_slash_subject_impl)

def _restricted__root_without_slash_verifier_impl(env, target):
    env.expect.that_target(target).failures().contains_predicate(matching.contains("Publicity: first_party_root must start with '//'"))

def test_restricted__root_without_slash():
    restricted__root_without_slash_subject_rule(name = "restricted__root_without_slash_target", tags = ["manual"])
    analysis_test(
        name = "restricted__root_without_slash_test",
        target = ":restricted__root_without_slash_target",
        impl = _restricted__root_without_slash_verifier_impl,
        expect_failure = True,
    )

# Test 5: Restricted fails when `first_party_root` has only a single leading "/"

def _restricted__root_with_single_slash_subject_impl(ctx):
    restricted(["foo"], first_party_root = "/no_double_slash")
    return []

restricted__root_with_single_slash_subject_rule = rule(implementation = _restricted__root_with_single_slash_subject_impl)

def _restricted__root_with_single_slash_verifier_impl(env, target):
    env.expect.that_target(target).failures().contains_predicate(matching.contains("Publicity: first_party_root must start with '//'"))

def test_restricted__root_with_single_slash():
    restricted__root_with_single_slash_subject_rule(name = "restricted__root_with_single_slash_target", tags = ["manual"])
    analysis_test(
        name = "restricted__root_with_single_slash_test",
        target = ":restricted__root_with_single_slash_target",
        impl = _restricted__root_with_single_slash_verifier_impl,
        expect_failure = True,
    )

# Test 6: Internal fails when `first_party_root` does not have a leading "//"

def _internal__root_without_slash_subject_impl(ctx):
    internal(first_party_root = "no_slash")
    return []

internal__root_without_slash_subject_rule = rule(implementation = _internal__root_without_slash_subject_impl)

def _internal__root_without_slash_verifier_impl(env, target):
    env.expect.that_target(target).failures().contains_predicate(matching.contains("Publicity: first_party_root must start with '//'"))

def test_internal__root_without_slash():
    internal__root_without_slash_subject_rule(name = "internal__root_without_slash_target", tags = ["manual"])
    analysis_test(
        name = "internal__root_without_slash_test",
        target = ":internal__root_without_slash_target",
        impl = _internal__root_without_slash_verifier_impl,
        expect_failure = True,
    )

# Test 7: Internal fails when `first_party_root` has only a single leading "/"

def _internal__root_with_single_slash_subject_impl(ctx):
    internal(first_party_root = "/no_double_slash")
    return []

internal__root_with_single_slash_subject_rule = rule(implementation = _internal__root_with_single_slash_subject_impl)

def _internal__root_with_single_slash_verifier_impl(env, target):
    env.expect.that_target(target).failures().contains_predicate(matching.contains("Publicity: first_party_root must start with '//'"))

def test_internal__root_with_single_slash():
    internal__root_with_single_slash_subject_rule(name = "internal__root_with_single_slash_target", tags = ["manual"])
    analysis_test(
        name = "internal__root_with_single_slash_test",
        target = ":internal__root_with_single_slash_target",
        impl = _internal__root_with_single_slash_verifier_impl,
        expect_failure = True,
    )

# All tests

def all_tests_expecting_failure():
    test_quarantined__package_without_slash()
    test_restricted__package_with_slash()
    test_restricted__package_with_double_slash()
    test_restricted__root_without_slash()
    test_restricted__root_with_single_slash()
    test_internal__root_without_slash()
    test_internal__root_with_single_slash()
