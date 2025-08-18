load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")
def pinned_maven_install():
    pass
    http_file(
        name = "aopalliance_aopalliance_1_0",
        sha256 = "0addec670fedcd3f113c5c8091d783280d23f75e3acb841b61a9cdb079376a08",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/aopalliance/aopalliance/1.0/aopalliance-1.0.jar", "https://repo.gradle.org/gradle/libs-releases/aopalliance/aopalliance/1.0/aopalliance-1.0.jar"],
        downloaded_file_path = "v1/aopalliance/aopalliance/1.0/aopalliance-1.0.jar",
    )
    http_file(
        name = "aopalliance_aopalliance_sources_1_0",
        sha256 = "e6ef91d439ada9045f419c77543ebe0416c3cdfc5b063448343417a3e4a72123",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/aopalliance/aopalliance/1.0/aopalliance-1.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/aopalliance/aopalliance/1.0/aopalliance-1.0-sources.jar"],
        downloaded_file_path = "v1/aopalliance/aopalliance/1.0/aopalliance-1.0-sources.jar",
    )
    http_file(
        name = "com_fasterxml_jackson_core_jackson_core_2_17_1",
        sha256 = "ddb26c8a1f1a84535e8213c48b35b253370434e3287b3cf15777856fc4e58ce6",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1.jar", "https://repo.gradle.org/gradle/libs-releases/com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1.jar"],
        downloaded_file_path = "v1/com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1.jar",
    )
    http_file(
        name = "com_fasterxml_jackson_core_jackson_core_sources_2_17_1",
        sha256 = "c2c97a708be197aae5fee64dcc8b5e8a09c76c79a44c0e8e5b48b235084ec395",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1-sources.jar"],
        downloaded_file_path = "v1/com/fasterxml/jackson/core/jackson-core/2.17.1/jackson-core-2.17.1-sources.jar",
    )
    http_file(
        name = "com_github_jknack_handlebars_4_3_1",
        sha256 = "5424fd12e911cf15befd16341b46e0e1bc681aa61e3cb1c070c57e68dccd5bbd",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/github/jknack/handlebars/4.3.1/handlebars-4.3.1.jar", "https://repo.gradle.org/gradle/libs-releases/com/github/jknack/handlebars/4.3.1/handlebars-4.3.1.jar"],
        downloaded_file_path = "v1/com/github/jknack/handlebars/4.3.1/handlebars-4.3.1.jar",
    )
    http_file(
        name = "com_github_jknack_handlebars_sources_4_3_1",
        sha256 = "5087778d1d83af86d8f8f38240d899037c874fa9888e7fb3f9c919f52340f028",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/github/jknack/handlebars/4.3.1/handlebars-4.3.1-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/github/jknack/handlebars/4.3.1/handlebars-4.3.1-sources.jar"],
        downloaded_file_path = "v1/com/github/jknack/handlebars/4.3.1/handlebars-4.3.1-sources.jar",
    )
    http_file(
        name = "com_google_android_annotations_4_1_1_4",
        sha256 = "ba734e1e84c09d615af6a09d33034b4f0442f8772dec120efb376d86a565ae15",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/android/annotations/4.1.1.4/annotations-4.1.1.4.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/android/annotations/4.1.1.4/annotations-4.1.1.4.jar"],
        downloaded_file_path = "v1/com/google/android/annotations/4.1.1.4/annotations-4.1.1.4.jar",
    )
    http_file(
        name = "com_google_android_annotations_sources_4_1_1_4",
        sha256 = "e9b667aa958df78ea1ad115f7bbac18a5869c3128b1d5043feb360b0cfce9d40",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/android/annotations/4.1.1.4/annotations-4.1.1.4-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/android/annotations/4.1.1.4/annotations-4.1.1.4-sources.jar"],
        downloaded_file_path = "v1/com/google/android/annotations/4.1.1.4/annotations-4.1.1.4-sources.jar",
    )
    http_file(
        name = "com_google_api_client_google_api_client_2_6_0",
        sha256 = "4ce2d30c647311098995a679562d2605903a9e7710da2e2ea1b17d062062f0c4",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0.jar"],
        downloaded_file_path = "v1/com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0.jar",
    )
    http_file(
        name = "com_google_api_client_google_api_client_sources_2_6_0",
        sha256 = "cb5b581ecb4c03751be5424223dd7215d83bcc5395ae1313880faf77c5a5148e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0-sources.jar"],
        downloaded_file_path = "v1/com/google/api-client/google-api-client/2.6.0/google-api-client-2.6.0-sources.jar",
    )
    http_file(
        name = "com_google_api_grpc_gapic_google_cloud_storage_v2_2_40_1_alpha",
        sha256 = "533ecc03e4835532d4e6bc032c9bc523172a6444c5261a03b184b7379fde51f8",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha.jar",
    )
    http_file(
        name = "com_google_api_grpc_gapic_google_cloud_storage_v2_sources_2_40_1_alpha",
        sha256 = "304981ba5c2d8e19adec72184f2c11934535465a16041b92d52300e0dcbbcb25",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha-sources.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/gapic-google-cloud-storage-v2/2.40.1-alpha/gapic-google-cloud-storage-v2-2.40.1-alpha-sources.jar",
    )
    http_file(
        name = "com_google_api_grpc_grpc_google_cloud_storage_v2_2_40_1_alpha",
        sha256 = "a92ff56ba6fbabc7a16d75f5d06f536e0087979ed6cc0ea8f3de5140733e8450",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha.jar",
    )
    http_file(
        name = "com_google_api_grpc_grpc_google_cloud_storage_v2_sources_2_40_1_alpha",
        sha256 = "896b6872d9591434cbd8fcfc1c1b2b6cd9d6bfc14bf45374d66891250af79ecb",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha-sources.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/grpc-google-cloud-storage-v2/2.40.1-alpha/grpc-google-cloud-storage-v2-2.40.1-alpha-sources.jar",
    )
    http_file(
        name = "com_google_api_grpc_proto_google_cloud_storage_v2_2_40_1_alpha",
        sha256 = "225a94c8509b78473df7701ac2343a0e2641fe6181a288f84b402d3ef5f90587",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha.jar",
    )
    http_file(
        name = "com_google_api_grpc_proto_google_cloud_storage_v2_sources_2_40_1_alpha",
        sha256 = "c59b68b1f566d23cbe628227eaca00be847fd1951e9104f3ef92f2536e7a431e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha-sources.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/proto-google-cloud-storage-v2/2.40.1-alpha/proto-google-cloud-storage-v2-2.40.1-alpha-sources.jar",
    )
    http_file(
        name = "com_google_api_grpc_proto_google_common_protos_2_41_0",
        sha256 = "49edeba62f334053b91aa9455c95e38449269891b920dbc36daa74e959a3d89a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0.jar",
    )
    http_file(
        name = "com_google_api_grpc_proto_google_common_protos_sources_2_41_0",
        sha256 = "a802dcf2a3f32b93b27e3b85988db08de834cdd32d2a26b5f1a1f04ca4fabcab",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0-sources.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/proto-google-common-protos/2.41.0/proto-google-common-protos-2.41.0-sources.jar",
    )
    http_file(
        name = "com_google_api_grpc_proto_google_iam_v1_1_36_0",
        sha256 = "ec95a04fa6ee822e91cd8b4917a4602fbc0fb57413c2ba8f079807545b8eb193",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0.jar",
    )
    http_file(
        name = "com_google_api_grpc_proto_google_iam_v1_sources_1_36_0",
        sha256 = "52b57ca0aa960716af378176e8ea544f667c13ca4c193706e60734571de0d51c",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0-sources.jar"],
        downloaded_file_path = "v1/com/google/api/grpc/proto-google-iam-v1/1.36.0/proto-google-iam-v1-1.36.0-sources.jar",
    )
    http_file(
        name = "com_google_api_api_common_2_33_0",
        sha256 = "5077981c2f6649b615a10631d89759861aeb4edb49ea4fa2e1bb5506a70db1be",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/api-common/2.33.0/api-common-2.33.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/api-common/2.33.0/api-common-2.33.0.jar"],
        downloaded_file_path = "v1/com/google/api/api-common/2.33.0/api-common-2.33.0.jar",
    )
    http_file(
        name = "com_google_api_api_common_sources_2_33_0",
        sha256 = "5565a14d90a26554523586785a5985c4d6a7dd1d79c7ad5d8341424a3c784cf3",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/api-common/2.33.0/api-common-2.33.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/api-common/2.33.0/api-common-2.33.0-sources.jar"],
        downloaded_file_path = "v1/com/google/api/api-common/2.33.0/api-common-2.33.0-sources.jar",
    )
    http_file(
        name = "com_google_api_gax_2_50_0",
        sha256 = "fa7d1cef5ef09dfcc1ff2e26d020f5023817dc14d4f1320391ea631698126a52",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/gax/2.50.0/gax-2.50.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/gax/2.50.0/gax-2.50.0.jar"],
        downloaded_file_path = "v1/com/google/api/gax/2.50.0/gax-2.50.0.jar",
    )
    http_file(
        name = "com_google_api_gax_sources_2_50_0",
        sha256 = "a2152d456b6ac517a6756b343bb147d1b0e9bd72e0a2137d751427cf4d72bb57",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/gax/2.50.0/gax-2.50.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/gax/2.50.0/gax-2.50.0-sources.jar"],
        downloaded_file_path = "v1/com/google/api/gax/2.50.0/gax-2.50.0-sources.jar",
    )
    http_file(
        name = "com_google_api_gax_grpc_2_50_0",
        sha256 = "b2c5d39326e402ecafbcc02221400b802a9e01e3cd457ab5e69386da2d53d737",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0.jar"],
        downloaded_file_path = "v1/com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0.jar",
    )
    http_file(
        name = "com_google_api_gax_grpc_sources_2_50_0",
        sha256 = "f57f7723acd422269f228360748e04bfe27f959781483fbae2f20c0f6a6ac85b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0-sources.jar"],
        downloaded_file_path = "v1/com/google/api/gax-grpc/2.50.0/gax-grpc-2.50.0-sources.jar",
    )
    http_file(
        name = "com_google_api_gax_httpjson_2_50_0",
        sha256 = "6c34ec75e64bb925af9fe15a024820bfaf3e19196b75a1be8092f842f13e47e4",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0.jar"],
        downloaded_file_path = "v1/com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0.jar",
    )
    http_file(
        name = "com_google_api_gax_httpjson_sources_2_50_0",
        sha256 = "25fb428060917b29e979b36bc7a3a92794dc8952a9e0ea6ba9038be0fb660fbb",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0-sources.jar"],
        downloaded_file_path = "v1/com/google/api/gax-httpjson/2.50.0/gax-httpjson-2.50.0-sources.jar",
    )
    http_file(
        name = "com_google_apis_google_api_services_storage_v1_rev20240621_2_0_0",
        sha256 = "db4a684212e945a9206c94f4730faa7fc825f0666417e7b7e6925451bde6096c",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0.jar"],
        downloaded_file_path = "v1/com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0.jar",
    )
    http_file(
        name = "com_google_apis_google_api_services_storage_sources_v1_rev20240621_2_0_0",
        sha256 = "1e4c6ad01d44e92b36de6d80cb211c3c53a0adcf4f4a63920d68df46ba4b6a97",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0-sources.jar"],
        downloaded_file_path = "v1/com/google/apis/google-api-services-storage/v1-rev20240621-2.0.0/google-api-services-storage-v1-rev20240621-2.0.0-sources.jar",
    )
    http_file(
        name = "com_google_auth_google_auth_library_credentials_1_23_0",
        sha256 = "d982eda20835e301dcbeec4d083289a44fdd06e9a35ce18449054f4ffd3f099f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0.jar"],
        downloaded_file_path = "v1/com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0.jar",
    )
    http_file(
        name = "com_google_auth_google_auth_library_credentials_sources_1_23_0",
        sha256 = "6151c76a0d9ef7bebe621370bbd812e927300bbfe5b11417c09bd29a1c54509b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0-sources.jar"],
        downloaded_file_path = "v1/com/google/auth/google-auth-library-credentials/1.23.0/google-auth-library-credentials-1.23.0-sources.jar",
    )
    http_file(
        name = "com_google_auth_google_auth_library_oauth2_http_1_23_0",
        sha256 = "f2bf739509b5f3697cb1bf33ff9dc27e8fc886cedb2f6376a458263f793ed133",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0.jar"],
        downloaded_file_path = "v1/com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0.jar",
    )
    http_file(
        name = "com_google_auth_google_auth_library_oauth2_http_sources_1_23_0",
        sha256 = "f4c00cac4c72cd39d0957dffad5d19c4ad63185e4fbec3d6211fb0cf3f5fdb6f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0-sources.jar"],
        downloaded_file_path = "v1/com/google/auth/google-auth-library-oauth2-http/1.23.0/google-auth-library-oauth2-http-1.23.0-sources.jar",
    )
    http_file(
        name = "com_google_auto_value_auto_value_annotations_1_10_4",
        sha256 = "e1c45e6beadaef9797cb0d9afd5a45621ad061cd8632012f85582853a3887825",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar"],
        downloaded_file_path = "v1/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar",
    )
    http_file(
        name = "com_google_auto_value_auto_value_annotations_sources_1_10_4",
        sha256 = "61a433f015b12a6cf4ecff227c7748486ff8f294ffe9d39827b382ade0514d0a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4-sources.jar"],
        downloaded_file_path = "v1/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4-sources.jar",
    )
    http_file(
        name = "com_google_cloud_google_cloud_core_2_40_0",
        sha256 = "a3c1993957ac597ccfbb111e2814aed9c5298f04987886fda81be102f426372c",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0.jar"],
        downloaded_file_path = "v1/com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0.jar",
    )
    http_file(
        name = "com_google_cloud_google_cloud_core_sources_2_40_0",
        sha256 = "a69c28251c5a18028e095bbff100f73720e6079a51183752a81cd833f56603e6",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0-sources.jar"],
        downloaded_file_path = "v1/com/google/cloud/google-cloud-core/2.40.0/google-cloud-core-2.40.0-sources.jar",
    )
    http_file(
        name = "com_google_cloud_google_cloud_core_grpc_2_40_0",
        sha256 = "e3535528221c6a5d33f55f164fc9602ed08f51bf4a4e31befc2e0a42a530159d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0.jar"],
        downloaded_file_path = "v1/com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0.jar",
    )
    http_file(
        name = "com_google_cloud_google_cloud_core_grpc_sources_2_40_0",
        sha256 = "a1e88dc771c31d4c1ab497e571fb246ee31f5fb3d2596d5c342065eecaecbf7d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0-sources.jar"],
        downloaded_file_path = "v1/com/google/cloud/google-cloud-core-grpc/2.40.0/google-cloud-core-grpc-2.40.0-sources.jar",
    )
    http_file(
        name = "com_google_cloud_google_cloud_core_http_2_40_0",
        sha256 = "66dfb9e652ac8f1a0c0a9067c263448126a3a6c4b2b00a24467a6056ff8f0298",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0.jar"],
        downloaded_file_path = "v1/com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0.jar",
    )
    http_file(
        name = "com_google_cloud_google_cloud_core_http_sources_2_40_0",
        sha256 = "ffe43cf1a9bd7f4ba1053bdbfa5aff9bee6f607550d6bf9e258795f3bd490f5c",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0-sources.jar"],
        downloaded_file_path = "v1/com/google/cloud/google-cloud-core-http/2.40.0/google-cloud-core-http-2.40.0-sources.jar",
    )
    http_file(
        name = "com_google_cloud_google_cloud_storage_2_40_1",
        sha256 = "d37399b0e96ecea896bdaa091375eb4e8783ebeb452d344ad9d55e20d67b8b72",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1.jar"],
        downloaded_file_path = "v1/com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1.jar",
    )
    http_file(
        name = "com_google_cloud_google_cloud_storage_sources_2_40_1",
        sha256 = "18e4beee641de0b4a5467506f7f35be6c96adcd48bb6bc10dedd48270643a72b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1-sources.jar"],
        downloaded_file_path = "v1/com/google/cloud/google-cloud-storage/2.40.1/google-cloud-storage-2.40.1-sources.jar",
    )
    http_file(
        name = "com_google_code_findbugs_jsr305_3_0_2",
        sha256 = "766ad2a0783f2687962c8ad74ceecc38a28b9f72a2d085ee438b7813e928d0c7",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar"],
        downloaded_file_path = "v1/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar",
    )
    http_file(
        name = "com_google_code_findbugs_jsr305_sources_3_0_2",
        sha256 = "1c9e85e272d0708c6a591dc74828c71603053b48cc75ae83cce56912a2aa063b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2-sources.jar"],
        downloaded_file_path = "v1/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2-sources.jar",
    )
    http_file(
        name = "com_google_code_gson_gson_2_11_0",
        sha256 = "57928d6e5a6edeb2abd3770a8f95ba44dce45f3b23b7a9dc2b309c581552a78b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/code/gson/gson/2.11.0/gson-2.11.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/code/gson/gson/2.11.0/gson-2.11.0.jar"],
        downloaded_file_path = "v1/com/google/code/gson/gson/2.11.0/gson-2.11.0.jar",
    )
    http_file(
        name = "com_google_code_gson_gson_sources_2_11_0",
        sha256 = "49a853f71bc874ee1898a4ad5009b57d0c536e5a998b3890253ffbf4b7276ad3",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/code/gson/gson/2.11.0/gson-2.11.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/code/gson/gson/2.11.0/gson-2.11.0-sources.jar"],
        downloaded_file_path = "v1/com/google/code/gson/gson/2.11.0/gson-2.11.0-sources.jar",
    )
    http_file(
        name = "com_google_errorprone_error_prone_annotations_2_36_0",
        sha256 = "77440e270b0bc9a249903c5a076c36a722c4886ca4f42675f2903a1c53ed61a5",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0.jar"],
        downloaded_file_path = "v1/com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0.jar",
    )
    http_file(
        name = "com_google_errorprone_error_prone_annotations_sources_2_36_0",
        sha256 = "7e117e0931cb2cb4226372af336189b49edb79969d120ec958a6df0beacb0612",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0-sources.jar"],
        downloaded_file_path = "v1/com/google/errorprone/error_prone_annotations/2.36.0/error_prone_annotations-2.36.0-sources.jar",
    )
    http_file(
        name = "com_google_googlejavaformat_google_java_format_1_22_0",
        sha256 = "4f4bdba0f2a3d7e84be47683a0c2a4ba69024d29d906d09784181f68f04af792",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0.jar"],
        downloaded_file_path = "v1/com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0.jar",
    )
    http_file(
        name = "com_google_googlejavaformat_google_java_format_sources_1_22_0",
        sha256 = "8ca9810fbf8a542b812e3e3111bae2b534f415bbec8219f6b69764af8ba19d11",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0-sources.jar"],
        downloaded_file_path = "v1/com/google/googlejavaformat/google-java-format/1.22.0/google-java-format-1.22.0-sources.jar",
    )
    http_file(
        name = "com_google_guava_failureaccess_1_0_3",
        sha256 = "cbfc3906b19b8f55dd7cfd6dfe0aa4532e834250d7f080bd8d211a3e246b59cb",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar"],
        downloaded_file_path = "v1/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar",
    )
    http_file(
        name = "com_google_guava_failureaccess_sources_1_0_3",
        sha256 = "6fef4dfd2eb9f961655f2a3c4ea87c023618d9fcbfb6b104c17862e5afe66b97",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3-sources.jar"],
        downloaded_file_path = "v1/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3-sources.jar",
    )
    http_file(
        name = "com_google_guava_guava_33_4_8_jre",
        sha256 = "f3d7f57f67fd622f4d468dfdd692b3a5e3909246c28017ac3263405f0fe617ed",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar"],
        downloaded_file_path = "v1/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar",
    )
    http_file(
        name = "com_google_guava_guava_sources_33_4_8_jre",
        sha256 = "9d3c6aad893daac9d4812eb9fa4c3f7956a9f2e472eb7df2fea0e467fed7e766",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre-sources.jar"],
        downloaded_file_path = "v1/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre-sources.jar",
    )
    http_file(
        name = "com_google_guava_listenablefuture_9999_0_empty_to_avoid_conflict_with_guava",
        sha256 = "b372a037d4230aa57fbeffdef30fd6123f9c0c2db85d0aced00c91b974f33f99",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"],
        downloaded_file_path = "v1/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_1_44_2",
        sha256 = "390618d7b51704240b8fd28e1230fa35d220f93f4b4ba80f63e38db00dacb09e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_sources_1_44_2",
        sha256 = "9419537a2973195619b43f76be92388b1e37a785503717d76afff5764884ebc2",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2-sources.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client/1.44.2/google-http-client-1.44.2-sources.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_apache_v2_1_44_2",
        sha256 = "104596bb9296a403a5150b8e3b72dc16d9ca0b0f94b1287348ada5825e19298d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_apache_v2_sources_1_44_2",
        sha256 = "e4adbb311021b29bd83d2ecb4eaa4eb608b543d16eeabc00633a10dccb1bb3b9",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2-sources.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-apache-v2/1.44.2/google-http-client-apache-v2-1.44.2-sources.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_appengine_1_44_2",
        sha256 = "f9af780c40f7de304c05dfe24b362588a56c8a804c0b8d929dd82296972abe47",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_appengine_sources_1_44_2",
        sha256 = "9ff129a43d696239eac49c34545587760b0491a9a01b8ed1dcf832176d275e2e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2-sources.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-appengine/1.44.2/google-http-client-appengine-1.44.2-sources.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_gson_1_44_2",
        sha256 = "1119b66685195310375b717de2215d6c5d14fa8ed9f57e07b4fecd461e7b9db7",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_gson_sources_1_44_2",
        sha256 = "3bac061bdac5c5c67713b8db689a1d6342afcb07a87c2f7285dffc1729fc4825",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2-sources.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-gson/1.44.2/google-http-client-gson-1.44.2-sources.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_jackson2_1_44_2",
        sha256 = "b6b81f992b8c20cd5de332e43ea964227e5dd71663ca27194c25bfc55c1067b6",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_jackson2_sources_1_44_2",
        sha256 = "f3ae513cc8c040c47087e004a781392ee7eb2cbbbd3cbb9e5b79d9e934954d83",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2-sources.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-jackson2/1.44.2/google-http-client-jackson2-1.44.2-sources.jar",
    )
    http_file(
        name = "com_google_inject_guice_classes_5_1_0",
        sha256 = "142ad4475e19524d2fe3ac995b3f7cbc962fc726f2edb9dbdccc61feab9b2bf9",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/inject/guice/5.1.0/guice-5.1.0-classes.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/inject/guice/5.1.0/guice-5.1.0-classes.jar"],
        downloaded_file_path = "v1/com/google/inject/guice/5.1.0/guice-5.1.0-classes.jar",
    )
    http_file(
        name = "com_google_inject_guice_sources_5_1_0",
        sha256 = "79484227656350f8ea315198ed2ebdc8583e7ba42ecd90d367d66a7e491de52e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/inject/guice/5.1.0/guice-5.1.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/inject/guice/5.1.0/guice-5.1.0-sources.jar"],
        downloaded_file_path = "v1/com/google/inject/guice/5.1.0/guice-5.1.0-sources.jar",
    )
    http_file(
        name = "com_google_j2objc_j2objc_annotations_3_0_0",
        sha256 = "88241573467ddca44ffd4d74aa04c2bbfd11bf7c17e0c342c94c9de7a70a7c64",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar"],
        downloaded_file_path = "v1/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar",
    )
    http_file(
        name = "com_google_j2objc_j2objc_annotations_sources_3_0_0",
        sha256 = "bd60019a0423c3a025ef6ab24fe0761f5f45ffb48a8cca74a01b678de1105d38",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0-sources.jar"],
        downloaded_file_path = "v1/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0-sources.jar",
    )
    http_file(
        name = "com_google_oauth_client_google_oauth_client_1_36_0",
        sha256 = "8fee7bbe7aaee214ce461f0cd983e3c438fd43941697394391aaa01edb7d703b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0.jar"],
        downloaded_file_path = "v1/com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0.jar",
    )
    http_file(
        name = "com_google_oauth_client_google_oauth_client_sources_1_36_0",
        sha256 = "3b8aa6bc51da9b22ef564b189714b914e866dd7274a09eb211239517da49db2e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0-sources.jar"],
        downloaded_file_path = "v1/com/google/oauth-client/google-oauth-client/1.36.0/google-oauth-client-1.36.0-sources.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_java_3_25_3",
        sha256 = "e90d8ddb963b20a972a6a59b5093ade2b07cbe546cab3279aaf4383260385f58",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_java_sources_3_25_3",
        sha256 = "1bc9c4b7f5f5a89781ba06ca23ac651b979c3bfd93b2d60d8156ec2389017392",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3-sources.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-java/3.25.3/protobuf-java-3.25.3-sources.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_java_util_3_25_3",
        sha256 = "b813c8d6d554cb71c1e82d171d7f80730ae74222a185c863cbedf05072c88155",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_java_util_sources_3_25_3",
        sha256 = "d859cbbfc492018d4de6461518c877420d12ca169bcca24067cc836bc5b643fd",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3-sources.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-java-util/3.25.3/protobuf-java-util-3.25.3-sources.jar",
    )
    http_file(
        name = "com_google_re2j_re2j_1_7",
        sha256 = "4f657af51ab8bb0909bcc3eb40862d26125af8cbcf92aaaba595fed77f947bc0",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/re2j/re2j/1.7/re2j-1.7.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/re2j/re2j/1.7/re2j-1.7.jar"],
        downloaded_file_path = "v1/com/google/re2j/re2j/1.7/re2j-1.7.jar",
    )
    http_file(
        name = "com_google_re2j_re2j_sources_1_7",
        sha256 = "ddc3b47bb1e556ac4c0d02c9d8ff18f3260198b76b720567a70eed0a03d3fed6",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/re2j/re2j/1.7/re2j-1.7-sources.jar", "https://repo.gradle.org/gradle/libs-releases/com/google/re2j/re2j/1.7/re2j-1.7-sources.jar"],
        downloaded_file_path = "v1/com/google/re2j/re2j/1.7/re2j-1.7-sources.jar",
    )
    http_file(
        name = "commons_codec_commons_codec_1_18_0",
        sha256 = "ba005f304cef92a3dede24a38ad5ac9b8afccf0d8f75839d6c1338634cf7f6e4",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/commons-codec/commons-codec/1.18.0/commons-codec-1.18.0.jar", "https://repo.gradle.org/gradle/libs-releases/commons-codec/commons-codec/1.18.0/commons-codec-1.18.0.jar"],
        downloaded_file_path = "v1/commons-codec/commons-codec/1.18.0/commons-codec-1.18.0.jar",
    )
    http_file(
        name = "commons_codec_commons_codec_sources_1_18_0",
        sha256 = "6c50e3dd81284139baddf94b3d0f78d25135eea0853f6495267196cdcf5949e3",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/commons-codec/commons-codec/1.18.0/commons-codec-1.18.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/commons-codec/commons-codec/1.18.0/commons-codec-1.18.0-sources.jar"],
        downloaded_file_path = "v1/commons-codec/commons-codec/1.18.0/commons-codec-1.18.0-sources.jar",
    )
    http_file(
        name = "commons_logging_commons_logging_1_2",
        sha256 = "daddea1ea0be0f56978ab3006b8ac92834afeefbd9b7e4e6316fca57df0fa636",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/commons-logging/commons-logging/1.2/commons-logging-1.2.jar", "https://repo.gradle.org/gradle/libs-releases/commons-logging/commons-logging/1.2/commons-logging-1.2.jar"],
        downloaded_file_path = "v1/commons-logging/commons-logging/1.2/commons-logging-1.2.jar",
    )
    http_file(
        name = "commons_logging_commons_logging_sources_1_2",
        sha256 = "44347acfe5860461728e9cb33251e97345be36f8a0dfd5c5130c172559455f41",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/commons-logging/commons-logging/1.2/commons-logging-1.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/commons-logging/commons-logging/1.2/commons-logging-1.2-sources.jar"],
        downloaded_file_path = "v1/commons-logging/commons-logging/1.2/commons-logging-1.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_alts_1_62_2",
        sha256 = "8c36fc921f18813a2f82e9f70211718c82280341c3822ab9d1782eaec2a8882a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_alts_sources_1_62_2",
        sha256 = "33e6302db01aed6ddd1403509aa516c4acc94d55667104f0a5dfe81ee00f8d61",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-alts/1.62.2/grpc-alts-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_api_1_62_2",
        sha256 = "2e896944cf513e0e5cfd32bcd72c89601a27c6ca56916f84b20f3a13bacf1b1f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-api/1.62.2/grpc-api-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-api/1.62.2/grpc-api-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-api/1.62.2/grpc-api-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_api_sources_1_62_2",
        sha256 = "aa2974982805cc998f79e7c4d5d536744fd5520b56eb15b0179f9331c1edb3b7",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-api/1.62.2/grpc-api-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-api/1.62.2/grpc-api-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-api/1.62.2/grpc-api-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_auth_1_62_2",
        sha256 = "6a16c43d956c79190486d3d0b951836a6706b3282b5d275a9bc4d33eb79d5618",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_auth_sources_1_62_2",
        sha256 = "ceeb29d4bd28f678a6ecdd8f417e4c43b44eb2a1e307b130f18b78b8d9bd65f3",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-auth/1.62.2/grpc-auth-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_context_1_62_2",
        sha256 = "9959747df6a753119e1c1a3dff01aa766d2455f5e4860acaa305359e1d533a05",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-context/1.62.2/grpc-context-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-context/1.62.2/grpc-context-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-context/1.62.2/grpc-context-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_context_sources_1_62_2",
        sha256 = "c656b874e58c84ca975c3708f2e001dba76233385b6a5b7cb098868bd6ce38b1",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-context/1.62.2/grpc-context-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-context/1.62.2/grpc-context-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-context/1.62.2/grpc-context-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_core_1_62_2",
        sha256 = "18439902c473a2c1511e517d13b8ae796378850a8eda43787c6ba778fa90fcc5",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-core/1.62.2/grpc-core-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-core/1.62.2/grpc-core-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-core/1.62.2/grpc-core-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_core_sources_1_62_2",
        sha256 = "351325425f07abc1d274d5afea1a3b8f48cf49b6f07a128ebe7e71a732188f92",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-core/1.62.2/grpc-core-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-core/1.62.2/grpc-core-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-core/1.62.2/grpc-core-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_googleapis_1_62_2",
        sha256 = "0b8350c417dd5757056d97be671de360d91d6327d8de5871f8f4a556a12564f5",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_googleapis_sources_1_62_2",
        sha256 = "c54bb67b01f75ba743402120129cf79b0b7af1d2cff7b09a69343e369269c17b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-googleapis/1.62.2/grpc-googleapis-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_grpclb_1_62_2",
        sha256 = "49ed5d4b35e8d0b4f9b6f39fef774fc2a5927eeaeca7f54610e1b7fa0dc31f5a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_grpclb_sources_1_62_2",
        sha256 = "1034584c8675456ecc2dd641dabd8e30377897cc1e68cadb512b1658d47772e8",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-grpclb/1.62.2/grpc-grpclb-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_inprocess_1_62_2",
        sha256 = "f3c28a9d7f13fa995e4dd89e4f6aa08fa3b383665314fdfccb9f87f346625df7",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_inprocess_sources_1_62_2",
        sha256 = "85eb82961732f483d8ad831f96f90993bd5a3b80923b5ceb8e0be1dd3c6b4289",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-inprocess/1.62.2/grpc-inprocess-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_netty_shaded_1_62_2",
        sha256 = "b3f1823ef30ca02ac721020f4b6492248efdbd0548c78e893d5d245cbca2cc60",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_netty_shaded_sources_1_62_2",
        sha256 = "c656b874e58c84ca975c3708f2e001dba76233385b6a5b7cb098868bd6ce38b1",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-netty-shaded/1.62.2/grpc-netty-shaded-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_protobuf_1_62_2",
        sha256 = "66a0b196318bdfd817d965d2d82b9c81dfced8eb08c0f7510fcb728d2994237a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_protobuf_sources_1_62_2",
        sha256 = "4020d5c7485d6dd261f07b3deeabfe1d06fcd13e8a20fc147683926a03c38ef1",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-protobuf/1.62.2/grpc-protobuf-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_protobuf_lite_1_62_2",
        sha256 = "79997989a8c2b5bf4dd18182a2df2e2f668703d68ba7c317e7a07809d33f91f4",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_protobuf_lite_sources_1_62_2",
        sha256 = "fd38569d1c610d12e0844873ea18542503334b5f4db8c2239b68553ccc58942b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-protobuf-lite/1.62.2/grpc-protobuf-lite-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_rls_1_62_2",
        sha256 = "2fa8cb6cc22d28080b30f9ff0c6143c180017ae64a51a61828432ff48813cc88",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_rls_sources_1_62_2",
        sha256 = "b298e51cbf6f71f66e8dae848c16a7764becb02b010feedd5810dfe0812017fd",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-rls/1.62.2/grpc-rls-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_services_1_62_2",
        sha256 = "72f6eba0670184b634e7dcde0b97cde378a7cd74cdf63300f453d15c23bbbb6a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-services/1.62.2/grpc-services-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-services/1.62.2/grpc-services-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-services/1.62.2/grpc-services-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_services_sources_1_62_2",
        sha256 = "e0fe73139c7399bd435c6a5c7ec01d3d04fc0993f72e1fa58865415b83b5ebf8",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-services/1.62.2/grpc-services-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-services/1.62.2/grpc-services-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-services/1.62.2/grpc-services-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_stub_1_62_2",
        sha256 = "fb4ca679a4214143406c65ac4167b2b5e2ee2cab1fc101566bb1c4695d105e36",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_stub_sources_1_62_2",
        sha256 = "da613a25d08f3915ab1d54634c6dc4ffa7441fea74d53fcd46e68afe53b1b29a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-stub/1.62.2/grpc-stub-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_util_1_62_2",
        sha256 = "3c7103e6f3738571e3aeda420fe2a6ac68e354534d8b66f41897b6755b48b735",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-util/1.62.2/grpc-util-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-util/1.62.2/grpc-util-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-util/1.62.2/grpc-util-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_util_sources_1_62_2",
        sha256 = "eea606bb4b3b6df7863604fd82321f8713bc1e13e8d124c8ae1374fba174052e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-util/1.62.2/grpc-util-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-util/1.62.2/grpc-util-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-util/1.62.2/grpc-util-1.62.2-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_xds_1_62_2",
        sha256 = "4da41475d04e82c414ceb957e744f5bf99d80c846d5c5eb504c085c563b28b2d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2.jar",
    )
    http_file(
        name = "io_grpc_grpc_xds_sources_1_62_2",
        sha256 = "eede613eb4461d1fb98e9f0de3b37b64fd926b37e85176884bfc05029997c3dd",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-xds/1.62.2/grpc-xds-1.62.2-sources.jar",
    )
    http_file(
        name = "io_netty_netty_buffer_4_1_111_Final",
        sha256 = "7d94b609fb36afd88304c73922411d08f383f7945aa882b59a15219b5ecbfb76",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_buffer_sources_4_1_111_Final",
        sha256 = "bbb941e2ff54341a4c2926eba18440fe225be9380894b6f8d083c5dd48c2d194",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-buffer/4.1.111.Final/netty-buffer-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_netty_netty_codec_4_1_111_Final",
        sha256 = "a63ac713f60ec0b8e2bb8182665d216662d1f474872ec5c368d25f15f544f4bc",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_codec_sources_4_1_111_Final",
        sha256 = "9001605735f53915b12f9d5e7a0267da346c4597bf5044b4d9023f3458a8b187",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-codec/4.1.111.Final/netty-codec-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_netty_netty_codec_http_4_1_111_Final",
        sha256 = "bf50c212caec876bb6fba85b74f18482da2d7824dd0766f1829ca9f93aa01a52",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_codec_http_sources_4_1_111_Final",
        sha256 = "ac9f37dc9ec3808551d5fca59f5fab401787c6f170482523d03ea010ebb0330d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-codec-http/4.1.111.Final/netty-codec-http-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_netty_netty_codec_http2_4_1_111_Final",
        sha256 = "a9eb9b0627041f4891de92aa896499ae334cdf607dba0dcdfafd3516a7d0ecca",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_codec_http2_sources_4_1_111_Final",
        sha256 = "b6212660687ae6e3463cea30ba64e22bbe52054f6aecb90bb636f5f19f1673cc",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-codec-http2/4.1.111.Final/netty-codec-http2-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_netty_netty_common_4_1_111_Final",
        sha256 = "9ae12e9a89f59ce24fb233851fdd93e3c2dfaeb9fa02c60627cd67fa7561871d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_common_sources_4_1_111_Final",
        sha256 = "9241a503ddbb8a4e51e96b9f0c37fcaf3183482ec8dceb85b675aa6e5a47456d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-common/4.1.111.Final/netty-common-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_netty_netty_handler_4_1_111_Final",
        sha256 = "1a034672ca26c8be6245c8e8641b29785ed2c018617bb2dd7e07be39f7ea71fc",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_handler_sources_4_1_111_Final",
        sha256 = "25e611f1494cc4ea7a55d5a834b1c21b83ae8776f40b9b984a54e5b87fd539b9",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-handler/4.1.111.Final/netty-handler-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_netty_netty_resolver_4_1_111_Final",
        sha256 = "78e735746d1f98ca89793176359c97ad5bb6393ec63cd2962f30db43be090e1b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_resolver_sources_4_1_111_Final",
        sha256 = "893c886d17f1c705066f56bb045655a6af0b4b90a2f30cce6da479a17cffde0c",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-resolver/4.1.111.Final/netty-resolver-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_netty_netty_transport_4_1_111_Final",
        sha256 = "49a3cc0b6d342340f0980f047026db74161c19ea2a07753a14b4d22ee0653aa5",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_transport_sources_4_1_111_Final",
        sha256 = "d348b0e4b9f7c9ec21c76c59787a031f3e9b0891466a05902820c7dc76a745da",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-transport/4.1.111.Final/netty-transport-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_netty_netty_transport_classes_epoll_4_1_111_Final",
        sha256 = "d230d1680d1517c7d7b2e25ef6a9a87c22ebbcf264a0da8e807734f15b7c7cae",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_transport_classes_epoll_sources_4_1_111_Final",
        sha256 = "32c9e24ee54f4053eb7db66f7ac2bd01edc4dab4a86f9e4adedfaa78f2b344bd",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-transport-classes-epoll/4.1.111.Final/netty-transport-classes-epoll-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_netty_netty_transport_native_unix_common_4_1_111_Final",
        sha256 = "ec4bd4574ee1ec776a1b14139fb69982ddf7f5039a803082074a81f6604ecad2",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final.jar"],
        downloaded_file_path = "v1/io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final.jar",
    )
    http_file(
        name = "io_netty_netty_transport_native_unix_common_sources_4_1_111_Final",
        sha256 = "5fd095440f24ac27ce637708a19ea9865d060d4449e53bf1b99560017662514f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final-sources.jar"],
        downloaded_file_path = "v1/io/netty/netty-transport-native-unix-common/4.1.111.Final/netty-transport-native-unix-common-4.1.111.Final-sources.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_api_0_31_1",
        sha256 = "f1474d47f4b6b001558ad27b952e35eda5cc7146788877fc52938c6eba24b382",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar", "https://repo.gradle.org/gradle/libs-releases/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_api_sources_0_31_1",
        sha256 = "6748d57aaae81995514ad3e2fb11a95aa88e158b3f93450288018eaccf31e86b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1-sources.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1-sources.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_contrib_http_util_0_31_1",
        sha256 = "3ea995b55a4068be22989b70cc29a4d788c2d328d1d50613a7a9afd13fdd2d0a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar", "https://repo.gradle.org/gradle/libs-releases/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_contrib_http_util_sources_0_31_1",
        sha256 = "d55afd5f96dc724bd903a77a38b0a344d0e59f02a64b9ab2f32618bc582ea924",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1-sources.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1-sources.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_proto_0_2_0",
        sha256 = "0c192d451e9dd74e98721b27d02f0e2b6bca44b51563b5dabf2e211f7a3ebf13",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0.jar", "https://repo.gradle.org/gradle/libs-releases/io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_proto_sources_0_2_0",
        sha256 = "7f077c177e1241e3afec0b42d7f64b89b18c2ef37a29651fc6d2a46315a3ca42",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0-sources.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-proto/0.2.0/opencensus-proto-0.2.0-sources.jar",
    )
    http_file(
        name = "io_perfmark_perfmark_api_0_27_0",
        sha256 = "c7b478503ec524e55df19b424d46d27c8a68aeb801664fadd4f069b71f52d0f6",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0.jar", "https://repo.gradle.org/gradle/libs-releases/io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0.jar"],
        downloaded_file_path = "v1/io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0.jar",
    )
    http_file(
        name = "io_perfmark_perfmark_api_sources_0_27_0",
        sha256 = "311551ab29cf51e5a8abee6a019e88dee47d1ea71deb9fcd3649db9c51b237bc",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0-sources.jar"],
        downloaded_file_path = "v1/io/perfmark/perfmark-api/0.27.0/perfmark-api-0.27.0-sources.jar",
    )
    http_file(
        name = "javax_annotation_javax_annotation_api_1_3_2",
        sha256 = "e04ba5195bcd555dc95650f7cc614d151e4bcd52d29a10b8aa2197f3ab89ab9b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar", "https://repo.gradle.org/gradle/libs-releases/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar"],
        downloaded_file_path = "v1/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar",
    )
    http_file(
        name = "javax_annotation_javax_annotation_api_sources_1_3_2",
        sha256 = "128971e52e0d84a66e3b6e049dab8ad7b2c58b7e1ad37fa2debd3d40c2947b95",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2-sources.jar"],
        downloaded_file_path = "v1/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2-sources.jar",
    )
    http_file(
        name = "javax_inject_javax_inject_1",
        sha256 = "91c77044a50c481636c32d916fd89c9118a72195390452c81065080f957de7ff",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/javax/inject/javax.inject/1/javax.inject-1.jar", "https://repo.gradle.org/gradle/libs-releases/javax/inject/javax.inject/1/javax.inject-1.jar"],
        downloaded_file_path = "v1/javax/inject/javax.inject/1/javax.inject-1.jar",
    )
    http_file(
        name = "javax_inject_javax_inject_sources_1",
        sha256 = "c4b87ee2911c139c3daf498a781967f1eb2e75bc1a8529a2e7b328a15d0e433e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/javax/inject/javax.inject/1/javax.inject-1-sources.jar", "https://repo.gradle.org/gradle/libs-releases/javax/inject/javax.inject/1/javax.inject-1-sources.jar"],
        downloaded_file_path = "v1/javax/inject/javax.inject/1/javax.inject-1-sources.jar",
    )
    http_file(
        name = "org_apache_httpcomponents_httpclient_4_5_14",
        sha256 = "c8bc7e1c51a6d4ce72f40d2ebbabf1c4b68bfe76e732104b04381b493478e9d6",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar"],
        downloaded_file_path = "v1/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar",
    )
    http_file(
        name = "org_apache_httpcomponents_httpclient_sources_4_5_14",
        sha256 = "55b01f9f4cbec9ac646866a4b64b176570d79e293a556796b5b0263d047ef8e6",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14-sources.jar"],
        downloaded_file_path = "v1/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14-sources.jar",
    )
    http_file(
        name = "org_apache_httpcomponents_httpcore_4_4_16",
        sha256 = "6c9b3dd142a09dc468e23ad39aad6f75a0f2b85125104469f026e52a474e464f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar"],
        downloaded_file_path = "v1/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar",
    )
    http_file(
        name = "org_apache_httpcomponents_httpcore_sources_4_4_16",
        sha256 = "705f8cf3671093b6c1db16bbf6971a7ef400e3819784f1af53e5bc3e67b5a9a0",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16-sources.jar"],
        downloaded_file_path = "v1/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16-sources.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_api_1_9_23",
        sha256 = "cf068ecef4342e47f526b180cda0bf754f871f68cc1003592d436ff8bf459549",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_api_sources_1_9_23",
        sha256 = "503487046a18c8e6f8c7106dbdcab6fa1bc7a69a30269992b50ae7d8efd111c4",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-api/1.9.23/maven-resolver-api-1.9.23-sources.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_connector_basic_1_9_23",
        sha256 = "0f83a762d3a9bffbb7304e5149fc6ed6f5870d76eb6eb0a54fef697fce1a17ac",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_connector_basic_sources_1_9_23",
        sha256 = "ef369092391a3a3935f305bc3651d3363a48ab579616df4b7bcbadc7413d90ef",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-connector-basic/1.9.23/maven-resolver-connector-basic-1.9.23-sources.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_impl_1_9_23",
        sha256 = "72d56c9cd0324425ba44554490bae11d7e51a9f9d6bd5cf416d9e31c8a7c2870",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_impl_sources_1_9_23",
        sha256 = "a34c18ffd90b5c5f23d3801dbaf877c3036ff4fa02ee73c2cf9a7f7ac15de06d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-impl/1.9.23/maven-resolver-impl-1.9.23-sources.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_named_locks_1_9_23",
        sha256 = "ec49361d3776c59765b211860e99d47905781db2b5c6ae2025a2eae2da493aed",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_named_locks_sources_1_9_23",
        sha256 = "ed8028b35a2590d66707d5c7e980517c7f0e61f8a5eb04fb5993e30e3e7b9b58",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-named-locks/1.9.23/maven-resolver-named-locks-1.9.23-sources.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_spi_1_9_23",
        sha256 = "755c96916687d115ff6c4fc8b016ac755a2b18bf0ca80ef45e7f5ad76e6c1b89",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_spi_sources_1_9_23",
        sha256 = "f97bbaba9595b408ab2ae82c149341707b3a3049452df689ee509e54ddb72237",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-spi/1.9.23/maven-resolver-spi-1.9.23-sources.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_transport_file_1_9_23",
        sha256 = "4192de597d89a0eaab87af586ed234eb7126d863dc1fee2ee85df42c3401362d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_transport_file_sources_1_9_23",
        sha256 = "cae0724af256319a8391a9036848656c02c67c86d953cade386af4f3d10138f1",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-transport-file/1.9.23/maven-resolver-transport-file-1.9.23-sources.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_transport_http_1_9_23",
        sha256 = "7c4d762c4c604db5c8a9eeadd5c98b8f2e03556b853b48aa3346eb8cef67b54d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_transport_http_sources_1_9_23",
        sha256 = "1c3f4f6d8bdd82af81c11e9ea0dc38c5dd3b99693ec6fc749f0cfe3a640c44d3",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-transport-http/1.9.23/maven-resolver-transport-http-1.9.23-sources.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_util_1_9_23",
        sha256 = "a6b81ce281313cc2adf19e697d59d53a83793d83e33707fc44836c4934f4030f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23.jar",
    )
    http_file(
        name = "org_apache_maven_resolver_maven_resolver_util_sources_1_9_23",
        sha256 = "e6859ae4614e258f4c04285e27755518f25c4acfd7a342b3e1ce291666ac7de9",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/resolver/maven-resolver-util/1.9.23/maven-resolver-util-1.9.23-sources.jar",
    )
    http_file(
        name = "org_apache_maven_shared_maven_shared_utils_3_4_2",
        sha256 = "b613357e1bad4dfc1dead801691c9460f9585fe7c6b466bc25186212d7d18487",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2.jar"],
        downloaded_file_path = "v1/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2.jar",
    )
    http_file(
        name = "org_apache_maven_shared_maven_shared_utils_sources_3_4_2",
        sha256 = "d465ae0a0e2fe3e75802b850dd8d8c87b479876dc250ccea116bc5e7ab79708b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_artifact_3_9_10",
        sha256 = "16d3e7ce7fa5006e8d88d89a699a7c5ebc1f86e853c97cf4ae4b4ba17f2b6811",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_artifact_sources_3_9_10",
        sha256 = "055ae6486d8bcbfebc4bf5be2144d54c5d125c8f1923e2325fb78ed9da9f11ac",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-artifact/3.9.10/maven-artifact-3.9.10-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_builder_support_3_9_10",
        sha256 = "f3ac94e186f77a5be579db9bd3daa3e34cd5e52b9e11c0cb5e23966cc083e168",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_builder_support_sources_3_9_10",
        sha256 = "fe12169b5be2b9689c7f085ea6c31986d59dfea5fc4b5eeae1f6be348e856514",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-builder-support/3.9.10/maven-builder-support-3.9.10-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_core_3_9_10",
        sha256 = "1b562f914ff546c69c16c98e946d3504dbbf1cbff34aa90f84e23188b8219eaf",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-core/3.9.10/maven-core-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-core/3.9.10/maven-core-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-core/3.9.10/maven-core-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_core_sources_3_9_10",
        sha256 = "a588f886a890c14aaa17f5157ec0fa9dbaab51b9e6babc6a16371eb185be4872",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-core/3.9.10/maven-core-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-core/3.9.10/maven-core-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-core/3.9.10/maven-core-3.9.10-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_model_3_9_10",
        sha256 = "d99037fb97de2de24382f3f7ec60a4244b9e960a8adb119b17cb26dcf5512469",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-model/3.9.10/maven-model-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-model/3.9.10/maven-model-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-model/3.9.10/maven-model-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_model_sources_3_9_10",
        sha256 = "7e4444b1f462e357b34319762d8e181689dd433ca058989faca41ab80774357f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-model/3.9.10/maven-model-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-model/3.9.10/maven-model-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-model/3.9.10/maven-model-3.9.10-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_model_builder_3_9_10",
        sha256 = "6a015c5285b6a904e66a3b688e9c7caa5effa26af8de86a1efbf173ced5e4896",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_model_builder_sources_3_9_10",
        sha256 = "054cd1a593de7e269285f1b67e35dd0c6f8e3233c8a1c37ba3bb672d1f90db4e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-model-builder/3.9.10/maven-model-builder-3.9.10-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_plugin_api_3_9_10",
        sha256 = "36c5b4f1eb003b616d12cd19e778d639d3514fbc778b200c4e9d3ca22d7e988a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_plugin_api_sources_3_9_10",
        sha256 = "e768254c5e3bdfd0779417c19ac82425ac6091e87e5587dc12830d1e57ada847",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-plugin-api/3.9.10/maven-plugin-api-3.9.10-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_repository_metadata_3_9_10",
        sha256 = "e968abc00b3ce52af8aa153a1ae8f6178c69cdac5b1ce5b45b79a8af0815ab23",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_repository_metadata_sources_3_9_10",
        sha256 = "d62758014abfe9859b5ca1fffb18bea1126ba981fa3037cb12b0bc9a325f3469",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-repository-metadata/3.9.10/maven-repository-metadata-3.9.10-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_resolver_provider_3_9_10",
        sha256 = "6d7b6fc2ed0673b144d92acd105d457105f14d3b7a51d7531ec05b20ff9798a8",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_resolver_provider_sources_3_9_10",
        sha256 = "e48e01b668f1766bd8551b21b65bb3c9bad00db50a4bde24dc92990260e0050b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-resolver-provider/3.9.10/maven-resolver-provider-3.9.10-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_settings_3_9_10",
        sha256 = "57e97124c757956d24fbd29733f4779aa5a305fff61c05ca6628203362288e91",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_settings_sources_3_9_10",
        sha256 = "f4cff70bf226d0432dc034df7b71f71ff77a8fdd3388a97351b7fdc907cf07bd",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-settings/3.9.10/maven-settings-3.9.10-sources.jar",
    )
    http_file(
        name = "org_apache_maven_maven_settings_builder_3_9_10",
        sha256 = "d52880da6a25d9f4ff23a1961ca7bdbc3b07b30e8af0926841d293fe9eddb63f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10.jar",
    )
    http_file(
        name = "org_apache_maven_maven_settings_builder_sources_3_9_10",
        sha256 = "ce545fc45785412608e9cecee3fd5f6b8ce2857c31e51f3ec7b791ec5411a25a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10-sources.jar"],
        downloaded_file_path = "v1/org/apache/maven/maven-settings-builder/3.9.10/maven-settings-builder-3.9.10-sources.jar",
    )
    http_file(
        name = "org_bouncycastle_bcpg_jdk15on_1_68",
        sha256 = "2251d9c9faa0ee534ab159a6dac1193a69b8a831f0a0e593dc79e34e7e256a4f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68.jar", "https://repo.gradle.org/gradle/libs-releases/org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68.jar"],
        downloaded_file_path = "v1/org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68.jar",
    )
    http_file(
        name = "org_bouncycastle_bcpg_jdk15on_sources_1_68",
        sha256 = "1689a9e2c6629c2b48015def02e7ad531d2fd485bdafbd177502aeeb232f321c",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68-sources.jar"],
        downloaded_file_path = "v1/org/bouncycastle/bcpg-jdk15on/1.68/bcpg-jdk15on-1.68-sources.jar",
    )
    http_file(
        name = "org_bouncycastle_bcprov_jdk15on_1_68",
        sha256 = "f732a46c8de7e2232f2007c682a21d1f4cc8a8a0149b6b7bd6aa1afdc65a0f8d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68.jar", "https://repo.gradle.org/gradle/libs-releases/org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68.jar"],
        downloaded_file_path = "v1/org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68.jar",
    )
    http_file(
        name = "org_bouncycastle_bcprov_jdk15on_sources_1_68",
        sha256 = "d9bb57dd73ae7ae3a3b37fcbee6e91ca87156343123d6d3079712928088fb370",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68-sources.jar"],
        downloaded_file_path = "v1/org/bouncycastle/bcprov-jdk15on/1.68/bcprov-jdk15on-1.68-sources.jar",
    )
    http_file(
        name = "org_checkerframework_checker_qual_3_44_0",
        sha256 = "30ed439602b6c2d4aaea2a85e58e388556f0cc7ae68ed29649bc1cd0c0102cd9",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0.jar", "https://repo.gradle.org/gradle/libs-releases/org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0.jar"],
        downloaded_file_path = "v1/org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0.jar",
    )
    http_file(
        name = "org_checkerframework_checker_qual_sources_3_44_0",
        sha256 = "3a2d87922cb834005ef35d69b2819af9f85c658f7d3a0438e86a15a38eba1dea",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0-sources.jar"],
        downloaded_file_path = "v1/org/checkerframework/checker-qual/3.44.0/checker-qual-3.44.0-sources.jar",
    )
    http_file(
        name = "org_codehaus_mojo_animal_sniffer_annotations_1_23",
        sha256 = "9ffe526bf43a6348e9d8b33b9cd6f580a7f5eed0cf055913007eda263de974d0",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23.jar"],
        downloaded_file_path = "v1/org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23.jar",
    )
    http_file(
        name = "org_codehaus_mojo_animal_sniffer_annotations_sources_1_23",
        sha256 = "4878fcc6808dbc88085a4622db670e703867754bc4bc40312c52bf3a3510d019",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23-sources.jar"],
        downloaded_file_path = "v1/org/codehaus/mojo/animal-sniffer-annotations/1.23/animal-sniffer-annotations-1.23-sources.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_cipher_2_1_0",
        sha256 = "ae34b6dcf0641a8bf5592244aeeeea49b6aa457f1889a68dd98a00a08cf1f38c",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_cipher_sources_2_1_0",
        sha256 = "9a05e2b3b472fcd1ab252270465dec441258736ae6737a70b9730518bb39bee9",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0-sources.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-cipher/2.1.0/plexus-cipher-2.1.0-sources.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_classworlds_2_9_0",
        sha256 = "1ad3292cd563381e3fd632f3fded1988f9e9b2be7a9f3db63ff4c4cedba13fa5",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_classworlds_sources_2_9_0",
        sha256 = "bc97e229e66187c137a0d1744d43daa88fc6aed3c0c7240b855d610c0992ee5a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0-sources.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-classworlds/2.9.0/plexus-classworlds-2.9.0-sources.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_component_annotations_2_2_0",
        sha256 = "50edb93c73786e62822b4fe1336e22880fdf147191373cf5c911370e16748fcf",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_component_annotations_sources_2_2_0",
        sha256 = "205bdeb24bb48dec548232b6faddff33a6d5800a1bafc9ed7e563845fd0eb736",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0-sources.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-component-annotations/2.2.0/plexus-component-annotations-2.2.0-sources.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_interpolation_1_28",
        sha256 = "ab2a8715570438a2e4164d85ad3e8d489eabc38ea5093c2eb8ab7f58403535b5",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_interpolation_sources_1_28",
        sha256 = "f094aa13163afc7bebff3ec277684bc28189cbea3d0f842939e1f9ecb70549ba",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28-sources.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-interpolation/1.28/plexus-interpolation-1.28-sources.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_sec_dispatcher_2_0",
        sha256 = "873139960c4c780176dda580b003a2c4bf82188bdce5bb99234e224ef7acfceb",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_sec_dispatcher_sources_2_0",
        sha256 = "ba4508f478d47717c8aeb41cf0ad9bc67e3c6bc7bf8f8bded2ca77b5885435a2",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0-sources.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-sec-dispatcher/2.0/plexus-sec-dispatcher-2.0-sources.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_utils_3_6_0",
        sha256 = "27ef130e32c236090e408fb5498d94cb9ea26d14070fb1c8985d607b62d098d1",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0.jar",
    )
    http_file(
        name = "org_codehaus_plexus_plexus_utils_sources_3_6_0",
        sha256 = "d4b488c7472f2e9fdfb9294276ea488ca523d978d23216a5a902e8f9b7acc24a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0-sources.jar"],
        downloaded_file_path = "v1/org/codehaus/plexus/plexus-utils/3.6.0/plexus-utils-3.6.0-sources.jar",
    )
    http_file(
        name = "org_conscrypt_conscrypt_openjdk_uber_2_5_2",
        sha256 = "eaf537d98e033d0f0451cd1b8cc74e02d7b55ec882da63c88060d806ba89c348",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2.jar", "https://repo.gradle.org/gradle/libs-releases/org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2.jar"],
        downloaded_file_path = "v1/org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2.jar",
    )
    http_file(
        name = "org_conscrypt_conscrypt_openjdk_uber_sources_2_5_2",
        sha256 = "aa1d02e65351e202e83ece0614bce1022aa1da6e77313ef7c7663ab45fa9e3a5",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2-sources.jar"],
        downloaded_file_path = "v1/org/conscrypt/conscrypt-openjdk-uber/2.5.2/conscrypt-openjdk-uber-2.5.2-sources.jar",
    )
    http_file(
        name = "org_eclipse_sisu_org_eclipse_sisu_inject_0_9_0_M4",
        sha256 = "1cbd7a965a5e2a9ea823bab311962a4e5aa5c240705bdbad5a52b40ffdfa1004",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4.jar", "https://repo.gradle.org/gradle/libs-releases/org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4.jar"],
        downloaded_file_path = "v1/org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4.jar",
    )
    http_file(
        name = "org_eclipse_sisu_org_eclipse_sisu_inject_sources_0_9_0_M4",
        sha256 = "92f9e983447eb06db12bbee37fb5f84a6b4acc49db6668e6c473d3b9852f15a4",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4-sources.jar"],
        downloaded_file_path = "v1/org/eclipse/sisu/org.eclipse.sisu.inject/0.9.0.M4/org.eclipse.sisu.inject-0.9.0.M4-sources.jar",
    )
    http_file(
        name = "org_eclipse_sisu_org_eclipse_sisu_plexus_0_9_0_M4",
        sha256 = "b90579bc652eac7331436e0a25533fce14130b9c6e015f2dd3a3d4bb07e942b7",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4.jar", "https://repo.gradle.org/gradle/libs-releases/org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4.jar"],
        downloaded_file_path = "v1/org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4.jar",
    )
    http_file(
        name = "org_eclipse_sisu_org_eclipse_sisu_plexus_sources_0_9_0_M4",
        sha256 = "cfec21fafbea8f5d1ad12fb60fb3e08aef2c43dcec893092c8188b7e3ef48387",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4-sources.jar"],
        downloaded_file_path = "v1/org/eclipse/sisu/org.eclipse.sisu.plexus/0.9.0.M4/org.eclipse.sisu.plexus-0.9.0.M4-sources.jar",
    )
    http_file(
        name = "org_fusesource_jansi_jansi_2_4_1",
        sha256 = "2e5e775a9dc58ffa6bbd6aa6f099d62f8b62dcdeb4c3c3bbbe5cf2301bc2dcc1",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1.jar", "https://repo.gradle.org/gradle/libs-releases/org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1.jar"],
        downloaded_file_path = "v1/org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1.jar",
    )
    http_file(
        name = "org_fusesource_jansi_jansi_sources_2_4_1",
        sha256 = "f707511567a13ebf8c51164133770eb5a8e023e1d391bfbc6e7a0591c71729b8",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1-sources.jar"],
        downloaded_file_path = "v1/org/fusesource/jansi/jansi/2.4.1/jansi-2.4.1-sources.jar",
    )
    http_file(
        name = "org_gradle_gradle_tooling_api_8_13",
        sha256 = "de8062b7aaf9d2b35df1c62dbb00978c5b41c05c0c30e5f2fd9cf59004f0d5dc",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13.jar", "https://repo.gradle.org/gradle/libs-releases/org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13.jar"],
        downloaded_file_path = "v1/org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13.jar",
    )
    http_file(
        name = "org_gradle_gradle_tooling_api_sources_8_13",
        sha256 = "6667287b0c013d36a980c59f2d0089cec30467f1e8da728eb1bed834b49fd6b9",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13-sources.jar"],
        downloaded_file_path = "v1/org/gradle/gradle-tooling-api/8.13/gradle-tooling-api-8.13-sources.jar",
    )
    http_file(
        name = "org_jspecify_jspecify_1_0_0",
        sha256 = "1fad6e6be7557781e4d33729d49ae1cdc8fdda6fe477bb0cc68ce351eafdfbab",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar", "https://repo.gradle.org/gradle/libs-releases/org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar"],
        downloaded_file_path = "v1/org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar",
    )
    http_file(
        name = "org_jspecify_jspecify_sources_1_0_0",
        sha256 = "adf0898191d55937fb3192ba971826f4f294292c4a960740f3c27310e7b70296",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jspecify/jspecify/1.0.0/jspecify-1.0.0-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/jspecify/jspecify/1.0.0/jspecify-1.0.0-sources.jar"],
        downloaded_file_path = "v1/org/jspecify/jspecify/1.0.0/jspecify-1.0.0-sources.jar",
    )
    http_file(
        name = "org_ow2_asm_asm_9_8",
        sha256 = "876eab6a83daecad5ca67eb9fcabb063c97b5aeb8cf1fca7a989ecde17522051",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/ow2/asm/asm/9.8/asm-9.8.jar", "https://repo.gradle.org/gradle/libs-releases/org/ow2/asm/asm/9.8/asm-9.8.jar"],
        downloaded_file_path = "v1/org/ow2/asm/asm/9.8/asm-9.8.jar",
    )
    http_file(
        name = "org_ow2_asm_asm_sources_9_8",
        sha256 = "c6294794f956f21e2b252a9bf65a96ce0489fb4c0f978447d85c8a75e485b633",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/ow2/asm/asm/9.8/asm-9.8-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/ow2/asm/asm/9.8/asm-9.8-sources.jar"],
        downloaded_file_path = "v1/org/ow2/asm/asm/9.8/asm-9.8-sources.jar",
    )
    http_file(
        name = "org_reactivestreams_reactive_streams_1_0_4",
        sha256 = "f75ca597789b3dac58f61857b9ac2e1034a68fa672db35055a8fb4509e325f28",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar", "https://repo.gradle.org/gradle/libs-releases/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar"],
        downloaded_file_path = "v1/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4.jar",
    )
    http_file(
        name = "org_reactivestreams_reactive_streams_sources_1_0_4",
        sha256 = "5a7a36ae9536698c434ebe119feb374d721210fee68eb821a37ef3859b64b708",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4-sources.jar"],
        downloaded_file_path = "v1/org/reactivestreams/reactive-streams/1.0.4/reactive-streams-1.0.4-sources.jar",
    )
    http_file(
        name = "org_slf4j_jcl_over_slf4j_1_7_36",
        sha256 = "ab57ca8fd223772c17365d121f59e94ecbf0ae59d08c03a3cb5b81071c019195",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36.jar"],
        downloaded_file_path = "v1/org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36.jar",
    )
    http_file(
        name = "org_slf4j_jcl_over_slf4j_sources_1_7_36",
        sha256 = "aa7a3dc5ff8fd8ca2e8b305d54442a99a722af90777227eb3ce4226c2ba47037",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36-sources.jar"],
        downloaded_file_path = "v1/org/slf4j/jcl-over-slf4j/1.7.36/jcl-over-slf4j-1.7.36-sources.jar",
    )
    http_file(
        name = "org_slf4j_jul_to_slf4j_2_0_12",
        sha256 = "84f02864cab866ffb196ed2022b1b8da682ea6fb3d4a161069429e8391ee2979",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12.jar"],
        downloaded_file_path = "v1/org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12.jar",
    )
    http_file(
        name = "org_slf4j_jul_to_slf4j_sources_2_0_12",
        sha256 = "62702e12ff5af75f4125c76403ffb577b54972478e83a1ae075bc5a38db233f7",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12-sources.jar"],
        downloaded_file_path = "v1/org/slf4j/jul-to-slf4j/2.0.12/jul-to-slf4j-2.0.12-sources.jar",
    )
    http_file(
        name = "org_slf4j_log4j_over_slf4j_2_0_12",
        sha256 = "6271f07eeab8f14321dcdfed8d1de9458198eaa3320174923d1ef3ace9048efa",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12.jar"],
        downloaded_file_path = "v1/org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12.jar",
    )
    http_file(
        name = "org_slf4j_log4j_over_slf4j_sources_2_0_12",
        sha256 = "77ff3d616f87fa07545753e3ed767f0d338a8bd4398598e43d8ce09314edcb15",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12-sources.jar"],
        downloaded_file_path = "v1/org/slf4j/log4j-over-slf4j/2.0.12/log4j-over-slf4j-2.0.12-sources.jar",
    )
    http_file(
        name = "org_slf4j_slf4j_api_2_0_12",
        sha256 = "a79502b8abdfbd722846a27691226a4088682d6d35654f9b80e2a9ccacf7ed47",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12.jar"],
        downloaded_file_path = "v1/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12.jar",
    )
    http_file(
        name = "org_slf4j_slf4j_api_sources_2_0_12",
        sha256 = "f05052e5924887edee5ba8228d210e763f85032e2b58245a37fa71e049950787",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12-sources.jar"],
        downloaded_file_path = "v1/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12-sources.jar",
    )
    http_file(
        name = "org_slf4j_slf4j_simple_2_0_12",
        sha256 = "4cd8f3d6236044600e7054da7c124c6d2e9f45eb43c77d4e9b093fe1095edc85",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12.jar"],
        downloaded_file_path = "v1/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12.jar",
    )
    http_file(
        name = "org_slf4j_slf4j_simple_sources_2_0_12",
        sha256 = "b4fca032b643ed51876cc2b3d3acc3a6526558273f6157abc4831f8aed9bea60",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12-sources.jar"],
        downloaded_file_path = "v1/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12-sources.jar",
    )
    http_file(
        name = "org_threeten_threetenbp_1_6_9",
        sha256 = "83fd82658f19984ecb7ca4d9ed96f0cd6a1f07c06d0398b20a3aa2d85929ef37",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/threeten/threetenbp/1.6.9/threetenbp-1.6.9.jar", "https://repo.gradle.org/gradle/libs-releases/org/threeten/threetenbp/1.6.9/threetenbp-1.6.9.jar"],
        downloaded_file_path = "v1/org/threeten/threetenbp/1.6.9/threetenbp-1.6.9.jar",
    )
    http_file(
        name = "org_threeten_threetenbp_sources_1_6_9",
        sha256 = "79e9334cb38e71b9d36e663865829c48cf873fbdeae909c079383b1dab96ebb0",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/org/threeten/threetenbp/1.6.9/threetenbp-1.6.9-sources.jar", "https://repo.gradle.org/gradle/libs-releases/org/threeten/threetenbp/1.6.9/threetenbp-1.6.9-sources.jar"],
        downloaded_file_path = "v1/org/threeten/threetenbp/1.6.9/threetenbp-1.6.9-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_annotations_2_26_12",
        sha256 = "cde91f502b700ca50221c3855ac0f80304b50ea882dabffa25505d756f64d041",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_annotations_sources_2_26_12",
        sha256 = "3c01f7069bd18fbcc9a4130f1249434a8bef44cf7d6518f353fef2c0391c0059",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/annotations/2.26.12/annotations-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_apache_client_2_26_12",
        sha256 = "637f8050259ef49f8cfe96fd7b3f46c0989211896c901a71ccde662d74334c3c",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_apache_client_sources_2_26_12",
        sha256 = "8c5c355c18cb5ef071b29ca96f95f058b135e8db7fddf2458e9a3a2344ea0e8f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/apache-client/2.26.12/apache-client-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_arns_2_26_12",
        sha256 = "d4ae0587d300acba18d1625b50dcdef5c8da082c23d4f36b44ba3bc06db50140",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/arns/2.26.12/arns-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/arns/2.26.12/arns-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/arns/2.26.12/arns-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_arns_sources_2_26_12",
        sha256 = "db9aa3f9eedab1236e7eb7598c3eb0dc089c93f4885931efc7cebf93da698d51",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/arns/2.26.12/arns-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/arns/2.26.12/arns-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/arns/2.26.12/arns-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_auth_2_26_12",
        sha256 = "35b54896c6d1f203258dd3fc584efde08df650ee37df2f99c6d203f6857ce8ad",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/auth/2.26.12/auth-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/auth/2.26.12/auth-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/auth/2.26.12/auth-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_auth_sources_2_26_12",
        sha256 = "a0062b02a758de7c9bb91e2ddc9824e07344d5d1edf8da378e94c056b529415a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/auth/2.26.12/auth-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/auth/2.26.12/auth-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/auth/2.26.12/auth-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_aws_core_2_26_12",
        sha256 = "fa0575f49bc302fc3651016b5fd25e71f2a01884f3c0c1d476deb9672557a054",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_aws_core_sources_2_26_12",
        sha256 = "5b6427875d784d0fc63a9aa51e4e2827d160f8b8a6108e147632112fbe62394f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/aws-core/2.26.12/aws-core-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_aws_query_protocol_2_26_12",
        sha256 = "1fdbf5f3edbd73dd7f2f10fbd8194527994c93e2f64fe757f212ab8631fa5a63",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_aws_query_protocol_sources_2_26_12",
        sha256 = "c3e99ddcb5e7d16ca675acc09e810d57f437d3ea3b0be3ddb862cd5730f5ca10",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/aws-query-protocol/2.26.12/aws-query-protocol-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_aws_xml_protocol_2_26_12",
        sha256 = "f89843e424f5ee36c5a53bf2e249606ac9e57c177319910aa50e0e419dfc2c7d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_aws_xml_protocol_sources_2_26_12",
        sha256 = "24662264adc0797eab17e98fff170efddc82dec5cceaedffe18fedbf57544649",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/aws-xml-protocol/2.26.12/aws-xml-protocol-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_checksums_2_26_12",
        sha256 = "300a62cea9ea191aaf10ab04572c614bcd80bffe7267bd3a6f0b06fe8a392be8",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_checksums_sources_2_26_12",
        sha256 = "748ebfd2edede444a15677c25b1383ce87b2b65cf3eeb31fd25c3c05f38c48c4",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/checksums/2.26.12/checksums-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_checksums_spi_2_26_12",
        sha256 = "e8c27652edfb4942044677cbdb9d94f40255c67e653f5319f1f84d366ccc1044",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_checksums_spi_sources_2_26_12",
        sha256 = "1c93d6dec09d4583d3b048662de376d139bf08ffef2538e357393fb48bbf8a80",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/checksums-spi/2.26.12/checksums-spi-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_crt_core_2_26_12",
        sha256 = "23b2947e8a6ceb6372802ba481bfc5bd5fa0c1647ef9f577596ead6cf2d45893",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_crt_core_sources_2_26_12",
        sha256 = "6712fd773874689e0fdad2059eff8015dbda423842c3c8aad18184a5a984e545",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/crt-core/2.26.12/crt-core-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_endpoints_spi_2_26_12",
        sha256 = "5ec4d35d564201d90d0d952f2e32857a00cf2e8dd0d49274d2b46075172d6935",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_endpoints_spi_sources_2_26_12",
        sha256 = "7f8538bd5885dc8c8095d1c475c660884a199a600c2f19455849e1ff93b97103",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/endpoints-spi/2.26.12/endpoints-spi-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_http_auth_2_26_12",
        sha256 = "99b42b8e66de459d9a40d6bd2ca98624ca5d8146b4834cb50159c986d02f6eb9",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_http_auth_sources_2_26_12",
        sha256 = "f20a5da54bb62e2a05dfe28c67e639d4865825534baa8fbc281c9096ca64e824",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/http-auth/2.26.12/http-auth-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_http_auth_aws_2_26_12",
        sha256 = "1d8552028084b79594a8232f3414746f80704c72d9cf99aab418050a91694da7",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_http_auth_aws_sources_2_26_12",
        sha256 = "43c8ac2db3b3feb54c786bdabe60a6408c902faa65c423c5c96ef82937e9024e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/http-auth-aws/2.26.12/http-auth-aws-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_http_auth_spi_2_26_12",
        sha256 = "a6dbb5d377302c6c9d2f520a6f07be34f9bd12e0f20571e05ca60773d51a79be",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_http_auth_spi_sources_2_26_12",
        sha256 = "0f4d77996b53a07820e24b816a2c043aed97f37726275359d7e4d8509cdb37b2",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/http-auth-spi/2.26.12/http-auth-spi-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_http_client_spi_2_26_12",
        sha256 = "6e1a74c3b865c1942fa857ca190ca48d6f487a2c765e1ca25c5ec4cab6e0c105",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_http_client_spi_sources_2_26_12",
        sha256 = "29fe0c2ef1eae6c922a9810204416d1bf88bdc62e1ff77f622f8a6ef5294ecdf",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/http-client-spi/2.26.12/http-client-spi-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_identity_spi_2_26_12",
        sha256 = "aa3a5968b76b5deb0b068aa99e4b56c47911af20defb4e3fa3cbab3323b11f18",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_identity_spi_sources_2_26_12",
        sha256 = "cfcd89407c0b23a5f374958be18ecc888c6add2a31fca45dcf0f3ed349787bbc",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/identity-spi/2.26.12/identity-spi-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_json_utils_2_26_12",
        sha256 = "77788e5d22ac33bf3afcd7417cb8b8216d9d76d75338ad49b81cf46bfa165e7e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_json_utils_sources_2_26_12",
        sha256 = "f1032931cfbb33402badd2ffa8997eac244987b824abf3107a9b96e5a632eae6",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/json-utils/2.26.12/json-utils-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_metrics_spi_2_26_12",
        sha256 = "fefdfa825083c2bd2b70bf32156163807618010587692b5cf51c212557d8c8ce",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_metrics_spi_sources_2_26_12",
        sha256 = "9ceb780f926040da1f5df3693271d10752fb89524dd9ea46cf3c19f7871d1ebe",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/metrics-spi/2.26.12/metrics-spi-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_netty_nio_client_2_26_12",
        sha256 = "01fb72b5407f5453429ea663a3359730febff4ce9d8a8571b3ff780bb61bfa07",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_netty_nio_client_sources_2_26_12",
        sha256 = "2768ad37d60a36ac08a9d5c0b404be6c80590b9a1191aba2bb7907b2a030b895",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/netty-nio-client/2.26.12/netty-nio-client-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_profiles_2_26_12",
        sha256 = "b481f4f84e021a45bb407f1b14524e3ebd4d07580361f74af9cec2412dea83f3",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_profiles_sources_2_26_12",
        sha256 = "eab71254d2aadd037b3435c7db9bb8d77e07451aceb91508027507c1e5bb9736",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/profiles/2.26.12/profiles-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_protocol_core_2_26_12",
        sha256 = "d31a0e570ef9320bab6028d415b2a76d161af6807391846e4a48f6d9915ee93a",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_protocol_core_sources_2_26_12",
        sha256 = "19142d7156b4e7c86e5a12ede3f8e9546b99d5c8d86c3f103fd8884f92ed714f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/protocol-core/2.26.12/protocol-core-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_regions_2_26_12",
        sha256 = "532e742d04dfedd24cbb87500e146bb0e1da45041fd7bb2756f9a5719dacd024",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/regions/2.26.12/regions-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/regions/2.26.12/regions-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/regions/2.26.12/regions-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_regions_sources_2_26_12",
        sha256 = "2a3a9035fc3f8d72862d7506e1c6322278cd05732c60a044ef856fc5f6701703",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/regions/2.26.12/regions-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/regions/2.26.12/regions-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/regions/2.26.12/regions-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_retries_2_26_12",
        sha256 = "bc203589f446885405569f45ed87afe177ce394032c3c344c10fd3c2b236333d",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/retries/2.26.12/retries-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/retries/2.26.12/retries-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/retries/2.26.12/retries-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_retries_sources_2_26_12",
        sha256 = "8866e41c781a0d6632cac852ec7015f27df312cfee0864ec42a3a7fc99f3d68c",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/retries/2.26.12/retries-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/retries/2.26.12/retries-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/retries/2.26.12/retries-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_retries_spi_2_26_12",
        sha256 = "157023fa50e8c308af3b496a778681d45c1af2d295a1edbb8f1af49146f66620",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_retries_spi_sources_2_26_12",
        sha256 = "23cee31b68f323e2357c2f69c04a1bf526f78c01f69d64662459da1e5b74f6df",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/retries-spi/2.26.12/retries-spi-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_s3_2_26_12",
        sha256 = "1f134d590c0b1bd346339083158c7cd7707cb9345f6369e32f283cf16d83380b",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/s3/2.26.12/s3-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/s3/2.26.12/s3-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/s3/2.26.12/s3-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_s3_sources_2_26_12",
        sha256 = "5babbeffe184b6b97f0eb0c6143a2a2a4469acc0e4bf378078d6449429cbc7de",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/s3/2.26.12/s3-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/s3/2.26.12/s3-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/s3/2.26.12/s3-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_sdk_core_2_26_12",
        sha256 = "424d6fe70f11009b1177a09c02053b223d8571c19b0762c913e773b588206700",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_sdk_core_sources_2_26_12",
        sha256 = "d65ffa3d934009226ce6c36cb1dcb3924efb73bb7eeb78759b48f0dd6474b105",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/sdk-core/2.26.12/sdk-core-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_third_party_jackson_core_2_26_12",
        sha256 = "3c7704454a012d2960b6ac4501c792ee89f36e9c4f2c25872896c0a0a7fa0fb7",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_third_party_jackson_core_sources_2_26_12",
        sha256 = "da39cb34f958ccc6842cc6a0220288ef2ea3308524e6a0b70b3044716c800c7f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/third-party-jackson-core/2.26.12/third-party-jackson-core-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_awssdk_utils_2_26_12",
        sha256 = "6a6374a9da18a5a705d7147fcc0deab80601fb5ca490204863bf330786faa00f",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/utils/2.26.12/utils-2.26.12.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/utils/2.26.12/utils-2.26.12.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/utils/2.26.12/utils-2.26.12.jar",
    )
    http_file(
        name = "software_amazon_awssdk_utils_sources_2_26_12",
        sha256 = "c3cccebea8184d321fd16c774843a11e7a0a64836eeb59396b91ec36008f276e",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/awssdk/utils/2.26.12/utils-2.26.12-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/awssdk/utils/2.26.12/utils-2.26.12-sources.jar"],
        downloaded_file_path = "v1/software/amazon/awssdk/utils/2.26.12/utils-2.26.12-sources.jar",
    )
    http_file(
        name = "software_amazon_eventstream_eventstream_1_0_1",
        sha256 = "0c37d8e696117f02c302191b8110b0d0eb20fa412fce34c3a269ec73c16ce822",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1.jar"],
        downloaded_file_path = "v1/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1.jar",
    )
    http_file(
        name = "software_amazon_eventstream_eventstream_sources_1_0_1",
        sha256 = "8953ddf1af1680008d7ae96877df9fcfff9b8d909998d5c52519dbd583215636",
        netrc = "../rules_jvm_external++maven+rules_jvm_external_deps/netrc",
        urls = ["https://repo1.maven.org/maven2/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1-sources.jar", "https://repo.gradle.org/gradle/libs-releases/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1-sources.jar"],
        downloaded_file_path = "v1/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1-sources.jar",
    )
