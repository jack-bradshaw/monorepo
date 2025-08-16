// Copyright 2017 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.android;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.common.collect.ImmutableList;
import com.google.devtools.build.android.aapt2.Aapt2Exception;
import com.google.devtools.build.android.resources.JavaIdentifierValidator.InvalidJavaIdentifier;
import com.google.devtools.build.lib.worker.ProtoWorkerMessageProcessor;
import com.google.devtools.build.lib.worker.WorkRequestHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides an entry point for the resource processing stages.
 *
 * <p>A single entry point simplifies the build tool binary configuration and keeps the size of tool
 * jar small, as opposed to multiple tools for each process step. It also makes it easy to prototype
 * changes in the resource processing system.
 *
 * <pre>
 * Example Usage:
 *   java/com/google/devtools/build/android/ResourceProcessorBusyBox\
 *      --tool AAPT2_PACKAGE\
 *      --sdkRoot path/to/sdk\
 *      --aapt path/to/sdk/aapt\
 *      --adb path/to/sdk/adb\
 *      --zipAlign path/to/sdk/zipAlign\
 *      --androidJar path/to/sdk/androidJar\
 *      --manifestOutput path/to/manifest\
 *      --primaryData path/to/resources:path/to/assets:path/to/manifest\
 *      --data p/t/res1:p/t/assets1:p/t/1/AndroidManifest.xml:p/t/1/R.txt:symbols,\
 *             p/t/res2:p/t/assets2:p/t/2/AndroidManifest.xml:p/t/2/R.txt:symbols\
 *      --packagePath path/to/write/archive.ap_\
 *      --srcJarOutput path/to/write/archive.srcjar
 * </pre>
 */
public class ResourceProcessorBusyBox {
  static enum Tool {
    GENERATE_BINARY_R() {
      @Override
      void call(String[] args) throws Exception {
        RClassGeneratorAction.main(args);
      }
    },
    PARSE() {
      @Override
      void call(String[] args) throws Exception {
        AndroidResourceParsingAction.main(args);
      }
    },
    MERGE_COMPILED() {
      @Override
      void call(String[] args) throws Exception {
        AndroidCompiledResourceMergingAction.main(args);
      }
    },
    GENERATE_AAR() {
      @Override
      void call(String[] args) throws Exception {
        AarGeneratorAction.main(args);
      }
    },
    MERGE_MANIFEST() {
      @Override
      void call(String[] args) throws Exception {
        ManifestMergerAction.main(args);
      }
    },
    COMPILE_LIBRARY_RESOURCES() {
      @Override
      void call(String[] args) throws Exception {
        CompileLibraryResourcesAction.main(args);
      }
    },
    LINK_STATIC_LIBRARY() {
      @Override
      void call(String[] args) throws Exception {
        ValidateAndLinkResourcesAction.main(args);
      }
    },
    AAPT2_PACKAGE() {
      @Override
      void call(String[] args) throws Exception {
        Aapt2ResourcePackagingAction.main(args);
      }
    },
    SHRINK_AAPT2() {
      @Override
      void call(String[] args) throws Exception {
        Aapt2ResourceShrinkingAction.main(args);
      }
    },
    AAPT2_OPTIMIZE() {
      @Override
      void call(String[] args) throws Exception {
        Aapt2OptimizeAction.main(args);
      }
    },
    CONVERT_RESOURCE_ZIP_TO_APK() {
      @Override
      void call(String[] args) throws Exception {
        ConvertResourceZipToApkAction.main(args);
      }
    },
    MERGE_ASSETS() {
      @Override
      void call(String[] args) throws Exception {
        AndroidAssetMergingAction.main(args);
      }
    },
    PROCESS_DATABINDING {
      @Override
      void call(String[] args) throws Exception {
        AndroidDataBindingProcessingAction.main(args);
      }
    },
    GEN_BASE_CLASSES {
      @Override
      void call(String[] args) throws Exception {
        GenerateDatabindingBaseClassesAction.main(args);
      }
    };

    abstract void call(String[] args) throws Exception;
  }

  private static final Logger logger = Logger.getLogger(ResourceProcessorBusyBox.class.getName());
  private static final Properties properties = loadSiteCustomizations();

