load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")
def pinned_maven_install():
    pass
    http_file(
        name = "com_google_auto_factory_auto_factory_1_1_0",
        sha256 = "9b4505cd7a60574d59386672c1d51d6154b803c892677eb909cf4155ebee771f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0.jar"],
        downloaded_file_path = "v1/com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0.jar",
    )
    http_file(
        name = "com_google_auto_factory_auto_factory_sources_1_1_0",
        sha256 = "4f46cc87784c2339d103c3b3d4e19bc03dd3ecca018ea931c61c4b63a4829616",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0-sources.jar"],
        downloaded_file_path = "v1/com/google/auto/factory/auto-factory/1.1.0/auto-factory-1.1.0-sources.jar",
    )
    http_file(
        name = "com_google_auto_service_auto_service_annotations_1_1_1",
        sha256 = "16a76dd00a2650568447f5d6e3a9e2c809d9a42367d56b45215cfb89731f4d24",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1.jar"],
        downloaded_file_path = "v1/com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1.jar",
    )
    http_file(
        name = "com_google_auto_service_auto_service_annotations_sources_1_1_1",
        sha256 = "371bc06d861278f8048157b5172fd5e830410cdd9cddf1cca02cd9ead7b64fff",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1-sources.jar"],
        downloaded_file_path = "v1/com/google/auto/service/auto-service-annotations/1.1.1/auto-service-annotations-1.1.1-sources.jar",
    )
    http_file(
        name = "com_google_auto_value_auto_value_1_11_0",
        sha256 = "aaf8d637bfed3c420436b9facf1b7a88d12c8785374e4202382783005319c2c3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0.jar"],
        downloaded_file_path = "v1/com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0.jar",
    )
    http_file(
        name = "com_google_auto_value_auto_value_sources_1_11_0",
        sha256 = "4bff06fe077d68f964bd5e05f020ed78fd7870730441e403a2eb306360c4890a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0-sources.jar"],
        downloaded_file_path = "v1/com/google/auto/value/auto-value/1.11.0/auto-value-1.11.0-sources.jar",
    )
    http_file(
        name = "com_google_auto_value_auto_value_annotations_1_11_0",
        sha256 = "5a055ce4255333b3346e1a8703da5bf8ff049532286fdcd31712d624abe111dd",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0.jar"],
        downloaded_file_path = "v1/com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0.jar",
    )
    http_file(
        name = "com_google_auto_value_auto_value_annotations_sources_1_11_0",
        sha256 = "d7941e5f19bb38afcfa85350d57e5245856c23c98c2bbe32f6d31b5577f2bc33",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0-sources.jar"],
        downloaded_file_path = "v1/com/google/auto/value/auto-value-annotations/1.11.0/auto-value-annotations-1.11.0-sources.jar",
    )
    http_file(
        name = "com_google_auto_auto_common_1_2_2",
        sha256 = "f50b1ce8a41fad31a8a819c052f8ffa362ea0a3dbe9ef8f7c7dc9a36d4738a59",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/auto-common/1.2.2/auto-common-1.2.2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/auto-common/1.2.2/auto-common-1.2.2.jar"],
        downloaded_file_path = "v1/com/google/auto/auto-common/1.2.2/auto-common-1.2.2.jar",
    )
    http_file(
        name = "com_google_auto_auto_common_sources_1_2_2",
        sha256 = "173f0a89b59e20a3219074a13d1656d7e207391438459521d11b0adcb814769e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/auto-common/1.2.2/auto-common-1.2.2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/auto/auto-common/1.2.2/auto-common-1.2.2-sources.jar"],
        downloaded_file_path = "v1/com/google/auto/auto-common/1.2.2/auto-common-1.2.2-sources.jar",
    )
    http_file(
        name = "com_google_code_findbugs_jsr305_3_0_2",
        sha256 = "766ad2a0783f2687962c8ad74ceecc38a28b9f72a2d085ee438b7813e928d0c7",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar"],
        downloaded_file_path = "v1/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar",
    )
    http_file(
        name = "com_google_code_findbugs_jsr305_sources_3_0_2",
        sha256 = "1c9e85e272d0708c6a591dc74828c71603053b48cc75ae83cce56912a2aa063b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2-sources.jar"],
        downloaded_file_path = "v1/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2-sources.jar",
    )
    http_file(
        name = "com_google_code_gson_gson_2_13_1",
        sha256 = "94855942d4992f112946d3de1c334e709237b8126d8130bf07807c018a4a2120",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/code/gson/gson/2.13.1/gson-2.13.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/code/gson/gson/2.13.1/gson-2.13.1.jar"],
        downloaded_file_path = "v1/com/google/code/gson/gson/2.13.1/gson-2.13.1.jar",
    )
    http_file(
        name = "com_google_code_gson_gson_sources_2_13_1",
        sha256 = "3317b8662ebc2b7a5ff34af2214cd0f8d74d37a9fa9757be494ad8824e96eee7",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/code/gson/gson/2.13.1/gson-2.13.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/code/gson/gson/2.13.1/gson-2.13.1-sources.jar"],
        downloaded_file_path = "v1/com/google/code/gson/gson/2.13.1/gson-2.13.1-sources.jar",
    )
    http_file(
        name = "com_google_dagger_dagger_2_57",
        sha256 = "c094a62e6c2a37f76a55ad8727e21f794396e3f3c8259bf430d5f2c4c5ec44c7",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/dagger/dagger/2.57/dagger-2.57.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/dagger/dagger/2.57/dagger-2.57.jar"],
        downloaded_file_path = "v1/com/google/dagger/dagger/2.57/dagger-2.57.jar",
    )
    http_file(
        name = "com_google_dagger_dagger_sources_2_57",
        sha256 = "93081c8c891350a870505136e2696225baf1913771c00610a4d343facc0ff82f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/dagger/dagger/2.57/dagger-2.57-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/dagger/dagger/2.57/dagger-2.57-sources.jar"],
        downloaded_file_path = "v1/com/google/dagger/dagger/2.57/dagger-2.57-sources.jar",
    )
    http_file(
        name = "com_google_dagger_dagger_compiler_2_57",
        sha256 = "06cd5d3b4f30924afc48009d2f3b4e236599e3657dc0a994f2e3752721f86525",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57.jar"],
        downloaded_file_path = "v1/com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57.jar",
    )
    http_file(
        name = "com_google_dagger_dagger_compiler_sources_2_57",
        sha256 = "d04c9fd58159c8a1caa6ee3eb56a7eec870ad301c61c47beaf6e96a99091cbb5",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57-sources.jar"],
        downloaded_file_path = "v1/com/google/dagger/dagger-compiler/2.57/dagger-compiler-2.57-sources.jar",
    )
    http_file(
        name = "com_google_dagger_dagger_producers_2_57",
        sha256 = "c0034b2b14a1cfc1eb32d1379d9c5f0fc004a2af4f976633f6f3b7477945eac8",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/dagger/dagger-producers/2.57/dagger-producers-2.57.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/dagger/dagger-producers/2.57/dagger-producers-2.57.jar"],
        downloaded_file_path = "v1/com/google/dagger/dagger-producers/2.57/dagger-producers-2.57.jar",
    )
    http_file(
        name = "com_google_dagger_dagger_producers_sources_2_57",
        sha256 = "ef9fe3a9b771fbd7fe9e9eb33172c96fe5403e9207f1f5f389e18120dbf8f538",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/dagger/dagger-producers/2.57/dagger-producers-2.57-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/dagger/dagger-producers/2.57/dagger-producers-2.57-sources.jar"],
        downloaded_file_path = "v1/com/google/dagger/dagger-producers/2.57/dagger-producers-2.57-sources.jar",
    )
    http_file(
        name = "com_google_dagger_dagger_spi_2_57",
        sha256 = "c14cbf028011edc416e49379ffc4ec34ca5c35e1b5d8408ad34141f1c3ae969c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/dagger/dagger-spi/2.57/dagger-spi-2.57.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/dagger/dagger-spi/2.57/dagger-spi-2.57.jar"],
        downloaded_file_path = "v1/com/google/dagger/dagger-spi/2.57/dagger-spi-2.57.jar",
    )
    http_file(
        name = "com_google_dagger_dagger_spi_sources_2_57",
        sha256 = "d73f4c335bc823bef9d35ca5dd89ecb380b272338fabb10f13f33ec26fddc562",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/dagger/dagger-spi/2.57/dagger-spi-2.57-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/dagger/dagger-spi/2.57/dagger-spi-2.57-sources.jar"],
        downloaded_file_path = "v1/com/google/dagger/dagger-spi/2.57/dagger-spi-2.57-sources.jar",
    )
    http_file(
        name = "com_google_devtools_ksp_symbol_processing_api_2_1_21_2_0_2",
        sha256 = "588d17c4ea982b11028c2c82cb1d7300d269023a8068009f9ae9fb2c9c20c6d9",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2.jar"],
        downloaded_file_path = "v1/com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2.jar",
    )
    http_file(
        name = "com_google_devtools_ksp_symbol_processing_api_sources_2_1_21_2_0_2",
        sha256 = "f37f9e6b579ed62aa5741fbd1c862e6b67907a6a49270336838104d43df2505c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2-sources.jar"],
        downloaded_file_path = "v1/com/google/devtools/ksp/symbol-processing-api/2.1.21-2.0.2/symbol-processing-api-2.1.21-2.0.2-sources.jar",
    )
    http_file(
        name = "com_google_errorprone_error_prone_annotations_2_38_0",
        sha256 = "6661d5335090a5fc61dd869d2095bc6c1e2156e3aa47a6e4ababdf64c99a7889",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0.jar"],
        downloaded_file_path = "v1/com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0.jar",
    )
    http_file(
        name = "com_google_errorprone_error_prone_annotations_sources_2_38_0",
        sha256 = "bc4e1535cf5a236ca2e2cfb66d76da4cea99ae52d7447f57a2b05943cac21747",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0-sources.jar"],
        downloaded_file_path = "v1/com/google/errorprone/error_prone_annotations/2.38.0/error_prone_annotations-2.38.0-sources.jar",
    )
    http_file(
        name = "com_google_errorprone_javac_shaded_9_dev_r4023_3",
        sha256 = "65bfccf60986c47fbc17c9ebab0be626afc41741e0a6ec7109e0768817a36f30",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3.jar"],
        downloaded_file_path = "v1/com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3.jar",
    )
    http_file(
        name = "com_google_errorprone_javac_shaded_sources_9_dev_r4023_3",
        sha256 = "cf0fde1aad77ac6e0e2d36a9f9179193ae1707088ba00ffa91fcfb5269304a6a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3-sources.jar"],
        downloaded_file_path = "v1/com/google/errorprone/javac-shaded/9-dev-r4023-3/javac-shaded-9-dev-r4023-3-sources.jar",
    )
    http_file(
        name = "com_google_flogger_flogger_0_9",
        sha256 = "b524636155975d0fbcbe6fe3977c23b35404f48b1458c68779004abba15fc765",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/flogger/flogger/0.9/flogger-0.9.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/flogger/flogger/0.9/flogger-0.9.jar"],
        downloaded_file_path = "v1/com/google/flogger/flogger/0.9/flogger-0.9.jar",
    )
    http_file(
        name = "com_google_flogger_flogger_sources_0_9",
        sha256 = "12f5fc277f8d657fd657c03982f0822b8348f9033c3b33fcf9cd62d6c6b6cf86",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/flogger/flogger/0.9/flogger-0.9-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/flogger/flogger/0.9/flogger-0.9-sources.jar"],
        downloaded_file_path = "v1/com/google/flogger/flogger/0.9/flogger-0.9-sources.jar",
    )
    http_file(
        name = "com_google_flogger_flogger_system_backend_0_9",
        sha256 = "1658db89e5d9e6c6e63565d80ad041466cb330b8686ba972d843686c62852fac",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9.jar"],
        downloaded_file_path = "v1/com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9.jar",
    )
    http_file(
        name = "com_google_flogger_flogger_system_backend_sources_0_9",
        sha256 = "e46768da84b0239ccbdc8bfee4c3d5fec2cd83deabb3130780bc78050c4897b5",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9-sources.jar"],
        downloaded_file_path = "v1/com/google/flogger/flogger-system-backend/0.9/flogger-system-backend-0.9-sources.jar",
    )
    http_file(
        name = "com_google_googlejavaformat_google_java_format_1_5",
        sha256 = "aa19ad7850fb85178aa22f2fddb163b84d6ce4d0035872f30d4408195ca1144e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5.jar"],
        downloaded_file_path = "v1/com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5.jar",
    )
    http_file(
        name = "com_google_googlejavaformat_google_java_format_sources_1_5",
        sha256 = "c204b15b3834128d335f17213f7e621ddb2cc5bfff5b8dd035cd1f2affb7fa8f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5-sources.jar"],
        downloaded_file_path = "v1/com/google/googlejavaformat/google-java-format/1.5/google-java-format-1.5-sources.jar",
    )
    http_file(
        name = "com_google_guava_failureaccess_1_0_3",
        sha256 = "cbfc3906b19b8f55dd7cfd6dfe0aa4532e834250d7f080bd8d211a3e246b59cb",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar"],
        downloaded_file_path = "v1/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3.jar",
    )
    http_file(
        name = "com_google_guava_failureaccess_sources_1_0_3",
        sha256 = "6fef4dfd2eb9f961655f2a3c4ea87c023618d9fcbfb6b104c17862e5afe66b97",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3-sources.jar"],
        downloaded_file_path = "v1/com/google/guava/failureaccess/1.0.3/failureaccess-1.0.3-sources.jar",
    )
    http_file(
        name = "com_google_guava_guava_33_4_8_jre",
        sha256 = "f3d7f57f67fd622f4d468dfdd692b3a5e3909246c28017ac3263405f0fe617ed",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar"],
        downloaded_file_path = "v1/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre.jar",
    )
    http_file(
        name = "com_google_guava_guava_sources_33_4_8_jre",
        sha256 = "9d3c6aad893daac9d4812eb9fa4c3f7956a9f2e472eb7df2fea0e467fed7e766",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre-sources.jar"],
        downloaded_file_path = "v1/com/google/guava/guava/33.4.8-jre/guava-33.4.8-jre-sources.jar",
    )
    http_file(
        name = "com_google_guava_guava_testlib_33_4_8_jre",
        sha256 = "a58a38746f97e02ae3d067b74a25ad2b136650227baa7124ce03fa3bce4e8576",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre.jar"],
        downloaded_file_path = "v1/com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre.jar",
    )
    http_file(
        name = "com_google_guava_guava_testlib_sources_33_4_8_jre",
        sha256 = "bd55e44db8e2cf41c4fbdbddd33d809eccf2b4744ddcd6bf1c58303f405f45e8",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre-sources.jar"],
        downloaded_file_path = "v1/com/google/guava/guava-testlib/33.4.8-jre/guava-testlib-33.4.8-jre-sources.jar",
    )
    http_file(
        name = "com_google_guava_listenablefuture_9999_0_empty_to_avoid_conflict_with_guava",
        sha256 = "b372a037d4230aa57fbeffdef30fd6123f9c0c2db85d0aced00c91b974f33f99",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"],
        downloaded_file_path = "v1/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_1_47_1",
        sha256 = "22447fde9f2e33e27a23a25953b1c622ead6c055c761fde6ca50573c9473457a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_sources_1_47_1",
        sha256 = "7fd1f5c075db280e2a878bf14c7da248e2984e6411a509bcad3e667dd3dd936d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1-sources.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client/1.47.1/google-http-client-1.47.1-sources.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_gson_1_47_1",
        sha256 = "64ac2b1313dca6b6fc9bd14128ab186528fe992b094f5371fa7f828eed8903bd",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_gson_sources_1_47_1",
        sha256 = "f1cd1055487dd85133f3665645c9a06a892664a500b6fe7a3e4eef4c2cefdbaf",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1-sources.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-gson/1.47.1/google-http-client-gson-1.47.1-sources.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_test_1_47_1",
        sha256 = "ec54daf64c976535f5b63a32a76b84a3a1d54a7728ab95b0752b7e2c8c273106",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1.jar",
    )
    http_file(
        name = "com_google_http_client_google_http_client_test_sources_1_47_1",
        sha256 = "4f044d7afd8c7a4878c8962ed7470a1306b29d40d00f331c6aed1c38df8495ee",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1-sources.jar"],
        downloaded_file_path = "v1/com/google/http-client/google-http-client-test/1.47.1/google-http-client-test-1.47.1-sources.jar",
    )
    http_file(
        name = "com_google_j2objc_j2objc_annotations_3_0_0",
        sha256 = "88241573467ddca44ffd4d74aa04c2bbfd11bf7c17e0c342c94c9de7a70a7c64",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar"],
        downloaded_file_path = "v1/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar",
    )
    http_file(
        name = "com_google_j2objc_j2objc_annotations_sources_3_0_0",
        sha256 = "bd60019a0423c3a025ef6ab24fe0761f5f45ffb48a8cca74a01b678de1105d38",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0-sources.jar"],
        downloaded_file_path = "v1/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0-sources.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_java_4_32_0_RC2",
        sha256 = "027dd8de3d607b4ac0abc800693ac760bb5bf1a88f826a974fdc2c0e1674a56b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_java_sources_4_32_0_RC2",
        sha256 = "7144ae8d056d47104f7960e73290ac49e1bc4e9f17399cc6891c752bcc58c030",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2-sources.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-java/4.32.0-RC2/protobuf-java-4.32.0-RC2-sources.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_java_util_4_32_0_RC2",
        sha256 = "4436ea2e0f5c721ee159925494f3496495d1d91dfb47497e1cea68e703e0c452",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_java_util_sources_4_32_0_RC2",
        sha256 = "91e92f090cc57abd22714bbe046f902c9f773324299e050c14000d0941f8b970",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2-sources.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-java-util/4.32.0-RC2/protobuf-java-util-4.32.0-RC2-sources.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_javalite_4_32_0_RC2",
        sha256 = "ee87fb4181b201f98c9821934f5326ed115930540a224675a9ab64067fbbec0e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_javalite_sources_4_32_0_RC2",
        sha256 = "5fa6524441ea169a527a860b03d669cd2f5ebf8f2a8a8ed8c18f4ba45cc05468",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2-sources.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-javalite/4.32.0-RC2/protobuf-javalite-4.32.0-RC2-sources.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_kotlin_4_32_0_RC2",
        sha256 = "2a0d008ff2d4edd9a7f7024068dc49ad62526f9bffc08d416b3d8c6c969be074",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_kotlin_sources_4_32_0_RC2",
        sha256 = "60eba663a9e2a716745593a3f814d146efc8d730864e828062dfff77b1f25bf4",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2-sources.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-kotlin/4.32.0-RC2/protobuf-kotlin-4.32.0-RC2-sources.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_kotlin_lite_4_32_0_RC2",
        sha256 = "2946c6526850e36c5de98e03fcd3cba8d90cdd73a2c21c2f79eb04d28a008da5",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_kotlin_lite_sources_4_32_0_RC2",
        sha256 = "8781408919b04114e2e7535e8b7b1053f1dd0bbd0e771c902232be0cdde8c6d6",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2-sources.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-kotlin-lite/4.32.0-RC2/protobuf-kotlin-lite-4.32.0-RC2-sources.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_lite_3_0_1",
        sha256 = "1413393db84e4adef79b2997d9dfeb4793d8f93d196f8347808d15711f0bc69c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_lite_sources_3_0_1",
        sha256 = "b3331d42ffeb8089878e769074e30a2468bd84a85f13ca5044c5a731c35d3997",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1-sources.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-lite/3.0.1/protobuf-lite-3.0.1-sources.jar",
    )
    http_file(
        name = "com_google_truth_truth_1_4_4",
        sha256 = "52c86cddadc31bc8457c1e15689fc6b75e2e97ce2a83d8b54b795d556d489f8c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/truth/truth/1.4.4/truth-1.4.4.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/truth/truth/1.4.4/truth-1.4.4.jar"],
        downloaded_file_path = "v1/com/google/truth/truth/1.4.4/truth-1.4.4.jar",
    )
    http_file(
        name = "com_google_truth_truth_sources_1_4_4",
        sha256 = "32da2ce3fd5f2622cda8bdecc316ee1634b376a8a330c910e4e46831f2c7a4f3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/truth/truth/1.4.4/truth-1.4.4-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/google/truth/truth/1.4.4/truth-1.4.4-sources.jar"],
        downloaded_file_path = "v1/com/google/truth/truth/1.4.4/truth-1.4.4-sources.jar",
    )
    http_file(
        name = "com_nativelibs4java_jnaerator_runtime_0_12",
        sha256 = "b0c1d95825285c7eff122ad9bc044079ee106d2efb5409f1faeb1e7a92b6ebc9",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12.jar"],
        downloaded_file_path = "v1/com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12.jar",
    )
    http_file(
        name = "com_nativelibs4java_jnaerator_runtime_sources_0_12",
        sha256 = "1a2757dfb49aae74bd6186eb0883ab54f1470a7b97d15c1bea1aac5a83e4f963",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12-sources.jar"],
        downloaded_file_path = "v1/com/nativelibs4java/jnaerator-runtime/0.12/jnaerator-runtime-0.12-sources.jar",
    )
    http_file(
        name = "com_nativelibs4java_ochafik_util_0_12",
        sha256 = "6387b61e01b1d8641ddce36497e4d08756227b60d7b9071063820017043ecd1a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12.jar"],
        downloaded_file_path = "v1/com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12.jar",
    )
    http_file(
        name = "com_nativelibs4java_ochafik_util_sources_0_12",
        sha256 = "f1007178417ccb001bc3e88d71e0770e1e5eb1143a79822c0f52992df0dce8be",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12-sources.jar"],
        downloaded_file_path = "v1/com/nativelibs4java/ochafik-util/0.12/ochafik-util-0.12-sources.jar",
    )
    http_file(
        name = "com_squareup_javapoet_1_13_0",
        sha256 = "4c7517e848a71b36d069d12bb3bf46a70fd4cda3105d822b0ed2e19c00b69291",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar"],
        downloaded_file_path = "v1/com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar",
    )
    http_file(
        name = "com_squareup_javapoet_sources_1_13_0",
        sha256 = "d1699067787846453fdcc104aeba3946f070fb2c167cfb3445838e4c86bb1f11",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/squareup/javapoet/1.13.0/javapoet-1.13.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/squareup/javapoet/1.13.0/javapoet-1.13.0-sources.jar"],
        downloaded_file_path = "v1/com/squareup/javapoet/1.13.0/javapoet-1.13.0-sources.jar",
    )
    http_file(
        name = "com_squareup_kotlinpoet_1_11_0",
        sha256 = "2887ada1ca03dd83baa2758640d87e840d1907564db0ef88d2289c868a980492",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0.jar"],
        downloaded_file_path = "v1/com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0.jar",
    )
    http_file(
        name = "com_squareup_kotlinpoet_sources_1_11_0",
        sha256 = "240a885acd6e3f3852644dc9e5820f4330fa2656b2c2c68ce67b04ae829dc89d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0-sources.jar"],
        downloaded_file_path = "v1/com/squareup/kotlinpoet/1.11.0/kotlinpoet-1.11.0-sources.jar",
    )
    http_file(
        name = "com_vaadin_external_google_android_json_0_0_20131108_vaadin1",
        sha256 = "dfb7bae2f404cfe0b72b4d23944698cb716b7665171812a0a4d0f5926c0fac79",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.jar"],
        downloaded_file_path = "v1/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.jar",
    )
    http_file(
        name = "com_vaadin_external_google_android_json_sources_0_0_20131108_vaadin1",
        sha256 = "54c781eea645c450cbbc4a5a1b5a474745465452cec1354cb567b781ea6622c3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1-sources.jar"],
        downloaded_file_path = "v1/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1-sources.jar",
    )
    http_file(
        name = "commons_codec_commons_codec_1_19_0",
        sha256 = "5c3881e4f556855e9c532927ee0c9dfde94cc66760d5805c031a59887070af5f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/commons-codec/commons-codec/1.19.0/commons-codec-1.19.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/commons-codec/commons-codec/1.19.0/commons-codec-1.19.0.jar"],
        downloaded_file_path = "v1/commons-codec/commons-codec/1.19.0/commons-codec-1.19.0.jar",
    )
    http_file(
        name = "commons_codec_commons_codec_sources_1_19_0",
        sha256 = "b0462142585d45fc15bc8091b7b02f1e3a85c83595068659548c82cac9cdc7a2",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/commons-codec/commons-codec/1.19.0/commons-codec-1.19.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/commons-codec/commons-codec/1.19.0/commons-codec-1.19.0-sources.jar"],
        downloaded_file_path = "v1/commons-codec/commons-codec/1.19.0/commons-codec-1.19.0-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_api_1_70_0",
        sha256 = "45faf2ac1bf2791e8fdabce53684a86b62c99b84cba26fb13a5ba3f4abf80d6c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-api/1.70.0/grpc-api-1.70.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/grpc/grpc-api/1.70.0/grpc-api-1.70.0.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-api/1.70.0/grpc-api-1.70.0.jar",
    )
    http_file(
        name = "io_grpc_grpc_api_sources_1_70_0",
        sha256 = "4797fb5b5fb495df9da6995792167862cef60ed2e392776c434d5df4098f1168",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-api/1.70.0/grpc-api-1.70.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/grpc/grpc-api/1.70.0/grpc-api-1.70.0-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-api/1.70.0/grpc-api-1.70.0-sources.jar",
    )
    http_file(
        name = "io_grpc_grpc_context_1_70_0",
        sha256 = "eb2824831c0ac03e741efda86b141aa863a481ebc4aaf5a5c1f13a481dbb40ff",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-context/1.70.0/grpc-context-1.70.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/grpc/grpc-context/1.70.0/grpc-context-1.70.0.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-context/1.70.0/grpc-context-1.70.0.jar",
    )
    http_file(
        name = "io_grpc_grpc_context_sources_1_70_0",
        sha256 = "419603fecc423fb2704c67dd7ad91fdf51637f004fc114dfb9f42d225e8ce40b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/grpc/grpc-context/1.70.0/grpc-context-1.70.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/grpc/grpc-context/1.70.0/grpc-context-1.70.0-sources.jar"],
        downloaded_file_path = "v1/io/grpc/grpc-context/1.70.0/grpc-context-1.70.0-sources.jar",
    )
    http_file(
        name = "io_ktor_ktor_events_3_2_3",
        sha256 = "44d19b95ee684c26889ed56008cd70f8fe2ccf313f6676c653d0e366d51a09de",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-events/3.2.3/ktor-events-3.2.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-events/3.2.3/ktor-events-3.2.3.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-events/3.2.3/ktor-events-3.2.3.jar",
    )
    http_file(
        name = "io_ktor_ktor_events_sources_3_2_3",
        sha256 = "25a4d89461d074f84b3f77d8bfe875832adfe744c83aee283f4586f37acdffc3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-events/3.2.3/ktor-events-3.2.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-events/3.2.3/ktor-events-3.2.3-sources.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-events/3.2.3/ktor-events-3.2.3-sources.jar",
    )
    http_file(
        name = "io_ktor_ktor_http_3_2_3",
        sha256 = "c77fdc26a5d31986c7cdfa7d873428bb75aa185887de1670cee54d1d02f413fa",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-http/3.2.3/ktor-http-3.2.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-http/3.2.3/ktor-http-3.2.3.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-http/3.2.3/ktor-http-3.2.3.jar",
    )
    http_file(
        name = "io_ktor_ktor_http_sources_3_2_3",
        sha256 = "e039306a7669d33c0fa8fda363f1ab37866c8c95a4bb1c4fe24d890b8920f986",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-http/3.2.3/ktor-http-3.2.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-http/3.2.3/ktor-http-3.2.3-sources.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-http/3.2.3/ktor-http-3.2.3-sources.jar",
    )
    http_file(
        name = "io_ktor_ktor_http_cio_3_2_3",
        sha256 = "d5bed64d01114adcc2bb0b4c8953e1444511d652c2e4375258d1c56b5ba701ff",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3.jar",
    )
    http_file(
        name = "io_ktor_ktor_http_cio_sources_3_2_3",
        sha256 = "9561057b2f4e7a47f3f64085fa0d38b1e0351cbd062dc155e5ca8681fd7299b2",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3-sources.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-http-cio/3.2.3/ktor-http-cio-3.2.3-sources.jar",
    )
    http_file(
        name = "io_ktor_ktor_io_3_2_3",
        sha256 = "0fc0d380b69c306f8bab2b701c814b463a01987f898e14949cf9b268da36185b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-io/3.2.3/ktor-io-3.2.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-io/3.2.3/ktor-io-3.2.3.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-io/3.2.3/ktor-io-3.2.3.jar",
    )
    http_file(
        name = "io_ktor_ktor_io_sources_3_2_3",
        sha256 = "d23380eb3bd69de6cf5a4a1862b0e1a06806429055b4661ff3d2d18d1cc50a49",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-io/3.2.3/ktor-io-3.2.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-io/3.2.3/ktor-io-3.2.3-sources.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-io/3.2.3/ktor-io-3.2.3-sources.jar",
    )
    http_file(
        name = "io_ktor_ktor_serialization_3_2_3",
        sha256 = "cfce9ba600d909b87221840ff2672685d91ccda738bd627a7c71318f732ea3b3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3.jar",
    )
    http_file(
        name = "io_ktor_ktor_serialization_sources_3_2_3",
        sha256 = "ddc84dd1f279b1c7cd2e476a2b214c1960577116e7abe15c469f8b37fbb46483",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3-sources.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-serialization/3.2.3/ktor-serialization-3.2.3-sources.jar",
    )
    http_file(
        name = "io_ktor_ktor_server_core_3_2_3",
        sha256 = "d440015063ec55bab257c323725b74a5d6cc42c760afa417fa94682496252216",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3.jar",
    )
    http_file(
        name = "io_ktor_ktor_server_core_sources_3_2_3",
        sha256 = "f7b5f42daa905c1d144d42495d54d7f6b8de5bff51eb6e0984ecb139a76bcfa1",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3-sources.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-server-core/3.2.3/ktor-server-core-3.2.3-sources.jar",
    )
    http_file(
        name = "io_ktor_ktor_server_netty_3_2_3",
        sha256 = "089e24417bf9f6c34eec5720eebb2cbcc82ea2855ecef90133eb17e1079d5868",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3.jar",
    )
    http_file(
        name = "io_ktor_ktor_server_netty_sources_3_2_3",
        sha256 = "c6deada2fac53b8ea6523dbda77597b128006674616f140f04df23264c6d1aa3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3-sources.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-server-netty/3.2.3/ktor-server-netty-3.2.3-sources.jar",
    )
    http_file(
        name = "io_ktor_ktor_utils_3_2_3",
        sha256 = "1e55afd47df15c3ca5527d8252174ce6cf5d71c4af742ba4a8142c67c62caef3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3.jar",
    )
    http_file(
        name = "io_ktor_ktor_utils_sources_3_2_3",
        sha256 = "0d24ff0b940d76428edd73256185c917c4b9dabd36580bf52264c45aac26ad57",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3-sources.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-utils/3.2.3/ktor-utils-3.2.3-sources.jar",
    )
    http_file(
        name = "io_ktor_ktor_websockets_3_2_3",
        sha256 = "82a95ddb9baf163e4036ee2d66e68a28315c5fd2eeeb97eaa21d80a45f024e88",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3.jar",
    )
    http_file(
        name = "io_ktor_ktor_websockets_sources_3_2_3",
        sha256 = "8c7a3618e96340c5e3e6b96fac04da5259b9efed986f3b004391b555d0f89e3b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3-sources.jar"],
        downloaded_file_path = "v1/io/ktor/ktor-websockets/3.2.3/ktor-websockets-3.2.3-sources.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_api_0_31_1",
        sha256 = "f1474d47f4b6b001558ad27b952e35eda5cc7146788877fc52938c6eba24b382",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_api_sources_0_31_1",
        sha256 = "6748d57aaae81995514ad3e2fb11a95aa88e158b3f93450288018eaccf31e86b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1-sources.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-api/0.31.1/opencensus-api-0.31.1-sources.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_contrib_http_util_0_31_1",
        sha256 = "3ea995b55a4068be22989b70cc29a4d788c2d328d1d50613a7a9afd13fdd2d0a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1.jar",
    )
    http_file(
        name = "io_opencensus_opencensus_contrib_http_util_sources_0_31_1",
        sha256 = "d55afd5f96dc724bd903a77a38b0a344d0e59f02a64b9ab2f32618bc582ea924",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1-sources.jar"],
        downloaded_file_path = "v1/io/opencensus/opencensus-contrib-http-util/0.31.1/opencensus-contrib-http-util-0.31.1-sources.jar",
    )
    http_file(
        name = "jakarta_inject_jakarta_inject_api_2_0_1",
        sha256 = "f7dc98062fccf14126abb751b64fab12c312566e8cbdc8483598bffcea93af7c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar"],
        downloaded_file_path = "v1/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar",
    )
    http_file(
        name = "jakarta_inject_jakarta_inject_api_sources_2_0_1",
        sha256 = "44f4c73fda69f8b7d87136f0f789f042f54e8ff506d40aa126199baf3752d1c9",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1-sources.jar"],
        downloaded_file_path = "v1/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1-sources.jar",
    )
    http_file(
        name = "javax_annotation_jsr250_api_1_0",
        sha256 = "a1a922d0d9b6d183ed3800dfac01d1e1eb159f0e8c6f94736931c1def54a941f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/javax/annotation/jsr250-api/1.0/jsr250-api-1.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/javax/annotation/jsr250-api/1.0/jsr250-api-1.0.jar"],
        downloaded_file_path = "v1/javax/annotation/jsr250-api/1.0/jsr250-api-1.0.jar",
    )
    http_file(
        name = "javax_annotation_jsr250_api_sources_1_0",
        sha256 = "025c47d76c60199381be07012a0c5f9e74661aac5bd67f5aec847741c5b7f838",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/javax/annotation/jsr250-api/1.0/jsr250-api-1.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/javax/annotation/jsr250-api/1.0/jsr250-api-1.0-sources.jar"],
        downloaded_file_path = "v1/javax/annotation/jsr250-api/1.0/jsr250-api-1.0-sources.jar",
    )
    http_file(
        name = "javax_inject_javax_inject_1",
        sha256 = "91c77044a50c481636c32d916fd89c9118a72195390452c81065080f957de7ff",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/javax/inject/javax.inject/1/javax.inject-1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/javax/inject/javax.inject/1/javax.inject-1.jar"],
        downloaded_file_path = "v1/javax/inject/javax.inject/1/javax.inject-1.jar",
    )
    http_file(
        name = "javax_inject_javax_inject_sources_1",
        sha256 = "c4b87ee2911c139c3daf498a781967f1eb2e75bc1a8529a2e7b328a15d0e433e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/javax/inject/javax.inject/1/javax.inject-1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/javax/inject/javax.inject/1/javax.inject-1-sources.jar"],
        downloaded_file_path = "v1/javax/inject/javax.inject/1/javax.inject-1-sources.jar",
    )
    http_file(
        name = "junit_junit_4_13_2",
        sha256 = "8e495b634469d64fb8acfa3495a065cbacc8a0fff55ce1e31007be4c16dc57d3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/junit/junit/4.13.2/junit-4.13.2.jar"],
        downloaded_file_path = "v1/junit/junit/4.13.2/junit-4.13.2.jar",
    )
    http_file(
        name = "junit_junit_sources_4_13_2",
        sha256 = "34181df6482d40ea4c046b063cb53c7ffae94bdf1b1d62695bdf3adf9dea7e3a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/junit/junit/4.13.2/junit-4.13.2-sources.jar"],
        downloaded_file_path = "v1/junit/junit/4.13.2/junit-4.13.2-sources.jar",
    )
    http_file(
        name = "net_bytebuddy_byte_buddy_1_17_5",
        sha256 = "71568c9f8396677219f650268fbf6493ded484edcdbdf2dae6129ca5be81e8db",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5.jar"],
        downloaded_file_path = "v1/net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5.jar",
    )
    http_file(
        name = "net_bytebuddy_byte_buddy_sources_1_17_5",
        sha256 = "722eaa17b935a25e994f625b32ec166c53d23770d0659c615e62a0b26ce71615",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5-sources.jar"],
        downloaded_file_path = "v1/net/bytebuddy/byte-buddy/1.17.5/byte-buddy-1.17.5-sources.jar",
    )
    http_file(
        name = "net_bytebuddy_byte_buddy_agent_1_17_5",
        sha256 = "c5b9334ad82e632f6af60df22bbbdbbb62cee04877f4f43c38ba04aed9bd9901",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5.jar"],
        downloaded_file_path = "v1/net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5.jar",
    )
    http_file(
        name = "net_bytebuddy_byte_buddy_agent_sources_1_17_5",
        sha256 = "40497a5ae2d6fb5931b4e00325ce747a6fb253996afb231b404dfadbe2811e68",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5-sources.jar"],
        downloaded_file_path = "v1/net/bytebuddy/byte-buddy-agent/1.17.5/byte-buddy-agent-1.17.5-sources.jar",
    )
    http_file(
        name = "net_java_dev_jna_jna_5_10_0",
        sha256 = "e335c10679f743207d822c5f7948e930319835492575a9dba6b94f8a3b96fcc8",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/java/dev/jna/jna/5.10.0/jna-5.10.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/java/dev/jna/jna/5.10.0/jna-5.10.0.jar"],
        downloaded_file_path = "v1/net/java/dev/jna/jna/5.10.0/jna-5.10.0.jar",
    )
    http_file(
        name = "net_java_dev_jna_jna_sources_5_10_0",
        sha256 = "b8dcd308699f6f4f75c545d10c4372d71b49316c1c014dc79ed28dfccfadd36e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/java/dev/jna/jna/5.10.0/jna-5.10.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/java/dev/jna/jna/5.10.0/jna-5.10.0-sources.jar"],
        downloaded_file_path = "v1/net/java/dev/jna/jna/5.10.0/jna-5.10.0-sources.jar",
    )
    http_file(
        name = "net_java_jinput_jinput_2_0_9",
        sha256 = "ff585bab64455a4b2bd2048821762c1a96be4d076a8e8729f9432194af288b4c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/java/jinput/jinput/2.0.9/jinput-2.0.9.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/java/jinput/jinput/2.0.9/jinput-2.0.9.jar"],
        downloaded_file_path = "v1/net/java/jinput/jinput/2.0.9/jinput-2.0.9.jar",
    )
    http_file(
        name = "net_java_jinput_jinput_natives_all_2_0_9",
        sha256 = "a6c17ab8db8de19a1ca2c877516a3594d1fcecf2dc3427bda83794e17dd97b5e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/java/jinput/jinput/2.0.9/jinput-2.0.9-natives-all.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/java/jinput/jinput/2.0.9/jinput-2.0.9-natives-all.jar"],
        downloaded_file_path = "v1/net/java/jinput/jinput/2.0.9/jinput-2.0.9-natives-all.jar",
    )
    http_file(
        name = "net_java_jinput_jinput_sources_2_0_9",
        sha256 = "d39227c755b6a15d182611d22d7a87cf4dbe53914d45db6153111ad5743a0e69",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/java/jinput/jinput/2.0.9/jinput-2.0.9-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/java/jinput/jinput/2.0.9/jinput-2.0.9-sources.jar"],
        downloaded_file_path = "v1/net/java/jinput/jinput/2.0.9/jinput-2.0.9-sources.jar",
    )
    http_file(
        name = "net_ltgt_gradle_incap_incap_0_2",
        sha256 = "b625b9806b0f1e4bc7a2e3457119488de3cd57ea20feedd513db070a573a4ffd",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/ltgt/gradle/incap/incap/0.2/incap-0.2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/ltgt/gradle/incap/incap/0.2/incap-0.2.jar"],
        downloaded_file_path = "v1/net/ltgt/gradle/incap/incap/0.2/incap-0.2.jar",
    )
    http_file(
        name = "net_ltgt_gradle_incap_incap_sources_0_2",
        sha256 = "15c3cd213a214c94ef7ed262e00ab10c75d1680b0b9203b47801e7068de1cf5c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/net/ltgt/gradle/incap/incap/0.2/incap-0.2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/net/ltgt/gradle/incap/incap/0.2/incap-0.2-sources.jar"],
        downloaded_file_path = "v1/net/ltgt/gradle/incap/incap/0.2/incap-0.2-sources.jar",
    )
    http_file(
        name = "org_apache_httpcomponents_httpclient_4_5_14",
        sha256 = "c8bc7e1c51a6d4ce72f40d2ebbabf1c4b68bfe76e732104b04381b493478e9d6",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar"],
        downloaded_file_path = "v1/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14.jar",
    )
    http_file(
        name = "org_apache_httpcomponents_httpclient_sources_4_5_14",
        sha256 = "55b01f9f4cbec9ac646866a4b64b176570d79e293a556796b5b0263d047ef8e6",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14-sources.jar"],
        downloaded_file_path = "v1/org/apache/httpcomponents/httpclient/4.5.14/httpclient-4.5.14-sources.jar",
    )
    http_file(
        name = "org_apache_httpcomponents_httpcore_4_4_16",
        sha256 = "6c9b3dd142a09dc468e23ad39aad6f75a0f2b85125104469f026e52a474e464f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar"],
        downloaded_file_path = "v1/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16.jar",
    )
    http_file(
        name = "org_apache_httpcomponents_httpcore_sources_4_4_16",
        sha256 = "705f8cf3671093b6c1db16bbf6971a7ef400e3819784f1af53e5bc3e67b5a9a0",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16-sources.jar"],
        downloaded_file_path = "v1/org/apache/httpcomponents/httpcore/4.4.16/httpcore-4.4.16-sources.jar",
    )
    http_file(
        name = "org_apiguardian_apiguardian_api_1_1_2",
        sha256 = "b509448ac506d607319f182537f0b35d71007582ec741832a1f111e5b5b70b38",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar"],
        downloaded_file_path = "v1/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar",
    )
    http_file(
        name = "org_apiguardian_apiguardian_api_sources_1_1_2",
        sha256 = "277a7a4315412817beb6655b324dc7276621e95ebff00b8bf65e17a27b685e2d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2-sources.jar"],
        downloaded_file_path = "v1/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2-sources.jar",
    )
    http_file(
        name = "org_checkerframework_checker_compat_qual_2_5_3",
        sha256 = "d76b9afea61c7c082908023f0cbc1427fab9abd2df915c8b8a3e7a509bccbc6d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3.jar"],
        downloaded_file_path = "v1/org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3.jar",
    )
    http_file(
        name = "org_checkerframework_checker_compat_qual_sources_2_5_3",
        sha256 = "68011773fd60cfc7772508134086787210ba2a1443e3f9c3f5d4233a226c3346",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3-sources.jar"],
        downloaded_file_path = "v1/org/checkerframework/checker-compat-qual/2.5.3/checker-compat-qual-2.5.3-sources.jar",
    )
    http_file(
        name = "org_hamcrest_hamcrest_core_1_3",
        sha256 = "66fdef91e9739348df7a096aa384a5685f4e875584cce89386a7a47251c4d8e9",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"],
        downloaded_file_path = "v1/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
    )
    http_file(
        name = "org_hamcrest_hamcrest_core_sources_1_3",
        sha256 = "e223d2d8fbafd66057a8848cc94222d63c3cedd652cc48eddc0ab5c39c0f84df",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3-sources.jar"],
        downloaded_file_path = "v1/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_metadata_jvm_2_1_21",
        sha256 = "5da27249dd65a3218af2826a30804380a5c02c09f063ce7a0258736388783fc4",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_metadata_jvm_sources_2_1_21",
        sha256 = "3f5468b5c409702c7cac30067208b7218ab9e3f7209e686966c6c2de2134dd18",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-metadata-jvm/2.1.21/kotlin-metadata-jvm-2.1.21-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_reflect_2_1_21",
        sha256 = "bcd75a36ca4ad8e06117214ed807f8dea2fe61a71e07f91ca14f4335024b8463",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_reflect_sources_2_1_21",
        sha256 = "39ec728e9f2169c9beaaa66d58ec593b21e489a3fb39d36fb736bcda771e0adb",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_stdlib_2_2_0",
        sha256 = "65d12d85a3b865c160db9147851712a64b10dadd68b22eea22a95bf8a8670dca",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_stdlib_sources_2_2_0",
        sha256 = "967ad9599254e3a60d96d6c789547cc35c22d770d9c8fb1e3f15fac3b4c3b65d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-stdlib/2.2.0/kotlin-stdlib-2.2.0-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7_1_6_10",
        sha256 = "2aedcdc6b69b33bdf5cc235bcea88e7cf6601146bb6bcdffdb312bbacd7be261",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7_sources_1_6_10",
        sha256 = "01950537506f314570b0867e02da2a7a1d0cc4106a91ad43c9dc35f510b78a9e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.10/kotlin-stdlib-jdk7-1.6.10-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8_1_6_10",
        sha256 = "1456d82d039ea30d8485b032901f52bbf07e7cdbe8bb1f8708ad32a8574c41ce",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8_sources_1_6_10",
        sha256 = "5520b4f2dfafdea57219d9c2ca219924897f0315d41282eda848a7c52c4477de",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.10/kotlin-stdlib-jdk8-1.6.10-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_test_2_2_0",
        sha256 = "8db175a3f56cf139ebdf8936f293ce5a64a16b52a0408804e0ec077e8848eb32",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_test_sources_2_2_0",
        sha256 = "c8445545032c2e09195fc883b274260dd414b33f2cdc1dadd4f3441c1a4e456b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-test/2.2.0/kotlin-test-2.2.0-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_1_10_2",
        sha256 = "319b653009d49c70982f98df29cc84fc7025b092cb0571c8e7532e3ad4366dae",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_sources_1_10_2",
        sha256 = "8178bb22665b78d08bc8b5f864a8ab946576d10fe352c77558dc9c8ed7f82ad7",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.2/kotlinx-coroutines-core-1.10.2-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_1_10_2",
        sha256 = "5ca175b38df331fd64155b35cd8cae1251fa9ee369709b36d42e0a288ccce3fd",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_sources_1_10_2",
        sha256 = "cd86e9635cc7ac1b7d9854ed589e3041fb11cc67d2273e7d312278ba5628e2c8",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.2/kotlinx-coroutines-core-jvm-1.10.2-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_io_bytestring_0_7_0",
        sha256 = "d356352b0e555be126aadca9be39bb774931e32a5008edd060098dbb47d57e9e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_io_bytestring_sources_0_7_0",
        sha256 = "df95c7f83dfae4b14372d8f45ee61bd8cc1d7516d94297d4f08035853b6e9ee3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-io-bytestring/0.7.0/kotlinx-io-bytestring-0.7.0-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_io_core_0_7_0",
        sha256 = "e102b088609e7a8bdbcc277a547e4684ec0447e40893d7285831fbebd1b27ee8",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_io_core_sources_0_7_0",
        sha256 = "58db7c88bae5f3ce1293fdcd6190b5bec5ea9578317670fef8974c54c9747c48",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-io-core/0.7.0/kotlinx-io-core-0.7.0-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_serialization_core_1_8_1",
        sha256 = "d99859ff8cc49ae769f948af823f46947b3e5617fec2683fde951d4d0e0a7749",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_serialization_core_sources_1_8_1",
        sha256 = "fd0d0edc1d8e0400b8124d82d1507dd4a8391660b3bc25ef45bd8ac91b33f4a8",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-serialization-core/1.8.1/kotlinx-serialization-core-1.8.1-sources.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_serialization_core_jvm_1_8_1",
        sha256 = "3565b6d4d789bf70683c45566944287fc1d8dc75c23d98bd87d01059cc76f2b3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_serialization_core_jvm_sources_1_8_1",
        sha256 = "d4a7221a0c2ccb47bc736340344b256506cf8a5a49882883f8f56385ae69a1bc",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-serialization-core-jvm/1.8.1/kotlinx-serialization-core-jvm-1.8.1-sources.jar",
    )
    http_file(
        name = "org_jetbrains_annotations_23_0_0",
        sha256 = "7b0f19724082cbfcbc66e5abea2b9bc92cf08a1ea11e191933ed43801eb3cd05",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar"],
        downloaded_file_path = "v1/org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar",
    )
    http_file(
        name = "org_jetbrains_annotations_sources_23_0_0",
        sha256 = "ff2309b42f7584520497bb48bc609aca04c9886cf48708f14be83f00423ec144",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/annotations/23.0.0/annotations-23.0.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jetbrains/annotations/23.0.0/annotations-23.0.0-sources.jar"],
        downloaded_file_path = "v1/org/jetbrains/annotations/23.0.0/annotations-23.0.0-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_bullet_3_3_2_stable",
        sha256 = "033a15df7da32a6dc86fd2c4228270b8ab59c544069fcbd75bf6bff18b7aed20",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_bullet_sources_3_3_2_stable",
        sha256 = "ba37342338b23bf690d47f1ab77e4fd661e08cb6da59313d3be402a22734aab1",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-bullet/3.3.2-stable/jme3-bullet-3.3.2-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_bullet_native_3_3_2_stable",
        sha256 = "cbe8173714b2178ae7dfe35b85adbe867e021df7021df4efcebd6eada199fdeb",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_bullet_native_sources_3_3_2_stable",
        sha256 = "342ecaec480fada0056b0277b4efac8fe7f99e32f1a3604383a264ad45f85e21",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-bullet-native/3.3.2-stable/jme3-bullet-native-3.3.2-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_core_3_7_0_stable",
        sha256 = "0853ee825d29bcf2874d471ab1ad0581518bcd3d32639910cbf9abd607f25339",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_core_sources_3_7_0_stable",
        sha256 = "2296f1a2f5dcdcd66d302c448757532c707055e3cd44e161c976d5f7525dded3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-core/3.7.0-stable/jme3-core-3.7.0-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_desktop_3_7_0_stable",
        sha256 = "9e2db567af2c310616e8952490822ed9c900e799466fbc4f811ff5fc967da88b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_desktop_sources_3_7_0_stable",
        sha256 = "0a8d2060e31d022bfe27979c7bbbd5083a0ebea9e9fe51bece3dceef42c66691",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-desktop/3.7.0-stable/jme3-desktop-3.7.0-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_effects_3_7_0_stable",
        sha256 = "420cfaf5b59677abc04e9907a9db80d99ed666ce301bb9e13aeb956735680490",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_effects_sources_3_7_0_stable",
        sha256 = "8962dfe44eb625bb9dc6575fef2742f4889de86cabd6d8635a7e8cb840034f6f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-effects/3.7.0-stable/jme3-effects-3.7.0-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_lwjgl_3_7_0_stable",
        sha256 = "de5caa020e17d14d58729a446bf538a80976e270bdc418ecec1ef14d73d8a59c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_lwjgl_sources_3_7_0_stable",
        sha256 = "d3f1b6c4563262d9d0920cc6660945ac22e876880d24193de3d852ed7271187c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-lwjgl/3.7.0-stable/jme3-lwjgl-3.7.0-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_lwjgl3_3_7_0_stable",
        sha256 = "53f9a674891d442f788d0b54625ffe5e64809b34f5025a03a48613a8a3743a48",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_lwjgl3_sources_3_7_0_stable",
        sha256 = "c3c4df1241925a9d4deadabea9f5f6d172a6791190aa8b237f522cbcaba927a4",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-lwjgl3/3.7.0-stable/jme3-lwjgl3-3.7.0-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_terrain_3_3_2_stable",
        sha256 = "3dfa6cf8d659e2362e0df3bc5443361655a3b35db54f8f3b3ca6e782bc524a43",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_terrain_sources_3_3_2_stable",
        sha256 = "fda5bb5b73394b11f1481aa5e2df4fb01c1a679f88b01eee0c46cf7aff0d2c0c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-terrain/3.3.2-stable/jme3-terrain-3.3.2-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_testdata_3_7_0_stable",
        sha256 = "8d6070f47f0e1fdc8b84b9a8a3ccb5056db790be6a2f5e52a123f2dd466439f2",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_testdata_sources_3_7_0_stable",
        sha256 = "9e89eecf7beebbb6932e2248ee19b725071d2ab3f5cb602a0827bd6182b04de5",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-testdata/3.7.0-stable/jme3-testdata-3.7.0-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_vr_3_7_0_stable",
        sha256 = "93decbe6604aa2e038a6df2c1503658c0ff30cbd6da2d47367fa0e96ee0837e6",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable.jar",
    )
    http_file(
        name = "org_jmonkeyengine_jme3_vr_sources_3_7_0_stable",
        sha256 = "07de6e1a5718bdaeed60a0ca0f8412e6b3687a55c878ca888203105e6ad41185",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/jme3-vr/3.7.0-stable/jme3-vr-3.7.0-stable-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_lwjgl_2_9_5",
        sha256 = "a2dcbd278b870e8d66eda4022726e3b7364385a1056b5767e25f4739ca5e2362",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5.jar",
    )
    http_file(
        name = "org_jmonkeyengine_lwjgl_sources_2_9_5",
        sha256 = "fef94eb90a9040edfccdd2e78c6f225674bd66185eaa3d5dc0c497ff32afdcee",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5-sources.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/lwjgl/2.9.5/lwjgl-2.9.5-sources.jar",
    )
    http_file(
        name = "org_jmonkeyengine_lwjgl_platform_natives_linux_2_9_5",
        sha256 = "752d66130c5e419798ae26d4c3ace622b6d327fc4558207b5dbcff6176f8f8c7",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-linux.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-linux.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-linux.jar",
    )
    http_file(
        name = "org_jmonkeyengine_lwjgl_platform_natives_osx_2_9_5",
        sha256 = "eb517ba5d4c819d314534b674239eacda8e1d9da78286b6410b470a13fb83d3c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-osx.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-osx.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-osx.jar",
    )
    http_file(
        name = "org_jmonkeyengine_lwjgl_platform_natives_windows_2_9_5",
        sha256 = "2814d395b2ef92b69d7d829d1559a5bc74ad5ecc6cc4d1d79d24efc540cea838",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-windows.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-windows.jar"],
        downloaded_file_path = "v1/org/jmonkeyengine/lwjgl-platform/2.9.5/lwjgl-platform-2.9.5-natives-windows.jar",
    )
    http_file(
        name = "org_jspecify_jspecify_1_0_0",
        sha256 = "1fad6e6be7557781e4d33729d49ae1cdc8fdda6fe477bb0cc68ce351eafdfbab",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar"],
        downloaded_file_path = "v1/org/jspecify/jspecify/1.0.0/jspecify-1.0.0.jar",
    )
    http_file(
        name = "org_jspecify_jspecify_sources_1_0_0",
        sha256 = "adf0898191d55937fb3192ba971826f4f294292c4a960740f3c27310e7b70296",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jspecify/jspecify/1.0.0/jspecify-1.0.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/jspecify/jspecify/1.0.0/jspecify-1.0.0-sources.jar"],
        downloaded_file_path = "v1/org/jspecify/jspecify/1.0.0/jspecify-1.0.0-sources.jar",
    )
    http_file(
        name = "org_junit_jupiter_junit_jupiter_api_5_13_4",
        sha256 = "d1bb81abfd9e03418306b4e6a3390c8db52c58372e749c2980ac29f0c08278f1",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4.jar"],
        downloaded_file_path = "v1/org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4.jar",
    )
    http_file(
        name = "org_junit_jupiter_junit_jupiter_api_sources_5_13_4",
        sha256 = "c6d33325ffa47307b795c13cb227f002417eebf0927e40487159c53938f2793e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4-sources.jar"],
        downloaded_file_path = "v1/org/junit/jupiter/junit-jupiter-api/5.13.4/junit-jupiter-api-5.13.4-sources.jar",
    )
    http_file(
        name = "org_junit_jupiter_junit_jupiter_engine_5_13_4",
        sha256 = "027404a92fe618b72465792a257951495c503a7d5751e2791e0f51c87f67f5bc",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4.jar"],
        downloaded_file_path = "v1/org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4.jar",
    )
    http_file(
        name = "org_junit_jupiter_junit_jupiter_engine_sources_5_13_4",
        sha256 = "14b5458d4bd2b103945f4b31daab17d66709b2b90efdb1556829adcff098beec",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4-sources.jar"],
        downloaded_file_path = "v1/org/junit/jupiter/junit-jupiter-engine/5.13.4/junit-jupiter-engine-5.13.4-sources.jar",
    )
    http_file(
        name = "org_junit_platform_junit_platform_commons_1_13_4",
        sha256 = "1c25ca641ebaae44ff3ad21ca1b2ef68d0dd84bfeb07c4805ba7840899b77408",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4.jar"],
        downloaded_file_path = "v1/org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4.jar",
    )
    http_file(
        name = "org_junit_platform_junit_platform_commons_sources_1_13_4",
        sha256 = "8bf2533dc967499bbb957a7b1cd0be471a0794d8d792fcb47c66393edfa66503",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4-sources.jar"],
        downloaded_file_path = "v1/org/junit/platform/junit-platform-commons/1.13.4/junit-platform-commons-1.13.4-sources.jar",
    )
    http_file(
        name = "org_junit_platform_junit_platform_engine_1_13_4",
        sha256 = "390c5f77b84283a64b644f88251b397e0b0debb80bdcc50f899881aecff43a5a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4.jar"],
        downloaded_file_path = "v1/org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4.jar",
    )
    http_file(
        name = "org_junit_platform_junit_platform_engine_sources_1_13_4",
        sha256 = "a460d1398df481d6bc6e8e41646fb2f71dfa6e1d3514a571c97ed3c86846f1ec",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4-sources.jar"],
        downloaded_file_path = "v1/org/junit/platform/junit-platform-engine/1.13.4/junit-platform-engine-1.13.4-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_3_3_6",
        sha256 = "b00e2781b74cc829db9d39fb68746b25bb7b94ce61d46293457dbccddabd999c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_natives_linux_3_3_6",
        sha256 = "2f0e65d6985d602c0e5e5aab6576db113a9a378a36f8144ec93b77a4fde5876c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_natives_linux_arm32_3_3_6",
        sha256 = "d54a952639f7a51714609415605e252e0c6be528601a11b60b5f9e9ff19f05de",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm32.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm32.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm32.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_natives_linux_arm64_3_3_6",
        sha256 = "07643ee5e95635b710715b41900c2a05c3f08c74be9309ba0763e31431bfad3b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-linux-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_natives_macos_3_3_6",
        sha256 = "a818cba530f8a541ef30e2cc2b731c8de2d081d25e9901959ff2bcdbf2344e0f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_natives_macos_arm64_3_3_6",
        sha256 = "e68652629a28f0262ca99a2132d370e3fbc70d9f10e11717afa3f2dbe85b64fc",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-macos-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_natives_windows_3_3_6",
        sha256 = "a8d8edda34718bf70f68d14de1295b5bfc0f477a9607a3a9705d9e2d88538a8c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_natives_windows_x86_3_3_6",
        sha256 = "55ba30f2b07913ba59a8aefaa004b3c1e0aebe46655b91ef095f89ebc05bf37a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows-x86.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows-x86.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-natives-windows-x86.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_sources_3_3_6",
        sha256 = "0c2cf94c9987d327049f55ac66fdb9c944ea1a313efa750683dae50507fe9a77",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl/3.3.6/lwjgl-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_assimp_3_3_6",
        sha256 = "2cd29a9422bbc1bec11d49a18c7b289828eea939b66da2573d203a5a69ee8172",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_assimp_sources_3_3_6",
        sha256 = "08c9f5b7094530ec4cbceb7425d6efb281653ceeb6d3d98da8199fc58b2a945f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-assimp/3.3.6/lwjgl-assimp-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_bgfx_3_3_6",
        sha256 = "b6ed63de154cef8675d378dbeacaf22168e92ad5025868f6195f8f0a05d9f180",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_bgfx_sources_3_3_6",
        sha256 = "f81173ce4eb1b5d63f0d085332e44012b39b0eda51055df5d8c216409a7dbd43",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-bgfx/3.3.6/lwjgl-bgfx-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_cuda_3_3_6",
        sha256 = "b7b2abdf1cde0a5ec8c415461d2ed03f43ba7ca422da32947ccd2189390338a5",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_cuda_sources_3_3_6",
        sha256 = "aaba88a95e8103c95272ca1a1e8b0770165e4962df05de34c1a6a976c02725f3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-cuda/3.3.6/lwjgl-cuda-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_egl_3_3_6",
        sha256 = "49671f4458be2edda2928c3ff4b08c9ffda20a11097ef5c1acc65c5341874769",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_egl_sources_3_3_6",
        sha256 = "1421ec1c638ac9c9310ce9414be6f710b0b3f3f956786d7ca8c15b4d280cd5f0",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-egl/3.3.6/lwjgl-egl-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_glfw_3_3_6",
        sha256 = "b29c938ecc4997ce256a831aeca2033dab9829b5c21e72ebeb64aecd9e08450c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_glfw_natives_linux_3_3_6",
        sha256 = "a1b60014597bc0e45bf39089f4d838c3aa87fd668f6fe4e7326aa314d2ec87c0",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_glfw_natives_linux_arm32_3_3_6",
        sha256 = "75fd5d4225591ae6b0416e1b6fcefb2d85f1288bfabb15890bb4254e7be943e3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm32.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm32.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm32.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_glfw_natives_linux_arm64_3_3_6",
        sha256 = "6a108ac764f88e0fc5b3c7de21227e7db123b1b27be531a93afb5954db4efa3f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-linux-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_glfw_natives_macos_3_3_6",
        sha256 = "826f9da50850d3e7e3b2002897b672cbd999d6d8a174ceea1d6e874d148c4bc1",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_glfw_natives_macos_arm64_3_3_6",
        sha256 = "b46d40f15c2534e3800c5c44f73a4c7ded8a73b2272512c47847c1004ef7ffa9",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-macos-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_glfw_natives_windows_3_3_6",
        sha256 = "7492d3f62a868f857173d85360bb58716cd3fe8563da18419dde858aed2deb41",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_glfw_natives_windows_x86_3_3_6",
        sha256 = "edb2470645c6a557b1604d8b22ff675f1ceecf653c85a3db4b531d309da1582d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows-x86.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows-x86.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-natives-windows-x86.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_glfw_sources_3_3_6",
        sha256 = "e3a4abf3780731cc3626c63a183a321a90ad396ba58fb521ac2b4ac845a2d24e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-glfw/3.3.6/lwjgl-glfw-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jawt_3_3_6",
        sha256 = "2093ee9a645689d43efd8c4f58ddd424f92a242813d93714642a3a2b45fdbf3e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jawt_sources_3_3_6",
        sha256 = "b977f2f03ce16afa9275c84c2bece69524deaeadf1624cd34f1880f617a76f2b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jawt/3.3.6/lwjgl-jawt-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jemalloc_3_3_6",
        sha256 = "194656f652d78b11c86e86757679e975acb94397a86f256c2225931c46d7ae71",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jemalloc_natives_linux_3_3_6",
        sha256 = "5e5866a925abe5c0409f086ba114419202e408f7f8ad0a5a99b40b755c65b46c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm32_3_3_6",
        sha256 = "11ec1ae93b7c7027e8feac902308ef40e65d7e3c4bf0aa91000b30f426d8990f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm32.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm32.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm32.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jemalloc_natives_linux_arm64_3_3_6",
        sha256 = "565fc2185052f9074b5e37bd31806d955e99e649351dfbe8c18bb8a372f0cfea",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-linux-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jemalloc_natives_macos_3_3_6",
        sha256 = "81a78fb0ce8079da46e40e2c3ec98f32f54f2acb053f536a448a5bf594266445",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jemalloc_natives_macos_arm64_3_3_6",
        sha256 = "ac8b95a42c37712049d0939249d913a19f6ebcab9d890809919e02f82194785d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-macos-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jemalloc_natives_windows_3_3_6",
        sha256 = "58371e1a9ef74ba94f559a18dc0c88f95e5e04c57f2b31850614e4e0a7a98c33",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jemalloc_natives_windows_x86_3_3_6",
        sha256 = "91c715ae43af5c5a7cebeff5e4f708481723c8405fa4506bfb54966b3a91d61e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows-x86.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows-x86.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-natives-windows-x86.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_jemalloc_sources_3_3_6",
        sha256 = "a488342b25a4dfc6c01c0853628dec73d0f22ebddfe3f6af0047bbbc37131259",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-jemalloc/3.3.6/lwjgl-jemalloc-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_libdivide_3_3_6",
        sha256 = "9a2c9dea5fda79f55642b5734ee673c9d1ad62320a429fafc5dbec7980605c76",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_libdivide_sources_3_3_6",
        sha256 = "1fec2d060c739b8dfdf955c758ea5beb35705686bb13724c05c95c88b56365f7",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-libdivide/3.3.6/lwjgl-libdivide-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_llvm_3_3_6",
        sha256 = "9e9276c648aedec0569314b932b41ca0ff2c39b44dc9f5b76af825d2f0ddeceb",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_llvm_sources_3_3_6",
        sha256 = "192a208d8f32cca5a8999b7470dada4e99fe13572ec0799bd42591c8a6fdfa26",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-llvm/3.3.6/lwjgl-llvm-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_lmdb_3_3_6",
        sha256 = "e8cd76aaaaa2dba28e0080888dda59c4e832f37a6a6372e7ad979a7203ae58db",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_lmdb_sources_3_3_6",
        sha256 = "63fd9327049adc6c8439e4011e1b9a592c473200acf71fa0dae094c8ba8109a9",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-lmdb/3.3.6/lwjgl-lmdb-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_lz4_3_3_6",
        sha256 = "350dbeb6a0f07540018743653e283ee34e112b56934b67f5ca28e18136a87ef2",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_lz4_sources_3_3_6",
        sha256 = "5b412e74f8c44e1a55350e5f56ce3d6f08ceabdcc3cbf91b43667006f2f5ffdf",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-lz4/3.3.6/lwjgl-lz4-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_meow_3_3_6",
        sha256 = "92fb5f4b4296441054c087d0c4b24cda644d1365892c6499579d5ea9d9c8320a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_meow_sources_3_3_6",
        sha256 = "76625b0a4f222560d889ee269cefc52284c97b6a14961a6fbd8351920de8186a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-meow/3.3.6/lwjgl-meow-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_meshoptimizer_3_3_6",
        sha256 = "d2350ed1cd7283b6f509f8f6326e8a54b49c9296706a7e934015cd52f0013866",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_meshoptimizer_sources_3_3_6",
        sha256 = "136c85f806721d9def66d5179ee3f79e02a93a9571a66289cab76a5fd912f9ed",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-meshoptimizer/3.3.6/lwjgl-meshoptimizer-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_nanovg_3_3_6",
        sha256 = "fad128cab80819180fb3bcf8aa487f00ff4d81b8043e5548be2ef9327882b23e",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_nanovg_sources_3_3_6",
        sha256 = "5f24194d27be631d88e2eb0a90d58d8542a3885db2b9b67a17d049a4f5a46c08",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-nanovg/3.3.6/lwjgl-nanovg-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_nfd_3_3_6",
        sha256 = "d43a10930f7980913d21e77d40318ecab84248dce63f5a4994a2a72a258c2af0",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_nfd_sources_3_3_6",
        sha256 = "58d7f091d5d518802709cee5caa8a317d4bc1252490bae19ff0882533f72e178",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-nfd/3.3.6/lwjgl-nfd-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_nuklear_3_3_6",
        sha256 = "ff6a15dd924df6922227072bfeb24c074de460f2384d5ce81c91d0b9af141d1d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_nuklear_sources_3_3_6",
        sha256 = "9f87f6ba7724c06025c09dc4e7d73bff8a84abfcc24e28f6d2395113cf4da130",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-nuklear/3.3.6/lwjgl-nuklear-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_odbc_3_3_6",
        sha256 = "7cddd44c2b19b902001925c6a1f6e194406b874360364f11d7db0c9a11d599b7",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_odbc_sources_3_3_6",
        sha256 = "70545b95926cb0c33061458de257479a8f011cadd98b837d3f7e74623380c78a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-odbc/3.3.6/lwjgl-odbc-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openal_3_3_6",
        sha256 = "5d8669ee52325080f500cf0060785a13135a10c602286cefc8ba285805fa8468",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openal_natives_linux_3_3_6",
        sha256 = "d30c407c1182f507c70ad12e21bac98189c767bf5c7a592dbfaa4f11d139545c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openal_natives_linux_arm32_3_3_6",
        sha256 = "bd07f32fba66a04ae35e6834d3050a063058d0bbc5bd3c3197cd39b60cf871b2",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm32.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm32.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm32.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openal_natives_linux_arm64_3_3_6",
        sha256 = "9cb8341ecd32b77a550f64afbb67f17464d1e61e252c44b3bbb521888176dca1",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-linux-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openal_natives_macos_3_3_6",
        sha256 = "e554a8c86f5b0c84d7136a8b1d050d6d76f228e7c1881e9f64de366f8b34258c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openal_natives_macos_arm64_3_3_6",
        sha256 = "99dbb6f65161cf6c966b5d064dd19285b6b0de202c8cd386bbb1115a5f84de22",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-macos-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openal_natives_windows_3_3_6",
        sha256 = "8ebb63e8014872709671161a896a720e2f09d038b0d3a33692218f7746504b35",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openal_natives_windows_x86_3_3_6",
        sha256 = "3a3adad184f5ab9e165db721677c44f9f95f04bc4415a2dffc67640f8d00a863",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows-x86.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows-x86.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-natives-windows-x86.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openal_sources_3_3_6",
        sha256 = "690065828e8af34b67625977fe9dd12f742750a2aae6c371b86a06a5ae0791b6",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openal/3.3.6/lwjgl-openal-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opencl_3_3_6",
        sha256 = "e8eda96fe465e5e18124a1eb18ad8e10861e16056095bb9795827cfe0eb58444",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opencl_sources_3_3_6",
        sha256 = "04d5516bd9f7785ab8601d4d7411c9a111fbed91140143c2beba3718857e8ce3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opencl/3.3.6/lwjgl-opencl-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengl_3_3_6",
        sha256 = "bb0430e0a5fd7b7b5770ae517960b2ea9887bb08d04f3a0cb1aae145332e1310",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengl_natives_linux_3_3_6",
        sha256 = "8c0b5c081a7872a3cdb02b6448921da5ae5c23ab49656f299edc7a09b7e99b74",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengl_natives_linux_arm32_3_3_6",
        sha256 = "abf7dbdf0f5a9110595b12a4ad0ae7ed6602e9a09bb234a6b8eba67ca2d82360",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm32.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm32.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm32.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengl_natives_linux_arm64_3_3_6",
        sha256 = "5bb6b9052f40df5d62fb43f06561b82307e4d50c48ab596ef73b9b2a59c446c1",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-linux-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengl_natives_macos_3_3_6",
        sha256 = "660fdc9f4f06083938b9e60ab7a3ce9bc9eb6d1c7e60cb54228796101b18b633",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengl_natives_macos_arm64_3_3_6",
        sha256 = "2c0f67e7d36d71beed503043c06141af9fd83f5126a726eefceb7b5ba2aaf99c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos-arm64.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos-arm64.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-macos-arm64.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengl_natives_windows_3_3_6",
        sha256 = "b54a9d98686284947270e11e94c02aa15c30522119e7b80fcf0c98da6fa3c83c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengl_natives_windows_x86_3_3_6",
        sha256 = "393f6d1a70fa673ca15b54b4e7ee7b0ca174eaf11613199df9f6dd502339b661",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows-x86.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows-x86.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-natives-windows-x86.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengl_sources_3_3_6",
        sha256 = "030b6223835f8dc4dae0c6528e1bc9d39a020b09b130b9672e2e8a49c32500d7",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengl/3.3.6/lwjgl-opengl-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengles_3_3_6",
        sha256 = "270f6eed9c3eb0190ddf9feedaf14b56a658bc7677822c4f08d142ed958fab8d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opengles_sources_3_3_6",
        sha256 = "cd67db4b71819aa3758ef4f65eac84f30c690c994b389c801103976d8f629558",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opengles/3.3.6/lwjgl-opengles-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openvr_3_3_6",
        sha256 = "cc1a7da856c7732bf248cd7469a0fa49b2c0a781ca2571b74185c263e135bfc9",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openvr_natives_linux_3_3_6",
        sha256 = "c8ebe99a07dd61eb402197fa9f5b6583e87b482ab0a92e727d4f89b2ea8f9932",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-linux.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-linux.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-linux.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openvr_natives_macos_3_3_6",
        sha256 = "6d622ebc6037cca1f167d8c7a2d91cb017e81e314806371f9a27fd85e262fb5d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-macos.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-macos.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-macos.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openvr_natives_windows_3_3_6",
        sha256 = "20c595ae518c22d2b236f3ad726866e86c557499114d60375a055f9f4936e99c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-windows.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-windows.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-natives-windows.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openvr_sources_3_3_6",
        sha256 = "7a7ecc76328f991d94444d80e2898fe51ba2c60396bc9e325577c2f7d910b062",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openvr/3.3.6/lwjgl-openvr-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openxr_3_3_6",
        sha256 = "93116ebaa001041e7fb7de60ec2959c328c020c0f88a4f4c24fec7aac37e5020",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_openxr_sources_3_3_6",
        sha256 = "d75c9dbb71419def35e9eacfafbaa2d96e08eed9a64e642dd381ea5cf41f8de3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-openxr/3.3.6/lwjgl-openxr-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opus_3_3_6",
        sha256 = "70acd8691aa4686ec9b33ba7df931a4bdce9c7f377d3aa1aa55b4de6f36699ed",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_opus_sources_3_3_6",
        sha256 = "a6b4715d173b814f1dea16f2a614759cd1365da60840bfa3a4c08cb7176d0579",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-opus/3.3.6/lwjgl-opus-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_ovr_3_3_3",
        sha256 = "54dd38e7ecba5fbf6fd157f06e47f7bb83fec42490bf1b8b92590e02d7b81f8d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_ovr_natives_windows_3_3_3",
        sha256 = "ffccb9cead6499539bbb4c033e05a7c5c16a6df9c2352fc3a42cfffacd0e35ad",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-natives-windows.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-natives-windows.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-natives-windows.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_ovr_sources_3_3_3",
        sha256 = "6d3296d34a19ac480ca63bc6e9df68e923bc9444ab88ae0580f868dea5a58592",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-ovr/3.3.3/lwjgl-ovr-3.3.3-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_par_3_3_6",
        sha256 = "84e3c4d095dd89086dc7b0ff627551e8097852d164c7bc49cb77c370e799d22b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_par_sources_3_3_6",
        sha256 = "ecb86e3d4497110b5c3f741725a546e9aac53b925fa614b40719ba985470178a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-par/3.3.6/lwjgl-par-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_remotery_3_3_6",
        sha256 = "8d70d096aa00acf6270f072be137a766440cd0c59e5218e05d2df43576e24125",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_remotery_sources_3_3_6",
        sha256 = "6cd253300d580237a833ddc583d66a7ce03606681ab58e95c37dfd7a08a2c1b8",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-remotery/3.3.6/lwjgl-remotery-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_rpmalloc_3_3_6",
        sha256 = "061aecdad5304cb121ca28217f9e5e8dcfa4609bcbc081c9cd8feed85c8388d4",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_rpmalloc_sources_3_3_6",
        sha256 = "56dcda4e6e9146ddaab66de8f2208c0993219528e8fe2fdfaaed90dacecfdd3f",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-rpmalloc/3.3.6/lwjgl-rpmalloc-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_shaderc_3_3_6",
        sha256 = "7bec76dbeef91cd0c899fa37f9f120217b422f8050235e5b36bba41b7be35f71",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_shaderc_sources_3_3_6",
        sha256 = "c7a3e1c79fa890665fde6f876256ea0fca25f855e69d87c537b5cb3faf389631",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-shaderc/3.3.6/lwjgl-shaderc-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_spvc_3_3_6",
        sha256 = "2e4c4528c2f37c82b1a8b66d0827c3791e379b73698660915edff0fcdb765987",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_spvc_sources_3_3_6",
        sha256 = "46964b3a36137a1f4d5127247c46a4dc95cf642cf80a8ac86379ce93ec785e4c",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-spvc/3.3.6/lwjgl-spvc-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_sse_3_3_6",
        sha256 = "1c6b2e95a6813ab5ca6af0dba06f44ab0949197d16cda8f3141aec9ada29f348",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_sse_sources_3_3_6",
        sha256 = "96218c47bc4236aface77875da6fa019545b7e57302fecbcdb1e5fac9dc9127d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-sse/3.3.6/lwjgl-sse-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_stb_3_3_6",
        sha256 = "ae658e15933e691b2ddba940a6e24b41a38ad6f02fd458e5e3b3ea91e222a6ab",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_stb_sources_3_3_6",
        sha256 = "432971b3237b7c8dfed0859ab82fc06336105a83e4ea5fd8f1d05b909534f65d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-stb/3.3.6/lwjgl-stb-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_tinyexr_3_3_6",
        sha256 = "789858322b4d0ec45246e5d17ad1a169a88c2ec81458f10cb7472433cc9a79bf",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_tinyexr_sources_3_3_6",
        sha256 = "3f60a5b7fa6799a9e3934df526b4300e86ed2d3c6633c758156e6484b5b74886",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-tinyexr/3.3.6/lwjgl-tinyexr-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_tootle_3_3_6",
        sha256 = "7fcc858fa26402479acc0eb0b13d45b19b8a751b214232e26c4c334e0dac8361",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_tootle_sources_3_3_6",
        sha256 = "9411c25605263b4870e71c81f0dd50cbba89314543312fcfbd70daf684f14d53",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-tootle/3.3.6/lwjgl-tootle-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_vma_3_3_6",
        sha256 = "9d92550b62d0bb2309cebbb3f4216ff4fe84ae8e32b34d73e888963a44609bf3",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_vma_sources_3_3_6",
        sha256 = "db9de3e6982695499a6e47c3456881cd3f65b32e6fcb727152b79385a318ac1d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-vma/3.3.6/lwjgl-vma-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_vulkan_3_3_6",
        sha256 = "07501098acdfe784f55f4f5ea2e99e33e89a1d18128ddbcd41571434b5e848e4",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_vulkan_sources_3_3_6",
        sha256 = "f00f10f538c33ca298c6d7cf2fd03173aa1b6ded939647ba287336e79b77fb61",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-vulkan/3.3.6/lwjgl-vulkan-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_xxhash_3_3_6",
        sha256 = "7fd290c3f64203a6f2139d946a636907cdfc60977b6a3317560e0dfdd4e7ad45",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_xxhash_sources_3_3_6",
        sha256 = "61cc896d1d323d2787676fb154b44017daf7763402569a1bfc01f2303b43365a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-xxhash/3.3.6/lwjgl-xxhash-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_yoga_3_3_6",
        sha256 = "92197991d55b5e32d307f64c861cfb7bc3c708905cbf0e58ca9e758966db522b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_yoga_sources_3_3_6",
        sha256 = "46f21d7a666891003fd7c75b6d176158d40beb313cb49d97bc8567addd97fab0",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-yoga/3.3.6/lwjgl-yoga-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_zstd_3_3_6",
        sha256 = "d636bfcb942810a66249c4bcccc18d7b20bfb9adee9e80282d3e43f99d16a858",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6.jar",
    )
    http_file(
        name = "org_lwjgl_lwjgl_zstd_sources_3_3_6",
        sha256 = "54faa4c785eb21385a30fafa125c562bf53e1485b645c56cb879702b788f6ad6",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6-sources.jar"],
        downloaded_file_path = "v1/org/lwjgl/lwjgl-zstd/3.3.6/lwjgl-zstd-3.3.6-sources.jar",
    )
    http_file(
        name = "org_lwjglx_lwjgl3_awt_0_1_8",
        sha256 = "4e6ee7fd90e03f1708e5882615253547e75876034c50e07e96f335cba3c4a5b2",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8.jar"],
        downloaded_file_path = "v1/org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8.jar",
    )
    http_file(
        name = "org_lwjglx_lwjgl3_awt_sources_0_1_8",
        sha256 = "46cf2e094f9394784b6a314752e3c58652c8841dca552ce113f557b23abb0a10",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8-sources.jar"],
        downloaded_file_path = "v1/org/lwjglx/lwjgl3-awt/0.1.8/lwjgl3-awt-0.1.8-sources.jar",
    )
    http_file(
        name = "org_mockito_mockito_core_5_18_0",
        sha256 = "a3d4e40f7fe66016fe42c00de4e343777d74132afc85018e0f03ac6334a60f29",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/mockito/mockito-core/5.18.0/mockito-core-5.18.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/mockito/mockito-core/5.18.0/mockito-core-5.18.0.jar"],
        downloaded_file_path = "v1/org/mockito/mockito-core/5.18.0/mockito-core-5.18.0.jar",
    )
    http_file(
        name = "org_mockito_mockito_core_sources_5_18_0",
        sha256 = "f966b1ec2b1b41ce4521854c6db1e8bd7d692c33142fa56beb02fbb86ced012a",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/mockito/mockito-core/5.18.0/mockito-core-5.18.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/mockito/mockito-core/5.18.0/mockito-core-5.18.0-sources.jar"],
        downloaded_file_path = "v1/org/mockito/mockito-core/5.18.0/mockito-core-5.18.0-sources.jar",
    )
    http_file(
        name = "org_objenesis_objenesis_3_3",
        sha256 = "02dfd0b0439a5591e35b708ed2f5474eb0948f53abf74637e959b8e4ef69bfeb",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/objenesis/objenesis/3.3/objenesis-3.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/objenesis/objenesis/3.3/objenesis-3.3.jar"],
        downloaded_file_path = "v1/org/objenesis/objenesis/3.3/objenesis-3.3.jar",
    )
    http_file(
        name = "org_objenesis_objenesis_sources_3_3",
        sha256 = "d06164f8ca002c8ef193cef2d682822014dd330505616af93a3fb64226fc131d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/objenesis/objenesis/3.3/objenesis-3.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/objenesis/objenesis/3.3/objenesis-3.3-sources.jar"],
        downloaded_file_path = "v1/org/objenesis/objenesis/3.3/objenesis-3.3-sources.jar",
    )
    http_file(
        name = "org_opentest4j_opentest4j_1_3_0",
        sha256 = "48e2df636cab6563ced64dcdff8abb2355627cb236ef0bf37598682ddf742f1b",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar"],
        downloaded_file_path = "v1/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.jar",
    )
    http_file(
        name = "org_opentest4j_opentest4j_sources_1_3_0",
        sha256 = "724a24e3a68267d5ebac9411389a15638a71e50c62448ffa58f59c34d5c1ebb2",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0-sources.jar"],
        downloaded_file_path = "v1/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0-sources.jar",
    )
    http_file(
        name = "org_ow2_asm_asm_9_7",
        sha256 = "adf46d5e34940bdf148ecdd26a9ee8eea94496a72034ff7141066b3eea5c4e9d",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/ow2/asm/asm/9.7/asm-9.7.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/ow2/asm/asm/9.7/asm-9.7.jar"],
        downloaded_file_path = "v1/org/ow2/asm/asm/9.7/asm-9.7.jar",
    )
    http_file(
        name = "org_ow2_asm_asm_sources_9_7",
        sha256 = "11dfd88129204be18c0f592f8e066d0c07d8a6bc001f6c7b2cce5ff0588d5d71",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/ow2/asm/asm/9.7/asm-9.7-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/ow2/asm/asm/9.7/asm-9.7-sources.jar"],
        downloaded_file_path = "v1/org/ow2/asm/asm/9.7/asm-9.7-sources.jar",
    )
    http_file(
        name = "org_skyscreamer_jsonassert_1_5_3",
        sha256 = "719095c07d4203961320da593441d8b3b643c18eb1d81aa98ea933bb7eb351ba",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3.jar"],
        downloaded_file_path = "v1/org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3.jar",
    )
    http_file(
        name = "org_skyscreamer_jsonassert_sources_1_5_3",
        sha256 = "a825c29f8cc40f85ea4e7a431a55d4278a785c34acdeef4cd1be7367f70ea6bb",
        netrc = "../rules_jvm_external++maven+com_jackbradshaw_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3-sources.jar", "https://repository.mulesoft.org/nexus/content/repositories/public/org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3-sources.jar"],
        downloaded_file_path = "v1/org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3-sources.jar",
    )
