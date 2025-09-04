load("//toolchain/triplet:TripletInfo.bzl", "TripletInfo")
load(":cpu.bzl", "cpu")
load(":os.bzl", "os")
load(":libc.bzl", "libc")

visibility("//toolchain/local/...")

def detect(rctx):
    return TripletInfo("{}-{}-{}".format(
        cpu(rctx),
        os(rctx).value,
        libc(rctx).value,
    ))
