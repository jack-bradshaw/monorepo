package first_party.dagger.tests

import org.junit.Test

/**
 * The tests runs the binary, which internally checks the Dagger generated code works properly.
 * Since the purpose of the test is to verify the Dagger generation was not broken by the JarJar
 * hack, this is sufficient.
 */
class BinaryMacroTest {

  @Test
  fun runBinary_doesNotFail() {
    BinaryMain.main(emptyArray())
  }
}
