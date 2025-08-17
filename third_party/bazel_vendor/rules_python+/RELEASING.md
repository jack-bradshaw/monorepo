# Releasing

Start from a clean checkout at `main`.

Before running through the release it's good to run the build and the tests
locally, and make sure CI is passing. You can also test-drive the commit in an
existing Bazel workspace to sanity check functionality.

## Releasing from HEAD

These are the steps for a regularly scheduled release from HEAD.

### Steps

1. [Determine the next semantic version number](#determining-semantic-version).
1. Update CHANGELOG.md: replace the `v0-0-0` and `0.0.0` with `X.Y.0`.
   ```
   awk -v version=X.Y.0 'BEGIN { hv=version; gsub(/\./, "-", hv) } /END_UNRELEASED_TEMPLATE/ { found_marker = 1 } found_marker { gsub(/v0-0-0/, hv, $0); gsub(/Unreleased/, "[" version "] - " strftime("%Y-%m-%d"), $0); gsub(/0.0.0/, version, $0); } { print } ' CHANGELOG.md > /tmp/changelog && cp /tmp/changelog CHANGELOG.md
   ```
1. Replace `VERSION_NEXT_*` strings with `X.Y.0`.
   ```
   grep -l --exclude=CONTRIBUTING.md --exclude=RELEASING.md --exclude-dir=.* VERSION_NEXT_ -r \
     | xargs sed -i -e 's/VERSION_NEXT_FEATURE/X.Y.0/' -e 's/VERSION_NEXT_PATCH/X.Y.0/'
   ```
1. Send these changes for review and get them merged.
1. Create a branch for the new release, named `release/X.Y`
   ```
   git branch --no-track release/X.Y upstream/main && git push upstream release/X.Y
   ```

The next step is to create tags to trigger release workflow, **however**
we start by using release candidate tags (`X.Y.Z-rcN`) before tagging the
final release (`X.Y.Z`).

1. Create release candidate tag and push. Increment `N` for each rc.
   ```
   git tag X.Y.0-rcN upstream/release/X.Y && git push upstream --tags
   ```
2. Announce the RC release: see [Announcing Releases]
3. Wait a week for feedback.
   * Follow [Patch release with cherry picks] to pull bug fixes into the
     release branch.
   * Repeat the RC tagging step, incrementing `N`.
4. Finally, tag the final release tag:
   ```
   git tag X.Y.0 upstream/release/X.Y && git push upstream --tags
   ```

Release automation will create a GitHub release and BCR pull request.

### Determining Semantic Version

**rules_python** uses [semantic version](https://semver.org), so releases with
API changes and new features bump the minor, and those with only bug fixes and
other minor changes bump the patch digit.

To find if there were any features added or incompatible changes made, review
[CHANGELOG.md](CHANGELOG.md) and the commit history. This can be done using
github by going to the url:
`https://github.com/bazel-contrib/rules_python/compare/<VERSION>...main`.

## Patch release with cherry picks

If a patch release from head would contain changes that aren't appropriate for
a patch release, then the patch release needs to be based on the original
release tag and the patch changes cherry-picked into it.

In this example, release `0.37.0` is being patched to create release `0.37.1`.
The fix being included is commit `deadbeef`.

1. `git checkout release/0.37`
1. `git cherry-pick -x deadbeef`
1. Fix merge conflicts, if any.
1. `git cherry-pick --continue` (if applicable)
1. `git push upstream`

If multiple commits need to be applied, repeat the `git cherry-pick` step for
each.

Once the release branch is in the desired state, use `git tag` to tag it, as
done with a release from head. Release automation will do the rest.

### Announcing releases

We announce releases in the #python channel in the Bazel slack
(bazelbuild.slack.com). Here's a template:

```
Greetings Pythonistas,

rules_python X.Y.Z-rcN is now available
Changelog: https://rules-python.readthedocs.io/en/X.Y.Z-rcN/changelog.html#vX-Y-Z

It will be promoted to stable next week, pending feedback.
```

It's traditional to include notable changes from the changelog, but not
required.

### Re-releasing a version

Re-releasing a version (i.e. changing the commit a tag points to)  is
*sometimes* possible, but it depends on how far into the release process it got.

The two points of no return are:
 * If the PyPI package has been published: PyPI disallows using the same
   filename/version twice. Once published, it cannot be replaced.
 * If the BCR package has been published: Once it's been committed to the BCR
   registry, it cannot be replaced.

If release steps fail _prior_ to those steps, then its OK to change the tag. You
may need to manually delete the GitHub release.

## Secrets

### PyPI user rules-python

Part of the release process uploads packages to PyPI as the user `rules-python`.
This account is managed by Google; contact rules-python-pyi@google.com if
something needs to be done with the PyPI account.
