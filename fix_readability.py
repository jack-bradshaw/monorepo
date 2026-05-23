with open('first_party/oksp/service/KspServiceTest.kt', 'r') as f:
    lines = f.readlines()

for i, line in enumerate(lines):
    if "files.first()" in line:
        lines[i] = line.replace("files.first()", "files.single()")
    if "getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).first()" in line:
        lines[i] = line.replace("getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).first()", "getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).single()")

with open('first_party/oksp/service/KspServiceTest.kt', 'w') as f:
    f.writelines(lines)

print("done")
