import json


def main():
    path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"
    with open(path, "r") as f:
        topics = json.load(f)

    additions = {
        "pragmatism": ["cynicism-refined"],
        "perfectionism": ["death-of-a-critic"],
        "isolation": ["on-the-red-space"],
        "simulated-reality": ["self-aware-antagonist"],
        "drafting-mechanics": ["the-bind", "the-bond"],
    }

    for t in topics:
        key = t["key"]
        if key in additions:
            for item in additions[key]:
                if item not in t["items"]:
                    t["items"].append(item)

    with open(path, "w") as f:
        json.dump(topics, f, indent=2)


if __name__ == "__main__":
    main()
