package com.jackbradshaw.codestone.lifecycle.conversion.uniconverter

interface UniConverter<in I, out O> {
  fun convert(input: I): O
}