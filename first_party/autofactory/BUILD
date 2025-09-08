# Makes [AutoFactory](https://github.com/google/auto/tree/main/factory) available to Bazel targets.
#
# Depending on :autofactory from a Java/Kotlin target adds all the necessary dependencies and
# installs the compilation plugin that makes autofactory work.
#
# Example usage:
#
# ```bzl
# java_library(
#   name = "myclass",
#   srcs = ["MyClass.java"],
#   deps = [
#     "//first_party/autofactory",
#   ]
# )
# ```
#
# ```kotlin
# @AutoFactory
# class MyClass(
#   @Provided private val myDep: myDep,
# )
# ```

load("@rules_java//java:java_library.bzl", "java_library")
load("@rules_java//java:java_plugin.bzl", "java_plugin")

java_plugin(
    name = "plugin",
    generates_api = 1,
    processor_class = "com.google.auto.factory.processor.AutoFactoryProcessor",
    visibility = ["//visibility:private"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_auto_auto_common",
        "@com_jackbradshaw_maven//:com_google_auto_factory_auto_factory",
        "@com_jackbradshaw_maven//:com_google_auto_value_auto_value",
        "@com_jackbradshaw_maven//:com_google_auto_value_auto_value_annotations",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

java_library(
    name = "autofactory",
    exported_plugins = [":plugin"],
    visibility = ["//first_party:__subpackages__"],
    exports = [
        "@com_jackbradshaw_maven//:com_google_auto_auto_common",
        "@com_jackbradshaw_maven//:com_google_auto_factory_auto_factory",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
