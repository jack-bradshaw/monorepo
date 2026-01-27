# Starlark Directives

Directives for [Starlark](https://github.com/bazelbuild/starlark) documentation in this repository.

## Scope

The directives in this document apply to all Starlark files (`.bzl`) and `BUILD` files in this
repository; however, the contents of [third_party](/third_party) are explicitly exempt, as they
originate from external sources.

## Function Documentation

Directives for documenting Starlark functions.

### Practice: Document Arguments

All Starlark functions must document their arguments in an `Args:` section using the following
comma-separated format: `[Name]: [Description], [Type], [Optionality], [Default].`

Example:

```starlark
def my_macro(name, srcs = [], visibility = None):
    """My macro description.

    Args:
        name: The name of the target, string, required.
        srcs: A list of source files, list of labels, optional, defaults to [].
        visibility: The visibility of the target, list of labels, optional, defaults to None.
    """
    pass
```

This ensures consistency and provides contributors with all necessary information (type,
optionality, defaults) in a concise, standardized format.

### Practice: Document Return Values

Any Starlark function that returns a value must document it in a `Returns:` section. Any Starlark
function that does not return a value must omit the `Returns:` section.

Example:

```starlark
def my_helper():
    """My helper description.

    Returns:
        A list of generated output files.
    """
    return []
```

This provides clarity on function outputs and aids in understanding the data flow within Starlark
modules.
