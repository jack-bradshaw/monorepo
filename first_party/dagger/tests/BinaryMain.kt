package first_party.dagger.tests

object BinaryMain {
  @JvmStatic
  fun main(args: Array<String>) {
    println("BinaryMain running")

    val component =
        DaggerTestComponent.builder()
            .upstreamComponent(
                object : UpstreamComponent {
                  override fun provideUpstreamString() = "Upstream"
                })
            .build()

    val result = component.getString()

    if (result != "Hello Dagger") {
      throw RuntimeException("Expected 'Hello Dagger' but got '$result'")
    }
  }
}
