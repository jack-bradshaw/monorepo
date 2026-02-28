load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_binary", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "kt_jvm_library_must_build",
    srcs = ["Main.kt"],
)

kt_jvm_binary(
    name = "kt_jvm_binary_must_build",
    srcs = ["Main.kt"],
    main_class = "MainKt",
)

kt_jvm_test(
    name = "kt_jvm_test_must_pass",
    srcs = ["Test.kt"],
    test_class = "Test",
    deps = ["@com_jackbradshaw_maven//:junit_junit"],
)
