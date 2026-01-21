load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "workorchestrator",
    srcs = ["WorkOrchestrator.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/worker",
        "//first_party/codestone/lifecycle/startstop",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["WorkOrchestratorImpl.kt"],
    deps = [
        ":workorchestrator",
        "//first_party/codestone/lifecycle/conversion/multiconverter",
        "//first_party/codestone/lifecycle/platforms/coroutines:platform",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "WorkOrchestratorImplTest",
    testonly = True,
    test_class = "com.jackbradshaw.codestone.lifecycle.orchestrator.WorkOrchestratorImplTest",
    srcs = [
        "WorkOrchestratorImplTest.kt",
    ],
    runtime_deps = [
        "//first_party/codestone/lifecycle/platforms/coroutines:platform",
        "//first_party/codestone/lifecycle/platforms/startstop:platform",
    ],
    deps = [
        ":workorchestrator",
        ":impl",
        "//first_party/codestone/lifecycle/orchestrator/factory:workorchestrators",
        "//first_party/codestone/lifecycle/platforms/coroutines:platform",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

java_library(
    name = "orchestrator",
    exports = [
        ":workorchestrator",
        ":impl",
        "//first_party/codestone/lifecycle/orchestrator/factory",
    ],
)