maven_artifacts = [
    "aopalliance:aopalliance:1.0",
    "aopalliance:aopalliance:1.0:sources",
    "com.fasterxml.jackson.core:jackson-core:2.17.1",
    "com.fasterxml.jackson.core:jackson-core:2.17.1:sources",
    "com.github.jknack:handlebars:4.3.1",
    "com.github.jknack:handlebars:4.3.1:sources",
    "com.google.android:annotations:4.1.1.4",
    "com.google.android:annotations:4.1.1.4:sources",
    "com.google.api-client:google-api-client:2.6.0",
    "com.google.api-client:google-api-client:2.6.0:sources",
    "com.google.api.grpc:gapic-google-cloud-storage-v2:2.40.1-alpha",
    "com.google.api.grpc:gapic-google-cloud-storage-v2:2.40.1-alpha:sources",
    "com.google.api.grpc:grpc-google-cloud-storage-v2:2.40.1-alpha",
    "com.google.api.grpc:grpc-google-cloud-storage-v2:2.40.1-alpha:sources",
    "com.google.api.grpc:proto-google-cloud-storage-v2:2.40.1-alpha",
    "com.google.api.grpc:proto-google-cloud-storage-v2:2.40.1-alpha:sources",
    "com.google.api.grpc:proto-google-common-protos:2.41.0",
    "com.google.api.grpc:proto-google-common-protos:2.41.0:sources",
    "com.google.api.grpc:proto-google-iam-v1:1.36.0",
    "com.google.api.grpc:proto-google-iam-v1:1.36.0:sources",
    "com.google.api:api-common:2.33.0",
    "com.google.api:api-common:2.33.0:sources",
    "com.google.api:gax:2.50.0",
    "com.google.api:gax:2.50.0:sources",
    "com.google.api:gax-grpc:2.50.0",
    "com.google.api:gax-grpc:2.50.0:sources",
    "com.google.api:gax-httpjson:2.50.0",
    "com.google.api:gax-httpjson:2.50.0:sources",
    "com.google.apis:google-api-services-storage:v1-rev20240621-2.0.0",
    "com.google.apis:google-api-services-storage:v1-rev20240621-2.0.0:sources",
    "com.google.auth:google-auth-library-credentials:1.23.0",
    "com.google.auth:google-auth-library-credentials:1.23.0:sources",
    "com.google.auth:google-auth-library-oauth2-http:1.23.0",
    "com.google.auth:google-auth-library-oauth2-http:1.23.0:sources",
    "com.google.auto.value:auto-value-annotations:1.10.4",
    "com.google.auto.value:auto-value-annotations:1.10.4:sources",
    "com.google.cloud:google-cloud-core:2.40.0",
    "com.google.cloud:google-cloud-core:2.40.0:sources",
    "com.google.cloud:google-cloud-core-grpc:2.40.0",
    "com.google.cloud:google-cloud-core-grpc:2.40.0:sources",
    "com.google.cloud:google-cloud-core-http:2.40.0",
    "com.google.cloud:google-cloud-core-http:2.40.0:sources",
    "com.google.cloud:google-cloud-storage:2.40.1",
    "com.google.cloud:google-cloud-storage:2.40.1:sources",
    "com.google.code.findbugs:jsr305:3.0.2",
    "com.google.code.findbugs:jsr305:3.0.2:sources",
    "com.google.code.gson:gson:2.11.0",
    "com.google.code.gson:gson:2.11.0:sources",
    "com.google.errorprone:error_prone_annotations:2.36.0",
    "com.google.errorprone:error_prone_annotations:2.36.0:sources",
    "com.google.googlejavaformat:google-java-format:1.22.0",
    "com.google.googlejavaformat:google-java-format:1.22.0:sources",
    "com.google.guava:failureaccess:1.0.3",
    "com.google.guava:failureaccess:1.0.3:sources",
    "com.google.guava:guava:33.4.8-jre",
    "com.google.guava:guava:33.4.8-jre:sources",
    "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.http-client:google-http-client:1.44.2",
    "com.google.http-client:google-http-client:1.44.2:sources",
    "com.google.http-client:google-http-client-apache-v2:1.44.2",
    "com.google.http-client:google-http-client-apache-v2:1.44.2:sources",
    "com.google.http-client:google-http-client-appengine:1.44.2",
    "com.google.http-client:google-http-client-appengine:1.44.2:sources",
    "com.google.http-client:google-http-client-gson:1.44.2",
    "com.google.http-client:google-http-client-gson:1.44.2:sources",
    "com.google.http-client:google-http-client-jackson2:1.44.2",
    "com.google.http-client:google-http-client-jackson2:1.44.2:sources",
    "com.google.inject:guice:5.1.0:classes",
    "com.google.inject:guice:5.1.0:sources",
    "com.google.j2objc:j2objc-annotations:3.0.0",
    "com.google.j2objc:j2objc-annotations:3.0.0:sources",
    "com.google.oauth-client:google-oauth-client:1.36.0",
    "com.google.oauth-client:google-oauth-client:1.36.0:sources",
    "com.google.protobuf:protobuf-java:3.25.3",
    "com.google.protobuf:protobuf-java:3.25.3:sources",
    "com.google.protobuf:protobuf-java-util:3.25.3",
    "com.google.protobuf:protobuf-java-util:3.25.3:sources",
    "com.google.re2j:re2j:1.7",
    "com.google.re2j:re2j:1.7:sources",
    "commons-codec:commons-codec:1.18.0",
    "commons-codec:commons-codec:1.18.0:sources",
    "commons-logging:commons-logging:1.2",
    "commons-logging:commons-logging:1.2:sources",
    "io.grpc:grpc-alts:1.62.2",
    "io.grpc:grpc-alts:1.62.2:sources",
    "io.grpc:grpc-api:1.62.2",
    "io.grpc:grpc-api:1.62.2:sources",
    "io.grpc:grpc-auth:1.62.2",
    "io.grpc:grpc-auth:1.62.2:sources",
    "io.grpc:grpc-context:1.62.2",
    "io.grpc:grpc-context:1.62.2:sources",
    "io.grpc:grpc-core:1.62.2",
    "io.grpc:grpc-core:1.62.2:sources",
    "io.grpc:grpc-googleapis:1.62.2",
    "io.grpc:grpc-googleapis:1.62.2:sources",
    "io.grpc:grpc-grpclb:1.62.2",
    "io.grpc:grpc-grpclb:1.62.2:sources",
    "io.grpc:grpc-inprocess:1.62.2",
    "io.grpc:grpc-inprocess:1.62.2:sources",
    "io.grpc:grpc-netty-shaded:1.62.2",
    "io.grpc:grpc-netty-shaded:1.62.2:sources",
    "io.grpc:grpc-protobuf:1.62.2",
    "io.grpc:grpc-protobuf:1.62.2:sources",
    "io.grpc:grpc-protobuf-lite:1.62.2",
    "io.grpc:grpc-protobuf-lite:1.62.2:sources",
    "io.grpc:grpc-rls:1.62.2",
    "io.grpc:grpc-rls:1.62.2:sources",
    "io.grpc:grpc-services:1.62.2",
    "io.grpc:grpc-services:1.62.2:sources",
    "io.grpc:grpc-stub:1.62.2",
    "io.grpc:grpc-stub:1.62.2:sources",
    "io.grpc:grpc-util:1.62.2",
    "io.grpc:grpc-util:1.62.2:sources",
    "io.grpc:grpc-xds:1.62.2",
    "io.grpc:grpc-xds:1.62.2:sources",
    "io.netty:netty-buffer:4.1.111.Final",
    "io.netty:netty-buffer:4.1.111.Final:sources",
    "io.netty:netty-codec:4.1.111.Final",
    "io.netty:netty-codec:4.1.111.Final:sources",
    "io.netty:netty-codec-http:4.1.111.Final",
    "io.netty:netty-codec-http:4.1.111.Final:sources",
    "io.netty:netty-codec-http2:4.1.111.Final",
    "io.netty:netty-codec-http2:4.1.111.Final:sources",
    "io.netty:netty-common:4.1.111.Final",
    "io.netty:netty-common:4.1.111.Final:sources",
    "io.netty:netty-handler:4.1.111.Final",
    "io.netty:netty-handler:4.1.111.Final:sources",
    "io.netty:netty-resolver:4.1.111.Final",
    "io.netty:netty-resolver:4.1.111.Final:sources",
    "io.netty:netty-transport:4.1.111.Final",
    "io.netty:netty-transport:4.1.111.Final:sources",
    "io.netty:netty-transport-classes-epoll:4.1.111.Final",
    "io.netty:netty-transport-classes-epoll:4.1.111.Final:sources",
    "io.netty:netty-transport-native-unix-common:4.1.111.Final",
    "io.netty:netty-transport-native-unix-common:4.1.111.Final:sources",
    "io.opencensus:opencensus-api:0.31.1",
    "io.opencensus:opencensus-api:0.31.1:sources",
    "io.opencensus:opencensus-contrib-http-util:0.31.1",
    "io.opencensus:opencensus-contrib-http-util:0.31.1:sources",
    "io.opencensus:opencensus-proto:0.2.0",
    "io.opencensus:opencensus-proto:0.2.0:sources",
    "io.perfmark:perfmark-api:0.27.0",
    "io.perfmark:perfmark-api:0.27.0:sources",
    "javax.annotation:javax.annotation-api:1.3.2",
    "javax.annotation:javax.annotation-api:1.3.2:sources",
    "javax.inject:javax.inject:1",
    "javax.inject:javax.inject:1:sources",
    "org.apache.httpcomponents:httpclient:4.5.14",
    "org.apache.httpcomponents:httpclient:4.5.14:sources",
    "org.apache.httpcomponents:httpcore:4.4.16",
    "org.apache.httpcomponents:httpcore:4.4.16:sources",
    "org.apache.maven.resolver:maven-resolver-api:1.9.23",
    "org.apache.maven.resolver:maven-resolver-api:1.9.23:sources",
    "org.apache.maven.resolver:maven-resolver-connector-basic:1.9.23",
    "org.apache.maven.resolver:maven-resolver-connector-basic:1.9.23:sources",
    "org.apache.maven.resolver:maven-resolver-impl:1.9.23",
    "org.apache.maven.resolver:maven-resolver-impl:1.9.23:sources",
    "org.apache.maven.resolver:maven-resolver-named-locks:1.9.23",
    "org.apache.maven.resolver:maven-resolver-named-locks:1.9.23:sources",
    "org.apache.maven.resolver:maven-resolver-spi:1.9.23",
    "org.apache.maven.resolver:maven-resolver-spi:1.9.23:sources",
    "org.apache.maven.resolver:maven-resolver-transport-file:1.9.23",
    "org.apache.maven.resolver:maven-resolver-transport-file:1.9.23:sources",
    "org.apache.maven.resolver:maven-resolver-transport-http:1.9.23",
    "org.apache.maven.resolver:maven-resolver-transport-http:1.9.23:sources",
    "org.apache.maven.resolver:maven-resolver-util:1.9.23",
    "org.apache.maven.resolver:maven-resolver-util:1.9.23:sources",
    "org.apache.maven.shared:maven-shared-utils:3.4.2",
    "org.apache.maven.shared:maven-shared-utils:3.4.2:sources",
    "org.apache.maven:maven-artifact:3.9.10",
    "org.apache.maven:maven-artifact:3.9.10:sources",
    "org.apache.maven:maven-builder-support:3.9.10",
    "org.apache.maven:maven-builder-support:3.9.10:sources",
    "org.apache.maven:maven-core:3.9.10",
    "org.apache.maven:maven-core:3.9.10:sources",
    "org.apache.maven:maven-model:3.9.10",
    "org.apache.maven:maven-model:3.9.10:sources",
    "org.apache.maven:maven-model-builder:3.9.10",
    "org.apache.maven:maven-model-builder:3.9.10:sources",
    "org.apache.maven:maven-plugin-api:3.9.10",
    "org.apache.maven:maven-plugin-api:3.9.10:sources",
    "org.apache.maven:maven-repository-metadata:3.9.10",
    "org.apache.maven:maven-repository-metadata:3.9.10:sources",
    "org.apache.maven:maven-resolver-provider:3.9.10",
    "org.apache.maven:maven-resolver-provider:3.9.10:sources",
    "org.apache.maven:maven-settings:3.9.10",
    "org.apache.maven:maven-settings:3.9.10:sources",
    "org.apache.maven:maven-settings-builder:3.9.10",
    "org.apache.maven:maven-settings-builder:3.9.10:sources",
    "org.bouncycastle:bcpg-jdk15on:1.68",
    "org.bouncycastle:bcpg-jdk15on:1.68:sources",
    "org.bouncycastle:bcprov-jdk15on:1.68",
    "org.bouncycastle:bcprov-jdk15on:1.68:sources",
    "org.checkerframework:checker-qual:3.44.0",
    "org.checkerframework:checker-qual:3.44.0:sources",
    "org.codehaus.mojo:animal-sniffer-annotations:1.23",
    "org.codehaus.mojo:animal-sniffer-annotations:1.23:sources",
    "org.codehaus.plexus:plexus-cipher:2.1.0",
    "org.codehaus.plexus:plexus-cipher:2.1.0:sources",
    "org.codehaus.plexus:plexus-classworlds:2.9.0",
    "org.codehaus.plexus:plexus-classworlds:2.9.0:sources",
    "org.codehaus.plexus:plexus-component-annotations:2.2.0",
    "org.codehaus.plexus:plexus-component-annotations:2.2.0:sources",
    "org.codehaus.plexus:plexus-interpolation:1.28",
    "org.codehaus.plexus:plexus-interpolation:1.28:sources",
    "org.codehaus.plexus:plexus-sec-dispatcher:2.0",
    "org.codehaus.plexus:plexus-sec-dispatcher:2.0:sources",
    "org.codehaus.plexus:plexus-utils:3.6.0",
    "org.codehaus.plexus:plexus-utils:3.6.0:sources",
    "org.conscrypt:conscrypt-openjdk-uber:2.5.2",
    "org.conscrypt:conscrypt-openjdk-uber:2.5.2:sources",
    "org.eclipse.sisu:org.eclipse.sisu.inject:0.9.0.M4",
    "org.eclipse.sisu:org.eclipse.sisu.inject:0.9.0.M4:sources",
    "org.eclipse.sisu:org.eclipse.sisu.plexus:0.9.0.M4",
    "org.eclipse.sisu:org.eclipse.sisu.plexus:0.9.0.M4:sources",
    "org.fusesource.jansi:jansi:2.4.1",
    "org.fusesource.jansi:jansi:2.4.1:sources",
    "org.gradle:gradle-tooling-api:8.13",
    "org.gradle:gradle-tooling-api:8.13:sources",
    "org.jspecify:jspecify:1.0.0",
    "org.jspecify:jspecify:1.0.0:sources",
    "org.ow2.asm:asm:9.8",
    "org.ow2.asm:asm:9.8:sources",
    "org.reactivestreams:reactive-streams:1.0.4",
    "org.reactivestreams:reactive-streams:1.0.4:sources",
    "org.slf4j:jcl-over-slf4j:1.7.36",
    "org.slf4j:jcl-over-slf4j:1.7.36:sources",
    "org.slf4j:jul-to-slf4j:2.0.12",
    "org.slf4j:jul-to-slf4j:2.0.12:sources",
    "org.slf4j:log4j-over-slf4j:2.0.12",
    "org.slf4j:log4j-over-slf4j:2.0.12:sources",
    "org.slf4j:slf4j-api:2.0.12",
    "org.slf4j:slf4j-api:2.0.12:sources",
    "org.slf4j:slf4j-simple:2.0.12",
    "org.slf4j:slf4j-simple:2.0.12:sources",
    "org.threeten:threetenbp:1.6.9",
    "org.threeten:threetenbp:1.6.9:sources",
    "software.amazon.awssdk:annotations:2.26.12",
    "software.amazon.awssdk:annotations:2.26.12:sources",
    "software.amazon.awssdk:apache-client:2.26.12",
    "software.amazon.awssdk:apache-client:2.26.12:sources",
    "software.amazon.awssdk:arns:2.26.12",
    "software.amazon.awssdk:arns:2.26.12:sources",
    "software.amazon.awssdk:auth:2.26.12",
    "software.amazon.awssdk:auth:2.26.12:sources",
    "software.amazon.awssdk:aws-core:2.26.12",
    "software.amazon.awssdk:aws-core:2.26.12:sources",
    "software.amazon.awssdk:aws-query-protocol:2.26.12",
    "software.amazon.awssdk:aws-query-protocol:2.26.12:sources",
    "software.amazon.awssdk:aws-xml-protocol:2.26.12",
    "software.amazon.awssdk:aws-xml-protocol:2.26.12:sources",
    "software.amazon.awssdk:checksums:2.26.12",
    "software.amazon.awssdk:checksums:2.26.12:sources",
    "software.amazon.awssdk:checksums-spi:2.26.12",
    "software.amazon.awssdk:checksums-spi:2.26.12:sources",
    "software.amazon.awssdk:crt-core:2.26.12",
    "software.amazon.awssdk:crt-core:2.26.12:sources",
    "software.amazon.awssdk:endpoints-spi:2.26.12",
    "software.amazon.awssdk:endpoints-spi:2.26.12:sources",
    "software.amazon.awssdk:http-auth:2.26.12",
    "software.amazon.awssdk:http-auth:2.26.12:sources",
    "software.amazon.awssdk:http-auth-aws:2.26.12",
    "software.amazon.awssdk:http-auth-aws:2.26.12:sources",
    "software.amazon.awssdk:http-auth-spi:2.26.12",
    "software.amazon.awssdk:http-auth-spi:2.26.12:sources",
    "software.amazon.awssdk:http-client-spi:2.26.12",
    "software.amazon.awssdk:http-client-spi:2.26.12:sources",
    "software.amazon.awssdk:identity-spi:2.26.12",
    "software.amazon.awssdk:identity-spi:2.26.12:sources",
    "software.amazon.awssdk:json-utils:2.26.12",
    "software.amazon.awssdk:json-utils:2.26.12:sources",
    "software.amazon.awssdk:metrics-spi:2.26.12",
    "software.amazon.awssdk:metrics-spi:2.26.12:sources",
    "software.amazon.awssdk:netty-nio-client:2.26.12",
    "software.amazon.awssdk:netty-nio-client:2.26.12:sources",
    "software.amazon.awssdk:profiles:2.26.12",
    "software.amazon.awssdk:profiles:2.26.12:sources",
    "software.amazon.awssdk:protocol-core:2.26.12",
    "software.amazon.awssdk:protocol-core:2.26.12:sources",
    "software.amazon.awssdk:regions:2.26.12",
    "software.amazon.awssdk:regions:2.26.12:sources",
    "software.amazon.awssdk:retries:2.26.12",
    "software.amazon.awssdk:retries:2.26.12:sources",
    "software.amazon.awssdk:retries-spi:2.26.12",
    "software.amazon.awssdk:retries-spi:2.26.12:sources",
    "software.amazon.awssdk:s3:2.26.12",
    "software.amazon.awssdk:s3:2.26.12:sources",
    "software.amazon.awssdk:sdk-core:2.26.12",
    "software.amazon.awssdk:sdk-core:2.26.12:sources",
    "software.amazon.awssdk:third-party-jackson-core:2.26.12",
    "software.amazon.awssdk:third-party-jackson-core:2.26.12:sources",
    "software.amazon.awssdk:utils:2.26.12",
    "software.amazon.awssdk:utils:2.26.12:sources",
    "software.amazon.eventstream:eventstream:1.0.1",
    "software.amazon.eventstream:eventstream:1.0.1:sources"
]