with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    content = f.read()

# Replace firstOrNull() with single()
content = content.replace(".firstOrNull()", ".single()")

# Remove requireNotNull wrappers because single() doesn't return null
import re
content = re.sub(r'defer\(requireNotNull\(([^)]+)\)\s*\{[^}]+\}\)', r'defer(\1)', content)

# Replace list .first() with .single()
content = content.replace("newFiles.first()", "newFiles.single()")
content = content.replace("getNewFiles().first()", "getNewFiles().single()")
content = content.replace("getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).first()", "getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).single()")

# Replace raw Source constructs
content = content.replace('Source("com.test", "DummyRound2", "kt", "class DummyRound2")', 'createGeneratedSource(2)')
content = content.replace('Source("com.test", "DummyRound3", "kt", "class DummyRound3")', 'createGeneratedSource(3)')

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.write(content)
print("Changes applied")
