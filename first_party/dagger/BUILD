load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_java//java:defs.bzl", "java_binary", "java_import")

java_binary(
    name = "dagger_compiler_fat_bin",
    main_class = "dagger.internal.codegen.ComponentProcessor",
    runtime_deps = [
        "@com_jackbradshaw_maven//:com_google_dagger_dagger_compiler",
    ],
    visibility = ["//visibility:private"],
)

java_binary(
    name = "jarjar_tool",
    main_class = "org.pantsbuild.jarjar.Main",
    runtime_deps = ["@com_jackbradshaw_maven//:org_pantsbuild_jarjar"],
    visibility = ["//visibility:private"],
)

genrule(
    name = "dagger_compiler_shaded",
    srcs = [":dagger_compiler_fat_bin_deploy.jar"],
    outs = ["dagger_compiler_shaded.jar"],
    cmd = """
        # Rewrite `com.google.common.**` classes to `dagger.shaded.com.google.common.@1`
        echo "rule com.google.common.** dagger.shaded.com.google.common.@1" > rules.txt
        $(location :jarjar_tool) process rules.txt $< $@
    """,
    tools = [":jarjar_tool"],
    toolchains = ["@bazel_tools//tools/jdk:current_host_java_runtime"], 
)

java_import(
    name = "dagger_compiler_shaded_lib",
    jars = [":dagger_compiler_shaded"],
)

kt_ksp_plugin(
    name = "plugin",
    processor_class = "dagger.internal.codegen.KspComponentProcessor$Provider",
    deps = [":dagger_compiler_shaded_lib"],
    visibility = ["//visibility:public"],
)
