import re

with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    content = f.read()

replacement = """  @Test
  fun e2e__deferral__multi_sources_defer_one__halts_natively_after_one_round() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION, SOURCE_WITHOUT_ANNOTATION)) { roundCount ->
        lateinit var deferredTarget: KSAnnotated
        withContext { context ->
          val resolver = context.resolver
          deferredTarget =
              resolver.getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).first()
        }
        defer(deferredTarget)

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        assertTerminatedWithoutError()
      }"""

old_test = """  @Test
  fun e2e__deferral__multi_sources_defer_one__halts_natively_after_one_round() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION, SOURCE_WITHOUT_ANNOTATION)) { roundCount ->
                deferTargetAnnotation()

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        assertTerminatedWithoutError()
      }"""

content = content.replace(old_test, replacement)

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.write(content)

print("done")
