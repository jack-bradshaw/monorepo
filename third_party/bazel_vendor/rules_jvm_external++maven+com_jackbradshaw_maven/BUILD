
# package(default_visibility = ["//visibility:public"])  # https://github.com/bazelbuild/bazel/issues/13681

load("@bazel_skylib//:bzl_library.bzl", "bzl_library")
load("@bazel_skylib//rules:copy_file.bzl", "copy_file")
load("@rules_license//rules:package_info.bzl", "package_info")
load("@rules_java//java:defs.bzl", "java_binary", "java_library", "java_plugin")
load("@rules_jvm_external//private/rules:pin_dependencies.bzl", "pin_dependencies")
load("@rules_jvm_external//private/rules:jvm_import.bzl", "jvm_import")
load("@rules_shell//shell:sh_binary.bzl", "sh_binary")


copy_file(
     name = "com_google_auto_factory_auto_factory_sources_1_1_0_extension",
     src = "@com_google_auto_factory_auto_factory_sources_1_1_0//file",
     out = "com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_auto_service_auto_service_annotations_sources_1_1_1_extension",
     src = "@com_google_auto_service_auto_service_annotations_sources_1_1_1//file",
     out = "com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_auto_value_auto_value_sources_1_11_0_extension",
     src = "@com_google_auto_value_auto_value_sources_1_11_0//file",
     out = "com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_auto_value_auto_value_annotations_sources_1_11_0_extension",
     src = "@com_google_auto_value_auto_value_annotations_sources_1_11_0//file",
     out = "com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_auto_auto_common_sources_1_2_2_extension",
     src = "@com_google_auto_auto_common_sources_1_2_2//file",
     out = "com/google/auto/auto-common/1.2.2/auto-common-1.2.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_code_findbugs_jsr305_sources_3_0_2_extension",
     src = "@com_google_code_findbugs_jsr305_sources_3_0_2//file",
     out = "com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_code_gson_gson_sources_2_13_1_extension",
     src = "@com_google_code_gson_gson_sources_2_13_1//file",
     out = "com/google/code/gson/gson/2.13.1/gson-2.13.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_dagger_dagger_sources_2_57_extension",
     src = "@com_google_dagger_dagger_sources_2_57//file",
     out = "com/google/dagger/dagger/2.57/dagger-2.57-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_dagger_dagger_compiler_sources_2_57_extension",
     src = "@com_google_dagger_dagger_compiler_sources_2_57//file",
     out = "com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_dagger_dagger_producers_sources_2_57_extension",
     src = "@com_google_dagger_dagger_producers_sources_2_57//file",
     out = "com/google/dagger/dagger-producers/2.57/dagger-producers-2.57-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_dagger_dagger_spi_sources_2_57_extension",
     src = "@com_google_dagger_dagger_spi_sources_2_57//file",
     out = "com/google/dagger/dagger-spi/2.57/dagger-spi-2.57-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_devtools_ksp_symbol_processing_api_sources_2_1_21_2_0_2_extension",
     src = "@com_google_devtools_ksp_symbol_processing_api_sources_2_1_21_2_0_2//file",
     out = "com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_errorprone_error_prone_annotations_sources_2_38_0_extension",
     src = "@com_google_errorprone_error_prone_annotations_sources_2_38_0//file",
     out = "com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_errorprone_javac_shaded_sources_9_dev_r4023_3_extension",
     src = "@com_google_errorprone_javac_shaded_sources_9_dev_r4023_3//file",
     out = "com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_flogger_flogger_sources_0_9_extension",
     src = "@com_google_flogger_flogger_sources_0_9//file",
     out = "com/google/flogger/flogger/0.9/flogger-0.9-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_flogger_flogger_system_backend_sources_0_9_extension",
     src = "@com_google_flogger_flogger_system_backend_sources_0_9//file",
     out = "com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_googlejavaformat_google_java_format_sources_1_5_extension",
     src = "@com_google_googlejavaformat_google_java_format_sources_1_5//file",
     out = "com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_guava_failureaccess_sources_1_0_3_extension",
     src = "@com_google_guava_failureaccess_sources_1_0_3//file",
     out = "com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_guava_guava_sources_33_4_8_jre_extension",
     src = "@com_google_guava_guava_sources_33_4_8_jre//file",
     out = "com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_guava_guava_testlib_sources_33_4_8_jre_extension",
     src = "@com_google_guava_guava_testlib_sources_33_4_8_jre//file",
     out = "com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_http_client_google_http_client_sources_1_47_1_extension",
     src = "@com_google_http_client_google_http_client_sources_1_47_1//file",
     out = "com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_http_client_google_http_client_gson_sources_1_47_1_extension",
     src = "@com_google_http_client_google_http_client_gson_sources_1_47_1//file",
     out = "com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_http_client_google_http_client_test_sources_1_47_1_extension",
     src = "@com_google_http_client_google_http_client_test_sources_1_47_1//file",
     out = "com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_j2objc_j2objc_annotations_sources_3_0_0_extension",
     src = "@com_google_j2objc_j2objc_annotations_sources_3_0_0//file",
     out = "com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_protobuf_protobuf_java_sources_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_java_sources_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_protobuf_protobuf_java_util_sources_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_java_util_sources_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_protobuf_protobuf_javalite_sources_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_javalite_sources_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_protobuf_protobuf_kotlin_sources_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_kotlin_sources_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_protobuf_protobuf_kotlin_lite_sources_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_kotlin_lite_sources_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_protobuf_protobuf_lite_sources_3_0_1_extension",
     src = "@com_google_protobuf_protobuf_lite_sources_3_0_1//file",
     out = "com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_truth_truth_sources_1_4_4_extension",
     src = "@com_google_truth_truth_sources_1_4_4//file",
     out = "com/google/truth/truth/1.4.4/truth-1.4.4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_nativelibs4java_jnaerator_runtime_sources_0_12_extension",
     src = "@com_nativelibs4java_jnaerator_runtime_sources_0_12//file",
     out = "com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_nativelibs4java_ochafik_util_sources_0_12_extension",
     src = "@com_nativelibs4java_ochafik_util_sources_0_12//file",
     out = "com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_squareup_javapoet_sources_1_13_0_extension",
     src = "@com_squareup_javapoet_sources_1_13_0//file",
     out = "com/squareup/javapoet/1.13.0/javapoet-1.13.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_squareup_kotlinpoet_sources_1_11_0_extension",
     src = "@com_squareup_kotlinpoet_sources_1_11_0//file",
     out = "com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_vaadin_external_google_android_json_sources_0_0_20131108_vaadin1_extension",
     src = "@com_vaadin_external_google_android_json_sources_0_0_20131108_vaadin1//file",
     out = "com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "commons_codec_commons_codec_sources_1_19_0_extension",
     src = "@commons_codec_commons_codec_sources_1_19_0//file",
     out = "commons-codec/commons-codec/1.19.0/commons-codec-1.19.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_api_sources_1_70_0_extension",
     src = "@io_grpc_grpc_api_sources_1_70_0//file",
     out = "io/grpc/grpc-api/1.70.0/grpc-api-1.70.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_context_sources_1_70_0_extension",
     src = "@io_grpc_grpc_context_sources_1_70_0//file",
     out = "io/grpc/grpc-context/1.70.0/grpc-context-1.70.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_ktor_ktor_events_sources_3_2_3_extension",
     src = "@io_ktor_ktor_events_sources_3_2_3//file",
     out = "io/ktor/ktor-events/3.2.3/ktor-events-3.2.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_ktor_ktor_http_sources_3_2_3_extension",
     src = "@io_ktor_ktor_http_sources_3_2_3//file",
     out = "io/ktor/ktor-http/3.2.3/ktor-http-3.2.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_ktor_ktor_http_cio_sources_3_2_3_extension",
     src = "@io_ktor_ktor_http_cio_sources_3_2_3//file",
     out = "io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_ktor_ktor_io_sources_3_2_3_extension",
     src = "@io_ktor_ktor_io_sources_3_2_3//file",
     out = "io/ktor/ktor-io/3.2.3/ktor-io-3.2.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_ktor_ktor_serialization_sources_3_2_3_extension",
     src = "@io_ktor_ktor_serialization_sources_3_2_3//file",
     out = "io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_ktor_ktor_server_core_sources_3_2_3_extension",
     src = "@io_ktor_ktor_server_core_sources_3_2_3//file",
     out = "io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_ktor_ktor_server_netty_sources_3_2_3_extension",
     src = "@io_ktor_ktor_server_netty_sources_3_2_3//file",
     out = "io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_ktor_ktor_utils_sources_3_2_3_extension",
     src = "@io_ktor_ktor_utils_sources_3_2_3//file",
     out = "io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_ktor_ktor_websockets_sources_3_2_3_extension",
     src = "@io_ktor_ktor_websockets_sources_3_2_3//file",
     out = "io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_opencensus_opencensus_api_sources_0_31_1_extension",
     src = "@io_opencensus_opencensus_api_sources_0_31_1//file",
     out = "io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_opencensus_opencensus_contrib_http_util_sources_0_31_1_extension",
     src = "@io_opencensus_opencensus_contrib_http_util_sources_0_31_1//file",
     out = "io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "jakarta_inject_jakarta_inject_api_sources_2_0_1_extension",
     src = "@jakarta_inject_jakarta_inject_api_sources_2_0_1//file",
     out = "jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "javax_annotation_jsr250_api_sources_1_0_extension",
     src = "@javax_annotation_jsr250_api_sources_1_0//file",
     out = "javax/annotation/jsr250-api/1.0/jsr250-api-1.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "javax_inject_javax_inject_sources_1_extension",
     src = "@javax_inject_javax_inject_sources_1//file",
     out = "javax/inject/javax.inject/1/javax.inject-1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "junit_junit_sources_4_13_2_extension",
     src = "@junit_junit_sources_4_13_2//file",
     out = "junit/junit/4.13.2/junit-4.13.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "net_bytebuddy_byte_buddy_sources_1_17_5_extension",
     src = "@net_bytebuddy_byte_buddy_sources_1_17_5//file",
     out = "net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "net_bytebuddy_byte_buddy_agent_sources_1_17_5_extension",
     src = "@net_bytebuddy_byte_buddy_agent_sources_1_17_5//file",
     out = "net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "net_java_dev_jna_jna_sources_5_10_0_extension",
     src = "@net_java_dev_jna_jna_sources_5_10_0//file",
     out = "net/java/dev/jna/jna/5.10.0/jna-5.10.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "net_java_jinput_jinput_sources_2_0_9_extension",
     src = "@net_java_jinput_jinput_sources_2_0_9//file",
     out = "net/java/jinput/jinput/2.0.9/jinput-2.0.9-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "net_ltgt_gradle_incap_incap_sources_0_2_extension",
     src = "@net_ltgt_gradle_incap_incap_sources_0_2//file",
     out = "net/ltgt/gradle/incap/incap/0.2/incap-0.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_httpcomponents_httpclient_sources_4_5_14_extension",
     src = "@org_apache_httpcomponents_httpclient_sources_4_5_14//file",
     out = "org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_httpcomponents_httpcore_sources_4_4_16_extension",
     src = "@org_apache_httpcomponents_httpcore_sources_4_4_16//file",
     out = "org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apiguardian_apiguardian_api_sources_1_1_2_extension",
     src = "@org_apiguardian_apiguardian_api_sources_1_1_2//file",
     out = "org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_checkerframework_checker_compat_qual_sources_2_5_3_extension",
     src = "@org_checkerframework_checker_compat_qual_sources_2_5_3//file",
     out = "org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_hamcrest_hamcrest_core_sources_1_3_extension",
     src = "@org_hamcrest_hamcrest_core_sources_1_3//file",
     out = "org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_metadata_jvm_sources_2_1_21_extension",
     src = "@org_jetbrains_kotlin_kotlin_metadata_jvm_sources_2_1_21//file",
     out = "org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_reflect_sources_2_1_21_extension",
     src = "@org_jetbrains_kotlin_kotlin_reflect_sources_2_1_21//file",
     out = "org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_stdlib_sources_2_2_0_extension",
     src = "@org_jetbrains_kotlin_kotlin_stdlib_sources_2_2_0//file",
     out = "org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7_sources_1_6_10_extension",
     src = "@org_jetbrains_kotlin_kotlin_stdlib_jdk7_sources_1_6_10//file",
     out = "org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8_sources_1_6_10_extension",
     src = "@org_jetbrains_kotlin_kotlin_stdlib_jdk8_sources_1_6_10//file",
     out = "org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_test_sources_2_2_0_extension",
     src = "@org_jetbrains_kotlin_kotlin_test_sources_2_2_0//file",
     out = "org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_sources_1_10_2_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_coroutines_core_sources_1_10_2//file",
     out = "org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_sources_1_10_2_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_sources_1_10_2//file",
     out = "org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_io_bytestring_sources_0_7_0_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_io_bytestring_sources_0_7_0//file",
     out = "org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_io_core_sources_0_7_0_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_io_core_sources_0_7_0//file",
     out = "org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_serialization_core_sources_1_8_1_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_serialization_core_sources_1_8_1//file",
     out = "org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_serialization_core_jvm_sources_1_8_1_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_serialization_core_jvm_sources_1_8_1//file",
     out = "org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jetbrains_annotations_sources_23_0_0_extension",
     src = "@org_jetbrains_annotations_sources_23_0_0//file",
     out = "org/jetbrains/annotations/23.0.0/annotations-23.0.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_bullet_sources_3_3_2_stable_extension",
     src = "@org_jmonkeyengine_jme3_bullet_sources_3_3_2_stable//file",
     out = "org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_bullet_native_sources_3_3_2_stable_extension",
     src = "@org_jmonkeyengine_jme3_bullet_native_sources_3_3_2_stable//file",
     out = "org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_core_sources_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_core_sources_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_desktop_sources_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_desktop_sources_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_effects_sources_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_effects_sources_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_lwjgl_sources_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_lwjgl_sources_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_lwjgl3_sources_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_lwjgl3_sources_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_terrain_sources_3_3_2_stable_extension",
     src = "@org_jmonkeyengine_jme3_terrain_sources_3_3_2_stable//file",
     out = "org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_testdata_sources_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_testdata_sources_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_jme3_vr_sources_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_vr_sources_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jmonkeyengine_lwjgl_sources_2_9_5_extension",
     src = "@org_jmonkeyengine_lwjgl_sources_2_9_5//file",
     out = "org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_jspecify_jspecify_sources_1_0_0_extension",
     src = "@org_jspecify_jspecify_sources_1_0_0//file",
     out = "org/jspecify/jspecify/1.0.0/jspecify-1.0.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_junit_jupiter_junit_jupiter_api_sources_5_13_4_extension",
     src = "@org_junit_jupiter_junit_jupiter_api_sources_5_13_4//file",
     out = "org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_junit_jupiter_junit_jupiter_engine_sources_5_13_4_extension",
     src = "@org_junit_jupiter_junit_jupiter_engine_sources_5_13_4//file",
     out = "org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_junit_platform_junit_platform_commons_sources_1_13_4_extension",
     src = "@org_junit_platform_junit_platform_commons_sources_1_13_4//file",
     out = "org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_junit_platform_junit_platform_engine_sources_1_13_4_extension",
     src = "@org_junit_platform_junit_platform_engine_sources_1_13_4//file",
     out = "org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_assimp_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_assimp_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_bgfx_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_bgfx_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_cuda_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_cuda_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_egl_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_egl_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_glfw_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_glfw_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_jawt_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jawt_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_jemalloc_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jemalloc_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_libdivide_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_libdivide_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_llvm_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_llvm_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_lmdb_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_lmdb_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_lz4_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_lz4_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_meow_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_meow_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_meshoptimizer_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_meshoptimizer_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_nanovg_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_nanovg_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_nfd_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_nfd_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_nuklear_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_nuklear_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_odbc_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_odbc_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_openal_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openal_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_opencl_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opencl_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_opengl_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengl_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_opengles_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengles_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_openvr_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openvr_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_openxr_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openxr_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_opus_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opus_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_ovr_sources_3_3_3_extension",
     src = "@org_lwjgl_lwjgl_ovr_sources_3_3_3//file",
     out = "org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_par_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_par_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_remotery_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_remotery_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_rpmalloc_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_rpmalloc_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_shaderc_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_shaderc_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_spvc_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_spvc_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_sse_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_sse_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_stb_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_stb_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_tinyexr_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_tinyexr_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_tootle_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_tootle_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_vma_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_vma_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_vulkan_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_vulkan_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_xxhash_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_xxhash_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_yoga_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_yoga_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjgl_lwjgl_zstd_sources_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_zstd_sources_3_3_6//file",
     out = "org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_lwjglx_lwjgl3_awt_sources_0_1_8_extension",
     src = "@org_lwjglx_lwjgl3_awt_sources_0_1_8//file",
     out = "org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_mockito_mockito_core_sources_5_18_0_extension",
     src = "@org_mockito_mockito_core_sources_5_18_0//file",
     out = "org/mockito/mockito-core/5.18.0/mockito-core-5.18.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_objenesis_objenesis_sources_3_3_extension",
     src = "@org_objenesis_objenesis_sources_3_3//file",
     out = "org/objenesis/objenesis/3.3/objenesis-3.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_opentest4j_opentest4j_sources_1_3_0_extension",
     src = "@org_opentest4j_opentest4j_sources_1_3_0//file",
     out = "org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_ow2_asm_asm_sources_9_7_extension",
     src = "@org_ow2_asm_asm_sources_9_7//file",
     out = "org/ow2/asm/asm/9.7/asm-9.7-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_skyscreamer_jsonassert_sources_1_5_3_extension",
     src = "@org_skyscreamer_jsonassert_sources_1_5_3//file",
     out = "org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_auto_factory_auto_factory",
	jar = "com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0.jar",
	srcjar = "com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0-sources.jar",
	deps = [
		":com_google_auto_service_auto_service_annotations",
		":com_google_auto_value_auto_value_annotations",
		":com_google_auto_auto_common",
		":com_google_guava_guava",
		":com_squareup_javapoet",
	],
	tags = [
		"maven_coordinates=com.google.auto.factory:auto-factory:1.1.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=9b4505cd7a60574d59386672c1d51d6154b803c892677eb909cf4155ebee771f",
	],
	maven_coordinates = "com.google.auto.factory:auto-factory:1.1.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_auto_factory_auto_factory_1_1_0",
	actual = "com_google_auto_factory_auto_factory",
	visibility = ["//visibility:public"],
)
java_plugin(
	name = "com_google_auto_factory_auto_factory__java_plugin__com_google_auto_factory_processor_AutoFactoryProcessor",
	deps = [":com_google_auto_factory_auto_factory"],
	generates_api = True,
processor_class = "com.google.auto.factory.processor.AutoFactoryProcessor",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_auto_factory_auto_factory_1_1_0_extension",
     src = "@com_google_auto_factory_auto_factory_1_1_0//file",
     out = "com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_auto_service_auto_service_annotations",
	jar = "com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1.jar",
	srcjar = "com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.auto.service:auto-service-annotations:1.1.1",
		"maven_url=https://repo1.maven.org/maven2/com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=16a76dd00a2650568447f5d6e3a9e2c809d9a42367d56b45215cfb89731f4d24",
	],
	maven_coordinates = "com.google.auto.service:auto-service-annotations:1.1.1",
	maven_url = "https://repo1.maven.org/maven2/com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_auto_service_auto_service_annotations_1_1_1",
	actual = "com_google_auto_service_auto_service_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_auto_service_auto_service_annotations_1_1_1_extension",
     src = "@com_google_auto_service_auto_service_annotations_1_1_1//file",
     out = "com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_auto_value_auto_value",
	jar = "com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0.jar",
	srcjar = "com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.auto.value:auto-value:1.11.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=aaf8d637bfed3c420436b9facf1b7a88d12c8785374e4202382783005319c2c3",
	],
	maven_coordinates = "com.google.auto.value:auto-value:1.11.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_auto_value_auto_value_1_11_0",
	actual = "com_google_auto_value_auto_value",
	visibility = ["//visibility:public"],
)
java_plugin(
	name = "com_google_auto_value_auto_value__java_plugin__com_google_auto_value_extension_memoized_processor_MemoizedValidator",
	deps = [":com_google_auto_value_auto_value"],
	generates_api = True,
processor_class = "com.google.auto.value.extension.memoized.processor.MemoizedValidator",
	visibility = ["//visibility:public"],
)
java_plugin(
	name = "com_google_auto_value_auto_value__java_plugin__com_google_auto_value_extension_toprettystring_processor_ToPrettyStringValidator",
	deps = [":com_google_auto_value_auto_value"],
	generates_api = True,
processor_class = "com.google.auto.value.extension.toprettystring.processor.ToPrettyStringValidator",
	visibility = ["//visibility:public"],
)
java_plugin(
	name = "com_google_auto_value_auto_value__java_plugin__com_google_auto_value_processor_AutoAnnotationProcessor",
	deps = [":com_google_auto_value_auto_value"],
	generates_api = True,
processor_class = "com.google.auto.value.processor.AutoAnnotationProcessor",
	visibility = ["//visibility:public"],
)
java_plugin(
	name = "com_google_auto_value_auto_value__java_plugin__com_google_auto_value_processor_AutoBuilderProcessor",
	deps = [":com_google_auto_value_auto_value"],
	generates_api = True,
processor_class = "com.google.auto.value.processor.AutoBuilderProcessor",
	visibility = ["//visibility:public"],
)
java_plugin(
	name = "com_google_auto_value_auto_value__java_plugin__com_google_auto_value_processor_AutoOneOfProcessor",
	deps = [":com_google_auto_value_auto_value"],
	generates_api = True,
processor_class = "com.google.auto.value.processor.AutoOneOfProcessor",
	visibility = ["//visibility:public"],
)
java_plugin(
	name = "com_google_auto_value_auto_value__java_plugin__com_google_auto_value_processor_AutoValueBuilderProcessor",
	deps = [":com_google_auto_value_auto_value"],
	generates_api = True,
processor_class = "com.google.auto.value.processor.AutoValueBuilderProcessor",
	visibility = ["//visibility:public"],
)
java_plugin(
	name = "com_google_auto_value_auto_value__java_plugin__com_google_auto_value_processor_AutoValueProcessor",
	deps = [":com_google_auto_value_auto_value"],
	generates_api = True,
processor_class = "com.google.auto.value.processor.AutoValueProcessor",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_auto_value_auto_value_1_11_0_extension",
     src = "@com_google_auto_value_auto_value_1_11_0//file",
     out = "com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_auto_value_auto_value_annotations",
	jar = "com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0.jar",
	srcjar = "com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.auto.value:auto-value-annotations:1.11.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5a055ce4255333b3346e1a8703da5bf8ff049532286fdcd31712d624abe111dd",
	],
	maven_coordinates = "com.google.auto.value:auto-value-annotations:1.11.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_auto_value_auto_value_annotations_1_11_0",
	actual = "com_google_auto_value_auto_value_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_auto_value_auto_value_annotations_1_11_0_extension",
     src = "@com_google_auto_value_auto_value_annotations_1_11_0//file",
     out = "com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_auto_auto_common",
	jar = "com/google/auto/auto-common/1.2.2/auto-common-1.2.2.jar",
	srcjar = "com/google/auto/auto-common/1.2.2/auto-common-1.2.2-sources.jar",
	deps = [
		":com_google_guava_guava",
	],
	tags = [
		"maven_coordinates=com.google.auto:auto-common:1.2.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/auto/auto-common/1.2.2/auto-common-1.2.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f50b1ce8a41fad31a8a819c052f8ffa362ea0a3dbe9ef8f7c7dc9a36d4738a59",
	],
	maven_coordinates = "com.google.auto:auto-common:1.2.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/auto/auto-common/1.2.2/auto-common-1.2.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_auto_auto_common_1_2_2",
	actual = "com_google_auto_auto_common",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_auto_auto_common_1_2_2_extension",
     src = "@com_google_auto_auto_common_1_2_2//file",
     out = "com/google/auto/auto-common/1.2.2/auto-common-1.2.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_code_findbugs_jsr305",
	jar = "com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar",
	srcjar = "com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.code.findbugs:jsr305:3.0.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=766ad2a0783f2687962c8ad74ceecc38a28b9f72a2d085ee438b7813e928d0c7",
	],
	maven_coordinates = "com.google.code.findbugs:jsr305:3.0.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_code_findbugs_jsr305_3_0_2",
	actual = "com_google_code_findbugs_jsr305",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_code_findbugs_jsr305_3_0_2_extension",
     src = "@com_google_code_findbugs_jsr305_3_0_2//file",
     out = "com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_code_gson_gson",
	jar = "com/google/code/gson/gson/2.13.1/gson-2.13.1.jar",
	srcjar = "com/google/code/gson/gson/2.13.1/gson-2.13.1-sources.jar",
	deps = [
		":com_google_errorprone_error_prone_annotations",
	],
	tags = [
		"maven_coordinates=com.google.code.gson:gson:2.13.1",
		"maven_url=https://repo1.maven.org/maven2/com/google/code/gson/gson/2.13.1/gson-2.13.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=94855942d4992f112946d3de1c334e709237b8126d8130bf07807c018a4a2120",
	],
	maven_coordinates = "com.google.code.gson:gson:2.13.1",
	maven_url = "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.13.1/gson-2.13.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_code_gson_gson_2_13_1",
	actual = "com_google_code_gson_gson",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_code_gson_gson_2_13_1_extension",
     src = "@com_google_code_gson_gson_2_13_1//file",
     out = "com/google/code/gson/gson/2.13.1/gson-2.13.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_dagger_dagger",
	jar = "com/google/dagger/dagger/2.57/dagger-2.57.jar",
	srcjar = "com/google/dagger/dagger/2.57/dagger-2.57-sources.jar",
	deps = [
		":jakarta_inject_jakarta_inject_api",
		":javax_inject_javax_inject",
		":org_jspecify_jspecify",
	],
	tags = [
		"maven_coordinates=com.google.dagger:dagger:2.57",
		"maven_url=https://repo1.maven.org/maven2/com/google/dagger/dagger/2.57/dagger-2.57.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=c094a62e6c2a37f76a55ad8727e21f794396e3f3c8259bf430d5f2c4c5ec44c7",
	],
	maven_coordinates = "com.google.dagger:dagger:2.57",
	maven_url = "https://repo1.maven.org/maven2/com/google/dagger/dagger/2.57/dagger-2.57.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_dagger_dagger_2_57",
	actual = "com_google_dagger_dagger",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_dagger_dagger_2_57_extension",
     src = "@com_google_dagger_dagger_2_57//file",
     out = "com/google/dagger/dagger/2.57/dagger-2.57.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_dagger_dagger_compiler",
	jar = "com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57.jar",
	srcjar = "com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57-sources.jar",
	deps = [
		":com_google_code_findbugs_jsr305",
		":com_google_dagger_dagger",
		":com_google_dagger_dagger_spi",
		":com_google_devtools_ksp_symbol_processing_api",
		":com_google_googlejavaformat_google_java_format",
		":com_google_guava_failureaccess",
		":com_google_guava_guava",
		":com_squareup_javapoet",
		":com_squareup_kotlinpoet",
		":javax_inject_javax_inject",
		":net_ltgt_gradle_incap_incap",
		":org_checkerframework_checker_compat_qual",
		":org_jetbrains_kotlin_kotlin_metadata_jvm",
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=com.google.dagger:dagger-compiler:2.57",
		"maven_url=https://repo1.maven.org/maven2/com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=06cd5d3b4f30924afc48009d2f3b4e236599e3657dc0a994f2e3752721f86525",
	],
	maven_coordinates = "com.google.dagger:dagger-compiler:2.57",
	maven_url = "https://repo1.maven.org/maven2/com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_dagger_dagger_compiler_2_57",
	actual = "com_google_dagger_dagger_compiler",
	visibility = ["//visibility:public"],
)
java_plugin(
	name = "com_google_dagger_dagger_compiler__java_plugin__dagger_internal_codegen_ComponentProcessor",
	deps = [":com_google_dagger_dagger_compiler"],
	generates_api = True,
processor_class = "dagger.internal.codegen.ComponentProcessor",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_dagger_dagger_compiler_2_57_extension",
     src = "@com_google_dagger_dagger_compiler_2_57//file",
     out = "com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_dagger_dagger_producers",
	jar = "com/google/dagger/dagger-producers/2.57/dagger-producers-2.57.jar",
	srcjar = "com/google/dagger/dagger-producers/2.57/dagger-producers-2.57-sources.jar",
	deps = [
		":com_google_dagger_dagger",
		":com_google_guava_failureaccess",
		":com_google_guava_guava",
		":javax_inject_javax_inject",
		":org_checkerframework_checker_compat_qual",
	],
	tags = [
		"maven_coordinates=com.google.dagger:dagger-producers:2.57",
		"maven_url=https://repo1.maven.org/maven2/com/google/dagger/dagger-producers/2.57/dagger-producers-2.57.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=c0034b2b14a1cfc1eb32d1379d9c5f0fc004a2af4f976633f6f3b7477945eac8",
	],
	maven_coordinates = "com.google.dagger:dagger-producers:2.57",
	maven_url = "https://repo1.maven.org/maven2/com/google/dagger/dagger-producers/2.57/dagger-producers-2.57.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_dagger_dagger_producers_2_57",
	actual = "com_google_dagger_dagger_producers",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_dagger_dagger_producers_2_57_extension",
     src = "@com_google_dagger_dagger_producers_2_57//file",
     out = "com/google/dagger/dagger-producers/2.57/dagger-producers-2.57.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_dagger_dagger_spi",
	jar = "com/google/dagger/dagger-spi/2.57/dagger-spi-2.57.jar",
	srcjar = "com/google/dagger/dagger-spi/2.57/dagger-spi-2.57-sources.jar",
	deps = [
		":com_google_code_findbugs_jsr305",
		":com_google_dagger_dagger",
		":com_google_devtools_ksp_symbol_processing_api",
		":com_google_guava_failureaccess",
		":com_google_guava_guava",
		":com_squareup_javapoet",
		":javax_inject_javax_inject",
	],
	tags = [
		"maven_coordinates=com.google.dagger:dagger-spi:2.57",
		"maven_url=https://repo1.maven.org/maven2/com/google/dagger/dagger-spi/2.57/dagger-spi-2.57.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=c14cbf028011edc416e49379ffc4ec34ca5c35e1b5d8408ad34141f1c3ae969c",
	],
	maven_coordinates = "com.google.dagger:dagger-spi:2.57",
	maven_url = "https://repo1.maven.org/maven2/com/google/dagger/dagger-spi/2.57/dagger-spi-2.57.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_dagger_dagger_spi_2_57",
	actual = "com_google_dagger_dagger_spi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_dagger_dagger_spi_2_57_extension",
     src = "@com_google_dagger_dagger_spi_2_57//file",
     out = "com/google/dagger/dagger-spi/2.57/dagger-spi-2.57.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_devtools_ksp_symbol_processing_api",
	jar = "com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2.jar",
	srcjar = "com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=com.google.devtools.ksp:symbol-processing-api:2.1.21-2.0.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=588d17c4ea982b11028c2c82cb1d7300d269023a8068009f9ae9fb2c9c20c6d9",
	],
	maven_coordinates = "com.google.devtools.ksp:symbol-processing-api:2.1.21-2.0.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_devtools_ksp_symbol_processing_api_2_1_21_2_0_2",
	actual = "com_google_devtools_ksp_symbol_processing_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_devtools_ksp_symbol_processing_api_2_1_21_2_0_2_extension",
     src = "@com_google_devtools_ksp_symbol_processing_api_2_1_21_2_0_2//file",
     out = "com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_errorprone_error_prone_annotations",
	jar = "com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0.jar",
	srcjar = "com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.errorprone:error_prone_annotations:2.38.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6661d5335090a5fc61dd869d2095bc6c1e2156e3aa47a6e4ababdf64c99a7889",
	],
	maven_coordinates = "com.google.errorprone:error_prone_annotations:2.38.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_errorprone_error_prone_annotations_2_38_0",
	actual = "com_google_errorprone_error_prone_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_errorprone_error_prone_annotations_2_38_0_extension",
     src = "@com_google_errorprone_error_prone_annotations_2_38_0//file",
     out = "com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_errorprone_javac_shaded",
	jar = "com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3.jar",
	srcjar = "com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.errorprone:javac-shaded:9-dev-r4023-3",
		"maven_url=https://repo1.maven.org/maven2/com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=65bfccf60986c47fbc17c9ebab0be626afc41741e0a6ec7109e0768817a36f30",
	],
	maven_coordinates = "com.google.errorprone:javac-shaded:9-dev-r4023-3",
	maven_url = "https://repo1.maven.org/maven2/com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_errorprone_javac_shaded_9_dev_r4023_3",
	actual = "com_google_errorprone_javac_shaded",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_errorprone_javac_shaded_9_dev_r4023_3_extension",
     src = "@com_google_errorprone_javac_shaded_9_dev_r4023_3//file",
     out = "com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_flogger_flogger",
	jar = "com/google/flogger/flogger/0.9/flogger-0.9.jar",
	srcjar = "com/google/flogger/flogger/0.9/flogger-0.9-sources.jar",
	deps = [
		":org_jspecify_jspecify",
	],
	tags = [
		"maven_coordinates=com.google.flogger:flogger:0.9",
		"maven_url=https://repo1.maven.org/maven2/com/google/flogger/flogger/0.9/flogger-0.9.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b524636155975d0fbcbe6fe3977c23b35404f48b1458c68779004abba15fc765",
	],
	maven_coordinates = "com.google.flogger:flogger:0.9",
	maven_url = "https://repo1.maven.org/maven2/com/google/flogger/flogger/0.9/flogger-0.9.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_flogger_flogger_0_9",
	actual = "com_google_flogger_flogger",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_flogger_flogger_0_9_extension",
     src = "@com_google_flogger_flogger_0_9//file",
     out = "com/google/flogger/flogger/0.9/flogger-0.9.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_flogger_flogger_system_backend",
	jar = "com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9.jar",
	srcjar = "com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9-sources.jar",
	deps = [
		":com_google_flogger_flogger",
		":org_jspecify_jspecify",
	],
	tags = [
		"maven_coordinates=com.google.flogger:flogger-system-backend:0.9",
		"maven_url=https://repo1.maven.org/maven2/com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1658db89e5d9e6c6e63565d80ad041466cb330b8686ba972d843686c62852fac",
	],
	maven_coordinates = "com.google.flogger:flogger-system-backend:0.9",
	maven_url = "https://repo1.maven.org/maven2/com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_flogger_flogger_system_backend_0_9",
	actual = "com_google_flogger_flogger_system_backend",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_flogger_flogger_system_backend_0_9_extension",
     src = "@com_google_flogger_flogger_system_backend_0_9//file",
     out = "com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_googlejavaformat_google_java_format",
	jar = "com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5.jar",
	srcjar = "com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5-sources.jar",
	deps = [
		":com_google_errorprone_javac_shaded",
		":com_google_guava_guava",
	],
	tags = [
		"maven_coordinates=com.google.googlejavaformat:google-java-format:1.5",
		"maven_url=https://repo1.maven.org/maven2/com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=aa19ad7850fb85178aa22f2fddb163b84d6ce4d0035872f30d4408195ca1144e",
	],
	maven_coordinates = "com.google.googlejavaformat:google-java-format:1.5",
	maven_url = "https://repo1.maven.org/maven2/com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_googlejavaformat_google_java_format_1_5",
	actual = "com_google_googlejavaformat_google_java_format",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_googlejavaformat_google_java_format_1_5_extension",
     src = "@com_google_googlejavaformat_google_java_format_1_5//file",
     out = "com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_guava_failureaccess",
	jar = "com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar",
	srcjar = "com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.guava:failureaccess:1.0.3",
		"maven_url=https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=cbfc3906b19b8f55dd7cfd6dfe0aa4532e834250d7f080bd8d211a3e246b59cb",
	],
	maven_coordinates = "com.google.guava:failureaccess:1.0.3",
	maven_url = "https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_guava_failureaccess_1_0_3",
	actual = "com_google_guava_failureaccess",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_guava_failureaccess_1_0_3_extension",
     src = "@com_google_guava_failureaccess_1_0_3//file",
     out = "com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_guava_guava",
	jar = "com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar",
	srcjar = "com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre-sources.jar",
	deps = [
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_failureaccess",
		":com_google_guava_listenablefuture",
		":com_google_j2objc_j2objc_annotations",
		":org_jspecify_jspecify",
	],
	tags = [
		"maven_coordinates=com.google.guava:guava:33.4.8-jre",
		"maven_url=https://repo1.maven.org/maven2/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f3d7f57f67fd622f4d468dfdd692b3a5e3909246c28017ac3263405f0fe617ed",
	],
	maven_coordinates = "com.google.guava:guava:33.4.8-jre",
	maven_url = "https://repo1.maven.org/maven2/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_guava_guava_33_4_8_jre",
	actual = "com_google_guava_guava",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_guava_guava_33_4_8_jre_extension",
     src = "@com_google_guava_guava_33_4_8_jre//file",
     out = "com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_guava_guava_testlib",
	jar = "com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre.jar",
	srcjar = "com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre-sources.jar",
	deps = [
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":com_google_j2objc_j2objc_annotations",
		":junit_junit",
		":org_jspecify_jspecify",
	],
	tags = [
		"maven_coordinates=com.google.guava:guava-testlib:33.4.8-jre",
		"maven_url=https://repo1.maven.org/maven2/com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a58a38746f97e02ae3d067b74a25ad2b136650227baa7124ce03fa3bce4e8576",
	],
	maven_coordinates = "com.google.guava:guava-testlib:33.4.8-jre",
	maven_url = "https://repo1.maven.org/maven2/com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_guava_guava_testlib_33_4_8_jre",
	actual = "com_google_guava_guava_testlib",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_guava_guava_testlib_33_4_8_jre_extension",
     src = "@com_google_guava_guava_testlib_33_4_8_jre//file",
     out = "com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_guava_listenablefuture",
	jar = "com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava",
		"maven_url=https://repo1.maven.org/maven2/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b372a037d4230aa57fbeffdef30fd6123f9c0c2db85d0aced00c91b974f33f99",
	],
	maven_coordinates = "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava",
	maven_url = "https://repo1.maven.org/maven2/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_guava_listenablefuture_9999_0_empty_to_avoid_conflict_with_guava",
	actual = "com_google_guava_listenablefuture",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_guava_listenablefuture_9999_0_empty_to_avoid_conflict_with_guava_extension",
     src = "@com_google_guava_listenablefuture_9999_0_empty_to_avoid_conflict_with_guava//file",
     out = "com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_http_client_google_http_client",
	jar = "com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1.jar",
	srcjar = "com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1-sources.jar",
	deps = [
		":com_google_code_findbugs_jsr305",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":com_google_j2objc_j2objc_annotations",
		":io_grpc_grpc_context",
		":io_opencensus_opencensus_api",
		":io_opencensus_opencensus_contrib_http_util",
		":org_apache_httpcomponents_httpclient",
		":org_apache_httpcomponents_httpcore",
	],
	tags = [
		"maven_coordinates=com.google.http-client:google-http-client:1.47.1",
		"maven_url=https://repo1.maven.org/maven2/com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=22447fde9f2e33e27a23a25953b1c622ead6c055c761fde6ca50573c9473457a",
	],
	maven_coordinates = "com.google.http-client:google-http-client:1.47.1",
	maven_url = "https://repo1.maven.org/maven2/com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_http_client_google_http_client_1_47_1",
	actual = "com_google_http_client_google_http_client",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_http_client_google_http_client_1_47_1_extension",
     src = "@com_google_http_client_google_http_client_1_47_1//file",
     out = "com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_http_client_google_http_client_gson",
	jar = "com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1.jar",
	srcjar = "com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1-sources.jar",
	deps = [
		":com_google_code_gson_gson",
		":com_google_http_client_google_http_client",
	],
	tags = [
		"maven_coordinates=com.google.http-client:google-http-client-gson:1.47.1",
		"maven_url=https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=64ac2b1313dca6b6fc9bd14128ab186528fe992b094f5371fa7f828eed8903bd",
	],
	maven_coordinates = "com.google.http-client:google-http-client-gson:1.47.1",
	maven_url = "https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_http_client_google_http_client_gson_1_47_1",
	actual = "com_google_http_client_google_http_client_gson",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_http_client_google_http_client_gson_1_47_1_extension",
     src = "@com_google_http_client_google_http_client_gson_1_47_1//file",
     out = "com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_http_client_google_http_client_test",
	jar = "com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1.jar",
	srcjar = "com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1-sources.jar",
	deps = [
		":com_google_http_client_google_http_client",
		":junit_junit",
	],
	tags = [
		"maven_coordinates=com.google.http-client:google-http-client-test:1.47.1",
		"maven_url=https://repo1.maven.org/maven2/com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ec54daf64c976535f5b63a32a76b84a3a1d54a7728ab95b0752b7e2c8c273106",
	],
	maven_coordinates = "com.google.http-client:google-http-client-test:1.47.1",
	maven_url = "https://repo1.maven.org/maven2/com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_http_client_google_http_client_test_1_47_1",
	actual = "com_google_http_client_google_http_client_test",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_http_client_google_http_client_test_1_47_1_extension",
     src = "@com_google_http_client_google_http_client_test_1_47_1//file",
     out = "com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_j2objc_j2objc_annotations",
	jar = "com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar",
	srcjar = "com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.j2objc:j2objc-annotations:3.0.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=88241573467ddca44ffd4d74aa04c2bbfd11bf7c17e0c342c94c9de7a70a7c64",
	],
	maven_coordinates = "com.google.j2objc:j2objc-annotations:3.0.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_j2objc_j2objc_annotations_3_0_0",
	actual = "com_google_j2objc_j2objc_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_j2objc_j2objc_annotations_3_0_0_extension",
     src = "@com_google_j2objc_j2objc_annotations_3_0_0//file",
     out = "com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_java",
	jar = "com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2.jar",
	srcjar = "com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-java:4.32.0-RC2",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=027dd8de3d607b4ac0abc800693ac760bb5bf1a88f826a974fdc2c0e1674a56b",
	],
	maven_coordinates = "com.google.protobuf:protobuf-java:4.32.0-RC2",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_java_4_32_0_RC2",
	actual = "com_google_protobuf_protobuf_java",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_java_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_java_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_java_util",
	jar = "com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2.jar",
	srcjar = "com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2-sources.jar",
	deps = [
		":com_google_code_findbugs_jsr305",
		":com_google_code_gson_gson",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":com_google_j2objc_j2objc_annotations",
		":com_google_protobuf_protobuf_java",
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-java-util:4.32.0-RC2",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=4436ea2e0f5c721ee159925494f3496495d1d91dfb47497e1cea68e703e0c452",
	],
	maven_coordinates = "com.google.protobuf:protobuf-java-util:4.32.0-RC2",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_java_util_4_32_0_RC2",
	actual = "com_google_protobuf_protobuf_java_util",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_java_util_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_java_util_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_javalite",
	jar = "com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2.jar",
	srcjar = "com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-javalite:4.32.0-RC2",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ee87fb4181b201f98c9821934f5326ed115930540a224675a9ab64067fbbec0e",
	],
	maven_coordinates = "com.google.protobuf:protobuf-javalite:4.32.0-RC2",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_javalite_4_32_0_RC2",
	actual = "com_google_protobuf_protobuf_javalite",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_javalite_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_javalite_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_kotlin",
	jar = "com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2.jar",
	srcjar = "com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2-sources.jar",
	deps = [
		":com_google_protobuf_protobuf_java",
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-kotlin:4.32.0-RC2",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2a0d008ff2d4edd9a7f7024068dc49ad62526f9bffc08d416b3d8c6c969be074",
	],
	maven_coordinates = "com.google.protobuf:protobuf-kotlin:4.32.0-RC2",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_kotlin_4_32_0_RC2",
	actual = "com_google_protobuf_protobuf_kotlin",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_kotlin_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_kotlin_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_kotlin_lite",
	jar = "com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2.jar",
	srcjar = "com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2-sources.jar",
	deps = [
		":com_google_protobuf_protobuf_javalite",
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-kotlin-lite:4.32.0-RC2",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2946c6526850e36c5de98e03fcd3cba8d90cdd73a2c21c2f79eb04d28a008da5",
	],
	maven_coordinates = "com.google.protobuf:protobuf-kotlin-lite:4.32.0-RC2",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_kotlin_lite_4_32_0_RC2",
	actual = "com_google_protobuf_protobuf_kotlin_lite",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_kotlin_lite_4_32_0_RC2_extension",
     src = "@com_google_protobuf_protobuf_kotlin_lite_4_32_0_RC2//file",
     out = "com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_lite",
	jar = "com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1.jar",
	srcjar = "com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-lite:3.0.1",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1413393db84e4adef79b2997d9dfeb4793d8f93d196f8347808d15711f0bc69c",
	],
	maven_coordinates = "com.google.protobuf:protobuf-lite:3.0.1",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_lite_3_0_1",
	actual = "com_google_protobuf_protobuf_lite",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_lite_3_0_1_extension",
     src = "@com_google_protobuf_protobuf_lite_3_0_1//file",
     out = "com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_truth_truth",
	jar = "com/google/truth/truth/1.4.4/truth-1.4.4.jar",
	srcjar = "com/google/truth/truth/1.4.4/truth-1.4.4-sources.jar",
	deps = [
		":com_google_auto_value_auto_value_annotations",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":junit_junit",
		":org_jspecify_jspecify",
		":org_ow2_asm_asm",
	],
	tags = [
		"maven_coordinates=com.google.truth:truth:1.4.4",
		"maven_url=https://repo1.maven.org/maven2/com/google/truth/truth/1.4.4/truth-1.4.4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=52c86cddadc31bc8457c1e15689fc6b75e2e97ce2a83d8b54b795d556d489f8c",
	],
	maven_coordinates = "com.google.truth:truth:1.4.4",
	maven_url = "https://repo1.maven.org/maven2/com/google/truth/truth/1.4.4/truth-1.4.4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_truth_truth_1_4_4",
	actual = "com_google_truth_truth",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_truth_truth_1_4_4_extension",
     src = "@com_google_truth_truth_1_4_4//file",
     out = "com/google/truth/truth/1.4.4/truth-1.4.4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_nativelibs4java_jnaerator_runtime",
	jar = "com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12.jar",
	srcjar = "com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12-sources.jar",
	deps = [
		":com_nativelibs4java_ochafik_util",
		":net_java_dev_jna_jna",
	],
	tags = [
		"maven_coordinates=com.nativelibs4java:jnaerator-runtime:0.12",
		"maven_url=https://repo1.maven.org/maven2/com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b0c1d95825285c7eff122ad9bc044079ee106d2efb5409f1faeb1e7a92b6ebc9",
	],
	maven_coordinates = "com.nativelibs4java:jnaerator-runtime:0.12",
	maven_url = "https://repo1.maven.org/maven2/com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_nativelibs4java_jnaerator_runtime_0_12",
	actual = "com_nativelibs4java_jnaerator_runtime",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_nativelibs4java_jnaerator_runtime_0_12_extension",
     src = "@com_nativelibs4java_jnaerator_runtime_0_12//file",
     out = "com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_nativelibs4java_ochafik_util",
	jar = "com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12.jar",
	srcjar = "com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.nativelibs4java:ochafik-util:0.12",
		"maven_url=https://repo1.maven.org/maven2/com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6387b61e01b1d8641ddce36497e4d08756227b60d7b9071063820017043ecd1a",
	],
	maven_coordinates = "com.nativelibs4java:ochafik-util:0.12",
	maven_url = "https://repo1.maven.org/maven2/com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_nativelibs4java_ochafik_util_0_12",
	actual = "com_nativelibs4java_ochafik_util",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_nativelibs4java_ochafik_util_0_12_extension",
     src = "@com_nativelibs4java_ochafik_util_0_12//file",
     out = "com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_squareup_javapoet",
	jar = "com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar",
	srcjar = "com/squareup/javapoet/1.13.0/javapoet-1.13.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.squareup:javapoet:1.13.0",
		"maven_url=https://repo1.maven.org/maven2/com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=4c7517e848a71b36d069d12bb3bf46a70fd4cda3105d822b0ed2e19c00b69291",
	],
	maven_coordinates = "com.squareup:javapoet:1.13.0",
	maven_url = "https://repo1.maven.org/maven2/com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_squareup_javapoet_1_13_0",
	actual = "com_squareup_javapoet",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_squareup_javapoet_1_13_0_extension",
     src = "@com_squareup_javapoet_1_13_0//file",
     out = "com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_squareup_kotlinpoet",
	jar = "com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0.jar",
	srcjar = "com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_reflect",
		":org_jetbrains_kotlin_kotlin_stdlib_jdk8",
	],
	tags = [
		"maven_coordinates=com.squareup:kotlinpoet:1.11.0",
		"maven_url=https://repo1.maven.org/maven2/com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2887ada1ca03dd83baa2758640d87e840d1907564db0ef88d2289c868a980492",
	],
	maven_coordinates = "com.squareup:kotlinpoet:1.11.0",
	maven_url = "https://repo1.maven.org/maven2/com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_squareup_kotlinpoet_1_11_0",
	actual = "com_squareup_kotlinpoet",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_squareup_kotlinpoet_1_11_0_extension",
     src = "@com_squareup_kotlinpoet_1_11_0//file",
     out = "com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_vaadin_external_google_android_json",
	jar = "com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.jar",
	srcjar = "com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.vaadin.external.google:android-json:0.0.20131108.vaadin1",
		"maven_url=https://repo1.maven.org/maven2/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=dfb7bae2f404cfe0b72b4d23944698cb716b7665171812a0a4d0f5926c0fac79",
	],
	maven_coordinates = "com.vaadin.external.google:android-json:0.0.20131108.vaadin1",
	maven_url = "https://repo1.maven.org/maven2/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_vaadin_external_google_android_json_0_0_20131108_vaadin1",
	actual = "com_vaadin_external_google_android_json",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_vaadin_external_google_android_json_0_0_20131108_vaadin1_extension",
     src = "@com_vaadin_external_google_android_json_0_0_20131108_vaadin1//file",
     out = "com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "commons_codec_commons_codec",
	jar = "commons-codec/commons-codec/1.19.0/commons-codec-1.19.0.jar",
	srcjar = "commons-codec/commons-codec/1.19.0/commons-codec-1.19.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=commons-codec:commons-codec:1.19.0",
		"maven_url=https://repo1.maven.org/maven2/commons-codec/commons-codec/1.19.0/commons-codec-1.19.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5c3881e4f556855e9c532927ee0c9dfde94cc66760d5805c031a59887070af5f",
	],
	maven_coordinates = "commons-codec:commons-codec:1.19.0",
	maven_url = "https://repo1.maven.org/maven2/commons-codec/commons-codec/1.19.0/commons-codec-1.19.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "commons_codec_commons_codec_1_19_0",
	actual = "commons_codec_commons_codec",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "commons_codec_commons_codec_1_19_0_extension",
     src = "@commons_codec_commons_codec_1_19_0//file",
     out = "commons-codec/commons-codec/1.19.0/commons-codec-1.19.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_api",
	jar = "io/grpc/grpc-api/1.70.0/grpc-api-1.70.0.jar",
	srcjar = "io/grpc/grpc-api/1.70.0/grpc-api-1.70.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-api:1.70.0",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-api/1.70.0/grpc-api-1.70.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=45faf2ac1bf2791e8fdabce53684a86b62c99b84cba26fb13a5ba3f4abf80d6c",
	],
	maven_coordinates = "io.grpc:grpc-api:1.70.0",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-api/1.70.0/grpc-api-1.70.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_api_1_70_0",
	actual = "io_grpc_grpc_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_api_1_70_0_extension",
     src = "@io_grpc_grpc_api_1_70_0//file",
     out = "io/grpc/grpc-api/1.70.0/grpc-api-1.70.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_context",
	jar = "io/grpc/grpc-context/1.70.0/grpc-context-1.70.0.jar",
	srcjar = "io/grpc/grpc-context/1.70.0/grpc-context-1.70.0-sources.jar",
	deps = [
		":io_grpc_grpc_api",
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-context:1.70.0",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-context/1.70.0/grpc-context-1.70.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=eb2824831c0ac03e741efda86b141aa863a481ebc4aaf5a5c1f13a481dbb40ff",
	],
	maven_coordinates = "io.grpc:grpc-context:1.70.0",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-context/1.70.0/grpc-context-1.70.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_context_1_70_0",
	actual = "io_grpc_grpc_context",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_context_1_70_0_extension",
     src = "@io_grpc_grpc_context_1_70_0//file",
     out = "io/grpc/grpc-context/1.70.0/grpc-context-1.70.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_ktor_ktor_events",
	jar = "io/ktor/ktor-events/3.2.3/ktor-events-3.2.3.jar",
	srcjar = "io/ktor/ktor-events/3.2.3/ktor-events-3.2.3-sources.jar",
	deps = [
		":io_ktor_ktor_utils",
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_coroutines_core",
	],
	tags = [
		"maven_coordinates=io.ktor:ktor-events:3.2.3",
		"maven_url=https://repo1.maven.org/maven2/io/ktor/ktor-events/3.2.3/ktor-events-3.2.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=44d19b95ee684c26889ed56008cd70f8fe2ccf313f6676c653d0e366d51a09de",
	],
	maven_coordinates = "io.ktor:ktor-events:3.2.3",
	maven_url = "https://repo1.maven.org/maven2/io/ktor/ktor-events/3.2.3/ktor-events-3.2.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_ktor_ktor_events_3_2_3",
	actual = "io_ktor_ktor_events",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_ktor_ktor_events_3_2_3_extension",
     src = "@io_ktor_ktor_events_3_2_3//file",
     out = "io/ktor/ktor-events/3.2.3/ktor-events-3.2.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_ktor_ktor_http",
	jar = "io/ktor/ktor-http/3.2.3/ktor-http-3.2.3.jar",
	srcjar = "io/ktor/ktor-http/3.2.3/ktor-http-3.2.3-sources.jar",
	deps = [
		":io_ktor_ktor_utils",
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_coroutines_core",
		":org_jetbrains_kotlinx_kotlinx_serialization_core",
	],
	tags = [
		"maven_coordinates=io.ktor:ktor-http:3.2.3",
		"maven_url=https://repo1.maven.org/maven2/io/ktor/ktor-http/3.2.3/ktor-http-3.2.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=c77fdc26a5d31986c7cdfa7d873428bb75aa185887de1670cee54d1d02f413fa",
	],
	maven_coordinates = "io.ktor:ktor-http:3.2.3",
	maven_url = "https://repo1.maven.org/maven2/io/ktor/ktor-http/3.2.3/ktor-http-3.2.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_ktor_ktor_http_3_2_3",
	actual = "io_ktor_ktor_http",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_ktor_ktor_http_3_2_3_extension",
     src = "@io_ktor_ktor_http_3_2_3//file",
     out = "io/ktor/ktor-http/3.2.3/ktor-http-3.2.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_ktor_ktor_http_cio",
	jar = "io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3.jar",
	srcjar = "io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3-sources.jar",
	deps = [
		":io_ktor_ktor_http",
		":io_ktor_ktor_io",
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_coroutines_core",
	],
	tags = [
		"maven_coordinates=io.ktor:ktor-http-cio:3.2.3",
		"maven_url=https://repo1.maven.org/maven2/io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d5bed64d01114adcc2bb0b4c8953e1444511d652c2e4375258d1c56b5ba701ff",
	],
	maven_coordinates = "io.ktor:ktor-http-cio:3.2.3",
	maven_url = "https://repo1.maven.org/maven2/io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_ktor_ktor_http_cio_3_2_3",
	actual = "io_ktor_ktor_http_cio",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_ktor_ktor_http_cio_3_2_3_extension",
     src = "@io_ktor_ktor_http_cio_3_2_3//file",
     out = "io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_ktor_ktor_io",
	jar = "io/ktor/ktor-io/3.2.3/ktor-io-3.2.3.jar",
	srcjar = "io/ktor/ktor-io/3.2.3/ktor-io-3.2.3-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_coroutines_core",
		":org_jetbrains_kotlinx_kotlinx_io_core",
	],
	tags = [
		"maven_coordinates=io.ktor:ktor-io:3.2.3",
		"maven_url=https://repo1.maven.org/maven2/io/ktor/ktor-io/3.2.3/ktor-io-3.2.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=0fc0d380b69c306f8bab2b701c814b463a01987f898e14949cf9b268da36185b",
	],
	maven_coordinates = "io.ktor:ktor-io:3.2.3",
	maven_url = "https://repo1.maven.org/maven2/io/ktor/ktor-io/3.2.3/ktor-io-3.2.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_ktor_ktor_io_3_2_3",
	actual = "io_ktor_ktor_io",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_ktor_ktor_io_3_2_3_extension",
     src = "@io_ktor_ktor_io_3_2_3//file",
     out = "io/ktor/ktor-io/3.2.3/ktor-io-3.2.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_ktor_ktor_serialization",
	jar = "io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3.jar",
	srcjar = "io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3-sources.jar",
	deps = [
		":io_ktor_ktor_websockets",
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_coroutines_core",
	],
	tags = [
		"maven_coordinates=io.ktor:ktor-serialization:3.2.3",
		"maven_url=https://repo1.maven.org/maven2/io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=cfce9ba600d909b87221840ff2672685d91ccda738bd627a7c71318f732ea3b3",
	],
	maven_coordinates = "io.ktor:ktor-serialization:3.2.3",
	maven_url = "https://repo1.maven.org/maven2/io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_ktor_ktor_serialization_3_2_3",
	actual = "io_ktor_ktor_serialization",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_ktor_ktor_serialization_3_2_3_extension",
     src = "@io_ktor_ktor_serialization_3_2_3//file",
     out = "io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_ktor_ktor_server_core",
	jar = "io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3.jar",
	srcjar = "io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3-sources.jar",
	deps = [
		":io_ktor_ktor_events",
		":io_ktor_ktor_http",
		":io_ktor_ktor_http_cio",
		":io_ktor_ktor_serialization",
		":io_ktor_ktor_utils",
		":io_ktor_ktor_websockets",
		":org_jetbrains_kotlin_kotlin_reflect",
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_coroutines_core",
	],
	tags = [
		"maven_coordinates=io.ktor:ktor-server-core:3.2.3",
		"maven_url=https://repo1.maven.org/maven2/io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d440015063ec55bab257c323725b74a5d6cc42c760afa417fa94682496252216",
	],
	maven_coordinates = "io.ktor:ktor-server-core:3.2.3",
	maven_url = "https://repo1.maven.org/maven2/io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_ktor_ktor_server_core_3_2_3",
	actual = "io_ktor_ktor_server_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_ktor_ktor_server_core_3_2_3_extension",
     src = "@io_ktor_ktor_server_core_3_2_3//file",
     out = "io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_ktor_ktor_server_netty",
	jar = "io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3.jar",
	srcjar = "io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_coroutines_core",
	],
	tags = [
		"maven_coordinates=io.ktor:ktor-server-netty:3.2.3",
		"maven_url=https://repo1.maven.org/maven2/io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=089e24417bf9f6c34eec5720eebb2cbcc82ea2855ecef90133eb17e1079d5868",
	],
	maven_coordinates = "io.ktor:ktor-server-netty:3.2.3",
	maven_url = "https://repo1.maven.org/maven2/io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_ktor_ktor_server_netty_3_2_3",
	actual = "io_ktor_ktor_server_netty",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_ktor_ktor_server_netty_3_2_3_extension",
     src = "@io_ktor_ktor_server_netty_3_2_3//file",
     out = "io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_ktor_ktor_utils",
	jar = "io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3.jar",
	srcjar = "io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3-sources.jar",
	deps = [
		":io_ktor_ktor_io",
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_coroutines_core",
		":org_jetbrains_kotlinx_kotlinx_serialization_core",
	],
	tags = [
		"maven_coordinates=io.ktor:ktor-utils:3.2.3",
		"maven_url=https://repo1.maven.org/maven2/io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1e55afd47df15c3ca5527d8252174ce6cf5d71c4af742ba4a8142c67c62caef3",
	],
	maven_coordinates = "io.ktor:ktor-utils:3.2.3",
	maven_url = "https://repo1.maven.org/maven2/io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_ktor_ktor_utils_3_2_3",
	actual = "io_ktor_ktor_utils",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_ktor_ktor_utils_3_2_3_extension",
     src = "@io_ktor_ktor_utils_3_2_3//file",
     out = "io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_ktor_ktor_websockets",
	jar = "io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3.jar",
	srcjar = "io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3-sources.jar",
	deps = [
		":io_ktor_ktor_http",
		":io_ktor_ktor_utils",
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_coroutines_core",
	],
	tags = [
		"maven_coordinates=io.ktor:ktor-websockets:3.2.3",
		"maven_url=https://repo1.maven.org/maven2/io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=82a95ddb9baf163e4036ee2d66e68a28315c5fd2eeeb97eaa21d80a45f024e88",
	],
	maven_coordinates = "io.ktor:ktor-websockets:3.2.3",
	maven_url = "https://repo1.maven.org/maven2/io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_ktor_ktor_websockets_3_2_3",
	actual = "io_ktor_ktor_websockets",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_ktor_ktor_websockets_3_2_3_extension",
     src = "@io_ktor_ktor_websockets_3_2_3//file",
     out = "io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_opencensus_opencensus_api",
	jar = "io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar",
	srcjar = "io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1-sources.jar",
	deps = [
		":io_grpc_grpc_context",
	],
	tags = [
		"maven_coordinates=io.opencensus:opencensus-api:0.31.1",
		"maven_url=https://repo1.maven.org/maven2/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f1474d47f4b6b001558ad27b952e35eda5cc7146788877fc52938c6eba24b382",
	],
	maven_coordinates = "io.opencensus:opencensus-api:0.31.1",
	maven_url = "https://repo1.maven.org/maven2/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_opencensus_opencensus_api_0_31_1",
	actual = "io_opencensus_opencensus_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_opencensus_opencensus_api_0_31_1_extension",
     src = "@io_opencensus_opencensus_api_0_31_1//file",
     out = "io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_opencensus_opencensus_contrib_http_util",
	jar = "io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar",
	srcjar = "io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1-sources.jar",
	deps = [
		":com_google_guava_guava",
		":io_opencensus_opencensus_api",
	],
	tags = [
		"maven_coordinates=io.opencensus:opencensus-contrib-http-util:0.31.1",
		"maven_url=https://repo1.maven.org/maven2/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=3ea995b55a4068be22989b70cc29a4d788c2d328d1d50613a7a9afd13fdd2d0a",
	],
	maven_coordinates = "io.opencensus:opencensus-contrib-http-util:0.31.1",
	maven_url = "https://repo1.maven.org/maven2/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_opencensus_opencensus_contrib_http_util_0_31_1",
	actual = "io_opencensus_opencensus_contrib_http_util",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_opencensus_opencensus_contrib_http_util_0_31_1_extension",
     src = "@io_opencensus_opencensus_contrib_http_util_0_31_1//file",
     out = "io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "jakarta_inject_jakarta_inject_api",
	jar = "jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar",
	srcjar = "jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=jakarta.inject:jakarta.inject-api:2.0.1",
		"maven_url=https://repo1.maven.org/maven2/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f7dc98062fccf14126abb751b64fab12c312566e8cbdc8483598bffcea93af7c",
	],
	maven_coordinates = "jakarta.inject:jakarta.inject-api:2.0.1",
	maven_url = "https://repo1.maven.org/maven2/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "jakarta_inject_jakarta_inject_api_2_0_1",
	actual = "jakarta_inject_jakarta_inject_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "jakarta_inject_jakarta_inject_api_2_0_1_extension",
     src = "@jakarta_inject_jakarta_inject_api_2_0_1//file",
     out = "jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "javax_annotation_jsr250_api",
	jar = "javax/annotation/jsr250-api/1.0/jsr250-api-1.0.jar",
	srcjar = "javax/annotation/jsr250-api/1.0/jsr250-api-1.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=javax.annotation:jsr250-api:1.0",
		"maven_url=https://repo1.maven.org/maven2/javax/annotation/jsr250-api/1.0/jsr250-api-1.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a1a922d0d9b6d183ed3800dfac01d1e1eb159f0e8c6f94736931c1def54a941f",
	],
	maven_coordinates = "javax.annotation:jsr250-api:1.0",
	maven_url = "https://repo1.maven.org/maven2/javax/annotation/jsr250-api/1.0/jsr250-api-1.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "javax_annotation_jsr250_api_1_0",
	actual = "javax_annotation_jsr250_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "javax_annotation_jsr250_api_1_0_extension",
     src = "@javax_annotation_jsr250_api_1_0//file",
     out = "javax/annotation/jsr250-api/1.0/jsr250-api-1.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "javax_inject_javax_inject",
	jar = "javax/inject/javax.inject/1/javax.inject-1.jar",
	srcjar = "javax/inject/javax.inject/1/javax.inject-1-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=javax.inject:javax.inject:1",
		"maven_url=https://repo1.maven.org/maven2/javax/inject/javax.inject/1/javax.inject-1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=91c77044a50c481636c32d916fd89c9118a72195390452c81065080f957de7ff",
	],
	maven_coordinates = "javax.inject:javax.inject:1",
	maven_url = "https://repo1.maven.org/maven2/javax/inject/javax.inject/1/javax.inject-1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "javax_inject_javax_inject_1",
	actual = "javax_inject_javax_inject",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "javax_inject_javax_inject_1_extension",
     src = "@javax_inject_javax_inject_1//file",
     out = "javax/inject/javax.inject/1/javax.inject-1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "junit_junit",
	jar = "junit/junit/4.13.2/junit-4.13.2.jar",
	srcjar = "junit/junit/4.13.2/junit-4.13.2-sources.jar",
	deps = [
		":org_hamcrest_hamcrest_core",
	],
	tags = [
		"maven_coordinates=junit:junit:4.13.2",
		"maven_url=https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8e495b634469d64fb8acfa3495a065cbacc8a0fff55ce1e31007be4c16dc57d3",
	],
	maven_coordinates = "junit:junit:4.13.2",
	maven_url = "https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "junit_junit_4_13_2",
	actual = "junit_junit",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "junit_junit_4_13_2_extension",
     src = "@junit_junit_4_13_2//file",
     out = "junit/junit/4.13.2/junit-4.13.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "net_bytebuddy_byte_buddy",
	jar = "net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5.jar",
	srcjar = "net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=net.bytebuddy:byte-buddy:1.17.5",
		"maven_url=https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=71568c9f8396677219f650268fbf6493ded484edcdbdf2dae6129ca5be81e8db",
	],
	maven_coordinates = "net.bytebuddy:byte-buddy:1.17.5",
	maven_url = "https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "net_bytebuddy_byte_buddy_1_17_5",
	actual = "net_bytebuddy_byte_buddy",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "net_bytebuddy_byte_buddy_1_17_5_extension",
     src = "@net_bytebuddy_byte_buddy_1_17_5//file",
     out = "net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "net_bytebuddy_byte_buddy_agent",
	jar = "net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5.jar",
	srcjar = "net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=net.bytebuddy:byte-buddy-agent:1.17.5",
		"maven_url=https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=c5b9334ad82e632f6af60df22bbbdbbb62cee04877f4f43c38ba04aed9bd9901",
	],
	maven_coordinates = "net.bytebuddy:byte-buddy-agent:1.17.5",
	maven_url = "https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "net_bytebuddy_byte_buddy_agent_1_17_5",
	actual = "net_bytebuddy_byte_buddy_agent",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "net_bytebuddy_byte_buddy_agent_1_17_5_extension",
     src = "@net_bytebuddy_byte_buddy_agent_1_17_5//file",
     out = "net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "net_java_dev_jna_jna",
	jar = "net/java/dev/jna/jna/5.10.0/jna-5.10.0.jar",
	srcjar = "net/java/dev/jna/jna/5.10.0/jna-5.10.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=net.java.dev.jna:jna:5.10.0",
		"maven_url=https://repo1.maven.org/maven2/net/java/dev/jna/jna/5.10.0/jna-5.10.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e335c10679f743207d822c5f7948e930319835492575a9dba6b94f8a3b96fcc8",
	],
	maven_coordinates = "net.java.dev.jna:jna:5.10.0",
	maven_url = "https://repo1.maven.org/maven2/net/java/dev/jna/jna/5.10.0/jna-5.10.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "net_java_dev_jna_jna_5_10_0",
	actual = "net_java_dev_jna_jna",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "net_java_dev_jna_jna_5_10_0_extension",
     src = "@net_java_dev_jna_jna_5_10_0//file",
     out = "net/java/dev/jna/jna/5.10.0/jna-5.10.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "net_java_jinput_jinput",
	jar = "net/java/jinput/jinput/2.0.9/jinput-2.0.9.jar",
	srcjar = "net/java/jinput/jinput/2.0.9/jinput-2.0.9-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=net.java.jinput:jinput:2.0.9",
		"maven_url=https://repo1.maven.org/maven2/net/java/jinput/jinput/2.0.9/jinput-2.0.9.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ff585bab64455a4b2bd2048821762c1a96be4d076a8e8729f9432194af288b4c",
	],
	maven_coordinates = "net.java.jinput:jinput:2.0.9",
	maven_url = "https://repo1.maven.org/maven2/net/java/jinput/jinput/2.0.9/jinput-2.0.9.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "net_java_jinput_jinput_2_0_9",
	actual = "net_java_jinput_jinput",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "net_java_jinput_jinput_2_0_9_extension",
     src = "@net_java_jinput_jinput_2_0_9//file",
     out = "net/java/jinput/jinput/2.0.9/jinput-2.0.9.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "net_java_jinput_jinput_natives_all",
	jar = "net/java/jinput/jinput/2.0.9/jinput-2.0.9-natives-all.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=net.java.jinput:jinput:2.0.9:natives-all",
		"maven_url=https://repo1.maven.org/maven2/net/java/jinput/jinput/2.0.9/jinput-2.0.9-natives-all.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a6c17ab8db8de19a1ca2c877516a3594d1fcecf2dc3427bda83794e17dd97b5e",
	],
	maven_coordinates = "net.java.jinput:jinput:2.0.9:natives-all",
	maven_url = "https://repo1.maven.org/maven2/net/java/jinput/jinput/2.0.9/jinput-2.0.9-natives-all.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "net_java_jinput_jinput_natives_all_2_0_9",
	actual = "net_java_jinput_jinput_natives_all",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "net_java_jinput_jinput_natives_all_2_0_9_extension",
     src = "@net_java_jinput_jinput_natives_all_2_0_9//file",
     out = "net/java/jinput/jinput/2.0.9/jinput-2.0.9-natives-all.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "net_ltgt_gradle_incap_incap",
	jar = "net/ltgt/gradle/incap/incap/0.2/incap-0.2.jar",
	srcjar = "net/ltgt/gradle/incap/incap/0.2/incap-0.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=net.ltgt.gradle.incap:incap:0.2",
		"maven_url=https://repo1.maven.org/maven2/net/ltgt/gradle/incap/incap/0.2/incap-0.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b625b9806b0f1e4bc7a2e3457119488de3cd57ea20feedd513db070a573a4ffd",
	],
	maven_coordinates = "net.ltgt.gradle.incap:incap:0.2",
	maven_url = "https://repo1.maven.org/maven2/net/ltgt/gradle/incap/incap/0.2/incap-0.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "net_ltgt_gradle_incap_incap_0_2",
	actual = "net_ltgt_gradle_incap_incap",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "net_ltgt_gradle_incap_incap_0_2_extension",
     src = "@net_ltgt_gradle_incap_incap_0_2//file",
     out = "net/ltgt/gradle/incap/incap/0.2/incap-0.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_httpcomponents_httpclient",
	jar = "org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar",
	srcjar = "org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14-sources.jar",
	deps = [
		":commons_codec_commons_codec",
		":org_apache_httpcomponents_httpcore",
	],
	tags = [
		"maven_coordinates=org.apache.httpcomponents:httpclient:4.5.14",
		"maven_url=https://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=c8bc7e1c51a6d4ce72f40d2ebbabf1c4b68bfe76e732104b04381b493478e9d6",
	],
	maven_coordinates = "org.apache.httpcomponents:httpclient:4.5.14",
	maven_url = "https://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_httpcomponents_httpclient_4_5_14",
	actual = "org_apache_httpcomponents_httpclient",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_httpcomponents_httpclient_4_5_14_extension",
     src = "@org_apache_httpcomponents_httpclient_4_5_14//file",
     out = "org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_httpcomponents_httpcore",
	jar = "org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar",
	srcjar = "org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.apache.httpcomponents:httpcore:4.4.16",
		"maven_url=https://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6c9b3dd142a09dc468e23ad39aad6f75a0f2b85125104469f026e52a474e464f",
	],
	maven_coordinates = "org.apache.httpcomponents:httpcore:4.4.16",
	maven_url = "https://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_httpcomponents_httpcore_4_4_16",
	actual = "org_apache_httpcomponents_httpcore",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_httpcomponents_httpcore_4_4_16_extension",
     src = "@org_apache_httpcomponents_httpcore_4_4_16//file",
     out = "org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apiguardian_apiguardian_api",
	jar = "org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar",
	srcjar = "org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.apiguardian:apiguardian-api:1.1.2",
		"maven_url=https://repo1.maven.org/maven2/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b509448ac506d607319f182537f0b35d71007582ec741832a1f111e5b5b70b38",
	],
	maven_coordinates = "org.apiguardian:apiguardian-api:1.1.2",
	maven_url = "https://repo1.maven.org/maven2/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apiguardian_apiguardian_api_1_1_2",
	actual = "org_apiguardian_apiguardian_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apiguardian_apiguardian_api_1_1_2_extension",
     src = "@org_apiguardian_apiguardian_api_1_1_2//file",
     out = "org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_checkerframework_checker_compat_qual",
	jar = "org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3.jar",
	srcjar = "org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.checkerframework:checker-compat-qual:2.5.3",
		"maven_url=https://repo1.maven.org/maven2/org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d76b9afea61c7c082908023f0cbc1427fab9abd2df915c8b8a3e7a509bccbc6d",
	],
	maven_coordinates = "org.checkerframework:checker-compat-qual:2.5.3",
	maven_url = "https://repo1.maven.org/maven2/org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_checkerframework_checker_compat_qual_2_5_3",
	actual = "org_checkerframework_checker_compat_qual",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_checkerframework_checker_compat_qual_2_5_3_extension",
     src = "@org_checkerframework_checker_compat_qual_2_5_3//file",
     out = "org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_hamcrest_hamcrest_core",
	jar = "org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
	srcjar = "org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.hamcrest:hamcrest-core:1.3",
		"maven_url=https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=66fdef91e9739348df7a096aa384a5685f4e875584cce89386a7a47251c4d8e9",
	],
	maven_coordinates = "org.hamcrest:hamcrest-core:1.3",
	maven_url = "https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_hamcrest_hamcrest_core_1_3",
	actual = "org_hamcrest_hamcrest_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_hamcrest_hamcrest_core_1_3_extension",
     src = "@org_hamcrest_hamcrest_core_1_3//file",
     out = "org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_metadata_jvm",
	jar = "org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21.jar",
	srcjar = "org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-metadata-jvm:2.1.21",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5da27249dd65a3218af2826a30804380a5c02c09f063ce7a0258736388783fc4",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-metadata-jvm:2.1.21",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_metadata_jvm_2_1_21",
	actual = "org_jetbrains_kotlin_kotlin_metadata_jvm",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_metadata_jvm_2_1_21_extension",
     src = "@org_jetbrains_kotlin_kotlin_metadata_jvm_2_1_21//file",
     out = "org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_reflect",
	jar = "org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.jar",
	srcjar = "org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-reflect:2.1.21",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=bcd75a36ca4ad8e06117214ed807f8dea2fe61a71e07f91ca14f4335024b8463",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-reflect:2.1.21",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_reflect_2_1_21",
	actual = "org_jetbrains_kotlin_kotlin_reflect",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_reflect_2_1_21_extension",
     src = "@org_jetbrains_kotlin_kotlin_reflect_2_1_21//file",
     out = "org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_stdlib",
	jar = "org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0.jar",
	srcjar = "org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0-sources.jar",
	deps = [
		":org_jetbrains_annotations",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-stdlib:2.2.0",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=65d12d85a3b865c160db9147851712a64b10dadd68b22eea22a95bf8a8670dca",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-stdlib:2.2.0",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_stdlib_2_2_0",
	actual = "org_jetbrains_kotlin_kotlin_stdlib",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_stdlib_2_2_0_extension",
     src = "@org_jetbrains_kotlin_kotlin_stdlib_2_2_0//file",
     out = "org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7",
	jar = "org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10.jar",
	srcjar = "org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2aedcdc6b69b33bdf5cc235bcea88e7cf6601146bb6bcdffdb312bbacd7be261",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7_1_6_10",
	actual = "org_jetbrains_kotlin_kotlin_stdlib_jdk7",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7_1_6_10_extension",
     src = "@org_jetbrains_kotlin_kotlin_stdlib_jdk7_1_6_10//file",
     out = "org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8",
	jar = "org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10.jar",
	srcjar = "org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlin_kotlin_stdlib_jdk7",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1456d82d039ea30d8485b032901f52bbf07e7cdbe8bb1f8708ad32a8574c41ce",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8_1_6_10",
	actual = "org_jetbrains_kotlin_kotlin_stdlib_jdk8",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8_1_6_10_extension",
     src = "@org_jetbrains_kotlin_kotlin_stdlib_jdk8_1_6_10//file",
     out = "org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_test",
	jar = "org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0.jar",
	srcjar = "org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-test:2.2.0",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8db175a3f56cf139ebdf8936f293ce5a64a16b52a0408804e0ec077e8848eb32",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-test:2.2.0",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_test_2_2_0",
	actual = "org_jetbrains_kotlin_kotlin_test",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_test_2_2_0_extension",
     src = "@org_jetbrains_kotlin_kotlin_test_2_2_0//file",
     out = "org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlinx_kotlinx_coroutines_core",
	jar = "org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2.jar",
	srcjar = "org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2-sources.jar",
	deps = [
		":org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=319b653009d49c70982f98df29cc84fc7025b092cb0571c8e7532e3ad4366dae",
	],
	maven_coordinates = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_1_10_2",
	actual = "org_jetbrains_kotlinx_kotlinx_coroutines_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_1_10_2_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_coroutines_core_1_10_2//file",
     out = "org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm",
	jar = "org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2.jar",
	srcjar = "org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_annotations",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.2",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5ca175b38df331fd64155b35cd8cae1251fa9ee369709b36d42e0a288ccce3fd",
	],
	maven_coordinates = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.2",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_1_10_2",
	actual = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_1_10_2_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_1_10_2//file",
     out = "org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlinx_kotlinx_io_bytestring",
	jar = "org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0.jar",
	srcjar = "org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlinx:kotlinx-io-bytestring:0.7.0",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d356352b0e555be126aadca9be39bb774931e32a5008edd060098dbb47d57e9e",
	],
	maven_coordinates = "org.jetbrains.kotlinx:kotlinx-io-bytestring:0.7.0",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlinx_kotlinx_io_bytestring_0_7_0",
	actual = "org_jetbrains_kotlinx_kotlinx_io_bytestring",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_io_bytestring_0_7_0_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_io_bytestring_0_7_0//file",
     out = "org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlinx_kotlinx_io_core",
	jar = "org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0.jar",
	srcjar = "org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlinx_kotlinx_io_bytestring",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlinx:kotlinx-io-core:0.7.0",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e102b088609e7a8bdbcc277a547e4684ec0447e40893d7285831fbebd1b27ee8",
	],
	maven_coordinates = "org.jetbrains.kotlinx:kotlinx-io-core:0.7.0",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlinx_kotlinx_io_core_0_7_0",
	actual = "org_jetbrains_kotlinx_kotlinx_io_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_io_core_0_7_0_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_io_core_0_7_0//file",
     out = "org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlinx_kotlinx_serialization_core",
	jar = "org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1.jar",
	srcjar = "org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1-sources.jar",
	deps = [
		":org_jetbrains_kotlinx_kotlinx_serialization_core_jvm",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d99859ff8cc49ae769f948af823f46947b3e5617fec2683fde951d4d0e0a7749",
	],
	maven_coordinates = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlinx_kotlinx_serialization_core_1_8_1",
	actual = "org_jetbrains_kotlinx_kotlinx_serialization_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_serialization_core_1_8_1_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_serialization_core_1_8_1//file",
     out = "org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_kotlinx_kotlinx_serialization_core_jvm",
	jar = "org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1.jar",
	srcjar = "org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1-sources.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.8.1",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=3565b6d4d789bf70683c45566944287fc1d8dc75c23d98bd87d01059cc76f2b3",
	],
	maven_coordinates = "org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.8.1",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlinx_kotlinx_serialization_core_jvm_1_8_1",
	actual = "org_jetbrains_kotlinx_kotlinx_serialization_core_jvm",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_serialization_core_jvm_1_8_1_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_serialization_core_jvm_1_8_1//file",
     out = "org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jetbrains_annotations",
	jar = "org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar",
	srcjar = "org/jetbrains/annotations/23.0.0/annotations-23.0.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.jetbrains:annotations:23.0.0",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=7b0f19724082cbfcbc66e5abea2b9bc92cf08a1ea11e191933ed43801eb3cd05",
	],
	maven_coordinates = "org.jetbrains:annotations:23.0.0",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_annotations_23_0_0",
	actual = "org_jetbrains_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_annotations_23_0_0_extension",
     src = "@org_jetbrains_annotations_23_0_0//file",
     out = "org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_bullet",
	jar = "org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable-sources.jar",
	deps = [
		":org_jmonkeyengine_jme3_core",
		":org_jmonkeyengine_jme3_terrain",
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-bullet:3.3.2-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=033a15df7da32a6dc86fd2c4228270b8ab59c544069fcbd75bf6bff18b7aed20",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-bullet:3.3.2-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_bullet_3_3_2_stable",
	actual = "org_jmonkeyengine_jme3_bullet",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_bullet_3_3_2_stable_extension",
     src = "@org_jmonkeyengine_jme3_bullet_3_3_2_stable//file",
     out = "org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_bullet_native",
	jar = "org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable-sources.jar",
	deps = [
		":org_jmonkeyengine_jme3_bullet",
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-bullet-native:3.3.2-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=cbe8173714b2178ae7dfe35b85adbe867e021df7021df4efcebd6eada199fdeb",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-bullet-native:3.3.2-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_bullet_native_3_3_2_stable",
	actual = "org_jmonkeyengine_jme3_bullet_native",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_bullet_native_3_3_2_stable_extension",
     src = "@org_jmonkeyengine_jme3_bullet_native_3_3_2_stable//file",
     out = "org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_core",
	jar = "org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-core:3.7.0-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=0853ee825d29bcf2874d471ab1ad0581518bcd3d32639910cbf9abd607f25339",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-core:3.7.0-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_core_3_7_0_stable",
	actual = "org_jmonkeyengine_jme3_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_core_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_core_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_desktop",
	jar = "org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable-sources.jar",
	deps = [
		":org_jmonkeyengine_jme3_core",
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-desktop:3.7.0-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=9e2db567af2c310616e8952490822ed9c900e799466fbc4f811ff5fc967da88b",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-desktop:3.7.0-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_desktop_3_7_0_stable",
	actual = "org_jmonkeyengine_jme3_desktop",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_desktop_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_desktop_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_effects",
	jar = "org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable-sources.jar",
	deps = [
		":org_jmonkeyengine_jme3_core",
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-effects:3.7.0-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=420cfaf5b59677abc04e9907a9db80d99ed666ce301bb9e13aeb956735680490",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-effects:3.7.0-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_effects_3_7_0_stable",
	actual = "org_jmonkeyengine_jme3_effects",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_effects_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_effects_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_lwjgl",
	jar = "org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable-sources.jar",
	deps = [
		":net_java_jinput_jinput",
		":net_java_jinput_jinput_natives_all",
		":org_jmonkeyengine_jme3_core",
		":org_jmonkeyengine_jme3_desktop",
		":org_jmonkeyengine_lwjgl",
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-lwjgl:3.7.0-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=de5caa020e17d14d58729a446bf538a80976e270bdc418ecec1ef14d73d8a59c",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-lwjgl:3.7.0-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_lwjgl_3_7_0_stable",
	actual = "org_jmonkeyengine_jme3_lwjgl",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_lwjgl_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_lwjgl_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_lwjgl3",
	jar = "org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable-sources.jar",
	deps = [
		":org_jmonkeyengine_jme3_core",
		":org_jmonkeyengine_jme3_desktop",
		":org_lwjgl_lwjgl",
		":org_lwjgl_lwjgl_glfw",
		":org_lwjgl_lwjgl_glfw_natives_linux",
		":org_lwjgl_lwjgl_glfw_natives_linux_arm32",
		":org_lwjgl_lwjgl_glfw_natives_linux_arm64",
		":org_lwjgl_lwjgl_glfw_natives_macos",
		":org_lwjgl_lwjgl_glfw_natives_macos_arm64",
		":org_lwjgl_lwjgl_glfw_natives_windows",
		":org_lwjgl_lwjgl_glfw_natives_windows_x86",
		":org_lwjgl_lwjgl_jawt",
		":org_lwjgl_lwjgl_jemalloc",
		":org_lwjgl_lwjgl_jemalloc_natives_linux",
		":org_lwjgl_lwjgl_jemalloc_natives_linux_arm32",
		":org_lwjgl_lwjgl_jemalloc_natives_linux_arm64",
		":org_lwjgl_lwjgl_jemalloc_natives_macos",
		":org_lwjgl_lwjgl_jemalloc_natives_macos_arm64",
		":org_lwjgl_lwjgl_jemalloc_natives_windows",
		":org_lwjgl_lwjgl_jemalloc_natives_windows_x86",
		":org_lwjgl_lwjgl_openal",
		":org_lwjgl_lwjgl_openal_natives_linux",
		":org_lwjgl_lwjgl_openal_natives_linux_arm32",
		":org_lwjgl_lwjgl_openal_natives_linux_arm64",
		":org_lwjgl_lwjgl_openal_natives_macos",
		":org_lwjgl_lwjgl_openal_natives_macos_arm64",
		":org_lwjgl_lwjgl_openal_natives_windows",
		":org_lwjgl_lwjgl_openal_natives_windows_x86",
		":org_lwjgl_lwjgl_opencl",
		":org_lwjgl_lwjgl_opengl",
		":org_lwjgl_lwjgl_opengl_natives_linux",
		":org_lwjgl_lwjgl_opengl_natives_linux_arm32",
		":org_lwjgl_lwjgl_opengl_natives_linux_arm64",
		":org_lwjgl_lwjgl_opengl_natives_macos",
		":org_lwjgl_lwjgl_opengl_natives_macos_arm64",
		":org_lwjgl_lwjgl_opengl_natives_windows",
		":org_lwjgl_lwjgl_opengl_natives_windows_x86",
		":org_lwjgl_lwjgl_natives_linux",
		":org_lwjgl_lwjgl_natives_linux_arm32",
		":org_lwjgl_lwjgl_natives_linux_arm64",
		":org_lwjgl_lwjgl_natives_macos",
		":org_lwjgl_lwjgl_natives_macos_arm64",
		":org_lwjgl_lwjgl_natives_windows",
		":org_lwjgl_lwjgl_natives_windows_x86",
		":org_lwjglx_lwjgl3_awt",
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-lwjgl3:3.7.0-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=53f9a674891d442f788d0b54625ffe5e64809b34f5025a03a48613a8a3743a48",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-lwjgl3:3.7.0-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_lwjgl3_3_7_0_stable",
	actual = "org_jmonkeyengine_jme3_lwjgl3",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_lwjgl3_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_lwjgl3_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_terrain",
	jar = "org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable-sources.jar",
	deps = [
		":org_jmonkeyengine_jme3_core",
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-terrain:3.3.2-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=3dfa6cf8d659e2362e0df3bc5443361655a3b35db54f8f3b3ca6e782bc524a43",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-terrain:3.3.2-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_terrain_3_3_2_stable",
	actual = "org_jmonkeyengine_jme3_terrain",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_terrain_3_3_2_stable_extension",
     src = "@org_jmonkeyengine_jme3_terrain_3_3_2_stable//file",
     out = "org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_testdata",
	jar = "org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-testdata:3.7.0-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8d6070f47f0e1fdc8b84b9a8a3ccb5056db790be6a2f5e52a123f2dd466439f2",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-testdata:3.7.0-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_testdata_3_7_0_stable",
	actual = "org_jmonkeyengine_jme3_testdata",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_testdata_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_testdata_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_jme3_vr",
	jar = "org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable.jar",
	srcjar = "org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable-sources.jar",
	deps = [
		":com_nativelibs4java_jnaerator_runtime",
		":net_java_dev_jna_jna",
		":org_jmonkeyengine_jme3_core",
		":org_jmonkeyengine_jme3_desktop",
		":org_jmonkeyengine_jme3_effects",
		":org_jmonkeyengine_jme3_lwjgl3",
		":org_lwjgl_lwjgl_openvr",
		":org_lwjgl_lwjgl_openvr_natives_linux",
		":org_lwjgl_lwjgl_openvr_natives_macos",
		":org_lwjgl_lwjgl_openvr_natives_windows",
		":org_lwjgl_lwjgl_ovr",
		":org_lwjgl_lwjgl_ovr_natives_windows",
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:jme3-vr:3.7.0-stable",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=93decbe6604aa2e038a6df2c1503658c0ff30cbd6da2d47367fa0e96ee0837e6",
	],
	maven_coordinates = "org.jmonkeyengine:jme3-vr:3.7.0-stable",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_jme3_vr_3_7_0_stable",
	actual = "org_jmonkeyengine_jme3_vr",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_jme3_vr_3_7_0_stable_extension",
     src = "@org_jmonkeyengine_jme3_vr_3_7_0_stable//file",
     out = "org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_lwjgl",
	jar = "org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5.jar",
	srcjar = "org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5-sources.jar",
	deps = [
		":net_java_jinput_jinput",
		":org_jmonkeyengine_lwjgl_platform_natives_linux",
		":org_jmonkeyengine_lwjgl_platform_natives_osx",
		":org_jmonkeyengine_lwjgl_platform_natives_windows",
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:lwjgl:2.9.5",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a2dcbd278b870e8d66eda4022726e3b7364385a1056b5767e25f4739ca5e2362",
	],
	maven_coordinates = "org.jmonkeyengine:lwjgl:2.9.5",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_lwjgl_2_9_5",
	actual = "org_jmonkeyengine_lwjgl",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_lwjgl_2_9_5_extension",
     src = "@org_jmonkeyengine_lwjgl_2_9_5//file",
     out = "org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_lwjgl_platform_natives_linux",
	jar = "org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-linux.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:lwjgl-platform:2.9.5:natives-linux",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-linux.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=752d66130c5e419798ae26d4c3ace622b6d327fc4558207b5dbcff6176f8f8c7",
	],
	maven_coordinates = "org.jmonkeyengine:lwjgl-platform:2.9.5:natives-linux",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-linux.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_lwjgl_platform_natives_linux_2_9_5",
	actual = "org_jmonkeyengine_lwjgl_platform_natives_linux",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_lwjgl_platform_natives_linux_2_9_5_extension",
     src = "@org_jmonkeyengine_lwjgl_platform_natives_linux_2_9_5//file",
     out = "org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-linux.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_lwjgl_platform_natives_osx",
	jar = "org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-osx.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:lwjgl-platform:2.9.5:natives-osx",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-osx.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=eb517ba5d4c819d314534b674239eacda8e1d9da78286b6410b470a13fb83d3c",
	],
	maven_coordinates = "org.jmonkeyengine:lwjgl-platform:2.9.5:natives-osx",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-osx.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_lwjgl_platform_natives_osx_2_9_5",
	actual = "org_jmonkeyengine_lwjgl_platform_natives_osx",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_lwjgl_platform_natives_osx_2_9_5_extension",
     src = "@org_jmonkeyengine_lwjgl_platform_natives_osx_2_9_5//file",
     out = "org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-osx.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jmonkeyengine_lwjgl_platform_natives_windows",
	jar = "org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-windows.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.jmonkeyengine:lwjgl-platform:2.9.5:natives-windows",
		"maven_url=https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-windows.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2814d395b2ef92b69d7d829d1559a5bc74ad5ecc6cc4d1d79d24efc540cea838",
	],
	maven_coordinates = "org.jmonkeyengine:lwjgl-platform:2.9.5:natives-windows",
	maven_url = "https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-windows.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jmonkeyengine_lwjgl_platform_natives_windows_2_9_5",
	actual = "org_jmonkeyengine_lwjgl_platform_natives_windows",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jmonkeyengine_lwjgl_platform_natives_windows_2_9_5_extension",
     src = "@org_jmonkeyengine_lwjgl_platform_natives_windows_2_9_5//file",
     out = "org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-windows.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_jspecify_jspecify",
	jar = "org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar",
	srcjar = "org/jspecify/jspecify/1.0.0/jspecify-1.0.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.jspecify:jspecify:1.0.0",
		"maven_url=https://repo1.maven.org/maven2/org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1fad6e6be7557781e4d33729d49ae1cdc8fdda6fe477bb0cc68ce351eafdfbab",
	],
	maven_coordinates = "org.jspecify:jspecify:1.0.0",
	maven_url = "https://repo1.maven.org/maven2/org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jspecify_jspecify_1_0_0",
	actual = "org_jspecify_jspecify",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jspecify_jspecify_1_0_0_extension",
     src = "@org_jspecify_jspecify_1_0_0//file",
     out = "org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_junit_jupiter_junit_jupiter_api",
	jar = "org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4.jar",
	srcjar = "org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4-sources.jar",
	deps = [
		":org_apiguardian_apiguardian_api",
		":org_junit_platform_junit_platform_commons",
		":org_opentest4j_opentest4j",
	],
	tags = [
		"maven_coordinates=org.junit.jupiter:junit-jupiter-api:5.13.4",
		"maven_url=https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d1bb81abfd9e03418306b4e6a3390c8db52c58372e749c2980ac29f0c08278f1",
	],
	maven_coordinates = "org.junit.jupiter:junit-jupiter-api:5.13.4",
	maven_url = "https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_junit_jupiter_junit_jupiter_api_5_13_4",
	actual = "org_junit_jupiter_junit_jupiter_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_junit_jupiter_junit_jupiter_api_5_13_4_extension",
     src = "@org_junit_jupiter_junit_jupiter_api_5_13_4//file",
     out = "org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_junit_jupiter_junit_jupiter_engine",
	jar = "org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4.jar",
	srcjar = "org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4-sources.jar",
	deps = [
		":org_apiguardian_apiguardian_api",
		":org_junit_jupiter_junit_jupiter_api",
		":org_junit_platform_junit_platform_engine",
	],
	tags = [
		"maven_coordinates=org.junit.jupiter:junit-jupiter-engine:5.13.4",
		"maven_url=https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=027404a92fe618b72465792a257951495c503a7d5751e2791e0f51c87f67f5bc",
	],
	maven_coordinates = "org.junit.jupiter:junit-jupiter-engine:5.13.4",
	maven_url = "https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_junit_jupiter_junit_jupiter_engine_5_13_4",
	actual = "org_junit_jupiter_junit_jupiter_engine",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_junit_jupiter_junit_jupiter_engine_5_13_4_extension",
     src = "@org_junit_jupiter_junit_jupiter_engine_5_13_4//file",
     out = "org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_junit_platform_junit_platform_commons",
	jar = "org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4.jar",
	srcjar = "org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4-sources.jar",
	deps = [
		":org_apiguardian_apiguardian_api",
	],
	tags = [
		"maven_coordinates=org.junit.platform:junit-platform-commons:1.13.4",
		"maven_url=https://repo1.maven.org/maven2/org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1c25ca641ebaae44ff3ad21ca1b2ef68d0dd84bfeb07c4805ba7840899b77408",
	],
	maven_coordinates = "org.junit.platform:junit-platform-commons:1.13.4",
	maven_url = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_junit_platform_junit_platform_commons_1_13_4",
	actual = "org_junit_platform_junit_platform_commons",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_junit_platform_junit_platform_commons_1_13_4_extension",
     src = "@org_junit_platform_junit_platform_commons_1_13_4//file",
     out = "org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_junit_platform_junit_platform_engine",
	jar = "org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4.jar",
	srcjar = "org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4-sources.jar",
	deps = [
		":org_apiguardian_apiguardian_api",
		":org_junit_platform_junit_platform_commons",
		":org_opentest4j_opentest4j",
	],
	tags = [
		"maven_coordinates=org.junit.platform:junit-platform-engine:1.13.4",
		"maven_url=https://repo1.maven.org/maven2/org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=390c5f77b84283a64b644f88251b397e0b0debb80bdcc50f899881aecff43a5a",
	],
	maven_coordinates = "org.junit.platform:junit-platform-engine:1.13.4",
	maven_url = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_junit_platform_junit_platform_engine_1_13_4",
	actual = "org_junit_platform_junit_platform_engine",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_junit_platform_junit_platform_engine_1_13_4_extension",
     src = "@org_junit_platform_junit_platform_engine_1_13_4//file",
     out = "org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl",
	jar = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b00e2781b74cc829db9d39fb68746b25bb7b94ce61d46293457dbccddabd999c",
	],
	maven_coordinates = "org.lwjgl:lwjgl:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_3_3_6",
	actual = "org_lwjgl_lwjgl",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_3_3_6//file",
     out = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_natives_linux",
	jar = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl:3.3.6:natives-linux",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2f0e65d6985d602c0e5e5aab6576db113a9a378a36f8144ec93b77a4fde5876c",
	],
	maven_coordinates = "org.lwjgl:lwjgl:3.3.6:natives-linux",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_natives_linux_3_3_6",
	actual = "org_lwjgl_lwjgl_natives_linux",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_natives_linux_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_natives_linux_3_3_6//file",
     out = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_natives_linux_arm32",
	jar = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm32.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl:3.3.6:natives-linux-arm32",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm32.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d54a952639f7a51714609415605e252e0c6be528601a11b60b5f9e9ff19f05de",
	],
	maven_coordinates = "org.lwjgl:lwjgl:3.3.6:natives-linux-arm32",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm32.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_natives_linux_arm32_3_3_6",
	actual = "org_lwjgl_lwjgl_natives_linux_arm32",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_natives_linux_arm32_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_natives_linux_arm32_3_3_6//file",
     out = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm32.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_natives_linux_arm64",
	jar = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm64.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl:3.3.6:natives-linux-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=07643ee5e95635b710715b41900c2a05c3f08c74be9309ba0763e31431bfad3b",
	],
	maven_coordinates = "org.lwjgl:lwjgl:3.3.6:natives-linux-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_natives_linux_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_natives_linux_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_natives_linux_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_natives_linux_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_natives_macos",
	jar = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl:3.3.6:natives-macos",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a818cba530f8a541ef30e2cc2b731c8de2d081d25e9901959ff2bcdbf2344e0f",
	],
	maven_coordinates = "org.lwjgl:lwjgl:3.3.6:natives-macos",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_natives_macos_3_3_6",
	actual = "org_lwjgl_lwjgl_natives_macos",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_natives_macos_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_natives_macos_3_3_6//file",
     out = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_natives_macos_arm64",
	jar = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos-arm64.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl:3.3.6:natives-macos-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e68652629a28f0262ca99a2132d370e3fbc70d9f10e11717afa3f2dbe85b64fc",
	],
	maven_coordinates = "org.lwjgl:lwjgl:3.3.6:natives-macos-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_natives_macos_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_natives_macos_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_natives_macos_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_natives_macos_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_natives_windows",
	jar = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl:3.3.6:natives-windows",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a8d8edda34718bf70f68d14de1295b5bfc0f477a9607a3a9705d9e2d88538a8c",
	],
	maven_coordinates = "org.lwjgl:lwjgl:3.3.6:natives-windows",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_natives_windows_3_3_6",
	actual = "org_lwjgl_lwjgl_natives_windows",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_natives_windows_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_natives_windows_3_3_6//file",
     out = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_natives_windows_x86",
	jar = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows-x86.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl:3.3.6:natives-windows-x86",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows-x86.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=55ba30f2b07913ba59a8aefaa004b3c1e0aebe46655b91ef095f89ebc05bf37a",
	],
	maven_coordinates = "org.lwjgl:lwjgl:3.3.6:natives-windows-x86",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows-x86.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_natives_windows_x86_3_3_6",
	actual = "org_lwjgl_lwjgl_natives_windows_x86",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_natives_windows_x86_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_natives_windows_x86_3_3_6//file",
     out = "org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows-x86.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_assimp",
	jar = "org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-assimp:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2cd29a9422bbc1bec11d49a18c7b289828eea939b66da2573d203a5a69ee8172",
	],
	maven_coordinates = "org.lwjgl:lwjgl-assimp:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_assimp_3_3_6",
	actual = "org_lwjgl_lwjgl_assimp",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_assimp_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_assimp_3_3_6//file",
     out = "org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_bgfx",
	jar = "org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-bgfx:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b6ed63de154cef8675d378dbeacaf22168e92ad5025868f6195f8f0a05d9f180",
	],
	maven_coordinates = "org.lwjgl:lwjgl-bgfx:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_bgfx_3_3_6",
	actual = "org_lwjgl_lwjgl_bgfx",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_bgfx_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_bgfx_3_3_6//file",
     out = "org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_cuda",
	jar = "org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-cuda:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b7b2abdf1cde0a5ec8c415461d2ed03f43ba7ca422da32947ccd2189390338a5",
	],
	maven_coordinates = "org.lwjgl:lwjgl-cuda:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_cuda_3_3_6",
	actual = "org_lwjgl_lwjgl_cuda",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_cuda_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_cuda_3_3_6//file",
     out = "org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_egl",
	jar = "org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-egl:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=49671f4458be2edda2928c3ff4b08c9ffda20a11097ef5c1acc65c5341874769",
	],
	maven_coordinates = "org.lwjgl:lwjgl-egl:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_egl_3_3_6",
	actual = "org_lwjgl_lwjgl_egl",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_egl_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_egl_3_3_6//file",
     out = "org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_glfw",
	jar = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-glfw:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b29c938ecc4997ce256a831aeca2033dab9829b5c21e72ebeb64aecd9e08450c",
	],
	maven_coordinates = "org.lwjgl:lwjgl-glfw:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_glfw_3_3_6",
	actual = "org_lwjgl_lwjgl_glfw",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_glfw_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_glfw_3_3_6//file",
     out = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_glfw_natives_linux",
	jar = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-glfw:3.3.6:natives-linux",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a1b60014597bc0e45bf39089f4d838c3aa87fd668f6fe4e7326aa314d2ec87c0",
	],
	maven_coordinates = "org.lwjgl:lwjgl-glfw:3.3.6:natives-linux",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_glfw_natives_linux_3_3_6",
	actual = "org_lwjgl_lwjgl_glfw_natives_linux",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_glfw_natives_linux_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_glfw_natives_linux_3_3_6//file",
     out = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_glfw_natives_linux_arm32",
	jar = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm32.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-glfw:3.3.6:natives-linux-arm32",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm32.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=75fd5d4225591ae6b0416e1b6fcefb2d85f1288bfabb15890bb4254e7be943e3",
	],
	maven_coordinates = "org.lwjgl:lwjgl-glfw:3.3.6:natives-linux-arm32",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm32.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_glfw_natives_linux_arm32_3_3_6",
	actual = "org_lwjgl_lwjgl_glfw_natives_linux_arm32",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_glfw_natives_linux_arm32_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_glfw_natives_linux_arm32_3_3_6//file",
     out = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm32.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_glfw_natives_linux_arm64",
	jar = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm64.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-glfw:3.3.6:natives-linux-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6a108ac764f88e0fc5b3c7de21227e7db123b1b27be531a93afb5954db4efa3f",
	],
	maven_coordinates = "org.lwjgl:lwjgl-glfw:3.3.6:natives-linux-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_glfw_natives_linux_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_glfw_natives_linux_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_glfw_natives_linux_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_glfw_natives_linux_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_glfw_natives_macos",
	jar = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-glfw:3.3.6:natives-macos",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=826f9da50850d3e7e3b2002897b672cbd999d6d8a174ceea1d6e874d148c4bc1",
	],
	maven_coordinates = "org.lwjgl:lwjgl-glfw:3.3.6:natives-macos",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_glfw_natives_macos_3_3_6",
	actual = "org_lwjgl_lwjgl_glfw_natives_macos",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_glfw_natives_macos_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_glfw_natives_macos_3_3_6//file",
     out = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_glfw_natives_macos_arm64",
	jar = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos-arm64.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-glfw:3.3.6:natives-macos-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b46d40f15c2534e3800c5c44f73a4c7ded8a73b2272512c47847c1004ef7ffa9",
	],
	maven_coordinates = "org.lwjgl:lwjgl-glfw:3.3.6:natives-macos-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_glfw_natives_macos_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_glfw_natives_macos_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_glfw_natives_macos_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_glfw_natives_macos_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_glfw_natives_windows",
	jar = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-glfw:3.3.6:natives-windows",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=7492d3f62a868f857173d85360bb58716cd3fe8563da18419dde858aed2deb41",
	],
	maven_coordinates = "org.lwjgl:lwjgl-glfw:3.3.6:natives-windows",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_glfw_natives_windows_3_3_6",
	actual = "org_lwjgl_lwjgl_glfw_natives_windows",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_glfw_natives_windows_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_glfw_natives_windows_3_3_6//file",
     out = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_glfw_natives_windows_x86",
	jar = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows-x86.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-glfw:3.3.6:natives-windows-x86",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows-x86.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=edb2470645c6a557b1604d8b22ff675f1ceecf653c85a3db4b531d309da1582d",
	],
	maven_coordinates = "org.lwjgl:lwjgl-glfw:3.3.6:natives-windows-x86",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows-x86.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_glfw_natives_windows_x86_3_3_6",
	actual = "org_lwjgl_lwjgl_glfw_natives_windows_x86",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_glfw_natives_windows_x86_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_glfw_natives_windows_x86_3_3_6//file",
     out = "org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows-x86.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_jawt",
	jar = "org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-jawt:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2093ee9a645689d43efd8c4f58ddd424f92a242813d93714642a3a2b45fdbf3e",
	],
	maven_coordinates = "org.lwjgl:lwjgl-jawt:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_jawt_3_3_6",
	actual = "org_lwjgl_lwjgl_jawt",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_jawt_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jawt_3_3_6//file",
     out = "org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_jemalloc",
	jar = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-jemalloc:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=194656f652d78b11c86e86757679e975acb94397a86f256c2225931c46d7ae71",
	],
	maven_coordinates = "org.lwjgl:lwjgl-jemalloc:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_jemalloc_3_3_6",
	actual = "org_lwjgl_lwjgl_jemalloc",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_jemalloc_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jemalloc_3_3_6//file",
     out = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_jemalloc_natives_linux",
	jar = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-jemalloc:3.3.6:natives-linux",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5e5866a925abe5c0409f086ba114419202e408f7f8ad0a5a99b40b755c65b46c",
	],
	maven_coordinates = "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-linux",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_jemalloc_natives_linux_3_3_6",
	actual = "org_lwjgl_lwjgl_jemalloc_natives_linux",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_jemalloc_natives_linux_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jemalloc_natives_linux_3_3_6//file",
     out = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm32",
	jar = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm32.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-jemalloc:3.3.6:natives-linux-arm32",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm32.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=11ec1ae93b7c7027e8feac902308ef40e65d7e3c4bf0aa91000b30f426d8990f",
	],
	maven_coordinates = "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-linux-arm32",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm32.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm32_3_3_6",
	actual = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm32",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm32_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jemalloc_natives_linux_arm32_3_3_6//file",
     out = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm32.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm64",
	jar = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm64.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-jemalloc:3.3.6:natives-linux-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=565fc2185052f9074b5e37bd31806d955e99e649351dfbe8c18bb8a372f0cfea",
	],
	maven_coordinates = "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-linux-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jemalloc_natives_linux_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_jemalloc_natives_macos",
	jar = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-jemalloc:3.3.6:natives-macos",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=81a78fb0ce8079da46e40e2c3ec98f32f54f2acb053f536a448a5bf594266445",
	],
	maven_coordinates = "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-macos",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_jemalloc_natives_macos_3_3_6",
	actual = "org_lwjgl_lwjgl_jemalloc_natives_macos",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_jemalloc_natives_macos_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jemalloc_natives_macos_3_3_6//file",
     out = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_jemalloc_natives_macos_arm64",
	jar = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos-arm64.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-jemalloc:3.3.6:natives-macos-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ac8b95a42c37712049d0939249d913a19f6ebcab9d890809919e02f82194785d",
	],
	maven_coordinates = "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-macos-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_jemalloc_natives_macos_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_jemalloc_natives_macos_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_jemalloc_natives_macos_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jemalloc_natives_macos_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_jemalloc_natives_windows",
	jar = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-jemalloc:3.3.6:natives-windows",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=58371e1a9ef74ba94f559a18dc0c88f95e5e04c57f2b31850614e4e0a7a98c33",
	],
	maven_coordinates = "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-windows",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_jemalloc_natives_windows_3_3_6",
	actual = "org_lwjgl_lwjgl_jemalloc_natives_windows",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_jemalloc_natives_windows_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jemalloc_natives_windows_3_3_6//file",
     out = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_jemalloc_natives_windows_x86",
	jar = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows-x86.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-jemalloc:3.3.6:natives-windows-x86",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows-x86.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=91c715ae43af5c5a7cebeff5e4f708481723c8405fa4506bfb54966b3a91d61e",
	],
	maven_coordinates = "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-windows-x86",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows-x86.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_jemalloc_natives_windows_x86_3_3_6",
	actual = "org_lwjgl_lwjgl_jemalloc_natives_windows_x86",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_jemalloc_natives_windows_x86_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_jemalloc_natives_windows_x86_3_3_6//file",
     out = "org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows-x86.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_libdivide",
	jar = "org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-libdivide:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=9a2c9dea5fda79f55642b5734ee673c9d1ad62320a429fafc5dbec7980605c76",
	],
	maven_coordinates = "org.lwjgl:lwjgl-libdivide:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_libdivide_3_3_6",
	actual = "org_lwjgl_lwjgl_libdivide",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_libdivide_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_libdivide_3_3_6//file",
     out = "org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_llvm",
	jar = "org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-llvm:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=9e9276c648aedec0569314b932b41ca0ff2c39b44dc9f5b76af825d2f0ddeceb",
	],
	maven_coordinates = "org.lwjgl:lwjgl-llvm:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_llvm_3_3_6",
	actual = "org_lwjgl_lwjgl_llvm",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_llvm_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_llvm_3_3_6//file",
     out = "org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_lmdb",
	jar = "org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-lmdb:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e8cd76aaaaa2dba28e0080888dda59c4e832f37a6a6372e7ad979a7203ae58db",
	],
	maven_coordinates = "org.lwjgl:lwjgl-lmdb:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_lmdb_3_3_6",
	actual = "org_lwjgl_lwjgl_lmdb",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_lmdb_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_lmdb_3_3_6//file",
     out = "org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_lz4",
	jar = "org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-lz4:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=350dbeb6a0f07540018743653e283ee34e112b56934b67f5ca28e18136a87ef2",
	],
	maven_coordinates = "org.lwjgl:lwjgl-lz4:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_lz4_3_3_6",
	actual = "org_lwjgl_lwjgl_lz4",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_lz4_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_lz4_3_3_6//file",
     out = "org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_meow",
	jar = "org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-meow:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=92fb5f4b4296441054c087d0c4b24cda644d1365892c6499579d5ea9d9c8320a",
	],
	maven_coordinates = "org.lwjgl:lwjgl-meow:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_meow_3_3_6",
	actual = "org_lwjgl_lwjgl_meow",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_meow_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_meow_3_3_6//file",
     out = "org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_meshoptimizer",
	jar = "org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-meshoptimizer:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d2350ed1cd7283b6f509f8f6326e8a54b49c9296706a7e934015cd52f0013866",
	],
	maven_coordinates = "org.lwjgl:lwjgl-meshoptimizer:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_meshoptimizer_3_3_6",
	actual = "org_lwjgl_lwjgl_meshoptimizer",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_meshoptimizer_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_meshoptimizer_3_3_6//file",
     out = "org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_nanovg",
	jar = "org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-nanovg:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=fad128cab80819180fb3bcf8aa487f00ff4d81b8043e5548be2ef9327882b23e",
	],
	maven_coordinates = "org.lwjgl:lwjgl-nanovg:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_nanovg_3_3_6",
	actual = "org_lwjgl_lwjgl_nanovg",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_nanovg_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_nanovg_3_3_6//file",
     out = "org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_nfd",
	jar = "org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-nfd:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d43a10930f7980913d21e77d40318ecab84248dce63f5a4994a2a72a258c2af0",
	],
	maven_coordinates = "org.lwjgl:lwjgl-nfd:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_nfd_3_3_6",
	actual = "org_lwjgl_lwjgl_nfd",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_nfd_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_nfd_3_3_6//file",
     out = "org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_nuklear",
	jar = "org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-nuklear:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ff6a15dd924df6922227072bfeb24c074de460f2384d5ce81c91d0b9af141d1d",
	],
	maven_coordinates = "org.lwjgl:lwjgl-nuklear:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_nuklear_3_3_6",
	actual = "org_lwjgl_lwjgl_nuklear",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_nuklear_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_nuklear_3_3_6//file",
     out = "org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_odbc",
	jar = "org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-odbc:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=7cddd44c2b19b902001925c6a1f6e194406b874360364f11d7db0c9a11d599b7",
	],
	maven_coordinates = "org.lwjgl:lwjgl-odbc:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_odbc_3_3_6",
	actual = "org_lwjgl_lwjgl_odbc",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_odbc_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_odbc_3_3_6//file",
     out = "org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openal",
	jar = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openal:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5d8669ee52325080f500cf0060785a13135a10c602286cefc8ba285805fa8468",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openal:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openal_3_3_6",
	actual = "org_lwjgl_lwjgl_openal",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openal_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openal_3_3_6//file",
     out = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openal_natives_linux",
	jar = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openal:3.3.6:natives-linux",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d30c407c1182f507c70ad12e21bac98189c767bf5c7a592dbfaa4f11d139545c",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openal:3.3.6:natives-linux",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openal_natives_linux_3_3_6",
	actual = "org_lwjgl_lwjgl_openal_natives_linux",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openal_natives_linux_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openal_natives_linux_3_3_6//file",
     out = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openal_natives_linux_arm32",
	jar = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm32.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openal:3.3.6:natives-linux-arm32",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm32.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=bd07f32fba66a04ae35e6834d3050a063058d0bbc5bd3c3197cd39b60cf871b2",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openal:3.3.6:natives-linux-arm32",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm32.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openal_natives_linux_arm32_3_3_6",
	actual = "org_lwjgl_lwjgl_openal_natives_linux_arm32",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openal_natives_linux_arm32_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openal_natives_linux_arm32_3_3_6//file",
     out = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm32.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openal_natives_linux_arm64",
	jar = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm64.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openal:3.3.6:natives-linux-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=9cb8341ecd32b77a550f64afbb67f17464d1e61e252c44b3bbb521888176dca1",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openal:3.3.6:natives-linux-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openal_natives_linux_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_openal_natives_linux_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openal_natives_linux_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openal_natives_linux_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openal_natives_macos",
	jar = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openal:3.3.6:natives-macos",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e554a8c86f5b0c84d7136a8b1d050d6d76f228e7c1881e9f64de366f8b34258c",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openal:3.3.6:natives-macos",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openal_natives_macos_3_3_6",
	actual = "org_lwjgl_lwjgl_openal_natives_macos",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openal_natives_macos_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openal_natives_macos_3_3_6//file",
     out = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openal_natives_macos_arm64",
	jar = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos-arm64.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openal:3.3.6:natives-macos-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=99dbb6f65161cf6c966b5d064dd19285b6b0de202c8cd386bbb1115a5f84de22",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openal:3.3.6:natives-macos-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openal_natives_macos_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_openal_natives_macos_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openal_natives_macos_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openal_natives_macos_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openal_natives_windows",
	jar = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openal:3.3.6:natives-windows",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8ebb63e8014872709671161a896a720e2f09d038b0d3a33692218f7746504b35",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openal:3.3.6:natives-windows",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openal_natives_windows_3_3_6",
	actual = "org_lwjgl_lwjgl_openal_natives_windows",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openal_natives_windows_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openal_natives_windows_3_3_6//file",
     out = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openal_natives_windows_x86",
	jar = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows-x86.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openal:3.3.6:natives-windows-x86",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows-x86.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=3a3adad184f5ab9e165db721677c44f9f95f04bc4415a2dffc67640f8d00a863",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openal:3.3.6:natives-windows-x86",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows-x86.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openal_natives_windows_x86_3_3_6",
	actual = "org_lwjgl_lwjgl_openal_natives_windows_x86",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openal_natives_windows_x86_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openal_natives_windows_x86_3_3_6//file",
     out = "org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows-x86.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opencl",
	jar = "org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opencl:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e8eda96fe465e5e18124a1eb18ad8e10861e16056095bb9795827cfe0eb58444",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opencl:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opencl_3_3_6",
	actual = "org_lwjgl_lwjgl_opencl",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opencl_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opencl_3_3_6//file",
     out = "org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opengl",
	jar = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opengl:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=bb0430e0a5fd7b7b5770ae517960b2ea9887bb08d04f3a0cb1aae145332e1310",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opengl:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opengl_3_3_6",
	actual = "org_lwjgl_lwjgl_opengl",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opengl_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengl_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opengl_natives_linux",
	jar = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opengl:3.3.6:natives-linux",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8c0b5c081a7872a3cdb02b6448921da5ae5c23ab49656f299edc7a09b7e99b74",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opengl:3.3.6:natives-linux",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opengl_natives_linux_3_3_6",
	actual = "org_lwjgl_lwjgl_opengl_natives_linux",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opengl_natives_linux_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengl_natives_linux_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opengl_natives_linux_arm32",
	jar = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm32.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opengl:3.3.6:natives-linux-arm32",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm32.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=abf7dbdf0f5a9110595b12a4ad0ae7ed6602e9a09bb234a6b8eba67ca2d82360",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opengl:3.3.6:natives-linux-arm32",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm32.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opengl_natives_linux_arm32_3_3_6",
	actual = "org_lwjgl_lwjgl_opengl_natives_linux_arm32",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opengl_natives_linux_arm32_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengl_natives_linux_arm32_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm32.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opengl_natives_linux_arm64",
	jar = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm64.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opengl:3.3.6:natives-linux-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5bb6b9052f40df5d62fb43f06561b82307e4d50c48ab596ef73b9b2a59c446c1",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opengl:3.3.6:natives-linux-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opengl_natives_linux_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_opengl_natives_linux_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opengl_natives_linux_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengl_natives_linux_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opengl_natives_macos",
	jar = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opengl:3.3.6:natives-macos",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=660fdc9f4f06083938b9e60ab7a3ce9bc9eb6d1c7e60cb54228796101b18b633",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opengl:3.3.6:natives-macos",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opengl_natives_macos_3_3_6",
	actual = "org_lwjgl_lwjgl_opengl_natives_macos",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opengl_natives_macos_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengl_natives_macos_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opengl_natives_macos_arm64",
	jar = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos-arm64.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opengl:3.3.6:natives-macos-arm64",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos-arm64.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2c0f67e7d36d71beed503043c06141af9fd83f5126a726eefceb7b5ba2aaf99c",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opengl:3.3.6:natives-macos-arm64",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos-arm64.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opengl_natives_macos_arm64_3_3_6",
	actual = "org_lwjgl_lwjgl_opengl_natives_macos_arm64",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opengl_natives_macos_arm64_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengl_natives_macos_arm64_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos-arm64.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opengl_natives_windows",
	jar = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opengl:3.3.6:natives-windows",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b54a9d98686284947270e11e94c02aa15c30522119e7b80fcf0c98da6fa3c83c",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opengl:3.3.6:natives-windows",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opengl_natives_windows_3_3_6",
	actual = "org_lwjgl_lwjgl_opengl_natives_windows",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opengl_natives_windows_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengl_natives_windows_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opengl_natives_windows_x86",
	jar = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows-x86.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opengl:3.3.6:natives-windows-x86",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows-x86.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=393f6d1a70fa673ca15b54b4e7ee7b0ca174eaf11613199df9f6dd502339b661",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opengl:3.3.6:natives-windows-x86",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows-x86.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opengl_natives_windows_x86_3_3_6",
	actual = "org_lwjgl_lwjgl_opengl_natives_windows_x86",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opengl_natives_windows_x86_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengl_natives_windows_x86_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows-x86.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opengles",
	jar = "org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opengles:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=270f6eed9c3eb0190ddf9feedaf14b56a658bc7677822c4f08d142ed958fab8d",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opengles:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opengles_3_3_6",
	actual = "org_lwjgl_lwjgl_opengles",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opengles_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opengles_3_3_6//file",
     out = "org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openvr",
	jar = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openvr:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=cc1a7da856c7732bf248cd7469a0fa49b2c0a781ca2571b74185c263e135bfc9",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openvr:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openvr_3_3_6",
	actual = "org_lwjgl_lwjgl_openvr",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openvr_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openvr_3_3_6//file",
     out = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openvr_natives_linux",
	jar = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-linux.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openvr:3.3.6:natives-linux",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-linux.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=c8ebe99a07dd61eb402197fa9f5b6583e87b482ab0a92e727d4f89b2ea8f9932",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openvr:3.3.6:natives-linux",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-linux.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openvr_natives_linux_3_3_6",
	actual = "org_lwjgl_lwjgl_openvr_natives_linux",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openvr_natives_linux_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openvr_natives_linux_3_3_6//file",
     out = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-linux.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openvr_natives_macos",
	jar = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-macos.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openvr:3.3.6:natives-macos",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-macos.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6d622ebc6037cca1f167d8c7a2d91cb017e81e314806371f9a27fd85e262fb5d",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openvr:3.3.6:natives-macos",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-macos.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openvr_natives_macos_3_3_6",
	actual = "org_lwjgl_lwjgl_openvr_natives_macos",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openvr_natives_macos_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openvr_natives_macos_3_3_6//file",
     out = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-macos.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openvr_natives_windows",
	jar = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-windows.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openvr:3.3.6:natives-windows",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-windows.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=20c595ae518c22d2b236f3ad726866e86c557499114d60375a055f9f4936e99c",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openvr:3.3.6:natives-windows",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-windows.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openvr_natives_windows_3_3_6",
	actual = "org_lwjgl_lwjgl_openvr_natives_windows",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openvr_natives_windows_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openvr_natives_windows_3_3_6//file",
     out = "org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-windows.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_openxr",
	jar = "org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-openxr:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=93116ebaa001041e7fb7de60ec2959c328c020c0f88a4f4c24fec7aac37e5020",
	],
	maven_coordinates = "org.lwjgl:lwjgl-openxr:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_openxr_3_3_6",
	actual = "org_lwjgl_lwjgl_openxr",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_openxr_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_openxr_3_3_6//file",
     out = "org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_opus",
	jar = "org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-opus:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=70acd8691aa4686ec9b33ba7df931a4bdce9c7f377d3aa1aa55b4de6f36699ed",
	],
	maven_coordinates = "org.lwjgl:lwjgl-opus:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_opus_3_3_6",
	actual = "org_lwjgl_lwjgl_opus",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_opus_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_opus_3_3_6//file",
     out = "org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_ovr",
	jar = "org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3.jar",
	srcjar = "org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-ovr:3.3.3",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=54dd38e7ecba5fbf6fd157f06e47f7bb83fec42490bf1b8b92590e02d7b81f8d",
	],
	maven_coordinates = "org.lwjgl:lwjgl-ovr:3.3.3",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_ovr_3_3_3",
	actual = "org_lwjgl_lwjgl_ovr",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_ovr_3_3_3_extension",
     src = "@org_lwjgl_lwjgl_ovr_3_3_3//file",
     out = "org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_ovr_natives_windows",
	jar = "org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-natives-windows.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-ovr:3.3.3:natives-windows",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-natives-windows.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ffccb9cead6499539bbb4c033e05a7c5c16a6df9c2352fc3a42cfffacd0e35ad",
	],
	maven_coordinates = "org.lwjgl:lwjgl-ovr:3.3.3:natives-windows",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-natives-windows.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_ovr_natives_windows_3_3_3",
	actual = "org_lwjgl_lwjgl_ovr_natives_windows",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_ovr_natives_windows_3_3_3_extension",
     src = "@org_lwjgl_lwjgl_ovr_natives_windows_3_3_3//file",
     out = "org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-natives-windows.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_par",
	jar = "org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-par:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=84e3c4d095dd89086dc7b0ff627551e8097852d164c7bc49cb77c370e799d22b",
	],
	maven_coordinates = "org.lwjgl:lwjgl-par:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_par_3_3_6",
	actual = "org_lwjgl_lwjgl_par",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_par_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_par_3_3_6//file",
     out = "org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_remotery",
	jar = "org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-remotery:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8d70d096aa00acf6270f072be137a766440cd0c59e5218e05d2df43576e24125",
	],
	maven_coordinates = "org.lwjgl:lwjgl-remotery:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_remotery_3_3_6",
	actual = "org_lwjgl_lwjgl_remotery",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_remotery_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_remotery_3_3_6//file",
     out = "org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_rpmalloc",
	jar = "org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-rpmalloc:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=061aecdad5304cb121ca28217f9e5e8dcfa4609bcbc081c9cd8feed85c8388d4",
	],
	maven_coordinates = "org.lwjgl:lwjgl-rpmalloc:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_rpmalloc_3_3_6",
	actual = "org_lwjgl_lwjgl_rpmalloc",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_rpmalloc_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_rpmalloc_3_3_6//file",
     out = "org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_shaderc",
	jar = "org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-shaderc:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=7bec76dbeef91cd0c899fa37f9f120217b422f8050235e5b36bba41b7be35f71",
	],
	maven_coordinates = "org.lwjgl:lwjgl-shaderc:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_shaderc_3_3_6",
	actual = "org_lwjgl_lwjgl_shaderc",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_shaderc_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_shaderc_3_3_6//file",
     out = "org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_spvc",
	jar = "org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-spvc:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2e4c4528c2f37c82b1a8b66d0827c3791e379b73698660915edff0fcdb765987",
	],
	maven_coordinates = "org.lwjgl:lwjgl-spvc:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_spvc_3_3_6",
	actual = "org_lwjgl_lwjgl_spvc",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_spvc_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_spvc_3_3_6//file",
     out = "org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_sse",
	jar = "org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-sse:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1c6b2e95a6813ab5ca6af0dba06f44ab0949197d16cda8f3141aec9ada29f348",
	],
	maven_coordinates = "org.lwjgl:lwjgl-sse:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_sse_3_3_6",
	actual = "org_lwjgl_lwjgl_sse",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_sse_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_sse_3_3_6//file",
     out = "org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_stb",
	jar = "org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-stb:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ae658e15933e691b2ddba940a6e24b41a38ad6f02fd458e5e3b3ea91e222a6ab",
	],
	maven_coordinates = "org.lwjgl:lwjgl-stb:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_stb_3_3_6",
	actual = "org_lwjgl_lwjgl_stb",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_stb_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_stb_3_3_6//file",
     out = "org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_tinyexr",
	jar = "org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-tinyexr:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=789858322b4d0ec45246e5d17ad1a169a88c2ec81458f10cb7472433cc9a79bf",
	],
	maven_coordinates = "org.lwjgl:lwjgl-tinyexr:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_tinyexr_3_3_6",
	actual = "org_lwjgl_lwjgl_tinyexr",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_tinyexr_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_tinyexr_3_3_6//file",
     out = "org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_tootle",
	jar = "org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-tootle:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=7fcc858fa26402479acc0eb0b13d45b19b8a751b214232e26c4c334e0dac8361",
	],
	maven_coordinates = "org.lwjgl:lwjgl-tootle:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_tootle_3_3_6",
	actual = "org_lwjgl_lwjgl_tootle",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_tootle_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_tootle_3_3_6//file",
     out = "org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_vma",
	jar = "org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-vma:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=9d92550b62d0bb2309cebbb3f4216ff4fe84ae8e32b34d73e888963a44609bf3",
	],
	maven_coordinates = "org.lwjgl:lwjgl-vma:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_vma_3_3_6",
	actual = "org_lwjgl_lwjgl_vma",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_vma_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_vma_3_3_6//file",
     out = "org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_vulkan",
	jar = "org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-vulkan:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=07501098acdfe784f55f4f5ea2e99e33e89a1d18128ddbcd41571434b5e848e4",
	],
	maven_coordinates = "org.lwjgl:lwjgl-vulkan:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_vulkan_3_3_6",
	actual = "org_lwjgl_lwjgl_vulkan",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_vulkan_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_vulkan_3_3_6//file",
     out = "org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_xxhash",
	jar = "org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-xxhash:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=7fd290c3f64203a6f2139d946a636907cdfc60977b6a3317560e0dfdd4e7ad45",
	],
	maven_coordinates = "org.lwjgl:lwjgl-xxhash:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_xxhash_3_3_6",
	actual = "org_lwjgl_lwjgl_xxhash",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_xxhash_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_xxhash_3_3_6//file",
     out = "org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_yoga",
	jar = "org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-yoga:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=92197991d55b5e32d307f64c861cfb7bc3c708905cbf0e58ca9e758966db522b",
	],
	maven_coordinates = "org.lwjgl:lwjgl-yoga:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_yoga_3_3_6",
	actual = "org_lwjgl_lwjgl_yoga",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_yoga_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_yoga_3_3_6//file",
     out = "org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjgl_lwjgl_zstd",
	jar = "org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6.jar",
	srcjar = "org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
	],
	tags = [
		"maven_coordinates=org.lwjgl:lwjgl-zstd:3.3.6",
		"maven_url=https://repo1.maven.org/maven2/org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d636bfcb942810a66249c4bcccc18d7b20bfb9adee9e80282d3e43f99d16a858",
	],
	maven_coordinates = "org.lwjgl:lwjgl-zstd:3.3.6",
	maven_url = "https://repo1.maven.org/maven2/org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjgl_lwjgl_zstd_3_3_6",
	actual = "org_lwjgl_lwjgl_zstd",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjgl_lwjgl_zstd_3_3_6_extension",
     src = "@org_lwjgl_lwjgl_zstd_3_3_6//file",
     out = "org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_lwjglx_lwjgl3_awt",
	jar = "org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8.jar",
	srcjar = "org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8-sources.jar",
	deps = [
		":org_lwjgl_lwjgl",
		":org_lwjgl_lwjgl_jawt",
	],
	tags = [
		"maven_coordinates=org.lwjglx:lwjgl3-awt:0.1.8",
		"maven_url=https://repo1.maven.org/maven2/org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=4e6ee7fd90e03f1708e5882615253547e75876034c50e07e96f335cba3c4a5b2",
	],
	maven_coordinates = "org.lwjglx:lwjgl3-awt:0.1.8",
	maven_url = "https://repo1.maven.org/maven2/org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_lwjglx_lwjgl3_awt_0_1_8",
	actual = "org_lwjglx_lwjgl3_awt",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_lwjglx_lwjgl3_awt_0_1_8_extension",
     src = "@org_lwjglx_lwjgl3_awt_0_1_8//file",
     out = "org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_mockito_mockito_core",
	jar = "org/mockito/mockito-core/5.18.0/mockito-core-5.18.0.jar",
	srcjar = "org/mockito/mockito-core/5.18.0/mockito-core-5.18.0-sources.jar",
	deps = [
		":net_bytebuddy_byte_buddy",
		":net_bytebuddy_byte_buddy_agent",
		":org_objenesis_objenesis",
	],
	tags = [
		"maven_coordinates=org.mockito:mockito-core:5.18.0",
		"maven_url=https://repo1.maven.org/maven2/org/mockito/mockito-core/5.18.0/mockito-core-5.18.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a3d4e40f7fe66016fe42c00de4e343777d74132afc85018e0f03ac6334a60f29",
	],
	maven_coordinates = "org.mockito:mockito-core:5.18.0",
	maven_url = "https://repo1.maven.org/maven2/org/mockito/mockito-core/5.18.0/mockito-core-5.18.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_mockito_mockito_core_5_18_0",
	actual = "org_mockito_mockito_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_mockito_mockito_core_5_18_0_extension",
     src = "@org_mockito_mockito_core_5_18_0//file",
     out = "org/mockito/mockito-core/5.18.0/mockito-core-5.18.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_objenesis_objenesis",
	jar = "org/objenesis/objenesis/3.3/objenesis-3.3.jar",
	srcjar = "org/objenesis/objenesis/3.3/objenesis-3.3-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.objenesis:objenesis:3.3",
		"maven_url=https://repo1.maven.org/maven2/org/objenesis/objenesis/3.3/objenesis-3.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=02dfd0b0439a5591e35b708ed2f5474eb0948f53abf74637e959b8e4ef69bfeb",
	],
	maven_coordinates = "org.objenesis:objenesis:3.3",
	maven_url = "https://repo1.maven.org/maven2/org/objenesis/objenesis/3.3/objenesis-3.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_objenesis_objenesis_3_3",
	actual = "org_objenesis_objenesis",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_objenesis_objenesis_3_3_extension",
     src = "@org_objenesis_objenesis_3_3//file",
     out = "org/objenesis/objenesis/3.3/objenesis-3.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_opentest4j_opentest4j",
	jar = "org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar",
	srcjar = "org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.opentest4j:opentest4j:1.3.0",
		"maven_url=https://repo1.maven.org/maven2/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=48e2df636cab6563ced64dcdff8abb2355627cb236ef0bf37598682ddf742f1b",
	],
	maven_coordinates = "org.opentest4j:opentest4j:1.3.0",
	maven_url = "https://repo1.maven.org/maven2/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_opentest4j_opentest4j_1_3_0",
	actual = "org_opentest4j_opentest4j",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_opentest4j_opentest4j_1_3_0_extension",
     src = "@org_opentest4j_opentest4j_1_3_0//file",
     out = "org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_ow2_asm_asm",
	jar = "org/ow2/asm/asm/9.7/asm-9.7.jar",
	srcjar = "org/ow2/asm/asm/9.7/asm-9.7-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.ow2.asm:asm:9.7",
		"maven_url=https://repo1.maven.org/maven2/org/ow2/asm/asm/9.7/asm-9.7.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=adf46d5e34940bdf148ecdd26a9ee8eea94496a72034ff7141066b3eea5c4e9d",
	],
	maven_coordinates = "org.ow2.asm:asm:9.7",
	maven_url = "https://repo1.maven.org/maven2/org/ow2/asm/asm/9.7/asm-9.7.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_ow2_asm_asm_9_7",
	actual = "org_ow2_asm_asm",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_ow2_asm_asm_9_7_extension",
     src = "@org_ow2_asm_asm_9_7//file",
     out = "org/ow2/asm/asm/9.7/asm-9.7.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_skyscreamer_jsonassert",
	jar = "org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3.jar",
	srcjar = "org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3-sources.jar",
	deps = [
		":com_vaadin_external_google_android_json",
	],
	tags = [
		"maven_coordinates=org.skyscreamer:jsonassert:1.5.3",
		"maven_url=https://repo1.maven.org/maven2/org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=719095c07d4203961320da593441d8b3b643c18eb1d81aa98ea933bb7eb351ba",
	],
	maven_coordinates = "org.skyscreamer:jsonassert:1.5.3",
	maven_url = "https://repo1.maven.org/maven2/org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_skyscreamer_jsonassert_1_5_3",
	actual = "org_skyscreamer_jsonassert",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_skyscreamer_jsonassert_1_5_3_extension",
     src = "@org_skyscreamer_jsonassert_1_5_3//file",
     out = "org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)

# Required by stardoc if the repo is ever frozen
bzl_library(
   name = "defs",
   srcs = ["defs.bzl"],
   deps = [
       "@rules_jvm_external//:implementation",
   ],
   visibility = ["//visibility:public"],
)

sh_binary(
    name = "outdated",
    srcs = ["outdated.sh"],
    data = [
        "@rules_jvm_external//private/tools/prebuilt:outdated_deploy.jar",
        "outdated.artifacts",
        "outdated.boms",
        "outdated.repositories",
    ],
    args = [
        "$(location @rules_jvm_external//private/tools/prebuilt:outdated_deploy.jar)",
        "$(location outdated.artifacts)",
        "$(location outdated.boms)",
        "$(location outdated.repositories)",
    ],
    visibility = ["//visibility:public"],
)

# Alias to unpinned to allow pinning
alias(
  name = "pin",
  actual = "@@rules_jvm_external++maven+unpinned_com_jackbradshaw_maven//:pin",
  visibility = ["//visibility:public"],
)
