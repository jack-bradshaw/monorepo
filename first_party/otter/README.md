# Otter

Otter is an experimental game engine providing an asynchronous Kotlin/protobuf wrapper around the
jMonkey Engine, with a focus on data-oriented design for VR/OpenXR applications.

## Status

This package is experimental and unreleased; consequently, the API is unstable and subject to change
without notice. Development was discontinued due to performance degradation; consequently, future
game engine efforts will focus on a C++ based implementation. The code is retained for posterity.

## Architecture

Otter provides an asynchronous Kotlin interface to the jMonkey Engine with the following design
goals:

- Async-first: Wraps jMonkey's imperative APIs with coroutine-based interfaces for non-blocking
  operations.
- Data-oriented: Uses Protocol Buffers for scene representations, physics state, and configuration.
- Dagger-based: Dependency injection throughout for testability and modularity.
- VR-ready: OpenXR manifest generation for direct controller input (i.e. without openxr profiles).

## Components

- Engine Core: Exposes jMonkey subsystems (rendering, physics, audio, input) through extraction
  interfaces.
- Scene Management: Hierarchical scene graph with items, primitives, and stages.
- Physics: Collision detection and rigid body dynamics via Bullet Physics.
- OpenXR: Manifest generation for standard VR controller profiles (Vive, Oculus, Index, etc.).
- Timing: Separate clocks for physics and rendering loops.
- Math: Vector, quaternion, and interpolation utilities.

The engine separates physics and rendering dispatchers, allowing asynchronous updates to run
independently on dedicated coroutine contexts.

## Usage

The demo provides a working example of the engine. It launches a level containing a swarm of
physics-enabled cubes that interact in a 3D space. The demo showcases:

- Dagger component composition.
- Scene construction and physics integration.
- Material application and rendering.
- General engine approach.

Run the demo with `bazel run //first_party/otter/demo` and view the [demo](/first_party/otter/demo)
package for further details.

## Discontinuation

Development was discontinued due to performance issues that emerged as the abstraction layers
accumulated overhead beyond acceptable limits for real-time rendering. Future game engine work will
pursue a C++-based architecture with direct access to rendering and physics APIs.
