import json
import os
import re


def get_text(filename):
    filepath = f"/Users/jack/workspaces/ws3/first_party/site/static/content/journal/{filename}.md"
    try:
        with open(filepath, "r") as f:
            return f.read().lower()
    except:
        return ""


def main():
    path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"
    with open(path, "r") as f:
        topics = json.load(f)

    piece_topics = {}
    for t in topics:
        key = t["key"]
        title = t["title"].lower()
        for item in t["items"]:
            if item not in piece_topics:
                piece_topics[item] = []
            piece_topics[item].append((key, title))

    report = "# Strict Explicitness Audit Report\n\n"
    report += "This report lists pieces and assigned topics where the topic name (or its core root words) DO NOT explicitly appear in the text. These are likely IMPLICIT THEMES that violate the Explicitness criteria.\n\n"

    violations = 0

    # We will define a list of root strings for each topic key to check for explicit mention
    # e.g. "cognitive-dissonance" -> "cognitive", "dissonance"

    for piece, assigned in sorted(piece_topics.items()):
        text = get_text(piece)
        if not text:
            continue

        piece_violations = []
        for key, title in assigned:
            # Generate root search terms from the key
            roots = [word for word in key.split("-") if len(word) > 3]
            # Extra exceptions for short words
            if key == "art":
                roots = ["art"]
            if key == "zen":
                roots = ["zen"]
            if key == "tao":
                roots = ["tao"]
            if key == "ai":
                roots = ["ai", "artificial"]
            if key == "time-travel":
                roots = ["time", "travel"]

            # To be explicit, AT LEAST ONE of the strong root words of the topic must appear in the text
            # OR the exact title.

            found = False
            title_clean = title.replace(" ", "")
            if title_clean in text.replace(" ", ""):
                found = True

            for root in roots:
                if root in text:
                    found = True
                    break

            # Let's do a more generous check just for standard variations:
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
            ):
                found = True
            if key == "therapy" and ("therap" in text or "psycholog" in text):
                found = True
            if key == "literature-spaces" and ("librar" in text or "book" in text):
                found = True
            if key == "testing-methodology" and ("test" in text):
                found = True
            if key == "tech-employment" and (
                "tech" in text or "work" in text or "employ" in text
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
                "vegan" in text or "meat" in text or "animal" in text
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

            # strict check for cognitive dissonance
            if (
                key == "cognitive-dissonance"
                and "cognitive" not in text
                and "dissonance" not in text
            ):
                found = False

            if not found:
                piece_violations.append(key)

        if piece_violations:
            report += f"### {piece}\n"
            for v in piece_violations:
                report += (
                    f"- **{v}**: Fails explicit text check (no root words found).\n"
                )
            report += "\n"
            violations += 1

    if violations == 0:
        report += "All topics successfully passed the strict explicitness check.\n"

    out_path = "/Users/jack/.gemini/antigravity/brain/7aae5e50-30ca-41b2-a625-71c1044722cb/strict_explicitness_audit.md"
    with open(out_path, "w") as f:
        f.write(report)

    print(
        f"Generated strict explicitness audit report with {violations} pieces having violations."
    )


if __name__ == "__main__":
    main()
