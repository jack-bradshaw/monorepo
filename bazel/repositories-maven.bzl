load("@rules_jvm_external//:defs.bzl", "DEFAULT_REPOSITORY_NAME", "maven_install")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")

AUTO_VALUE_VERSION = "1.9"

DAGGER_VERSION = "2.42"

FLOGGER_VERSION = "0.7.4"

J_MONKEY_ENGINE_MAIN_VERSION = "3.5.2-stable"

J_MONKEY_ENGINE_BULLET_VERSION = "3.3.2-stable"

GUAVA_VERSION = "31.0.1-jre"

GOOGLE_HTTP_CLIENT_VERSION = "1.41.4"

KTOR_VERSION = "1.6.7"

LWJGL_VERSION = "3.3.1"

PROTOBUF_GENERAL_VERSION = "3.21.1"

def io_jackbradshaw_maven_repositories():
    maybe(
        maven_install,
        name = DEFAULT_REPOSITORY_NAME,
        artifacts = [
            "commons-codec:commons-codec:1.15",
            "com.google.auto.value:auto-value-annotations:%s" % AUTO_VALUE_VERSION,
            "com.google.auto.value:auto-value:%s" % AUTO_VALUE_VERSION,
            "com.google.auto:auto-common:1.2.1",
            "com.google.auto.factory:auto-factory:1.0.1",
            "com.google.code.findbugs:jsr305:3.0.2",
            "com.google.code.gson:gson:2.9.0",
            "com.google.dagger:dagger:%s" % DAGGER_VERSION,
            "com.google.dagger:dagger-compiler:%s" % DAGGER_VERSION,
            "com.google.flogger:flogger-system-backend:%s" % FLOGGER_VERSION,
            "com.google.flogger:flogger:%s" % FLOGGER_VERSION,
            "org.jmonkeyengine:jme3-bullet:%s" % J_MONKEY_ENGINE_BULLET_VERSION,
            "org.jmonkeyengine:jme3-bullet-native:%s" % J_MONKEY_ENGINE_BULLET_VERSION,
            "org.jmonkeyengine:jme3-core:%s" % J_MONKEY_ENGINE_MAIN_VERSION,
            "org.jmonkeyengine:jme3-desktop:%s" % J_MONKEY_ENGINE_MAIN_VERSION,
            "org.jmonkeyengine:jme3-lwjgl:%s" % J_MONKEY_ENGINE_MAIN_VERSION,
            "org.jmonkeyengine:jme3-lwjgl3:%s" % J_MONKEY_ENGINE_MAIN_VERSION,
            "org.jmonkeyengine:jme3-testdata:%s" % J_MONKEY_ENGINE_MAIN_VERSION,
            "org.jmonkeyengine:jme3-vr:%s" % J_MONKEY_ENGINE_MAIN_VERSION,
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
            "org.junit.jupiter:junit-jupiter-engine:5.8.1",
            "com.google.protobuf:protobuf-java:%s" % PROTOBUF_GENERAL_VERSION,
            "com.google.protobuf:protobuf-java-util:%s" % PROTOBUF_GENERAL_VERSION,
            "com.google.protobuf:protobuf-javalite:%s" % PROTOBUF_GENERAL_VERSION,
            "com.google.protobuf:protobuf-lite:3.0.1",
            "com.google.protobuf:protobuf-kotlin:%s" % PROTOBUF_GENERAL_VERSION,
            "com.google.protobuf:protobuf-kotlin-lite:%s" % PROTOBUF_GENERAL_VERSION,
            "org.jetbrains.kotlin:kotlin-test:1.7.10",
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2",
            "org.lwjgl:lwjgl:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-assimp:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-bgfx:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-cuda:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-egl:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-glfw:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-jawt:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-jemalloc:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-libdivide:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-llvm:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-lmdb:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-lz4:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-meow:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-meshoptimizer:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-nanovg:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-nfd:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-nuklear:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-odbc:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-openal:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-opencl:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-opengl:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-opengles:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-openvr:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-openxr:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-opus:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-par:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-remotery:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-rpmalloc:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-shaderc:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-spvc:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-sse:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-stb:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-tinyexr:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-tootle:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-vma:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-vulkan:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-xxhash:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-yoga:%s" % LWJGL_VERSION,
            "org.lwjgl:lwjgl-zstd:%s" % LWJGL_VERSION,
            "org.mockito:mockito-core:4.3.1",
        ],
        repositories = [
            "https://maven.google.com",
            "https://repo1.maven.org/maven2",
            "https://repository.mulesoft.org/nexus/content/repositories/public/",
        ],
    )
