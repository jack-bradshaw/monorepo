# KMonkey

Kotlin tools for the JMonkey engine.

## Getting Access

There are three ways to include this library in your project:

1. Use the pre-built package.
2. Build the package from source.
3. Reference the source directly.

### Pre-built Package

Add `io.jackbradshaw:kmonkey:0.0.0` to your project to download the latest pre-built package. Older versions are
available in the [Maven Repository](https://search.maven.org/artifact/io.jackbradshaw/kmonkey).

### Building From Source

To build the package from source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository: `git clone https://github.com/jack-bradshaw/monorepo`
3. Start the build: `bazel build //java/io/jackbradshaw/kmonkey:binary.deploy`

This will produce a jar in the `monorepo/bazel-out/java/io/jackbradshaw/kmonkey` directory. Copy this Jar into your
project as needed.

### Referencing Directly

To reference the package directly in another Bazel workspace:

1. Install this repository in your WORKSPACE.
2. Reference the package in your target deps.

For example:

```
# In your WORKSPACE file
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
git_repository(
    name = "io_jackbradshaw",
    branch = "main",
    remote = "https://github.com/jack-bradshaw/monorepo",
)

# In your BUILD file
kt_jvm_library(
    name = "hello_world",
    srcs = "HelloWorld.kt",
    deps = [
        "@io_jackbradshaw//:java/io/jackbradshaw/kmonkey",
    ]
)
```

## Tutorial

KMonkey provides two utilities for using coroutines with JMonkey:

- A rendering dispatcher that runs coroutines on the main thread.
- A physics dispatcher that runs coroutines on the physics thread.

For example, the following code will add an item to the scene graph and move by a small amount for 100 seconds:

```
import io.jackbradshaw.kmonkey.coroutines.renderingDispatcher

class MyApplication : SimpleApplication {
  
  /* Snip: All the usual setup stuff. */
  
  private val worldItem by lazy { SomeWorldItem() }
  
  init {
    renderingDispatcher().launch {
      rootNode.attachChild(worldItem)
      for (i in 0..100) {
        delay(1000L) // 1 second
        worldItem.setLocalTranslation(i * 10, 0, 0)
      }
    }
  }
}
```

Similarly, the following code will add an item to the physics space and apply a small force to it for 100 seconds:

```
import io.jackbradshaw.kmonkey.coroutines.physicsDispatcher

class MyApplication : SimpleApplication {
  
  /* Snip: All the usual setup stuff. */
  
  private val physicsItem by lazy { SomePhysicsItem() }
  
  init {
    val bullet = BulletAppState().apply { stateManager.attach(it) }
    val physicsSpace = bullet.getPhysicsSpace()
    physicsSpace.physicsDispatcher().launch { 
      physicsSpace.add(physicsItem)
      for (i in 0..100) {
        delay(1000L) // 1 second
        physicsItem.applyCentralForce(Vector3f(10, 0, 0))
      }
    }
  }
}
```

You don't actually need to be in the SimpleApplication to use the dispatchers. For example:

```
class SomeRandomClass(
  private val application: SimpleApplication,
  private val physicsSpace: PhysicsSpace
) {
  init {
    application.renderingDispatcher().launch { /* rendering code */ }
    physicsSpace.physicsDispatcer().launch { /* physics code */ }
  }
}

```

Voilà! You can now use coroutines with JMonkey without the risk of wrong-thread exceptions.