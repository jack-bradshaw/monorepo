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

package dagger.hilt.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.HiltTestApplication;
import dagger.hilt.android.testsubpackage.PackagePrivateConstructorTestClasses.BaseActivity;
import dagger.hilt.android.testsubpackage.PackagePrivateConstructorTestClasses.BaseBroadcastReceiver;
import dagger.hilt.android.testsubpackage.PackagePrivateConstructorTestClasses.BaseFragment;
import dagger.hilt.android.testsubpackage.PackagePrivateConstructorTestClasses.BaseIntentService;
import dagger.hilt.android.testsubpackage.PackagePrivateConstructorTestClasses.BaseService;
import dagger.hilt.android.testsubpackage.PackagePrivateConstructorTestClasses.BaseView;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

/** Regression test for b/331280240. */
@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
// Robolectric requires Java9 to run API 29 and above, so use API 28 instead
@Config(sdk = Build.VERSION_CODES.P, application = HiltTestApplication.class)
public final class PackagePrivateConstructorTest {
  @Rule public final HiltAndroidRule rule = new HiltAndroidRule(this);

  @AndroidEntryPoint(BaseActivity.class)
  public static final class TestActivity extends Hilt_PackagePrivateConstructorTest_TestActivity {
  }

  @AndroidEntryPoint(BaseFragment.class)
  public static final class TestFragment extends Hilt_PackagePrivateConstructorTest_TestFragment {
  }

  @AndroidEntryPoint(BaseView.class)
  public static final class TestView extends Hilt_PackagePrivateConstructorTest_TestView {
      TestView(Context context) {
        super(context);
      }
  }

  @AndroidEntryPoint(BaseService.class)
  public static final class TestService extends Hilt_PackagePrivateConstructorTest_TestService {
    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }
  }

  @AndroidEntryPoint(BaseIntentService.class)
  public static final class TestIntentService
      extends Hilt_PackagePrivateConstructorTest_TestIntentService {
    public TestIntentService() {
      super("TestIntentServiceName");
    }

    @Override
    public void onHandleIntent(Intent intent) {}
  }

  @AndroidEntryPoint(BaseBroadcastReceiver.class)
  public static final class TestBroadcastReceiver
      extends Hilt_PackagePrivateConstructorTest_TestBroadcastReceiver {
  }

  @Before
  public void setup() {
    rule.inject();
  }

  // Technically all the tests need to do is check for compilation, but might as well make sure the
  // classes are usable
  @Test
  public void testActivityFragmentView() throws Exception {
    try (ActivityScenario<TestActivity> scenario = ActivityScenario.launch(TestActivity.class)) {
      scenario.onActivity(
          activity -> {
            TestFragment fragment = new TestFragment();
            activity.getSupportFragmentManager().beginTransaction().add(fragment, "").commitNow();
            TestView unused = new TestView(fragment.getContext());
          });
    }
  }

  @Test
  public void testServices() throws Exception {
    Robolectric.setupService(TestService.class);
    Robolectric.setupService(TestIntentService.class);
  }

  @Test
  public void testBroadcastReceiver() throws Exception {
    TestBroadcastReceiver testBroadcastReceiver = new TestBroadcastReceiver();
    Intent intent = new Intent();
    testBroadcastReceiver.onReceive(ApplicationProvider.getApplicationContext(), intent);
  }
}
