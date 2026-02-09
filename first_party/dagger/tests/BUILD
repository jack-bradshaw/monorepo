load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_binary_with_dagger", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

# Tests for the library macro. The lib target exercises the macro and the test checks the results.

kt_jvm_library_with_dagger(
    name = "lib_test_components",
    testonly = True,
    srcs = ["LibTestComponents.kt"],
)

kt_jvm_test(
    name = "lib_test",
    srcs = ["LibTest.kt"],
    test_class = "first_party.dagger.tests.LibTest",
    deps = [
        ":lib_test_components",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

# Tests for binary macro. The bin target exercises the macro and the test checks the result.

kt_jvm_binary_with_dagger(
    name = "bin_test_components",
    testonly = True,
    srcs = ["BinTestComponents.kt"],
    main_class = "first_party.dagger.tests.BinMain",
)

kt_jvm_test(
    name = "bin_test",
    srcs = ["BinTest.kt"],
    test_class = "first_party.dagger.tests.BinTest",
    deps = [
        ":bin_test_components",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

# Tests for the library macro. Unlike lib and bin tests, one target can build and verify the macro,
# since the macro generates a test target.

kt_jvm_test_with_dagger(
    name = "test_test",
    srcs = ["TestTest.kt"],
    test_class = "first_party.dagger.tests.TestTest",
    deps = [
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
