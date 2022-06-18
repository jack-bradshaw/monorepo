load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")

def io_matthewbradshaw_bazel_repositories():
    maybe(
        http_archive,
        name = "rules_jvm_external",
        sha256 = "62133c125bf4109dfd9d2af64830208356ce4ef8b165a6ef15bbff7460b35c3a",
        strip_prefix = "rules_jvm_external-3.0",
        url = "https://github.com/bazelbuild/rules_jvm_external/archive/3.0.zip",
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
        sha256 = "86c6d481b3f7aedc1d60c1c211c6f76da282ae197c3b3160f54bd3a8f847896f",
        urls = [
            "https://mirror.bazel.build/github.com/bazelbuild/bazel-gazelle/releases/download/v0.19.1/bazel-gazelle-v0.19.1.tar.gz",
            "https://github.com/bazelbuild/bazel-gazelle/releases/download/v0.19.1/bazel-gazelle-v0.19.1.tar.gz",
        ],
    )

    maybe(
        http_archive,
        name = "rules_pkg",
        sha256 = "5bdc04987af79bd27bc5b00fe30f59a858f77ffa0bd2d8143d5b31ad8b1bd71c",
        url = "https://github.com/bazelbuild/rules_pkg/releases/download/0.2.0/rules_pkg-0.2.0.tar.gz",
    )

    http_archive(
        name = "io_bazel_rules_go",
        sha256 = "d6b2513456fe2229811da7eb67a444be7785f5323c6708b38d851d2b51e54d83",
        urls = [
            "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v0.30.0/rules_go-v0.30.0.zip",
            "https://github.com/bazelbuild/rules_go/releases/download/v0.30.0/rules_go-v0.30.0.zip",
        ],
    )

    http_archive(
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
        sha256 = "12d22a3d9cbcf00f2e2d8f0683ba87d3823cb8c7f6837568dd7e48846e023307",
        url = "https://github.com/bazelbuild/rules_kotlin/releases/download/v1.5.0/rules_kotlin_release.tgz",
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

    http_archive(
        name = "dagger",
        sha256 = "5c2b22e88e52110178afebda100755f31f5dd505c317be0bfb4f7ad88a88db86",
        strip_prefix = "dagger-dagger-2.41",
        url = "https://github.com/google/dagger/archive/dagger-2.41.zip",
    )
