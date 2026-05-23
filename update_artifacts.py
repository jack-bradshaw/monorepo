import re

with open("/Users/jack/.gemini/antigravity-ide/brain/45c2e604-14e3-41bd-8d06-509f3c540245/task.md", "r") as f:
    content = f.read()

content = content.replace("- `[/]` Fix test suite build issues resulting from refactoring KspServiceTest", "- `[x]` Fix test suite build issues resulting from refactoring KspServiceTest")
content = content.replace("- `[ ]` Sync backstab branch to origin", "- `[x]` Sync backstab branch to origin")

with open("/Users/jack/.gemini/antigravity-ide/brain/45c2e604-14e3-41bd-8d06-509f3c540245/task.md", "w") as f:
    f.write(content)

with open("/Users/jack/.gemini/antigravity-ide/brain/45c2e604-14e3-41bd-8d06-509f3c540245/walkthrough.md", "a") as f:
    f.write("\n\n## Refactoring and Sync Verification\n")
    f.write("- **Compilation Fixes:** Resolved build breakages across `first_party/sealant` and `first_party/concurrency` caused by missing dependency edges (e.g. `StandardObservableClosableComponent`).\n")
    f.write("- **Asynchronous Instantiation Fixes:** Corrected the instantiation of suspendable closables within the test suite (e.g., `QuinnImplObservableClosableTest`).\n")
    f.write("- **Branch Syncing:** Committed all final test updates and successfully pushed the `backstab` branch to `origin/backstab`.\n")

print("done")
