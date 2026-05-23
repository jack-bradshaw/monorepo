with open('first_party/oksp/service/KspServiceTest.kt', 'r') as f:
    lines = f.readlines()

abstracts = lines[67:101]
helpers = lines[44:66]

for i in range(len(helpers)):
    helpers[i] = helpers[i].replace("protected fun runKsp", "private fun runKsp")

del lines[44:101]

insert_idx = 0
for i, line in enumerate(lines):
    if "private suspend fun KspService.advanceThroughCurrentRound()" in line:
        insert_idx = i
        while "/**" not in lines[insert_idx - 1]:
            insert_idx -= 1
        insert_idx -= 1
        break

lines = lines[:insert_idx] + abstracts + ["\n"] + helpers + ["\n"] + lines[insert_idx:]

with open('first_party/oksp/service/KspServiceTest.kt', 'w') as f:
    f.writelines(lines)

print("done")
