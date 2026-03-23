import json


def main():
    topics_path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"
    with open(topics_path, "r") as f:
        topics = json.load(f)

    # Helper to add item to topic
    def add_to_topic(topic_key, topic_title, topic_desc, items):
        # find topic
        t = next((tx for tx in topics if tx["key"] == topic_key), None)
        if t:
            for i in items:
                if i not in t["items"]:
                    t["items"].append(i)
        else:
            topics.append(
                {
                    "key": topic_key,
                    "title": topic_title,
                    "description": topic_desc,
                    "items": items,
                }
            )

    # 1. drafting-mechanics (failed my crude text parser but explicitly discuss writing elements)
    add_to_topic(
        "drafting-mechanics",
        "Drafting Mechanics",
        "Pieces involving the translation of thought into prose",
        [
            "a-writers-dilemma",
            "death-of-a-critic",
            "on-imperfection",
            "the-bond",
            "cynicism-refined",
            "challenge-of-monologue",
        ],
    )

    # 2. nature
    add_to_topic(
        "nature",
        "Nature",
        "Pieces explicitly navigating the natural world, weather, and landscapes",
        ["first-poem"],
    )

    # 3. space-and-physics
    add_to_topic(
        "physics",
        "Physics",
        "Pieces explicitly involving physical mechanics, space, and the cosmos",
        ["into-the-subverse", "stars"],
    )

    # 4. sociology
    add_to_topic(
        "sociology",
        "Sociology",
        "Pieces exploring organized society, community behavior, and adulthood",
        ["judgement-vs-understanding"],
    )

    # 5. philosophy
    add_to_topic(
        "philosophy",
        "Philosophy",
        "Pieces explicitly outlining and exploring philosophical frameworks",
        [
            "hermeticity",
            "pragmatic-altruism",
            "welcome-to-the-machine",
            "cynicism-exiled",
        ],
    )

    # 6. tech-employment / programming
    add_to_topic(
        "tech-employment",
        "Tech Employment",
        "Pieces involving tech industry jobs, salaries, and technical career trajectories",
        ["the-cursed-cursor", "timeless-engineering"],
    )
    add_to_topic(
        "programming",
        "Programming",
        "Pieces involving the maintenance and creation of source code",
        [
            "the-cursed-cursor",
            "radix-pragmatismi-est-taedium-ideologiae-inflexibilis-et-dogmatis-rigidi",
        ],
    )

    # 7. mindfulness
    add_to_topic(
        "meditation",
        "Meditation",
        "Pieces involving focused mental practices and inner tranquility",
        ["humanity-loop", "the-tao-of-the-pen"],
    )

    # 8. solitary-confinement
    add_to_topic(
        "imprisonment",
        "Imprisonment",
        "Pieces involving literal solitary confinement and physical imprisonment",
        ["on-the-red-space"],
    )

    # 9. literature-spaces
    add_to_topic(
        "literature-spaces",
        "Literature Spaces",
        "Pieces involving reading spaces, book repositories, and other spaces dedicated to liteature",
        ["the-treehouse-library"],
    )

    with open(topics_path, "w") as f:
        json.dump(topics, f, indent=2)


if __name__ == "__main__":
    main()
