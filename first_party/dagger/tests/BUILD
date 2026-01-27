load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_binary_with_dagger", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library_with_dagger(
    name = "test_component",
    testonly = True,
    srcs = ["TestComponent.kt"],
)

# Tests the kt_jvm_library_with_dagger macro (:test_component) works as intended.
kt_jvm_test(
    name = "LibraryMacroTest",
    srcs = ["LibraryAndTestMacroTest.kt"],
    test_class = "first_party.dagger.tests.LibraryAndTestMacroTest",
    deps = [
        ":test_component",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

# Tests the kt_jvm_test_with_dagger macro works as intended.
kt_jvm_test_with_dagger(
    name = "TestMacroTest",
    srcs = ["LibraryAndTestMacroTest.kt"],
    test_class = "first_party.dagger.tests.LibraryAndTestMacroTest",
    deps = [
        ":test_component",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_binary_with_dagger(
    name = "BinaryMacroTestBin",
    testonly = True,
    srcs = ["BinaryMain.kt"],
    main_class = "first_party.dagger.tests.BinaryMain",
    deps = [":test_component"],
)

kt_jvm_test(
    name = "BinaryMacroTest",
    srcs = ["BinaryMacroTest.kt"],
    test_class = "first_party.dagger.tests.BinaryMacroTest",
    deps = [
        ":BinaryMacroTestBin",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
