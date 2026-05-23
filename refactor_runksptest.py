import re

with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    content = f.read()

# Insert the helpers in the abstract block before setupSubject
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
   * Constructs the service subject. Must be called precisely once at the start of every test before
"""
content = content.replace("  /**\n   * Constructs the service subject. Must be called precisely once at the start of every test before", helpers)

# Now refactor runBlocking tests.
# Pattern 1: e2e tests with roundCount
pattern_e2e = r'fun (e2e_[^()]+)\(\) =\n\s*runBlocking<Unit> \{\n\s*val roundCount =\s*beginProcessingWithRoundCount\(([^)]+)\)'
def replace_e2e(m):
    return f"fun {m.group(1)}() =\n      runKspTest({m.group(2)}) {{ roundCount ->"

content = re.sub(pattern_e2e, replace_e2e, content)

# Pattern 2: unit tests with beginProcessingWithoutRoundCount
pattern_unit = r'fun (unit_[^()]+)\(\) =\n\s*runBlocking<Unit> \{\n\s*beginProcessingWithoutRoundCount\(([^)]+)\)'
def replace_unit(m):
    return f"fun {m.group(1)}() =\n      runKspUnitTest({m.group(2)}) {{"

content = re.sub(pattern_unit, replace_unit, content)

# Pattern 3: remove subject(). where it is now implicitly this
# But only inside the test blocks. Since we can't easily parse blocks in regex perfectly,
# we can just do a global replace of "subject()." -> "" inside the whole file? No! There are
# other places (like the abstract methods and private methods) where subject() is called.
# But wait, runKspTest and runKspUnitTest blocks provide `subject()` as `this`.
# We can safely replace `subject().` with nothing for the entire file EXCEPT the definition of subject()
# and inside runKspTest/runKspUnitTest definitions.
pass

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.write(content)

print("regex run")
