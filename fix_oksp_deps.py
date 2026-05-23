with open("first_party/oksp/application/testing/BUILD", "r") as f:
    content = f.read()

content = content.replace('        "//first_party/coroutines:component",\n    ],\n)', '        "//first_party/coroutines:component",\n        "//first_party/kale/provider:component",\n    ],\n)')

with open("first_party/oksp/application/testing/BUILD", "w") as f:
    f.write(content)

print("done")
