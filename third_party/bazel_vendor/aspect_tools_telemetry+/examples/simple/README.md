# Example project

Serves as a demo for collecting telemetry

``` shellsession
❯ aspect bazel build \
    --repo_env=CI=1 \
    --repo_env=DRONE_BUILD_NUMBER=680 \
    --repo_env=GIT_URL=http://github.com/aspect-build/tools_telemetry.git \
    //:report.json && cat bazel-bin/report.json
```
