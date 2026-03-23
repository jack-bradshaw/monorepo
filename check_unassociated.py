import json, os, glob


def check_unassociated():
    path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"
    with open(path, "r") as f:
        topics = json.load(f)

    associated = set()
    for t in topics:
        for item in t["items"]:
            associated.add(item)

    files = glob.glob(
        "/Users/jack/workspaces/ws3/first_party/site/static/content/journal/*.md"
    )
    unassoc = []

    for f in files:
        basename = os.path.basename(f)
        if basename == "_index.md":
            continue
        name = basename[:-3]
        name_dashed = name.replace("_", "-")  # 10_words vs 10-words

        # some files have exact match like first-poem, some have first_poem in JSON?
        # Let's just check the set
        if name_dashed not in associated and name not in associated:
            unassoc.append(name)

    print("Unassociated manuscripts:")
    for m in unassoc:
        print(f" - {m}")


if __name__ == "__main__":
    check_unassociated()
