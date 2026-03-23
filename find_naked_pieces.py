import json
import glob
import os


def main():
    topics_path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"

    with open(topics_path, "r") as f:
        topics = json.load(f)

    all_pieces_in_topics = set()
    for t in topics:
        for item in t["items"]:
            all_pieces_in_topics.add(item)

    files = glob.glob(
        "/Users/jack/workspaces/ws3/first_party/site/static/content/journal/*.md"
    )
    all_known_pieces = []

    for f in files:
        basename = os.path.basename(f)
        if basename == "_index.md":
            continue
        name = basename[:-3].replace("_", "-")
        all_known_pieces.append(name)

    naked_pieces = []
    for piece in all_known_pieces:
        # Also check exact basename since some pieces aren't renamed with dashes in the registry?
        # Let's check both
        exact = piece.replace("-", "_")
        if piece not in all_pieces_in_topics and exact not in all_pieces_in_topics:
            if exact == "10_words" and "10-words" in all_pieces_in_topics:
                continue
            if (
                exact == "treehouse_library"
                and "treehouse-library" in all_pieces_in_topics
            ):
                continue
            if piece.startswith("radix-"):
                if "radix-pragmatismi" in all_pieces_in_topics:
                    continue

            # Simple check
            found = False
            for tpiece in all_pieces_in_topics:
                if tpiece in piece or piece in tpiece:
                    found = True
                    break
            if not found:
                naked_pieces.append(piece)

    print("Naked pieces:", naked_pieces)


if __name__ == "__main__":
    main()
