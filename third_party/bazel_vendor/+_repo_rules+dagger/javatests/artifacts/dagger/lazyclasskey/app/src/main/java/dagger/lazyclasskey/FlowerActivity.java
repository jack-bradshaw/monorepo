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

package dagger.lazyclasskey;

import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import java.util.Locale;
import java.util.Map;

/** Displays flower price information. */
public final class FlowerActivity extends ComponentActivity {
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Map<Class<?>, Integer> flowerPrices = DaggerFlowerComponent.create().getFlowerMap();
    setContentView(R.layout.flower_activity);
    if (!flowerPrices.containsKey(Rose.class)) {
      throw new IllegalStateException("Rose price not found");
    }
    if (!flowerPrices.containsKey(Lily.class)) {
      throw new IllegalStateException("Lily price not found");
    }
    ((TextView) findViewById(R.id.flower_info))
        .setText(
            String.format(
                Locale.US,
                "%s : %d dollar, %s : %d dollar",
                Lily.class.getSimpleName(),
                flowerPrices.get(Lily.class),
                Rose.class.getSimpleName(),
                flowerPrices.get(Rose.class)));
  }

  class ProguardClassNames {
    Rose rose;
    Lily lily;
  }
}
