# Presubmit

A series of formatting and correctness checks. Run with:

```
source presubmit.sh
run_presubmit
```

All checks must pass before code can be merged into main, unless presubmit itself is broken.

## Checks

The following checks are enabled:

- All Bazel files must be formatted according to Buildifier.
- All Kotlin files in java/ and javatest/ msut be formatted according to [ktfmt](https://github.com/facebook/ktfmt).
- All Bazel targets must build.
- All Bazel test targets must pass.

## Adding New Checks

To add a new check:

1. Write a bash script which performs the check logic.
2. Add the bash script to this directory.
3. Reference the new bash script in [presubmit.sh](presubmit.sh).

That's it.

## GitHub Integration

GitHub integration is handled git the [.github](.github) directory. All checks check will run
automatically on-push and on-PR.