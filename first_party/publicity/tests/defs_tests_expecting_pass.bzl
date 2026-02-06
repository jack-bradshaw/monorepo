load("@bazel_skylib//lib:unittest.bzl", "asserts", "unittest")
load("//first_party/publicity:defs.bzl", "internal", "public", "quarantined", "restricted")

# Test 1: `public` returns correct visibility

def _public_test_impl(ctx):
    env = unittest.begin(ctx)
    actual = public()
    expected = ["//visibility:public"]
    asserts.equals(env, expected, actual)
    return unittest.end(env)

public_test = unittest.make(_public_test_impl)

# Test 2: `internal` returns workspace visibility (default root)

def _internal_default_root_test_impl(ctx):
    env = unittest.begin(ctx)
    actual = internal()
    expected = ["//first_party:__subpackages__"]
    asserts.equals(env, expected, actual)
    return unittest.end(env)

internal_default_root_test = unittest.make(_internal_default_root_test_impl)

# Test 3: `internal` returns workspace visibility (custom root)

def _internal_custom_root_test_impl(ctx):
    env = unittest.begin(ctx)
    actual = internal(first_party_root = "//custom")
    expected = ["//custom:__subpackages__"]
    asserts.equals(env, expected, actual)
    return unittest.end(env)

internal_custom_root_test = unittest.make(_internal_custom_root_test_impl)

# Test 4: `quarantined` derives correct package path

def _quarantined_test_impl(ctx):
    env = unittest.begin(ctx)
    actual = quarantined("//first_party/foo")
    expected = ["//first_party/foo:__subpackages__"]
    asserts.equals(env, expected, actual)
    return unittest.end(env)

quarantined_test = unittest.make(_quarantined_test_impl)

# Test 5: `restricted` returns correct visibility (default root)

def _restricted_default_root_test_impl(ctx):
    env = unittest.begin(ctx)
    actual = restricted(["foo", "bar"])
    expected = [
        "//first_party/foo:__subpackages__",
        "//first_party/bar:__subpackages__",
    ]
    asserts.equals(env, expected, actual)
    return unittest.end(env)

restricted_default_root_test = unittest.make(_restricted_default_root_test_impl)

# Test 6: `restricted` returns correct visibility (custom root)

def _restricted_custom_root_test_impl(ctx):
    env = unittest.begin(ctx)
    actual = restricted(["foo", "bar"], first_party_root = "//custom")
    expected = [
        "//custom/foo:__subpackages__",
        "//custom/bar:__subpackages__",
    ]
    asserts.equals(env, expected, actual)
    return unittest.end(env)

restricted_custom_root_test = unittest.make(_restricted_custom_root_test_impl)

# All tests

def all_tests_expecting_pass():
    unittest.suite(
        "all_test_expecting_pass",
        public_test,
        internal_default_root_test,
        internal_custom_root_test,
        quarantined_test,
        restricted_default_root_test,
        restricted_custom_root_test,
    )
