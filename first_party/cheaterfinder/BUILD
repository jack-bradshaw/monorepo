load("@rules_python//python:defs.bzl", "py_binary")

py_binary(
  name="test",
  srcs = ["main.py"],
  main = "main.py",
  data = ["game.dem"],
  deps = ["@pypi//demoparser2", "@rules_python//python/runfiles"],
  python_version = "3.12",
)