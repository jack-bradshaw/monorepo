load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")
def pinned_maven_install():
    pass
    http_file(
        name = "com_google_auto_value_auto_value_annotations_1_10_4",
        sha256 = "e1c45e6beadaef9797cb0d9afd5a45621ad061cd8632012f85582853a3887825",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar"],
        downloaded_file_path = "v1/com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar",
    )
    http_file(
        name = "com_google_code_findbugs_jsr305_3_0_2",
        sha256 = "766ad2a0783f2687962c8ad74ceecc38a28b9f72a2d085ee438b7813e928d0c7",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar"],
        downloaded_file_path = "v1/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar",
    )
    http_file(
        name = "com_google_errorprone_error_prone_annotations_2_28_0",
        sha256 = "f3fc8a3a0a4020706a373b00e7f57c2512dd26d1f83d28c7d38768f8682b231e",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.28.0/error_prone_annotations-2.28.0.jar"],
        downloaded_file_path = "v1/com/google/errorprone/error_prone_annotations/2.28.0/error_prone_annotations-2.28.0.jar",
    )
    http_file(
        name = "com_google_guava_failureaccess_1_0_2",
        sha256 = "8a8f81cf9b359e3f6dfa691a1e776985c061ef2f223c9b2c80753e1b458e8064",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.2/failureaccess-1.0.2.jar"],
        downloaded_file_path = "v1/com/google/guava/failureaccess/1.0.2/failureaccess-1.0.2.jar",
    )
    http_file(
        name = "com_google_guava_guava_33_3_1_android",
        sha256 = "2c3e41d1b380f2044d257947a3aa82dabf3ae4b978622745254aa18b6cf89ab0",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/33.3.1-android/guava-33.3.1-android.jar"],
        downloaded_file_path = "v1/com/google/guava/guava/33.3.1-android/guava-33.3.1-android.jar",
    )
    http_file(
        name = "com_google_guava_listenablefuture_9999_0_empty_to_avoid_conflict_with_guava",
        sha256 = "b372a037d4230aa57fbeffdef30fd6123f9c0c2db85d0aced00c91b974f33f99",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"],
        downloaded_file_path = "v1/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar",
    )
    http_file(
        name = "com_google_j2objc_j2objc_annotations_3_0_0",
        sha256 = "88241573467ddca44ffd4d74aa04c2bbfd11bf7c17e0c342c94c9de7a70a7c64",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar"],
        downloaded_file_path = "v1/com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar",
    )
    http_file(
        name = "com_google_jimfs_jimfs_1_3_0",
        sha256 = "82494408bb513f5512652e7b7f63d6f31f01eff57ce35c878644ffc2d25aee4f",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/jimfs/jimfs/1.3.0/jimfs-1.3.0.jar"],
        downloaded_file_path = "v1/com/google/jimfs/jimfs/1.3.0/jimfs-1.3.0.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_java_4_30_2",
        sha256 = "0f3a4e9264db07cec429f2a68a66030e9b7487277b76863cdd0e9238cece249b",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/4.30.2/protobuf-java-4.30.2.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-java/4.30.2/protobuf-java-4.30.2.jar",
    )
    http_file(
        name = "com_google_protobuf_protobuf_kotlin_4_30_2",
        sha256 = "278ac5ad950b92045955404ee4b58f0054c3b6e302afbf62860004ab043ae18f",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin/4.30.2/protobuf-kotlin-4.30.2.jar"],
        downloaded_file_path = "v1/com/google/protobuf/protobuf-kotlin/4.30.2/protobuf-kotlin-4.30.2.jar",
    )
    http_file(
        name = "com_google_truth_extensions_truth_liteproto_extension_1_4_2",
        sha256 = "df6511ca3609c510c266bfdca3d0cedd6eecbcfcb725305eb1c676b5cb4f20a0",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/truth/extensions/truth-liteproto-extension/1.4.2/truth-liteproto-extension-1.4.2.jar"],
        downloaded_file_path = "v1/com/google/truth/extensions/truth-liteproto-extension/1.4.2/truth-liteproto-extension-1.4.2.jar",
    )
    http_file(
        name = "com_google_truth_extensions_truth_proto_extension_1_4_2",
        sha256 = "85fc5b5c3323d7dfdcaa359c0397cf045a5cf6e625d7d9d9d3ffa5cec7cc30e3",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/truth/extensions/truth-proto-extension/1.4.2/truth-proto-extension-1.4.2.jar"],
        downloaded_file_path = "v1/com/google/truth/extensions/truth-proto-extension/1.4.2/truth-proto-extension-1.4.2.jar",
    )
    http_file(
        name = "com_google_truth_truth_1_4_2",
        sha256 = "14c297bc64ca8bc15b6baf67f160627e4562ec91624797e312e907b431113508",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/google/truth/truth/1.4.2/truth-1.4.2.jar"],
        downloaded_file_path = "v1/com/google/truth/truth/1.4.2/truth-1.4.2.jar",
    )
    http_file(
        name = "com_squareup_kotlinpoet_1_14_2",
        sha256 = "102d5d8a289d961cd7f39204c264d272e4aad775e388d909f6050e14558aae9b",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/com/squareup/kotlinpoet/1.14.2/kotlinpoet-1.14.2.jar"],
        downloaded_file_path = "v1/com/squareup/kotlinpoet/1.14.2/kotlinpoet-1.14.2.jar",
    )
    http_file(
        name = "junit_junit_4_13_2",
        sha256 = "8e495b634469d64fb8acfa3495a065cbacc8a0fff55ce1e31007be4c16dc57d3",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar"],
        downloaded_file_path = "v1/junit/junit/4.13.2/junit-4.13.2.jar",
    )
    http_file(
        name = "org_checkerframework_checker_qual_3_43_0",
        sha256 = "3fbc2e98f05854c3df16df9abaa955b91b15b3ecac33623208ed6424640ef0f6",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-qual/3.43.0/checker-qual-3.43.0.jar"],
        downloaded_file_path = "v1/org/checkerframework/checker-qual/3.43.0/checker-qual-3.43.0.jar",
    )
    http_file(
        name = "org_hamcrest_hamcrest_core_1_3",
        sha256 = "66fdef91e9739348df7a096aa384a5685f4e875584cce89386a7a47251c4d8e9",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"],
        downloaded_file_path = "v1/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_reflect_1_8_21",
        sha256 = "8a6cd5a3cf092acee274ce2c444dc36eefdb631579859dd4d857b3309a529c91",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-reflect/1.8.21/kotlin-reflect-1.8.21.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-reflect/1.8.21/kotlin-reflect-1.8.21.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_stdlib_2_1_0",
        sha256 = "d6f91b7b0f306cca299fec74fb7c34e4874d6f5ec5b925a0b4de21901e119c3f",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.1.0/kotlin-stdlib-2.1.0.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-stdlib/2.1.0/kotlin-stdlib-2.1.0.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7_1_8_21",
        sha256 = "33d148db0e11debd0d90677d28242bced907f9c77730000fd597867089039d86",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.8.21/kotlin-stdlib-jdk7-1.8.21.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.8.21/kotlin-stdlib-jdk7-1.8.21.jar",
    )
    http_file(
        name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8_1_8_21",
        sha256 = "3db752a30074f06ee6c57984aa6f27da44f4d2bbc7f5442651f6988f1cb2b7d7",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.8.21/kotlin-stdlib-jdk8-1.8.21.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.8.21/kotlin-stdlib-jdk8-1.8.21.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_1_10_1",
        sha256 = "fae4771dd987cfadabae129dd7f625af40d9e4f14abb7ffc72e42dccb97b7010",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.1/kotlinx-coroutines-core-1.10.1.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.1/kotlinx-coroutines-core-1.10.1.jar",
    )
    http_file(
        name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_1_10_1",
        sha256 = "069c5988633230e074ec0d39321ec3cdaa4547c49e90ba936c63d8fc91c8c00d",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.1/kotlinx-coroutines-core-jvm-1.10.1.jar"],
        downloaded_file_path = "v1/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.1/kotlinx-coroutines-core-jvm-1.10.1.jar",
    )
    http_file(
        name = "org_jetbrains_annotations_23_0_0",
        sha256 = "7b0f19724082cbfcbc66e5abea2b9bc92cf08a1ea11e191933ed43801eb3cd05",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar"],
        downloaded_file_path = "v1/org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar",
    )
    http_file(
        name = "org_ow2_asm_asm_9_6",
        sha256 = "3c6fac2424db3d4a853b669f4e3d1d9c3c552235e19a319673f887083c2303a1",
        netrc = "../rules_jvm_external++maven+grpc_kotlin_maven/netrc",
        urls = ["https://repo1.maven.org/maven2/org/ow2/asm/asm/9.6/asm-9.6.jar"],
        downloaded_file_path = "v1/org/ow2/asm/asm/9.6/asm-9.6.jar",
    )
