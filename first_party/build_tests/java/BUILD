load("@rules_java//java:defs.bzl", "java_binary", "java_import", "java_library", "java_plugin", "java_test")

java_library(
    name = "java_library_must_build",
    srcs = ["Library.java"],
)

java_binary(
    name = "java_binary_must_build",
    srcs = ["Binary.java"],
    main_class = "Binary",
)

java_test(
    name = "java_test_must_pass",
    srcs = ["Testing.java"],
    test_class = "Testing",
    deps = ["@com_jackbradshaw_maven//:junit_junit"],
)

java_import(
    name = "java_import_must_build",
    jars = ["dummy.jar"],
)

java_plugin(
    name = "java_plugin_must_build",
    srcs = [
        "Annotation.java",
        "Processor.java",
    ],
    processor_class = "Processor",
)
