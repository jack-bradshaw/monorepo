#!/bin/bash
set -e

SSH_CMD="ssh -i /Users/jack/gdrive/3_System/shell/ssh/keys/cloudtop_key jackjbradshaw_gmail_com@136.109.87.2 -o StrictHostKeyChecking=no"
SCP_CMD="scp -i /Users/jack/gdrive/3_System/shell/ssh/keys/cloudtop_key -o StrictHostKeyChecking=no"

echo ">>> Cloning and running Playwright tests on cloudtop using checked-in Bazelisk bin..."
$SSH_CMD "rm -rf /tmp/monorepo_test && git clone --depth 1 -b journal_update https://github.com/jack-bradshaw/monorepo.git /tmp/monorepo_test && cd /tmp/monorepo_test && ./third_party/bazelisk/bin/bazelisk-linux-\$(uname -m | sed s/aarch64/arm64/) test //first_party/site/tests/... --test_output=errors || true"

echo ">>> Fetching Playwright output JPGs from cloudtop..."
rm -rf /tmp/cloudtop_testlogs
mkdir -p /tmp/cloudtop_testlogs
$SCP_CMD -r "jackjbradshaw_gmail_com@136.109.87.2:/tmp/monorepo_test/bazel-testlogs/first_party/site/tests" /tmp/cloudtop_testlogs/ 2>/dev/null || true

echo ">>> Distributing Linux-native goldens to local source tree..."
find /tmp/cloudtop_testlogs -name "*.jpg" | while read -r new_golden; do
  filename=$(basename "$new_golden")
  target=$(find first_party/site/tests -name "$filename" | head -n 1)
  if [[ -n "$target" ]]; then
    cp "$new_golden" "$target"
    echo "Updated $target"
  else
    echo "Warning: No existing golden found locally for $filename"
  fi
done

echo ">>> Amending local Git commit with the updated images..."
git add first_party/site/tests
git commit --amend --no-edit

echo ">>> Done!"
