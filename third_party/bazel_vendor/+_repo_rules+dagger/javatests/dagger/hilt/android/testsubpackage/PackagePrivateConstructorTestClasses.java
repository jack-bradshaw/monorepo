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

package dagger.hilt.android.testsubpackage;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public final class PackagePrivateConstructorTestClasses {

  public abstract static class BaseActivity extends FragmentActivity {
    public BaseActivity() {}

    BaseActivity(int unused) {}
  }

  public abstract static class BaseFragment extends Fragment {
    public BaseFragment() {}

    BaseFragment(int unused) {}
  }

  public abstract static class BaseView extends LinearLayout {
    public BaseView(Context context) {
      super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
      super(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
    }

    BaseView(Context context, int unused) {
      super(context);
    }
  }

  public abstract static class BaseService extends Service {
    public BaseService() {}

    BaseService(int unused) {}
  }

  public abstract static class BaseIntentService extends IntentService {
    public BaseIntentService(String name) {
      super(name);
    }

    BaseIntentService(String name, int unused) {
      super(name);
    }
  }

  public abstract static class BaseBroadcastReceiver extends BroadcastReceiver {
    public BaseBroadcastReceiver() {}

    BaseBroadcastReceiver(int unused) {}
  }

}
