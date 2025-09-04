load("//toolchain/triplet:split.bzl", "split")
load("//toolchain/triplet:VersionedInfo.bzl", "VersionedInfo")

visibility("//toolchain/local/triplet/...")

def _header(rctx, path):
    """
    Reads the Linux version header to determine the correct Linux version.

    Args:
      rctx: The repository context that can execute commands on the host machine.
      path: the path to the Linux version header to read.

    Returns:
      The `VersionedInfo` provider
    """
    data = rctx.read(path).strip()

    def _split(line):
        if not line.startswith("#define"):
            return (None, None)

        _, name, value = line.split(" ", 2)

        if "(" in name:
            return (None, None)

        name = name.removeprefix("LINUX_VERSION_").lower()

        return (name, value)

    pairs = [_split(line) for line in data.splitlines()]
    map = {k: v for k, v in pairs if k and v}

    major = map.get("major", None)
    minor = map.get("patchlevel", None)
    patch = map.get("sublevel", None)

    if major and minor and patch:
        return VersionedInfo("linux.{}.{}.{}".format(int(major), int(minor), int(patch)))

    if "code" not in map:
        fail("Failed to find a `LINUX_VERSION_CODE` in {}".format(path))

    code = int(map["code"])

    major = (code >> 16) & 0xFF
    minor = (code >> 8) & 0xFF
    patch = (code >> 0) & 0xFF

    return VersionedInfo("linux.{}.{}.{}".format(major, minor, patch))

def _sw_vers(rctx, path):
    """
    Determines the operating system version from `sw_vers`.

    Args:
      rctx: The repository context that can execute commands on the host machine.
      path: the path to the `sw_vers` executable.

    Returns:
      The `VersionedInfo` provider
    """
    result = rctx.execute((path, "-productName"))
    if result.return_code != 0:
        fail("Failed to get `sw_vers` product name: {}".format(result.stderr))

    name = {
        "macOS": "macos",
    }[result.stdout.strip()]

    result = rctx.execute((path, "-productVersion"))
    if result.return_code != 0:
        fail("Failed to get `sw_vers` product version: {}".format(result.stderr))

    version = result.stdout.strip()

    major, minor, patch = split(version, ".", {
        1: lambda x: (x, 0, 0),
        2: lambda x, y: (x, y, 0),
        3: lambda x, y, z: (x, y, z),
    })

    return VersionedInfo("{}.{}.{}.{}".format(name, int(major), int(minor), int(patch)))

def _uname(rctx, path):
    """
    Determines the operating system version from `uname`

    Args:
      rctx: The repository context that can execute commands on the host machine.
      path: the path to the `uname` executable.

    Returns:
      The `VersionedInfo` provider
    """
    result = rctx.execute((path, "-s"))
    if result.return_code != 0:
        fail("Failed to get `uname` kernel: {}".format(result.stderr))

    stdout = result.stdout.strip()

    _, nt, version = stdout.partition("_NT-")
    if nt:
        version, patch = version.split("-")
        major, minor = version.split(".")
        return VersionedInfo("windows.{}.{}.{}".format(int(major), int(minor), int(patch)))

    kernel = {
        "Linux": "linux",
    }[result.stdout.strip()]

    result = rctx.execute((path, "-r"))
    if result.return_code != 0:
        fail("Failed to get `uname` release: {}".format(result.stderr))

    version = result.stdout.strip().split("-", 1)[0]

    major, minor, patch = split(version, ".", {
        3: lambda x, y, z: (x, y, z),
        4: lambda x, y, z, _: (x, y, z),
    })

    if rctx.path("/.dockerenv").exists:
        print("`uname` release is the host kernel inside a container.")

    return VersionedInfo("{}.{}.{}.{}".format(kernel, int(major), int(minor), int(patch)))

def _cmd(rctx, path):
    """
    Determines the operating system version from `ver`, a `cmd` built-in.

    Args:
      rctx: The repository context that can execute commands on the host machine.
      path: the path to the `cmd` executable.

    Returns:
      The `VersionedInfo` provider
    """
    result = rctx.execute((path, "/C", "ver"))
    if result.return_code != 0:
        fail("Failed to get `ver` release: {}".format(result.stderr))

    version = result.stdout.strip()
    sku, version = version.split(" [Version ")
    version = version.removesuffix("]")

    major, minor, build, patch = split(version, ".", {
        4: lambda w, x, y, z: (w, x, y, z),
    })

    variant = {
        "Microsoft Windows": "windows",
    }[sku]

    return VersionedInfo("{}.{}.{}.{}+{}".format(variant, int(major), int(minor), int(build), int(patch)))

def os(rctx):
    """
    Detects the host operating system.

    Args:
      rctx: the repository context to use for detection.

    Return:
      A `VersionedInfo` operating system triplet part.
    """
    path = rctx.path("/usr/include/linux/version.h")
    if path.exists:
        return _header(rctx, path)

    path = rctx.which("sw_vers")
    if path:
        return _sw_vers(rctx, path)

    path = rctx.which("cmd.exe")
    if path:
        return _cmd(rctx, path)

    path = rctx.which("uname")
    if path:
        return _uname(rctx, path)

    return VersionedInfo({
        "linux": "linux",
        "windows 10": "windows.10",
    }[rctx.os.name])
