import os
import re


def resolve_file(filepath):
    if not os.path.exists(filepath):
        return
    with open(filepath, "r") as f:
        content = f.read()

    # We want to KEEP the HEAD version for these, because HEAD has the new concurrency/quinn imports
    # Format:
    # <<<<<<< HEAD
    # HEAD_CONTENT
    # =======
    # B42_CONTENT
    # >>>>>>> b42e5aee...

    # regex to replace conflict blocks with HEAD part
    pattern = re.compile(r"<<<<<<< HEAD\n(.*?)=======\n.*?>>>>>>> [^\n]+\n", re.DOTALL)

    new_content = pattern.sub(r"\1", content)

    with open(filepath, "w") as f:
        f.write(new_content)


for root, _, files in os.walk("first_party/kale/resolver/chassis"):
    for f in files:
        if f.endswith(".kt") or f == "BUILD":
            resolve_file(os.path.join(root, f))
