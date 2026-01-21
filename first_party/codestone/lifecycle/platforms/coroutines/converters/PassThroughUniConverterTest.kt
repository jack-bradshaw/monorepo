package com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters

import com.google.common.truth.Truth.assertThat

import kotlin.reflect.typeOf
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PassThroughUniConverterTest {

  private val converter = PassThroughUniConverter()

  @Test
  fun convert_returnsInput() {
    val job = GlobalScope.launch { }
    val source = object : Work<Job> {
      override val handle = job
      override val workType = typeOf<Job>()
    }

    val output = converter.convert(source)
    assertThat(output).isEqualTo(source)
  }
}