  /** Flag specifications for this action. */
  public static final class Options extends OptionsBaseWithResidue {
    @Parameter(
        names = "--tool",
        description =
            "The processing tool to execute. "
                + "Valid tools: GENERATE_BINARY_R, PARSE, "
                + "GENERATE_AAR, MERGE_MANIFEST, COMPILE_LIBRARY_RESOURCES, "
                + "LINK_STATIC_LIBRARY, AAPT2_PACKAGE, SHRINK_AAPT2, MERGE_COMPILED.")
    public Tool tool;
  }

  public static void main(String[] args) throws Exception {
    // It's cheaper and cleaner to detect for a single flag to start worker mode without having to
    // initialize Options/OptionsParser here. This keeps the processRequest interface minimal and
    // minimizes moving option state between these methods.
    if (args.length == 1 && args[0].equals("--persistent_worker")) {
      System.exit(runPersistentWorker());
    } else {
      System.exit(processRequest(Arrays.asList(args)));
    }
  }

  private static int runPersistentWorker() throws Exception {
    PrintStream realStdErr = System.err;

    try {
      WorkRequestHandler workerHandler =
          new WorkRequestHandler.WorkRequestHandlerBuilder(
                  new WorkRequestHandler.WorkRequestCallback(
                      (request, pw) -> processRequest(request.getArgumentsList(), pw)),
                  realStdErr,
                  new ProtoWorkerMessageProcessor(System.in, System.out))
              .setCpuUsageBeforeGc(Duration.ofSeconds(10))
              .build();
      workerHandler.processRequests();
    } catch (IOException e) {
      logger.severe(e.getMessage());
      e.printStackTrace(realStdErr);
      return 1;
    }
    return 0;
  }

  /**
   * Processes the request for the given args and writes the captured byte array buffer to the
   * WorkRequestHandler print writer.
   */
  private static int processRequest(List<String> args, PrintWriter pw) {
    int exitCode;
    try {
      // Process the actual request and grab the exit code
      exitCode = processRequest(args);
    } catch (Exception e) {
      e.printStackTrace(pw);
      exitCode = 1;
    }

    return exitCode;
  }

  private static int processRequest(List<String> args) throws Exception {
    Options options = new Options();
    try {
      JCommander jc = new JCommander(options);
      // Handle arg files (start with "@")
      // NOTE: JCommander handles this automatically, but enabling Main Parameter (aka "residue")
      // collection seems to disable this behavior. In case that behavior changes in the future,
      // we'll want to _always_ disable this, since JCommander's handling of escaped quotes in arg
      // files does not interact well with how the sub-tools handle them.
      jc.setExpandAtSign(false);
      if (args.size() == 1 && args.get(0).startsWith("@")) {
        // Use CompatShellQuotedParamsFilePreProcessor to handle the arg file.
        FileSystem fs = FileSystems.getDefault();
        Path argFile = fs.getPath(args.get(0).substring(1));
        CompatShellQuotedParamsFilePreProcessor paramsFilePreProcessor =
            new CompatShellQuotedParamsFilePreProcessor(fs);
        args = paramsFilePreProcessor.preProcess(ImmutableList.of("@" + argFile));
      }
      jc.parse(args.toArray(new String[0]));
      List<String> residue = options.getResidue();

      options.tool.call(residue.toArray(new String[0]));
    } catch (UserException e) {
      // UserException is for exceptions that shouldn't have stack traces recorded, including
      // AndroidDataMerger.MergeConflictException.
      logger.log(Level.SEVERE, e.getMessage());
      return 1;
    } catch (ParameterException // thrown by CompatShellQuotedParamsFilePreProcessor and JCommander.
        | IOException
        | Aapt2Exception
        | InvalidJavaIdentifier e) {
      logSuppressed(e);
      throw e;
    } catch (Exception e) {
      // TODO(jingwen): consider just removing this block.
      logger.log(Level.SEVERE, "Error during processing", e);
      throw e;
    }
    return 0;
  }

  private static void logSuppressed(Throwable e) {
    Arrays.stream(e.getSuppressed()).map(Throwable::getMessage).forEach(logger::severe);
  }

  /** Returns a flag from {@code rpbb.properties}. */
  public static boolean getProperty(String name) {
    return Boolean.parseBoolean(properties.getProperty(name, "false"));
  }

  private static Properties loadSiteCustomizations() {
    Properties properties = new Properties();
    try (InputStream propertiesInput =
        ResourceProcessorBusyBox.class.getResourceAsStream("rpbb.properties")) {
      if (propertiesInput != null) {
        properties.load(propertiesInput);
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error loading site customizations", e);
    }
    return properties;
  }
}
