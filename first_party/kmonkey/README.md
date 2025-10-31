# KMonkey

Kotlin tools for the JMonkey engine.

## Release

Released to [Maven Central](https://search.maven.org/artifact/com.jackbradshaw/kmonkey).

## Utilities

This package provides the following utilities:

- [Coroutines](/first_party/kmonkey/coroutines): Coroutine dispatchers for the JMonkey engine.

Dispatchers are provided for the rendering thread and the physics thread.

## Rendering

The rendering dispatcher executes coroutines on the JMonkey main rendering thread. It's provided as
an extension function on the `SimpleApplication`. For example, the following code moves an item
every second:

```kotlin
import com.jackbradshaw.kmonkey.coroutines.renderingDispatcher

class MyApplication : SimpleApplication {

  private val worldItem by lazy { SomeWorldItem() }

  init {
    this.renderingDispatcher().launch {
      rootNode.attachChild(worldItem)
      var index = 0
      while(true) {
        index += 1
        delay(1000L)
        worldItem.setLocalTranslation(Vector3f(index, 0, 0))
      }
    }
  }
}
```

## Physics

The physics dispatcher executes coroutines on the JMonkey physics thread. It's provided as an
extension function on the `PhysicsSpace`. For example, the following code applies a force every
second:

```kotlin
import com.jackbradshaw.kmonkey.coroutines.physicsDispatcher

class MyApplication : SimpleApplication {

  private val physicsItem by lazy { SomePhysicsItem() }

  init {
    val bullet = BulletAppState().apply { stateManager.attach(it) }
    val physicsSpace = bullet.getPhysicsSpace()
    physicsSpace.physicsDispatcher().launch {
      physicsSpace.add(physicsItem)
      while(true) {
        delay(1000L)
        physicsItem.applyCentralForce(Vector3f(1, 0, 0))
      }
    }
  }
}
```

## Issues

Issues relating to this package and its subpackages are tagged with `kmonkey`.

## Contributions

Third-party contributions are accepted.
