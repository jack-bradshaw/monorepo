# Concurrency

The concurrency package contains utilities and helpers for working with asynchronous Kotlin code.

## Once

These helpers guard a block of code to ensure it runs at most once. For example, the following code prints `Hello,
world!` one time even though the block is called twice.

```
import io.jackbradshaw.klu.concurrency.once

val hello = once {
  println("Hello, world!")
}

hello() // prints
hello() // does nothing
```

No errors are thrown by the above block, but this can be changed with the `throwing()` function. For example, the
following code prints `Hello, world!` then throws an IllegalStateException:

```
import io.jackbradshaw.klu.concurrency.once
import io.jackbradshaw.klu.concurrency.throwing

val hello = once {
  println("Hello, world!")
}.throwing()

hello() // prints
hello() // throws
```

There are various overload functions to configure the exception, specifically `throwing(String)` which allows you to set
the message in the error, `throwing(Throwable)` which allows you to specify the exception entirely, and
`throwing(() -> Throwable)` which allows you to specify the exception lazily.