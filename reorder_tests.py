import re

with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    lines = f.readlines()

other_content_before = []
midway_tests = []
other_content_after = []

state = 'BEFORE'
current_test_lines = []
current_test_name = None

i = 0
while i < len(lines):
    line = lines[i]
    if line.strip() == '@Test':
        # Check if the next non-empty line is a midway test
        j = i + 1
        is_midway = False
        func_name = None
        while j < len(lines):
            if 'fun midway_' in lines[j]:
                is_midway = True
                func_name = re.search(r'fun midway_(\w+)', lines[j]).group(1)
                break
            elif 'fun ' in lines[j]:
                break
            j += 1
            
        if is_midway:
            if state == 'BEFORE':
                state = 'MIDWAY'
            
            # Flush current_test_lines if we were building one
            if state == 'MIDWAY' and current_test_lines:
                midway_tests.append((current_test_name, current_test_lines))
                current_test_lines = []
                
            current_test_name = func_name
            current_test_lines.append(line)
            
        else:
            if state == 'MIDWAY':
                # We finished midway tests
                state = 'AFTER'
                if current_test_lines:
                    midway_tests.append((current_test_name, current_test_lines))
                    current_test_lines = []
            
            if state == 'BEFORE':
                other_content_before.append(line)
            else:
                other_content_after.append(line)
    elif line.strip().startswith('private ') and state == 'MIDWAY':
        # We hit private helper functions at the end of the class
        state = 'AFTER'
        if current_test_lines:
            midway_tests.append((current_test_name, current_test_lines))
            current_test_lines = []
        other_content_after.append(line)
    else:
        if state == 'BEFORE':
            other_content_before.append(line)
        elif state == 'MIDWAY':
            current_test_lines.append(line)
        elif state == 'AFTER':
            other_content_after.append(line)
            
    i += 1

if current_test_lines and state == 'MIDWAY':
    midway_tests.append((current_test_name, current_test_lines))

midway_tests.sort(key=lambda x: x[0])

for name, block in midway_tests:
    for k in range(len(block)):
        block[k] = block[k].replace(f"fun midway_{name}", f"fun {name}")

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.writelines(other_content_before)
    for name, block in midway_tests:
        f.writelines(block)
    f.writelines(other_content_after)

print("Renamed and sorted:", [t[0] for t in midway_tests])
