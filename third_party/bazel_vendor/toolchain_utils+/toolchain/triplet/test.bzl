load("@bazel_skylib//lib:unittest.bzl", "analysistest", "asserts")
load(":TripletInfo.bzl", "TripletInfo")
load(":unversioned.bzl", "unversioned")

visibility("//toolchain/test/...")

def implementation(ctx):
    env = analysistest.begin(ctx)
    target = analysistest.target_under_test(env)
    info = target[TripletInfo]
    asserts.equals(env, "amd64-linux-gnu", info.value)
    asserts.equals(env, "amd64", info.cpu)
    asserts.equals(env, "linux", info.os.kind)
    asserts.equals(env, None, info.os.version)
    asserts.equals(env, "gnu", info.libc.kind)
    asserts.equals(env, None, info.libc.version)
    asserts.equals(env, info.constraints, (
        Label("//toolchain/constraint/cpu:amd64"),
        Label("//toolchain/constraint/os:linux"),
        Label("//toolchain/constraint/libc:gnu"),
    ))
    asserts.equals(env, unversioned(info).value, "amd64-linux-gnu")
    return analysistest.end(env)

triplet_test = analysistest.make(implementation)

test = triplet_test
