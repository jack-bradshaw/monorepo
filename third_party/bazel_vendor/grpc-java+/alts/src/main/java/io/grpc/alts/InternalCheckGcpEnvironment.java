/*
 * Copyright 2018 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc.alts;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.Internal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for checking if the system is running on Google Cloud Platform (GCP). This is intended for
 * usage internal to the gRPC team. If you *really* think you need to use this, contact the gRPC
 * team first.
 */
@Internal
public final class InternalCheckGcpEnvironment {

  private static final Logger logger =
      Logger.getLogger(InternalCheckGcpEnvironment.class.getName());
  private static final String WINDOWS_COMMAND = "powershell.exe";
  private static Boolean cachedResult = null;

  // Construct me not!
  private InternalCheckGcpEnvironment() {}

  /** Returns {@code true} if currently running on Google Cloud Platform (GCP). */
  public static synchronized boolean isOnGcp() {
    if (cachedResult == null) {
      cachedResult = isRunningOnGcp();
    }
    return cachedResult;
  }

  @VisibleForTesting
  static boolean checkProductNameOnLinux(BufferedReader reader) throws IOException {
    String name = reader.readLine().trim();
    return name.equals("Google") || name.equals("Google Compute Engine");
  }

  @VisibleForTesting
  static boolean checkBiosDataOnWindows(BufferedReader reader) throws IOException {
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.startsWith("Manufacturer")) {
        String name = line.substring(line.indexOf(':') + 1).trim();
        return name.equals("Google");
      }
    }
    return false;
  }

  private static boolean isRunningOnGcp() {
    String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
    try {
      if (osName.startsWith("linux")) {
        // Checks GCE residency on Linux platform.
        return checkProductNameOnLinux(
            Files.newBufferedReader(Paths.get("/sys/class/dmi/id/product_name"), UTF_8));
      } else if (osName.startsWith("windows")) {
        // Checks GCE residency on Windows platform.
        Process p =
            new ProcessBuilder()
                .command(WINDOWS_COMMAND, "Get-WmiObject", "-Class", "Win32_BIOS")
                .start();
        return checkBiosDataOnWindows(
            new BufferedReader(new InputStreamReader(p.getInputStream(), UTF_8)));
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "Fail to read platform information: ", e);
      return false;
    }
    // Platforms other than Linux and Windows are not supported.
    return false;
  }
}
