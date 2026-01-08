load("@pypi//:requirements.bzl", "requirement")
load("@rules_python//python:defs.bzl", "py_binary")

package(default_visibility = ["//first_party/council:__subpackages__"])

py_binary(
    name = "server_bin",
    srcs = ["server.py"],
    data = [
        "agent_prompts",
        "instructions",
        "simulation_config.json",
        "static",
    ],
    main = "server.py",
    deps = [
        requirement("fastapi"),
        requirement("uvicorn"),
        requirement("google-generativeai"),
        requirement("python-dotenv"),
        requirement("websockets"),
    ],
)

py_binary(
    name = "simulation_bin",
    srcs = ["simulation.py"],
    data = [
        "agent_prompts",
        "instructions",
        "simulation_config.json",
    ],
    main = "simulation.py",
    deps = [
        requirement("websockets"),
        requirement("google-generativeai"),
        requirement("requests"),
    ],
)

sh_binary(
    name = "council",
    srcs = ["start.sh"],
    data = [
        ":server_bin",
        ":simulation_bin",
    ],
    deps = ["@bazel_tools//tools/bash/runfiles"],
)
