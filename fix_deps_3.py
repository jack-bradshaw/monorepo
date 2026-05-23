with open("first_party/sealant/BUILD", "r") as f:
    content = f.read()

content = content.replace('        "//first_party/coroutines/testing/realistic:component_impl",\n        "//first_party/sealant:scope",', '        "//first_party/coroutines/testing/realistic:component_impl",\n        "//first_party/sealant:scope",\n        "//first_party/closet/observable/standard:component",\n        "//first_party/closet/observable/standard:component_impl",')

with open("first_party/sealant/BUILD", "w") as f:
    f.write(content)

with open("first_party/concurrency/quinn/QuinnImplObservableClosableTest.kt", "r") as f:
    content = f.read()

content = content.replace('class QuinnImplAsObservableClosableTest : ObservableClosableTest<Quinn<String>>() {', 'class QuinnImplObservableClosableTest : ObservableClosableTest<Quinn<String>>() {')
content = content.replace('underTest = DaggerQuinnImplAsObservableClosableTest_TestComponent.builder()', 'underTest = DaggerQuinnImplObservableClosableTest_TestComponent.builder()')

with open("first_party/concurrency/quinn/QuinnImplObservableClosableTest.kt", "w") as f:
    f.write(content)

print("done")
