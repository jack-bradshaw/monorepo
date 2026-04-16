load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/kale:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "basic_processor",
    testonly = True,
    srcs = ["BasicProcessor.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "basic_provider",
    testonly = True,
    srcs = ["BasicProvider.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        ":basic_processor",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "code_generating_processor",
    testonly = True,
    srcs = ["CodeGeneratingProcessor.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "code_generating_provider",
    testonly = True,
    srcs = ["CodeGeneratingProvider.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        ":code_generating_processor",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "options_collecting_provider",
    testonly = True,
    srcs = ["OptionsCollectingProvider.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        ":basic_processor",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "class_collecting_processor",
    testonly = True,
    srcs = ["ClassCollectingProcessor.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "class_collecting_provider",
    testonly = True,
    srcs = ["ClassCollectingProvider.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        ":class_collecting_processor",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "exception_throwing_processor",
    testonly = True,
    srcs = ["ExceptionThrowingProcessor.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "exception_throwing_provider",
    testonly = True,
    srcs = ["ExceptionThrowingProvider.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        ":exception_throwing_processor",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "logging_processor",
    testonly = True,
    srcs = ["LoggingProcessor.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "logging_provider",
    testonly = True,
    srcs = ["LoggingProvider.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        ":logging_processor",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)
