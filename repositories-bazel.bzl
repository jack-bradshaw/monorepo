load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")

BAZEL_VERSION = "b3baae9720653a9c6fa8d97780d59331bd590e23"
BAZEL_BUILDTOOLS_VERSION = "23e2a9e4721aa4969503b2fdfe5ce9efa95b4259"
PROTOBUF_VERSION = "3.19.2"
PROTOBUF_RULES_VERSION = "9cd4f8f1ede19d81c6d48910429fe96776e567b1"
GO_RULES_VERSION = "0.21.0"
KOTLIN_RULES_VERSION = "1.5.0"
CPP_RULES_VERSION = "ea5c5422a6b9e79e6432de3b2b29bbd84eb41081"
JAVA_RULES_VERSION = "0.1.0"
PYTHON_RULES_VERSION = "9d68f24659e8ce8b736590ba1e4418af06ec2552"
JVM_EXTERNAL_VERSION = "3.0"
PKG_VERSION = "0.2.0"
DAGGER_VERSION = "2.41"
GAZELLE_VERSION = "0.19.1"
SKYLIB_VERSION = "398f3122891b9b711f5aab1adc7597d9fce09085"

def io_matthewbradshaw_bazel_repositories():
    maybe(
        http_archive,
        name = "rules_jvm_external",
        sha256 = "62133c125bf4109dfd9d2af64830208356ce4ef8b165a6ef15bbff7460b35c3a",
        strip_prefix = "rules_jvm_external-%s" % JVM_EXTERNAL_VERSION,
        url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % JVM_EXTERNAL_VERSION,
    )

    maybe(
        http_archive,
        name = "io_bazel",
        sha256 = "c72661c4a4b91c47b40c54a23be6ea8c0928f409db8dd2295aae90262c3bef6b",
        strip_prefix = "bazel-%s" % BAZEL_VERSION,
        url = "https://github.com/bazelbuild/bazel/archive/%s.zip" % BAZEL_VERSION,
    )

    maybe(
        http_archive,
        name = "com_github_bazelbuild_buildtools",
        sha256 = "b0769a4d485d9ba722c92c36e8a88379665adf073a789f8d00524876c3fc8c9c",
        strip_prefix = "buildtools-%s" % BAZEL_BUILDTOOLS_VERSION,
        url = "https://github.com/bazelbuild/buildtools/archive/%s.zip" % BAZEL_BUILDTOOLS_VERSION,
    )

    maybe(
        http_archive,
        name = "bazel_skylib",
        sha256 = "2d9a5be0c710c62e04d0b684f783c531d70e13f90378a4a8d9d1925e3bc487af",
        strip_prefix = "bazel-skylib-%s" % SKYLIB_VERSION,
        url = "https://github.com/bazelbuild/bazel-skylib/archive/%s.zip" % SKYLIB_VERSION,
    )

    maybe(
        http_archive,
        name = "bazel_gazelle",
        sha256 = "86c6d481b3f7aedc1d60c1c211c6f76da282ae197c3b3160f54bd3a8f847896f",
        urls = [
            "https://mirror.bazel.build/github.com/bazelbuild/bazel-gazelle/releases/download/v%s/bazel-gazelle-v%s.tar.gz" % (GAZELLE_VERSION, GAZELLE_VERSION),
            "https://github.com/bazelbuild/bazel-gazelle/releases/download/v%s/bazel-gazelle-v%s.tar.gz" % (GAZELLE_VERSION, GAZELLE_VERSION),
        ],
    )

    maybe(
        http_archive,
        name = "rules_pkg",
        sha256 = "5bdc04987af79bd27bc5b00fe30f59a858f77ffa0bd2d8143d5b31ad8b1bd71c",
        url = "https://github.com/bazelbuild/rules_pkg/releases/download/%s/rules_pkg-%s.tar.gz" % (PKG_VERSION, PKG_VERSION),
    )

    maybe(
        http_archive,
        name = "io_bazel_rules_go",
        sha256 = "b27e55d2dcc9e6020e17614ae6e0374818a3e3ce6f2024036e688ada24110444",
        urls = [
            "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v%s/rules_go-v%s.tar.gz" % (GO_RULES_VERSION, GO_RULES_VERSION),
            "https://github.com/bazelbuild/rules_go/releases/download/v%s/rules_go-v%s.tar.gz" % (GO_RULES_VERSION, GO_RULES_VERSION),
        ],
    )

    maybe(
        http_archive,
        name = "rules_java",
        sha256 = "52423cb07384572ab60ef1132b0c7ded3a25c421036176c0273873ec82f5d2b2",
        url = "https://github.com/bazelbuild/rules_java/releases/download/%s/rules_java-%s.tar.gz" % (JAVA_RULES_VERSION, JAVA_RULES_VERSION),
    )

    maybe(
        http_archive,
        name = "rules_python",
        sha256 = "f7402f11691d657161f871e11968a984e5b48b023321935f5a55d7e56cf4758a",
        strip_prefix = "rules_python-%s" % PYTHON_RULES_VERSION,
        url = "https://github.com/bazelbuild/rules_python/archive/%s.zip" % PYTHON_RULES_VERSION,
    )

    maybe(
        http_archive,
        name = "io_bazel_rules_kotlin",
        sha256 = "12d22a3d9cbcf00f2e2d8f0683ba87d3823cb8c7f6837568dd7e48846e023307",
        url = "https://github.com/bazelbuild/rules_kotlin/releases/download/v%s/rules_kotlin_release.tgz" % KOTLIN_RULES_VERSION,
    )

    maybe(
        http_archive,
        name = "rules_cc",
        sha256 = "faa25a149f46077e7eca2637744f494e53a29fe3814bfe240a2ce37115f6e04d",
        strip_prefix = "rules_cc-%s" % CPP_RULES_VERSION,
        url = "https://github.com/bazelbuild/rules_cc/archive/%s.zip" % CPP_RULES_VERSION,
    )

    maybe(
        http_archive,
        name = "com_google_protobuf",
        patch_args = ["-p1"],
        patches = ["@io_bazel//third_party/protobuf:%s.patch" % PROTOBUF_VERSION],
        patch_cmds = [
            "test -f BUILD && chmod u+w BUILD || true",
            "echo >> BUILD",
            "echo 'exports_files([\"WORKSPACE\"], visibility = [\"//visibility:public\"])' >> BUILD",
        ],
        patch_cmds_win = [
            "Add-Content -Path BUILD -Value \"`nexports_files([`\"WORKSPACE`\"], visibility = [`\"//visibility:public`\"])`n\" -Force",
        ],
        sha256 = "4dd35e788944b7686aac898f77df4e9a54da0ca694b8801bd6b2a9ffc1b3085e",
        strip_prefix = "protobuf-%s" % PROTOBUF_VERSION,
        urls = [
            "https://mirror.bazel.build/github.com/protocolbuffers/protobuf/archive/v%s.tar.gz" % PROTOBUF_VERSION,
            "https://github.com/protocolbuffers/protobuf/archive/v%s.tar.gz" % PROTOBUF_VERSION,
        ],
    )

    maybe(
        http_archive,
        name = "rules_proto",
        sha256 = "7d05492099a4359a6006d1b89284d34b76390c3b67d08e30840299b045838e2d",
        strip_prefix = "rules_proto-%s" % PROTOBUF_RULES_VERSION,
        url = "https://github.com/bazelbuild/rules_proto/archive/%s.zip" % PROTOBUF_RULES_VERSION,
    )

    http_archive(
        name = "dagger",
        url = "https://github.com/google/dagger/archive/dagger-%s.zip" % DAGGER_VERSION,
        strip_prefix = "dagger-dagger-%s" % DAGGER_VERSION,
    )
