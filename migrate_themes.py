import json
import os


def main():
    topics_path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"
    themes_path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/themes.json"

    with open(topics_path, "r") as f:
        topics = json.load(f)
    with open(themes_path, "r") as f:
        themes = json.load(f)

    keys_to_migrate = [
        "chaos",
        "order",
        "perfectionism",
        "cynicism",
        "altruism",
        "pragmatism",
        "cognitive-dissonance",
        "curiosity",
        "butterfly-effect",
        "determinism",
        "free-will",
        "greed",
        "isolation",
        "aging",
        "violence",
        "zealotry",
        "judgement",
        "understanding",
        "engineering-sustainability",
        "death",
    ]

    new_topics = []
    migrated_count = 0

    for t in topics:
        if t["key"] in keys_to_migrate:
            # Check if it already exists in themes
            existing_theme = next((th for th in themes if th["key"] == t["key"]), None)
            if existing_theme:
                # Merge items
                for item in t["items"]:
                    if item not in existing_theme["items"]:
                        existing_theme["items"].append(item)
            else:
                themes.append(t)
            migrated_count += 1
        else:
            new_topics.append(t)

    with open(topics_path, "w") as f:
        json.dump(new_topics, f, indent=2)
    with open(themes_path, "w") as f:
        json.dump(themes, f, indent=2)

    print(f"Migrated {migrated_count} thematic items from topics.json to themes.json")

    # Check for naked pieces
    all_pieces_in_topics = set()
    for t in new_topics:
        for item in t["items"]:
            all_pieces_in_topics.add(item)

    # List of all known pieces
    # We can get this from themes.json or what we migrated
    all_known_pieces = set()
    for th in themes:
        for item in th["items"]:
            all_known_pieces.add(item)

    naked_pieces = []
    for piece in all_known_pieces:
        if piece not in all_pieces_in_topics:
            naked_pieces.append(piece)

    print("Naked pieces (0 valid explicit topics left):", naked_pieces)


if __name__ == "__main__":
    main()
