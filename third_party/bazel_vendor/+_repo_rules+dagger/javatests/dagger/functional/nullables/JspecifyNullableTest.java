/*
 * Copyright (C) 2023 The Dagger Authors.
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

package dagger.functional.nullables;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import javax.inject.Provider;
import org.jspecify.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class JspecifyNullableTest {
  @Component(modules = MyModule.class, dependencies = ComponentDependency.class)
  interface MyComponent {
    Integer getInt();
    InnerType getInnerType();
        Provider<Dependency> getDependencyProvider();
  }

  interface Dependency {}

  interface InnerType {}

  @Module
  static class MyModule {
    private final Integer integer;
    private final InnerType innerType;

    MyModule(Integer integer, InnerType innerType) {
      this.integer = integer;
      this.innerType = innerType;
    }

    @Provides
    @Nullable Integer provideInt() {
      return integer;
    }

    @Provides
    @Nullable InnerType provideInnerType() {
      return innerType;
    }
  }

  @Component(modules = DependencyModule.class)
  interface ComponentDependency {
    @Nullable Dependency dependency();
  }

  @Module
  static class DependencyModule {
    private final Dependency dependency;

    DependencyModule(Dependency dependency) {
      this.dependency = dependency;
    }

    @Provides
    @Nullable Dependency provideDependency() {
      return dependency;
    }
  }

  @Test
  public void testWithValue() {
    MyComponent component = DaggerJspecifyNullableTest_MyComponent.builder()
        .myModule(new MyModule(15, new InnerType() {}))
        .componentDependency(
            DaggerJspecifyNullableTest_ComponentDependency.builder()
                .dependencyModule(new DependencyModule(new Dependency() {})).build())
        .build();
    assertThat(component.getInt()).isEqualTo(15);
    assertThat(component.getInnerType()).isNotNull();
    assertThat(component.getDependencyProvider().get()).isNotNull();
  }

  @Test
  public void testWithNull() {
    MyComponent component = DaggerJspecifyNullableTest_MyComponent.builder()
        .myModule(new MyModule(null, null))
        .componentDependency(
            DaggerJspecifyNullableTest_ComponentDependency.builder()
                .dependencyModule(new DependencyModule(null)).build())
        .build();
    NullPointerException expectedException =
        assertThrows(NullPointerException.class, component::getInt);
    assertThat(expectedException)
        .hasMessageThat()
        .contains("Cannot return null from a non-@Nullable @Provides method");
    NullPointerException expectedException2 =
        assertThrows(NullPointerException.class, component::getInnerType);
    assertThat(expectedException2)
        .hasMessageThat()
        .contains("Cannot return null from a non-@Nullable @Provides method");
    NullPointerException expectedException3 =
        assertThrows(NullPointerException.class, () -> component.getDependencyProvider().get());
    assertThat(expectedException3)
        .hasMessageThat()
        .contains("Cannot return null from a non-@Nullable @Provides method");
  }
}
