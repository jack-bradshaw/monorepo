package com.jackbradshaw.codestone.lifecycle.platforms.futures.converters

import com.google.common.truth.Truth.assertThat
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlin.reflect.typeOf
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PassThroughUniConverterTest {
  private val converter = PassThroughUniConverter()

  @Test
  fun convert_returnsInput() {
    val future = SettableFuture.create<Unit>()
    val source = object : Work<ListenableFuture<Unit>> {
      override val handle = future
      override val workType = typeOf<ListenableFuture<Unit>>()
    }

    val output = converter.convert(source)
    assertThat(output).isEqualTo(source)
    
    future.set(Unit)
  }
}
