import re

with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    content = f.read()

tests_to_rename = [
    "allowProcessing_failsAfterStart",
    "completeRound_failsAfterFinalRoundCompletes",
    "completeRound_failsBeforeProcessingBegins",
    "defer_failsAfterFinalRoundCompletes",
    "failString_failsAfterFinalRoundCompletes",
    "failString_failsBeforeProcessingBegins",
    "failThrowable_failsAfterFinalRoundCompletes",
    "failThrowable_failsBeforeProcessingBegins",
    "log_failsAfterFinalRoundCompletes",
    "log_failsBeforeProcessingBegins",
    "publish_failsAfterFinalRoundCompletes",
    "publish_failsBeforeProcessingBegins",
    "resolution_completingRoundCancelsPendingCalls",
    "resolution_firstRoundNoSources_evaluatesNothing",
    "resolution_firstRoundWithSources_evaluatesSources",
    "resolution_secondRound_evaluatesDeferredSources",
    "resolution_secondRound_evaluatesGeneratedSources",
    "withContext_failsAfterFinalRoundCompletes",
    "withContext_failsBeforeProcessingBegins"
]

for test in tests_to_rename:
    content = re.sub(rf"fun {test}\(\)", f"fun unit_{test}()", content)

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.write(content)

print("Renamed to unit_")
