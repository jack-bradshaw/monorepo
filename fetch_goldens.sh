#!/bin/bash
set -e

SSH_CMD="ssh -i /Users/jack/gdrive/3_System/shell/ssh/keys/cloudtop_key jackjbradshaw_gmail_com@136.109.87.2 -o StrictHostKeyChecking=no"

echo ">>> Extracting JPGs natively on cloudtop..."
$SSH_CMD "cd /tmp/monorepo_test && find bazel-testlogs -name '*.jpg' | tar -czf goldens.tar.gz -T -" || true

echo ">>> Fetching Playwright output JPGs from cloudtop..."
rm -rf /tmp/cloudtop_testlogs
mkdir -p /tmp/cloudtop_testlogs
scp -i /Users/jack/gdrive/3_System/shell/ssh/keys/cloudtop_key -o StrictHostKeyChecking=no "jackjbradshaw_gmail_com@136.109.87.2:/tmp/monorepo_test/goldens.tar.gz" /tmp/cloudtop_testlogs/

echo ">>> Distributing Linux-native goldens to local source tree..."
cd /tmp/cloudtop_testlogs
tar -xzf goldens.tar.gz
find bazel-testlogs -name "*.jpg" | while read -r new_golden; do
  filename=$(basename "$new_golden")
  target=$(find /Users/jack/workspaces/ws3/first_party/site/tests -name "$filename" | head -n 1)
  if [[ -n "$target" ]]; then
    cp "$new_golden" "$target"
    echo "Updated $target"
  else
    echo "Warning: No existing golden found locally for $filename"
  fi
done

cd /Users/jack/workspaces/ws3
echo ">>> Amending local Git commit with the updated images..."
git add first_party/site/tests
git commit --amend --no-edit
echo ">>> Complete!"
