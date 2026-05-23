import re

with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    lines = f.readlines()

class_start = 0
for i, line in enumerate(lines):
    if line.startswith("abstract class KspServiceTest"):
        class_start = i
        break

header = lines[:class_start+1]
body = lines[class_start+1:-1]
footer = lines[-1:]

blocks = []
current_block = []
bracket_count = 0

for line in body:
    if line.startswith("  ") and not line.startswith("   ") and bracket_count == 0:
        if line.strip() == "":
            if current_block:
                current_block.append(line)
            continue
            
        if current_block:
            blocks.append(current_block)
        current_block = [line]
    else:
        if current_block:
            current_block.append(line)
        else:
            current_block = [line]
            
    # Need to handle strings or comments carefully? The file is well formatted.
    # We can just count braces
    bracket_count += line.count('{') - line.count('}')

if current_block:
    blocks.append(current_block)

def get_block_type(block):
    text = "".join(block).strip()
    if "@Test" in text:
        return "test"
    if "@Before" in text or "@After" in text:
        return "before_after"
    if "abstract " in text and (" fun " in text or " val " in text or " var " in text):
        return "abstract"
    if "companion object" in text:
        return "companion"
    if "private val" in text or "private var" in text or "lateinit var" in text or "lateinit val" in text:
        if "private fun" not in text and "private suspend fun" not in text:
            if text.startswith("private val") or text.startswith("lateinit") or (text.startswith("/**") and ("\nprivate val" in text or "\nlateinit" in text)):
                return "prop"
    if "private fun" in text or "private suspend fun" in text:
        return "private_fun"
    return "unknown"

props = []
before_after = []
tests = []
abstracts = []
private_funs = []
companions = []
unknowns = []

for b in blocks:
    t = get_block_type(b)
    if t == "prop": props.append(b)
    elif t == "before_after": before_after.append(b)
    elif t == "test": tests.append(b)
    elif t == "abstract": abstracts.append(b)
    elif t == "private_fun": private_funs.append(b)
    elif t == "companion": companions.append(b)
    else: unknowns.append(b)

if unknowns:
    print("Warning: unknowns found")
    for b in unknowns:
        print("".join(b[:3]))

# 1. private val / lateinit val
# 2. before/after
# 3. test
# 4. abstract
# (private helpers not explicitly specified, we'll put them before companion)
# 5. companion object

final_body = []
for b in props: final_body.extend(b)
for b in before_after: final_body.extend(b)
for b in tests: final_body.extend(b)
for b in abstracts: final_body.extend(b)
for b in private_funs: final_body.extend(b)
for b in companions: final_body.extend(b)

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.writelines(header)
    f.writelines(final_body)
    f.writelines(footer)

print(f"Reordered: {len(props)} props, {len(before_after)} b/a, {len(tests)} tests, {len(abstracts)} abstracts, {len(private_funs)} helpers, {len(companions)} companions.")
