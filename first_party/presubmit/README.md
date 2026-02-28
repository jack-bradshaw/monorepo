# Presubmit

Continuous integration system.

## Release

Not released to third party package managers.

## Checks

This package contains the following checks:

- [build.sh](/first_party/presubmit/build.sh): Verifies that all targets build.
- [test.sh](/first_party/presubmit/test.sh): Verifies that all test targets pass.
- [formatting.sh](/first_party/presubmit/formatting.sh): Verifies that all sources are in their
- [markdown_directives.sh](/first_party/presubmit/markdown_directives.sh): Verifies that markdown
  files follow the automated directives listed in
  [markdown](/first_party/contributing/documentation/markdown.md).

All checks must succeed for presubmit to pass. Even a single failed check blocks submission to main.

## Usage

Presubmit is triggered automatically when a PR is opened or updated on GitHub. It can be invoked
manually with:

```bash
source presubmit.sh; run_presubmit
```

## Infrastructure

A self-hosted runner is used for presubmit to alleviate the limitations of free GitHub action
runners, specifically the lack of a cache that can be shared between actions, which required a full
rebuild on each run.

### Details

Runner details:

- Host: `cloudtop` VM in `ops-cloudtop-jack-003` GCP project.
- OS: Ubuntu 22.04 LTS (x86_64).
- Agent: Standard Github Actions Runner (Linux x64).
- Persistence: The runner is stateful, meaning:
  - The Bazel Cache (`~/.cache/bazel`) is persistent across runs.
  - The repository is checked out to `actions-runner/_work/monorepo/monorepo` using incremental
    fetches.

### Security

Unauthorized code execution on the runner is blocked by the security model. External contributors
must be explicitly approved by [the repository owner](mailto:jack@jack-bradshaw) before GitHub will
execute the CI actions.

### Limitations

The runner has the following limitations:

- The runner is not fault tolerant. If it crashes or the machine restarts, it needs to be manually
  restarted via `gcloud` and SSH.
- The runner agent is not auto-updated. It must be manually updated via SSH.
- Manual Bazel cache invalidation may be required via SSH if it becomes corrupted.
- The CI machine was setup manually (not with terraform/tofu).

A more robust system may be setup in the future if required. For now, this system is more performant
than the free GitHub actions system by an order of magnitude.
