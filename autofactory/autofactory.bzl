# AutoFactory Bazel integration.
#
# Usage example:
#
# ```
# load("//:auto_factory.bzl", "auto_factory")
# auto_factory()
#
# java_library(
#   name = "some_lib",
#   srcs = ["SomeClass.java"],
#   deps = [
#     ":auto_factory",
#   ]
# )
# ```
#
# See https://github.com/google/auto/tree/main/factory for context.

load("@rules_java//java:java_library.bzl", "java_library")
load("@rules_java//java:java_plugin.bzl", "java_plugin")

def auto_factory():
    java_plugin(
        name = "auto_factory_processor",
        generates_api = 1,
        processor_class = "com.google.auto.factory.processor.AutoFactoryProcessor",
        deps = [
            "@com_jackbradshaw_maven//:javax_inject_javax_inject",
            "@com_jackbradshaw_maven//:com_google_auto_auto_common",
            "@com_jackbradshaw_maven//:com_google_auto_factory_auto_factory",
            "@com_jackbradshaw_maven//:com_google_auto_value_auto_value",
            "@com_jackbradshaw_maven//:com_google_auto_value_auto_value_annotations",
        ],
    )
    java_library(
        name = "auto_factory",
        exported_plugins = [":auto_factory_processor"],
        exports = [
            "@com_jackbradshaw_maven//:javax_inject_javax_inject",
            "@com_jackbradshaw_maven//:com_google_auto_factory_auto_factory",
            "@com_jackbradshaw_maven//:com_google_auto_auto_common",
        ],
    )
