package io.matthewbradshaw.gmonkey.octavius

import io.matthewbradshaw.gmonkey.octavius.engine.Paradigm

fun otto(paradigm: Paradigm): Octavius = DaggerOctavius.builder().paradigm(paradigm).build()