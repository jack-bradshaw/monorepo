/*
 * Copyright (C) 2018 The Dagger Authors.
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

package dagger.functional.multibindings;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.truth.Truth.assertThat;

import dagger.Component;
import dagger.functional.multibindings.subpackage.BindsInaccessibleMapKeyModule;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

// b/73820357
@RunWith(JUnit4.class)
public class BindsInaccessibleMapKeyTest {
  @Component(modules = BindsInaccessibleMapKeyModule.class)
  interface TestComponent {
    Map<Class<?>, Object> mapWithAnInaccessibleMapKey();
  }

  @Test
  public void test() {
    Map<Class<?>, Object> map =
        DaggerBindsInaccessibleMapKeyTest_TestComponent.create().mapWithAnInaccessibleMapKey();
    assertThat(map).hasSize(1);
    assertThat(getOnlyElement(map.keySet()).getCanonicalName())
        .isEqualTo(
            "dagger.functional.multibindings.subpackage."
                + "BindsInaccessibleMapKeyModule.Inaccessible");
  }
}
