class RandomFake : Random {

  var nextIntValue = 0

  override fun nextInt() = nextIntValue
}
