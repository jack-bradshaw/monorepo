load("//toolchain/triplet:split.bzl", "split")
load("//toolchain/triplet:VersionedInfo.bzl", "VersionedInfo")

visibility("//toolchain/local/triplet/...")

def _unquote(value):
    if value[0] == '"' and value[-1] == '"':
        return value[1:-1]
    return value

def _release(rctx, path):
    content = rctx.read(path)
    lines = content.splitlines()
    pairs = [line.split("=", 1) for line in lines if "=" in line]
    processed = {"id": None, "id_like": None}
    processed |= {k.lower(): _unquote(v) for k, v in pairs}
    data = struct(**processed)

    if data.id in ("arch", "debian", "fedora"):
        return VersionedInfo("gnu")

    if data.id in ("alpine",):
        return VersionedInfo("musl")

    if data.id_like in ("debian",):
        return VersionedInfo("gnu")

    fail("Failed to determine host C library from `{}`".format(path))

def _ldd(rctx, path):
    result = rctx.execute([path, "--version"])

    # musl’s dynamic linker returns 1 and prints to stderr
    if result.return_code == 1 and "musl libc" in result.stderr:
        second = result.stderr.strip().splitlines()[1]
        version = split(second, " ", {
            2: lambda _, v: v,
        })

        return VersionedInfo("musl.{}".format(version))

    if result.return_code != 0:
        fail("Failed to retrieve `ldd` version output:\n{}".format(result.stderr))

    first = result.stdout.strip().splitlines()[0]

    if first.startswith("ldd"):
        _, _, description = first.partition(" (")
        description, _, version = description.rpartition(") ")

        if description == "GNU libc" or "GLIBC" in description:
            return VersionedInfo("gnu.{}".format(version))

        if description == "cygwin":
            return VersionedInfo("cygwin.{}".format(version))

    fail("Failed to detect `{}` version:\n{}".format(path, result.stdout))

def _powershell(rctx, path):
    result = rctx.execute([path, "-Command", "Get-Package -Name 'Universal CRT Redistributable'| Format-Wide -Property Version"])
    if result.return_code != 0:
        fail("Failed to retrieve `Get-Package` version output:\n{}".format(result.stderr))

    version = result.stdout.strip()

    major, minor, build, patch = split(version, ".", {
        3: lambda w, x, y, z: (w, x, y, None),
        4: lambda w, x, y, z: (w, x, y, z),
    })

    if patch:
        return VersionedInfo("ucrt.{}.{}.{}+{}".format(int(major), int(minor), int(build), int(patch)))

    return VersionedInfo("ucrt.{}.{}.{}".format(int(major), int(minor), int(build)))

def _uname(rctx, path):
    result = rctx.execute((path, "-s"))
    if result.return_code != 0:
        fail("Failed to retrieve `uanme` kernel:\n{}".format(result.stderr))

    names = {
        "Darwin": "darwin",
    }

    name = result.stdout.strip()

    if name not in names:
        fail("Unsupported `libc` version from `uname`: {}".format(name))

    name = names[name]

    result = rctx.execute((path, "-r"))
    if result.return_code != 0:
        fail("Failed to retrieve `uanme` vresion:\n{}".format(result.stderr))

    version = result.stdout.strip()

    major, minor, patch = split(version, ".", {
        3: lambda x, y, z: (x, y, z),
    })

    return VersionedInfo("{}.{}.{}.{}".format(name, int(major), int(minor), int(patch)))

def libc(rctx):
    """
    Detects the host C library.

    Args:
      rctx: the repository context to use for detection.

    Return:
      A `VersionedInfo` operating system triplet part.
    """
    path = rctx.which("ldd")
    if path:
        return _ldd(rctx, path)

    path = rctx.path("/etc/os-release")
    if path.exists:
        return _release(rctx, path)

    path = rctx.which("powershell.exe")
    if path:
        return _powershell(rctx, path)

    path = rctx.which("uname")
    if path:
        return _uname(rctx, path)

    fail("Failed to detect host C library")
