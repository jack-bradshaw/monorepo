workspace(name = "io_jackbradshaw")

load("//bazel:repositories-bazel.bzl", "io_jackbradshaw_bazel_repositories")

io_jackbradshaw_bazel_repositories()

load("@rules_java//java:repositories.bzl", "rules_java_dependencies", "rules_java_toolchains")

rules_java_dependencies()

rules_java_toolchains()

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("//bazel:repositories-maven.bzl", "maven_repositories")

maven_repositories()

load("@bazel_features//:deps.bzl", "bazel_features_deps")

bazel_features_deps()

load("@robolectric//bazel:robolectric.bzl", "robolectric_repositories")

robolectric_repositories()

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_rules_dependencies()

go_register_toolchains(version = "1.18.3")

load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

load(
    "@io_bazel_rules_kotlin//kotlin:repositories.bzl",
    "kotlin_repositories",
    "kotlinc_version",
)

kotlin_repositories(
    compiler_release = kotlinc_version(
        release = "1.9.25",
        sha256 = "6ab72d6144e71cbbc380b770c2ad380972548c63ab6ed4c79f11c88f2967332e"
    )
)

register_toolchains("//:kotlin_toolchain")

load("@rules_android//android:rules.bzl", "android_sdk_repository")

android_sdk_repository(name = "androidsdk")