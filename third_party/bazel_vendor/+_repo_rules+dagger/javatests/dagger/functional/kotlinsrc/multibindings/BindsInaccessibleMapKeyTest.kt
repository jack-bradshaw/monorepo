/*
 * Copyright (C) 2024 The Dagger Authors.
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

package dagger.functional.kotlinsrc.multibindings

import com.google.common.truth.Truth.assertThat
import dagger.Component
import dagger.functional.kotlinsrc.multibindings.subpackage.BindsInaccessibleMapKeyModule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

// b/73820357
@RunWith(JUnit4::class)
class BindsInaccessibleMapKeyTest {
  @Component(modules = [BindsInaccessibleMapKeyModule::class])
  internal interface TestComponent {
    fun mapWithAnInaccessibleMapKey(): Map<Class<*>, Any>
  }

  @Test
  fun test() {
    val map = DaggerBindsInaccessibleMapKeyTest_TestComponent.create().mapWithAnInaccessibleMapKey()
    assertThat(map).hasSize(1)
    assertThat(map.keys.single().canonicalName)
        .isEqualTo("dagger.functional.kotlinsrc.multibindings.subpackage.Inaccessible");
  }
}
