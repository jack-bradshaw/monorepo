# Testing Strategy: Decoupling KSP Logic from the Build System

## The Problem
We are building a Kotlin Symbol Processing (KSP) plugin (`Backstab`). To ensure its correctness and security, we have two distinct testing needs:
1.  **Source Inspection**: Verifying the *exact* generated code. This is crucial for catching subtle bugs or ensuring no malicious/unexpected code is injected into the production build.
2.  **Integration**: Verifying that the generated code *compiles* and *runs* correctly in the target environment (Dagger graph correctness).

## The Challenge
Bazel, our build system, is hermetic. It intentionally hides intermediate artifacts (like KSP-generated sources) inside the sandbox. "Piping" these generated files out to a test rule for inspection proved elusive because `rules_kotlin` does not fully reify these artifacts as public output groups.

We attempted to:
*   Use `output_group="srcjars"`: Empty.
*   Use internal flags like `_ksp_generated_src_jar`: Inaccessible.
We considered using `kotlin-compile-testing`, a library that allows testing annotation processors in-memory. However, we decided against it to avoid **bifurcating our compiler infrastructure**. Introducing a secondary compilation system within our tests would increase maintenance burden and creates a risk where the test environment drifts from the actual production Bazel build environment. We prefer tests that either use the *real* build system (Integration) or *no* build system (Unit).

## The Solution: decoupling
Instead of fighting the build system to inspect the *output* of the KSP task, we will refactor the *input* of the problem.

We will introduce a **Transformer** object.
*   **Role**: purely functional logic.
*   **Input**: A representation of the source file (or the KSP `KSClassDeclaration` purely as data).
*   **Output**: The String content of the file to be written.

### Architecture
*   **`BackstabProcessor` (The Glue)**: Handles the KSP environment, file creation, and logging. It parses the inputs and passes them to the Transformer.
*   **`BackstabTransformer` (The Logic)**: Contains all the code generation rules. It has *zero* dependencies on the build system or file system.

### Testing Plan
1.  **Unit Tests (`BackstabTransformerTest`)**: We perform "Source Inspection" here. We pass mock inputs to the Transformer and assert that the returned String matches our golden expectations. This runs as a standard `kt_jvm_test` with no Bazel magic required.
2.  **Integration Tests (`BackstabIntegrationTest`)**: We verify the "Runtime Behavior". We use the actual KSP plugin in a `kt_jvm_library`, let it generate code (invisible to us), and then write a test that *uses* that generated code (e.g., trying to inject a Dagger component). If the test compiles and runs, the integration is successful.
