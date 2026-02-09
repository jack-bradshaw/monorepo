load("@rules_java//java:java_library.bzl", "java_library")
load("@rules_java//java:java_plugin.bzl", "java_plugin")
load("//first_party/autofactory:publicity.bzl", "PUBLICITY")

java_plugin(
    name = "plugin",
    generates_api = True,
    processor_class = "com.google.auto.factory.processor.AutoFactoryProcessor",
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
    visibility = PUBLICITY,
    exports = [
        "@com_jackbradshaw_maven//:com_google_auto_auto_common",
        "@com_jackbradshaw_maven//:com_google_auto_factory_auto_factory",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
