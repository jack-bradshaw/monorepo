load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "backstab_target",
    srcs = ["backstabTarget.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/typeregistry:javax",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_library(
    name = "type",
    srcs = [
        "type.kt",
        "typeArgument.kt",
    ],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:type",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_test_with_dagger(
    name = "backstab_target_test",
    srcs = ["backstabTargetTest.kt"],
    test_class = "com.jackbradshaw.backstab.core.model.kotlinpoet.BackstabTargetTest",
    deps = [
        ":backstab_target",
        "//first_party/backstab/core/model:target",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test_with_dagger(
    name = "type_test",
    srcs = ["typeTest.kt"],
    test_class = "com.jackbradshaw.backstab.core.model.kotlinpoet.TypeTest",
    deps = [
        ":type",
        "//first_party/backstab/core/model:type",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test_with_dagger(
    name = "type_argument_test",
    srcs = ["typeArgumentTest.kt"],
    test_class = "com.jackbradshaw.backstab.core.model.kotlinpoet.TypeArgumentTest",
    deps = [
        ":type",
        "//first_party/backstab/core/model:type",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
