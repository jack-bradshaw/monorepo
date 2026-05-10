import os
import re

def resolve_file(filepath):
    if not os.path.exists(filepath): return
    with open(filepath, 'r') as f:
        content = f.read()

    # The incoming changes are in the BOTTOM part of the conflict block
    # <<<<<<< HEAD
    # HEAD_CONTENT
    # =======
    # INCOMING_CONTENT
    # >>>>>>> e720e748 (Commit message)
    
    pattern = re.compile(r'<<<<<<< HEAD.*?=======\n(.*?)>>>>>>> [a-f0-9]+.*?\n', re.DOTALL)
    
    if pattern.search(content):
        new_content = pattern.sub(r'\1', content)
        
        # fix package names
        new_content = new_content.replace('package com.jackbradshaw.quinn', 'package com.jackbradshaw.concurrency.quinn')
        new_content = new_content.replace('import com.jackbradshaw.quinn.', 'import com.jackbradshaw.concurrency.quinn.')
        
        with open(filepath, 'w') as f:
            f.write(new_content)
        print(f"Resolved {filepath}")

for root, _, files in os.walk('first_party/concurrency/quinn'):
    for f in files:
        if f.endswith('.kt') or f == 'BUILD' or f == 'README.md':
            resolve_file(os.path.join(root, f))
