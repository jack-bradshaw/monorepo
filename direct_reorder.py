with open('first_party/oksp/service/KspServiceTest.kt', 'r') as f:
    lines = f.readlines()

# indices are 0-based, so line N is lines[N-1]
# Lines 1-43 -> lines[0:43]
header_and_after = lines[0:43]

# Lines 44-68 -> lines[43:68]
protected_helpers = lines[43:68]

# Lines 69-102 -> lines[68:102]
abstracts = lines[68:102]

# Lines 103-1162 -> lines[102:1162] (Wait, let's verify line 1162)
# We can just search for `@Test`
tests_start = 102
tests_end = tests_start
while tests_end < len(lines):
    if "private suspend fun KspService.advanceThroughCurrentRound()" in lines[tests_end]:
        # Back up over the KDoc
        while "/**" not in lines[tests_end-1]:
            tests_end -= 1
        tests_end -= 1 # The '/**' line
        break
    tests_end += 1

tests = lines[tests_start:tests_end]

# Make sure we got it right
print("Tests end at line:", tests_end)
print("Line at tests_end:", lines[tests_end].strip())

rest = lines[tests_end:]

# Change protected to private
for i in range(len(protected_helpers)):
    protected_helpers[i] = protected_helpers[i].replace("protected fun runKsp", "private fun runKsp")

# New order: header -> tests -> abstracts -> protected_helpers -> rest
new_lines = header_and_after + tests + abstracts + ["\n"] + protected_helpers + ["\n"] + rest

with open('first_party/oksp/service/KspServiceTest.kt', 'w') as f:
    f.writelines(new_lines)

print("done")