maven_artifacts = [
    "com.google.auto.factory:auto-factory:1.1.0",
    "com.google.auto.factory:auto-factory:1.1.0:sources",
    "com.google.auto.service:auto-service-annotations:1.1.1",
    "com.google.auto.service:auto-service-annotations:1.1.1:sources",
    "com.google.auto.value:auto-value:1.11.0",
    "com.google.auto.value:auto-value:1.11.0:sources",
    "com.google.auto.value:auto-value-annotations:1.11.0",
    "com.google.auto.value:auto-value-annotations:1.11.0:sources",
    "com.google.auto:auto-common:1.2.2",
    "com.google.auto:auto-common:1.2.2:sources",
    "com.google.code.findbugs:jsr305:3.0.2",
    "com.google.code.findbugs:jsr305:3.0.2:sources",
    "com.google.code.gson:gson:2.13.1",
    "com.google.code.gson:gson:2.13.1:sources",
    "com.google.dagger:dagger:2.57",
    "com.google.dagger:dagger:2.57:sources",
    "com.google.dagger:dagger-compiler:2.57",
    "com.google.dagger:dagger-compiler:2.57:sources",
    "com.google.dagger:dagger-producers:2.57",
    "com.google.dagger:dagger-producers:2.57:sources",
    "com.google.dagger:dagger-spi:2.57",
    "com.google.dagger:dagger-spi:2.57:sources",
    "com.google.devtools.ksp:symbol-processing-api:2.1.21-2.0.2",
    "com.google.devtools.ksp:symbol-processing-api:2.1.21-2.0.2:sources",
    "com.google.errorprone:error_prone_annotations:2.38.0",
    "com.google.errorprone:error_prone_annotations:2.38.0:sources",
    "com.google.errorprone:javac-shaded:9-dev-r4023-3",
    "com.google.errorprone:javac-shaded:9-dev-r4023-3:sources",
    "com.google.flogger:flogger:0.9",
    "com.google.flogger:flogger:0.9:sources",
    "com.google.flogger:flogger-system-backend:0.9",
    "com.google.flogger:flogger-system-backend:0.9:sources",
    "com.google.googlejavaformat:google-java-format:1.5",
    "com.google.googlejavaformat:google-java-format:1.5:sources",
    "com.google.guava:failureaccess:1.0.3",
    "com.google.guava:failureaccess:1.0.3:sources",
    "com.google.guava:guava:33.4.8-jre",
    "com.google.guava:guava:33.4.8-jre:sources",
    "com.google.guava:guava-testlib:33.4.8-jre",
    "com.google.guava:guava-testlib:33.4.8-jre:sources",
    "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.http-client:google-http-client:1.47.1",
    "com.google.http-client:google-http-client:1.47.1:sources",
    "com.google.http-client:google-http-client-gson:1.47.1",
    "com.google.http-client:google-http-client-gson:1.47.1:sources",
    "com.google.http-client:google-http-client-test:1.47.1",
    "com.google.http-client:google-http-client-test:1.47.1:sources",
    "com.google.j2objc:j2objc-annotations:3.0.0",
    "com.google.j2objc:j2objc-annotations:3.0.0:sources",
    "com.google.protobuf:protobuf-java:4.32.0-RC2",
    "com.google.protobuf:protobuf-java:4.32.0-RC2:sources",
    "com.google.protobuf:protobuf-java-util:4.32.0-RC2",
    "com.google.protobuf:protobuf-java-util:4.32.0-RC2:sources",
    "com.google.protobuf:protobuf-javalite:4.32.0-RC2",
    "com.google.protobuf:protobuf-javalite:4.32.0-RC2:sources",
    "com.google.protobuf:protobuf-kotlin:4.32.0-RC2",
    "com.google.protobuf:protobuf-kotlin:4.32.0-RC2:sources",
    "com.google.protobuf:protobuf-kotlin-lite:4.32.0-RC2",
    "com.google.protobuf:protobuf-kotlin-lite:4.32.0-RC2:sources",
    "com.google.protobuf:protobuf-lite:3.0.1",
    "com.google.protobuf:protobuf-lite:3.0.1:sources",
    "com.google.truth:truth:1.4.4",
    "com.google.truth:truth:1.4.4:sources",
    "com.nativelibs4java:jnaerator-runtime:0.12",
    "com.nativelibs4java:jnaerator-runtime:0.12:sources",
    "com.nativelibs4java:ochafik-util:0.12",
    "com.nativelibs4java:ochafik-util:0.12:sources",
    "com.squareup:javapoet:1.13.0",
    "com.squareup:javapoet:1.13.0:sources",
    "com.squareup:kotlinpoet:1.11.0",
    "com.squareup:kotlinpoet:1.11.0:sources",
    "com.vaadin.external.google:android-json:0.0.20131108.vaadin1",
    "com.vaadin.external.google:android-json:0.0.20131108.vaadin1:sources",
    "commons-codec:commons-codec:1.19.0",
    "commons-codec:commons-codec:1.19.0:sources",
    "io.grpc:grpc-api:1.70.0",
    "io.grpc:grpc-api:1.70.0:sources",
    "io.grpc:grpc-context:1.70.0",
    "io.grpc:grpc-context:1.70.0:sources",
    "io.ktor:ktor-events:3.2.3",
    "io.ktor:ktor-events:3.2.3:sources",
    "io.ktor:ktor-http:3.2.3",
    "io.ktor:ktor-http:3.2.3:sources",
    "io.ktor:ktor-http-cio:3.2.3",
    "io.ktor:ktor-http-cio:3.2.3:sources",
    "io.ktor:ktor-io:3.2.3",
    "io.ktor:ktor-io:3.2.3:sources",
    "io.ktor:ktor-serialization:3.2.3",
    "io.ktor:ktor-serialization:3.2.3:sources",
    "io.ktor:ktor-server-core:3.2.3",
    "io.ktor:ktor-server-core:3.2.3:sources",
    "io.ktor:ktor-server-netty:3.2.3",
    "io.ktor:ktor-server-netty:3.2.3:sources",
    "io.ktor:ktor-utils:3.2.3",
    "io.ktor:ktor-utils:3.2.3:sources",
    "io.ktor:ktor-websockets:3.2.3",
    "io.ktor:ktor-websockets:3.2.3:sources",
    "io.opencensus:opencensus-api:0.31.1",
    "io.opencensus:opencensus-api:0.31.1:sources",
    "io.opencensus:opencensus-contrib-http-util:0.31.1",
    "io.opencensus:opencensus-contrib-http-util:0.31.1:sources",
    "jakarta.inject:jakarta.inject-api:2.0.1",
    "jakarta.inject:jakarta.inject-api:2.0.1:sources",
    "javax.annotation:jsr250-api:1.0",
    "javax.annotation:jsr250-api:1.0:sources",
    "javax.inject:javax.inject:1",
    "javax.inject:javax.inject:1:sources",
    "junit:junit:4.13.2",
    "junit:junit:4.13.2:sources",
    "net.bytebuddy:byte-buddy:1.17.5",
    "net.bytebuddy:byte-buddy:1.17.5:sources",
    "net.bytebuddy:byte-buddy-agent:1.17.5",
    "net.bytebuddy:byte-buddy-agent:1.17.5:sources",
    "net.java.dev.jna:jna:5.10.0",
    "net.java.dev.jna:jna:5.10.0:sources",
    "net.java.jinput:jinput:2.0.9",
    "net.java.jinput:jinput:2.0.9:natives-all",
    "net.java.jinput:jinput:2.0.9:sources",
    "net.ltgt.gradle.incap:incap:0.2",
    "net.ltgt.gradle.incap:incap:0.2:sources",
    "org.apache.httpcomponents:httpclient:4.5.14",
    "org.apache.httpcomponents:httpclient:4.5.14:sources",
    "org.apache.httpcomponents:httpcore:4.4.16",
    "org.apache.httpcomponents:httpcore:4.4.16:sources",
    "org.apiguardian:apiguardian-api:1.1.2",
    "org.apiguardian:apiguardian-api:1.1.2:sources",
    "org.checkerframework:checker-compat-qual:2.5.3",
    "org.checkerframework:checker-compat-qual:2.5.3:sources",
    "org.hamcrest:hamcrest-core:1.3",
    "org.hamcrest:hamcrest-core:1.3:sources",
    "org.jetbrains.kotlin:kotlin-metadata-jvm:2.1.21",
    "org.jetbrains.kotlin:kotlin-metadata-jvm:2.1.21:sources",
    "org.jetbrains.kotlin:kotlin-reflect:2.1.21",
    "org.jetbrains.kotlin:kotlin-reflect:2.1.21:sources",
    "org.jetbrains.kotlin:kotlin-stdlib:2.2.0",
    "org.jetbrains.kotlin:kotlin-stdlib:2.2.0:sources",
    "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10",
    "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10:sources",
    "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10",
    "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10:sources",
    "org.jetbrains.kotlin:kotlin-test:2.2.0",
    "org.jetbrains.kotlin:kotlin-test:2.2.0:sources",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2:sources",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.2",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.2:sources",
    "org.jetbrains.kotlinx:kotlinx-io-bytestring:0.7.0",
    "org.jetbrains.kotlinx:kotlinx-io-bytestring:0.7.0:sources",
    "org.jetbrains.kotlinx:kotlinx-io-core:0.7.0",
    "org.jetbrains.kotlinx:kotlinx-io-core:0.7.0:sources",
    "org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1",
    "org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1:sources",
    "org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.8.1",
    "org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.8.1:sources",
    "org.jetbrains:annotations:23.0.0",
    "org.jetbrains:annotations:23.0.0:sources",
    "org.jmonkeyengine:jme3-bullet:3.3.2-stable",
    "org.jmonkeyengine:jme3-bullet:3.3.2-stable:sources",
    "org.jmonkeyengine:jme3-bullet-native:3.3.2-stable",
    "org.jmonkeyengine:jme3-bullet-native:3.3.2-stable:sources",
    "org.jmonkeyengine:jme3-core:3.7.0-stable",
    "org.jmonkeyengine:jme3-core:3.7.0-stable:sources",
    "org.jmonkeyengine:jme3-desktop:3.7.0-stable",
    "org.jmonkeyengine:jme3-desktop:3.7.0-stable:sources",
    "org.jmonkeyengine:jme3-effects:3.7.0-stable",
    "org.jmonkeyengine:jme3-effects:3.7.0-stable:sources",
    "org.jmonkeyengine:jme3-lwjgl:3.7.0-stable",
    "org.jmonkeyengine:jme3-lwjgl:3.7.0-stable:sources",
    "org.jmonkeyengine:jme3-lwjgl3:3.7.0-stable",
    "org.jmonkeyengine:jme3-lwjgl3:3.7.0-stable:sources",
    "org.jmonkeyengine:jme3-terrain:3.3.2-stable",
    "org.jmonkeyengine:jme3-terrain:3.3.2-stable:sources",
    "org.jmonkeyengine:jme3-testdata:3.7.0-stable",
    "org.jmonkeyengine:jme3-testdata:3.7.0-stable:sources",
    "org.jmonkeyengine:jme3-vr:3.7.0-stable",
    "org.jmonkeyengine:jme3-vr:3.7.0-stable:sources",
    "org.jmonkeyengine:lwjgl:2.9.5",
    "org.jmonkeyengine:lwjgl:2.9.5:sources",
    "org.jmonkeyengine:lwjgl-platform:2.9.5:natives-linux",
    "org.jmonkeyengine:lwjgl-platform:2.9.5:natives-osx",
    "org.jmonkeyengine:lwjgl-platform:2.9.5:natives-windows",
    "org.jspecify:jspecify:1.0.0",
    "org.jspecify:jspecify:1.0.0:sources",
    "org.junit.jupiter:junit-jupiter-api:5.13.4",
    "org.junit.jupiter:junit-jupiter-api:5.13.4:sources",
    "org.junit.jupiter:junit-jupiter-engine:5.13.4",
    "org.junit.jupiter:junit-jupiter-engine:5.13.4:sources",
    "org.junit.platform:junit-platform-commons:1.13.4",
    "org.junit.platform:junit-platform-commons:1.13.4:sources",
    "org.junit.platform:junit-platform-engine:1.13.4",
    "org.junit.platform:junit-platform-engine:1.13.4:sources",
    "org.lwjgl:lwjgl:3.3.6",
    "org.lwjgl:lwjgl:3.3.6:natives-linux",
    "org.lwjgl:lwjgl:3.3.6:natives-linux-arm32",
    "org.lwjgl:lwjgl:3.3.6:natives-linux-arm64",
    "org.lwjgl:lwjgl:3.3.6:natives-macos",
    "org.lwjgl:lwjgl:3.3.6:natives-macos-arm64",
    "org.lwjgl:lwjgl:3.3.6:natives-windows",
    "org.lwjgl:lwjgl:3.3.6:natives-windows-x86",
    "org.lwjgl:lwjgl:3.3.6:sources",
    "org.lwjgl:lwjgl-assimp:3.3.6",
    "org.lwjgl:lwjgl-assimp:3.3.6:sources",
    "org.lwjgl:lwjgl-bgfx:3.3.6",
    "org.lwjgl:lwjgl-bgfx:3.3.6:sources",
    "org.lwjgl:lwjgl-cuda:3.3.6",
    "org.lwjgl:lwjgl-cuda:3.3.6:sources",
    "org.lwjgl:lwjgl-egl:3.3.6",
    "org.lwjgl:lwjgl-egl:3.3.6:sources",
    "org.lwjgl:lwjgl-glfw:3.3.6",
    "org.lwjgl:lwjgl-glfw:3.3.6:natives-linux",
    "org.lwjgl:lwjgl-glfw:3.3.6:natives-linux-arm32",
    "org.lwjgl:lwjgl-glfw:3.3.6:natives-linux-arm64",
    "org.lwjgl:lwjgl-glfw:3.3.6:natives-macos",
    "org.lwjgl:lwjgl-glfw:3.3.6:natives-macos-arm64",
    "org.lwjgl:lwjgl-glfw:3.3.6:natives-windows",
    "org.lwjgl:lwjgl-glfw:3.3.6:natives-windows-x86",
    "org.lwjgl:lwjgl-glfw:3.3.6:sources",
    "org.lwjgl:lwjgl-jawt:3.3.6",
    "org.lwjgl:lwjgl-jawt:3.3.6:sources",
    "org.lwjgl:lwjgl-jemalloc:3.3.6",
    "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-linux",
    "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-linux-arm32",
    "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-linux-arm64",
    "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-macos",
    "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-macos-arm64",
    "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-windows",
    "org.lwjgl:lwjgl-jemalloc:3.3.6:natives-windows-x86",
    "org.lwjgl:lwjgl-jemalloc:3.3.6:sources",
    "org.lwjgl:lwjgl-libdivide:3.3.6",
    "org.lwjgl:lwjgl-libdivide:3.3.6:sources",
    "org.lwjgl:lwjgl-llvm:3.3.6",
    "org.lwjgl:lwjgl-llvm:3.3.6:sources",
    "org.lwjgl:lwjgl-lmdb:3.3.6",
    "org.lwjgl:lwjgl-lmdb:3.3.6:sources",
    "org.lwjgl:lwjgl-lz4:3.3.6",
    "org.lwjgl:lwjgl-lz4:3.3.6:sources",
    "org.lwjgl:lwjgl-meow:3.3.6",
    "org.lwjgl:lwjgl-meow:3.3.6:sources",
    "org.lwjgl:lwjgl-meshoptimizer:3.3.6",
    "org.lwjgl:lwjgl-meshoptimizer:3.3.6:sources",
    "org.lwjgl:lwjgl-nanovg:3.3.6",
    "org.lwjgl:lwjgl-nanovg:3.3.6:sources",
    "org.lwjgl:lwjgl-nfd:3.3.6",
    "org.lwjgl:lwjgl-nfd:3.3.6:sources",
    "org.lwjgl:lwjgl-nuklear:3.3.6",
    "org.lwjgl:lwjgl-nuklear:3.3.6:sources",
    "org.lwjgl:lwjgl-odbc:3.3.6",
    "org.lwjgl:lwjgl-odbc:3.3.6:sources",
    "org.lwjgl:lwjgl-openal:3.3.6",
    "org.lwjgl:lwjgl-openal:3.3.6:natives-linux",
    "org.lwjgl:lwjgl-openal:3.3.6:natives-linux-arm32",
    "org.lwjgl:lwjgl-openal:3.3.6:natives-linux-arm64",
    "org.lwjgl:lwjgl-openal:3.3.6:natives-macos",
    "org.lwjgl:lwjgl-openal:3.3.6:natives-macos-arm64",
    "org.lwjgl:lwjgl-openal:3.3.6:natives-windows",
    "org.lwjgl:lwjgl-openal:3.3.6:natives-windows-x86",
    "org.lwjgl:lwjgl-openal:3.3.6:sources",
    "org.lwjgl:lwjgl-opencl:3.3.6",
    "org.lwjgl:lwjgl-opencl:3.3.6:sources",
    "org.lwjgl:lwjgl-opengl:3.3.6",
    "org.lwjgl:lwjgl-opengl:3.3.6:natives-linux",
    "org.lwjgl:lwjgl-opengl:3.3.6:natives-linux-arm32",
    "org.lwjgl:lwjgl-opengl:3.3.6:natives-linux-arm64",
    "org.lwjgl:lwjgl-opengl:3.3.6:natives-macos",
    "org.lwjgl:lwjgl-opengl:3.3.6:natives-macos-arm64",
    "org.lwjgl:lwjgl-opengl:3.3.6:natives-windows",
    "org.lwjgl:lwjgl-opengl:3.3.6:natives-windows-x86",
    "org.lwjgl:lwjgl-opengl:3.3.6:sources",
    "org.lwjgl:lwjgl-opengles:3.3.6",
    "org.lwjgl:lwjgl-opengles:3.3.6:sources",
    "org.lwjgl:lwjgl-openvr:3.3.6",
    "org.lwjgl:lwjgl-openvr:3.3.6:natives-linux",
    "org.lwjgl:lwjgl-openvr:3.3.6:natives-macos",
    "org.lwjgl:lwjgl-openvr:3.3.6:natives-windows",
    "org.lwjgl:lwjgl-openvr:3.3.6:sources",
    "org.lwjgl:lwjgl-openxr:3.3.6",
    "org.lwjgl:lwjgl-openxr:3.3.6:sources",
    "org.lwjgl:lwjgl-opus:3.3.6",
    "org.lwjgl:lwjgl-opus:3.3.6:sources",
    "org.lwjgl:lwjgl-ovr:3.3.3",
    "org.lwjgl:lwjgl-ovr:3.3.3:natives-windows",
    "org.lwjgl:lwjgl-ovr:3.3.3:sources",
    "org.lwjgl:lwjgl-par:3.3.6",
    "org.lwjgl:lwjgl-par:3.3.6:sources",
    "org.lwjgl:lwjgl-remotery:3.3.6",
    "org.lwjgl:lwjgl-remotery:3.3.6:sources",
    "org.lwjgl:lwjgl-rpmalloc:3.3.6",
    "org.lwjgl:lwjgl-rpmalloc:3.3.6:sources",
    "org.lwjgl:lwjgl-shaderc:3.3.6",
    "org.lwjgl:lwjgl-shaderc:3.3.6:sources",
    "org.lwjgl:lwjgl-spvc:3.3.6",
    "org.lwjgl:lwjgl-spvc:3.3.6:sources",
    "org.lwjgl:lwjgl-sse:3.3.6",
    "org.lwjgl:lwjgl-sse:3.3.6:sources",
    "org.lwjgl:lwjgl-stb:3.3.6",
    "org.lwjgl:lwjgl-stb:3.3.6:sources",
    "org.lwjgl:lwjgl-tinyexr:3.3.6",
    "org.lwjgl:lwjgl-tinyexr:3.3.6:sources",
    "org.lwjgl:lwjgl-tootle:3.3.6",
    "org.lwjgl:lwjgl-tootle:3.3.6:sources",
    "org.lwjgl:lwjgl-vma:3.3.6",
    "org.lwjgl:lwjgl-vma:3.3.6:sources",
    "org.lwjgl:lwjgl-vulkan:3.3.6",
    "org.lwjgl:lwjgl-vulkan:3.3.6:sources",
    "org.lwjgl:lwjgl-xxhash:3.3.6",
    "org.lwjgl:lwjgl-xxhash:3.3.6:sources",
    "org.lwjgl:lwjgl-yoga:3.3.6",
    "org.lwjgl:lwjgl-yoga:3.3.6:sources",
    "org.lwjgl:lwjgl-zstd:3.3.6",
    "org.lwjgl:lwjgl-zstd:3.3.6:sources",
    "org.lwjglx:lwjgl3-awt:0.1.8",
    "org.lwjglx:lwjgl3-awt:0.1.8:sources",
    "org.mockito:mockito-core:5.18.0",
    "org.mockito:mockito-core:5.18.0:sources",
    "org.objenesis:objenesis:3.3",
    "org.objenesis:objenesis:3.3:sources",
    "org.opentest4j:opentest4j:1.3.0",
    "org.opentest4j:opentest4j:1.3.0:sources",
    "org.ow2.asm:asm:9.7",
    "org.ow2.asm:asm:9.7:sources",
    "org.skyscreamer:jsonassert:1.5.3",
    "org.skyscreamer:jsonassert:1.5.3:sources"
]