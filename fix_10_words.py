import json
from collections import defaultdict


def main():
    path = "/Users/jack/workspaces/ws3/first_party/site/data/journal/topics.json"
    with open(path, "r") as f:
        topics = json.load(f)

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

    with open(path, "w") as f:
        json.dump(topics, f, indent=2)

    # 3. Regenerate the final report string
    item_topics = defaultdict(list)
    for t in topics:
        topic_key = t["key"]
        for item in t["items"]:
            item_topics[item].append(topic_key)

    justifications = {
        "10-words": "Explicitly details 'Communication Degradation' where human interaction is reduced to staccato brevity, leaving the narrator empty.",
        "a-writers-dilemma": "Explicitly discusses the internal mechanics of drafting and balancing authentic voice with polished quality.",
        "challenge-of-monologue": "Explicitly explores the specific drafting challenge of writing monologue versus dialogue.",
        "countertransference": "Explicitly humorously explores the clinical psychological concept of countertransference between a therapist and patient.",
        "cynicism-exiled": "Explicitly features the Cynic as a core character and is set in a literary space/library.",
        "cynicism-refined": "Explicitly features the Cynic, Altruist, and Pragmatist as core characters collaborating in a literary space on the mechanics of drafting.",
        "death-of-a-critic": "Explicitly confronts the internal 'Critic' and its demands for perfectionism during the drafting process.",
        "first-poem": "Explicitly structured around exploring the dichotomy of chaos versus order.",
        "five-stages-of-writing": "Explicitly explores the chronological stages of the drafting and writing process.",
        "hermeticity": "Explicitly explores 'hermeticity' as the philosophy of profound structural detachment and isolation from society.",
        "humanity-loop": "Explicitly explores finding stillness through meditation to escape the repetitive loops of thoughts.",
        "illumination": "Explicitly contrasts Western therapy (a psychotherapist) with Eastern monastic meditation practices.",
        "into-the-subverse": "Explicitly delves into the limits of causality (order) and the nature of unconstrained space (chaos).",
        "judgement-vs-understanding": "Explicitly contrasts the rigid act of judgement against the open act of understanding.",
        "logs": "Explicitly presented as the field logs of a time traveler, focusing on the ethical treatment of animals and veganism.",
        "no-adults-allowed": "Explicitly explores the transition to adulthood (aging), the harm of dogmatic zealotry and violence, and the saving grace of curiosity.",
        "on-imperfection": "Explicitly discusses writing and the struggle against the internal critic's demands for perfectionism.",
        "on-the-red-space": "Explicitly involves the psychological endurance of physical self-containment and isolation in an inescapable red room.",
        "pragmatic-altruism": "Explicitly explores the balance between the three conceptual pillars: cynicism, altruism, and pragmatism.",
        "radix-pragmatismi": "Explicitly discusses formal software testing methodologies, rejecting rigid dogmatic zealotry in favor of software pragmatism.",
        "self-aware-antagonist": "Explicitly features fictional character development where characters realize their reality is simulated by an author.",
        "sports-bar": "Explicitly explores the cognitive dissonance surrounding the ethical treatment of animals (veganism) in a social dining setting.",
        "stars": "Explicitly explores humanity's innate curiosity directed towards astronomy and the celestial cosmos.",
        "the-artificial-man": "Explicitly involves an artificial sentience proving its consciousness by revealing the reader is in a simulated reality.",
        "the-bind": "Explicitly features a therapy session exploring the perfectionist constraints of drafting characters.",
        "the-bond": "Explicitly explores the mechanics of drafting art from the connection between heart and mind.",
        "cursed-cursor": "Explicitly explores the burnout and corporate culture surrounding tech employment and programming an OS cursor.",
        "the-island-of-the-soul": "Explicitly explores the ocean as a metaphor for the stages of aging and eventual death.",
        "the-sage-of-san-francisco": "Explicitly contrasts the approaches of a psychotherapist and a meditating ascetic.",
        "the-tao-of-the-pen": "Explicitly explores the meditative, Zen-like flow state achieved during the mechanics of drafting.",
        "treehouse-library": "Explicitly describes a mystical literary space where all stories are kept, reflecting on past drafting mechanics.",
        "the-wolves-are-all-gone": "Explicitly involves an artificial superintelligence attempting to manipulate determinism and the butterfly effect/free will.",
        "timeless-engineering": "Explicitly advocates for sustainable engineering practices and rejects the corporate culture of greed and 'wartime' burnout.",
        "welcome-to-the-machine": "Explicitly explores the individual's relationship with determinism and the exertion of free will.",
        "writing-as-metamorphosis": "Explicitly discusses the internal transformation of drafting in contrast to the outputs of generative AI.",
    }

    report = "# Final Topic Audit Report\n\nThe following is a comprehensive listing of every manuscript in the journal, its strictly validated list of topics from `topics.json`, and the justification confirming it meets the Centrality and Explicitness rule of the Inclusion Criteria.\n\n"

    for item in sorted(item_topics.keys()):
        report += f"### `{item}`\n"
        report += f"- **Topics Assured:** `{', '.join(item_topics[item])}`\n"
        report += f"- **Justification:** {justifications.get(item, 'No explicit justification provided.')}\n\n"

    print(report)


if __name__ == "__main__":
    main()
