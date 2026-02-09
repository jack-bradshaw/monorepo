load("@pypi//:requirements.bzl", "requirement")
load("@rules_python//python:defs.bzl", "py_binary")
load("//first_party/bash_runfiles:shell.bzl", "sh_binary_with_runfiles", "sh_library_with_runfiles")
load("//first_party/council:publicity.bzl", "PUBLICITY")

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

sh_binary_with_runfiles(
    name = "council",
    src = "start.sh",
    data = [
        ":server_bin",
        ":simulation_bin",
    ],
    visibility = PUBLICITY,
)
