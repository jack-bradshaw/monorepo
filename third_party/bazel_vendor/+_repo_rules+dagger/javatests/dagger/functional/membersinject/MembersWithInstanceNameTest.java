/*
 * Copyright (C) 2020 The Dagger Authors.
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

package dagger.functional.membersinject;

import static com.google.common.truth.Truth.assertThat;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class MembersWithInstanceNameTest {

  static final class Foo {
    // Checks that member injection fields can use "instance" as a name (b/175818837).
    @Inject String instance;

    @Inject Foo() {}
  }

  static final class Bar {
    // Checks that member injection fields can use injecting a bound instance that was
    // named "instance" when bound. Note that the field name here doesn't matter as of
    // this writing, but name it "instance" anyway in case that changes.
    // https://github.com/google/dagger/issues/4352
    @Inject BoundInstance instance;

    @Inject Bar() {}
  }

  @Module
  interface TestModule {
    @Provides
    static String provideString() {
      return "test";
    }
  }

  public static final class BoundInstance {}

  @Component(modules = TestModule.class)
  interface TestComponent {
    Foo foo();

    Bar bar();

    @Component.Builder
    interface Builder {
      // As of writing, the method name is the one that matters, but name the
      // parameter the same anyway in case that changes.
      Builder instance(@BindsInstance BoundInstance instance);
      TestComponent build();
    }
  }

  @Component(modules = TestModule.class)
  interface TestComponentWithFactory {
    Foo foo();

    Bar bar();

    @Component.Factory
    interface Factory {
      // As of writing, the parameter name is the one that matters, but name the
      // method the same anyway in case that changes.
      TestComponentWithFactory instance(@BindsInstance BoundInstance instance);
    }
  }

  @Test
  public void testMemberWithInstanceName() {
    BoundInstance boundInstance = new BoundInstance();
    TestComponent component = DaggerMembersWithInstanceNameTest_TestComponent
        .builder().instance(boundInstance).build();
    Foo foo = component.foo();
    assertThat(foo).isNotNull();
    assertThat(foo.instance).isEqualTo("test");
    Bar bar = component.bar();
    assertThat(bar).isNotNull();
    assertThat(bar.instance).isSameInstanceAs(boundInstance);
  }

  @Test
  public void testMemberWithInstanceNameUsingFactory() {
    BoundInstance boundInstance = new BoundInstance();
    TestComponentWithFactory component = DaggerMembersWithInstanceNameTest_TestComponentWithFactory
        .factory().instance(boundInstance);
    Foo foo = component.foo();
    assertThat(foo).isNotNull();
    assertThat(foo.instance).isEqualTo("test");
    Bar bar = component.bar();
    assertThat(bar).isNotNull();
    assertThat(bar.instance).isSameInstanceAs(boundInstance);
  }
}
