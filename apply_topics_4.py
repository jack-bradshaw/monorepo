import json
import os

TOPICS_FILE = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"

new_topics = [
    {
        "key": "chaos",
        "title": "Chaos",
        "description": "Pieces involving the unpredictable and disorganized elements of existence",
        "items": ["first-poem", "into-the-subverse"],
    },
    {
        "key": "order",
        "title": "Order",
        "description": "Pieces involving the structured and organized elements of existence",
        "items": ["first-poem", "into-the-subverse"],
    },
    {
        "key": "judgement",
        "title": "Judgement",
        "description": "Pieces involving the critical evaluation of others and their actions",
        "items": ["judgement-vs-understanding"],
    },
    {
        "key": "understanding",
        "title": "Understanding",
        "description": "Pieces involving the effort to comprehend others without immediate condemnation",
        "items": ["judgement-vs-understanding"],
    },
    {
        "key": "perfectionism",
        "title": "Perfectionism",
        "description": "Pieces involving the pursuit of flawlessness and the inner critic",
        "items": ["on-imperfection", "the-bind"],
    },
    {
        "key": "cynicism",
        "title": "Cynicism",
        "description": "Pieces involving skeptical or pessimistic views of human motives",
        "items": ["cynicism-exiled", "cynicism-refined", "pragmatic-altruism"],
    },
    {
        "key": "altruism",
        "title": "Altruism",
        "description": "Pieces involving selfless concern for the well-being of others",
        "items": ["pragmatic-altruism", "cynicism-refined"],
    },
    {
        "key": "pragmatism",
        "title": "Pragmatism",
        "description": "Pieces involving practical, realistic approaches to solving problems",
        "items": ["pragmatic-altruism", "radix-pragmatismi"],
    },
    {
        "key": "cognitive-dissonance",
        "title": "Cognitive Dissonance",
        "description": "Pieces involving the mental discomfort of holding contradictory beliefs",
        "items": ["sports-bar"],
    },
    {
        "key": "curiosity",
        "title": "Curiosity",
        "description": "Pieces involving the driving need to ask questions and learn",
        "items": ["stars", "no-adults-allowed"],
    },
    {
        "key": "sentience",
        "title": "Sentience",
        "description": "Pieces involving the capacity to feel and experience subjectivity",
        "items": ["the-artificial-man"],
    },
    {
        "key": "simulation",
        "title": "Simulation",
        "description": "Pieces involving the hypothesis of simulated reality",
        "items": ["the-artificial-man"],
    },
    {
        "key": "corporate-culture",
        "title": "Corporate Culture",
        "description": "Pieces involving the organizational dynamics and jargon of large companies",
        "items": ["cursed-cursor"],
    },
    {
        "key": "butterfly-effect",
        "title": "Butterfly Effect",
        "description": "Pieces involving how small actions lead to large, unpredictable consequences",
        "items": ["the-wolves-are-all-gone"],
    },
    {
        "key": "determinism",
        "title": "Determinism",
        "description": "Pieces involving the philosophy that all events are determined by external causes",
        "items": ["welcome-to-the-machine", "the-wolves-are-all-gone"],
    },
    {
        "key": "free-will",
        "title": "Free Will",
        "description": "Pieces involving the ability of agents to make unconstrained choices",
        "items": ["welcome-to-the-machine", "the-wolves-are-all-gone"],
    },
    {
        "key": "greed",
        "title": "Greed",
        "description": "Pieces involving the intense, selfish desire for wealth or power",
        "items": ["timeless-engineering"],
    },
    {
        "key": "engineering-sustainability",
        "title": "Engineering Sustainability",
        "description": "Pieces involving the design of long-lasting, resilient systems",
        "items": ["timeless-engineering"],
    },
    {
        "key": "generative-ai",
        "title": "Generative AI",
        "description": "Pieces involving artificial intelligence capable of producing content",
        "items": ["writing-as-metamorphosis"],
    },
]


def apply_updates():
    with open(TOPICS_FILE, "r") as f:
        data = json.load(f)

    # 1. Update veganism (add 'logs')
    for topic in data:
        if topic["key"] == "veganism":
            if "logs" not in topic["items"]:
                topic["items"].append("logs")
        elif topic["key"] == "astronomy":
            if "stars" not in topic["items"]:
                topic["items"].append("stars")

    # 2. Append new topics to the end, avoiding duplicates
    existing_keys = {topic["key"] for topic in data}
    for new_topic in new_topics:
        if new_topic["key"] not in existing_keys:
            data.append(new_topic)
        else:
            # If it somehow already exists, just merge items
            for topic in data:
                if topic["key"] == new_topic["key"]:
                    for item in new_topic["items"]:
                        if item not in topic["items"]:
                            topic["items"].append(item)

    with open(TOPICS_FILE, "w") as f:
        json.dump(data, f, indent=2)


if __name__ == "__main__":
    apply_updates()
    print("Updates to topics.json applied successfully.")
