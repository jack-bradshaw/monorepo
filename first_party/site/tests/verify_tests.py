import re

with open('first_party/site/tests/AppearanceTest.kt', 'r') as f:
    content = f.read()

# Regex to find test methods
# Assumes standard formatting
matches = re.findall(r'@Test\s+fun\s+(\w+)_matchesGolden\(\)\s*\{\s*runPageScreendiffTest\(.*?(?:,\s*ScreenWidth\.(\w+))?\)\s*\}', content, re.DOTALL)

for method_name, screen_width in matches:
    # If screen_width is None, it defaults to LARGE
    actual_width = screen_width if screen_width else "LARGE"
    
    expected_width = "UNKNOWN"
    if "smallScreen" in method_name:
        expected_width = "SMALL"
    elif "mediumScreen" in method_name:
        expected_width = "MEDIUM"
    elif "largeScreen" in method_name:
        expected_width = "LARGE"
    
    if expected_width != "UNKNOWN" and expected_width != actual_width:
        print(f"MISMATCH: {method_name} expects {expected_width} bu uses {actual_width}")

print("Verification complete.")
