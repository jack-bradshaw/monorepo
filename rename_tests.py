import re

with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    lines = f.readlines()

def to_lcc(snake_str):
    components = snake_str.split('_')
    if len(components) == 1:
        return components[0]
    return components[0].lower() + ''.join(x.title() for x in components[1:])

new_lines = []
renamed_tests = []

for line in lines:
    match = re.search(r'fun (e2e__|partial__)(.*)\(\)', line)
    if match:
        prefix = match.group(1)
        rest = match.group(2)
        old_name = match.group(0)[4:-2] # remove 'fun ' and '()'
        
        new_name = old_name
        
        if new_name.startswith("partial__functionBoundaryValidation__beforeProcessing__"):
            func = new_name.split("__")[-1]
            new_name = f"midway_{func}_failsBeforeProcessingBegins"
        elif new_name.startswith("partial__functionBoundaryValidation__afterFinalRound__"):
            func = new_name.split("__")[-1]
            new_name = f"midway_{func}_failsAfterFinalRoundCompletes"
        elif new_name.startswith("partial__functionBoundaryValidation__afterStart__"):
            func = new_name.split("__")[-1]
            new_name = f"midway_{func}_failsAfterStart"
        else:
            if new_name.startswith("partial__"):
                new_name = new_name.replace("partial__", "midway_")
            elif new_name.startswith("e2e__"):
                new_name = new_name.replace("e2e__", "e2e_")
            
            # Now we have midway_rest or e2e_rest where rest is separated by __
            # We want to replace __ with _ and apply to_lcc to the parts separated by __
            # Actually, the prefix (e2e, midway) is already handled and followed by _.
            parts = new_name.split("_", 1) # [prefix, rest]
            rest_parts = parts[1].split("__")
            
            lcc_parts = [to_lcc(p) for p in rest_parts]
            new_name = parts[0] + "_" + "_".join(lcc_parts)
            
        line = line.replace(old_name, new_name)
        if new_name.startswith("e2e_"):
            renamed_tests.append(new_name)
            
    new_lines.append(line)

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.writelines(new_lines)

print("Renamed e2e tests:")
for t in renamed_tests:
    print(t)
