import sys

with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    lines = f.readlines()

new_lines = []
in_test = False

i = 0
while i < len(lines):
    line = lines[i]
    if line.strip().startswith("@Test"):
        in_test = True
        new_lines.append(line)
        i += 1
        continue
    
    if in_test and line.strip().startswith("private fun"):
        in_test = False
    if in_test and line.strip().startswith("abstract"):
        in_test = False

    if in_test:
        line = line.replace("subject().", "")

        if line.strip() == "runBlocking<Unit> {":
            next_line = lines[i+1]
            if "val roundCount =" in next_line and "beginProcessingWithRoundCount(" in next_line:
                args = next_line.split("beginProcessingWithRoundCount(")[1].rsplit(")", 1)[0]
                new_lines.append(f"      runKspTest({args}) {{ roundCount ->\n")
                i += 2
                continue
            elif "val roundCount =" in next_line:
                next_next_line = lines[i+2]
                args = next_next_line.split("beginProcessingWithRoundCount(")[1].rsplit(")", 1)[0]
                new_lines.append(f"      runKspTest({args}) {{ roundCount ->\n")
                i += 3
                continue
            elif "beginProcessingWithoutRoundCount(" in next_line:
                args = next_line.split("beginProcessingWithoutRoundCount(")[1].rsplit(")", 1)[0]
                new_lines.append(f"      runKspUnitTest({args}) {{\n")
                i += 2
                continue

    new_lines.append(line)
    i += 1

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
