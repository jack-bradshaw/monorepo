# KSP Model Tests

This directory contains the tests for the
[KSP model converters](/first_party/backstab/platforms/ksp/conversion/).

## Overview

The tests in this package deviate from the common JVM testing approach used throughout this
repository because the subject under test (the KSP model converters) depends on types that are only
available in a KSP processing context (e.g., `KSType`, `KSDeclaration`, and `KSAnnotation`). These
dependencies cannot be manually constructed or mocked without significant maintenance burden, due to
their inherent complexity, and operating on real objects is an engineering best practice; therefore,
the tests are implemented as custom KSP processors that turn test sources into KSP types, invoke the
subject under test on the KSP types, and make assertions on the result. Thhis approach essentially
runs the tests at build-time via KSP insted of uisn gJunit.

## Usage

To execute all tests:

```bash
bazel test //first_party/backstab/platforms/ksp/conversion/tests/...
```

## Benefits

This approach ensures:

- The tests are resilient to implementation detail changes in KSP and only break when the public KSP
  API changes or the conversion logic is defective.
- The tests validate the actual production behavior of the code under test. They use real KSP
  objects and run in a real KSP context.

This strategy exercises the subject under test in an environment that reflects production with
minimal mocking or faking, thereby mitigating the risk of bugs and reducing test maintenance
overhead.

## Tradeoffs

This approach runs test logic in KSP itself, meaning tests run during the Bazel Execution Phase, not
the test runtime, and failures will fail the build at build time. Furthermore, the lack of a
standardized testing framework has resulted in considerable boilerplate across the tests. These
tradeoffs are the cost of testing in an environment that closely mirrors production.

## Structure

The tests are organized into multiple packages, with one for each underlying converter (e.g.,
[backstabTarget.kt](/first_party/backstab/platforms/ksp/conversion/backstabTarget.kt) and
[type.kt](/first_party/backstab/platforms/ksp/conversion/type.kt) each have a package). Each test
suite contains numerous tests, with each test consisting of:

1. An Input File (`*input.kt`): Contains the Kotlin source code defining the symbols to be tested.
2. A Test Processor (`*test.kt`): A `SymbolProcessor` that resolves symbols from the input file,
   passes them to the converter, and performs assertions on the results.
3. A BUILD target: A test target which depends on the KSP target, effectively allowing the tests to
   be run with `bazel test` even though the test logic runs before test runtime.

To maintain test independence, each test case is implemented as a separate input file, KSP plugin,
and test target. This prevents cross-case interference and allows parallel execution. While this
increases package size, it ensures that a single failure does not prevent other tests from running.

## Future Work

Repeated boilerplate code in the processors indicates an opportunity to refactor the logic into a
standardized testing framework.

## Alternatives

The following alternatives were considered as ways to keep test logic in JUnit:

1. Mocking KSP types. Rejected due to extreme setup complexity (exceeding 40 lines of code per test
   case) and low utility (as mocks do not reliably represent production behavior).
2. Transporting symbols from KSP to a separate JUnit runtime. Rejected due to the complexity of
   serializing compiler models across JVM boundaries.
3. Running KSP within the test runtime via libraries such as
   [kotlin-compile-testing](https://github.com/tschuchortdev/kotlin-compile-testing). Rejected to
   ensure all compilation occurs via the primary Bazel build system (reduces maintenance burden).
