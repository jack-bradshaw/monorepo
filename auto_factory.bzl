def auto_factory():
    native.java_plugin(
        name = "auto_factory_processor",
        generates_api = 1,
        processor_class = "com.google.auto.factory.processor.AutoFactoryProcessor",
        deps = [
            "@maven//:com_google_auto_auto_common",
            "@maven//:com_google_auto_factory_auto_factory",
            "@maven//:com_google_auto_value_auto_value",
            "@maven//:com_google_auto_value_auto_value_annotations",
        ],
    )

    native.java_library(
        name = "auto_factory",
        exported_plugins = [":auto_factory_processor"],
        exports = [
            "@maven//:com_google_auto_factory_auto_factory",
            "@maven//:com_google_auto_auto_common",
        ],
    )
