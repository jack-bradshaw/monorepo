load("@rules_java//java:defs.bzl", "java_binary", "java_import")
load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load(":publicity.bzl", "PUBLICITY")

java_binary(
    name = "jarjar",
    main_class = "org.pantsbuild.jarjar.Main",
    visibility = PUBLICITY,
    runtime_deps = ["@com_jackbradshaw_maven//:org_pantsbuild_jarjar"],
)

java_binary(
    name = "dagger_compiler_original",
    main_class = "dagger.internal.codegen.ComponentProcessor",
    visibility = PUBLICITY,
    runtime_deps = ["@com_jackbradshaw_maven//:com_google_dagger_dagger_compiler"],
)

genrule(
    name = "dagger_compiler_shaded_generated",
    srcs = [":dagger_compiler_original_deploy.jar"],
    outs = ["dagger_compiler_shaded.jar"],
    cmd = """
        # Rewrite `com.google.common.**` classes to `dagger.shaded.com.google.common.@1`
        echo "rule com.google.common.** dagger.shaded.com.google.common.@1" > rules.txt
        $(location :jarjar) process rules.txt $< $@
    """,
    toolchains = ["@bazel_tools//tools/jdk:current_host_java_runtime"],
    tools = [":jarjar"],
)

java_import(
    name = "dagger_compiler_shaded",
    jars = [":dagger_compiler_shaded_generated"],
    visibility = PUBLICITY,
)

kt_ksp_plugin(
    name = "dagger_ksp_plugin",
    processor_class = "dagger.internal.codegen.KspComponentProcessor$Provider",
    visibility = PUBLICITY,
    deps = [":dagger_compiler_shaded"],
)
