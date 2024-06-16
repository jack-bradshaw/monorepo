load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")
load("//bazel:robolectric.bzl", "ROBOLECTRIC_VERSION")

def io_jackbradshaw_bazel_repositories():
    maybe(
        http_archive,
        name = "rules_jvm_external",
        sha256 = "42a6d48eb2c08089961c715a813304f30dc434df48e371ebdd868fc3636f0e82",
        strip_prefix = "rules_jvm_external-6.1",
        url = "https://github.com/bazelbuild/rules_jvm_external/archive/6.1.zip",
    )

    maybe(
        http_archive,
        name = "io_bazel",
        sha256 = "c72661c4a4b91c47b40c54a23be6ea8c0928f409db8dd2295aae90262c3bef6b",
        strip_prefix = "bazel-b3baae9720653a9c6fa8d97780d59331bd590e23",
        url = "https://github.com/bazelbuild/bazel/archive/b3baae9720653a9c6fa8d97780d59331bd590e23.zip",
    )

    maybe(
        http_archive,
        name = "com_github_bazelbuild_buildtools",
        sha256 = "39c59cb5352892292cbe3174055aac187edcb5324c9b4e2d96cb6e40bd753877",
        strip_prefix = "buildtools-7.1.2",
        url = "https://github.com/bazelbuild/buildtools/archive/refs/tags/v7.1.2.tar.gz",
    )

    maybe(
        http_archive,
        name = "bazel_skylib",
        sha256 = "2d9a5be0c710c62e04d0b684f783c531d70e13f90378a4a8d9d1925e3bc487af",
        strip_prefix = "bazel-skylib-398f3122891b9b711f5aab1adc7597d9fce09085",
        url = "https://github.com/bazelbuild/bazel-skylib/archive/398f3122891b9b711f5aab1adc7597d9fce09085.zip",
    )

    maybe(
        http_archive,
        name = "bazel_gazelle",
        sha256 = "5982e5463f171da99e3bdaeff8c0f48283a7a5f396ec5282910b9e8a49c0dd7e",
        urls = [
            "https://mirror.bazel.build/github.com/bazelbuild/bazel-gazelle/releases/download/v0.25.0/bazel-gazelle-v0.25.0.tar.gz",
            "https://github.com/bazelbuild/bazel-gazelle/releases/download/v0.25.0/bazel-gazelle-v0.25.0.tar.gz",
        ],
    )

    maybe(
        http_archive,
        name = "rules_pkg",
        sha256 = "5bdc04987af79bd27bc5b00fe30f59a858f77ffa0bd2d8143d5b31ad8b1bd71c",
        url = "https://github.com/bazelbuild/rules_pkg/releases/download/0.2.0/rules_pkg-0.2.0.tar.gz",
    )

    maybe(
        http_archive,
        name = "io_bazel_rules_go",
        sha256 = "33acc4ae0f70502db4b893c9fc1dd7a9bf998c23e7ff2c4517741d4049a976f8",
        urls = [
            "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v0.48.0/rules_go-v0.48.0.zip",
            "https://github.com/bazelbuild/rules_go/releases/download/v0.48.0/rules_go-v0.48.0.zip",
        ],
    )

    maybe(
        http_archive,
        name = "rules_java",
        urls = [
            "https://github.com/bazelbuild/rules_java/releases/download/7.6.1/rules_java-7.6.1.tar.gz",
        ],
        sha256 = "f8ae9ed3887df02f40de9f4f7ac3873e6dd7a471f9cddf63952538b94b59aeb3",
    )

    maybe(
        http_archive,
        name = "rules_python",
        sha256 = "f7402f11691d657161f871e11968a984e5b48b023321935f5a55d7e56cf4758a",
        strip_prefix = "rules_python-9d68f24659e8ce8b736590ba1e4418af06ec2552",
        url = "https://github.com/bazelbuild/rules_python/archive/9d68f24659e8ce8b736590ba1e4418af06ec2552.zip",
    )

    maybe(
        http_archive,
        name = "io_bazel_rules_kotlin",
        url = "https://github.com/bazelbuild/rules_kotlin/releases/download/v1.9.5/rules_kotlin-v1.9.5.tar.gz",
        sha256 = "34e8c0351764b71d78f76c8746e98063979ce08dcf1a91666f3f3bc2949a533d",
    )

    maybe(
        http_archive,
        name = "rules_cc",
        sha256 = "faa25a149f46077e7eca2637744f494e53a29fe3814bfe240a2ce37115f6e04d",
        strip_prefix = "rules_cc-ea5c5422a6b9e79e6432de3b2b29bbd84eb41081",
        url = "https://github.com/bazelbuild/rules_cc/archive/ea5c5422a6b9e79e6432de3b2b29bbd84eb41081.zip",
    )

    maybe(
        http_archive,
        name = "com_google_protobuf",
        patch_args = ["-p1"],
        patches = ["@io_bazel//third_party/protobuf:3.19.2.patch"],
        patch_cmds = [
            "test -f BUILD && chmod u+w BUILD || true",
            "echo >> BUILD",
            "echo 'exports_files([\"WORKSPACE\"], visibility = [\"//visibility:public\"])' >> BUILD",
        ],
        patch_cmds_win = [
            "Add-Content -Path BUILD -Value \"`nexports_files([`\"WORKSPACE`\"], visibility = [`\"//visibility:public`\"])`n\" -Force",
        ],
        sha256 = "4dd35e788944b7686aac898f77df4e9a54da0ca694b8801bd6b2a9ffc1b3085e",
        strip_prefix = "protobuf-3.19.2",
        urls = [
            "https://mirror.bazel.build/github.com/protocolbuffers/protobuf/archive/v3.19.2.tar.gz",
            "https://github.com/protocolbuffers/protobuf/archive/v3.19.2.tar.gz",
        ],
    )

    maybe(
        http_archive,
        name = "bazel_features",
        sha256 = "5d7e4eb0bb17aee392143cd667b67d9044c270a9345776a5e5a3cccbc44aa4b3",
        strip_prefix = "bazel_features-1.13.0",
        url = "https://github.com/bazel-contrib/bazel_features/releases/download/v1.13.0/bazel_features-v1.13.0.tar.gz",
    )

    maybe(
        http_archive,
        name = "rules_proto",
        sha256 = "303e86e722a520f6f326a50b41cfc16b98fe6d1955ce46642a5b7a67c11c0f5d",
        strip_prefix = "rules_proto-6.0.0",
        url = "https://github.com/bazelbuild/rules_proto/releases/download/6.0.0/rules_proto-6.0.0.tar.gz",
    )

    maybe(
        http_archive,
        name = "dagger",
        sha256 = "5c2b22e88e52110178afebda100755f31f5dd505c317be0bfb4f7ad88a88db86",
        strip_prefix = "dagger-dagger-2.41",
        url = "https://github.com/google/dagger/archive/dagger-2.41.zip",
    )

    maybe(
        http_archive,
        name = "io_grpc_grpc_java",
        sha256 = "2f2ca0701cf23234e512f415318bfeae00036a980f6a83574264f41c0201e5cd",
        strip_prefix = "grpc-java-1.46.0",
        url = "https://github.com/grpc/grpc-java/archive/v1.46.0.zip",
    )

    maybe(
        git_repository,
        name = "com_github_grpc_grpc_kotlin",
        remote = "https://github.com/grpc/grpc-kotlin",
        commit = "0681fc85677e2cca53bdf1cbf71f8d92d0355117",
        shallow_since = "1658949766 -0600",
    )

    maybe(
        http_archive,
        name = "rules_android",
        urls = ["https://github.com/bazelbuild/rules_android/archive/v0.1.1.zip"],
        sha256 = "cd06d15dd8bb59926e4d65f9003bfc20f9da4b2519985c27e190cddc8b7a7806",
        strip_prefix = "rules_android-0.1.1",
    )

    maybe(
        http_archive,
        name = "robolectric",
        sha256 = "f7b8e08f246f29f26fddd97b7ab5dfa01aaa6170ccc24b9b6a21931bde01ad6f",
        strip_prefix = "robolectric-bazel-%s" % ROBOLECTRIC_VERSION,
        url = "https://github.com/robolectric/robolectric-bazel/releases/download/%s/robolectric-bazel-%s.tar.gz" % (ROBOLECTRIC_VERSION,ROBOLECTRIC_VERSION),
    )

    
