/*
 * Copyright (C) 2016 The Dagger Authors.
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

package dagger.internal.codegen;

import static com.google.common.truth.Truth.assertAbout;

import androidx.room.compiler.processing.util.Source;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.Subject;
import com.google.common.truth.Truth;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.Module;
import dagger.producers.ProducerModule;
import dagger.testing.compile.CompilerTests;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/** A {@link Truth} subject for testing Dagger module methods. */
final class DaggerModuleMethodSubject extends Subject {

  /** A {@link Truth} subject factory for testing Dagger module methods. */
  static final class Factory implements Subject.Factory<DaggerModuleMethodSubject, String> {

    /** Starts a clause testing a Dagger {@link Module @Module} method. */
    static DaggerModuleMethodSubject assertThatModuleMethod(String method) {
      return assertAbout(new Factory())
          .that(method)
          .withDeclaration("@Module abstract class %s { %s }");
    }

    /** Starts a clause testing a Dagger {@link ProducerModule @ProducerModule} method. */
    static DaggerModuleMethodSubject assertThatProductionModuleMethod(String method) {
      return assertAbout(new Factory())
          .that(method)
          .withDeclaration("@ProducerModule abstract class %s { %s }");
    }

    /** Starts a clause testing a method in an unannotated class. */
    static DaggerModuleMethodSubject assertThatMethodInUnannotatedClass(String method) {
      return assertAbout(new Factory())
          .that(method)
          .withDeclaration("abstract class %s { %s }");
    }

    private Factory() {}

    @Override
    public DaggerModuleMethodSubject createSubject(FailureMetadata failureMetadata, String that) {
      return new DaggerModuleMethodSubject(failureMetadata, that);
    }
  }

  private final String actual;
  private String declaration;
  private ImmutableList<Source> additionalSources = ImmutableList.of();
  private final ImmutableList.Builder<String> additionalImports = ImmutableList.builder();
  private final ImmutableMap.Builder<String, String> processorOptions = ImmutableMap.builder();

  private DaggerModuleMethodSubject(FailureMetadata failureMetadata, String subject) {
    super(failureMetadata, subject);
    this.actual = subject;
  }

  /**
   * Imports classes and interfaces. Note that all types in the following packages are already
   * imported:<ul>
   * <li>{@code dagger.*}
   * <li>{@code dagger.multibindings.*}
   * <li>(@code dagger.producers.*}
   * <li>{@code java.util.*}
   * <li>{@code javax.inject.*}
   * </ul>
   */
  @CanIgnoreReturnValue
  DaggerModuleMethodSubject importing(Class<?>... imports) {
    return importing(Arrays.asList(imports));
  }

  /**
   * Imports classes and interfaces. Note that all types in the following packages are already
   * imported:<ul>
   * <li>{@code dagger.*}
   * <li>{@code dagger.multibindings.*}
   * <li>(@code dagger.producers.*}
   * <li>{@code java.util.*}
   * <li>{@code javax.inject.*}
   * </ul>
   */
  @CanIgnoreReturnValue
  DaggerModuleMethodSubject importing(List<? extends Class<?>> imports) {
    imports.stream()
        .map(clazz -> String.format("import %s;", clazz.getCanonicalName()))
        .forEachOrdered(additionalImports::add);
    return this;
  }

  /**
   * Sets the declaration of the module. Must be a string with two {@code %s} parameters. The first
   * will be replaced with the name of the type, and the second with the method declaration, which
   * must be within paired braces.
   */
  @CanIgnoreReturnValue
  DaggerModuleMethodSubject withDeclaration(String declaration) {
    this.declaration = declaration;
    return this;
  }

  /** Additional source files that must be compiled with the module. */
  @CanIgnoreReturnValue
  DaggerModuleMethodSubject withAdditionalSources(Source... sources) {
    this.additionalSources = ImmutableList.copyOf(sources);
    return this;
  }

  @CanIgnoreReturnValue
  DaggerModuleMethodSubject withProcessorOptions(String key, String value) {
    this.processorOptions.put(key, value);
    return this;
  }

  @CanIgnoreReturnValue
  DaggerModuleMethodSubject withProcessorOptions(Map<String, String> processorOptions) {
    this.processorOptions.putAll(processorOptions);
    return this;
  }

  /**
   * Fails if compiling the module with the method doesn't report an error at the method
   * declaration whose message contains {@code errorSubstring}.
   */
  void hasError(String errorSubstring) {
    String source = moduleSource();
    Source module = CompilerTests.javaSource("test.TestModule", source);
    CompilerTests.daggerCompiler(
            ImmutableList.<Source>builder().add(module).addAll(additionalSources).build())
        .withProcessingOptions(processorOptions.buildOrThrow())
        .compile(
            subject ->
                subject
                    .hasErrorContaining(errorSubstring)
                    .onSource(module)
                    .onLine(methodLine(source)));
  }

  private int methodLine(String source) {
    String beforeMethod = source.substring(0, source.indexOf(actual));
    int methodLine = 1;
    for (int nextNewlineIndex = beforeMethod.indexOf('\n');
        nextNewlineIndex >= 0;
        nextNewlineIndex = beforeMethod.indexOf('\n', nextNewlineIndex + 1)) {
      methodLine++;
    }
    return methodLine;
  }

  private String moduleSource() {
    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    writer.println("package test;");
    writer.println();
    // explicitly import Module so it's not ambiguous with java.lang.Module
    writer.println("import dagger.Module;");
    writer.println("import dagger.*;");
    writer.println("import dagger.Provides;");
    writer.println("import dagger.multibindings.*;");
    writer.println("import dagger.producers.*;");
    writer.println("import java.util.*;");
    writer.println("import javax.inject.*;");
    for (String importLine : additionalImports.build()) {
      writer.println(importLine);
    }
    writer.println();
    writer.printf(declaration, "TestModule", "\n" + actual + "\n");
    writer.println();
    return stringWriter.toString();
  }
}
