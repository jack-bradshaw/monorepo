load("@rules_jvm_external//:defs.bzl", "DEFAULT_REPOSITORY_NAME", "maven_install")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")

AUTO_VALUE_VERSION = "1.9"

DAGGER_VERSION = "2.41"

FLOGGER_VERSION = "0.7.4"

GUAVA_VERSION = "31.0.1-jre"

GOOGLE_HTTP_CLIENT_VERSION = "1.41.4"

KTOR_VERSION = "1.6.7"

def io_matthewbradshaw_maven_repositories():
    maybe(
        maven_install,
        name = DEFAULT_REPOSITORY_NAME,
        artifacts = [
            "commons-codec:commons-codec:1.15",
            "com.google.auto.value:auto-value-annotations:%s" % AUTO_VALUE_VERSION,
            "com.google.auto.value:auto-value:%s" % AUTO_VALUE_VERSION,
            "com.google.auto:auto-common:1.2.1",
            "com.google.code.findbugs:jsr305:3.0.2",
            "com.google.code.gson:gson:2.9.0",
            "com.google.dagger:dagger:%s" % DAGGER_VERSION,
	    "com.google.dagger:dagger-compiler:%s" % DAGGER_VERSION,
            "com.google.flogger:flogger-system-backend:%s" % FLOGGER_VERSION,
            "com.google.flogger:flogger:%s" % FLOGGER_VERSION,
            "com.google.guava:failureaccess:1.0.1",
            "com.google.guava:guava-testlib:%s" % GUAVA_VERSION,
            "com.google.guava:guava:%s" % GUAVA_VERSION,
            "com.google.http-client:google-http-client-gson:%s" % GOOGLE_HTTP_CLIENT_VERSION,
            "com.google.http-client:google-http-client-test:%s" % GOOGLE_HTTP_CLIENT_VERSION,
            "com.google.http-client:google-http-client:%s" % GOOGLE_HTTP_CLIENT_VERSION,
            "com.google.truth:truth:1.1.3",
            "io.ktor:ktor-server-core:%s" % KTOR_VERSION,
            "io.ktor:ktor-server-netty:%s" % KTOR_VERSION,
	    "javax.inject:jsr330-api:0.9",
	    "javax.annotation:jsr250-api:1.0",
	    "javax.inject:javax.inject:1",
            "junit:junit:4.13.2",
            "org.mockito:mockito-core:4.3.1",
        ],
        repositories = [
            "https://maven.google.com",
            "https://repo1.maven.org/maven2",
        ],
    )
