
# package(default_visibility = ["@@//visibility:private"])  # https://github.com/bazelbuild/bazel/issues/13681

load("@bazel_skylib//:bzl_library.bzl", "bzl_library")
load("@bazel_skylib//rules:copy_file.bzl", "copy_file")
load("@rules_license//rules:package_info.bzl", "package_info")
load("@rules_java//java:defs.bzl", "java_binary", "java_library", "java_plugin")
load("@rules_jvm_external//private/rules:pin_dependencies.bzl", "pin_dependencies")
load("@rules_jvm_external//private/rules:jvm_import.bzl", "jvm_import")
load("@rules_shell//shell:sh_binary.bzl", "sh_binary")


jvm_import(
	name = "com_google_auto_value_auto_value_annotations",
	jar = "com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar",
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
	visibility = ["@@//visibility:private"],
)
alias(
	name = "com_google_auto_value_auto_value_annotations_1_10_4",
	actual = "com_google_auto_value_auto_value_annotations",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "com_google_auto_value_auto_value_annotations_1_10_4_extension",
     src = "@com_google_auto_value_auto_value_annotations_1_10_4//file",
     out = "com/google/auto/value/auto-value-annotations/1.10.4/auto-value-annotations-1.10.4.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_code_findbugs_jsr305",
	jar = "com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar",
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
	visibility = ["@@//visibility:private"],
)
alias(
	name = "com_google_code_findbugs_jsr305_3_0_2",
	actual = "com_google_code_findbugs_jsr305",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "com_google_code_findbugs_jsr305_3_0_2_extension",
     src = "@com_google_code_findbugs_jsr305_3_0_2//file",
     out = "com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_errorprone_error_prone_annotations",
	jar = "com/google/errorprone/error_prone_annotations/2.28.0/error_prone_annotations-2.28.0.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.errorprone:error_prone_annotations:2.28.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.28.0/error_prone_annotations-2.28.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=f3fc8a3a0a4020706a373b00e7f57c2512dd26d1f83d28c7d38768f8682b231e",
	],
	maven_coordinates = "com.google.errorprone:error_prone_annotations:2.28.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.28.0/error_prone_annotations-2.28.0.jar",
	visibility = ["@@//visibility:private"],
)
alias(
	name = "com_google_errorprone_error_prone_annotations_2_28_0",
	actual = "com_google_errorprone_error_prone_annotations",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "com_google_errorprone_error_prone_annotations_2_28_0_extension",
     src = "@com_google_errorprone_error_prone_annotations_2_28_0//file",
     out = "com/google/errorprone/error_prone_annotations/2.28.0/error_prone_annotations-2.28.0.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_guava_failureaccess",
	jar = "com/google/guava/failureaccess/1.0.2/failureaccess-1.0.2.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.guava:failureaccess:1.0.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.2/failureaccess-1.0.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8a8f81cf9b359e3f6dfa691a1e776985c061ef2f223c9b2c80753e1b458e8064",
	],
	maven_coordinates = "com.google.guava:failureaccess:1.0.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.2/failureaccess-1.0.2.jar",
	visibility = ["@@//visibility:private"],
)
alias(
	name = "com_google_guava_failureaccess_1_0_2",
	actual = "com_google_guava_failureaccess",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "com_google_guava_failureaccess_1_0_2_extension",
     src = "@com_google_guava_failureaccess_1_0_2//file",
     out = "com/google/guava/failureaccess/1.0.2/failureaccess-1.0.2.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_guava_guava",
	jar = "com/google/guava/guava/33.3.1-android/guava-33.3.1-android.jar",
	deps = [
		":com_google_code_findbugs_jsr305",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_failureaccess",
		":com_google_guava_listenablefuture",
		":com_google_j2objc_j2objc_annotations",
		":org_checkerframework_checker_qual",
	],
	tags = [
		"maven_coordinates=com.google.guava:guava:33.3.1-android",
		"maven_url=https://repo1.maven.org/maven2/com/google/guava/guava/33.3.1-android/guava-33.3.1-android.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=2c3e41d1b380f2044d257947a3aa82dabf3ae4b978622745254aa18b6cf89ab0",
	],
	maven_coordinates = "com.google.guava:guava:33.3.1-android",
	maven_url = "https://repo1.maven.org/maven2/com/google/guava/guava/33.3.1-android/guava-33.3.1-android.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_guava_guava_33_3_1_android",
	actual = "com_google_guava_guava",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_guava_guava_33_3_1_android_extension",
     src = "@com_google_guava_guava_33_3_1_android//file",
     out = "com/google/guava/guava/33.3.1-android/guava-33.3.1-android.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
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
	visibility = ["@@//visibility:private"],
)
alias(
	name = "com_google_guava_listenablefuture_9999_0_empty_to_avoid_conflict_with_guava",
	actual = "com_google_guava_listenablefuture",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "com_google_guava_listenablefuture_9999_0_empty_to_avoid_conflict_with_guava_extension",
     src = "@com_google_guava_listenablefuture_9999_0_empty_to_avoid_conflict_with_guava//file",
     out = "com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_j2objc_j2objc_annotations",
	jar = "com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar",
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
	visibility = ["@@//visibility:private"],
)
alias(
	name = "com_google_j2objc_j2objc_annotations_3_0_0",
	actual = "com_google_j2objc_j2objc_annotations",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "com_google_j2objc_j2objc_annotations_3_0_0_extension",
     src = "@com_google_j2objc_j2objc_annotations_3_0_0//file",
     out = "com/google/j2objc/j2objc-annotations/3.0.0/j2objc-annotations-3.0.0.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_jimfs_jimfs",
	jar = "com/google/jimfs/jimfs/1.3.0/jimfs-1.3.0.jar",
	deps = [
		":com_google_guava_guava",
	],
	tags = [
		"maven_coordinates=com.google.jimfs:jimfs:1.3.0",
		"maven_url=https://repo1.maven.org/maven2/com/google/jimfs/jimfs/1.3.0/jimfs-1.3.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=82494408bb513f5512652e7b7f63d6f31f01eff57ce35c878644ffc2d25aee4f",
	],
	maven_coordinates = "com.google.jimfs:jimfs:1.3.0",
	maven_url = "https://repo1.maven.org/maven2/com/google/jimfs/jimfs/1.3.0/jimfs-1.3.0.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_jimfs_jimfs_1_3_0",
	actual = "com_google_jimfs_jimfs",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_jimfs_jimfs_1_3_0_extension",
     src = "@com_google_jimfs_jimfs_1_3_0//file",
     out = "com/google/jimfs/jimfs/1.3.0/jimfs-1.3.0.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_java",
	jar = "com/google/protobuf/protobuf-java/4.30.2/protobuf-java-4.30.2.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-java:4.30.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/4.30.2/protobuf-java-4.30.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=0f3a4e9264db07cec429f2a68a66030e9b7487277b76863cdd0e9238cece249b",
	],
	maven_coordinates = "com.google.protobuf:protobuf-java:4.30.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/4.30.2/protobuf-java-4.30.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_java_4_30_2",
	actual = "com_google_protobuf_protobuf_java",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_java_4_30_2_extension",
     src = "@com_google_protobuf_protobuf_java_4_30_2//file",
     out = "com/google/protobuf/protobuf-java/4.30.2/protobuf-java-4.30.2.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_protobuf_protobuf_kotlin",
	jar = "com/google/protobuf/protobuf-kotlin/4.30.2/protobuf-kotlin-4.30.2.jar",
	deps = [
		":com_google_protobuf_protobuf_java",
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=com.google.protobuf:protobuf-kotlin:4.30.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin/4.30.2/protobuf-kotlin-4.30.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=278ac5ad950b92045955404ee4b58f0054c3b6e302afbf62860004ab043ae18f",
	],
	maven_coordinates = "com.google.protobuf:protobuf-kotlin:4.30.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/protobuf/protobuf-kotlin/4.30.2/protobuf-kotlin-4.30.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_protobuf_protobuf_kotlin_4_30_2",
	actual = "com_google_protobuf_protobuf_kotlin",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_protobuf_protobuf_kotlin_4_30_2_extension",
     src = "@com_google_protobuf_protobuf_kotlin_4_30_2//file",
     out = "com/google/protobuf/protobuf-kotlin/4.30.2/protobuf-kotlin-4.30.2.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_truth_extensions_truth_liteproto_extension",
	jar = "com/google/truth/extensions/truth-liteproto-extension/1.4.2/truth-liteproto-extension-1.4.2.jar",
	deps = [
		":com_google_auto_value_auto_value_annotations",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":com_google_truth_truth",
		":org_checkerframework_checker_qual",
	],
	tags = [
		"maven_coordinates=com.google.truth.extensions:truth-liteproto-extension:1.4.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/truth/extensions/truth-liteproto-extension/1.4.2/truth-liteproto-extension-1.4.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=df6511ca3609c510c266bfdca3d0cedd6eecbcfcb725305eb1c676b5cb4f20a0",
	],
	maven_coordinates = "com.google.truth.extensions:truth-liteproto-extension:1.4.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/truth/extensions/truth-liteproto-extension/1.4.2/truth-liteproto-extension-1.4.2.jar",
	visibility = ["@@//visibility:private"],
)
alias(
	name = "com_google_truth_extensions_truth_liteproto_extension_1_4_2",
	actual = "com_google_truth_extensions_truth_liteproto_extension",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "com_google_truth_extensions_truth_liteproto_extension_1_4_2_extension",
     src = "@com_google_truth_extensions_truth_liteproto_extension_1_4_2//file",
     out = "com/google/truth/extensions/truth-liteproto-extension/1.4.2/truth-liteproto-extension-1.4.2.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_truth_extensions_truth_proto_extension",
	jar = "com/google/truth/extensions/truth-proto-extension/1.4.2/truth-proto-extension-1.4.2.jar",
	deps = [
		":com_google_auto_value_auto_value_annotations",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":com_google_protobuf_protobuf_java",
		":com_google_truth_extensions_truth_liteproto_extension",
		":com_google_truth_truth",
		":org_checkerframework_checker_qual",
	],
	tags = [
		"maven_coordinates=com.google.truth.extensions:truth-proto-extension:1.4.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/truth/extensions/truth-proto-extension/1.4.2/truth-proto-extension-1.4.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=85fc5b5c3323d7dfdcaa359c0397cf045a5cf6e625d7d9d9d3ffa5cec7cc30e3",
	],
	maven_coordinates = "com.google.truth.extensions:truth-proto-extension:1.4.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/truth/extensions/truth-proto-extension/1.4.2/truth-proto-extension-1.4.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_truth_extensions_truth_proto_extension_1_4_2",
	actual = "com_google_truth_extensions_truth_proto_extension",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_truth_extensions_truth_proto_extension_1_4_2_extension",
     src = "@com_google_truth_extensions_truth_proto_extension_1_4_2//file",
     out = "com/google/truth/extensions/truth-proto-extension/1.4.2/truth-proto-extension-1.4.2.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_google_truth_truth",
	jar = "com/google/truth/truth/1.4.2/truth-1.4.2.jar",
	deps = [
		":com_google_auto_value_auto_value_annotations",
		":com_google_errorprone_error_prone_annotations",
		":com_google_guava_guava",
		":junit_junit",
		":org_checkerframework_checker_qual",
		":org_ow2_asm_asm",
	],
	tags = [
		"maven_coordinates=com.google.truth:truth:1.4.2",
		"maven_url=https://repo1.maven.org/maven2/com/google/truth/truth/1.4.2/truth-1.4.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=14c297bc64ca8bc15b6baf67f160627e4562ec91624797e312e907b431113508",
	],
	maven_coordinates = "com.google.truth:truth:1.4.2",
	maven_url = "https://repo1.maven.org/maven2/com/google/truth/truth/1.4.2/truth-1.4.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_google_truth_truth_1_4_2",
	actual = "com_google_truth_truth",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_google_truth_truth_1_4_2_extension",
     src = "@com_google_truth_truth_1_4_2//file",
     out = "com/google/truth/truth/1.4.2/truth-1.4.2.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "com_squareup_kotlinpoet",
	jar = "com/squareup/kotlinpoet/1.14.2/kotlinpoet-1.14.2.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_reflect",
		":org_jetbrains_kotlin_kotlin_stdlib_jdk8",
	],
	tags = [
		"maven_coordinates=com.squareup:kotlinpoet:1.14.2",
		"maven_url=https://repo1.maven.org/maven2/com/squareup/kotlinpoet/1.14.2/kotlinpoet-1.14.2.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=102d5d8a289d961cd7f39204c264d272e4aad775e388d909f6050e14558aae9b",
	],
	maven_coordinates = "com.squareup:kotlinpoet:1.14.2",
	maven_url = "https://repo1.maven.org/maven2/com/squareup/kotlinpoet/1.14.2/kotlinpoet-1.14.2.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "com_squareup_kotlinpoet_1_14_2",
	actual = "com_squareup_kotlinpoet",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "com_squareup_kotlinpoet_1_14_2_extension",
     src = "@com_squareup_kotlinpoet_1_14_2//file",
     out = "com/squareup/kotlinpoet/1.14.2/kotlinpoet-1.14.2.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "junit_junit",
	jar = "junit/junit/4.13.2/junit-4.13.2.jar",
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
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_checkerframework_checker_qual",
	jar = "org/checkerframework/checker-qual/3.43.0/checker-qual-3.43.0.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.checkerframework:checker-qual:3.43.0",
		"maven_url=https://repo1.maven.org/maven2/org/checkerframework/checker-qual/3.43.0/checker-qual-3.43.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=3fbc2e98f05854c3df16df9abaa955b91b15b3ecac33623208ed6424640ef0f6",
	],
	maven_coordinates = "org.checkerframework:checker-qual:3.43.0",
	maven_url = "https://repo1.maven.org/maven2/org/checkerframework/checker-qual/3.43.0/checker-qual-3.43.0.jar",
	visibility = ["@@//visibility:private"],
)
alias(
	name = "org_checkerframework_checker_qual_3_43_0",
	actual = "org_checkerframework_checker_qual",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "org_checkerframework_checker_qual_3_43_0_extension",
     src = "@org_checkerframework_checker_qual_3_43_0//file",
     out = "org/checkerframework/checker-qual/3.43.0/checker-qual-3.43.0.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_hamcrest_hamcrest_core",
	jar = "org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
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
	visibility = ["@@//visibility:private"],
)
alias(
	name = "org_hamcrest_hamcrest_core_1_3",
	actual = "org_hamcrest_hamcrest_core",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "org_hamcrest_hamcrest_core_1_3_extension",
     src = "@org_hamcrest_hamcrest_core_1_3//file",
     out = "org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_reflect",
	jar = "org/jetbrains/kotlin/kotlin-reflect/1.8.21/kotlin-reflect-1.8.21.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-reflect:1.8.21",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-reflect/1.8.21/kotlin-reflect-1.8.21.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=8a6cd5a3cf092acee274ce2c444dc36eefdb631579859dd4d857b3309a529c91",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-reflect:1.8.21",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-reflect/1.8.21/kotlin-reflect-1.8.21.jar",
	visibility = ["@@//visibility:private"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_reflect_1_8_21",
	actual = "org_jetbrains_kotlin_kotlin_reflect",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_reflect_1_8_21_extension",
     src = "@org_jetbrains_kotlin_kotlin_reflect_1_8_21//file",
     out = "org/jetbrains/kotlin/kotlin-reflect/1.8.21/kotlin-reflect-1.8.21.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_stdlib",
	jar = "org/jetbrains/kotlin/kotlin-stdlib/2.1.0/kotlin-stdlib-2.1.0.jar",
	deps = [
		":org_jetbrains_annotations",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-stdlib:2.1.0",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.1.0/kotlin-stdlib-2.1.0.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=d6f91b7b0f306cca299fec74fb7c34e4874d6f5ec5b925a0b4de21901e119c3f",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-stdlib:2.1.0",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.1.0/kotlin-stdlib-2.1.0.jar",
	visibility = ["@@//visibility:private"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_stdlib_2_1_0",
	actual = "org_jetbrains_kotlin_kotlin_stdlib",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_stdlib_2_1_0_extension",
     src = "@org_jetbrains_kotlin_kotlin_stdlib_2_1_0//file",
     out = "org/jetbrains/kotlin/kotlin-stdlib/2.1.0/kotlin-stdlib-2.1.0.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7",
	jar = "org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.8.21/kotlin-stdlib-jdk7-1.8.21.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.21",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.8.21/kotlin-stdlib-jdk7-1.8.21.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=33d148db0e11debd0d90677d28242bced907f9c77730000fd597867089039d86",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.21",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.8.21/kotlin-stdlib-jdk7-1.8.21.jar",
	visibility = ["@@//visibility:private"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7_1_8_21",
	actual = "org_jetbrains_kotlin_kotlin_stdlib_jdk7",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_stdlib_jdk7_1_8_21_extension",
     src = "@org_jetbrains_kotlin_kotlin_stdlib_jdk7_1_8_21//file",
     out = "org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.8.21/kotlin-stdlib-jdk7-1.8.21.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8",
	jar = "org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.8.21/kotlin-stdlib-jdk8-1.8.21.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_kotlin_kotlin_stdlib_jdk7",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.21",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.8.21/kotlin-stdlib-jdk8-1.8.21.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=3db752a30074f06ee6c57984aa6f27da44f4d2bbc7f5442651f6988f1cb2b7d7",
	],
	maven_coordinates = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.21",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.8.21/kotlin-stdlib-jdk8-1.8.21.jar",
	visibility = ["@@//visibility:private"],
)
alias(
	name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8_1_8_21",
	actual = "org_jetbrains_kotlin_kotlin_stdlib_jdk8",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "org_jetbrains_kotlin_kotlin_stdlib_jdk8_1_8_21_extension",
     src = "@org_jetbrains_kotlin_kotlin_stdlib_jdk8_1_8_21//file",
     out = "org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.8.21/kotlin-stdlib-jdk8-1.8.21.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_jetbrains_kotlinx_kotlinx_coroutines_core",
	jar = "org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.1/kotlinx-coroutines-core-1.10.1.jar",
	deps = [
		":org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.1/kotlinx-coroutines-core-1.10.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=fae4771dd987cfadabae129dd7f625af40d9e4f14abb7ffc72e42dccb97b7010",
	],
	maven_coordinates = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.1/kotlinx-coroutines-core-1.10.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_1_10_1",
	actual = "org_jetbrains_kotlinx_kotlinx_coroutines_core",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_1_10_1_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_coroutines_core_1_10_1//file",
     out = "org/jetbrains/kotlinx/kotlinx-coroutines-core/1.10.1/kotlinx-coroutines-core-1.10.1.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm",
	jar = "org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.1/kotlinx-coroutines-core-jvm-1.10.1.jar",
	deps = [
		":org_jetbrains_kotlin_kotlin_stdlib",
		":org_jetbrains_annotations",
	],
	tags = [
		"maven_coordinates=org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.1",
		"maven_url=https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.1/kotlinx-coroutines-core-jvm-1.10.1.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=069c5988633230e074ec0d39321ec3cdaa4547c49e90ba936c63d8fc91c8c00d",
	],
	maven_coordinates = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.1",
	maven_url = "https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.1/kotlinx-coroutines-core-jvm-1.10.1.jar",
	visibility = ["//visibility:public"],
)
alias(
	name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_1_10_1",
	actual = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm",
	visibility = ["//visibility:public"],
)
copy_file(
     name = "org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_1_10_1_extension",
     src = "@org_jetbrains_kotlinx_kotlinx_coroutines_core_jvm_1_10_1//file",
     out = "org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.10.1/kotlinx-coroutines-core-jvm-1.10.1.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_jetbrains_annotations",
	jar = "org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar",
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
	visibility = ["@@//visibility:private"],
)
alias(
	name = "org_jetbrains_annotations_23_0_0",
	actual = "org_jetbrains_annotations",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "org_jetbrains_annotations_23_0_0_extension",
     src = "@org_jetbrains_annotations_23_0_0//file",
     out = "org/jetbrains/annotations/23.0.0/annotations-23.0.0.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
)
jvm_import(
	name = "org_ow2_asm_asm",
	jar = "org/ow2/asm/asm/9.6/asm-9.6.jar",
	deps = [
	],
	tags = [
		"maven_coordinates=org.ow2.asm:asm:9.6",
		"maven_url=https://repo1.maven.org/maven2/org/ow2/asm/asm/9.6/asm-9.6.jar",
		"maven_repository=https://repo1.maven.org/maven2",
		"maven_sha256=3c6fac2424db3d4a853b669f4e3d1d9c3c552235e19a319673f887083c2303a1",
	],
	maven_coordinates = "org.ow2.asm:asm:9.6",
	maven_url = "https://repo1.maven.org/maven2/org/ow2/asm/asm/9.6/asm-9.6.jar",
	visibility = ["@@//visibility:private"],
)
alias(
	name = "org_ow2_asm_asm_9_6",
	actual = "org_ow2_asm_asm",
	visibility = ["@@//visibility:private"],
)
copy_file(
     name = "org_ow2_asm_asm_9_6_extension",
     src = "@org_ow2_asm_asm_9_6//file",
     out = "org/ow2/asm/asm/9.6/asm-9.6.jar",
     allow_symlink = True,
     visibility = ["@@//visibility:private"]
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
  actual = "@@rules_jvm_external++maven+unpinned_grpc_kotlin_maven//:pin",
  visibility = ["//visibility:public"],
)
