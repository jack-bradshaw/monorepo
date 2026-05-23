import re

def parse_blocks(filepath):
    with open(filepath, 'r') as f:
        lines = f.readlines()

    start_idx = 0
    for i, line in enumerate(lines):
        if line.startswith("abstract class KspServiceTest"):
            start_idx = i
            break

    header = lines[:start_idx + 1]
    body = lines[start_idx + 1:]

    blocks = []
    current_block = []
    depth = 1
    
    current_kdoc = []
    
    i = 0
    while i < len(body):
        line = body[i]
        
        # approximate braces
        stripped = line.strip()
        
        # Don't count braces in comments or string literals
        s = line
        if "//" in s:
            s = s[:s.index("//")]
        s = re.sub(r'".*?(?<!\\)"', '""', s)
        s = s.replace("'{'", "''").replace("'}'", "''")
        
        d_change = s.count('{') - s.count('}')
        
        if depth == 1:
            if stripped == "}":
                if current_kdoc:
                    current_block.extend(current_kdoc)
                    current_kdoc = []
                if current_block:
                    blocks.append(current_block)
                blocks.append([line])
                break
                
            if stripped.startswith("/**"):
                current_kdoc.append(line)
                if "*/" in stripped:
                    pass
                else:
                    i += 1
                    while i < len(body):
                        kdoc_line = body[i]
                        current_kdoc.append(kdoc_line)
                        if "*/" in kdoc_line:
                            break
                        i += 1
                i += 1
                continue
                
            if stripped == "":
                if current_block:
                    current_block.append(line)
                i += 1
                continue
                
            current_block = current_kdoc + [line]
            current_kdoc = []
            depth += d_change
            if depth == 1:
                blocks.append(current_block)
                current_block = []
        else:
            current_block.append(line)
            depth += d_change
            if depth == 1:
                blocks.append(current_block)
                current_block = []
                
        i += 1
        
    return header, blocks

header, blocks = parse_blocks('first_party/oksp/service/KspServiceTest.kt')

properties = []
lifecycles = []
tests = []
abstracts = []
helpers = []
companion = []
footer = []

for block in blocks:
    text = "".join(block)
    if text.strip() == "}":
        footer = block
        continue
        
    if "private val latches" in text:
        properties = block
    elif "@After" in text:
        lifecycles = block
    elif "@Test" in text:
        tests.append(block)
    elif "abstract " in text:
        abstracts.append(block)
    elif "companion object" in text:
        companion = block
    elif "protected fun runKsp" in text:
        # Change to private
        modified_block = []
        for line in block:
            modified_block.append(line.replace("protected fun runKsp", "private fun runKsp"))
        helpers.append(modified_block)
    else:
        helpers.append(block)

# Remove any empty lines at the end of each block and add exactly one empty line
def clean_block(b):
    while b and b[-1].strip() == "":
        b.pop()
    b.append("\n")
    return b

with open('first_party/oksp/service/KspServiceTest.kt', 'w') as f:
    for h in header:
        f.write(h)
    
    if properties:
        for b in clean_block(properties): f.write(b)
        f.write("\n")
        
    if lifecycles:
        for b in clean_block(lifecycles): f.write(b)
        f.write("\n")
        
    for test in tests:
        for b in clean_block(test): f.write(b)
        f.write("\n")
        
    for ab in abstracts:
        for b in clean_block(ab): f.write(b)
        f.write("\n")
        
    for h in helpers:
        for b in clean_block(h): f.write(b)
        f.write("\n")
        
    if companion:
        for b in clean_block(companion): f.write(b)
        
    for b in footer:
        f.write(b)

print("Props:", 1 if properties else 0)
print("Life:", 1 if lifecycles else 0)
print("Tests:", len(tests))
print("Abstract:", len(abstracts))
print("Helpers:", len(helpers))
print("Companion:", 1 if companion else 0)

