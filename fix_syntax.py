with open("first_party/oksp/service/KspServiceTest.kt", "r") as f:
    content = f.read()

content = content.replace("onEachRoundStart().flow.flow.collect", "onEachRoundStart().flow.collect")
content = content.replace("onFinalRoundComplete().first()", "onFinalRoundComplete().flow.first()")

with open("first_party/oksp/service/KspServiceTest.kt", "w") as f:
    f.write(content)
print("Syntax fixed.")
