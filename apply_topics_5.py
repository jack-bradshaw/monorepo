import json
import os

TOPICS_FILE = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"


def apply_remediations():
    with open(TOPICS_FILE, "r") as f:
        data = json.load(f)

    # 1. Remediations to existing topics
    # We will remove artificial-intelligence, corpspeak, system-architecture
    # We will modify sentience to artificial-sentience

    new_data = []

    for topic in data:
        if topic["key"] in [
            "artificial-intelligence",
            "corpspeak",
            "system-architecture",
        ]:
            continue  # Delete these

        if topic["key"] == "sentience":
            topic["key"] = "artificial-sentience"
            topic["title"] = "Artificial Sentience"
            topic["description"] = (
                "Pieces involving synthetic or machine programs achieving conscious subjectivity"
            )
            new_data.append(topic)
            continue

        if topic["key"] == "generative-ai":
            if "10-words" not in topic["items"]:
                topic["items"].append("10-words")
            new_data.append(topic)
            continue

        new_data.append(topic)

    # Add artificial-superintelligence
    asi = {
        "key": "artificial-superintelligence",
        "title": "Artificial Superintelligence",
        "description": "Pieces involving vastly superior, omniscient machine intellects",
        "items": ["the-wolves-are-all-gone"],
    }

    # Check if ASI exists (idempotency)
    asi_exists = any(t["key"] == "artificial-superintelligence" for t in new_data)
    if not asi_exists:
        new_data.append(asi)

    with open(TOPICS_FILE, "w") as f:
        json.dump(new_data, f, indent=2)


if __name__ == "__main__":
    apply_remediations()
    print("Flattening and remediation completed.")
