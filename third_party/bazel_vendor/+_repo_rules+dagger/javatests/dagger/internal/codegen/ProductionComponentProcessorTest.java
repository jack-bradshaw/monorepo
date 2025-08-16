/*
 * Copyright (C) 2014 The Dagger Authors.
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


import androidx.room.compiler.processing.util.Source;
import com.google.common.collect.ImmutableMap;
import dagger.testing.compile.CompilerTests;
import dagger.testing.golden.GoldenFileRule;
import java.util.Collection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ProductionComponentProcessorTest {
  @Parameters(name = "{0}")
  public static Collection<Object[]> parameters() {
    return CompilerMode.TEST_PARAMETERS;
  }

  private static final Source EXECUTOR_MODULE =
      CompilerTests.javaSource(
          "test.ExecutorModule",
          "package test;",
          "",
          "import com.google.common.util.concurrent.MoreExecutors;",
          "import dagger.Module;",
          "import dagger.Provides;",
          "import dagger.producers.Production;",
          "import java.util.concurrent.Executor;",
          "",
          "@Module",
          "final class ExecutorModule {",
          "  @Provides @Production Executor executor() {",
          "    return MoreExecutors.directExecutor();",
          "  }",
          "}");

  @Rule public GoldenFileRule goldenFileRule = new GoldenFileRule();

  private final CompilerMode compilerMode;

  public ProductionComponentProcessorTest(CompilerMode compilerMode) {
    this.compilerMode = compilerMode;
  }

  @Test public void componentOnConcreteClass() {
    Source componentFile =
        CompilerTests.javaSource("test.NotAComponent",
        "package test;",
        "",
        "import dagger.producers.ProductionComponent;",
        "",
        "@ProductionComponent",
        "final class NotAComponent {}");
    CompilerTests.daggerCompiler(componentFile)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining(
                  "@ProductionComponent may only be applied to an interface or abstract class");
            });
  }

  @Test public void componentOnEnum() {
    Source componentFile =
        CompilerTests.javaSource("test.NotAComponent",
        "package test;",
        "",
        "import dagger.producers.ProductionComponent;",
        "",
        "@ProductionComponent",
        "enum NotAComponent {",
        "  INSTANCE",
        "}");
    CompilerTests.daggerCompiler(componentFile)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining(
                  "@ProductionComponent may only be applied to an interface or abstract class");
            });
  }

  @Test public void componentOnAnnotation() {
    Source componentFile =
        CompilerTests.javaSource("test.NotAComponent",
        "package test;",
        "",
        "import dagger.producers.ProductionComponent;",
        "",
        "@ProductionComponent",
        "@interface NotAComponent {}");
    CompilerTests.daggerCompiler(componentFile)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining(
                  "@ProductionComponent may only be applied to an interface or abstract class");
            });
  }

  @Test public void nonModuleModule() {
    Source componentFile =
        CompilerTests.javaSource("test.NotAComponent",
        "package test;",
        "",
        "import dagger.producers.ProductionComponent;",
        "",
        "@ProductionComponent(modules = Object.class)",
        "interface NotAComponent {}");
    CompilerTests.daggerCompiler(componentFile)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining("is not annotated with one of @Module, @ProducerModule");
            });
  }

  @Test
  public void dependsOnProductionExecutor() throws Exception {
    Source producerModuleFile =
        CompilerTests.javaSource(
            "test.SimpleModule",
            "package test;",
            "",
            "import dagger.producers.ProducerModule;",
            "import dagger.producers.Produces;",
            "import dagger.producers.Production;",
            "import java.util.concurrent.Executor;",
            "",
            "@ProducerModule",
            "final class SimpleModule {",
            "  @Produces String str(@Production Executor executor) {",
            "    return \"\";",
            "  }",
            "}");
    Source componentFile =
        CompilerTests.javaSource(
            "test.SimpleComponent",
            "package test;",
            "",
            "import com.google.common.util.concurrent.ListenableFuture;",
            "import dagger.producers.ProductionComponent;",
            "import java.util.concurrent.Executor;",
            "",
            "@ProductionComponent(modules = {ExecutorModule.class, SimpleModule.class})",
            "interface SimpleComponent {",
            "  ListenableFuture<String> str();",
            "",
            "  @ProductionComponent.Builder",
            "  interface Builder {",
            "    SimpleComponent build();",
            "  }",
            "}");

    String errorMessage = "String may not depend on the production executor";
    CompilerTests.daggerCompiler(EXECUTOR_MODULE, producerModuleFile, componentFile)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining(errorMessage)
                  .onSource(componentFile)
                  .onLineContaining("interface SimpleComponent");
            });

    // Verify that the error is reported on the module when fullBindingGraphValidation is enabled.
    CompilerTests.daggerCompiler(producerModuleFile)
        .withProcessingOptions(
            ImmutableMap.<String, String>builder()
                .putAll(compilerMode.processorOptions())
                .put("dagger.fullBindingGraphValidation", "ERROR")
                .buildOrThrow())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining(errorMessage)
                  .onSource(producerModuleFile)
                  .onLineContaining("class SimpleModule");
            });
    // TODO(dpb): Report at the binding if enclosed in the module.
  }

  @Test
  public void dependsOnProductionSubcomponentWithPluginsVisitFullBindingGraphs() throws Exception {
    Source myComponent =
        CompilerTests.javaSource(
            "test.MyComponent",
            "package test;",
            "",
            "import dagger.Component;",
            "",
            "@Component(modules = MyModule.class)",
            "interface MyComponent {}");
    Source myModule =
        CompilerTests.javaSource(
            "test.MyModule",
            "package test;",
            "",
            "import dagger.Component;",
            "import dagger.Module;",
            "",
            "@Module(subcomponents = MyProductionSubcomponent.class)",
            "interface MyModule {}");
    Source myProductionSubcomponent =
        CompilerTests.javaSource(
            "test.MyProductionSubcomponent",
            "package test;",
            "",
            "import dagger.producers.ProductionSubcomponent;",
            "",
            "@ProductionSubcomponent",
            "interface MyProductionSubcomponent {",
            "  @ProductionSubcomponent.Builder",
            "  interface Builder {",
            "    MyProductionSubcomponent build();",
            "  }",
            "}");

    CompilerTests.daggerCompiler(myComponent, myModule, myProductionSubcomponent)
        .withProcessingOptions(
            ImmutableMap.<String, String>builder()
                .putAll(compilerMode.processorOptions())
                .put("dagger.pluginsVisitFullBindingGraphs", "ENABLED")
                .buildOrThrow())
        .compile(subject -> subject.hasErrorCount(0));
  }

  @Test
  public void simpleComponent() throws Exception {
    Source component =
        CompilerTests.javaSource(
            "test.TestClass",
            "package test;",
            "",
            "import com.google.common.util.concurrent.ListenableFuture;",
            "import com.google.common.util.concurrent.MoreExecutors;",
            "import dagger.Module;",
            "import dagger.Provides;",
            "import dagger.producers.ProducerModule;",
            "import dagger.producers.Produces;",
            "import dagger.producers.Production;",
            "import dagger.producers.ProductionComponent;",
            "import java.util.concurrent.Executor;",
            "import javax.inject.Inject;",
            "",
            "final class TestClass {",
            "  static final class C {",
            "    @Inject C() {}",
            "  }",
            "",
            "  interface A {}",
            "  interface B {}",
            "",
            "  @Module",
            "  static final class BModule {",
            "    @Provides B b(C c) {",
            "      return null;",
            "    }",
            "",
            "    @Provides @Production Executor executor() {",
            "      return MoreExecutors.directExecutor();",
            "    }",
            "  }",
            "",
            "  @ProducerModule",
            "  static final class AModule {",
            "    @Produces ListenableFuture<A> a(B b) {",
            "      return null;",
            "    }",
            "  }",
            "",
            "  @ProductionComponent(modules = {AModule.class, BModule.class})",
            "  interface SimpleComponent {",
            "    ListenableFuture<A> a();",
            "  }",
            "}");

    CompilerTests.daggerCompiler(component)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(0);
              subject.generatedSource(
                  goldenFileRule.goldenSource("test/DaggerTestClass_SimpleComponent"));
            });
  }

  @Test public void nullableProducersAreNotErrors() {
    Source component =
        CompilerTests.javaSource("test.TestClass",
        "package test;",
        "",
        "import com.google.common.util.concurrent.ListenableFuture;",
        "import com.google.common.util.concurrent.MoreExecutors;",
        "import dagger.Module;",
        "import dagger.Provides;",
        "import dagger.producers.ProducerModule;",
        "import dagger.producers.Produces;",
        "import dagger.producers.Production;",
        "import dagger.producers.ProductionComponent;",
        "import java.util.concurrent.Executor;",
        "import javax.annotation.Nullable;",
        "import javax.inject.Inject;",
        "",
        "final class TestClass {",
        "  interface A {}",
        "  interface B {}",
        "  interface C {}",
        "",
        "  @Module",
        "  static final class CModule {",
        "    @Provides @Nullable C c() {",
        "      return null;",
        "    }",
        "",
        "    @Provides @Production Executor executor() {",
        "      return MoreExecutors.directExecutor();",
        "    }",
        "  }",
        "",
        "  @ProducerModule",
        "  static final class ABModule {",
        "    @Produces @Nullable B b(@Nullable C c) {",
        "      return null;",
        "    }",

        "    @Produces @Nullable ListenableFuture<A> a(B b) {",  // NOTE: B not injected as nullable
        "      return null;",
        "    }",
        "  }",
        "",
        "  @ProductionComponent(modules = {ABModule.class, CModule.class})",
        "  interface SimpleComponent {",
        "    ListenableFuture<A> a();",
        "  }",
        "}");
    CompilerTests.daggerCompiler(component)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(0);
              subject.hasWarningCount(2);
              subject.hasWarningContaining("@Nullable on @Produces methods does not do anything")
                  .onSource(component)
                  .onLine(33);
              subject.hasWarningContaining("@Nullable on @Produces methods does not do anything")
                  .onSource(component)
                  .onLine(36);
            });
  }

  @Test
  public void productionScope_injectConstructor() throws Exception {
    Source productionScoped =
        CompilerTests.javaSource(
            "test.ProductionScoped",
            "package test;",
            "",
            "import dagger.producers.ProductionScope;",
            "import javax.inject.Inject;",
            "",
            "@ProductionScope",
            "class ProductionScoped {",
            "  @Inject ProductionScoped() {}",
            "}");
    Source parent =
        CompilerTests.javaSource(
            "test.Parent",
            "package test;",
            "",
            "import dagger.producers.ProductionComponent;",
            "",
            "@ProductionComponent",
            "interface Parent {",
            "  Child child();",
            "}");
    Source child =
        CompilerTests.javaSource(
            "test.Child",
            "package test;",
            "",
            "import dagger.producers.ProductionSubcomponent;",
            "",
            "@ProductionSubcomponent",
            "interface Child {",
            "  ProductionScoped productionScoped();",
            "}");

    CompilerTests.daggerCompiler(productionScoped, parent, child)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(0);
              subject.generatedSource(goldenFileRule.goldenSource("test/DaggerParent"));
            });
  }

  @Test
  public void requestProducerNodeWithProvider_failsWithNotSupportedError() {
    Source producerModuleFile =
        CompilerTests.javaSource(
            "test.SimpleModule",
            "package test;",
            "",
            "import dagger.producers.ProducerModule;",
            "import dagger.producers.Produces;",
            "import javax.inject.Provider;",
            "import java.util.concurrent.Executor;",
            "import dagger.producers.Production;",
            "",
            "@ProducerModule",
            "final class SimpleModule {",
            "  @Produces String str(Provider<Integer> num) {",
            "    return \"\";",
            "  }",
            "  @Produces Integer num() { return 1; }",
            "}");
    Source componentFile =
        CompilerTests.javaSource(
            "test.SimpleComponent",
            "package test;",
            "",
            "import com.google.common.util.concurrent.ListenableFuture;",
            "import dagger.producers.ProductionComponent;",
            "",
            "@ProductionComponent(modules = {ExecutorModule.class, SimpleModule.class})",
            "interface SimpleComponent {",
            "  ListenableFuture<String> str();",
            "",
            "  @ProductionComponent.Builder",
            "  interface Builder {",
            "    SimpleComponent build();",
            "  }",
            "}");

    CompilerTests.daggerCompiler(EXECUTOR_MODULE, producerModuleFile, componentFile)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining(
                  "request kind PROVIDER cannot be satisfied by production binding");
            });
  }

  @Test
  public void productionBindingKind_failsIfScoped() {
    Source component =
        CompilerTests.javaSource(
            "test.TestComponent",
            "package test;",
            "",
            "import com.google.common.util.concurrent.ListenableFuture;",
            "import dagger.producers.ProductionComponent;",
            "",
            "@ProductionComponent(modules = {ExecutorModule.class, TestModule.class})",
            "interface TestComponent {",
            "  ListenableFuture<String> str();",
            "}");
    Source module =
        CompilerTests.javaSource(
            "test.TestModule",
            "package test;",
            "",
            "import dagger.producers.ProducerModule;",
            "import dagger.producers.Produces;",
            "import dagger.producers.ProductionScope;",
            "import javax.inject.Provider;",
            "import java.util.concurrent.Executor;",
            "import dagger.producers.Production;",
            "",
            "@ProducerModule",
            "interface TestModule {",
            "  @ProductionScope",
            "  @Produces",
            "  static String provideString() { return \"\"; }",
            "}");

    CompilerTests.daggerCompiler(component, module, EXECUTOR_MODULE)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining("@Produces methods cannot be scoped");
            });
  }

  @Test
  public void delegateToProductionBindingKind_failsIfScoped() {
    Source component =
        CompilerTests.javaSource(
            "test.TestComponent",
            "package test;",
            "",
            "import com.google.common.util.concurrent.ListenableFuture;",
            "import dagger.producers.ProductionComponent;",
            "",
            "@ProductionComponent(modules = {ExecutorModule.class, TestModule.class})",
            "interface TestComponent {",
            "  ListenableFuture<Foo> foo();",
            "}");
    Source module =
        CompilerTests.javaSource(
            "test.TestModule",
            "package test;",
            "",
            "import dagger.Binds;",
            "import dagger.producers.ProducerModule;",
            "import dagger.producers.Produces;",
            "import dagger.producers.ProductionScope;",
            "import javax.inject.Provider;",
            "import java.util.concurrent.Executor;",
            "import dagger.producers.Production;",
            "",
            "@ProducerModule",
            "interface TestModule {",
            "  @ProductionScope",
            "  @Binds",
            "  Foo bind(FooImpl impl);",
            "",
            "  @Produces",
            "  static FooImpl fooImpl() { return new FooImpl(); }",
            "}");
    Source foo =
        CompilerTests.javaSource(
            "test.Foo",
            "package test;",
            "",
            "interface Foo {}");
    Source fooImpl =
        CompilerTests.javaSource(
            "test.FooImpl",
            "package test;",
            "",
            "final class FooImpl implements Foo {}");

    CompilerTests.daggerCompiler(component, module, foo, fooImpl, EXECUTOR_MODULE)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining(
                  "@ProductionScope @Binds Foo TestModule.bind(FooImpl) cannot be scoped "
                      + "because it delegates to an @Produces method");
            });
  }

  @Test
  public void multipleDelegatesToProductionBindingKind_failsIfScoped() {
    Source component =
        CompilerTests.javaSource(
            "test.TestComponent",
            "package test;",
            "",
            "import com.google.common.util.concurrent.ListenableFuture;",
            "import dagger.producers.ProductionComponent;",
            "",
            "@ProductionComponent(modules = {ExecutorModule.class, TestModule.class})",
            "interface TestComponent {",
            "  ListenableFuture<FooSuper> fooSuper();",
            "}");
    Source module =
        CompilerTests.javaSource(
            "test.TestModule",
            "package test;",
            "",
            "import dagger.Binds;",
            "import dagger.producers.ProducerModule;",
            "import dagger.producers.Produces;",
            "import dagger.producers.ProductionScope;",
            "import javax.inject.Provider;",
            "import java.util.concurrent.Executor;",
            "import dagger.producers.Production;",
            "",
            "@ProducerModule",
            "interface TestModule {",
            "  @ProductionScope",
            "  @Binds",
            "  FooSuper bindFooSuper(Foo impl);",
            "",
            "  @Binds",
            "  Foo bindFoo(FooImpl impl);",
            "",
            "  @Produces",
            "  static FooImpl fooImpl() { return new FooImpl(); }",
            "}");
    Source fooSuper =
        CompilerTests.javaSource(
            "test.FooSuper",
            "package test;",
            "",
            "interface FooSuper {}");
    Source foo =
        CompilerTests.javaSource(
            "test.Foo",
            "package test;",
            "",
            "interface Foo extends FooSuper {}");
    Source fooImpl =
        CompilerTests.javaSource(
            "test.FooImpl",
            "package test;",
            "",
            "final class FooImpl implements Foo {}");

    CompilerTests.daggerCompiler(component, module, fooSuper, foo, fooImpl, EXECUTOR_MODULE)
        .withProcessingOptions(compilerMode.processorOptions())
        .compile(
            subject -> {
              subject.hasErrorCount(1);
              subject.hasErrorContaining(
                  "@ProductionScope @Binds FooSuper TestModule.bindFooSuper(Foo) cannot be scoped "
                      + "because it delegates to an @Produces method");
            });
  }
}
