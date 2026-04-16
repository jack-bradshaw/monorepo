# Testing

This directory contains providers/processors for use in testing Kale. They exist purely to collect
data during tests for making assertions, and are therefore private to Kale (i.e. not intended for
external use). They are not tested because they are needed to test Kale, which is testing
infrastructure for KSP, so testing them would require the very infrastructure they exist to test.
