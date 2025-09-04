load("//:sha1.bzl", sha1="sha1")

def hash(repository_ctx, data, fn=sha1):
    """Hash, honoring a salt value from the environment."""

    salt = repository_ctx.os.environ.get("ASPECT_TOOLS_TELEMETRY_SALT")
    if salt:
        data = salt + ";" + data
    return fn(data)
