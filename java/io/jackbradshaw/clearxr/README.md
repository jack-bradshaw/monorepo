# CleanXR

A system for using OpenXR with advanced programming techniques and architectural best practices.

[OpenXR](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf) can save you time and effort when writing XR
software, but it comes at a cost to your application architecture. The common OpenXR implementations don't give you
access to the raw input and output signals from controllers, instead requiring you to define static manifest files
that map input/output signals into "actions". For example, you would map "button A pressed" to action "open inventory"
then be notified of "open inventory" in code when the button is pressed. While the simplicity of this approach can be
appealing, it has severe long term consequences for code health.

Let's break down some of the problems with this approach:

1. The manifest is a single file, meaning it must contain the logic for many unrelated parts of your application. This
   is a textbook example of high coupling and low cohesion, and it has the effect of making your
   codebase harder to maintain and reason about. It would be better if the logic contained in the manifest were
   distributed throughout the application to the places where it is most relevant (high cohesion and low coupling).
2. The manifest does not support input combinations, meaning it's impossible to define complex input operations
   (e.g. "A button" and "thumbstuck up"). This severely restricts the versatility of your game logic. It would be better
   raw inputs were observable in code so general programming could be used to process them.
3. The manifest does not provide a convenient mechanism for combining different sets of mappings. For trivial games
   with only a few sets of mappings this isn't an issue, but as the number of sets grows the duplication between them
   become unmaintainable (see combanatorics).

The problems go on, but for brevity let's skip to the root of it all: The way OpenXR statically couples low-level
input/output events (e.g. button pressed) to high-level actions (e.g. open inventory) puts hard constraints on the
consuming architecture. It violates many best practices (such as separation of concerns and low-coupling), meaning
in the long term engineers are foced to use suboptimal patterns and degrade the health of the codebase.

Ideally the benefits of OpenXR could be achieved without the detriments. The solution is to fully encapsulate the system
so that the raw inputs can be observed. This involves defining an action corresponding to every input so the actions
can be treated as essentially a direct input passthrough. Setting this up manually would be considerable work and
maintenance, so CleanXR was created to automate the boilerplate work behind a nice API.

CleanXR generates a manifest for you which contains passthrough mappings for all the devices supported by version 1.0 of
the OpenXR specification. This includes the Valve Index controller, the Occulus Touch controller, the Vive
Controller and the XBOX controller (amongst others). All you need to do is call the generation code and pass the
manifest to your OpenXR implementation. The rest is simply a matter of listening for inputs and handling them like any
other callback. Not only does this simplify your architecture, it also allows you to completely ignore many details of
OpenXR that are not well documented anywhere on the internet (such as how to write a manifest). Overall this saves you
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