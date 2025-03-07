package io.jackbradshaw.codestone.sustainment.omniconverter

import com.google.common.util.concurrent.ListenableFuture
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.uniconverter.UniConverter
import io.jackbradshaw.codestone.sustainment.omnisustainer.factoring.ListenableFuturePlatform
import io.jackbradshaw.codestone.sustainment.operations.StartStopOperation
import io.jackbradshaw.codestone.sustainment.startstop.StartStopSimplex
import java.lang.IllegalStateException
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.test.assertFailsWith
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OmniConverterSimplexTest {
  private val source =
      object : StartStopOperation() {
        override fun work() = StartStopSimplex()
      }

  @Test
  fun convert_missingMatchingConverter_throwsException() {
    val converter =
        OmniConverterSimplex<Operation<ListenableFuture<Unit>>>(
            typeOf<ListenableFuture<Unit>>(), mapOf())
    assertFailsWith(IllegalStateException::class) { converter.convert(source) }
  }

  @Test
  fun convert_hasMatchingConverter_performsConversion() {
    val converter =
        OmniConverterSimplex<Operation<ListenableFuture<Unit>>>(
            typeOf<ListenableFuture<Unit>>(),
            mapOf(
                ListenableFuturePlatform().forwardsUniConverter()
                    as Pair<Pair<KType, KType>, UniConverter<Operation<*>, Operation<*>>>))
    val out: Operation<ListenableFuture<Unit>> = converter.convert(source)
    // No assertion needed.
  }
}
