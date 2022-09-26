# ClearXR

ClearXR gives JMonkey Engine developers a way to access raw input signals from OpenXR hardware. This is useful if you
need to define input handling logic that is too complex for the basic static
[action manifest](https://github.com/ValveSoftware/openvr/wiki/Action-manifest) approach, and it allows you to
distribute your input handling logic throughout the application instead of putting it all in one place. This approach
can save you time and effort in the long-term by making your application more cohesive, less coupled, and more
programmatic.

Although not strictly required, it's useful to have a cursory understanding of
the [OpenXR standard](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf) before proceeding.

## Getting Access

There are three ways to include ClearXR in your project:

1. Download the pre-built binaries.
2. Build the binaries from source.
3. Reference the source directly.

**Option 1**

The pre-built binaries are available via Maven. The latest version is `io.jackbradshaw:clearxr:0.0.0` and previous
versions are available in the [Maven Central Repository](https://search.maven.org/artifact/io.jackbradshaw/clearxr).

**Option 2**

To build the binary from source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository by running `git clone https://github.com/jack-bradshaw/monorepo && cd monorepo`
3. Invoke the build by running `bazel build //java/io/jackbradshaw/clearxr/clearxr_full.deploy`
4. Collect the binary from the `monorepo/bazel-out` for inclusion in your project.

**Option 3**

To include the source in your project directly:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html) and follow
   the [Introduction Guide](https://bazel.build/about/intro) to set up a workspace if you don't already have one.
2. Install this repository in your WORKSPACE file:

```
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
git_repository(
    name = "io_jackbradshaw",
    branch = "main",
    remote = "https://github.com/jack-bradshaw/monorepo",
)
```

3. Reference the library in the deps of your BUILD target:

```
kt_jvm_library(
    name = "my_hello_world",
    srcs = "MyHelloWorld.kt",
    deps = [
        "@io_jackbradshaw//:java/io/jackbradshaw/clearxr",
    ]
)
```

## Basic Tutorial

Instantiate the ClearXR object. This object exposes all the utilities contained in the library.

```
import io.jackbradshaw.clearxr.clearXr

val clearXr = clearXr()
```

Use the manifest installer to generate an action manifest file and write it to disk. You don't need to worry about how
it works, it's enough to know that it contains important configuration details that ClearXr depends on at runtime.

```
clearXr.manifestInstaller().deployActionManifestFiles()
```

Declare the action manifest to the JMonkey Engine VR system.

```
vrAppState.getVrInput().registerActionManifest(clearXr.config().actionManifestFile, clearXr.config().actionSetName)
```

Register a listener with JMonkey. For example: The "A button" on the Valve Index controller.

```
import io.jackbradshaw.clearxr.events.booleanEvent
import io.jackbradshaw.clearxr.standard.input
import io.jackbradshaw.clearxr.standard.StandardUser.LEFT_HAND
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.A
import io.jackbradshaw.clearxr.standard.StandardInputComponent.CLICK
import io.jackbradshaw.clearxr.standard.StandardInteractionProfile.VALVE_INDEX_CONTROLLER

val input: Input = input(LEFT_HAND, A, CLICK),
val action: String = clearXr.manifestEncoder().encodeInput(VALVE_INDEX_CONTROLLER.profile, input)!!
val events: Flow<Boolean> = vrAppState.getVrInput().booleanEvent()

events.collect { 
  println("The A button on the left hand controller is " + if (it) "pressed." else "released.")
}
```

An update will be printed to the console whenever the "A button" is pressed or released.

## Non-Binary Input

For events which are not on/off, use the `floatEvent` function instead. This is useful for thumbsticks, touchpads, and
other such inputs. For example: The "Thumbstick" on the Valve Index controller.

```
import io.jackbradshaw.clearxr.events.floatEvent
import io.jackbradshaw.clearxr.standard.input
import io.jackbradshaw.clearxr.standard.StandardUser.LEFT_HAND
import io.jackbradshaw.clearxr.standard.StandardInputIdentifier.THUMBSTICK
import io.jackbradshaw.clearxr.standard.StandardInputComponent.X
import io.jackbradshaw.clearxr.standard.StandardInteractionProfile.VALVE_INDEX_CONTROLLER

val input: Input = input(LEFT_HAND, THUMBSTICK, X),
val action: String = clearXr.manifestEncoder().encodeInput(VALVE_INDEX_CONTROLLER.profile, input)!!
val events: Flow<Boolean> = vrAppState.getVrInput().floatEvent()

events.collect { 
  println("The X position of the left hand thumbstick is $it.")
}
```

An update will be printed to the console whenever the thumbstick is moved.

## Non-Default Configurations

The default config can be overriden by passing a custom config.

```
import io.jackbradshaw.clearxr.clearXr
import io.jackbradshaw.clearxr.config.config

val config = config(
    actionManifestDirectory = "/home/myapp",
    actionManifestName = "action_manifest.json",
    actionSetName = "myset"
)

val clearXr = clearXr(config)
```

## Dagger Integration

The ClearXR object is implemented as a [Dagger](https://github.com/google/dagger) component. This means other Dagger
components can install it as a component dependency and inject the ClearXR classes directly.

```
import io.jackbradshaw.clearxr.ClearXr
import io.jackbradshaw.clearxr.manifest.installer.ManifestInstaller
import javax.inject.Inject

@Component(dependencies = [ClearXr::class])
interface MyComponent() {
  @Component.Builder
  interface Builder {
    fun setClearXr(clearXr: ClearXr): Builder
    fun build(): MyComponent
  }
}

class MySetup @Inject internal constructor(
  private val clearXrInstaller: ManifestInstaller
) {
  suspend fun doAllSetupTasks() {
    clearXrInstaller.deployActionManifestFiles()
  }
}
```

## OpenXR Model

ClearXR contains a model that encodes the OpenXR standard. The
[io/jackbradshaw/clearxr/model](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/clearxr/model)
package and the
[io/jackbradshaw/clearxr/standard](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/clearxr/standard)
package contain the relevant classes.

## Protobuf Support

Most data holder types in this package are implemented using
Google's [protobuf library](https://developers.google.com/protocol-buffers). This provides build-in support
for serialization and deserialization without extra libraries.

## Support

Please include [#clearxr](https://github.com/jack-bradshaw/monorepo/issues?q=is%3Aissue+is%3Aopen+%23clearxr+) in all
related bugs.