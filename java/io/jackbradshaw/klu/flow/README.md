# Flow

The flow package contains utilities and helpers for working with [Kotlin Flows](https://kotlinlang.org/docs/flow.html).

## Operators

These helpers are simple functions for manipulating Kotlin Flows. The available operators are:

### collectToMap

The `collectToMap()` operator takes a flow of pairs and produces a map containing the values. For example, the following
code prints `{0=Hello, 1=World}`:

```
import io.jackbradshaw.klu.flow.collecToMap

val pairs = flow {
  emit(0 to "Hello")
  emit(1 to "World")
}

val map = pairs.collectToMap()

println(map)
```

## Annotations

These are metadata labels to make documenting flow-based code less work. The available annotations are:

- HotFlow
- ColdFlow
- IndefiniteFlow

View the [KDoc](https://github.com/jack-bradshaw/monorepo/blob/main/java/io/jackbradshaw/klu/flow/Annotations.kt) for
detailed information about them.