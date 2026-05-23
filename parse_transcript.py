import json

with open('/Users/jack/.gemini/antigravity-ide/brain/45c2e604-14e3-41bd-8d06-509f3c540245/.system_generated/logs/transcript.jsonl', 'r') as f:
    lines = f.readlines()

for i, line in enumerate(lines):
    if '"content":"<USER_REQUEST>\\npoints 2 and 3 approved' in line:
        # found the user input, now print the previous PLANNER_RESPONSE
        for j in range(i-1, -1, -1):
            try:
                data = json.loads(lines[j])
                if data.get('type') == 'PLANNER_RESPONSE' and data.get('content'):
                    print(data['content'])
                    break
            except:
                pass
        break
