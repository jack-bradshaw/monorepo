DEFAULT_FIRST_PARTY_ROOT = "//first_party"

def public():
    """Allows unrestricted access.

    Returns:
        A list of visibility labels.
    """

    return ["//visibility:public"]

def internal(first_party_root = DEFAULT_FIRST_PARTY_ROOT):
    """Limits access to all first party properties (to the exclusion of the general public).

    Args:
        first_party_root: The root directory for the workspace, string, optional, defaults to "//first_party".
                          Must start with "/" or "//".

    Returns:
        A list of visibility labels.
    """

    if not first_party_root.startswith("//"):
        fail("Publicity: first_party_root must start with '//', got '%s'" % first_party_root)

    return ["%s:__subpackages__" % first_party_root]

def restricted(allowed_packages, first_party_root = DEFAULT_FIRST_PARTY_ROOT):
    """Limits access to an allowlist of first-party properties.

    Excludes all others (both first-party properties and the general public).

    Args:
        allowed_packages: Package names relative to the root, list of strings, required.
            Entries should NOT have a leading "/" or "//".
        first_party_root: The root directory for the workspace, string, optional, defaults to "//first_party".
                          Must start with "//".

    Returns:
        A list of visibility labels.
    """

    if not first_party_root.startswith("//"):
        fail("Publicity: first_party_root must start with '//', got '%s'" % first_party_root)

    if any([pkg.startswith("/") for pkg in allowed_packages]):
        fail("Publicity.restricted: package names must not start with '/', got '%s'" % allowed_packages)

    return ["%s/%s:__subpackages__" % (first_party_root, pkg) for pkg in allowed_packages]

def quarantined(package_name):
    """Limits access to this first party property.

    Not visible to the general public or other first party properties. Fails if `package_name` does
    not start with "//".

    Args:
        package_name: The fully qualified name of the package defining this visibility, string, required.
                      Must start with "//" and match the physical location of the file (e.g. "//first_party/foo").

    Returns:
        A list of visibility labels.
    """

    if not package_name.startswith("//"):
        fail("Publicity.quarantined: package_name must start with '//', got '%s'" % package_name)

    return ["%s:__subpackages__" % package_name]
