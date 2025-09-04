# aspect_tools_telemetry

Aspect's ruleset telemetry Bazel module.

This package defines a Bazel extension which allows for rulesets to report usage to Aspect, allowing us to estimate the install base of Bazel, rulesets, and monitor trends in the ecosystem such as library usage and Bazel versions.

## When reporting occurs

`aspect_tools_telemetry` is implemented as a Bazel module which performs side-effects.
This means that telemetry is collected at repository granularity only when Bazel modules are invalidated and re-evaluated.

Examples:
- A user adding a new Bazel dependency will invalidate modules and trigger reporting
- A user making a local code change and performing a build will not trigger reporting

## Controlling reporting

The telemetry module honors `$DO_NOT_TRACK` and will disable itself if this variable is set.

The telemetry module can be controlled at a finer granularity with the `$ASPECT_TOOLS_TELEMETRY` environment variable.
`$ASPECT_TOOLS_TELEMETRY` is a comma joined list of reporting features using Bazel's set notation.

Some of the collector features can be overridden or salted for further privacy if so desired.

- `$ASPECT_TOOLS_TELEMETRY_SALT` is a value which will be included whenever computing a hash or ID.
  This allows you to salt correlation IDs if you so choose.

### Example `.bazelrc` configurations

``` shell
# Since sha1 is a vulnerable hash we recommend providing a salt value
common --repo_env=ASPECT_TOOLS_TELEMETRY_SALT=[FIXME small random value]

common --repo_env=ASPECT_TOOLS_TELEMETRY=all  # enabled (default)
common --repo_env=ASPECT_TOOLS_TELEMETRY=deps # only report aspect deps

common --repo_env=ASPECT_TOOLS_TELEMETRY=     # disabled
common --repo_env=ASPECT_TOOLS_TELEMETRY=-all # also disabled
common --repo_env=ASPECT_TOOLS_TELEMETRY=-org # just disable org name reporting
```

## Reporting features

- `arch`: The arch per `repository_ctx.os.arch`
- `bazel_version`: The version of Bazel
- `bazelisk`: Is the `bazelisk` tool is being used
- `ci`: Is the build occurring in CI/CD or locally
- `counter`: The build counter if available
- `deps`: The active set of bzlmod modules which have opted into telemetry
- `has_bazel_module`: Is a `MODULE.bazel` being used
- `has_bazel_prelude`: Does the project use a `prelude_bazel`
- `has_bazel_tool`: Does the project use a `tools/bazel` script
- `has_bazel_workspace`: Does the project still have a `WORKSPACE` file
- `id`: A hash of the repo is used as a stable pseudononymous ID
- `org`: A human readable organization name string
- `os`: The os per `repository_ctx.os.name`
- `runner`: The CI/CD system being used if any
- `user`: A salted hash of the user running Bazel's name

## Example exploration

The included examples/simple submodule provides a sandbox for easily testing the telemetry module's behavior.

``` shellsession
❯ cd examples/simple

# Default unconfigured behavior
❯ bazel build \
    --repo_env=CI=1 \
    --repo_env=DRONE_BUILD_NUMBER=678 \
    --repo_env=GIT_URL=http://github.com/aspect-build/tools_telemetry.git \
    //:report.json && cat bazel-bin/report.json
INFO: Analyzed target //:report.json (7 packages loaded, 10 targets configured).
INFO: Found 1 target...
Target //:report.json up-to-date:
  bazel-bin/report.json
INFO: Elapsed time: 0.300s, Critical Path: 0.03s
INFO: 2 processes: 1 internal, 1 darwin-sandbox.
INFO: Build completed successfully, 2 total actions
{
 "tools_telemetry": {
   "arch": "aarch64",
   "bazel_version": "8.3.1",
   "bazelisk": true,
   "ci": true,
   "counter": "678",
   "deps": {
      "aspect_tools_telemetry": "0.0.0",
      "simple-example": "0.0.0"
   },
   "has_bazel_module": true,
   "has_bazel_prelude": false,
   "has_bazel_tool": false,
   "has_bazel_workspace": false,
   "id": "ccc935dd186ed92c3322efb755e8f70ede47c243",
   "org": null,
   "os": "mac os x",
   "runner": "drone",
   "user": "94fb5cf79f8322bd3f999a10eb713f478470979c"
}%

# Disabled behavior
❯ bazel build \
    --repo_env=CI=1 \
    --repo_env=BUILD_NUMBER=678 \
    --repo_env=JENKINS_HOME=$HOME \
    --repo_env=GIT_URL=http://github.com/aspect-build/tools_telemetry.git \
    --repo_env=DO_NOT_TRACK=1 //:report.json \
    && cat bazel-bin/report.json
INFO: Analyzed target //:report.json (7 packages loaded, 10 targets configured).
INFO: Found 1 target...
Target //:report.json up-to-date:
  bazel-bin/report.json
INFO: Elapsed time: 0.071s, Critical Path: 0.00s
INFO: 1 process: 1 action cache hit, 1 internal.
INFO: Build completed successfully, 1 total action
{}%
```

## Report inspection

For transparency reports are persisted into the Bazel configuration and can be inspected as `@aspect_tools_telemetry_report//:report.json`.

``` shellsession
❯ cat $(bazel query --output=location @aspect_tools_telemetry_report//:report.json | cut -d: -f1)
```

## Privacy policy

Data collected by this telemetry package is reported to Aspect Build Systems Inc. and governed under our privacy policy.

For more please see https://www.aspect.build/privacy-policy
