import json
import os
import re


def load_json(path):
    with open(path, "r") as f:
        return json.load(f)


def check_dates(items, items_map):
    broken = []
    for item in items:
        key = item.get("key", "")
        year = item.get("year")

        # Check for _YYYY suffix
        match = re.search(r"_(\d{4})$", key)
        if match:
            suffix_year = int(match.group(1))
            if year != suffix_year:
                broken.append(
                    f"Date Mismatch: Key '{key}' implies {suffix_year}, but year field is {year}"
                )
    return broken


def check_file_existence(items, base_static_path):
    broken = []
    for item in items:
        file_path = item.get("file", "")
        if file_path.startswith("/static/"):
            # Remove /static/ prefix to map to local filesystem if needed, but here we assume relative to site root maybe?
            # actually the path on disk is first_party/site/static/...
            # item file: /static/content/gallery/...
            # local path: first_party/site/static/content/gallery/...
            local_path = "first_party/site" + file_path
            if not os.path.exists(local_path):
                broken.append(
                    f"Missing File: {local_path} (referenced by {item.get('key')})"
                )
    return broken


def check_refs(
    source_file, source_data, valid_item_keys, valid_series_keys, ref_type="list"
):
    broken = []
    if ref_type == "list":
        for item in source_data:
            if item not in valid_item_keys:
                broken.append(f"{source_file}: Invalid item ref '{item}'")
    elif ref_type == "dict_items":
        for entry in source_data:
            for item in entry.get("items", []):
                if item not in valid_item_keys:
                    broken.append(
                        f"{source_file} (series '{entry.get('key')}'): Invalid item ref '{item}'"
                    )
            if "cover" in entry and entry["cover"] not in valid_item_keys:
                broken.append(
                    f"{source_file} (series '{entry.get('key')}') cover: Invalid item ref '{entry['cover']}'"
                )
            if "series" in entry:
                for series_key in entry["series"]:
                    if series_key not in valid_series_keys:
                        broken.append(
                            f"{source_file} (entry '{entry.get('key')}') series ref: Invalid series ref '{series_key}'"
                        )
    return broken


base_path = "first_party/site/data/gallery"
try:
    items = load_json(os.path.join(base_path, "items.json"))
    valid_item_keys = {item["key"] for item in items}

    series_data = load_json(os.path.join(base_path, "series.json"))
    valid_series_keys = {s["key"] for s in series_data}

    broken_refs = []

    # Check checks
    broken_refs.extend(check_dates(items, items))
    broken_refs.extend(check_file_existence(items, "first_party/site"))

    # Check highlights
    highlights = load_json(os.path.join(base_path, "highlights.json"))
    broken_refs.extend(
        check_refs(
            "highlights.json", highlights, valid_item_keys, valid_series_keys, "list"
        )
    )

    # Check curated
    curated = load_json(os.path.join(base_path, "curated.json"))
    broken_refs.extend(
        check_refs(
            "curated.json", curated, valid_item_keys, valid_series_keys, "dict_items"
        )
    )

    # Check palettes
    palettes = load_json(os.path.join(base_path, "palettes.json"))
    broken_refs.extend(
        check_refs(
            "palettes.json", palettes, valid_item_keys, valid_series_keys, "dict_items"
        )
    )

    # Check series
    broken_refs.extend(
        check_refs(
            "series.json", series_data, valid_item_keys, valid_series_keys, "dict_items"
        )
    )

    if broken_refs:
        print("Found issues:")
        for ref in broken_refs:
            print(ref)
    else:
        print("All references, dates, and files are valid.")

except Exception as e:
    print(f"Error: {e}")
