:::{default-domain} bzl
:::

# Download (bzlmod)

:::{seealso}
For WORKSPACE instructions see [here](./download-workspace).
:::

To add PyPI dependencies to your `MODULE.bazel` file, use the `pip.parse`
extension, and call it to create the central external repo and individual wheel
external repos. Include in the `MODULE.bazel` the toolchain extension as shown
in the first bzlmod example above.

```starlark
pip = use_extension("@rules_python//python/extensions:pip.bzl", "pip")

pip.parse(
    hub_name = "my_deps",
    python_version = "3.13",
    requirements_lock = "//:requirements_lock_3_11.txt",
)

use_repo(pip, "my_deps")
```

For more documentation, see the bzlmod examples under the {gh-path}`examples` folder or the documentation
for the {obj}`@rules_python//python/extensions:pip.bzl` extension.

:::note}
We are using a host-platform compatible toolchain by default to setup pip dependencies.
During the setup phase, we create some symlinks, which may be inefficient on Windows
by default. In that case use the following `.bazelrc` options to improve performance if
you have admin privileges:

    startup --windows_enable_symlinks

This will enable symlinks on Windows and help with bootstrap performance of setting up the 
hermetic host python interpreter on this platform. Linux and OSX users should see no
difference.
:::

## Interpreter selection

The {obj}`pip.parse` `bzlmod` extension by default uses the hermetic python toolchain for the host
platform, but you can customize the interpreter using {attr}`pip.parse.python_interpreter` and
{attr}`pip.parse.python_interpreter_target`.

You can use the pip extension multiple times. This configuration will create
multiple external repos that have no relation to one another and may result in
downloading the same wheels numerous times.

As with any repository rule or extension, if you would like to ensure that `pip_parse` is
re-executed to pick up a non-hermetic change to your environment (e.g., updating your system
`python` interpreter), you can force it to re-execute by running `bazel sync --only [pip_parse
name]`.

(per-os-arch-requirements)=
## Requirements for a specific OS/Architecture

In some cases you may need to use different requirements files for different OS, Arch combinations.
This is enabled via the `requirements_by_platform` attribute in `pip.parse` extension and the
{obj}`pip.parse` tag class. The keys of the dictionary are labels to the file and the values are a
list of comma separated target (os, arch) tuples.

For example:
```starlark
    # ...
    requirements_by_platform = {
        "requirements_linux_x86_64.txt": "linux_x86_64",
        "requirements_osx.txt": "osx_*",
        "requirements_linux_exotic.txt": "linux_exotic",
        "requirements_some_platforms.txt": "linux_aarch64,windows_*",
    },
    # For the list of standard platforms that the rules_python has toolchains for, default to
    # the following requirements file.
    requirements_lock = "requirements_lock.txt",
```

In case of duplicate platforms, `rules_python` will raise an error as there has
to be unambiguous mapping of the requirement files to the (os, arch) tuples.

An alternative way is to use per-OS requirement attributes.
```starlark
    # ...
    requirements_windows = "requirements_windows.txt",
    requirements_darwin = "requirements_darwin.txt",
    # For the remaining platforms (which is basically only linux OS), use this file.
    requirements_lock = "requirements_lock.txt",
)
```

:::{note}
If you are using a universal lock file but want to restrict the list of platforms that
the lock file will be evaluated against, consider using the aforementioned
`requirements_by_platform` attribute and listing the platforms explicitly.
:::

## Multi-platform support

Historically the {obj}`pip_parse` and {obj}`pip.parse` have been only downloading/building
Python dependencies for the host platform that the `bazel` commands are executed on. Over
the years people started needing support for building containers and usually that involves
fetching dependencies for a particular target platform that may be other than the host
platform.

Multi-platform support of cross-building the wheels can be done in two ways:
1. using {attr}`experimental_index_url` for the {bzl:obj}`pip.parse` bzlmod tag class
2. using {attr}`pip.parse.download_only` setting.

:::{warning}
This will not for sdists with C extensions, but pure Python sdists may still work using the first
approach.
:::

### Using `download_only` attribute

Let's say you have 2 requirements files:
```
# requirements.linux_x86_64.txt
--platform=manylinux_2_17_x86_64
--python-version=39
--implementation=cp
--abi=cp39

foo==0.0.1 --hash=sha256:deadbeef
bar==0.0.1 --hash=sha256:deadb00f
```

```
# requirements.osx_aarch64.txt contents
--platform=macosx_10_9_arm64
--python-version=39
--implementation=cp
--abi=cp39

foo==0.0.3 --hash=sha256:deadbaaf
```

With these 2 files your {bzl:obj}`pip.parse` could look like:
```starlark
pip.parse(
    hub_name = "pip",
    python_version = "3.9",
    # Tell `pip` to ignore sdists
    download_only = True,
    requirements_by_platform = {
        "requirements.linux_x86_64.txt": "linux_x86_64",
        "requirements.osx_aarch64.txt": "osx_aarch64",
    },
)
```

With this, the `pip.parse` will create a hub repository that is going to
support only two platforms - `cp39_osx_aarch64` and `cp39_linux_x86_64` and it
will only use `wheels` and ignore any sdists that it may find on the PyPI
compatible indexes.

:::{warning}
Because bazel is not aware what exactly is downloaded, the same wheel may be downloaded
multiple times.
:::

:::{note}
This will only work for wheel-only setups, i.e. all of your dependencies need to have wheels
available on the PyPI index that you use.
:::

### Customizing `Requires-Dist` resolution

:::{note}
Currently this is disabled by default, but you can turn it on using 
{envvar}`RULES_PYTHON_ENABLE_PIPSTAR` environment variable.
:::

