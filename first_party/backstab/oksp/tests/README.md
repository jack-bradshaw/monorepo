# Tests

Documentation for Backstab KSP tests.

## Structure

The tests are organized into suites based on the dimension of the system being verified:

- **BindingInternalization**: Verifies top-down propagation (Propagation). Ensures that bindings
  passed into the aggregate root builder successfully reachleaf elements.
- **BindingExternalization**: Verifies bottom-up discovery (Discovery). Ensures that bindings
  provided at the leaf level are correctly aggregated and accessible at the root.
- **Topologies**: Validates complex component dependency structures (Deep, Wide, Diamond, Broad).
- **Instantiators**: Verifies construction patterns (Builder, Factory, Implicit).
- **Misc**: Covers edge cases like manually provided components and nested types.

## Verification Approaches

The suite employs two distinct verification strategies:

### Tracing Tests

These tests verify graph integrity by tracing a specific object instance from its origin to its
destination using reference equality.

- **Mechanism**: Modules provide static pre-wired instances (e.g., `val instance = Foo()`). Tests
  assert that the retrieved object is exactly that instance using `isSameInstanceAs`.
- **Coverage**: `Topologies`, `BindingInternalization`, `BindingExternalization`, `Instantiators`.
- **Goal**: Validates that Backstab correctly wires and shares instances across complex or
  multi-root graphs.

### Compilation Tests

These tests verify that non-standard or structurally complex graphs can be successfully processed
and integrated by Backstab and Dagger.

- **Mechanism**: The test attempts to build the aggregate component and access the exposed types.
  The primary validation occurs during the compilation phase; if the graph is invalid, code
  generation will fail.
- **Coverage**: `Exposures`, `Misc` (Structural Nesting).
- **Goal**: Ensures that specific topological structures (like nested components or multiple
  transitive exposures) don't cause binding conflicts or missing dependency errors.
