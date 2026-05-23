import sys

with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    lines = f.readlines()

new_lines = []
in_test = False
test_lines = []

for i, line in enumerate(lines):
    if line.strip().startswith("@Test"):
        if test_lines:
            # flush previous test
            new_lines.extend(test_lines)
            test_lines = []
        in_test = True
        test_lines.append(line)
        continue
    
    if in_test and (line.strip().startswith("private fun") or line.strip().startswith("abstract")):
        in_test = False
        # flush test
        if test_lines:
            new_lines.extend(test_lines)
            test_lines = []
        new_lines.append(line)
        continue

    if in_test:
        test_lines.append(line)
        if line.strip() == "}": # End of runBlocking<Unit> { block
            in_test = False
            # Now we process test_lines
            # Check if it uses beginProcessingWithRoundCount
            text = "".join(test_lines)
            if "val roundCount = beginProcessingWithRoundCount(" in text or "val roundCount =\n            beginProcessingWithRoundCount(" in text:
                # Replace with runKspTest
                import re
                match = re.search(r"val roundCount =\s*beginProcessingWithRoundCount\((.*?)\)\n", text)
                if match:
                    args = match.group(1)
                    text = text.replace("runBlocking<Unit> {", f"runKspTest({args}) {{ roundCount ->")
                    text = text.replace(match.group(0), "")
                    text = text.replace("subject().", "")
                test_lines = [text]
            elif "beginProcessingWithoutRoundCount(" in text:
                import re
                match = re.search(r"beginProcessingWithoutRoundCount\((.*?)\)\n", text)
                if match:
                    args = match.group(1)
                    text = text.replace("runBlocking<Unit> {", f"runKspUnitTest({args}) {{")
                    text = text.replace(match.group(0), "")
                    text = text.replace("subject().", "")
                test_lines = [text]
            
            # flush test
            new_lines.extend(test_lines)
            test_lines = []
            
    else:
        new_lines.append(line)

content = "".join(new_lines)

helpers = """  /**
   * Helper function to reduce boilerplate across KspService tests that track rounds.
   */
  protected fun runKspTest(
      sources: Set<Source> = emptySet(),
      block: suspend KspService.(kotlinx.coroutines.Deferred<Int>) -> Unit
  ) = runBlocking<Unit> {
    val roundCount = beginProcessingWithRoundCount(sources)
    subject().block(roundCount)
  }

  /**
   * Helper function to reduce boilerplate across KspService tests that do not track rounds.
   */
  protected fun runKspUnitTest(
      sources: Set<Source> = emptySet(),
      block: suspend KspService.() -> Unit
  ) = runBlocking<Unit> {
    beginProcessingWithoutRoundCount(sources)
    subject().block()
  }

  /**
   * Prepares [subject].
"""
content = content.replace("  /**\n   * Prepares [subject].\n", helpers)

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.write(content)

print("done")
