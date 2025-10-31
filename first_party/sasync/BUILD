load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "scope",
    srcs = [
        "SasyncScope.kt",
    ],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "sasync",
    srcs = [
        "Sasync.kt",
    ],
    deps = [
        ":scope",
        "//:dagger",
        "//first_party/concurrency",
        "//first_party/coroutines",
        "//first_party/sasync/config:defaults",
        "//first_party/sasync/config:kt_jvm_proto",
        "//first_party/sasync/standard/error",
        "//first_party/sasync/standard/error:module",
        "//first_party/sasync/standard/input",
        "//first_party/sasync/standard/input:module",
        "//first_party/sasync/standard/output",
        "//first_party/sasync/standard/output:module",
        "//first_party/sasync/transport/config:module",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = ["SasyncTest.kt"],
    test_class = "com.jackbradshaw.sasync.SasyncTest",
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":sasync",
        "//:dagger",
        "//first_party/coroutines/testing",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
