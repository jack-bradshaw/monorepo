load("@rules_python//python:defs.bzl", "py_library")

package(default_visibility = ["//visibility:private"])

py_library(
    name = "pip_deps_must_resolve",
    deps = [
        "@pypi//fastapi",
        "@pypi//google_generativeai",
        "@pypi//python_dotenv",
        "@pypi//requests",
        "@pypi//uvicorn",
        "@pypi//websockets",
    ],
)
