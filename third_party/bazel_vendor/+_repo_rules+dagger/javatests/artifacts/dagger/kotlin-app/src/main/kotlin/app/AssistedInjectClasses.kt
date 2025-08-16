/*
 * Copyright (C) 2021 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app

import dagger.Component
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

// This is a regression test for https://github.com/google/dagger/issues/2309
/** A simple, skeletal application that defines an assisted inject binding. */
class AssistedInjectClasses {
  @Component
  interface MyComponent {
    fun fooFactory(): FooFactory

    fun parameterizedFooFactory(): ParameterizedFooFactory<Bar, String>
  }

  class Bar @Inject constructor()

  class Foo @AssistedInject constructor(val bar: Bar, @Assisted val assistedStr: String)

  @AssistedFactory
  interface FooFactory {
    fun create(str: String): Foo
  }

  class ParameterizedFoo<T1, T2>
  @AssistedInject
  constructor(val t1: T1, @Assisted val assistedT2: T2)

  @AssistedFactory
  interface ParameterizedFooFactory<T1, T2> {
    fun create(t2: T2): ParameterizedFoo<T1, T2>
  }
}
