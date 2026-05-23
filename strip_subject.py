import re

with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    content = f.read()

# Replace all subject(). with empty string
content = content.replace("subject().", "")

# Add it back to the places we know it's needed
content = content.replace(
"""  private suspend fun beginProcessingWithRoundCount(
      sources: Set<Source>
  ): kotlinx.coroutines.Deferred<Int> {
    setupSubject(sources)
    val roundCount = countRoundsAsync()
    taskBarrier().awaitAllIdle()
    advanceToFirstRound()""",
"""  private suspend fun beginProcessingWithRoundCount(
      sources: Set<Source>
  ): kotlinx.coroutines.Deferred<Int> {
    setupSubject(sources)
    val roundCount = countRoundsAsync()
    taskBarrier().awaitAllIdle()
    subject().advanceToFirstRound()"""
)

content = content.replace(
"""  private suspend fun beginProcessingWithoutRoundCount(sources: Set<Source>) {
    setupSubject(sources)
    taskBarrier().awaitAllIdle()
    advanceToFirstRound()""",
"""  private suspend fun beginProcessingWithoutRoundCount(sources: Set<Source>) {
    setupSubject(sources)
    taskBarrier().awaitAllIdle()
    subject().advanceToFirstRound()"""
)

content = content.replace(
"""  private fun countRoundsAsync(): kotlinx.coroutines.Deferred<Int> {
    return CoroutineScope(testDispatcher()).async {
      var count = 0
      onEachRoundStart().flow.collect { count++ }""",
"""  private fun countRoundsAsync(): kotlinx.coroutines.Deferred<Int> {
    return CoroutineScope(testDispatcher()).async {
      var count = 0
      subject().onEachRoundStart().flow.collect { count++ }"""
)

content = content.replace(
"""  protected fun runKspTest(
      sources: Set<Source> = emptySet(),
      block: suspend KspService.(kotlinx.coroutines.Deferred<Int>) -> Unit
  ) = runBlocking<Unit> {
    val roundCount = beginProcessingWithRoundCount(sources)
    block(roundCount)
  }""",
"""  protected fun runKspTest(
      sources: Set<Source> = emptySet(),
      block: suspend KspService.(kotlinx.coroutines.Deferred<Int>) -> Unit
  ) = runBlocking<Unit> {
    val roundCount = beginProcessingWithRoundCount(sources)
    subject().block(roundCount)
  }"""
)

content = content.replace(
"""  protected fun runKspUnitTest(
      sources: Set<Source> = emptySet(),
      block: suspend KspService.() -> Unit
  ) = runBlocking<Unit> {
    beginProcessingWithoutRoundCount(sources)
    block()
  }""",
"""  protected fun runKspUnitTest(
      sources: Set<Source> = emptySet(),
      block: suspend KspService.() -> Unit
  ) = runBlocking<Unit> {
    beginProcessingWithoutRoundCount(sources)
    subject().block()
  }"""
)

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.write(content)

print("done")
