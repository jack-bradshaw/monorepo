load("@rules_tf//tf:def.bzl", "tf_providers_versions", "tf_module")

tf_providers_versions(
    name = "providers",
    tf_version = "1.2.3",
    providers = {
        "random" : "hashicorp/random:>=3.3",
        "null"   : "hashicorp/null:>=3.1",
    },
)

tf_module(
    name = "thing",
   srcs = ["main.tf"],
    providers = [
        "random",
    ],
    providers_versions = ":providers",
)