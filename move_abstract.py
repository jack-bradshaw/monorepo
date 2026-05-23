with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    lines = f.readlines()

# The abstract block is lines 45-80 (0-indexed: 44 to 79).
# Let's dynamically find it just to be safe.
start_idx = -1
end_idx = -1
for i, line in enumerate(lines):
    if line.strip() == "/**" and "Prepares [subject]." in "".join(lines[i:i+3]):
        start_idx = i
        break

for i in range(start_idx, len(lines)):
    if "abstract suspend fun finishExtraneousProcessing()" in lines[i]:
        end_idx = i
        break

abstract_block = lines[start_idx:end_idx+1]
# also remove the trailing blank line after abstract block if it exists
if lines[end_idx+1].strip() == "":
    end_idx += 1

# delete it from original place
del lines[start_idx:end_idx+1]

# find companion object
companion_idx = -1
for i, line in enumerate(lines):
    if "private companion object {" in line:
        companion_idx = i
        break

# insert abstract_block before companion
lines.insert(companion_idx, "\n")
for i, line in enumerate(abstract_block):
    lines.insert(companion_idx + 1 + i, line)

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.writelines(lines)
print("Moved abstract block to before companion object")
