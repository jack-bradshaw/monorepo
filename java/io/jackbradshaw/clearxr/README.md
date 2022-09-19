# CleanXR

[OpenXR](https://registry.khronos.org/OpenXR/specs/1.0/pdf/xrspec.pdf) without the architectural burden.

OpenXR does not allow applications to access raw input signals from controllers. Instead it provides an [action manifest
system](https://github.com/ValveSoftware/openvr/wiki/Action-manifest) which hides the raw input signals behind
semantically meaningful actions such as "open inventory" and "fire weapon". While the simplicity of this system can be
appealing, by coupling low-level IO handling to high-level logic it creates endless problems for code health which are
best avoided up front. To this end, it would be better if applications could handle the raw inputs using standard
programming techniques instead of being forced to use the action manifest system. CleanXR makes this simple.

CleanXR essentially provides a complete bypass for the action manifest system. It contains a utility which generates
a manifest containing an action for each input, so you can essentially observe each input as an action. You simply need
to invoke the generation logic, pass the generated file to OpenXR, and listen to the actions as if they were the raw
inputs. Follow the tutorial below for a practical usage guide.

## Tutorial

This tutorial is divided into three sections:

1. Getting access: How to import the library into your project.
2. Core usage guide: How to use the core features of the library.
3. Supplementary usage guide: How to use the the supplementary features of the library.

If you still have questions after reading the tutorial, please email
jack@jackbradshaw.io. [G: consider business address instead of main personal]

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

The core usage guide covers the basics of using CleanXR.

Start by instantiating the CleanXR object:

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
you need to change this, read the advanced usage guide for details.

Next, pass the manifest to the OpenXR framework you're using. The details of this step depend on your framework of
choice. Using the JMonkey Engine for example:

```
vrAppState.getVrInput().registerActionManifest(cleanXr.config().actionManifestFile(), cleanXr.config().actionSetName)
```

Finally listen for input events and use CleanXR to turn them into useful objects. Again, the details of this step depend
on your framework of choice. Using the JMonkey Engine for example:

```
vrAppState.getVrInput().registerAnalogListener(input -> {
  val input = cleanXr.manifestEncoder().decodeInput(input)
  TODO() // Your input handling logic
})
```

Now you can listen for all inputs directly and handle them using whichever programming technique you like. No need for
complex manifest files, mappings, and action sets. Just input events and standard logic.

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

#### Integrating With Dagger

The CleanXR object is actually a [Dagger](https://github.com/google/dagger) component. This means other Dagger
components can use it as a component dependency and inject the CleanXR classes directly. For example:

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

#### OpenXR Model

OpenXR contains a rich model of the OpenXR input/output system which may be useful to you. See the
[io/jackbradshaw/cleanxr/model](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/cleanxr/model)
package and the
[io/jackbradshaw/cleanxr/standard](https://github.com/jack-bradshaw/monorepo/tree/main/java/io/jackbradshaw/cleanxr/standard)
package for details.

## Support

Please include [#clearxr](https://github.com/jack-bradshaw/monorepo/issues?q=is%3Aissue+is%3Aopen+%23clearxr+) in all
related bugs.