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

package dagger.hilt.processor.internal.root;

import androidx.room.compiler.processing.util.Source;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.truth.StringSubject;
import dagger.hilt.android.testing.compile.HiltCompilerTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

// This test makes sure we don't regress the formatting in the components file.
@RunWith(JUnit4.class)
public final class RootFileFormatterTest {
  private static final Joiner JOINER = Joiner.on("\n");

  @Test
  public void testProdComponents() {
    HiltCompilerTests.hiltCompiler(
            HiltCompilerTests.javaSource(
                "test.TestApplication",
                "package test;",
                "",
                "import android.app.Application;",
                "import dagger.hilt.android.HiltAndroidApp;",
                "",
                "@HiltAndroidApp(Application.class)",
                "public class TestApplication extends Hilt_TestApplication {}"),
            entryPoint("SingletonComponent", "EntryPoint1"),
            entryPoint("SingletonComponent", "EntryPoint2"),
            entryPoint("ActivityComponent", "EntryPoint3"),
            entryPoint("ActivityComponent", "EntryPoint4"))
        .compile(
            subject -> {
              subject.hasErrorCount(0);
              StringSubject stringSubject =
                  subject.generatedSourceFileWithPath("test/TestApplication_HiltComponents.java");
              stringSubject.contains(
                  JOINER.join(
                      "  public abstract static class SingletonC implements"
                         + " HiltWrapper_ActivityRetainedComponentManager"
                         + "_ActivityRetainedComponentBuilderEntryPoint,",
                      "      ServiceComponentManager.ServiceComponentBuilderEntryPoint,",
                      "      SingletonComponent,",
                      "      GeneratedComponent,",
                      "      EntryPoint1,",
                      "      EntryPoint2,",
                      "      TestApplication_GeneratedInjector {"));
              stringSubject.contains(
                  JOINER.join(
                      "  public abstract static class ActivityC implements ActivityComponent,",
                      "      DefaultViewModelFactories.ActivityEntryPoint,",
                      "      HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint,",
                      "      FragmentComponentManager.FragmentComponentBuilderEntryPoint,",
                      "      ViewComponentManager.ViewComponentBuilderEntryPoint,",
                      "      GeneratedComponent,",
                      "      EntryPoint3,",
                      "      EntryPoint4 {"));
            });
  }

  @Test
  public void testTestComponents() {
    HiltCompilerTests.hiltCompiler(
            HiltCompilerTests.javaSource(
                "test.MyTest",
                "package test;",
                "",
                "import dagger.hilt.android.testing.HiltAndroidTest;",
                "",
                "@HiltAndroidTest",
                "public class MyTest {}"),
            entryPoint("SingletonComponent", "EntryPoint1"),
            entryPoint("SingletonComponent", "EntryPoint2"),
            entryPoint("ActivityComponent", "EntryPoint3"),
            entryPoint("ActivityComponent", "EntryPoint4"))
        .withProcessorOptions(
            ImmutableMap.of("dagger.hilt.shareTestComponents", Boolean.toString(false)))
        .compile(
            subject -> {
              subject.hasErrorCount(0);
              StringSubject stringSubject =
                  subject.generatedSourceFileWithPath("test/MyTest_HiltComponents.java");
              stringSubject.contains(
                  JOINER.join(
                      "  public abstract static class SingletonC implements"
                      + " HiltWrapper_ActivityRetainedComponentManager"
                      + "_ActivityRetainedComponentBuilderEntryPoint,",
                      "      ServiceComponentManager.ServiceComponentBuilderEntryPoint,",
                      "      SingletonComponent,",
                      "      TestSingletonComponent,",
                      "      EntryPoint1,",
                      "      EntryPoint2,",
                      "      MyTest_GeneratedInjector {"));

              stringSubject.contains(
                  JOINER.join(
                      "  public abstract static class ActivityC implements ActivityComponent,",
                      "      DefaultViewModelFactories.ActivityEntryPoint,",
                      "      HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint,",
                      "      FragmentComponentManager.FragmentComponentBuilderEntryPoint,",
                      "      ViewComponentManager.ViewComponentBuilderEntryPoint,",
                      "      GeneratedComponent,",
                      "      EntryPoint3,",
                      "      EntryPoint4 {"));
            });
  }

  @Test
  public void testSharedTestComponents() {
    HiltCompilerTests.hiltCompiler(
            HiltCompilerTests.javaSource(
                "test.MyTest",
                "package test;",
                "",
                "import dagger.hilt.android.testing.HiltAndroidTest;",
                "",
                "@HiltAndroidTest",
                "public class MyTest {}"),
            entryPoint("SingletonComponent", "EntryPoint1"))
        .withProcessorOptions(
            ImmutableMap.of("dagger.hilt.shareTestComponents", Boolean.toString(true)))
        .compile(
            subject -> {
              subject.hasErrorCount(0);
              StringSubject stringSubject =
                  subject.generatedSourceFileWithPath(
                      "dagger/hilt/android/internal/testing/root/Default_HiltComponents.java");
              stringSubject.contains(
                  JOINER.join(
                      "  public abstract static class SingletonC implements"
                      + " HiltWrapper_ActivityRetainedComponentManager"
                      + "_ActivityRetainedComponentBuilderEntryPoint,",
                      "      ServiceComponentManager.ServiceComponentBuilderEntryPoint,",
                      "      SingletonComponent,",
                      "      TestSingletonComponent,",
                      "      EntryPoint1,",
                      "      MyTest_GeneratedInjector {"));
            });
  }

  private static Source entryPoint(String component, String name) {
    return HiltCompilerTests.javaSource(
        "test." + name,
        "package test;",
        "",
        "import dagger.hilt.EntryPoint;",
        "import dagger.hilt.InstallIn;",
        component.equals("SingletonComponent")
            ? "import dagger.hilt.components.SingletonComponent;"
            : "import dagger.hilt.android.components." + component + ";",
        "",
        "@EntryPoint",
        "@InstallIn(" + component + ".class)",
        "public interface " + name + " {}");
  }
}
