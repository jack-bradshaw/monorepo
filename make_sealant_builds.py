import os

packages = ["connectable", "funnel", "inlet", "junction", "manifold", "outlet", "pipe"]

for pkg in packages:
    dir_path = f"first_party/sealant/{pkg}"
    if not os.path.exists(dir_path):
        continue

    kt_name = pkg.capitalize()

    deps = [
        '"@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"',
    ]
    if pkg == "connectable":
        deps.append('"//first_party/closet/observable"')
    else:
        deps.append('"//first_party/sealant/connectable"')

    test_deps = [
        f'":{pkg}"',
        '"//first_party/chronosphere/testingtaskbarrier"',
        '"@com_jackbradshaw_maven//:com_google_truth_truth"',
        '"@com_jackbradshaw_maven//:junit_junit"',
        '"@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"',
    ]
    if pkg != "connectable":
        test_deps.append('"//first_party/sealant/connectable:test_abstract"')

    build_content = f"""load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "{pkg}",
    srcs = ["{kt_name}.kt"],
    visibility = ["//visibility:public"],
    deps = [
        {", ".join(deps)}
    ],
)

kt_jvm_library(
    name = "test_abstract",
    srcs = ["{kt_name}Test.kt"],
    visibility = ["//visibility:public"],
    deps = [
        {", ".join(test_deps)}
    ],
)
"""
    with open(f"{dir_path}/BUILD", "w") as f:
        f.write(build_content)
