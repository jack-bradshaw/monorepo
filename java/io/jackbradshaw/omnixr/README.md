# OmniXR

A system for using OpenXR without compromising on architectural best practices.

Contents:

- Problem: The problem this library solves.
- Solution: How this library solves the problem.
- Usage Guide: How to use the library in your project.

Please include [#omnixr](https://github.com/jack-bradshaw/monorepo/issues?q=is%3Aissue+is%3Aopen+%23omnixr+) in all
related bugs.

## Problem

OpenXR is a system for working with AR/VR hardware and software that radically simplifies using XR in games and other
applications. The system essentially has two main parts:
The official [specification document](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf) which defines the
core ideas and concepts of OpenXR, and a series of third party libraries/APIs which implement the specification as
usable software. Together these create a way for engineers to use OpenXR in their applications with less friction, but
it comes at a cost. To explain the cost, we must first cover how OpenXR is used by engineers.

Many OpenXR libraries/APIs use the concept of an "action manifest" for configuring the way OpenXR handles user
input from controllers and head mounted displays. An action manifest is simply a JSON file on disk which contains a list
of "actions" along with a list of "mappings" that transform raw controller input into actions. The application listens
for these actions and never has to consider the raw input events. For example a manifest could contain a "jump" action
along with two mappings: One for the "A" button on the Valve Index controller and one for the "X" button on the Occulus
Touch controller. At runtime the application registers the manifest file with the system and listens for the "jump"
event instead of the "A" and "X" events. While this system is appealing because it avoid needing to support individual
controllers, it has some non-obvious limitations which have long-term consequences.

The limitations of this approach are:

1. The action manifest must be defined as a single JSON file, meaning eventually the file will contain pieces of logic
   across the entire application. This is an example of coupling which is a generally recognized problem for
   applications. In the long term it makes it harder for engineers to understand the program and make changes safely.
2. The acton manifest exposes internal application logic, meaning users can modify the runtime behavior of the
   application in ways that may not be safe or reliable. Some games allow this intentionally, but if the goal is to make
   the controls modifiable then this should available via an in-game user interface for an optimal user experience.
3. The action manifest makes it difficult to change actions at runtime. There is a way to define different "action sets"
   in a manaifest (e.g. one for walking, one for driving), but this creates duplication and chain-reaction complexity
   whenever there is overlap between the sets. Read about combinatorics for more information on this problem.
4. It works well for simple cases but makes it harder to combine inputs in complex cases. When there are 1:1 button
   mappings there are no issues, but the system provides no way to combine differen inputs. For example there is no way
   to easily implement "hold A and move the thumbstick" to switch weapons. These cases would be better handled by
   processing the raw inputs.
5. It couples high-level application logic to the low-level IO system. Actions are high-level concepts that are
   dependent on game logic and context to have any meaning, whereas the IO system is a low-level hardware interface that
   simply turns real world events into program signals. When two unrelated concepts are merged together it creates
   coupling and leads to a system which doesn't to either job correctly.

These issues are well known errors in application architecture and they should be avoided wherever possible. OmniXR
exists so you can still use OpenXR without taking on the architectural burden it imposes.

## Solution

TODO

## Usage

There are three ways to access the library:

1. Download the pre-built binaries.
2. Build the binaries from source.
3. Reference the source directly.

The pre-built binaries are distributed via Maven. Include `io.jackbradshaw:omnixr:0.0.1` in your dependencies to use
the latest version. Visit the [Maven Central Repository](https://search.maven.org/artifact/io.jackbradshaw/omnixr)
for detailed instructions and previous versions.

To build the binary from source:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository by running `git clone https://github.com/jack-bradshaw/monorepo && cd monorepo`
3. Invoke the build by running `bazel build //java/io/jackbradshaw/klu_full.deploy`
4. Collect the binary from the `monorepo/bazel-out` for inclusion in your project.

To reference the source directly:

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

4. Run `bazel sync` if you run into issues.

## Usage Guide

This section covers how to use the OmniXR library in your application. It's divided into:

- Getting access.
- Instantiating the utility.
- Configuring the utility.
- Consuming OpenXR events.

### Getting Access

There are three ways to access the library:

1. Download the pre-built binaries.
2. Build the binaries from source.
3. Reference the source directly.

Option 1 is the simplest and is what most engineers need. The pre-built binaries are available via Maven
at `io.jackbradshaw:omnixr:0.0.1` Visit
the [Maven Central Repository](https://search.maven.org/artifact/io.jackbradshaw/omnixr) previous versions.

For option 2:

1. [Install Bazel](https://docs.bazel.build/versions/main/install.html).
2. Clone the repository by running `git clone https://github.com/jack-bradshaw/monorepo && cd monorepo`
3. Invoke the build by running `bazel build //java/io/jackbradshaw/klu_full.deploy`
4. Collect the binary from the `monorepo/bazel-out` for inclusion in your project.

For option 3:

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

4. Run `bazel sync` if you run into issues.

### Using the library.

The library is configured using a `Config` object. You can create a config using the builder directly:

```
import io.jackbradshaw.omnixr.config.Config

Config
    .newBuilder()
    .setActionManifestDirectory("/home/myapp")
    .setActionManifestFilename("action_manifest.json")
    .build()
    
```

or by using the shortcut function:

```
import io.jackbradshaw.omnixr.config.config

config("/home/myapp", "action_manifest.json")
```

For convenience you can also access the configuration as a `File` via the utility function:

```
import io.jackbradshaw.omnixr.config.manifestFile

val config: Config = ("/home/myapp", "action_manifest.json")
val manifestFile: File = config.manifestFile()
```

Note that configs are protobufs. This means you can use all the goodness of Google's protobuf format with them, such as
easily serializing and deserializing them with virtually no boilerplate or overhead.

Once you have a config you need to instantiate the OmniXr object to get access to the various utilities. It can be
instantiated by calling the factory directly:

```
import io.jackbradshaw.omnixr.config.config
import io.jackbradshaw.omnixr.DaggerOmniXr
import io.jackbradshaw.omnixr.OmniXr

val omniXr: OmniXr = DaggerOmniXr.create(config("/home/myapp", "action_manifest.json"))
```

or by using the shortcut function:

```
import io.jackbradshaw.omnixr.config.config
import io.jackbradshaw.omnixr.omniXr

val omniXr: OmniXr = omniXr(config("/home/myapp", "action_manifest.json"))
```

From here you can access the various functions of the library, for example:

```
val omniXr = /* same as above */
omniXr.manifestInstaller()
```

You may have noticed that the OmniXr object is a Dagger Component. If you're using Dagger in your application you could
include OmniXr in your component dependencies to include the bindings in your graph. This would let you inject the
functionality directly into your classes. For example:

```
import io.jackbradshaw.omnixr.OmniXr
import io.jackbradshaw.omnixr.manifest.intaller.ManifestInstaller

@Component(dependencies = [OmniXr::class])
interface MyComponent() {
  /* your content here */
}

class MyClass @Inject internal constructor(
  val omniXrInstaller: ManifestInstaller
)
```

Most engineers will only need the `manifestInstaller()` class. This creates a manifest as described in the previous
sections and writes it config location. For example:

```
import io.jackbradshaw.omnixr.omniXr

val omniXr = omniXr(config("/home/myapp", "action_manifest.proto"))
omniXr.manifestInstaller().deployActionManifestFiles()
```

will create the manifest files in `"/home/myapp"`. You can then pass the manifest file to whichever OpenXR
framework you're using and observe the input events. For example, in the [jMonkey Engine](https://jmonkeyengine.org/)
you would do:

```
import io.jackbradshaw.omnixr.omniXr

val omniXr = omniXr(config("/home/myapp", "action_manifest.proto"))
omniXr.manifestInstaller().deployActionManifestFiles()
vrAppState.getVrInput().registerActionManifest(config.manifestFile().path, config.actionSetName)
```

From here you just need to observe the input events from the framework you're using and respond to them as you wish.
For example, in the jMonkey Engine you would do:

```
val omniXr = omniXr(config("/home/myapp", "action_manifest.proto"))
omniXr.manifestInstaller().deployActionManifestFiles()
vrAppState.getVrInput().registerActionManifest(config.manifestFile().path, config.actionSetName)
val openInventoryEvents = setOf(
  input()
  input()
)


```

That's it. Some of the exact steps will vary depending on which framework you're using, but the process is essentially
the same for all of them. Install the manifest with OmniXR, register the manifest with the framework, observe the
event in your code and combine them as you need.