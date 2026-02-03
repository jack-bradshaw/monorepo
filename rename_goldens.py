import os

goldens_dir = "first_party/site/tests/goldens"

# ContentAppearanceTest mappings
content_mappings = {
    "ContentAppearanceTest_indexPage_smallScreen_matchesGolden.png": "ContentAppearanceTest_indexPage_smallScreen_appearance.png",
    "ContentAppearanceTest_indexPage_mediumScreen_matchesGolden.png": "ContentAppearanceTest_indexPage_mediumScreen_appearance.png",
    "ContentAppearanceTest_indexPage_largeScreen_matchesGolden.png": "ContentAppearanceTest_indexPage_largeScreen_appearance.png",
    "ContentAppearanceTest_galleryHighlightsPage_matchesGolden.png": "ContentAppearanceTest_galleryHighlightsPage_largeScreen_appearance.png",
    "ContentAppearanceTest_journalHighlightsPage_matchesGolden.png": "ContentAppearanceTest_journalHighlightsPage_largeScreen_appearance.png",
    "ContentAppearanceTest_repositoryHighlightsPage_matchesGolden.png": "ContentAppearanceTest_repositoryHighlightsPage_largeScreen_appearance.png",
}

# General pattern for expansion tests
expansion_pages = [
    "gallerySubjectsPage", "galleryPalettesPage", "galleryChronologicalPage",
    "journalTopicsPage", "journalSeriesPage", "journalGenresPage", "journalChronologicalPage",
    "repositoryLocationsPage", "repositoryTechnologiesPage", "aboutPage"
]

for page in expansion_pages:
    content_mappings[f"ContentAppearanceTest_{page}_largeScreen_defaultExpansion_matchesGolden.png"] = f"ContentAppearanceTest_{page}_largeScreen_defaultExpansion_appearance.png"
    content_mappings[f"ContentAppearanceTest_{page}_largeScreen_expanded_matchesGolden.png"] = f"ContentAppearanceTest_{page}_largeScreen_expandAll_appearance.png"
    content_mappings[f"ContentAppearanceTest_{page}_largeScreen_collapsed_matchesGolden.png"] = f"ContentAppearanceTest_{page}_largeScreen_collapseAll_appearance.png"

# MenuAppearanceTest mappings
menu_mappings = {
    "MenuTest_primaryPopupMenu_matchesGolden.png": "MenuAppearanceTest_indexPage_smallScreen_primaryMenu_appearance.png",
    "MenuTest_secondaryPopupMenu_matchesGolden.png": "MenuAppearanceTest_galleryHighlightsPage_smallScreen_secondaryMenu_appearance.png",
}

all_mappings = {**content_mappings, **menu_mappings}

for old_name, new_name in all_mappings.items():
    old_path = os.path.join(goldens_dir, old_name)
    new_path = os.path.join(goldens_dir, new_name)
    if os.path.exists(old_path):
        print(f"Renaming {old_name} to {new_name}")
        os.rename(old_path, new_path)
    else:
        print(f"Warning: {old_name} not found")