maven_artifacts = [
    "com.google.auto.value:auto-value-annotations:1.10.4",
    "com.google.code.findbugs:jsr305:3.0.2",
    "com.google.errorprone:error_prone_annotations:2.28.0",
    "com.google.guava:failureaccess:1.0.2",
    "com.google.guava:guava:33.3.1-android",
    "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.j2objc:j2objc-annotations:3.0.0",
    "com.google.jimfs:jimfs:1.3.0",
    "com.google.protobuf:protobuf-java:4.30.2",
    "com.google.protobuf:protobuf-kotlin:4.30.2",
    "com.google.truth.extensions:truth-liteproto-extension:1.4.2",
    "com.google.truth.extensions:truth-proto-extension:1.4.2",
    "com.google.truth:truth:1.4.2",
    "com.squareup:kotlinpoet:1.14.2",
    "junit:junit:4.13.2",
    "org.checkerframework:checker-qual:3.43.0",
    "org.hamcrest:hamcrest-core:1.3",
    "org.jetbrains.kotlin:kotlin-reflect:1.8.21",
    "org.jetbrains.kotlin:kotlin-stdlib:2.1.0",
    "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.21",
    "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.21",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.1",
    "org.jetbrains:annotations:23.0.0",
    "org.ow2.asm:asm:9.6"
]