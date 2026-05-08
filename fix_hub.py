import os

def replace_in_file(path, old, new):
    if not os.path.exists(path): return
    with open(path, 'r') as f:
        content = f.read()
    content = content.replace(old, new)
    with open(path, 'w') as f:
        f.write(content)

# Rename files
os.rename('first_party/concurrency/quinn/testing/factory/IdleableQuinnHubImpl.kt', 'first_party/concurrency/quinn/testing/factory/IdleableQuinnFactoryImpl.kt')
os.rename('first_party/concurrency/quinn/testing/factory/IdleableQuinnHubModule.kt', 'first_party/concurrency/quinn/testing/factory/IdleableQuinnFactoryModule.kt')

# Update references in all relevant files
files = [
    'first_party/concurrency/quinn/testing/BUILD',
    'first_party/concurrency/quinn/testing/idleable/IdleableQuinn.kt',
    'first_party/concurrency/quinn/testing/factory/IdleableQuinnFactoryImpl.kt',
    'first_party/concurrency/quinn/testing/factory/IdleableQuinnFactoryModule.kt',
    'first_party/concurrency/quinn/testing/TestingQuinnComponent.kt',
    'first_party/concurrency/quinn/testing/idleable/IdleableQuinnImplTest.kt',
    'first_party/concurrency/quinn/testing/idleable/IdleableQuinnAsQuinnTest.kt',
    'first_party/concurrency/quinn/testing/taskbarrier/TaskBarrierModule.kt',
    'first_party/concurrency/quinn/README.md'
]

for f in files:
    replace_in_file(f, 'IdleableQuinn.Hub', 'IdleableQuinn.Factory')
    replace_in_file(f, 'IdleableQuinnHubImpl', 'IdleableQuinnFactoryImpl')
    replace_in_file(f, 'IdleableQuinnHubModule', 'IdleableQuinnFactoryModule')
    replace_in_file(f, 'idleableHub', 'idleableFactory')
    replace_in_file(f, 'quinnHub', 'quinnFactory')
    replace_in_file(f, 'factory/IdleableQuinnHubImpl.kt', 'factory/IdleableQuinnFactoryImpl.kt')
    replace_in_file(f, 'factory/IdleableQuinnHubModule.kt', 'factory/IdleableQuinnFactoryModule.kt')

# README specific updates to fix the injection setup since we're using realistic testing components
with open('first_party/concurrency/quinn/README.md', 'r') as f:
    readme = f.read()

# Make the realistic testing example better
readme = readme.replace('DaggerTestComponent.create().inject(this)', 'val coroutinesComponent = realisticCoroutinesTestingComponent(taskBarrierComponent)\n    // setup injection...')

with open('first_party/concurrency/quinn/README.md', 'w') as f:
    f.write(readme)
