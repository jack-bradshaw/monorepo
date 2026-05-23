with open("first_party/sealant/hub/BUILD", "r") as f:
    content = f.read()

content = content.replace('        "//first_party/sealant:scope",\n        "//first_party/sealant/session:test",\n    ],\n)', '        "//first_party/sealant:scope",\n        "//first_party/sealant/session:test",\n        "//first_party/closet/observable/standard:component",\n        "//first_party/closet/observable/standard:component_impl",\n    ],\n)')

with open("first_party/sealant/hub/BUILD", "w") as f:
    f.write(content)

with open("first_party/sealant/BUILD", "r") as f:
    content = f.read()

content = content.replace('        "//first_party/coroutines/testing/realistic:component_impl",\n    ],\n)', '        "//first_party/coroutines/testing/realistic:component_impl",\n        "//first_party/closet/observable/standard:component",\n        "//first_party/closet/observable/standard:component_impl",\n    ],\n)')

with open("first_party/sealant/BUILD", "w") as f:
    f.write(content)

print("done")
