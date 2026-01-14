package first_party.build_tests.dagger;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import javax.inject.Inject;

public class TestComponent {

  static class Data {
    @Inject
    Data() {}
  }

  @Module
  static class TestModule {
    @Provides
    static String provideString() {
      return "Hello";
    }
  }

  @Component(modules = TestModule.class)
  interface MyComponent {
    Data data();

    String string();
  }
}
