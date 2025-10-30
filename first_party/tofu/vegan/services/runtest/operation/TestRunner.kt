package services.runtest.operation

import dataaccess.unpacked.UnpackedFirmTofu

interface TestRunner {
  fun runTests(firmTofu: UnpackedFirmTofu): TestResults
}

class TestResults(val results: String)
