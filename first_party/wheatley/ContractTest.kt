package com.jackbradshaw.wheatley

/**
 * Contract for tests that verify a subject against an abstract behavioral contract.
 *
 * Defines the standard interface for abstract test classes that test implementations of a common
 * interface. Concrete test classes implement this contract to provide dependency injection setup
 * and infrastructure while abstract test classes define the test methods.
 *
 * @param T The type of the subject under test.
 * @param C The type of configuration passed to [setupSubject].
 */
interface ContractTest<T, C> {

  /**
   * Initializes the subject under test and its dependencies.
   *
   * Implementations typically create and invoke a Dagger component to inject dependencies into the
   * concrete test class. Must be called before [subject].
   *
   * @param config Configuration for the subject under test.
   */
  fun setupSubject(config: C)

  /**
   * Returns the subject under test.
   *
   * The same instance must be returned across multiple calls within a single test. Must be called
   * after [setupSubject].
   *
   * @return The subject under test.
   */
  fun subject(): T

  /**
   * Tears down the subject under test and releases resources.
   *
   * Implementations clean up resources allocated during [setupSubject]. Called after test
   * completion.
   */
  fun teardownSubject()
}
