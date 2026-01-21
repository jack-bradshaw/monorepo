load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "coordinator",
    srcs = ["Coordinator.kt"],
    deps = [
        "//first_party/codestone/interaction/destination",
        "//first_party/codestone/interaction/navigator",
        "//first_party/codestone/interaction/usable",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/worker",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["CoordinatorImpl.kt"],
    deps = [
        ":coordinator",
        "//first_party/codestone/interaction/destination",
        "//first_party/codestone/interaction/navigator",
        "//first_party/codestone/interaction/usable",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/worker",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/orchestrator:workorchestrator",
        "//first_party/codestone/lifecycle/orchestrator/factory:workorchestrators",
        "//first_party/codestone/lifecycle/platforms/startstop",
        "//first_party/codestone/lifecycle/platforms/coroutines",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "CoordinatorImplTest",
    test_class = "com.jackbradshaw.codestone.interaction.coordinator.CoordinatorImplTest",
    srcs = ["CoordinatorImplTest.kt"],
    deps = [
        ":impl",
        ":coordinator",
        "//first_party/codestone/interaction/destination",
        "//first_party/codestone/interaction/navigator",
        "//first_party/codestone/interaction/usable",
        "//first_party/codestone/lifecycle/orchestrator/factory:workorchestrators",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/worker",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/platforms/startstop",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