In order to understand what dependencies to pull for a particular package
`rules_python` parses the `whl` file [`METADATA`][metadata].
Packages can express dependencies via `Requires-Dist` and they can add conditions using
"environment markers", which represent the Python version, OS, etc.

While the PyPI integration provides reasonable defaults to support most
platforms and environment markers, the values it uses can be customized in case
more esoteric configurations are needed.

To customize the values used, you need to do two things:
1. Define a target that returns {obj}`EnvMarkerInfo`
2. Set the {obj}`//python/config_settings:pip_env_marker_config` flag to
   the target defined in (1).

The keys and values should be compatible with the [PyPA dependency specifiers
specification](https://packaging.python.org/en/latest/specifications/dependency-specifiers/).
This is not strictly enforced, however, so you can return a subset of keys or
additional keys, which become available during dependency evaluation.

[metadata]: https://packaging.python.org/en/latest/specifications/core-metadata/

(bazel-downloader)=
### Bazel downloader and multi-platform wheel hub repository.

:::{warning}
This is currently still experimental and whilst it has been proven to work in quite a few
environments, the APIs are still being finalized and there may be changes to the APIs for this
feature without much notice.

The issues that you can subscribe to for updates are:
* {gh-issue}`260`
* {gh-issue}`1357`
:::

The {obj}`pip` extension supports pulling information from `PyPI` (or a compatible mirror) and it
will ensure that the [bazel downloader][bazel_downloader] is used for downloading the wheels.

This provides the following benefits:
* Integration with the [credential_helper](#credential-helper) to authenticate with private
  mirrors.
* Cache the downloaded wheels speeding up the consecutive re-initialization of the repositories.
* Reuse the same instance of the wheel for multiple target platforms.
* Allow using transitions and targeting free-threaded and musl platforms more easily.
* Avoids `pip` for wheel fetching and results in much faster dependency fetching.

To enable the feature specify {attr}`pip.parse.experimental_index_url` as shown in
the {gh-path}`examples/bzlmod/MODULE.bazel` example.

Similar to [uv](https://docs.astral.sh/uv/configuration/indexes/), one can override the
index that is used for a single package. By default we first search in the index specified by
{attr}`pip.parse.experimental_index_url`, then we iterate through the
{attr}`pip.parse.experimental_extra_index_urls` unless there are overrides specified via
{attr}`pip.parse.experimental_index_url_overrides`.

When using this feature during the `pip` extension evaluation you will see the accessed indexes similar to below:
```console
Loading: 0 packages loaded
    Fetching module extension @@//python/extensions:pip.bzl%pip; Fetch package lists from PyPI index
    Fetching https://pypi.org/simple/jinja2/

```

This does not mean that `rules_python` is fetching the wheels eagerly, but it
rather means that it is calling the PyPI server to get the Simple API response
to get the list of all available source and wheel distributions. Once it has
got all of the available distributions, it will select the right ones depending
on the `sha256` values in your `requirements_lock.txt` file. If `sha256` hashes
are not present in the requirements file, we will fallback to matching by version
specified in the lock file.

Fetching the distribution information from the PyPI allows `rules_python` to
know which `whl` should be used on which target platform and it will determine
that by parsing the `whl` filename based on [PEP600], [PEP656] standards. This
allows the user to configure the behaviour by using the following publicly
available flags:
* {obj}`--@rules_python//python/config_settings:py_linux_libc` for selecting the Linux libc variant.
* {obj}`--@rules_python//python/config_settings:pip_whl` for selecting `whl` distribution preference.
* {obj}`--@rules_python//python/config_settings:pip_whl_osx_arch` for selecting MacOS wheel preference.
* {obj}`--@rules_python//python/config_settings:pip_whl_glibc_version` for selecting the GLIBC version compatibility.
* {obj}`--@rules_python//python/config_settings:pip_whl_muslc_version` for selecting the musl version compatibility.
* {obj}`--@rules_python//python/config_settings:pip_whl_osx_version` for selecting MacOS version compatibility.

[bazel_downloader]: https://bazel.build/rules/lib/builtins/repository_ctx#download
[pep600]: https://peps.python.org/pep-0600/
[pep656]: https://peps.python.org/pep-0656/

(credential-helper)=
## Credential Helper

The [Bazel downloader](#bazel-downloader) usage allows for the Bazel
[Credential Helper][cred-helper-design].
Your python artifact registry may provide a credential helper for you. 
Refer to your index's docs to see if one is provided.

The simplest form of a credential helper is a bash script that accepts an arg and spits out JSON to
stdout. For a service like Google Artifact Registry that uses ['Basic' HTTP Auth][rfc7617] and does
not provide a credential helper that conforms to the [spec][cred-helper-spec], the script might
look like:

```bash
#!/bin/bash
# cred_helper.sh
ARG=$1  # but we don't do anything with it as it's always "get"

# formatting is optional
echo '{'
echo '  "headers": {'
echo '    "Authorization": ["Basic dGVzdDoxMjPCow=="]'
echo '  }'
echo '}'
```

Configure Bazel to use this credential helper for your python index `example.com`:

```
# .bazelrc
build --credential_helper=example.com=/full/path/to/cred_helper.sh
```

Bazel will call this file like `cred_helper.sh get` and use the returned JSON to inject headers
into whatever HTTP(S) request it performs against `example.com`.

See the [Credential Helper Spec][cred-helper-spec] for more details.

[rfc7617]: https://datatracker.ietf.org/doc/html/rfc7617
[cred-helper-design]: https://github.com/bazelbuild/proposals/blob/main/designs/2022-06-07-bazel-credential-helpers.md
[cred-helper-spec]: https://github.com/EngFlow/credential-helper-spec/blob/main/spec.md
