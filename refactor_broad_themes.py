import json

items_path = 'first_party/site/data/journal/items.json'
with open(items_path, 'r') as f:
    master_keys = set([i['key'] for i in json.load(f)])

themes_path = 'first_party/site/data/journal/themes.json'
with open(themes_path, 'r') as f:
    themes = json.load(f)

# The buckets to nuke
nuke = ['human-nature', 'human-psychology', 'existence-and-experience']

new_themes = [t for t in themes if t['key'] not in nuke]

# Add stars to meaning-and-purpose
for t in new_themes:
    if t['key'] == 'meaning-and-purpose' and 'stars' not in t['items']:
        t['items'].append('stars')
    # Add on-the-red-space to solitude-and-isolation
    if t['key'] == 'solitude-and-isolation' and 'on-the-red-space' not in t['items']:
        t['items'].append('on-the-red-space')

# Are there any other orphans?
themed = set()
for t in new_themes:
    for i in t.get('items', []):
        themed.add(i)

orphans = master_keys - themed
print("Orphans after nuke and targeted distribution:", sorted(list(orphans)))

with open('first_party/site/data/journal/themes.json', 'w') as f:
    json.dump(new_themes, f, indent=2)

print("Nuked broad themes and reorganized successfully.")
