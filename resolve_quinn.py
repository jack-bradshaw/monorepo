import os
import re


def resolve_file(filepath):
    if not os.path.exists(filepath):
        return
    with open(filepath, "r") as f:
        content = f.read()

    # If it's a conflict file, we want the incoming version (b42e5aee), which is the bottom part
    # format:
    # <<<<<<< HEAD
    # ...
    # =======
    # ...
    # >>>>>>> b42e5aee...

    # regex to replace conflict blocks with the bottom part
    pattern = re.compile(r"<<<<<<< HEAD.*?=======(.*?)>>>>>>> [^\n]+", re.DOTALL)

    new_content = pattern.sub(r"\1", content)

    # replace package names
    new_content = new_content.replace(
        "package com.jackbradshaw.quinn\n",
        "package com.jackbradshaw.concurrency.quinn\n",
    )
    new_content = new_content.replace(
        "package com.jackbradshaw.quinn.", "package com.jackbradshaw.concurrency.quinn."
    )

    # replace imports
    new_content = new_content.replace(
        "import com.jackbradshaw.quinn.", "import com.jackbradshaw.concurrency.quinn."
    )

    with open(filepath, "w") as f:
        f.write(new_content)


for root, _, files in os.walk("first_party/concurrency/quinn"):
    for f in files:
        if f.endswith(".kt") or f == "BUILD":
            resolve_file(os.path.join(root, f))
