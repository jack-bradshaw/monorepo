import re

with open('first_party/oksp/service/KspServiceTest.kt', 'r') as f:
    lines = f.readlines()

header = []
properties = []
lifecycles = []
tests = []
abstracts = []
helpers = []
companion = []
footer = []

# Find where class starts
class_start_idx = 0
for i, line in enumerate(lines):
    header.append(line)
    if line.startswith("abstract class KspServiceTest"):
        class_start_idx = i
        break

# The rest of the file
blocks = []
current_block = []

def save_block(block):
    if not block: return
    text = "".join(block)
    if "private val latches" in text:
        properties.append(text)
    elif "@After" in text:
        lifecycles.append(text)
    elif "@Test" in text:
        tests.append(text)
    elif "abstract " in text:
        abstracts.append(text)
    elif "companion object" in text:
        companion.append(text)
    elif "fun runKsp" in text or "fun KspService." in text or "private fun" in text or "private suspend fun" in text:
        text = text.replace("protected fun runKsp", "private fun runKsp")
        helpers.append(text)
    elif text.strip() == "}":
        footer.append(text)
    else:
        print("UNKNOWN BLOCK:", text)

in_kdoc = False
for i in range(class_start_idx + 1, len(lines)):
    line = lines[i]
    if line.strip() == "}":
        if i == len(lines) - 1 or lines[i+1].strip() == "":
            save_block(current_block)
            current_block = [line]
            save_block(current_block)
            continue
            
    is_block_start = False
    
    if line.startswith("  /**"):
        is_block_start = True
    elif not in_kdoc and line.startswith("  @"):
        is_block_start = True
    elif not in_kdoc and line.startswith("  private ") or line.startswith("  protected ") or line.startswith("  abstract "):
        is_block_start = True
        
    if "/**" in line:
        in_kdoc = True
    if "*/" in line:
        in_kdoc = False

    # Check if this is truly a block start or inside an expression
    if is_block_start and (not current_block or current_block[-1].strip() == "" or current_block[-1].strip() == "}" or "*/" in current_block[-1]):
        # It's a new block
        save_block(current_block)
        current_block = [line]
    else:
        current_block.append(line)

save_block(current_block)

print(f"Props: {len(properties)}, Lifecycle: {len(lifecycles)}, Tests: {len(tests)}, Abstract: {len(abstracts)}, Helpers: {len(helpers)}, Companion: {len(companion)}")

with open('first_party/oksp/service/KspServiceTest.kt', 'w') as f:
    f.writelines(header)
    for b in properties: f.write(b)
    for b in lifecycles: f.write(b)
    for b in tests: f.write(b)
    for b in abstracts: f.write(b)
    for b in helpers: f.write(b)
    for b in companion: f.write(b)
    for b in footer: f.write(b)

print("done")
