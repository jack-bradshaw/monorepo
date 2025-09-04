load("@local//:triplet.bzl", LOCAL = "TRIPLET")
load(":TripletInfo.bzl", "TripletInfo")

visibility("//toolchain/...")

TRIPLETS = (
    LOCAL,
    TripletInfo("arm64-linux-gnu"),
    TripletInfo("amd64-linux-gnu"),
)
