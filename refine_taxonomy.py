import json
import os


def get_text(filename):
    base = filename.replace("-", "_")
    for f in [filename, base]:
        filepath = (
            f"/Users/jack/workspaces/ws3/first_party/site/static/content/journal/{f}.md"
        )
        try:
            with open(filepath, "r") as fl:
                return fl.read().lower()
        except Exception as e:
            pass
    return ""


def main():
    topics_path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"
    themes_path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/themes.json"

    with open(topics_path, "r") as f:
        topics = json.load(f)
    with open(themes_path, "r") as f:
        themes = json.load(f)

    # Re-apply the 10-words fix because git checkout reverted it!
    # 1. Remove 10-words from generative-ai
    for t in topics:
        if t["key"] == "generative-ai":
            if "10-words" in t["items"]:
                t["items"].remove("10-words")
    # 2. Add new topic communication-degradation
    topics.append(
        {
            "key": "communication-degradation",
            "title": "Communication Degradation",
            "description": "Pieces involving the breakdown, over-simplification, and loss of depth in human interaction and language",
            "items": ["10-words"],
        }
    )

    theme_map = {th["key"]: th for th in themes}
    new_topics = []

    for t in topics:
        key = t["key"]
        title = t["title"].lower()

        explicit_items = []
        implicit_items = []

        roots = [word for word in key.split("-") if len(word) > 3]
        if key == "ai":
            roots = ["ai", "artificial"]
        if key == "time-travel":
            roots = ["time", "travel"]

        for item in t["items"]:
            text = get_text(item)
            found = False
            title_clean = title.replace(" ", "")
            if title_clean in text.replace(" ", ""):
                found = True
            for root in roots:
                if root in text:
                    found = True
                    break

            # Generous exceptions
            if key == "generative-ai" and (
                "ai" in text or "artificial" in text or "generate" in text
            ):
                found = True
            if key == "drafting-mechanics" and (
                "draft" in text
                or "writ" in text
                or "edit" in text
                or "prose" in text
                or "author" in text
                or "manuscript" in text
            ):
                found = True
            if key == "therapy" and ("therap" in text or "psycholog" in text):
                found = True
            if key == "literature-spaces" and ("librar" in text or "book" in text):
                found = True
            if key == "testing-methodology" and ("test" in text):
                found = True
            if key == "tech-employment" and (
                "tech" in text or "work" in text or "employ" in text or "job" in text
            ):
                found = True
            if key == "corporate-culture" and (
                "corporat" in text or "compani" in text or "company" in text
            ):
                found = True
            if key == "aging" and (
                "age" in text or "aging" in text or "old" in text or "adult" in text
            ):
                found = True
            if key == "veganism" and (
                "vegan" in text
                or "meat" in text
                or "animal" in text
                or "carnis" in text
            ):
                found = True
            if key == "oceans" and (
                "ocean" in text or "sea" in text or "water" in text
            ):
                found = True
            if key == "artificial-sentience" and ("sentien" in text or "ai" in text):
                found = True
            if key == "monasticism" and ("monk" in text or "monaster" in text):
                found = True
            if key == "simulated-reality" and ("simulat" in text or "real" in text):
                found = True

            # Strict overrides to ensure themes don't sneak in to topics
            if (
                key == "cognitive-dissonance"
                and "cognitive" not in text
                and "dissonance" not in text
            ):
                found = False
            if key == "cynicism" and "cynic" not in text:
                found = False
            if key == "altruism" and "altruis" not in text:
                found = False
            if key == "pragmatism" and "pragmat" not in text:
                found = False
            if key == "isolation" and "isolat" not in text and "alone" not in text:
                found = False

            if found:
                explicit_items.append(item)
            else:
                implicit_items.append(item)

        # Update topics.json
        if explicit_items:
            t["items"] = explicit_items
            new_topics.append(t)

        # Update themes.json
        if implicit_items:
            if key in theme_map:
                for imp in implicit_items:
                    if imp not in theme_map[key]["items"]:
                        theme_map[key]["items"].append(imp)
            else:
                new_theme = {
                    "key": t["key"],
                    "title": t["title"],
                    "description": t["description"],
                    "items": implicit_items,
                }
                theme_map[key] = new_theme
                if new_theme not in themes:
                    themes.append(new_theme)

    with open(topics_path, "w") as f:
        json.dump(new_topics, f, indent=2)
    with open(themes_path, "w") as f:
        json.dump(list(theme_map.values()), f, indent=2)

    print("Successfully mapped pieces into topics and themes based on explicit text.")


if __name__ == "__main__":
    main()
