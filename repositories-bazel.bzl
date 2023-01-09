load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

def io_jackbradshaw_bazel_repositories():
    maybe(
        http_archive,
        name = "rules_jvm_external",
        sha256 = "cd1a77b7b02e8e008439ca76fd34f5b07aecb8c752961f9640dea15e9e5ba1ca",
        strip_prefix = "rules_jvm_external-4.2",
        url = "https://github.com/bazelbuild/rules_jvm_external/archive/4.2.zip",
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
        sha256 = "b0769a4d485d9ba722c92c36e8a88379665adf073a789f8d00524876c3fc8c9c",
        strip_prefix = "buildtools-23e2a9e4721aa4969503b2fdfe5ce9efa95b4259",
        url = "https://github.com/bazelbuild/buildtools/archive/23e2a9e4721aa4969503b2fdfe5ce9efa95b4259.zip",
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
        sha256 = "685052b498b6ddfe562ca7a97736741d87916fe536623afb7da2824c0211c369",
        urls = [
            "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v0.33.0/rules_go-v0.33.0.zip",
            "https://github.com/bazelbuild/rules_go/releases/download/v0.33.0/rules_go-v0.33.0.zip",
        ],
    )

    maybe(
        http_archive,
        name = "rules_java",
        urls = [
            "https://mirror.bazel.build/github.com/bazelbuild/rules_java/releases/download/5.0.0/rules_java-5.0.0.tar.gz",
            "https://github.com/bazelbuild/rules_java/releases/download/5.0.0/rules_java-5.0.0.tar.gz",
        ],
        sha256 = "8c376f1e4ab7d7d8b1880e4ef8fc170862be91b7c683af97ca2768df546bb073",
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
        sha256 = "fd92a98bd8a8f0e1cdcb490b93f5acef1f1727ed992571232d33de42395ca9b3",
        url = "https://github.com/bazelbuild/rules_kotlin/releases/download/v1.7.1/rules_kotlin_release.tgz",
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
        name = "rules_proto",
        sha256 = "7d05492099a4359a6006d1b89284d34b76390c3b67d08e30840299b045838e2d",
        strip_prefix = "rules_proto-9cd4f8f1ede19d81c6d48910429fe96776e567b1",
        url = "https://github.com/bazelbuild/rules_proto/archive/9cd4f8f1ede19d81c6d48910429fe96776e567b1.zip",
    )

    maybe(
        http_archive,
        name = "dagger",
        sha256 = "5c2b22e88e52110178afebda100755f31f5dd505c317be0bfb4f7ad88a88db86",
        strip_prefix = "dagger-dagger-2.41",
        url = "https://github.com/google/dagger/archive/dagger-2.41.zip",
    )

    http_archive(
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
