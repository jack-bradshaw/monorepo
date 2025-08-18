
# package(default_visibility = ["//visibility:public"])  # https://github.com/bazelbuild/bazel/issues/13681

load("@bazel_skylib//:bzl_library.bzl", "bzl_library")
load("@bazel_skylib//rules:copy_file.bzl", "copy_file")
load("@rules_license//rules:package_info.bzl", "package_info")
load("@rules_java//java:defs.bzl", "java_binary", "java_library", "java_plugin")
load("@rules_jvm_external//private/rules:pin_dependencies.bzl", "pin_dependencies")
load("@rules_jvm_external//private/rules:jvm_import.bzl", "jvm_import")
load("@rules_shell//shell:sh_binary.bzl", "sh_binary")


copy_file(
     name = "aopalliance_aopalliance_sources_1_0_extension",
     src = "@aopalliance_aopalliance_sources_1_0//file",
     out = "aopalliance/aopalliance/1.0/aopalliance-1.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_fasterxml_jackson_core_jackson_core_sources_2_17_1_extension",
     src = "@com_fasterxml_jackson_core_jackson_core_sources_2_17_1//file",
     out = "com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_github_jknack_handlebars_sources_4_3_1_extension",
     src = "@com_github_jknack_handlebars_sources_4_3_1//file",
     out = "com/github/jknack/handlebars/4.3.1/handlebars-4.3.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_android_annotations_sources_4_1_1_4_extension",
     src = "@com_google_android_annotations_sources_4_1_1_4//file",
     out = "com/google/android/annotations/4.1.1.4/annotations-4.1.1.4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_client_google_api_client_sources_2_6_0_extension",
     src = "@com_google_api_client_google_api_client_sources_2_6_0//file",
     out = "com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_grpc_gapic_google_cloud_storage_v2_sources_2_40_1_alpha_extension",
     src = "@com_google_api_grpc_gapic_google_cloud_storage_v2_sources_2_40_1_alpha//file",
     out = "com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_grpc_grpc_google_cloud_storage_v2_sources_2_40_1_alpha_extension",
     src = "@com_google_api_grpc_grpc_google_cloud_storage_v2_sources_2_40_1_alpha//file",
     out = "com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_grpc_proto_google_cloud_storage_v2_sources_2_40_1_alpha_extension",
     src = "@com_google_api_grpc_proto_google_cloud_storage_v2_sources_2_40_1_alpha//file",
     out = "com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_grpc_proto_google_common_protos_sources_2_41_0_extension",
     src = "@com_google_api_grpc_proto_google_common_protos_sources_2_41_0//file",
     out = "com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_grpc_proto_google_iam_v1_sources_1_36_0_extension",
     src = "@com_google_api_grpc_proto_google_iam_v1_sources_1_36_0//file",
     out = "com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_api_common_sources_2_33_0_extension",
     src = "@com_google_api_api_common_sources_2_33_0//file",
     out = "com/google/api/api-common/2.33.0/api-common-2.33.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_gax_sources_2_50_0_extension",
     src = "@com_google_api_gax_sources_2_50_0//file",
     out = "com/google/api/gax/2.50.0/gax-2.50.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_gax_grpc_sources_2_50_0_extension",
     src = "@com_google_api_gax_grpc_sources_2_50_0//file",
     out = "com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_api_gax_httpjson_sources_2_50_0_extension",
     src = "@com_google_api_gax_httpjson_sources_2_50_0//file",
     out = "com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_apis_google_api_services_storage_sources_v1_rev20240621_2_0_0_extension",
     src = "@com_google_apis_google_api_services_storage_sources_v1_rev20240621_2_0_0//file",
     out = "com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_auth_google_auth_library_credentials_sources_1_23_0_extension",
     src = "@com_google_auth_google_auth_library_credentials_sources_1_23_0//file",
     out = "com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_auth_google_auth_library_oauth2_http_sources_1_23_0_extension",
     src = "@com_google_auth_google_auth_library_oauth2_http_sources_1_23_0//file",
     out = "com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_auto_value_auto_value_annotations_sources_1_10_4_extension",
     src = "@com_google_auto_value_auto_value_annotations_sources_1_10_4//file",
     out = "com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_cloud_google_cloud_core_sources_2_40_0_extension",
     src = "@com_google_cloud_google_cloud_core_sources_2_40_0//file",
     out = "com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_cloud_google_cloud_core_grpc_sources_2_40_0_extension",
     src = "@com_google_cloud_google_cloud_core_grpc_sources_2_40_0//file",
     out = "com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_cloud_google_cloud_core_http_sources_2_40_0_extension",
     src = "@com_google_cloud_google_cloud_core_http_sources_2_40_0//file",
     out = "com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_cloud_google_cloud_storage_sources_2_40_1_extension",
     src = "@com_google_cloud_google_cloud_storage_sources_2_40_1//file",
     out = "com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1-sources.jar",
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
     name = "com_google_code_gson_gson_sources_2_11_0_extension",
     src = "@com_google_code_gson_gson_sources_2_11_0//file",
     out = "com/google/code/gson/gson/2.11.0/gson-2.11.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_errorprone_error_prone_annotations_sources_2_36_0_extension",
     src = "@com_google_errorprone_error_prone_annotations_sources_2_36_0//file",
     out = "com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_googlejavaformat_google_java_format_sources_1_22_0_extension",
     src = "@com_google_googlejavaformat_google_java_format_sources_1_22_0//file",
     out = "com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0-sources.jar",
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
     name = "com_google_http_client_google_http_client_sources_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_sources_1_44_2//file",
     out = "com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_http_client_google_http_client_apache_v2_sources_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_apache_v2_sources_1_44_2//file",
     out = "com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_http_client_google_http_client_appengine_sources_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_appengine_sources_1_44_2//file",
     out = "com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_http_client_google_http_client_gson_sources_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_gson_sources_1_44_2//file",
     out = "com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_http_client_google_http_client_jackson2_sources_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_jackson2_sources_1_44_2//file",
     out = "com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_inject_guice_sources_5_1_0_extension",
     src = "@com_google_inject_guice_sources_5_1_0//file",
     out = "com/google/inject/guice/5.1.0/guice-5.1.0-sources.jar",
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
     name = "com_google_oauth_client_google_oauth_client_sources_1_36_0_extension",
     src = "@com_google_oauth_client_google_oauth_client_sources_1_36_0//file",
     out = "com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_protobuf_protobuf_java_sources_3_25_3_extension",
     src = "@com_google_protobuf_protobuf_java_sources_3_25_3//file",
     out = "com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_protobuf_protobuf_java_util_sources_3_25_3_extension",
     src = "@com_google_protobuf_protobuf_java_util_sources_3_25_3//file",
     out = "com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "com_google_re2j_re2j_sources_1_7_extension",
     src = "@com_google_re2j_re2j_sources_1_7//file",
     out = "com/google/re2j/re2j/1.7/re2j-1.7-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "commons_codec_commons_codec_sources_1_18_0_extension",
     src = "@commons_codec_commons_codec_sources_1_18_0//file",
     out = "commons-codec/commons-codec/1.18.0/commons-codec-1.18.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "commons_logging_commons_logging_sources_1_2_extension",
     src = "@commons_logging_commons_logging_sources_1_2//file",
     out = "commons-logging/commons-logging/1.2/commons-logging-1.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_alts_sources_1_62_2_extension",
     src = "@io_grpc_grpc_alts_sources_1_62_2//file",
     out = "io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_api_sources_1_62_2_extension",
     src = "@io_grpc_grpc_api_sources_1_62_2//file",
     out = "io/grpc/grpc-api/1.62.2/grpc-api-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_auth_sources_1_62_2_extension",
     src = "@io_grpc_grpc_auth_sources_1_62_2//file",
     out = "io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_context_sources_1_62_2_extension",
     src = "@io_grpc_grpc_context_sources_1_62_2//file",
     out = "io/grpc/grpc-context/1.62.2/grpc-context-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_core_sources_1_62_2_extension",
     src = "@io_grpc_grpc_core_sources_1_62_2//file",
     out = "io/grpc/grpc-core/1.62.2/grpc-core-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_googleapis_sources_1_62_2_extension",
     src = "@io_grpc_grpc_googleapis_sources_1_62_2//file",
     out = "io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_grpclb_sources_1_62_2_extension",
     src = "@io_grpc_grpc_grpclb_sources_1_62_2//file",
     out = "io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_inprocess_sources_1_62_2_extension",
     src = "@io_grpc_grpc_inprocess_sources_1_62_2//file",
     out = "io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_netty_shaded_sources_1_62_2_extension",
     src = "@io_grpc_grpc_netty_shaded_sources_1_62_2//file",
     out = "io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_protobuf_sources_1_62_2_extension",
     src = "@io_grpc_grpc_protobuf_sources_1_62_2//file",
     out = "io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_protobuf_lite_sources_1_62_2_extension",
     src = "@io_grpc_grpc_protobuf_lite_sources_1_62_2//file",
     out = "io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_rls_sources_1_62_2_extension",
     src = "@io_grpc_grpc_rls_sources_1_62_2//file",
     out = "io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_services_sources_1_62_2_extension",
     src = "@io_grpc_grpc_services_sources_1_62_2//file",
     out = "io/grpc/grpc-services/1.62.2/grpc-services-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_stub_sources_1_62_2_extension",
     src = "@io_grpc_grpc_stub_sources_1_62_2//file",
     out = "io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_util_sources_1_62_2_extension",
     src = "@io_grpc_grpc_util_sources_1_62_2//file",
     out = "io/grpc/grpc-util/1.62.2/grpc-util-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_grpc_grpc_xds_sources_1_62_2_extension",
     src = "@io_grpc_grpc_xds_sources_1_62_2//file",
     out = "io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_buffer_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_buffer_sources_4_1_111_Final//file",
     out = "io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_codec_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_codec_sources_4_1_111_Final//file",
     out = "io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_codec_http_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_codec_http_sources_4_1_111_Final//file",
     out = "io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_codec_http2_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_codec_http2_sources_4_1_111_Final//file",
     out = "io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_common_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_common_sources_4_1_111_Final//file",
     out = "io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_handler_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_handler_sources_4_1_111_Final//file",
     out = "io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_resolver_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_resolver_sources_4_1_111_Final//file",
     out = "io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_transport_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_transport_sources_4_1_111_Final//file",
     out = "io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_transport_classes_epoll_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_transport_classes_epoll_sources_4_1_111_Final//file",
     out = "io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_netty_netty_transport_native_unix_common_sources_4_1_111_Final_extension",
     src = "@io_netty_netty_transport_native_unix_common_sources_4_1_111_Final//file",
     out = "io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final-sources.jar",
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
     name = "io_opencensus_opencensus_proto_sources_0_2_0_extension",
     src = "@io_opencensus_opencensus_proto_sources_0_2_0//file",
     out = "io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "io_perfmark_perfmark_api_sources_0_27_0_extension",
     src = "@io_perfmark_perfmark_api_sources_0_27_0//file",
     out = "io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "javax_annotation_javax_annotation_api_sources_1_3_2_extension",
     src = "@javax_annotation_javax_annotation_api_sources_1_3_2//file",
     out = "javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2-sources.jar",
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
     name = "org_apache_maven_resolver_maven_resolver_api_sources_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_api_sources_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_connector_basic_sources_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_connector_basic_sources_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_impl_sources_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_impl_sources_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_named_locks_sources_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_named_locks_sources_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_spi_sources_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_spi_sources_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_transport_file_sources_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_transport_file_sources_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_transport_http_sources_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_transport_http_sources_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_util_sources_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_util_sources_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_shared_maven_shared_utils_sources_3_4_2_extension",
     src = "@org_apache_maven_shared_maven_shared_utils_sources_3_4_2//file",
     out = "org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_artifact_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_artifact_sources_3_9_10//file",
     out = "org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_builder_support_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_builder_support_sources_3_9_10//file",
     out = "org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_core_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_core_sources_3_9_10//file",
     out = "org/apache/maven/maven-core/3.9.10/maven-core-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_model_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_model_sources_3_9_10//file",
     out = "org/apache/maven/maven-model/3.9.10/maven-model-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_model_builder_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_model_builder_sources_3_9_10//file",
     out = "org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_plugin_api_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_plugin_api_sources_3_9_10//file",
     out = "org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_repository_metadata_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_repository_metadata_sources_3_9_10//file",
     out = "org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_resolver_provider_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_resolver_provider_sources_3_9_10//file",
     out = "org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_settings_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_settings_sources_3_9_10//file",
     out = "org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_apache_maven_maven_settings_builder_sources_3_9_10_extension",
     src = "@org_apache_maven_maven_settings_builder_sources_3_9_10//file",
     out = "org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_bouncycastle_bcpg_jdk15on_sources_1_68_extension",
     src = "@org_bouncycastle_bcpg_jdk15on_sources_1_68//file",
     out = "org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_bouncycastle_bcprov_jdk15on_sources_1_68_extension",
     src = "@org_bouncycastle_bcprov_jdk15on_sources_1_68//file",
     out = "org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_checkerframework_checker_qual_sources_3_44_0_extension",
     src = "@org_checkerframework_checker_qual_sources_3_44_0//file",
     out = "org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_codehaus_mojo_animal_sniffer_annotations_sources_1_23_extension",
     src = "@org_codehaus_mojo_animal_sniffer_annotations_sources_1_23//file",
     out = "org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_codehaus_plexus_plexus_cipher_sources_2_1_0_extension",
     src = "@org_codehaus_plexus_plexus_cipher_sources_2_1_0//file",
     out = "org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_codehaus_plexus_plexus_classworlds_sources_2_9_0_extension",
     src = "@org_codehaus_plexus_plexus_classworlds_sources_2_9_0//file",
     out = "org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_codehaus_plexus_plexus_component_annotations_sources_2_2_0_extension",
     src = "@org_codehaus_plexus_plexus_component_annotations_sources_2_2_0//file",
     out = "org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_codehaus_plexus_plexus_interpolation_sources_1_28_extension",
     src = "@org_codehaus_plexus_plexus_interpolation_sources_1_28//file",
     out = "org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_codehaus_plexus_plexus_sec_dispatcher_sources_2_0_extension",
     src = "@org_codehaus_plexus_plexus_sec_dispatcher_sources_2_0//file",
     out = "org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_codehaus_plexus_plexus_utils_sources_3_6_0_extension",
     src = "@org_codehaus_plexus_plexus_utils_sources_3_6_0//file",
     out = "org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_conscrypt_conscrypt_openjdk_uber_sources_2_5_2_extension",
     src = "@org_conscrypt_conscrypt_openjdk_uber_sources_2_5_2//file",
     out = "org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_eclipse_sisu_org_eclipse_sisu_inject_sources_0_9_0_M4_extension",
     src = "@org_eclipse_sisu_org_eclipse_sisu_inject_sources_0_9_0_M4//file",
     out = "org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_eclipse_sisu_org_eclipse_sisu_plexus_sources_0_9_0_M4_extension",
     src = "@org_eclipse_sisu_org_eclipse_sisu_plexus_sources_0_9_0_M4//file",
     out = "org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_fusesource_jansi_jansi_sources_2_4_1_extension",
     src = "@org_fusesource_jansi_jansi_sources_2_4_1//file",
     out = "org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_gradle_gradle_tooling_api_sources_8_13_extension",
     src = "@org_gradle_gradle_tooling_api_sources_8_13//file",
     out = "org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13-sources.jar",
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
     name = "org_ow2_asm_asm_sources_9_8_extension",
     src = "@org_ow2_asm_asm_sources_9_8//file",
     out = "org/ow2/asm/asm/9.8/asm-9.8-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_reactivestreams_reactive_streams_sources_1_0_4_extension",
     src = "@org_reactivestreams_reactive_streams_sources_1_0_4//file",
     out = "org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_slf4j_jcl_over_slf4j_sources_1_7_36_extension",
     src = "@org_slf4j_jcl_over_slf4j_sources_1_7_36//file",
     out = "org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_slf4j_jul_to_slf4j_sources_2_0_12_extension",
     src = "@org_slf4j_jul_to_slf4j_sources_2_0_12//file",
     out = "org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_slf4j_log4j_over_slf4j_sources_2_0_12_extension",
     src = "@org_slf4j_log4j_over_slf4j_sources_2_0_12//file",
     out = "org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_slf4j_slf4j_api_sources_2_0_12_extension",
     src = "@org_slf4j_slf4j_api_sources_2_0_12//file",
     out = "org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_slf4j_slf4j_simple_sources_2_0_12_extension",
     src = "@org_slf4j_slf4j_simple_sources_2_0_12//file",
     out = "org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "org_threeten_threetenbp_sources_1_6_9_extension",
     src = "@org_threeten_threetenbp_sources_1_6_9//file",
     out = "org/threeten/threetenbp/1.6.9/threetenbp-1.6.9-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_annotations_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_annotations_sources_2_26_12//file",
     out = "software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_apache_client_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_apache_client_sources_2_26_12//file",
     out = "software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_arns_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_arns_sources_2_26_12//file",
     out = "software/amazon/awssdk/arns/2.26.12/arns-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_auth_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_auth_sources_2_26_12//file",
     out = "software/amazon/awssdk/auth/2.26.12/auth-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_aws_core_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_aws_core_sources_2_26_12//file",
     out = "software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_aws_query_protocol_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_aws_query_protocol_sources_2_26_12//file",
     out = "software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_aws_xml_protocol_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_aws_xml_protocol_sources_2_26_12//file",
     out = "software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_checksums_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_checksums_sources_2_26_12//file",
     out = "software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_checksums_spi_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_checksums_spi_sources_2_26_12//file",
     out = "software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_crt_core_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_crt_core_sources_2_26_12//file",
     out = "software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_endpoints_spi_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_endpoints_spi_sources_2_26_12//file",
     out = "software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_http_auth_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_http_auth_sources_2_26_12//file",
     out = "software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_http_auth_aws_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_http_auth_aws_sources_2_26_12//file",
     out = "software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_http_auth_spi_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_http_auth_spi_sources_2_26_12//file",
     out = "software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_http_client_spi_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_http_client_spi_sources_2_26_12//file",
     out = "software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_identity_spi_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_identity_spi_sources_2_26_12//file",
     out = "software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_json_utils_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_json_utils_sources_2_26_12//file",
     out = "software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_metrics_spi_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_metrics_spi_sources_2_26_12//file",
     out = "software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_netty_nio_client_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_netty_nio_client_sources_2_26_12//file",
     out = "software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_profiles_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_profiles_sources_2_26_12//file",
     out = "software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_protocol_core_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_protocol_core_sources_2_26_12//file",
     out = "software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_regions_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_regions_sources_2_26_12//file",
     out = "software/amazon/awssdk/regions/2.26.12/regions-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_retries_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_retries_sources_2_26_12//file",
     out = "software/amazon/awssdk/retries/2.26.12/retries-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_retries_spi_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_retries_spi_sources_2_26_12//file",
     out = "software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_s3_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_s3_sources_2_26_12//file",
     out = "software/amazon/awssdk/s3/2.26.12/s3-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_sdk_core_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_sdk_core_sources_2_26_12//file",
     out = "software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_third_party_jackson_core_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_third_party_jackson_core_sources_2_26_12//file",
     out = "software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_awssdk_utils_sources_2_26_12_extension",
     src = "@software_amazon_awssdk_utils_sources_2_26_12//file",
     out = "software/amazon/awssdk/utils/2.26.12/utils-2.26.12-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
copy_file(
     name = "software_amazon_eventstream_eventstream_sources_1_0_1_extension",
     src = "@software_amazon_eventstream_eventstream_sources_1_0_1//file",
     out = "software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1-sources.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "aopalliance_aopalliance",
	jar = "aopalliance/aopalliance/1.0/aopalliance-1.0.jar",
	srcjar = "aopalliance/aopalliance/1.0/aopalliance-1.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=aopalliance:aopalliance:1.0",
		"maven_url=https://repo1.maven.org/maven2/aopalliance/aopalliance/1.0/aopalliance-1.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=0addec670fedcd3f113c5c8091d783280d23f75e3acb841b61a9cdb079376a08",
	],
	maven_coordinates = "aopalliance:aopalliance:1.0",
	maven_url = "https://repo1.maven.org/maven2/aopalliance/aopalliance/1.0/aopalliance-1.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "aopalliance_aopalliance_1_0",
	actual = "aopalliance_aopalliance",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "aopalliance_aopalliance_1_0_extension",
     src = "@aopalliance_aopalliance_1_0//file",
     out = "aopalliance/aopalliance/1.0/aopalliance-1.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_fasterxml_jackson_core_jackson_core",
	jar = "com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1.jar",
	srcjar = "com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.fasterxml.jackson.core:jackson-core:2.17.1",
		"maven_url=https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ddb26c8a1f1a84535e8213c48b35b253370434e3287b3cf15777856fc4e58ce6",
	],
	maven_coordinates = "com.fasterxml.jackson.core:jackson-core:2.17.1",
	maven_url = "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_fasterxml_jackson_core_jackson_core_2_17_1",
	actual = "com_fasterxml_jackson_core_jackson_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_fasterxml_jackson_core_jackson_core_2_17_1_extension",
     src = "@com_fasterxml_jackson_core_jackson_core_2_17_1//file",
     out = "com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_github_jknack_handlebars",
	jar = "com/github/jknack/handlebars/4.3.1/handlebars-4.3.1.jar",
	srcjar = "com/github/jknack/handlebars/4.3.1/handlebars-4.3.1-sources.jar",
	deps = [
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=com.github.jknack:handlebars:4.3.1",
		"maven_url=https://repo1.maven.org/maven2/com/github/jknack/handlebars/4.3.1/handlebars-4.3.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5424fd12e911cf15befd16341b46e0e1bc681aa61e3cb1c070c57e68dccd5bbd",
	],
	maven_coordinates = "com.github.jknack:handlebars:4.3.1",
	maven_url = "https://repo1.maven.org/maven2/com/github/jknack/handlebars/4.3.1/handlebars-4.3.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_github_jknack_handlebars_4_3_1",
	actual = "com_github_jknack_handlebars",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_github_jknack_handlebars_4_3_1_extension",
     src = "@com_github_jknack_handlebars_4_3_1//file",
     out = "com/github/jknack/handlebars/4.3.1/handlebars-4.3.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_android_annotations",
	jar = "com/google/android/annotations/4.1.1.4/annotations-4.1.1.4.jar",
	srcjar = "com/google/android/annotations/4.1.1.4/annotations-4.1.1.4-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.android:annotations:4.1.1.4",
		"maven_url=https://repo1.maven.org/maven2/com/google/android/annotations/4.1.1.4/annotations-4.1.1.4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ba734e1e84c09d615af6a09d33034b4f0442f8772dec120efb376d86a565ae15",
	],
	maven_coordinates = "com.google.android:annotations:4.1.1.4",
	maven_url = "https://repo1.maven.org/maven2/com/google/android/annotations/4.1.1.4/annotations-4.1.1.4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_android_annotations_4_1_1_4",
	actual = "com_google_android_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_android_annotations_4_1_1_4_extension",
     src = "@com_google_android_annotations_4_1_1_4//file",
     out = "com/google/android/annotations/4.1.1.4/annotations-4.1.1.4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_client_google_api_client",
	jar = "com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0.jar",
	srcjar = "com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.api-client:google-api-client:2.6.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=4ce2d30c647311098995a679562d2605903a9e7710da2e2ea1b17d062062f0c4",
	],
	maven_coordinates = "com.google.api-client:google-api-client:2.6.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_client_google_api_client_2_6_0",
	actual = "com_google_api_client_google_api_client",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_client_google_api_client_2_6_0_extension",
     src = "@com_google_api_client_google_api_client_2_6_0//file",
     out = "com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_grpc_gapic_google_cloud_storage_v2",
	jar = "com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha.jar",
	srcjar = "com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.api.grpc:gapic-google-cloud-storage-v2:2.40.1-alpha",
		"maven_url=https://repo1.maven.org/maven2/com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=533ecc03e4835532d4e6bc032c9bc523172a6444c5261a03b184b7379fde51f8",
	],
	maven_coordinates = "com.google.api.grpc:gapic-google-cloud-storage-v2:2.40.1-alpha",
	maven_url = "https://repo1.maven.org/maven2/com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_grpc_gapic_google_cloud_storage_v2_2_40_1_alpha",
	actual = "com_google_api_grpc_gapic_google_cloud_storage_v2",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_grpc_gapic_google_cloud_storage_v2_2_40_1_alpha_extension",
     src = "@com_google_api_grpc_gapic_google_cloud_storage_v2_2_40_1_alpha//file",
     out = "com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_grpc_grpc_google_cloud_storage_v2",
	jar = "com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha.jar",
	srcjar = "com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.api.grpc:grpc-google-cloud-storage-v2:2.40.1-alpha",
		"maven_url=https://repo1.maven.org/maven2/com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a92ff56ba6fbabc7a16d75f5d06f536e0087979ed6cc0ea8f3de5140733e8450",
	],
	maven_coordinates = "com.google.api.grpc:grpc-google-cloud-storage-v2:2.40.1-alpha",
	maven_url = "https://repo1.maven.org/maven2/com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_grpc_grpc_google_cloud_storage_v2_2_40_1_alpha",
	actual = "com_google_api_grpc_grpc_google_cloud_storage_v2",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_grpc_grpc_google_cloud_storage_v2_2_40_1_alpha_extension",
     src = "@com_google_api_grpc_grpc_google_cloud_storage_v2_2_40_1_alpha//file",
     out = "com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_grpc_proto_google_cloud_storage_v2",
	jar = "com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha.jar",
	srcjar = "com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.api.grpc:proto-google-cloud-storage-v2:2.40.1-alpha",
		"maven_url=https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=225a94c8509b78473df7701ac2343a0e2641fe6181a288f84b402d3ef5f90587",
	],
	maven_coordinates = "com.google.api.grpc:proto-google-cloud-storage-v2:2.40.1-alpha",
	maven_url = "https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_grpc_proto_google_cloud_storage_v2_2_40_1_alpha",
	actual = "com_google_api_grpc_proto_google_cloud_storage_v2",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_grpc_proto_google_cloud_storage_v2_2_40_1_alpha_extension",
     src = "@com_google_api_grpc_proto_google_cloud_storage_v2_2_40_1_alpha//file",
     out = "com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_grpc_proto_google_common_protos",
	jar = "com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0.jar",
	srcjar = "com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0-sources.jar",
	deps = [
		":com_google_protobuf_protobuf_java",
	],
	tags = [
		"maven_coordinates=com.google.api.grpc:proto-google-common-protos:2.41.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=49edeba62f334053b91aa9455c95e38449269891b920dbc36daa74e959a3d89a",
	],
	maven_coordinates = "com.google.api.grpc:proto-google-common-protos:2.41.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_grpc_proto_google_common_protos_2_41_0",
	actual = "com_google_api_grpc_proto_google_common_protos",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_grpc_proto_google_common_protos_2_41_0_extension",
     src = "@com_google_api_grpc_proto_google_common_protos_2_41_0//file",
     out = "com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_grpc_proto_google_iam_v1",
	jar = "com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0.jar",
	srcjar = "com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0-sources.jar",
	deps = [
		":com_google_api_grpc_proto_google_common_protos",
		":com_google_protobuf_protobuf_java",
	],
	tags = [
		"maven_coordinates=com.google.api.grpc:proto-google-iam-v1:1.36.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ec95a04fa6ee822e91cd8b4917a4602fbc0fb57413c2ba8f079807545b8eb193",
	],
	maven_coordinates = "com.google.api.grpc:proto-google-iam-v1:1.36.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_grpc_proto_google_iam_v1_1_36_0",
	actual = "com_google_api_grpc_proto_google_iam_v1",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_grpc_proto_google_iam_v1_1_36_0_extension",
     src = "@com_google_api_grpc_proto_google_iam_v1_1_36_0//file",
     out = "com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_api_common",
	jar = "com/google/api/api-common/2.33.0/api-common-2.33.0.jar",
	srcjar = "com/google/api/api-common/2.33.0/api-common-2.33.0-sources.jar",
	deps = [
		":com_google_auto_value_auto_value_annotations",
		":com_google_code_findbugs_jsr305",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":com_google_j2objc_j2objc_annotations",
		":javax_annotation_javax_annotation_api",
	],
	tags = [
		"maven_coordinates=com.google.api:api-common:2.33.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/api/api-common/2.33.0/api-common-2.33.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5077981c2f6649b615a10631d89759861aeb4edb49ea4fa2e1bb5506a70db1be",
	],
	maven_coordinates = "com.google.api:api-common:2.33.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/api/api-common/2.33.0/api-common-2.33.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_api_common_2_33_0",
	actual = "com_google_api_api_common",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_api_common_2_33_0_extension",
     src = "@com_google_api_api_common_2_33_0//file",
     out = "com/google/api/api-common/2.33.0/api-common-2.33.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_gax",
	jar = "com/google/api/gax/2.50.0/gax-2.50.0.jar",
	srcjar = "com/google/api/gax/2.50.0/gax-2.50.0-sources.jar",
	deps = [
		":com_google_api_grpc_proto_google_common_protos",
		":com_google_api_api_common",
		":com_google_auth_google_auth_library_credentials",
		":com_google_auth_google_auth_library_oauth2_http",
		":com_google_guava_guava",
		":com_google_protobuf_protobuf_java",
		":io_opencensus_opencensus_api",
		":org_threeten_threetenbp",
	],
	tags = [
		"maven_coordinates=com.google.api:gax:2.50.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/api/gax/2.50.0/gax-2.50.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=fa7d1cef5ef09dfcc1ff2e26d020f5023817dc14d4f1320391ea631698126a52",
	],
	maven_coordinates = "com.google.api:gax:2.50.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/api/gax/2.50.0/gax-2.50.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_gax_2_50_0",
	actual = "com_google_api_gax",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_gax_2_50_0_extension",
     src = "@com_google_api_gax_2_50_0//file",
     out = "com/google/api/gax/2.50.0/gax-2.50.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_gax_grpc",
	jar = "com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0.jar",
	srcjar = "com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.api:gax-grpc:2.50.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b2c5d39326e402ecafbcc02221400b802a9e01e3cd457ab5e69386da2d53d737",
	],
	maven_coordinates = "com.google.api:gax-grpc:2.50.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_gax_grpc_2_50_0",
	actual = "com_google_api_gax_grpc",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_gax_grpc_2_50_0_extension",
     src = "@com_google_api_gax_grpc_2_50_0//file",
     out = "com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_api_gax_httpjson",
	jar = "com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0.jar",
	srcjar = "com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.api:gax-httpjson:2.50.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6c34ec75e64bb925af9fe15a024820bfaf3e19196b75a1be8092f842f13e47e4",
	],
	maven_coordinates = "com.google.api:gax-httpjson:2.50.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_api_gax_httpjson_2_50_0",
	actual = "com_google_api_gax_httpjson",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_api_gax_httpjson_2_50_0_extension",
     src = "@com_google_api_gax_httpjson_2_50_0//file",
     out = "com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_apis_google_api_services_storage",
	jar = "com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0.jar",
	srcjar = "com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.apis:google-api-services-storage:v1-rev20240621-2.0.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=db4a684212e945a9206c94f4730faa7fc825f0666417e7b7e6925451bde6096c",
	],
	maven_coordinates = "com.google.apis:google-api-services-storage:v1-rev20240621-2.0.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_apis_google_api_services_storage_v1_rev20240621_2_0_0",
	actual = "com_google_apis_google_api_services_storage",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_apis_google_api_services_storage_v1_rev20240621_2_0_0_extension",
     src = "@com_google_apis_google_api_services_storage_v1_rev20240621_2_0_0//file",
     out = "com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_auth_google_auth_library_credentials",
	jar = "com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0.jar",
	srcjar = "com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.auth:google-auth-library-credentials:1.23.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d982eda20835e301dcbeec4d083289a44fdd06e9a35ce18449054f4ffd3f099f",
	],
	maven_coordinates = "com.google.auth:google-auth-library-credentials:1.23.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_auth_google_auth_library_credentials_1_23_0",
	actual = "com_google_auth_google_auth_library_credentials",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_auth_google_auth_library_credentials_1_23_0_extension",
     src = "@com_google_auth_google_auth_library_credentials_1_23_0//file",
     out = "com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_auth_google_auth_library_oauth2_http",
	jar = "com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0.jar",
	srcjar = "com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0-sources.jar",
	deps = [
		":com_google_auth_google_auth_library_credentials",
		":com_google_auto_value_auto_value_annotations",
		":com_google_code_findbugs_jsr305",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":com_google_http_client_google_http_client",
		":com_google_http_client_google_http_client_gson",
	],
	tags = [
		"maven_coordinates=com.google.auth:google-auth-library-oauth2-http:1.23.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f2bf739509b5f3697cb1bf33ff9dc27e8fc886cedb2f6376a458263f793ed133",
	],
	maven_coordinates = "com.google.auth:google-auth-library-oauth2-http:1.23.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_auth_google_auth_library_oauth2_http_1_23_0",
	actual = "com_google_auth_google_auth_library_oauth2_http",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_auth_google_auth_library_oauth2_http_1_23_0_extension",
     src = "@com_google_auth_google_auth_library_oauth2_http_1_23_0//file",
     out = "com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_auto_value_auto_value_annotations",
	jar = "com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar",
	srcjar = "com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.auto.value:auto-value-annotations:1.10.4",
		"maven_url=https://repo1.maven.org/maven2/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e1c45e6beadaef9797cb0d9afd5a45621ad061cd8632012f85582853a3887825",
	],
	maven_coordinates = "com.google.auto.value:auto-value-annotations:1.10.4",
	maven_url = "https://repo1.maven.org/maven2/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_auto_value_auto_value_annotations_1_10_4",
	actual = "com_google_auto_value_auto_value_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_auto_value_auto_value_annotations_1_10_4_extension",
     src = "@com_google_auto_value_auto_value_annotations_1_10_4//file",
     out = "com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_cloud_google_cloud_core",
	jar = "com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0.jar",
	srcjar = "com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0-sources.jar",
	deps = [
		":com_google_api_grpc_proto_google_common_protos",
		":com_google_api_grpc_proto_google_iam_v1",
		":com_google_api_api_common",
		":com_google_api_gax",
		":com_google_auth_google_auth_library_credentials",
		":com_google_auth_google_auth_library_oauth2_http",
		":com_google_auto_value_auto_value_annotations",
		":com_google_code_findbugs_jsr305",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":com_google_http_client_google_http_client",
		":com_google_http_client_google_http_client_gson",
		":com_google_protobuf_protobuf_java",
		":com_google_protobuf_protobuf_java_util",
		":org_threeten_threetenbp",
	],
	tags = [
		"maven_coordinates=com.google.cloud:google-cloud-core:2.40.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a3c1993957ac597ccfbb111e2814aed9c5298f04987886fda81be102f426372c",
	],
	maven_coordinates = "com.google.cloud:google-cloud-core:2.40.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_cloud_google_cloud_core_2_40_0",
	actual = "com_google_cloud_google_cloud_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_cloud_google_cloud_core_2_40_0_extension",
     src = "@com_google_cloud_google_cloud_core_2_40_0//file",
     out = "com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_cloud_google_cloud_core_grpc",
	jar = "com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0.jar",
	srcjar = "com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.cloud:google-cloud-core-grpc:2.40.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e3535528221c6a5d33f55f164fc9602ed08f51bf4a4e31befc2e0a42a530159d",
	],
	maven_coordinates = "com.google.cloud:google-cloud-core-grpc:2.40.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_cloud_google_cloud_core_grpc_2_40_0",
	actual = "com_google_cloud_google_cloud_core_grpc",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_cloud_google_cloud_core_grpc_2_40_0_extension",
     src = "@com_google_cloud_google_cloud_core_grpc_2_40_0//file",
     out = "com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_cloud_google_cloud_core_http",
	jar = "com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0.jar",
	srcjar = "com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.cloud:google-cloud-core-http:2.40.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=66dfb9e652ac8f1a0c0a9067c263448126a3a6c4b2b00a24467a6056ff8f0298",
	],
	maven_coordinates = "com.google.cloud:google-cloud-core-http:2.40.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_cloud_google_cloud_core_http_2_40_0",
	actual = "com_google_cloud_google_cloud_core_http",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_cloud_google_cloud_core_http_2_40_0_extension",
     src = "@com_google_cloud_google_cloud_core_http_2_40_0//file",
     out = "com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_cloud_google_cloud_storage",
	jar = "com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1.jar",
	srcjar = "com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1-sources.jar",
	deps = [
		":com_fasterxml_jackson_core_jackson_core",
		":com_google_android_annotations",
		":com_google_api_client_google_api_client",
		":com_google_api_grpc_gapic_google_cloud_storage_v2",
		":com_google_api_grpc_grpc_google_cloud_storage_v2",
		":com_google_api_grpc_proto_google_cloud_storage_v2",
		":com_google_api_grpc_proto_google_common_protos",
		":com_google_api_grpc_proto_google_iam_v1",
		":com_google_api_api_common",
		":com_google_api_gax",
		":com_google_api_gax_grpc",
		":com_google_api_gax_httpjson",
		":com_google_apis_google_api_services_storage",
		":com_google_auth_google_auth_library_credentials",
		":com_google_auth_google_auth_library_oauth2_http",
		":com_google_auto_value_auto_value_annotations",
		":com_google_cloud_google_cloud_core",
		":com_google_cloud_google_cloud_core_grpc",
		":com_google_cloud_google_cloud_core_http",
		":com_google_code_findbugs_jsr305",
		":com_google_code_gson_gson",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_failureaccess",
		":com_google_guava_guava",
		":com_google_guava_listenablefuture",
		":com_google_http_client_google_http_client",
		":com_google_http_client_google_http_client_apache_v2",
		":com_google_http_client_google_http_client_appengine",
		":com_google_http_client_google_http_client_gson",
		":com_google_http_client_google_http_client_jackson2",
		":com_google_j2objc_j2objc_annotations",
		":com_google_oauth_client_google_oauth_client",
		":com_google_protobuf_protobuf_java",
		":com_google_protobuf_protobuf_java_util",
		":com_google_re2j_re2j",
		":commons_codec_commons_codec",
		":io_grpc_grpc_alts",
		":io_grpc_grpc_api",
		":io_grpc_grpc_auth",
		":io_grpc_grpc_context",
		":io_grpc_grpc_core",
		":io_grpc_grpc_googleapis",
		":io_grpc_grpc_grpclb",
		":io_grpc_grpc_inprocess",
		":io_grpc_grpc_netty_shaded",
		":io_grpc_grpc_protobuf",
		":io_grpc_grpc_protobuf_lite",
		":io_grpc_grpc_rls",
		":io_grpc_grpc_services",
		":io_grpc_grpc_stub",
		":io_grpc_grpc_util",
		":io_grpc_grpc_xds",
		":io_opencensus_opencensus_api",
		":io_opencensus_opencensus_contrib_http_util",
		":io_opencensus_opencensus_proto",
		":io_perfmark_perfmark_api",
		":javax_annotation_javax_annotation_api",
		":org_checkerframework_checker_qual",
		":org_codehaus_mojo_animal_sniffer_annotations",
		":org_conscrypt_conscrypt_openjdk_uber",
		":org_threeten_threetenbp",
	],
	tags = [
		"maven_coordinates=com.google.cloud:google-cloud-storage:2.40.1",
		"maven_url=https://repo1.maven.org/maven2/com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d37399b0e96ecea896bdaa091375eb4e8783ebeb452d344ad9d55e20d67b8b72",
	],
	maven_coordinates = "com.google.cloud:google-cloud-storage:2.40.1",
	maven_url = "https://repo1.maven.org/maven2/com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_cloud_google_cloud_storage_2_40_1",
	actual = "com_google_cloud_google_cloud_storage",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_cloud_google_cloud_storage_2_40_1_extension",
     src = "@com_google_cloud_google_cloud_storage_2_40_1//file",
     out = "com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1.jar",
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
	jar = "com/google/code/gson/gson/2.11.0/gson-2.11.0.jar",
	srcjar = "com/google/code/gson/gson/2.11.0/gson-2.11.0-sources.jar",
	deps = [
		":com_google_errorprone_error_prone_annotations",
	],
	tags = [
		"maven_coordinates=com.google.code.gson:gson:2.11.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/code/gson/gson/2.11.0/gson-2.11.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=57928d6e5a6edeb2abd3770a8f95ba44dce45f3b23b7a9dc2b309c581552a78b",
	],
	maven_coordinates = "com.google.code.gson:gson:2.11.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.11.0/gson-2.11.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_code_gson_gson_2_11_0",
	actual = "com_google_code_gson_gson",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_code_gson_gson_2_11_0_extension",
     src = "@com_google_code_gson_gson_2_11_0//file",
     out = "com/google/code/gson/gson/2.11.0/gson-2.11.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_errorprone_error_prone_annotations",
	jar = "com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0.jar",
	srcjar = "com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.errorprone:error_prone_annotations:2.36.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=77440e270b0bc9a249903c5a076c36a722c4886ca4f42675f2903a1c53ed61a5",
	],
	maven_coordinates = "com.google.errorprone:error_prone_annotations:2.36.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_errorprone_error_prone_annotations_2_36_0",
	actual = "com_google_errorprone_error_prone_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_errorprone_error_prone_annotations_2_36_0_extension",
     src = "@com_google_errorprone_error_prone_annotations_2_36_0//file",
     out = "com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_googlejavaformat_google_java_format",
	jar = "com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0.jar",
	srcjar = "com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0-sources.jar",
	deps = [
		":com_google_guava_guava",
	],
	tags = [
		"maven_coordinates=com.google.googlejavaformat:google-java-format:1.22.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=4f4bdba0f2a3d7e84be47683a0c2a4ba69024d29d906d09784181f68f04af792",
	],
	maven_coordinates = "com.google.googlejavaformat:google-java-format:1.22.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_googlejavaformat_google_java_format_1_22_0",
	actual = "com_google_googlejavaformat_google_java_format",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_googlejavaformat_google_java_format_1_22_0_extension",
     src = "@com_google_googlejavaformat_google_java_format_1_22_0//file",
     out = "com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0.jar",
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
	jar = "com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2.jar",
	srcjar = "com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2-sources.jar",
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
		"maven_coordinates=com.google.http-client:google-http-client:1.44.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=390618d7b51704240b8fd28e1230fa35d220f93f4b4ba80f63e38db00dacb09e",
	],
	maven_coordinates = "com.google.http-client:google-http-client:1.44.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_http_client_google_http_client_1_44_2",
	actual = "com_google_http_client_google_http_client",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_http_client_google_http_client_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_1_44_2//file",
     out = "com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_http_client_google_http_client_apache_v2",
	jar = "com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2.jar",
	srcjar = "com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.http-client:google-http-client-apache-v2:1.44.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=104596bb9296a403a5150b8e3b72dc16d9ca0b0f94b1287348ada5825e19298d",
	],
	maven_coordinates = "com.google.http-client:google-http-client-apache-v2:1.44.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_http_client_google_http_client_apache_v2_1_44_2",
	actual = "com_google_http_client_google_http_client_apache_v2",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_http_client_google_http_client_apache_v2_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_apache_v2_1_44_2//file",
     out = "com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_http_client_google_http_client_appengine",
	jar = "com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2.jar",
	srcjar = "com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.http-client:google-http-client-appengine:1.44.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f9af780c40f7de304c05dfe24b362588a56c8a804c0b8d929dd82296972abe47",
	],
	maven_coordinates = "com.google.http-client:google-http-client-appengine:1.44.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_http_client_google_http_client_appengine_1_44_2",
	actual = "com_google_http_client_google_http_client_appengine",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_http_client_google_http_client_appengine_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_appengine_1_44_2//file",
     out = "com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_http_client_google_http_client_gson",
	jar = "com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2.jar",
	srcjar = "com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2-sources.jar",
	deps = [
		":com_google_code_gson_gson",
		":com_google_http_client_google_http_client",
	],
	tags = [
		"maven_coordinates=com.google.http-client:google-http-client-gson:1.44.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1119b66685195310375b717de2215d6c5d14fa8ed9f57e07b4fecd461e7b9db7",
	],
	maven_coordinates = "com.google.http-client:google-http-client-gson:1.44.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_http_client_google_http_client_gson_1_44_2",
	actual = "com_google_http_client_google_http_client_gson",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_http_client_google_http_client_gson_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_gson_1_44_2//file",
     out = "com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_http_client_google_http_client_jackson2",
	jar = "com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2.jar",
	srcjar = "com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.http-client:google-http-client-jackson2:1.44.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b6b81f992b8c20cd5de332e43ea964227e5dd71663ca27194c25bfc55c1067b6",
	],
	maven_coordinates = "com.google.http-client:google-http-client-jackson2:1.44.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_http_client_google_http_client_jackson2_1_44_2",
	actual = "com_google_http_client_google_http_client_jackson2",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_http_client_google_http_client_jackson2_1_44_2_extension",
     src = "@com_google_http_client_google_http_client_jackson2_1_44_2//file",
     out = "com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_inject_guice_classes",
	jar = "com/google/inject/guice/5.1.0/guice-5.1.0-classes.jar",
	deps = [
		":aopalliance_aopalliance",
		":javax_inject_javax_inject",
	],
	tags = [
		"maven_coordinates=com.google.inject:guice:5.1.0:classes",
		"maven_url=https://repo1.maven.org/maven2/com/google/inject/guice/5.1.0/guice-5.1.0-classes.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=142ad4475e19524d2fe3ac995b3f7cbc962fc726f2edb9dbdccc61feab9b2bf9",
	],
	maven_coordinates = "com.google.inject:guice:5.1.0:classes",
	maven_url = "https://repo1.maven.org/maven2/com/google/inject/guice/5.1.0/guice-5.1.0-classes.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_inject_guice_classes_5_1_0",
	actual = "com_google_inject_guice_classes",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_inject_guice_classes_5_1_0_extension",
     src = "@com_google_inject_guice_classes_5_1_0//file",
     out = "com/google/inject/guice/5.1.0/guice-5.1.0-classes.jar",
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
	name = "com_google_oauth_client_google_oauth_client",
	jar = "com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0.jar",
	srcjar = "com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.oauth-client:google-oauth-client:1.36.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8fee7bbe7aaee214ce461f0cd983e3c438fd43941697394391aaa01edb7d703b",
	],
	maven_coordinates = "com.google.oauth-client:google-oauth-client:1.36.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_oauth_client_google_oauth_client_1_36_0",
	actual = "com_google_oauth_client_google_oauth_client",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_oauth_client_google_oauth_client_1_36_0_extension",
     src = "@com_google_oauth_client_google_oauth_client_1_36_0//file",
     out = "com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_java",
	jar = "com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3.jar",
	srcjar = "com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-java:3.25.3",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e90d8ddb963b20a972a6a59b5093ade2b07cbe546cab3279aaf4383260385f58",
	],
	maven_coordinates = "com.google.protobuf:protobuf-java:3.25.3",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_java_3_25_3",
	actual = "com_google_protobuf_protobuf_java",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_java_3_25_3_extension",
     src = "@com_google_protobuf_protobuf_java_3_25_3//file",
     out = "com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_java_util",
	jar = "com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3.jar",
	srcjar = "com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3-sources.jar",
	deps = [
		":com_google_code_findbugs_jsr305",
		":com_google_code_gson_gson",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":com_google_j2objc_j2objc_annotations",
		":com_google_protobuf_protobuf_java",
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-java-util:3.25.3",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b813c8d6d554cb71c1e82d171d7f80730ae74222a185c863cbedf05072c88155",
	],
	maven_coordinates = "com.google.protobuf:protobuf-java-util:3.25.3",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_java_util_3_25_3",
	actual = "com_google_protobuf_protobuf_java_util",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_java_util_3_25_3_extension",
     src = "@com_google_protobuf_protobuf_java_util_3_25_3//file",
     out = "com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "com_google_re2j_re2j",
	jar = "com/google/re2j/re2j/1.7/re2j-1.7.jar",
	srcjar = "com/google/re2j/re2j/1.7/re2j-1.7-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.re2j:re2j:1.7",
		"maven_url=https://repo1.maven.org/maven2/com/google/re2j/re2j/1.7/re2j-1.7.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=4f657af51ab8bb0909bcc3eb40862d26125af8cbcf92aaaba595fed77f947bc0",
	],
	maven_coordinates = "com.google.re2j:re2j:1.7",
	maven_url = "https://repo1.maven.org/maven2/com/google/re2j/re2j/1.7/re2j-1.7.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_re2j_re2j_1_7",
	actual = "com_google_re2j_re2j",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_re2j_re2j_1_7_extension",
     src = "@com_google_re2j_re2j_1_7//file",
     out = "com/google/re2j/re2j/1.7/re2j-1.7.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "commons_codec_commons_codec",
	jar = "commons-codec/commons-codec/1.18.0/commons-codec-1.18.0.jar",
	srcjar = "commons-codec/commons-codec/1.18.0/commons-codec-1.18.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=commons-codec:commons-codec:1.18.0",
		"maven_url=https://repo1.maven.org/maven2/commons-codec/commons-codec/1.18.0/commons-codec-1.18.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ba005f304cef92a3dede24a38ad5ac9b8afccf0d8f75839d6c1338634cf7f6e4",
	],
	maven_coordinates = "commons-codec:commons-codec:1.18.0",
	maven_url = "https://repo1.maven.org/maven2/commons-codec/commons-codec/1.18.0/commons-codec-1.18.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "commons_codec_commons_codec_1_18_0",
	actual = "commons_codec_commons_codec",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "commons_codec_commons_codec_1_18_0_extension",
     src = "@commons_codec_commons_codec_1_18_0//file",
     out = "commons-codec/commons-codec/1.18.0/commons-codec-1.18.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "commons_logging_commons_logging",
	jar = "commons-logging/commons-logging/1.2/commons-logging-1.2.jar",
	srcjar = "commons-logging/commons-logging/1.2/commons-logging-1.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=commons-logging:commons-logging:1.2",
		"maven_url=https://repo1.maven.org/maven2/commons-logging/commons-logging/1.2/commons-logging-1.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=daddea1ea0be0f56978ab3006b8ac92834afeefbd9b7e4e6316fca57df0fa636",
	],
	maven_coordinates = "commons-logging:commons-logging:1.2",
	maven_url = "https://repo1.maven.org/maven2/commons-logging/commons-logging/1.2/commons-logging-1.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "commons_logging_commons_logging_1_2",
	actual = "commons_logging_commons_logging",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "commons_logging_commons_logging_1_2_extension",
     src = "@commons_logging_commons_logging_1_2//file",
     out = "commons-logging/commons-logging/1.2/commons-logging-1.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_alts",
	jar = "io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2.jar",
	srcjar = "io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-alts:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8c36fc921f18813a2f82e9f70211718c82280341c3822ab9d1782eaec2a8882a",
	],
	maven_coordinates = "io.grpc:grpc-alts:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_alts_1_62_2",
	actual = "io_grpc_grpc_alts",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_alts_1_62_2_extension",
     src = "@io_grpc_grpc_alts_1_62_2//file",
     out = "io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_api",
	jar = "io/grpc/grpc-api/1.62.2/grpc-api-1.62.2.jar",
	srcjar = "io/grpc/grpc-api/1.62.2/grpc-api-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-api:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-api/1.62.2/grpc-api-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2e896944cf513e0e5cfd32bcd72c89601a27c6ca56916f84b20f3a13bacf1b1f",
	],
	maven_coordinates = "io.grpc:grpc-api:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-api/1.62.2/grpc-api-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_api_1_62_2",
	actual = "io_grpc_grpc_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_api_1_62_2_extension",
     src = "@io_grpc_grpc_api_1_62_2//file",
     out = "io/grpc/grpc-api/1.62.2/grpc-api-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_auth",
	jar = "io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2.jar",
	srcjar = "io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-auth:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6a16c43d956c79190486d3d0b951836a6706b3282b5d275a9bc4d33eb79d5618",
	],
	maven_coordinates = "io.grpc:grpc-auth:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_auth_1_62_2",
	actual = "io_grpc_grpc_auth",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_auth_1_62_2_extension",
     src = "@io_grpc_grpc_auth_1_62_2//file",
     out = "io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_context",
	jar = "io/grpc/grpc-context/1.62.2/grpc-context-1.62.2.jar",
	srcjar = "io/grpc/grpc-context/1.62.2/grpc-context-1.62.2-sources.jar",
	deps = [
		":io_grpc_grpc_api",
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-context:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-context/1.62.2/grpc-context-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=9959747df6a753119e1c1a3dff01aa766d2455f5e4860acaa305359e1d533a05",
	],
	maven_coordinates = "io.grpc:grpc-context:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-context/1.62.2/grpc-context-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_context_1_62_2",
	actual = "io_grpc_grpc_context",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_context_1_62_2_extension",
     src = "@io_grpc_grpc_context_1_62_2//file",
     out = "io/grpc/grpc-context/1.62.2/grpc-context-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_core",
	jar = "io/grpc/grpc-core/1.62.2/grpc-core-1.62.2.jar",
	srcjar = "io/grpc/grpc-core/1.62.2/grpc-core-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-core:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-core/1.62.2/grpc-core-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=18439902c473a2c1511e517d13b8ae796378850a8eda43787c6ba778fa90fcc5",
	],
	maven_coordinates = "io.grpc:grpc-core:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-core/1.62.2/grpc-core-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_core_1_62_2",
	actual = "io_grpc_grpc_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_core_1_62_2_extension",
     src = "@io_grpc_grpc_core_1_62_2//file",
     out = "io/grpc/grpc-core/1.62.2/grpc-core-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_googleapis",
	jar = "io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2.jar",
	srcjar = "io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-googleapis:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=0b8350c417dd5757056d97be671de360d91d6327d8de5871f8f4a556a12564f5",
	],
	maven_coordinates = "io.grpc:grpc-googleapis:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_googleapis_1_62_2",
	actual = "io_grpc_grpc_googleapis",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_googleapis_1_62_2_extension",
     src = "@io_grpc_grpc_googleapis_1_62_2//file",
     out = "io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_grpclb",
	jar = "io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2.jar",
	srcjar = "io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-grpclb:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=49ed5d4b35e8d0b4f9b6f39fef774fc2a5927eeaeca7f54610e1b7fa0dc31f5a",
	],
	maven_coordinates = "io.grpc:grpc-grpclb:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_grpclb_1_62_2",
	actual = "io_grpc_grpc_grpclb",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_grpclb_1_62_2_extension",
     src = "@io_grpc_grpc_grpclb_1_62_2//file",
     out = "io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_inprocess",
	jar = "io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2.jar",
	srcjar = "io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-inprocess:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f3c28a9d7f13fa995e4dd89e4f6aa08fa3b383665314fdfccb9f87f346625df7",
	],
	maven_coordinates = "io.grpc:grpc-inprocess:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_inprocess_1_62_2",
	actual = "io_grpc_grpc_inprocess",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_inprocess_1_62_2_extension",
     src = "@io_grpc_grpc_inprocess_1_62_2//file",
     out = "io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_netty_shaded",
	jar = "io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2.jar",
	srcjar = "io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-netty-shaded:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b3f1823ef30ca02ac721020f4b6492248efdbd0548c78e893d5d245cbca2cc60",
	],
	maven_coordinates = "io.grpc:grpc-netty-shaded:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_netty_shaded_1_62_2",
	actual = "io_grpc_grpc_netty_shaded",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_netty_shaded_1_62_2_extension",
     src = "@io_grpc_grpc_netty_shaded_1_62_2//file",
     out = "io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_protobuf",
	jar = "io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2.jar",
	srcjar = "io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-protobuf:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=66a0b196318bdfd817d965d2d82b9c81dfced8eb08c0f7510fcb728d2994237a",
	],
	maven_coordinates = "io.grpc:grpc-protobuf:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_protobuf_1_62_2",
	actual = "io_grpc_grpc_protobuf",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_protobuf_1_62_2_extension",
     src = "@io_grpc_grpc_protobuf_1_62_2//file",
     out = "io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_protobuf_lite",
	jar = "io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2.jar",
	srcjar = "io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-protobuf-lite:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=79997989a8c2b5bf4dd18182a2df2e2f668703d68ba7c317e7a07809d33f91f4",
	],
	maven_coordinates = "io.grpc:grpc-protobuf-lite:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_protobuf_lite_1_62_2",
	actual = "io_grpc_grpc_protobuf_lite",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_protobuf_lite_1_62_2_extension",
     src = "@io_grpc_grpc_protobuf_lite_1_62_2//file",
     out = "io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_rls",
	jar = "io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2.jar",
	srcjar = "io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-rls:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2fa8cb6cc22d28080b30f9ff0c6143c180017ae64a51a61828432ff48813cc88",
	],
	maven_coordinates = "io.grpc:grpc-rls:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_rls_1_62_2",
	actual = "io_grpc_grpc_rls",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_rls_1_62_2_extension",
     src = "@io_grpc_grpc_rls_1_62_2//file",
     out = "io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_services",
	jar = "io/grpc/grpc-services/1.62.2/grpc-services-1.62.2.jar",
	srcjar = "io/grpc/grpc-services/1.62.2/grpc-services-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-services:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-services/1.62.2/grpc-services-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=72f6eba0670184b634e7dcde0b97cde378a7cd74cdf63300f453d15c23bbbb6a",
	],
	maven_coordinates = "io.grpc:grpc-services:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-services/1.62.2/grpc-services-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_services_1_62_2",
	actual = "io_grpc_grpc_services",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_services_1_62_2_extension",
     src = "@io_grpc_grpc_services_1_62_2//file",
     out = "io/grpc/grpc-services/1.62.2/grpc-services-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_stub",
	jar = "io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2.jar",
	srcjar = "io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-stub:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=fb4ca679a4214143406c65ac4167b2b5e2ee2cab1fc101566bb1c4695d105e36",
	],
	maven_coordinates = "io.grpc:grpc-stub:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_stub_1_62_2",
	actual = "io_grpc_grpc_stub",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_stub_1_62_2_extension",
     src = "@io_grpc_grpc_stub_1_62_2//file",
     out = "io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_util",
	jar = "io/grpc/grpc-util/1.62.2/grpc-util-1.62.2.jar",
	srcjar = "io/grpc/grpc-util/1.62.2/grpc-util-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-util:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-util/1.62.2/grpc-util-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=3c7103e6f3738571e3aeda420fe2a6ac68e354534d8b66f41897b6755b48b735",
	],
	maven_coordinates = "io.grpc:grpc-util:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-util/1.62.2/grpc-util-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_util_1_62_2",
	actual = "io_grpc_grpc_util",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_util_1_62_2_extension",
     src = "@io_grpc_grpc_util_1_62_2//file",
     out = "io/grpc/grpc-util/1.62.2/grpc-util-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_grpc_grpc_xds",
	jar = "io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2.jar",
	srcjar = "io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.grpc:grpc-xds:1.62.2",
		"maven_url=https://repo1.maven.org/maven2/io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=4da41475d04e82c414ceb957e744f5bf99d80c846d5c5eb504c085c563b28b2d",
	],
	maven_coordinates = "io.grpc:grpc-xds:1.62.2",
	maven_url = "https://repo1.maven.org/maven2/io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_grpc_grpc_xds_1_62_2",
	actual = "io_grpc_grpc_xds",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_grpc_grpc_xds_1_62_2_extension",
     src = "@io_grpc_grpc_xds_1_62_2//file",
     out = "io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_buffer",
	jar = "io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final.jar",
	srcjar = "io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final-sources.jar",
	deps = [
		":io_netty_netty_common",
	],
	tags = [
		"maven_coordinates=io.netty:netty-buffer:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=7d94b609fb36afd88304c73922411d08f383f7945aa882b59a15219b5ecbfb76",
	],
	maven_coordinates = "io.netty:netty-buffer:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_buffer_4_1_111_Final",
	actual = "io_netty_netty_buffer",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_buffer_4_1_111_Final_extension",
     src = "@io_netty_netty_buffer_4_1_111_Final//file",
     out = "io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_codec",
	jar = "io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final.jar",
	srcjar = "io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final-sources.jar",
	deps = [
		":io_netty_netty_buffer",
		":io_netty_netty_common",
		":io_netty_netty_transport",
	],
	tags = [
		"maven_coordinates=io.netty:netty-codec:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a63ac713f60ec0b8e2bb8182665d216662d1f474872ec5c368d25f15f544f4bc",
	],
	maven_coordinates = "io.netty:netty-codec:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_codec_4_1_111_Final",
	actual = "io_netty_netty_codec",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_codec_4_1_111_Final_extension",
     src = "@io_netty_netty_codec_4_1_111_Final//file",
     out = "io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_codec_http",
	jar = "io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final.jar",
	srcjar = "io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final-sources.jar",
	deps = [
		":io_netty_netty_buffer",
		":io_netty_netty_codec",
		":io_netty_netty_common",
		":io_netty_netty_handler",
		":io_netty_netty_transport",
	],
	tags = [
		"maven_coordinates=io.netty:netty-codec-http:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=bf50c212caec876bb6fba85b74f18482da2d7824dd0766f1829ca9f93aa01a52",
	],
	maven_coordinates = "io.netty:netty-codec-http:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_codec_http_4_1_111_Final",
	actual = "io_netty_netty_codec_http",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_codec_http_4_1_111_Final_extension",
     src = "@io_netty_netty_codec_http_4_1_111_Final//file",
     out = "io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_codec_http2",
	jar = "io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final.jar",
	srcjar = "io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final-sources.jar",
	deps = [
		":io_netty_netty_buffer",
		":io_netty_netty_codec",
		":io_netty_netty_codec_http",
		":io_netty_netty_common",
		":io_netty_netty_handler",
		":io_netty_netty_transport",
	],
	tags = [
		"maven_coordinates=io.netty:netty-codec-http2:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a9eb9b0627041f4891de92aa896499ae334cdf607dba0dcdfafd3516a7d0ecca",
	],
	maven_coordinates = "io.netty:netty-codec-http2:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_codec_http2_4_1_111_Final",
	actual = "io_netty_netty_codec_http2",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_codec_http2_4_1_111_Final_extension",
     src = "@io_netty_netty_codec_http2_4_1_111_Final//file",
     out = "io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_common",
	jar = "io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final.jar",
	srcjar = "io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.netty:netty-common:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=9ae12e9a89f59ce24fb233851fdd93e3c2dfaeb9fa02c60627cd67fa7561871d",
	],
	maven_coordinates = "io.netty:netty-common:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_common_4_1_111_Final",
	actual = "io_netty_netty_common",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_common_4_1_111_Final_extension",
     src = "@io_netty_netty_common_4_1_111_Final//file",
     out = "io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_handler",
	jar = "io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final.jar",
	srcjar = "io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final-sources.jar",
	deps = [
		":io_netty_netty_buffer",
		":io_netty_netty_codec",
		":io_netty_netty_common",
		":io_netty_netty_resolver",
		":io_netty_netty_transport",
		":io_netty_netty_transport_native_unix_common",
	],
	tags = [
		"maven_coordinates=io.netty:netty-handler:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1a034672ca26c8be6245c8e8641b29785ed2c018617bb2dd7e07be39f7ea71fc",
	],
	maven_coordinates = "io.netty:netty-handler:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_handler_4_1_111_Final",
	actual = "io_netty_netty_handler",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_handler_4_1_111_Final_extension",
     src = "@io_netty_netty_handler_4_1_111_Final//file",
     out = "io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_resolver",
	jar = "io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final.jar",
	srcjar = "io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final-sources.jar",
	deps = [
		":io_netty_netty_common",
	],
	tags = [
		"maven_coordinates=io.netty:netty-resolver:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=78e735746d1f98ca89793176359c97ad5bb6393ec63cd2962f30db43be090e1b",
	],
	maven_coordinates = "io.netty:netty-resolver:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_resolver_4_1_111_Final",
	actual = "io_netty_netty_resolver",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_resolver_4_1_111_Final_extension",
     src = "@io_netty_netty_resolver_4_1_111_Final//file",
     out = "io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_transport",
	jar = "io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final.jar",
	srcjar = "io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final-sources.jar",
	deps = [
		":io_netty_netty_buffer",
		":io_netty_netty_common",
		":io_netty_netty_resolver",
	],
	tags = [
		"maven_coordinates=io.netty:netty-transport:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=49a3cc0b6d342340f0980f047026db74161c19ea2a07753a14b4d22ee0653aa5",
	],
	maven_coordinates = "io.netty:netty-transport:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_transport_4_1_111_Final",
	actual = "io_netty_netty_transport",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_transport_4_1_111_Final_extension",
     src = "@io_netty_netty_transport_4_1_111_Final//file",
     out = "io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_transport_classes_epoll",
	jar = "io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final.jar",
	srcjar = "io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final-sources.jar",
	deps = [
		":io_netty_netty_buffer",
		":io_netty_netty_common",
		":io_netty_netty_transport",
		":io_netty_netty_transport_native_unix_common",
	],
	tags = [
		"maven_coordinates=io.netty:netty-transport-classes-epoll:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d230d1680d1517c7d7b2e25ef6a9a87c22ebbcf264a0da8e807734f15b7c7cae",
	],
	maven_coordinates = "io.netty:netty-transport-classes-epoll:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_transport_classes_epoll_4_1_111_Final",
	actual = "io_netty_netty_transport_classes_epoll",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_transport_classes_epoll_4_1_111_Final_extension",
     src = "@io_netty_netty_transport_classes_epoll_4_1_111_Final//file",
     out = "io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_netty_netty_transport_native_unix_common",
	jar = "io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final.jar",
	srcjar = "io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final-sources.jar",
	deps = [
		":io_netty_netty_buffer",
		":io_netty_netty_common",
		":io_netty_netty_transport",
	],
	tags = [
		"maven_coordinates=io.netty:netty-transport-native-unix-common:4.1.111.Final",
		"maven_url=https://repo1.maven.org/maven2/io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ec4bd4574ee1ec776a1b14139fb69982ddf7f5039a803082074a81f6604ecad2",
	],
	maven_coordinates = "io.netty:netty-transport-native-unix-common:4.1.111.Final",
	maven_url = "https://repo1.maven.org/maven2/io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_netty_netty_transport_native_unix_common_4_1_111_Final",
	actual = "io_netty_netty_transport_native_unix_common",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_netty_netty_transport_native_unix_common_4_1_111_Final_extension",
     src = "@io_netty_netty_transport_native_unix_common_4_1_111_Final//file",
     out = "io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final.jar",
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
	name = "io_opencensus_opencensus_proto",
	jar = "io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0.jar",
	srcjar = "io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.opencensus:opencensus-proto:0.2.0",
		"maven_url=https://repo1.maven.org/maven2/io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=0c192d451e9dd74e98721b27d02f0e2b6bca44b51563b5dabf2e211f7a3ebf13",
	],
	maven_coordinates = "io.opencensus:opencensus-proto:0.2.0",
	maven_url = "https://repo1.maven.org/maven2/io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_opencensus_opencensus_proto_0_2_0",
	actual = "io_opencensus_opencensus_proto",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_opencensus_opencensus_proto_0_2_0_extension",
     src = "@io_opencensus_opencensus_proto_0_2_0//file",
     out = "io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "io_perfmark_perfmark_api",
	jar = "io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0.jar",
	srcjar = "io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=io.perfmark:perfmark-api:0.27.0",
		"maven_url=https://repo1.maven.org/maven2/io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=c7b478503ec524e55df19b424d46d27c8a68aeb801664fadd4f069b71f52d0f6",
	],
	maven_coordinates = "io.perfmark:perfmark-api:0.27.0",
	maven_url = "https://repo1.maven.org/maven2/io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "io_perfmark_perfmark_api_0_27_0",
	actual = "io_perfmark_perfmark_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "io_perfmark_perfmark_api_0_27_0_extension",
     src = "@io_perfmark_perfmark_api_0_27_0//file",
     out = "io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "javax_annotation_javax_annotation_api",
	jar = "javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar",
	srcjar = "javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=javax.annotation:javax.annotation-api:1.3.2",
		"maven_url=https://repo1.maven.org/maven2/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e04ba5195bcd555dc95650f7cc614d151e4bcd52d29a10b8aa2197f3ab89ab9b",
	],
	maven_coordinates = "javax.annotation:javax.annotation-api:1.3.2",
	maven_url = "https://repo1.maven.org/maven2/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "javax_annotation_javax_annotation_api_1_3_2",
	actual = "javax_annotation_javax_annotation_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "javax_annotation_javax_annotation_api_1_3_2_extension",
     src = "@javax_annotation_javax_annotation_api_1_3_2//file",
     out = "javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar",
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
	name = "org_apache_httpcomponents_httpclient",
	jar = "org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar",
	srcjar = "org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14-sources.jar",
	deps = [
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
	name = "org_apache_maven_resolver_maven_resolver_api",
	jar = "org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23.jar",
	srcjar = "org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.apache.maven.resolver:maven-resolver-api:1.9.23",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=cf068ecef4342e47f526b180cda0bf754f871f68cc1003592d436ff8bf459549",
	],
	maven_coordinates = "org.apache.maven.resolver:maven-resolver-api:1.9.23",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_resolver_maven_resolver_api_1_9_23",
	actual = "org_apache_maven_resolver_maven_resolver_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_api_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_api_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_resolver_maven_resolver_connector_basic",
	jar = "org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23.jar",
	srcjar = "org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23-sources.jar",
	deps = [
		":org_apache_maven_resolver_maven_resolver_api",
		":org_apache_maven_resolver_maven_resolver_spi",
		":org_apache_maven_resolver_maven_resolver_util",
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.apache.maven.resolver:maven-resolver-connector-basic:1.9.23",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=0f83a762d3a9bffbb7304e5149fc6ed6f5870d76eb6eb0a54fef697fce1a17ac",
	],
	maven_coordinates = "org.apache.maven.resolver:maven-resolver-connector-basic:1.9.23",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_resolver_maven_resolver_connector_basic_1_9_23",
	actual = "org_apache_maven_resolver_maven_resolver_connector_basic",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_connector_basic_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_connector_basic_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_resolver_maven_resolver_impl",
	jar = "org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23.jar",
	srcjar = "org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23-sources.jar",
	deps = [
		":org_apache_maven_resolver_maven_resolver_api",
		":org_apache_maven_resolver_maven_resolver_named_locks",
		":org_apache_maven_resolver_maven_resolver_spi",
		":org_apache_maven_resolver_maven_resolver_util",
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.apache.maven.resolver:maven-resolver-impl:1.9.23",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=72d56c9cd0324425ba44554490bae11d7e51a9f9d6bd5cf416d9e31c8a7c2870",
	],
	maven_coordinates = "org.apache.maven.resolver:maven-resolver-impl:1.9.23",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_resolver_maven_resolver_impl_1_9_23",
	actual = "org_apache_maven_resolver_maven_resolver_impl",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_impl_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_impl_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_resolver_maven_resolver_named_locks",
	jar = "org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23.jar",
	srcjar = "org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23-sources.jar",
	deps = [
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.apache.maven.resolver:maven-resolver-named-locks:1.9.23",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ec49361d3776c59765b211860e99d47905781db2b5c6ae2025a2eae2da493aed",
	],
	maven_coordinates = "org.apache.maven.resolver:maven-resolver-named-locks:1.9.23",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_resolver_maven_resolver_named_locks_1_9_23",
	actual = "org_apache_maven_resolver_maven_resolver_named_locks",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_named_locks_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_named_locks_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_resolver_maven_resolver_spi",
	jar = "org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23.jar",
	srcjar = "org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23-sources.jar",
	deps = [
		":org_apache_maven_resolver_maven_resolver_api",
	],
	tags = [
		"maven_coordinates=org.apache.maven.resolver:maven-resolver-spi:1.9.23",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=755c96916687d115ff6c4fc8b016ac755a2b18bf0ca80ef45e7f5ad76e6c1b89",
	],
	maven_coordinates = "org.apache.maven.resolver:maven-resolver-spi:1.9.23",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_resolver_maven_resolver_spi_1_9_23",
	actual = "org_apache_maven_resolver_maven_resolver_spi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_spi_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_spi_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_resolver_maven_resolver_transport_file",
	jar = "org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23.jar",
	srcjar = "org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23-sources.jar",
	deps = [
		":org_apache_maven_resolver_maven_resolver_api",
		":org_apache_maven_resolver_maven_resolver_spi",
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.apache.maven.resolver:maven-resolver-transport-file:1.9.23",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=4192de597d89a0eaab87af586ed234eb7126d863dc1fee2ee85df42c3401362d",
	],
	maven_coordinates = "org.apache.maven.resolver:maven-resolver-transport-file:1.9.23",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_resolver_maven_resolver_transport_file_1_9_23",
	actual = "org_apache_maven_resolver_maven_resolver_transport_file",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_transport_file_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_transport_file_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_resolver_maven_resolver_transport_http",
	jar = "org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23.jar",
	srcjar = "org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23-sources.jar",
	deps = [
		":commons_codec_commons_codec",
		":org_apache_httpcomponents_httpclient",
		":org_apache_httpcomponents_httpcore",
		":org_apache_maven_resolver_maven_resolver_api",
		":org_apache_maven_resolver_maven_resolver_spi",
		":org_apache_maven_resolver_maven_resolver_util",
		":org_slf4j_jcl_over_slf4j",
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.apache.maven.resolver:maven-resolver-transport-http:1.9.23",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=7c4d762c4c604db5c8a9eeadd5c98b8f2e03556b853b48aa3346eb8cef67b54d",
	],
	maven_coordinates = "org.apache.maven.resolver:maven-resolver-transport-http:1.9.23",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_resolver_maven_resolver_transport_http_1_9_23",
	actual = "org_apache_maven_resolver_maven_resolver_transport_http",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_transport_http_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_transport_http_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_resolver_maven_resolver_util",
	jar = "org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23.jar",
	srcjar = "org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23-sources.jar",
	deps = [
		":org_apache_maven_resolver_maven_resolver_api",
	],
	tags = [
		"maven_coordinates=org.apache.maven.resolver:maven-resolver-util:1.9.23",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a6b81ce281313cc2adf19e697d59d53a83793d83e33707fc44836c4934f4030f",
	],
	maven_coordinates = "org.apache.maven.resolver:maven-resolver-util:1.9.23",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_resolver_maven_resolver_util_1_9_23",
	actual = "org_apache_maven_resolver_maven_resolver_util",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_resolver_maven_resolver_util_1_9_23_extension",
     src = "@org_apache_maven_resolver_maven_resolver_util_1_9_23//file",
     out = "org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_shared_maven_shared_utils",
	jar = "org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2.jar",
	srcjar = "org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2-sources.jar",
	deps = [
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.apache.maven.shared:maven-shared-utils:3.4.2",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b613357e1bad4dfc1dead801691c9460f9585fe7c6b466bc25186212d7d18487",
	],
	maven_coordinates = "org.apache.maven.shared:maven-shared-utils:3.4.2",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_shared_maven_shared_utils_3_4_2",
	actual = "org_apache_maven_shared_maven_shared_utils",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_shared_maven_shared_utils_3_4_2_extension",
     src = "@org_apache_maven_shared_maven_shared_utils_3_4_2//file",
     out = "org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_artifact",
	jar = "org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10.jar",
	srcjar = "org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10-sources.jar",
	deps = [
		":org_codehaus_plexus_plexus_utils",
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-artifact:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=16d3e7ce7fa5006e8d88d89a699a7c5ebc1f86e853c97cf4ae4b4ba17f2b6811",
	],
	maven_coordinates = "org.apache.maven:maven-artifact:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_artifact_3_9_10",
	actual = "org_apache_maven_maven_artifact",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_artifact_3_9_10_extension",
     src = "@org_apache_maven_maven_artifact_3_9_10//file",
     out = "org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_builder_support",
	jar = "org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10.jar",
	srcjar = "org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-builder-support:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f3ac94e186f77a5be579db9bd3daa3e34cd5e52b9e11c0cb5e23966cc083e168",
	],
	maven_coordinates = "org.apache.maven:maven-builder-support:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_builder_support_3_9_10",
	actual = "org_apache_maven_maven_builder_support",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_builder_support_3_9_10_extension",
     src = "@org_apache_maven_maven_builder_support_3_9_10//file",
     out = "org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_core",
	jar = "org/apache/maven/maven-core/3.9.10/maven-core-3.9.10.jar",
	srcjar = "org/apache/maven/maven-core/3.9.10/maven-core-3.9.10-sources.jar",
	deps = [
		":com_google_guava_failureaccess",
		":com_google_guava_guava",
		":com_google_inject_guice_classes",
		":javax_inject_javax_inject",
		":org_apache_maven_resolver_maven_resolver_api",
		":org_apache_maven_resolver_maven_resolver_impl",
		":org_apache_maven_resolver_maven_resolver_spi",
		":org_apache_maven_resolver_maven_resolver_util",
		":org_apache_maven_shared_maven_shared_utils",
		":org_apache_maven_maven_artifact",
		":org_apache_maven_maven_builder_support",
		":org_apache_maven_maven_model",
		":org_apache_maven_maven_model_builder",
		":org_apache_maven_maven_plugin_api",
		":org_apache_maven_maven_repository_metadata",
		":org_apache_maven_maven_resolver_provider",
		":org_apache_maven_maven_settings",
		":org_apache_maven_maven_settings_builder",
		":org_codehaus_plexus_plexus_classworlds",
		":org_codehaus_plexus_plexus_component_annotations",
		":org_codehaus_plexus_plexus_interpolation",
		":org_codehaus_plexus_plexus_utils",
		":org_eclipse_sisu_org_eclipse_sisu_inject",
		":org_eclipse_sisu_org_eclipse_sisu_plexus",
		":org_ow2_asm_asm",
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-core:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-core/3.9.10/maven-core-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1b562f914ff546c69c16c98e946d3504dbbf1cbff34aa90f84e23188b8219eaf",
	],
	maven_coordinates = "org.apache.maven:maven-core:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-core/3.9.10/maven-core-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_core_3_9_10",
	actual = "org_apache_maven_maven_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_core_3_9_10_extension",
     src = "@org_apache_maven_maven_core_3_9_10//file",
     out = "org/apache/maven/maven-core/3.9.10/maven-core-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_model",
	jar = "org/apache/maven/maven-model/3.9.10/maven-model-3.9.10.jar",
	srcjar = "org/apache/maven/maven-model/3.9.10/maven-model-3.9.10-sources.jar",
	deps = [
		":org_codehaus_plexus_plexus_utils",
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-model:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-model/3.9.10/maven-model-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d99037fb97de2de24382f3f7ec60a4244b9e960a8adb119b17cb26dcf5512469",
	],
	maven_coordinates = "org.apache.maven:maven-model:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-model/3.9.10/maven-model-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_model_3_9_10",
	actual = "org_apache_maven_maven_model",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_model_3_9_10_extension",
     src = "@org_apache_maven_maven_model_3_9_10//file",
     out = "org/apache/maven/maven-model/3.9.10/maven-model-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_model_builder",
	jar = "org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10.jar",
	srcjar = "org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10-sources.jar",
	deps = [
		":javax_inject_javax_inject",
		":org_apache_maven_maven_artifact",
		":org_apache_maven_maven_builder_support",
		":org_apache_maven_maven_model",
		":org_codehaus_plexus_plexus_interpolation",
		":org_eclipse_sisu_org_eclipse_sisu_inject",
		":org_ow2_asm_asm",
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-model-builder:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6a015c5285b6a904e66a3b688e9c7caa5effa26af8de86a1efbf173ced5e4896",
	],
	maven_coordinates = "org.apache.maven:maven-model-builder:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_model_builder_3_9_10",
	actual = "org_apache_maven_maven_model_builder",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_model_builder_3_9_10_extension",
     src = "@org_apache_maven_maven_model_builder_3_9_10//file",
     out = "org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_plugin_api",
	jar = "org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10.jar",
	srcjar = "org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10-sources.jar",
	deps = [
		":org_apache_maven_maven_artifact",
		":org_apache_maven_maven_model",
		":org_codehaus_plexus_plexus_classworlds",
		":org_codehaus_plexus_plexus_utils",
		":org_eclipse_sisu_org_eclipse_sisu_plexus",
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-plugin-api:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=36c5b4f1eb003b616d12cd19e778d639d3514fbc778b200c4e9d3ca22d7e988a",
	],
	maven_coordinates = "org.apache.maven:maven-plugin-api:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_plugin_api_3_9_10",
	actual = "org_apache_maven_maven_plugin_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_plugin_api_3_9_10_extension",
     src = "@org_apache_maven_maven_plugin_api_3_9_10//file",
     out = "org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_repository_metadata",
	jar = "org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10.jar",
	srcjar = "org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10-sources.jar",
	deps = [
		":org_codehaus_plexus_plexus_utils",
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-repository-metadata:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e968abc00b3ce52af8aa153a1ae8f6178c69cdac5b1ce5b45b79a8af0815ab23",
	],
	maven_coordinates = "org.apache.maven:maven-repository-metadata:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_repository_metadata_3_9_10",
	actual = "org_apache_maven_maven_repository_metadata",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_repository_metadata_3_9_10_extension",
     src = "@org_apache_maven_maven_repository_metadata_3_9_10//file",
     out = "org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_resolver_provider",
	jar = "org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10.jar",
	srcjar = "org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10-sources.jar",
	deps = [
		":javax_inject_javax_inject",
		":org_apache_maven_resolver_maven_resolver_api",
		":org_apache_maven_resolver_maven_resolver_impl",
		":org_apache_maven_resolver_maven_resolver_spi",
		":org_apache_maven_resolver_maven_resolver_util",
		":org_apache_maven_maven_model",
		":org_apache_maven_maven_model_builder",
		":org_apache_maven_maven_repository_metadata",
		":org_codehaus_plexus_plexus_utils",
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-resolver-provider:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6d7b6fc2ed0673b144d92acd105d457105f14d3b7a51d7531ec05b20ff9798a8",
	],
	maven_coordinates = "org.apache.maven:maven-resolver-provider:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_resolver_provider_3_9_10",
	actual = "org_apache_maven_maven_resolver_provider",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_resolver_provider_3_9_10_extension",
     src = "@org_apache_maven_maven_resolver_provider_3_9_10//file",
     out = "org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_settings",
	jar = "org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10.jar",
	srcjar = "org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10-sources.jar",
	deps = [
		":org_codehaus_plexus_plexus_utils",
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-settings:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=57e97124c757956d24fbd29733f4779aa5a305fff61c05ca6628203362288e91",
	],
	maven_coordinates = "org.apache.maven:maven-settings:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_settings_3_9_10",
	actual = "org_apache_maven_maven_settings",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_settings_3_9_10_extension",
     src = "@org_apache_maven_maven_settings_3_9_10//file",
     out = "org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_apache_maven_maven_settings_builder",
	jar = "org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10.jar",
	srcjar = "org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10-sources.jar",
	deps = [
		":javax_inject_javax_inject",
		":org_apache_maven_maven_builder_support",
		":org_apache_maven_maven_settings",
		":org_codehaus_plexus_plexus_interpolation",
		":org_codehaus_plexus_plexus_sec_dispatcher",
		":org_codehaus_plexus_plexus_utils",
	],
	tags = [
		"maven_coordinates=org.apache.maven:maven-settings-builder:3.9.10",
		"maven_url=https://repo1.maven.org/maven2/org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d52880da6a25d9f4ff23a1961ca7bdbc3b07b30e8af0926841d293fe9eddb63f",
	],
	maven_coordinates = "org.apache.maven:maven-settings-builder:3.9.10",
	maven_url = "https://repo1.maven.org/maven2/org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_apache_maven_maven_settings_builder_3_9_10",
	actual = "org_apache_maven_maven_settings_builder",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_apache_maven_maven_settings_builder_3_9_10_extension",
     src = "@org_apache_maven_maven_settings_builder_3_9_10//file",
     out = "org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_bouncycastle_bcpg_jdk15on",
	jar = "org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68.jar",
	srcjar = "org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68-sources.jar",
	deps = [
		":org_bouncycastle_bcprov_jdk15on",
	],
	tags = [
		"maven_coordinates=org.bouncycastle:bcpg-jdk15on:1.68",
		"maven_url=https://repo1.maven.org/maven2/org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2251d9c9faa0ee534ab159a6dac1193a69b8a831f0a0e593dc79e34e7e256a4f",
	],
	maven_coordinates = "org.bouncycastle:bcpg-jdk15on:1.68",
	maven_url = "https://repo1.maven.org/maven2/org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_bouncycastle_bcpg_jdk15on_1_68",
	actual = "org_bouncycastle_bcpg_jdk15on",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_bouncycastle_bcpg_jdk15on_1_68_extension",
     src = "@org_bouncycastle_bcpg_jdk15on_1_68//file",
     out = "org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_bouncycastle_bcprov_jdk15on",
	jar = "org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68.jar",
	srcjar = "org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.bouncycastle:bcprov-jdk15on:1.68",
		"maven_url=https://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f732a46c8de7e2232f2007c682a21d1f4cc8a8a0149b6b7bd6aa1afdc65a0f8d",
	],
	maven_coordinates = "org.bouncycastle:bcprov-jdk15on:1.68",
	maven_url = "https://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_bouncycastle_bcprov_jdk15on_1_68",
	actual = "org_bouncycastle_bcprov_jdk15on",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_bouncycastle_bcprov_jdk15on_1_68_extension",
     src = "@org_bouncycastle_bcprov_jdk15on_1_68//file",
     out = "org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_checkerframework_checker_qual",
	jar = "org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0.jar",
	srcjar = "org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.checkerframework:checker-qual:3.44.0",
		"maven_url=https://repo1.maven.org/maven2/org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=30ed439602b6c2d4aaea2a85e58e388556f0cc7ae68ed29649bc1cd0c0102cd9",
	],
	maven_coordinates = "org.checkerframework:checker-qual:3.44.0",
	maven_url = "https://repo1.maven.org/maven2/org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_checkerframework_checker_qual_3_44_0",
	actual = "org_checkerframework_checker_qual",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_checkerframework_checker_qual_3_44_0_extension",
     src = "@org_checkerframework_checker_qual_3_44_0//file",
     out = "org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_codehaus_mojo_animal_sniffer_annotations",
	jar = "org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23.jar",
	srcjar = "org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.codehaus.mojo:animal-sniffer-annotations:1.23",
		"maven_url=https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=9ffe526bf43a6348e9d8b33b9cd6f580a7f5eed0cf055913007eda263de974d0",
	],
	maven_coordinates = "org.codehaus.mojo:animal-sniffer-annotations:1.23",
	maven_url = "https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_codehaus_mojo_animal_sniffer_annotations_1_23",
	actual = "org_codehaus_mojo_animal_sniffer_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_codehaus_mojo_animal_sniffer_annotations_1_23_extension",
     src = "@org_codehaus_mojo_animal_sniffer_annotations_1_23//file",
     out = "org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_codehaus_plexus_plexus_cipher",
	jar = "org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0.jar",
	srcjar = "org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0-sources.jar",
	deps = [
		":javax_inject_javax_inject",
	],
	tags = [
		"maven_coordinates=org.codehaus.plexus:plexus-cipher:2.1.0",
		"maven_url=https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ae34b6dcf0641a8bf5592244aeeeea49b6aa457f1889a68dd98a00a08cf1f38c",
	],
	maven_coordinates = "org.codehaus.plexus:plexus-cipher:2.1.0",
	maven_url = "https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_codehaus_plexus_plexus_cipher_2_1_0",
	actual = "org_codehaus_plexus_plexus_cipher",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_codehaus_plexus_plexus_cipher_2_1_0_extension",
     src = "@org_codehaus_plexus_plexus_cipher_2_1_0//file",
     out = "org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_codehaus_plexus_plexus_classworlds",
	jar = "org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0.jar",
	srcjar = "org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.codehaus.plexus:plexus-classworlds:2.9.0",
		"maven_url=https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1ad3292cd563381e3fd632f3fded1988f9e9b2be7a9f3db63ff4c4cedba13fa5",
	],
	maven_coordinates = "org.codehaus.plexus:plexus-classworlds:2.9.0",
	maven_url = "https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_codehaus_plexus_plexus_classworlds_2_9_0",
	actual = "org_codehaus_plexus_plexus_classworlds",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_codehaus_plexus_plexus_classworlds_2_9_0_extension",
     src = "@org_codehaus_plexus_plexus_classworlds_2_9_0//file",
     out = "org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_codehaus_plexus_plexus_component_annotations",
	jar = "org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0.jar",
	srcjar = "org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.codehaus.plexus:plexus-component-annotations:2.2.0",
		"maven_url=https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=50edb93c73786e62822b4fe1336e22880fdf147191373cf5c911370e16748fcf",
	],
	maven_coordinates = "org.codehaus.plexus:plexus-component-annotations:2.2.0",
	maven_url = "https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_codehaus_plexus_plexus_component_annotations_2_2_0",
	actual = "org_codehaus_plexus_plexus_component_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_codehaus_plexus_plexus_component_annotations_2_2_0_extension",
     src = "@org_codehaus_plexus_plexus_component_annotations_2_2_0//file",
     out = "org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_codehaus_plexus_plexus_interpolation",
	jar = "org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28.jar",
	srcjar = "org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.codehaus.plexus:plexus-interpolation:1.28",
		"maven_url=https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ab2a8715570438a2e4164d85ad3e8d489eabc38ea5093c2eb8ab7f58403535b5",
	],
	maven_coordinates = "org.codehaus.plexus:plexus-interpolation:1.28",
	maven_url = "https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_codehaus_plexus_plexus_interpolation_1_28",
	actual = "org_codehaus_plexus_plexus_interpolation",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_codehaus_plexus_plexus_interpolation_1_28_extension",
     src = "@org_codehaus_plexus_plexus_interpolation_1_28//file",
     out = "org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_codehaus_plexus_plexus_sec_dispatcher",
	jar = "org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0.jar",
	srcjar = "org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0-sources.jar",
	deps = [
		":javax_inject_javax_inject",
		":org_codehaus_plexus_plexus_cipher",
		":org_codehaus_plexus_plexus_utils",
	],
	tags = [
		"maven_coordinates=org.codehaus.plexus:plexus-sec-dispatcher:2.0",
		"maven_url=https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=873139960c4c780176dda580b003a2c4bf82188bdce5bb99234e224ef7acfceb",
	],
	maven_coordinates = "org.codehaus.plexus:plexus-sec-dispatcher:2.0",
	maven_url = "https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_codehaus_plexus_plexus_sec_dispatcher_2_0",
	actual = "org_codehaus_plexus_plexus_sec_dispatcher",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_codehaus_plexus_plexus_sec_dispatcher_2_0_extension",
     src = "@org_codehaus_plexus_plexus_sec_dispatcher_2_0//file",
     out = "org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_codehaus_plexus_plexus_utils",
	jar = "org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0.jar",
	srcjar = "org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.codehaus.plexus:plexus-utils:3.6.0",
		"maven_url=https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=27ef130e32c236090e408fb5498d94cb9ea26d14070fb1c8985d607b62d098d1",
	],
	maven_coordinates = "org.codehaus.plexus:plexus-utils:3.6.0",
	maven_url = "https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_codehaus_plexus_plexus_utils_3_6_0",
	actual = "org_codehaus_plexus_plexus_utils",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_codehaus_plexus_plexus_utils_3_6_0_extension",
     src = "@org_codehaus_plexus_plexus_utils_3_6_0//file",
     out = "org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_conscrypt_conscrypt_openjdk_uber",
	jar = "org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2.jar",
	srcjar = "org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.conscrypt:conscrypt-openjdk-uber:2.5.2",
		"maven_url=https://repo1.maven.org/maven2/org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=eaf537d98e033d0f0451cd1b8cc74e02d7b55ec882da63c88060d806ba89c348",
	],
	maven_coordinates = "org.conscrypt:conscrypt-openjdk-uber:2.5.2",
	maven_url = "https://repo1.maven.org/maven2/org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_conscrypt_conscrypt_openjdk_uber_2_5_2",
	actual = "org_conscrypt_conscrypt_openjdk_uber",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_conscrypt_conscrypt_openjdk_uber_2_5_2_extension",
     src = "@org_conscrypt_conscrypt_openjdk_uber_2_5_2//file",
     out = "org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_eclipse_sisu_org_eclipse_sisu_inject",
	jar = "org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4.jar",
	srcjar = "org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4-sources.jar",
	deps = [
		":org_ow2_asm_asm",
	],
	tags = [
		"maven_coordinates=org.eclipse.sisu:org.eclipse.sisu.inject:0.9.0.M4",
		"maven_url=https://repo1.maven.org/maven2/org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1cbd7a965a5e2a9ea823bab311962a4e5aa5c240705bdbad5a52b40ffdfa1004",
	],
	maven_coordinates = "org.eclipse.sisu:org.eclipse.sisu.inject:0.9.0.M4",
	maven_url = "https://repo1.maven.org/maven2/org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_eclipse_sisu_org_eclipse_sisu_inject_0_9_0_M4",
	actual = "org_eclipse_sisu_org_eclipse_sisu_inject",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_eclipse_sisu_org_eclipse_sisu_inject_0_9_0_M4_extension",
     src = "@org_eclipse_sisu_org_eclipse_sisu_inject_0_9_0_M4//file",
     out = "org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_eclipse_sisu_org_eclipse_sisu_plexus",
	jar = "org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4.jar",
	srcjar = "org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4-sources.jar",
	deps = [
		":org_codehaus_plexus_plexus_classworlds",
		":org_codehaus_plexus_plexus_component_annotations",
		":org_codehaus_plexus_plexus_utils",
	],
	tags = [
		"maven_coordinates=org.eclipse.sisu:org.eclipse.sisu.plexus:0.9.0.M4",
		"maven_url=https://repo1.maven.org/maven2/org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b90579bc652eac7331436e0a25533fce14130b9c6e015f2dd3a3d4bb07e942b7",
	],
	maven_coordinates = "org.eclipse.sisu:org.eclipse.sisu.plexus:0.9.0.M4",
	maven_url = "https://repo1.maven.org/maven2/org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_eclipse_sisu_org_eclipse_sisu_plexus_0_9_0_M4",
	actual = "org_eclipse_sisu_org_eclipse_sisu_plexus",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_eclipse_sisu_org_eclipse_sisu_plexus_0_9_0_M4_extension",
     src = "@org_eclipse_sisu_org_eclipse_sisu_plexus_0_9_0_M4//file",
     out = "org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_fusesource_jansi_jansi",
	jar = "org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1.jar",
	srcjar = "org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.fusesource.jansi:jansi:2.4.1",
		"maven_url=https://repo1.maven.org/maven2/org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2e5e775a9dc58ffa6bbd6aa6f099d62f8b62dcdeb4c3c3bbbe5cf2301bc2dcc1",
	],
	maven_coordinates = "org.fusesource.jansi:jansi:2.4.1",
	maven_url = "https://repo1.maven.org/maven2/org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_fusesource_jansi_jansi_2_4_1",
	actual = "org_fusesource_jansi_jansi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_fusesource_jansi_jansi_2_4_1_extension",
     src = "@org_fusesource_jansi_jansi_2_4_1//file",
     out = "org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_gradle_gradle_tooling_api",
	jar = "org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13.jar",
	srcjar = "org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13-sources.jar",
	deps = [
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.gradle:gradle-tooling-api:8.13",
		"maven_url=https://repo1.maven.org/maven2/org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=de8062b7aaf9d2b35df1c62dbb00978c5b41c05c0c30e5f2fd9cf59004f0d5dc",
	],
	maven_coordinates = "org.gradle:gradle-tooling-api:8.13",
	maven_url = "https://repo1.maven.org/maven2/org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_gradle_gradle_tooling_api_8_13",
	actual = "org_gradle_gradle_tooling_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_gradle_gradle_tooling_api_8_13_extension",
     src = "@org_gradle_gradle_tooling_api_8_13//file",
     out = "org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13.jar",
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
	name = "org_ow2_asm_asm",
	jar = "org/ow2/asm/asm/9.8/asm-9.8.jar",
	srcjar = "org/ow2/asm/asm/9.8/asm-9.8-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.ow2.asm:asm:9.8",
		"maven_url=https://repo1.maven.org/maven2/org/ow2/asm/asm/9.8/asm-9.8.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=876eab6a83daecad5ca67eb9fcabb063c97b5aeb8cf1fca7a989ecde17522051",
	],
	maven_coordinates = "org.ow2.asm:asm:9.8",
	maven_url = "https://repo1.maven.org/maven2/org/ow2/asm/asm/9.8/asm-9.8.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_ow2_asm_asm_9_8",
	actual = "org_ow2_asm_asm",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_ow2_asm_asm_9_8_extension",
     src = "@org_ow2_asm_asm_9_8//file",
     out = "org/ow2/asm/asm/9.8/asm-9.8.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_reactivestreams_reactive_streams",
	jar = "org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar",
	srcjar = "org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.reactivestreams:reactive-streams:1.0.4",
		"maven_url=https://repo1.maven.org/maven2/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f75ca597789b3dac58f61857b9ac2e1034a68fa672db35055a8fb4509e325f28",
	],
	maven_coordinates = "org.reactivestreams:reactive-streams:1.0.4",
	maven_url = "https://repo1.maven.org/maven2/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_reactivestreams_reactive_streams_1_0_4",
	actual = "org_reactivestreams_reactive_streams",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_reactivestreams_reactive_streams_1_0_4_extension",
     src = "@org_reactivestreams_reactive_streams_1_0_4//file",
     out = "org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_slf4j_jcl_over_slf4j",
	jar = "org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36.jar",
	srcjar = "org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36-sources.jar",
	deps = [
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.slf4j:jcl-over-slf4j:1.7.36",
		"maven_url=https://repo1.maven.org/maven2/org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=ab57ca8fd223772c17365d121f59e94ecbf0ae59d08c03a3cb5b81071c019195",
	],
	maven_coordinates = "org.slf4j:jcl-over-slf4j:1.7.36",
	maven_url = "https://repo1.maven.org/maven2/org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_slf4j_jcl_over_slf4j_1_7_36",
	actual = "org_slf4j_jcl_over_slf4j",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_slf4j_jcl_over_slf4j_1_7_36_extension",
     src = "@org_slf4j_jcl_over_slf4j_1_7_36//file",
     out = "org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_slf4j_jul_to_slf4j",
	jar = "org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12.jar",
	srcjar = "org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12-sources.jar",
	deps = [
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.slf4j:jul-to-slf4j:2.0.12",
		"maven_url=https://repo1.maven.org/maven2/org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=84f02864cab866ffb196ed2022b1b8da682ea6fb3d4a161069429e8391ee2979",
	],
	maven_coordinates = "org.slf4j:jul-to-slf4j:2.0.12",
	maven_url = "https://repo1.maven.org/maven2/org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_slf4j_jul_to_slf4j_2_0_12",
	actual = "org_slf4j_jul_to_slf4j",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_slf4j_jul_to_slf4j_2_0_12_extension",
     src = "@org_slf4j_jul_to_slf4j_2_0_12//file",
     out = "org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_slf4j_log4j_over_slf4j",
	jar = "org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12.jar",
	srcjar = "org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12-sources.jar",
	deps = [
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.slf4j:log4j-over-slf4j:2.0.12",
		"maven_url=https://repo1.maven.org/maven2/org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6271f07eeab8f14321dcdfed8d1de9458198eaa3320174923d1ef3ace9048efa",
	],
	maven_coordinates = "org.slf4j:log4j-over-slf4j:2.0.12",
	maven_url = "https://repo1.maven.org/maven2/org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_slf4j_log4j_over_slf4j_2_0_12",
	actual = "org_slf4j_log4j_over_slf4j",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_slf4j_log4j_over_slf4j_2_0_12_extension",
     src = "@org_slf4j_log4j_over_slf4j_2_0_12//file",
     out = "org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_slf4j_slf4j_api",
	jar = "org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12.jar",
	srcjar = "org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.slf4j:slf4j-api:2.0.12",
		"maven_url=https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a79502b8abdfbd722846a27691226a4088682d6d35654f9b80e2a9ccacf7ed47",
	],
	maven_coordinates = "org.slf4j:slf4j-api:2.0.12",
	maven_url = "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_slf4j_slf4j_api_2_0_12",
	actual = "org_slf4j_slf4j_api",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_slf4j_slf4j_api_2_0_12_extension",
     src = "@org_slf4j_slf4j_api_2_0_12//file",
     out = "org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_slf4j_slf4j_simple",
	jar = "org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12.jar",
	srcjar = "org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12-sources.jar",
	deps = [
		":org_slf4j_slf4j_api",
	],
	tags = [
		"maven_coordinates=org.slf4j:slf4j-simple:2.0.12",
		"maven_url=https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=4cd8f3d6236044600e7054da7c124c6d2e9f45eb43c77d4e9b093fe1095edc85",
	],
	maven_coordinates = "org.slf4j:slf4j-simple:2.0.12",
	maven_url = "https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_slf4j_slf4j_simple_2_0_12",
	actual = "org_slf4j_slf4j_simple",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_slf4j_slf4j_simple_2_0_12_extension",
     src = "@org_slf4j_slf4j_simple_2_0_12//file",
     out = "org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "org_threeten_threetenbp",
	jar = "org/threeten/threetenbp/1.6.9/threetenbp-1.6.9.jar",
	srcjar = "org/threeten/threetenbp/1.6.9/threetenbp-1.6.9-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.threeten:threetenbp:1.6.9",
		"maven_url=https://repo1.maven.org/maven2/org/threeten/threetenbp/1.6.9/threetenbp-1.6.9.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=83fd82658f19984ecb7ca4d9ed96f0cd6a1f07c06d0398b20a3aa2d85929ef37",
	],
	maven_coordinates = "org.threeten:threetenbp:1.6.9",
	maven_url = "https://repo1.maven.org/maven2/org/threeten/threetenbp/1.6.9/threetenbp-1.6.9.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_threeten_threetenbp_1_6_9",
	actual = "org_threeten_threetenbp",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_threeten_threetenbp_1_6_9_extension",
     src = "@org_threeten_threetenbp_1_6_9//file",
     out = "org/threeten/threetenbp/1.6.9/threetenbp-1.6.9.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_annotations",
	jar = "software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12.jar",
	srcjar = "software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:annotations:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=cde91f502b700ca50221c3855ac0f80304b50ea882dabffa25505d756f64d041",
	],
	maven_coordinates = "software.amazon.awssdk:annotations:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_annotations_2_26_12",
	actual = "software_amazon_awssdk_annotations",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_annotations_2_26_12_extension",
     src = "@software_amazon_awssdk_annotations_2_26_12//file",
     out = "software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_apache_client",
	jar = "software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12.jar",
	srcjar = "software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12-sources.jar",
	deps = [
		":commons_codec_commons_codec",
		":org_apache_httpcomponents_httpclient",
		":org_apache_httpcomponents_httpcore",
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_metrics_spi",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:apache-client:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=637f8050259ef49f8cfe96fd7b3f46c0989211896c901a71ccde662d74334c3c",
	],
	maven_coordinates = "software.amazon.awssdk:apache-client:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_apache_client_2_26_12",
	actual = "software_amazon_awssdk_apache_client",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_apache_client_2_26_12_extension",
     src = "@software_amazon_awssdk_apache_client_2_26_12//file",
     out = "software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_arns",
	jar = "software/amazon/awssdk/arns/2.26.12/arns-2.26.12.jar",
	srcjar = "software/amazon/awssdk/arns/2.26.12/arns-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:arns:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/arns/2.26.12/arns-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d4ae0587d300acba18d1625b50dcdef5c8da082c23d4f36b44ba3bc06db50140",
	],
	maven_coordinates = "software.amazon.awssdk:arns:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/arns/2.26.12/arns-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_arns_2_26_12",
	actual = "software_amazon_awssdk_arns",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_arns_2_26_12_extension",
     src = "@software_amazon_awssdk_arns_2_26_12//file",
     out = "software/amazon/awssdk/arns/2.26.12/arns-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_auth",
	jar = "software/amazon/awssdk/auth/2.26.12/auth-2.26.12.jar",
	srcjar = "software/amazon/awssdk/auth/2.26.12/auth-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_http_auth",
		":software_amazon_awssdk_http_auth_aws",
		":software_amazon_awssdk_http_auth_spi",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_identity_spi",
		":software_amazon_awssdk_json_utils",
		":software_amazon_awssdk_profiles",
		":software_amazon_awssdk_regions",
		":software_amazon_awssdk_sdk_core",
		":software_amazon_awssdk_utils",
		":software_amazon_eventstream_eventstream",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:auth:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/auth/2.26.12/auth-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=35b54896c6d1f203258dd3fc584efde08df650ee37df2f99c6d203f6857ce8ad",
	],
	maven_coordinates = "software.amazon.awssdk:auth:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/auth/2.26.12/auth-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_auth_2_26_12",
	actual = "software_amazon_awssdk_auth",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_auth_2_26_12_extension",
     src = "@software_amazon_awssdk_auth_2_26_12//file",
     out = "software/amazon/awssdk/auth/2.26.12/auth-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_aws_core",
	jar = "software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12.jar",
	srcjar = "software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_auth",
		":software_amazon_awssdk_endpoints_spi",
		":software_amazon_awssdk_http_auth",
		":software_amazon_awssdk_http_auth_spi",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_identity_spi",
		":software_amazon_awssdk_metrics_spi",
		":software_amazon_awssdk_profiles",
		":software_amazon_awssdk_regions",
		":software_amazon_awssdk_retries",
		":software_amazon_awssdk_retries_spi",
		":software_amazon_awssdk_sdk_core",
		":software_amazon_awssdk_utils",
		":software_amazon_eventstream_eventstream",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:aws-core:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=fa0575f49bc302fc3651016b5fd25e71f2a01884f3c0c1d476deb9672557a054",
	],
	maven_coordinates = "software.amazon.awssdk:aws-core:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_aws_core_2_26_12",
	actual = "software_amazon_awssdk_aws_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_aws_core_2_26_12_extension",
     src = "@software_amazon_awssdk_aws_core_2_26_12//file",
     out = "software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_aws_query_protocol",
	jar = "software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12.jar",
	srcjar = "software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_aws_core",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_protocol_core",
		":software_amazon_awssdk_sdk_core",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:aws-query-protocol:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1fdbf5f3edbd73dd7f2f10fbd8194527994c93e2f64fe757f212ab8631fa5a63",
	],
	maven_coordinates = "software.amazon.awssdk:aws-query-protocol:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_aws_query_protocol_2_26_12",
	actual = "software_amazon_awssdk_aws_query_protocol",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_aws_query_protocol_2_26_12_extension",
     src = "@software_amazon_awssdk_aws_query_protocol_2_26_12//file",
     out = "software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_aws_xml_protocol",
	jar = "software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12.jar",
	srcjar = "software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_aws_core",
		":software_amazon_awssdk_aws_query_protocol",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_protocol_core",
		":software_amazon_awssdk_sdk_core",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:aws-xml-protocol:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f89843e424f5ee36c5a53bf2e249606ac9e57c177319910aa50e0e419dfc2c7d",
	],
	maven_coordinates = "software.amazon.awssdk:aws-xml-protocol:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_aws_xml_protocol_2_26_12",
	actual = "software_amazon_awssdk_aws_xml_protocol",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_aws_xml_protocol_2_26_12_extension",
     src = "@software_amazon_awssdk_aws_xml_protocol_2_26_12//file",
     out = "software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_checksums",
	jar = "software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12.jar",
	srcjar = "software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_checksums_spi",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:checksums:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=300a62cea9ea191aaf10ab04572c614bcd80bffe7267bd3a6f0b06fe8a392be8",
	],
	maven_coordinates = "software.amazon.awssdk:checksums:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_checksums_2_26_12",
	actual = "software_amazon_awssdk_checksums",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_checksums_2_26_12_extension",
     src = "@software_amazon_awssdk_checksums_2_26_12//file",
     out = "software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_checksums_spi",
	jar = "software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12.jar",
	srcjar = "software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:checksums-spi:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=e8c27652edfb4942044677cbdb9d94f40255c67e653f5319f1f84d366ccc1044",
	],
	maven_coordinates = "software.amazon.awssdk:checksums-spi:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_checksums_spi_2_26_12",
	actual = "software_amazon_awssdk_checksums_spi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_checksums_spi_2_26_12_extension",
     src = "@software_amazon_awssdk_checksums_spi_2_26_12//file",
     out = "software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_crt_core",
	jar = "software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12.jar",
	srcjar = "software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:crt-core:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=23b2947e8a6ceb6372802ba481bfc5bd5fa0c1647ef9f577596ead6cf2d45893",
	],
	maven_coordinates = "software.amazon.awssdk:crt-core:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_crt_core_2_26_12",
	actual = "software_amazon_awssdk_crt_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_crt_core_2_26_12_extension",
     src = "@software_amazon_awssdk_crt_core_2_26_12//file",
     out = "software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_endpoints_spi",
	jar = "software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12.jar",
	srcjar = "software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:endpoints-spi:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=5ec4d35d564201d90d0d952f2e32857a00cf2e8dd0d49274d2b46075172d6935",
	],
	maven_coordinates = "software.amazon.awssdk:endpoints-spi:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_endpoints_spi_2_26_12",
	actual = "software_amazon_awssdk_endpoints_spi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_endpoints_spi_2_26_12_extension",
     src = "@software_amazon_awssdk_endpoints_spi_2_26_12//file",
     out = "software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_http_auth",
	jar = "software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12.jar",
	srcjar = "software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_http_auth_spi",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_identity_spi",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:http-auth:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=99b42b8e66de459d9a40d6bd2ca98624ca5d8146b4834cb50159c986d02f6eb9",
	],
	maven_coordinates = "software.amazon.awssdk:http-auth:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_http_auth_2_26_12",
	actual = "software_amazon_awssdk_http_auth",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_http_auth_2_26_12_extension",
     src = "@software_amazon_awssdk_http_auth_2_26_12//file",
     out = "software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_http_auth_aws",
	jar = "software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12.jar",
	srcjar = "software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_checksums",
		":software_amazon_awssdk_checksums_spi",
		":software_amazon_awssdk_http_auth_spi",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_identity_spi",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:http-auth-aws:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1d8552028084b79594a8232f3414746f80704c72d9cf99aab418050a91694da7",
	],
	maven_coordinates = "software.amazon.awssdk:http-auth-aws:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_http_auth_aws_2_26_12",
	actual = "software_amazon_awssdk_http_auth_aws",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_http_auth_aws_2_26_12_extension",
     src = "@software_amazon_awssdk_http_auth_aws_2_26_12//file",
     out = "software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_http_auth_spi",
	jar = "software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12.jar",
	srcjar = "software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12-sources.jar",
	deps = [
		":org_reactivestreams_reactive_streams",
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_identity_spi",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:http-auth-spi:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=a6dbb5d377302c6c9d2f520a6f07be34f9bd12e0f20571e05ca60773d51a79be",
	],
	maven_coordinates = "software.amazon.awssdk:http-auth-spi:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_http_auth_spi_2_26_12",
	actual = "software_amazon_awssdk_http_auth_spi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_http_auth_spi_2_26_12_extension",
     src = "@software_amazon_awssdk_http_auth_spi_2_26_12//file",
     out = "software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_http_client_spi",
	jar = "software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12.jar",
	srcjar = "software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12-sources.jar",
	deps = [
		":org_reactivestreams_reactive_streams",
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_metrics_spi",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:http-client-spi:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6e1a74c3b865c1942fa857ca190ca48d6f487a2c765e1ca25c5ec4cab6e0c105",
	],
	maven_coordinates = "software.amazon.awssdk:http-client-spi:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_http_client_spi_2_26_12",
	actual = "software_amazon_awssdk_http_client_spi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_http_client_spi_2_26_12_extension",
     src = "@software_amazon_awssdk_http_client_spi_2_26_12//file",
     out = "software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_identity_spi",
	jar = "software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12.jar",
	srcjar = "software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:identity-spi:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=aa3a5968b76b5deb0b068aa99e4b56c47911af20defb4e3fa3cbab3323b11f18",
	],
	maven_coordinates = "software.amazon.awssdk:identity-spi:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_identity_spi_2_26_12",
	actual = "software_amazon_awssdk_identity_spi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_identity_spi_2_26_12_extension",
     src = "@software_amazon_awssdk_identity_spi_2_26_12//file",
     out = "software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_json_utils",
	jar = "software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12.jar",
	srcjar = "software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_third_party_jackson_core",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:json-utils:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=77788e5d22ac33bf3afcd7417cb8b8216d9d76d75338ad49b81cf46bfa165e7e",
	],
	maven_coordinates = "software.amazon.awssdk:json-utils:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_json_utils_2_26_12",
	actual = "software_amazon_awssdk_json_utils",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_json_utils_2_26_12_extension",
     src = "@software_amazon_awssdk_json_utils_2_26_12//file",
     out = "software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_metrics_spi",
	jar = "software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12.jar",
	srcjar = "software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:metrics-spi:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=fefdfa825083c2bd2b70bf32156163807618010587692b5cf51c212557d8c8ce",
	],
	maven_coordinates = "software.amazon.awssdk:metrics-spi:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_metrics_spi_2_26_12",
	actual = "software_amazon_awssdk_metrics_spi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_metrics_spi_2_26_12_extension",
     src = "@software_amazon_awssdk_metrics_spi_2_26_12//file",
     out = "software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_netty_nio_client",
	jar = "software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12.jar",
	srcjar = "software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12-sources.jar",
	deps = [
		":io_netty_netty_buffer",
		":io_netty_netty_codec",
		":io_netty_netty_codec_http",
		":io_netty_netty_codec_http2",
		":io_netty_netty_common",
		":io_netty_netty_handler",
		":io_netty_netty_resolver",
		":io_netty_netty_transport",
		":io_netty_netty_transport_classes_epoll",
		":org_reactivestreams_reactive_streams",
		":org_slf4j_slf4j_api",
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_metrics_spi",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:netty-nio-client:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=01fb72b5407f5453429ea663a3359730febff4ce9d8a8571b3ff780bb61bfa07",
	],
	maven_coordinates = "software.amazon.awssdk:netty-nio-client:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_netty_nio_client_2_26_12",
	actual = "software_amazon_awssdk_netty_nio_client",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_netty_nio_client_2_26_12_extension",
     src = "@software_amazon_awssdk_netty_nio_client_2_26_12//file",
     out = "software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_profiles",
	jar = "software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12.jar",
	srcjar = "software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:profiles:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=b481f4f84e021a45bb407f1b14524e3ebd4d07580361f74af9cec2412dea83f3",
	],
	maven_coordinates = "software.amazon.awssdk:profiles:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_profiles_2_26_12",
	actual = "software_amazon_awssdk_profiles",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_profiles_2_26_12_extension",
     src = "@software_amazon_awssdk_profiles_2_26_12//file",
     out = "software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_protocol_core",
	jar = "software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12.jar",
	srcjar = "software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_sdk_core",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:protocol-core:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d31a0e570ef9320bab6028d415b2a76d161af6807391846e4a48f6d9915ee93a",
	],
	maven_coordinates = "software.amazon.awssdk:protocol-core:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_protocol_core_2_26_12",
	actual = "software_amazon_awssdk_protocol_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_protocol_core_2_26_12_extension",
     src = "@software_amazon_awssdk_protocol_core_2_26_12//file",
     out = "software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_regions",
	jar = "software/amazon/awssdk/regions/2.26.12/regions-2.26.12.jar",
	srcjar = "software/amazon/awssdk/regions/2.26.12/regions-2.26.12-sources.jar",
	deps = [
		":org_slf4j_slf4j_api",
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_json_utils",
		":software_amazon_awssdk_profiles",
		":software_amazon_awssdk_sdk_core",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:regions:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/regions/2.26.12/regions-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=532e742d04dfedd24cbb87500e146bb0e1da45041fd7bb2756f9a5719dacd024",
	],
	maven_coordinates = "software.amazon.awssdk:regions:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/regions/2.26.12/regions-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_regions_2_26_12",
	actual = "software_amazon_awssdk_regions",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_regions_2_26_12_extension",
     src = "@software_amazon_awssdk_regions_2_26_12//file",
     out = "software/amazon/awssdk/regions/2.26.12/regions-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_retries",
	jar = "software/amazon/awssdk/retries/2.26.12/retries-2.26.12.jar",
	srcjar = "software/amazon/awssdk/retries/2.26.12/retries-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_retries_spi",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:retries:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/retries/2.26.12/retries-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=bc203589f446885405569f45ed87afe177ce394032c3c344c10fd3c2b236333d",
	],
	maven_coordinates = "software.amazon.awssdk:retries:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/retries/2.26.12/retries-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_retries_2_26_12",
	actual = "software_amazon_awssdk_retries",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_retries_2_26_12_extension",
     src = "@software_amazon_awssdk_retries_2_26_12//file",
     out = "software/amazon/awssdk/retries/2.26.12/retries-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_retries_spi",
	jar = "software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12.jar",
	srcjar = "software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:retries-spi:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=157023fa50e8c308af3b496a778681d45c1af2d295a1edbb8f1af49146f66620",
	],
	maven_coordinates = "software.amazon.awssdk:retries-spi:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_retries_spi_2_26_12",
	actual = "software_amazon_awssdk_retries_spi",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_retries_spi_2_26_12_extension",
     src = "@software_amazon_awssdk_retries_spi_2_26_12//file",
     out = "software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_s3",
	jar = "software/amazon/awssdk/s3/2.26.12/s3-2.26.12.jar",
	srcjar = "software/amazon/awssdk/s3/2.26.12/s3-2.26.12-sources.jar",
	deps = [
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_apache_client",
		":software_amazon_awssdk_arns",
		":software_amazon_awssdk_auth",
		":software_amazon_awssdk_aws_core",
		":software_amazon_awssdk_aws_xml_protocol",
		":software_amazon_awssdk_checksums",
		":software_amazon_awssdk_checksums_spi",
		":software_amazon_awssdk_crt_core",
		":software_amazon_awssdk_endpoints_spi",
		":software_amazon_awssdk_http_auth",
		":software_amazon_awssdk_http_auth_aws",
		":software_amazon_awssdk_http_auth_spi",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_identity_spi",
		":software_amazon_awssdk_json_utils",
		":software_amazon_awssdk_metrics_spi",
		":software_amazon_awssdk_netty_nio_client",
		":software_amazon_awssdk_profiles",
		":software_amazon_awssdk_protocol_core",
		":software_amazon_awssdk_regions",
		":software_amazon_awssdk_retries_spi",
		":software_amazon_awssdk_sdk_core",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:s3:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/s3/2.26.12/s3-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=1f134d590c0b1bd346339083158c7cd7707cb9345f6369e32f283cf16d83380b",
	],
	maven_coordinates = "software.amazon.awssdk:s3:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/s3/2.26.12/s3-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_s3_2_26_12",
	actual = "software_amazon_awssdk_s3",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_s3_2_26_12_extension",
     src = "@software_amazon_awssdk_s3_2_26_12//file",
     out = "software/amazon/awssdk/s3/2.26.12/s3-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_sdk_core",
	jar = "software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12.jar",
	srcjar = "software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12-sources.jar",
	deps = [
		":org_reactivestreams_reactive_streams",
		":org_slf4j_slf4j_api",
		":software_amazon_awssdk_annotations",
		":software_amazon_awssdk_checksums",
		":software_amazon_awssdk_checksums_spi",
		":software_amazon_awssdk_endpoints_spi",
		":software_amazon_awssdk_http_auth_aws",
		":software_amazon_awssdk_http_auth_spi",
		":software_amazon_awssdk_http_client_spi",
		":software_amazon_awssdk_identity_spi",
		":software_amazon_awssdk_metrics_spi",
		":software_amazon_awssdk_profiles",
		":software_amazon_awssdk_retries",
		":software_amazon_awssdk_retries_spi",
		":software_amazon_awssdk_utils",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:sdk-core:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=424d6fe70f11009b1177a09c02053b223d8571c19b0762c913e773b588206700",
	],
	maven_coordinates = "software.amazon.awssdk:sdk-core:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_sdk_core_2_26_12",
	actual = "software_amazon_awssdk_sdk_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_sdk_core_2_26_12_extension",
     src = "@software_amazon_awssdk_sdk_core_2_26_12//file",
     out = "software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_third_party_jackson_core",
	jar = "software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12.jar",
	srcjar = "software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:third-party-jackson-core:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=3c7704454a012d2960b6ac4501c792ee89f36e9c4f2c25872896c0a0a7fa0fb7",
	],
	maven_coordinates = "software.amazon.awssdk:third-party-jackson-core:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_third_party_jackson_core_2_26_12",
	actual = "software_amazon_awssdk_third_party_jackson_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_third_party_jackson_core_2_26_12_extension",
     src = "@software_amazon_awssdk_third_party_jackson_core_2_26_12//file",
     out = "software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_awssdk_utils",
	jar = "software/amazon/awssdk/utils/2.26.12/utils-2.26.12.jar",
	srcjar = "software/amazon/awssdk/utils/2.26.12/utils-2.26.12-sources.jar",
	deps = [
		":org_reactivestreams_reactive_streams",
		":org_slf4j_slf4j_api",
		":software_amazon_awssdk_annotations",
	],
	tags = [
		"maven_coordinates=software.amazon.awssdk:utils:2.26.12",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/awssdk/utils/2.26.12/utils-2.26.12.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=6a6374a9da18a5a705d7147fcc0deab80601fb5ca490204863bf330786faa00f",
	],
	maven_coordinates = "software.amazon.awssdk:utils:2.26.12",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/awssdk/utils/2.26.12/utils-2.26.12.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_awssdk_utils_2_26_12",
	actual = "software_amazon_awssdk_utils",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_awssdk_utils_2_26_12_extension",
     src = "@software_amazon_awssdk_utils_2_26_12//file",
     out = "software/amazon/awssdk/utils/2.26.12/utils-2.26.12.jar",
     allow_symlink = True,
     visibility = ["//visibility:public"]
)
jvm_import(
	name = "software_amazon_eventstream_eventstream",
	jar = "software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1.jar",
	srcjar = "software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1-sources.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=software.amazon.eventstream:eventstream:1.0.1",
		"maven_url=https://repo1.maven.org/maven2/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=0c37d8e696117f02c302191b8110b0d0eb20fa412fce34c3a269ec73c16ce822",
	],
	maven_coordinates = "software.amazon.eventstream:eventstream:1.0.1",
	maven_url = "https://repo1.maven.org/maven2/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "software_amazon_eventstream_eventstream_1_0_1",
	actual = "software_amazon_eventstream_eventstream",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "software_amazon_eventstream_eventstream_1_0_1_extension",
     src = "@software_amazon_eventstream_eventstream_1_0_1//file",
     out = "software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1.jar",
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
  actual = "@@rules_jvm_external++maven+unpinned_rules_jvm_external_deps//:pin",
  visibility = ["//visibility:public"],
)
