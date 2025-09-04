visibility("//toolchain/...")

def split(value, delimiter, map):
    """
    Splits a value and invokes a callback based on the number of split elements.

    ```
    a, b = split("a.b", ".", {
        1: lambda a: (major, None),
        2: lambda a, b: (major, minor),
    })

    ```

    Args:
      value: The value to invoke `.split` on, often a `str`.
      delimiter: The argument to pass to `.split` which delimits the split parts.
      map: A `Mapping[int, callable]` that processes the split parts.

    Returns:
      The result of the matching callable
    """
    parts = value.split(delimiter)
    length = len(parts)

    def _fail(*_):
        fail("No callable specified for a split length of `{}` with `{}` on `{}`".format(length, delimiter, value))

    return map.get(length, _fail)(*parts)
