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

package dagger.hilt.android.processor.internal.androidentrypoint;

import androidx.room.compiler.processing.util.Source;
import dagger.hilt.android.testing.compile.HiltCompilerTests;
import dagger.testing.golden.GoldenFileRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ActivityGeneratorTest {
  @Rule public GoldenFileRule goldenFileRule = new GoldenFileRule();

  @Test
  public void generate_componentActivity() {
    Source myActivity =
        HiltCompilerTests.javaSource(
            "test.MyActivity",
            "package test;",
            "",
            "import androidx.activity.ComponentActivity;",
            "import dagger.hilt.android.AndroidEntryPoint;",
            "",
            "@AndroidEntryPoint(ComponentActivity.class)",
            "public class MyActivity extends Hilt_MyActivity {",
            "}");
    HiltCompilerTests.hiltCompiler(myActivity).compile(subject -> subject.hasErrorCount(0));
  }

  @Test
  public void generate_baseHiltComponentActivity() {
    Source baseActivity =
        HiltCompilerTests.javaSource(
            "test.BaseActivity",
            "package test;",
            "",
            "import androidx.activity.ComponentActivity;",
            "import dagger.hilt.android.AndroidEntryPoint;",
            "",
            "@AndroidEntryPoint(ComponentActivity.class)",
            "public class BaseActivity extends Hilt_BaseActivity {",
            "}");
    Source myActivity =
        HiltCompilerTests.javaSource(
            "test.MyActivity",
            "package test;",
            "",
            "import androidx.activity.ComponentActivity;",
            "import dagger.hilt.android.AndroidEntryPoint;",
            "",
            "@AndroidEntryPoint(BaseActivity.class)",
            "public class MyActivity extends Hilt_MyActivity {",
            "}");
    HiltCompilerTests.hiltCompiler(baseActivity, myActivity)
        .compile(subject -> subject.hasErrorCount(0));
  }

  @Test
  public void baseActivityHasFinalOnDestroy_fails() {
    Source myActivity =
        HiltCompilerTests.javaSource(
            "test.MyActivity",
            "package test;",
            "",
            "import dagger.hilt.android.AndroidEntryPoint;",
            "",
            "@AndroidEntryPoint(BaseActivity.class)",
            "public class MyActivity extends Hilt_MyActivity {}");
    Source baseActivity =
        HiltCompilerTests.javaSource(
            "test.BaseActivity",
            "package test;",
            "",
            "import androidx.activity.ComponentActivity;",
            "",
            "public class BaseActivity extends ComponentActivity {",
            "   @Override public final void onDestroy() {}",
            "}");
    HiltCompilerTests.hiltCompiler(myActivity, baseActivity)
        .compile(
            subject -> {
              // TODO(b/319663779) make error count consistent.
              // subject.hasErrorCount(1);
              subject.hasErrorContaining(
                  "Do not mark onDestroy as final in base Activity class, as Hilt needs to override"
                      + " it to clean up SavedStateHandle");
            });
  }

  @Test
  public void baseActivityHasFinalOnCreate_fails() {
    Source myActivity =
        HiltCompilerTests.javaSource(
            "test.MyActivity",
            "package test;",
            "",
            "import dagger.hilt.android.AndroidEntryPoint;",
            "",
            "@AndroidEntryPoint(BaseActivity.class)",
            "public class MyActivity extends Hilt_MyActivity {}");
    Source baseActivity =
        HiltCompilerTests.javaSource(
            "test.BaseActivity",
            "package test;",
            "",
            "import android.os.Bundle;",
            "import androidx.activity.ComponentActivity;",
            "",
            "public class BaseActivity extends ComponentActivity {",
            "   @Override public final void onCreate(Bundle bundle) {}",
            "}");
    HiltCompilerTests.hiltCompiler(myActivity, baseActivity)
        .compile(
            subject -> {
              // TODO(b/319663779) make error count consistent.
              // subject.hasErrorCount(1);
              subject.hasErrorContaining(
                  "Do not mark onCreate as final in base Activity class, as Hilt needs to override"
                      + " it to inject SavedStateHandle");
            });
  }

  @Test
  public void secondBaseActivityHasFinalOnCreate_fails() {
    Source myActivity =
        HiltCompilerTests.javaSource(
            "test.MyActivity",
            "package test;",
            "",
            "import dagger.hilt.android.AndroidEntryPoint;",
            "",
            "@AndroidEntryPoint(BaseActivity.class)",
            "public class MyActivity extends Hilt_MyActivity {}");
    Source baseActivity =
        HiltCompilerTests.javaSource(
            "test.BaseActivity",
            "package test;",
            "",
            "public class BaseActivity extends BaseActivity2 {}");
    Source baseActivity2 =
        HiltCompilerTests.javaSource(
            "test.BaseActivity2",
            "package test;",
            "",
            "import android.os.Bundle;",
            "import androidx.activity.ComponentActivity;",
            "",
            "public class BaseActivity2 extends ComponentActivity {",
            "   @Override public final void onCreate(Bundle bundle) {}",
            "}");
    HiltCompilerTests.hiltCompiler(myActivity, baseActivity, baseActivity2)
        .compile(
            subject -> {
              // TODO(b/319663779) make error count consistent.
              // subject.hasErrorCount(1);
              subject.hasErrorContaining(
                  "Do not mark onCreate as final in base Activity class, as Hilt needs to override"
                      + " it to inject SavedStateHandle");
            });
  }
}
