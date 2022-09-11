# CleanXR

A system for using OpenXR with advanced programming techniques and architectural best practices.

[OpenXR](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf) can save you a lot of time when writing XR
software, but it comes at a cost to your application architecture. The common OpenXR implementations don't give you
access to the raw input and output signals from controllers, instead requiring you to define static mapping files
that transform input/output into "actions". For example instead of receiving an "button A pressed" event, you define
a mapping that transform the A button into the "open inventory" action, then you listen for the open inventory event.
While this can be useful in trivial cases, it has long term consequences for the health of your codebase.

To be specific about some of the problems:

1. The manifest is a single file, which means it must contain the logic for many unrelated parts of your application.
   This is a textbook example of high-coupling/low-cohesion and it has the effect of making your codebase harder to
   maintain and reason able. It would be better if the logic contained in the manifest were be distributed throughout
   the application to the places where it is most relevant (high-cohesion/low-coupling).
2. The manifest does not support input combinations, which means it's impossible to define complex input operations
   (e.g. "A button" + "thumbstuck up"). This severely restricts the versatility of the controls and limits you to simple
   mappings. It would be better if combinations of controls could be defined using code.
3. While the manifest provides a way to define multiple mapping profiles (e.g. walking controls, driving controls, pause
   menu menu controls), there's no way to intelligently combine them. If there was overlap and you wanted to simplify
   the controls by defining a base set and several extension sets, you couldn't do this. It would be better if the
   mappings could be defined in code to make use of standard programming techniques.

The problems go on, but what can be done to fix them? The root of the problem is the way OpenXR couples low-level events
(button pressed) to high-level actions (open inventory). Since there's no way to observe the inputs directly in many
OpenXR implementations, the next best solution is to simply create an action for every input then listen to the actions
as if they were inputs. This is the solution provided by CleanXR.

CleanXR generates a manifest for you which contains passthrough mappings for all the devices supported by version 1.0 of
the OpenXR specification. This includes the Valve Index controller, the Occulus Touch controller, and the Vive
Controller amongst others. All you need to do is call the generation class and pass the manifest to your OpenXR
implementation. The rest is simply a matter of listening for inputs and handling them like any other callback. Not only
does this simplify your architecture, it also allows you to completely ignore the details of how the manifest is
supposed to be written (which as of writing is not well documented anywhere on the internet). Overall this saves you
time, makes your application architecture cleaner, and lets you create controls that would be impossible otherwise.

Follow the tutorial below for a practical guide to using CleanXR.

## Tutorial

This tutorial is divided into three sections:

1. Getting access: How to actually import the library into your project.
2. Core usage guide: How to use the core features of the library.
3. Supplementary usage guide: How to use the the supplementary features of the library.

If you still have questions after reading the tutorial please email jack@jackbradshaw.io.

### Getting Access

Getting access covers including CleanXR in your project dependencies. You have three options:

1. Download the pre-built binaries.
2. Build the binaries from source.
3. Reference the source directly.

The pre-built binaries are available via Maven. The latest version is `io.jackbradshaw:clearxr:0.0.1` and previous
versions are available in the [Maven Central Repository](https://search.maven.org/artifact/io.jackbradshaw/clearxr).

To build the binary from source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository by running `git clone https://github.com/jack-bradshaw/monorepo && cd monorepo`
3. Invoke the build by running `bazel build //java/io/jackbradshaw/klu_full.deploy`
4. Collect the binary from the `monorepo/bazel-out` for inclusion in your project.

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
        "@io_jackbradshaw//:java/io/jackbradshaw/openxr",
    ]
)
```

### Core Usage Guide

The core usage guide covers the basics of using CleanXR. Start by instantiating the CleanXR object:

```
import io.jackbradshaw.clearxr.clearxr

val clearXr = clearXr()
```

This gives you access to all the different parts of the library.

Next use the manifest installer to generate a manifest and write it to disk:

```
cleanXr.manifestInstaller().deployActionManifestFiles()
```

By default the file is named `clearxr_action_manifest.json` and is placed in the device's temporary directory. If
you need to change this, read the supplementary usage guide for details.

Next pass the manifest to the OpenXR framework you're using. The details of this step depend on your framework of
choice. Using the JMonkey Engine for example:

```
vrAppState.getVrInput().declareActionManifest(cleanXr.config().actionManifestFile(), cleanXr.config().actionSetName)
```

Finally listen for input events and use CleanXR to turn them into useful objects. Again the details of this step depend
onyour framework of choice. Using the JMonkey Engine for example:

```
vrAppState.getVrInput().registerAnalogListener(input -> {
  val input = cleanXr.manifestEncoder().decodeInput(input)
  TODO() // Your input handling logic
})
```

That's it. Now you can listen for all inputs directly and handle them using whichever programming technique you like. No
need for complex manifest files, mappings, and action sets. Just input events and logic.

### Advanced Usage Guide

The advanced usage guide covers a few additional points that were left out of the core usage guide. Specifically:

1. Configuring CleanXR.
2. Integrating with Dagger.
3. The OpenXR model.

#### Configuring CleanXR

CleanXR can be configured using a `Config` object:

```
import io.jackbradshaw.clearxr.cleanXr
import io.jackbradshaw.clearxr.config.config

val config = config(
    actionManifestDirectory = "/home/myapp",
    actionManifestName = "action_manifest.json",
    actionSetName = "omniset"
)
val cleanXr = cleanXr(config)
```

The config object is defined using Google's protobuf library. This provides various built-in advantags such as easily
serializing and deserializing them with virtually no boilerplate or overhead.

#### Integrating With Dagger

The CleanXR object is actually a Dagger component, so if you're using Dagger you can add it to you your component
dependencies and inject the classes it contains into your objects:

```
import io.jackbradshaw.clearxr.ClearXr
import io.jackbradshaw.clearxr.manifest.intaller.ManifestInstaller
import javax.inject.Inject

@Component(dependencies = [ClearXr::class])
interface MyComponent() {
  fun setup(): Setup
  
  @Component.Builder
  interface Builder {
    fun setClearXr(clearXr: ClearXr): Builder
    fun build(): MyComponent
  }
}

class Setup @Inject internal constructor(
  private val clearXrInstaller: ManifestInstaller
) {
  suspend fun doAllSetupTasks() {
    clearXrInstaller.deployActionManifestFiles()
  }
}
```

Using Dagger for dependency injection is highly recommended for testability and maintainability.

#### OpenXR Model

The library actually contains a rich model of the OpenXR input/output system and the standard controlers. These are
based on the standard which is unlikely to change so you may safely use them in your application. See the
io/jackbradshaw/cleanxr/model package and the io/jackbradshaw/cleanxr/standard package for details.

### FAQ

Q: Does this system create extra work for the application programmer? Won't they have to define all the mappings now?
A: This was already something engineers needed to do, it's simply a different way of doing it. Without this library they
would be defined in a single json file, where with the library they're defined in code. This solution reduces work in
the long term by enabling a more maintainable application architecture, and it reduces the need to learn about
action manifests in the short term.

Q: Will you update the system when new devices become available?
A: Yes, but there might be some delay. If you need a device urgently please email jack@jackbradshaw.io or consider
creating a pull request to add it directly. Adding a new device is a simple as adding a new constant to the
StandardInteractionProfile enum.

### Support

Please include [#clearxr](https://github.com/jack-bradshaw/monorepo/issues?q=is%3Aissue+is%3Aopen+%23clearxr+) in all
related bugs.