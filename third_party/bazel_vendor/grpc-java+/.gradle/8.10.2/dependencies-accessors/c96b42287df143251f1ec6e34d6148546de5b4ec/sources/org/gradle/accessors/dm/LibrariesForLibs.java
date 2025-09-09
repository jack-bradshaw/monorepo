package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final AndroidLibraryAccessors laccForAndroidLibraryAccessors = new AndroidLibraryAccessors(owner);
    private final AndroidxLibraryAccessors laccForAndroidxLibraryAccessors = new AndroidxLibraryAccessors(owner);
    private final AnimalsnifferLibraryAccessors laccForAnimalsnifferLibraryAccessors = new AnimalsnifferLibraryAccessors(owner);
    private final AssertjLibraryAccessors laccForAssertjLibraryAccessors = new AssertjLibraryAccessors(owner);
    private final AutoLibraryAccessors laccForAutoLibraryAccessors = new AutoLibraryAccessors(owner);
    private final CommonsLibraryAccessors laccForCommonsLibraryAccessors = new CommonsLibraryAccessors(owner);
    private final CronetLibraryAccessors laccForCronetLibraryAccessors = new CronetLibraryAccessors(owner);
    private final ErrorproneLibraryAccessors laccForErrorproneLibraryAccessors = new ErrorproneLibraryAccessors(owner);
    private final GoogleLibraryAccessors laccForGoogleLibraryAccessors = new GoogleLibraryAccessors(owner);
    private final GuavaLibraryAccessors laccForGuavaLibraryAccessors = new GuavaLibraryAccessors(owner);
    private final JakartaLibraryAccessors laccForJakartaLibraryAccessors = new JakartaLibraryAccessors(owner);
    private final JavaxLibraryAccessors laccForJavaxLibraryAccessors = new JavaxLibraryAccessors(owner);
    private final JettyLibraryAccessors laccForJettyLibraryAccessors = new JettyLibraryAccessors(owner);
    private final MockitoLibraryAccessors laccForMockitoLibraryAccessors = new MockitoLibraryAccessors(owner);
    private final NettyLibraryAccessors laccForNettyLibraryAccessors = new NettyLibraryAccessors(owner);
    private final OpencensusLibraryAccessors laccForOpencensusLibraryAccessors = new OpencensusLibraryAccessors(owner);
    private final OpentelemetryLibraryAccessors laccForOpentelemetryLibraryAccessors = new OpentelemetryLibraryAccessors(owner);
    private final PerfmarkLibraryAccessors laccForPerfmarkLibraryAccessors = new PerfmarkLibraryAccessors(owner);
    private final ProtobufLibraryAccessors laccForProtobufLibraryAccessors = new ProtobufLibraryAccessors(owner);
    private final SignatureLibraryAccessors laccForSignatureLibraryAccessors = new SignatureLibraryAccessors(owner);
    private final TomcatLibraryAccessors laccForTomcatLibraryAccessors = new TomcatLibraryAccessors(owner);
    private final UndertowLibraryAccessors laccForUndertowLibraryAccessors = new UndertowLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>checkstyle</b> with <b>com.puppycrawl.tools:checkstyle</b> coordinates and
     * with version <b>10.21.2</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getCheckstyle() {
        return create("checkstyle");
    }

    /**
     * Dependency provider for <b>checkstylejava8</b> with <b>com.puppycrawl.tools:checkstyle</b> coordinates and
     * with version <b>9.3</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getCheckstylejava8() {
        return create("checkstylejava8");
    }

    /**
     * Dependency provider for <b>conscrypt</b> with <b>org.conscrypt:conscrypt-openjdk-uber</b> coordinates and
     * with version <b>2.5.2</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getConscrypt() {
        return create("conscrypt");
    }

    /**
     * Dependency provider for <b>gson</b> with <b>com.google.code.gson:gson</b> coordinates and
     * with version <b>2.11.0</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getGson() {
        return create("gson");
    }

    /**
     * Dependency provider for <b>hdrhistogram</b> with <b>org.hdrhistogram:HdrHistogram</b> coordinates and
     * with version <b>2.2.2</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getHdrhistogram() {
        return create("hdrhistogram");
    }

    /**
     * Dependency provider for <b>jsr305</b> with <b>com.google.code.findbugs:jsr305</b> coordinates and
     * with version <b>3.0.2</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getJsr305() {
        return create("jsr305");
    }

    /**
     * Dependency provider for <b>junit</b> with <b>junit:junit</b> coordinates and
     * with version <b>4.13.2</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getJunit() {
        return create("junit");
    }

    /**
     * Dependency provider for <b>lincheck</b> with <b>org.jetbrains.kotlinx:lincheck-jvm</b> coordinates and
     * with version <b>2.16</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getLincheck() {
        return create("lincheck");
    }

    /**
     * Dependency provider for <b>okhttp</b> with <b>com.squareup.okhttp:okhttp</b> coordinates and
     * with version <b>2.7.5</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getOkhttp() {
        return create("okhttp");
    }

    /**
     * Dependency provider for <b>okio</b> with <b>com.squareup.okio:okio</b> coordinates and
     * with version <b>3.4.0</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getOkio() {
        return create("okio");
    }

    /**
     * Dependency provider for <b>re2j</b> with <b>com.google.re2j:re2j</b> coordinates and
     * with version <b>1.8</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getRe2j() {
        return create("re2j");
    }

    /**
     * Dependency provider for <b>robolectric</b> with <b>org.robolectric:robolectric</b> coordinates and
     * with version <b>4.14.1</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getRobolectric() {
        return create("robolectric");
    }

    /**
     * Dependency provider for <b>truth</b> with <b>com.google.truth:truth</b> coordinates and
     * with version <b>1.4.4</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getTruth() {
        return create("truth");
    }

    /**
     * Group of libraries at <b>android</b>
     */
    public AndroidLibraryAccessors getAndroid() {
        return laccForAndroidLibraryAccessors;
    }

    /**
     * Group of libraries at <b>androidx</b>
     */
    public AndroidxLibraryAccessors getAndroidx() {
        return laccForAndroidxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>animalsniffer</b>
     */
    public AnimalsnifferLibraryAccessors getAnimalsniffer() {
        return laccForAnimalsnifferLibraryAccessors;
    }

    /**
     * Group of libraries at <b>assertj</b>
     */
    public AssertjLibraryAccessors getAssertj() {
        return laccForAssertjLibraryAccessors;
    }

    /**
     * Group of libraries at <b>auto</b>
     */
    public AutoLibraryAccessors getAuto() {
        return laccForAutoLibraryAccessors;
    }

    /**
     * Group of libraries at <b>commons</b>
     */
    public CommonsLibraryAccessors getCommons() {
        return laccForCommonsLibraryAccessors;
    }

    /**
     * Group of libraries at <b>cronet</b>
     */
    public CronetLibraryAccessors getCronet() {
        return laccForCronetLibraryAccessors;
    }

    /**
     * Group of libraries at <b>errorprone</b>
     */
    public ErrorproneLibraryAccessors getErrorprone() {
        return laccForErrorproneLibraryAccessors;
    }

    /**
     * Group of libraries at <b>google</b>
     */
    public GoogleLibraryAccessors getGoogle() {
        return laccForGoogleLibraryAccessors;
    }

    /**
     * Group of libraries at <b>guava</b>
     */
    public GuavaLibraryAccessors getGuava() {
        return laccForGuavaLibraryAccessors;
    }

    /**
     * Group of libraries at <b>jakarta</b>
     */
    public JakartaLibraryAccessors getJakarta() {
        return laccForJakartaLibraryAccessors;
    }

    /**
     * Group of libraries at <b>javax</b>
     */
    public JavaxLibraryAccessors getJavax() {
        return laccForJavaxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>jetty</b>
     */
    public JettyLibraryAccessors getJetty() {
        return laccForJettyLibraryAccessors;
    }

    /**
     * Group of libraries at <b>mockito</b>
     */
    public MockitoLibraryAccessors getMockito() {
        return laccForMockitoLibraryAccessors;
    }

    /**
     * Group of libraries at <b>netty</b>
     */
    public NettyLibraryAccessors getNetty() {
        return laccForNettyLibraryAccessors;
    }

    /**
     * Group of libraries at <b>opencensus</b>
     */
    public OpencensusLibraryAccessors getOpencensus() {
        return laccForOpencensusLibraryAccessors;
    }

    /**
     * Group of libraries at <b>opentelemetry</b>
     */
    public OpentelemetryLibraryAccessors getOpentelemetry() {
        return laccForOpentelemetryLibraryAccessors;
    }

    /**
     * Group of libraries at <b>perfmark</b>
     */
    public PerfmarkLibraryAccessors getPerfmark() {
        return laccForPerfmarkLibraryAccessors;
    }

    /**
     * Group of libraries at <b>protobuf</b>
     */
    public ProtobufLibraryAccessors getProtobuf() {
        return laccForProtobufLibraryAccessors;
    }

    /**
     * Group of libraries at <b>signature</b>
     */
    public SignatureLibraryAccessors getSignature() {
        return laccForSignatureLibraryAccessors;
    }

    /**
     * Group of libraries at <b>tomcat</b>
     */
    public TomcatLibraryAccessors getTomcat() {
        return laccForTomcatLibraryAccessors;
    }

    /**
     * Group of libraries at <b>undertow</b>
     */
    public UndertowLibraryAccessors getUndertow() {
        return laccForUndertowLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class AndroidLibraryAccessors extends SubDependencyFactory {

        public AndroidLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotations</b> with <b>com.google.android:annotations</b> coordinates and
         * with version <b>4.1.1.4</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("android.annotations");
        }

    }

    public static class AndroidxLibraryAccessors extends SubDependencyFactory {
        private final AndroidxLifecycleLibraryAccessors laccForAndroidxLifecycleLibraryAccessors = new AndroidxLifecycleLibraryAccessors(owner);
        private final AndroidxTestLibraryAccessors laccForAndroidxTestLibraryAccessors = new AndroidxTestLibraryAccessors(owner);

        public AndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotation</b> with <b>androidx.annotation:annotation</b> coordinates and
         * with version <b>1.9.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotation() {
            return create("androidx.annotation");
        }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.core:core</b> coordinates and
         * with version <b>1.13.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("androidx.core");
        }

        /**
         * Group of libraries at <b>androidx.lifecycle</b>
         */
        public AndroidxLifecycleLibraryAccessors getLifecycle() {
            return laccForAndroidxLifecycleLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.test</b>
         */
        public AndroidxTestLibraryAccessors getTest() {
            return laccForAndroidxTestLibraryAccessors;
        }

    }

    public static class AndroidxLifecycleLibraryAccessors extends SubDependencyFactory {

        public AndroidxLifecycleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>common</b> with <b>androidx.lifecycle:lifecycle-common</b> coordinates and
         * with version <b>2.8.7</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommon() {
            return create("androidx.lifecycle.common");
        }

        /**
         * Dependency provider for <b>service</b> with <b>androidx.lifecycle:lifecycle-service</b> coordinates and
         * with version <b>2.8.7</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getService() {
            return create("androidx.lifecycle.service");
        }

    }

    public static class AndroidxTestLibraryAccessors extends SubDependencyFactory {
        private final AndroidxTestExtLibraryAccessors laccForAndroidxTestExtLibraryAccessors = new AndroidxTestExtLibraryAccessors(owner);

        public AndroidxTestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.test:core</b> coordinates and
         * with version <b>1.6.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("androidx.test.core");
        }

        /**
         * Dependency provider for <b>rules</b> with <b>androidx.test:rules</b> coordinates and
         * with version <b>1.6.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRules() {
            return create("androidx.test.rules");
        }

        /**
         * Group of libraries at <b>androidx.test.ext</b>
         */
        public AndroidxTestExtLibraryAccessors getExt() {
            return laccForAndroidxTestExtLibraryAccessors;
        }

    }

    public static class AndroidxTestExtLibraryAccessors extends SubDependencyFactory {

        public AndroidxTestExtLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit</b> with <b>androidx.test.ext:junit</b> coordinates and
         * with version <b>1.2.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit() {
            return create("androidx.test.ext.junit");
        }

    }

    public static class AnimalsnifferLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AnimalsnifferLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>animalsniffer</b> with <b>org.codehaus.mojo:animal-sniffer</b> coordinates and
         * with version <b>1.24</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("animalsniffer");
        }

        /**
         * Dependency provider for <b>annotations</b> with <b>org.codehaus.mojo:animal-sniffer-annotations</b> coordinates and
         * with version <b>1.24</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("animalsniffer.annotations");
        }

    }

    public static class AssertjLibraryAccessors extends SubDependencyFactory {

        public AssertjLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>org.assertj:assertj-core</b> coordinates and
         * with version <b>3.27.3</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("assertj.core");
        }

    }

    public static class AutoLibraryAccessors extends SubDependencyFactory {
        private final AutoValueLibraryAccessors laccForAutoValueLibraryAccessors = new AutoValueLibraryAccessors(owner);

        public AutoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>auto.value</b>
         */
        public AutoValueLibraryAccessors getValue() {
            return laccForAutoValueLibraryAccessors;
        }

    }

    public static class AutoValueLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AutoValueLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>value</b> with <b>com.google.auto.value:auto-value</b> coordinates and
         * with version <b>1.11.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("auto.value");
        }

        /**
         * Dependency provider for <b>annotations</b> with <b>com.google.auto.value:auto-value-annotations</b> coordinates and
         * with version <b>1.11.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("auto.value.annotations");
        }

    }

    public static class CommonsLibraryAccessors extends SubDependencyFactory {

        public CommonsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>math3</b> with <b>org.apache.commons:commons-math3</b> coordinates and
         * with version <b>3.6.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMath3() {
            return create("commons.math3");
        }

    }

    public static class CronetLibraryAccessors extends SubDependencyFactory {

        public CronetLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>org.chromium.net:cronet-api</b> coordinates and
         * with version <b>119.6045.31</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("cronet.api");
        }

        /**
         * Dependency provider for <b>embedded</b> with <b>org.chromium.net:cronet-embedded</b> coordinates and
         * with version <b>119.6045.31</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEmbedded() {
            return create("cronet.embedded");
        }

    }

    public static class ErrorproneLibraryAccessors extends SubDependencyFactory {

        public ErrorproneLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotations</b> with <b>com.google.errorprone:error_prone_annotations</b> coordinates and
         * with version <b>2.30.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotations() {
            return create("errorprone.annotations");
        }

        /**
         * Dependency provider for <b>core</b> with <b>com.google.errorprone:error_prone_core</b> coordinates and
         * with version <b>2.31.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("errorprone.core");
        }

        /**
         * Dependency provider for <b>corejava8</b> with <b>com.google.errorprone:error_prone_core</b> coordinates and
         * with version <b>2.10.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCorejava8() {
            return create("errorprone.corejava8");
        }

    }

    public static class GoogleLibraryAccessors extends SubDependencyFactory {
        private final GoogleApiLibraryAccessors laccForGoogleApiLibraryAccessors = new GoogleApiLibraryAccessors(owner);
        private final GoogleAuthLibraryAccessors laccForGoogleAuthLibraryAccessors = new GoogleAuthLibraryAccessors(owner);
        private final GoogleCloudLibraryAccessors laccForGoogleCloudLibraryAccessors = new GoogleCloudLibraryAccessors(owner);

        public GoogleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>google.api</b>
         */
        public GoogleApiLibraryAccessors getApi() {
            return laccForGoogleApiLibraryAccessors;
        }

        /**
         * Group of libraries at <b>google.auth</b>
         */
        public GoogleAuthLibraryAccessors getAuth() {
            return laccForGoogleAuthLibraryAccessors;
        }

        /**
         * Group of libraries at <b>google.cloud</b>
         */
        public GoogleCloudLibraryAccessors getCloud() {
            return laccForGoogleCloudLibraryAccessors;
        }

    }

    public static class GoogleApiLibraryAccessors extends SubDependencyFactory {

        public GoogleApiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>protos</b> with <b>com.google.api.grpc:proto-google-common-protos</b> coordinates and
         * with version <b>2.51.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getProtos() {
            return create("google.api.protos");
        }

    }

    public static class GoogleAuthLibraryAccessors extends SubDependencyFactory {

        public GoogleAuthLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>credentials</b> with <b>com.google.auth:google-auth-library-credentials</b> coordinates and
         * with version <b>1.24.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCredentials() {
            return create("google.auth.credentials");
        }

        /**
         * Dependency provider for <b>oauth2Http</b> with <b>com.google.auth:google-auth-library-oauth2-http</b> coordinates and
         * with version <b>1.24.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getOauth2Http() {
            return create("google.auth.oauth2Http");
        }

    }

    public static class GoogleCloudLibraryAccessors extends SubDependencyFactory {

        public GoogleCloudLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>logging</b> with <b>com.google.cloud:google-cloud-logging</b> coordinates and
         * with version <b>3.21.2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLogging() {
            return create("google.cloud.logging");
        }

    }

    public static class GuavaLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public GuavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>guava</b> with <b>com.google.guava:guava</b> coordinates and
         * with version <b>33.3.1-android</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("guava");
        }

        /**
         * Dependency provider for <b>betaChecker</b> with <b>com.google.guava:guava-beta-checker</b> coordinates and
         * with version <b>1.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBetaChecker() {
            return create("guava.betaChecker");
        }

        /**
         * Dependency provider for <b>jre</b> with <b>com.google.guava:guava</b> coordinates and
         * with version <b>33.3.1-jre</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJre() {
            return create("guava.jre");
        }

        /**
         * Dependency provider for <b>testlib</b> with <b>com.google.guava:guava-testlib</b> coordinates and
         * with version <b>33.3.1-android</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTestlib() {
            return create("guava.testlib");
        }

    }

    public static class JakartaLibraryAccessors extends SubDependencyFactory {
        private final JakartaServletLibraryAccessors laccForJakartaServletLibraryAccessors = new JakartaServletLibraryAccessors(owner);

        public JakartaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>jakarta.servlet</b>
         */
        public JakartaServletLibraryAccessors getServlet() {
            return laccForJakartaServletLibraryAccessors;
        }

    }

    public static class JakartaServletLibraryAccessors extends SubDependencyFactory {

        public JakartaServletLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>jakarta.servlet:jakarta.servlet-api</b> coordinates and
         * with version <b>5.0.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("jakarta.servlet.api");
        }

    }

    public static class JavaxLibraryAccessors extends SubDependencyFactory {
        private final JavaxServletLibraryAccessors laccForJavaxServletLibraryAccessors = new JavaxServletLibraryAccessors(owner);

        public JavaxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>annotation</b> with <b>org.apache.tomcat:annotations-api</b> coordinates and
         * with version <b>6.0.53</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAnnotation() {
            return create("javax.annotation");
        }

        /**
         * Group of libraries at <b>javax.servlet</b>
         */
        public JavaxServletLibraryAccessors getServlet() {
            return laccForJavaxServletLibraryAccessors;
        }

    }

    public static class JavaxServletLibraryAccessors extends SubDependencyFactory {

        public JavaxServletLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>javax.servlet:javax.servlet-api</b> coordinates and
         * with version <b>4.0.1</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("javax.servlet.api");
        }

    }

    public static class JettyLibraryAccessors extends SubDependencyFactory {
        private final JettyHttp2LibraryAccessors laccForJettyHttp2LibraryAccessors = new JettyHttp2LibraryAccessors(owner);

        public JettyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>client</b> with <b>org.eclipse.jetty:jetty-client</b> coordinates and
         * with version <b>11.0.24</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getClient() {
            return create("jetty.client");
        }

        /**
         * Dependency provider for <b>servlet</b> with <b>org.eclipse.jetty.ee10:jetty-ee10-servlet</b> coordinates and
         * with version <b>12.0.16</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getServlet() {
            return create("jetty.servlet");
        }

        /**
         * Dependency provider for <b>servlet10</b> with <b>org.eclipse.jetty:jetty-servlet</b> coordinates and
         * with version <b>10.0.20</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getServlet10() {
            return create("jetty.servlet10");
        }

        /**
         * Group of libraries at <b>jetty.http2</b>
         */
        public JettyHttp2LibraryAccessors getHttp2() {
            return laccForJettyHttp2LibraryAccessors;
        }

    }

    public static class JettyHttp2LibraryAccessors extends SubDependencyFactory {

        public JettyHttp2LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>server</b> with <b>org.eclipse.jetty.http2:jetty-http2-server</b> coordinates and
         * with version <b>12.0.16</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getServer() {
            return create("jetty.http2.server");
        }

        /**
         * Dependency provider for <b>server10</b> with <b>org.eclipse.jetty.http2:http2-server</b> coordinates and
         * with version <b>10.0.20</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getServer10() {
            return create("jetty.http2.server10");
        }

    }

    public static class MockitoLibraryAccessors extends SubDependencyFactory {

        public MockitoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>android</b> with <b>org.mockito:mockito-android</b> coordinates and
         * with version <b>4.4.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroid() {
            return create("mockito.android");
        }

        /**
         * Dependency provider for <b>core</b> with <b>org.mockito:mockito-core</b> coordinates and
         * with version <b>4.4.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("mockito.core");
        }

    }

    public static class NettyLibraryAccessors extends SubDependencyFactory {
        private final NettyCodecLibraryAccessors laccForNettyCodecLibraryAccessors = new NettyCodecLibraryAccessors(owner);
        private final NettyHandlerLibraryAccessors laccForNettyHandlerLibraryAccessors = new NettyHandlerLibraryAccessors(owner);
        private final NettyTcnativeLibraryAccessors laccForNettyTcnativeLibraryAccessors = new NettyTcnativeLibraryAccessors(owner);
        private final NettyTransportLibraryAccessors laccForNettyTransportLibraryAccessors = new NettyTransportLibraryAccessors(owner);
        private final NettyUnixLibraryAccessors laccForNettyUnixLibraryAccessors = new NettyUnixLibraryAccessors(owner);

        public NettyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>netty.codec</b>
         */
        public NettyCodecLibraryAccessors getCodec() {
            return laccForNettyCodecLibraryAccessors;
        }

        /**
         * Group of libraries at <b>netty.handler</b>
         */
        public NettyHandlerLibraryAccessors getHandler() {
            return laccForNettyHandlerLibraryAccessors;
        }

        /**
         * Group of libraries at <b>netty.tcnative</b>
         */
        public NettyTcnativeLibraryAccessors getTcnative() {
            return laccForNettyTcnativeLibraryAccessors;
        }

        /**
         * Group of libraries at <b>netty.transport</b>
         */
        public NettyTransportLibraryAccessors getTransport() {
            return laccForNettyTransportLibraryAccessors;
        }

        /**
         * Group of libraries at <b>netty.unix</b>
         */
        public NettyUnixLibraryAccessors getUnix() {
            return laccForNettyUnixLibraryAccessors;
        }

    }

    public static class NettyCodecLibraryAccessors extends SubDependencyFactory {

        public NettyCodecLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>http2</b> with <b>io.netty:netty-codec-http2</b> coordinates and
         * with version reference <b>netty</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getHttp2() {
            return create("netty.codec.http2");
        }

    }

    public static class NettyHandlerLibraryAccessors extends SubDependencyFactory {

        public NettyHandlerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>proxy</b> with <b>io.netty:netty-handler-proxy</b> coordinates and
         * with version reference <b>netty</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getProxy() {
            return create("netty.handler.proxy");
        }

    }

    public static class NettyTcnativeLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public NettyTcnativeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>tcnative</b> with <b>io.netty:netty-tcnative-boringssl-static</b> coordinates and
         * with version reference <b>nettytcnative</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("netty.tcnative");
        }

        /**
         * Dependency provider for <b>classes</b> with <b>io.netty:netty-tcnative-classes</b> coordinates and
         * with version reference <b>nettytcnative</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getClasses() {
            return create("netty.tcnative.classes");
        }

    }

    public static class NettyTransportLibraryAccessors extends SubDependencyFactory {

        public NettyTransportLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>epoll</b> with <b>io.netty:netty-transport-native-epoll</b> coordinates and
         * with version reference <b>netty</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEpoll() {
            return create("netty.transport.epoll");
        }

    }

    public static class NettyUnixLibraryAccessors extends SubDependencyFactory {

        public NettyUnixLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>common</b> with <b>io.netty:netty-transport-native-unix-common</b> coordinates and
         * with version reference <b>netty</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommon() {
            return create("netty.unix.common");
        }

    }

    public static class OpencensusLibraryAccessors extends SubDependencyFactory {
        private final OpencensusContribLibraryAccessors laccForOpencensusContribLibraryAccessors = new OpencensusContribLibraryAccessors(owner);
        private final OpencensusExporterLibraryAccessors laccForOpencensusExporterLibraryAccessors = new OpencensusExporterLibraryAccessors(owner);

        public OpencensusLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>io.opencensus:opencensus-api</b> coordinates and
         * with version reference <b>opencensus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("opencensus.api");
        }

        /**
         * Dependency provider for <b>impl</b> with <b>io.opencensus:opencensus-impl</b> coordinates and
         * with version reference <b>opencensus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getImpl() {
            return create("opencensus.impl");
        }

        /**
         * Group of libraries at <b>opencensus.contrib</b>
         */
        public OpencensusContribLibraryAccessors getContrib() {
            return laccForOpencensusContribLibraryAccessors;
        }

        /**
         * Group of libraries at <b>opencensus.exporter</b>
         */
        public OpencensusExporterLibraryAccessors getExporter() {
            return laccForOpencensusExporterLibraryAccessors;
        }

    }

    public static class OpencensusContribLibraryAccessors extends SubDependencyFactory {
        private final OpencensusContribGrpcLibraryAccessors laccForOpencensusContribGrpcLibraryAccessors = new OpencensusContribGrpcLibraryAccessors(owner);

        public OpencensusContribLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>opencensus.contrib.grpc</b>
         */
        public OpencensusContribGrpcLibraryAccessors getGrpc() {
            return laccForOpencensusContribGrpcLibraryAccessors;
        }

    }

    public static class OpencensusContribGrpcLibraryAccessors extends SubDependencyFactory {

        public OpencensusContribGrpcLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>metrics</b> with <b>io.opencensus:opencensus-contrib-grpc-metrics</b> coordinates and
         * with version reference <b>opencensus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMetrics() {
            return create("opencensus.contrib.grpc.metrics");
        }

    }

    public static class OpencensusExporterLibraryAccessors extends SubDependencyFactory {
        private final OpencensusExporterStatsLibraryAccessors laccForOpencensusExporterStatsLibraryAccessors = new OpencensusExporterStatsLibraryAccessors(owner);
        private final OpencensusExporterTraceLibraryAccessors laccForOpencensusExporterTraceLibraryAccessors = new OpencensusExporterTraceLibraryAccessors(owner);

        public OpencensusExporterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>opencensus.exporter.stats</b>
         */
        public OpencensusExporterStatsLibraryAccessors getStats() {
            return laccForOpencensusExporterStatsLibraryAccessors;
        }

        /**
         * Group of libraries at <b>opencensus.exporter.trace</b>
         */
        public OpencensusExporterTraceLibraryAccessors getTrace() {
            return laccForOpencensusExporterTraceLibraryAccessors;
        }

    }

    public static class OpencensusExporterStatsLibraryAccessors extends SubDependencyFactory {

        public OpencensusExporterStatsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>stackdriver</b> with <b>io.opencensus:opencensus-exporter-stats-stackdriver</b> coordinates and
         * with version reference <b>opencensus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getStackdriver() {
            return create("opencensus.exporter.stats.stackdriver");
        }

    }

    public static class OpencensusExporterTraceLibraryAccessors extends SubDependencyFactory {

        public OpencensusExporterTraceLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>stackdriver</b> with <b>io.opencensus:opencensus-exporter-trace-stackdriver</b> coordinates and
         * with version reference <b>opencensus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getStackdriver() {
            return create("opencensus.exporter.trace.stackdriver");
        }

    }

    public static class OpentelemetryLibraryAccessors extends SubDependencyFactory {
        private final OpentelemetryExporterLibraryAccessors laccForOpentelemetryExporterLibraryAccessors = new OpentelemetryExporterLibraryAccessors(owner);
        private final OpentelemetryGcpLibraryAccessors laccForOpentelemetryGcpLibraryAccessors = new OpentelemetryGcpLibraryAccessors(owner);
        private final OpentelemetrySdkLibraryAccessors laccForOpentelemetrySdkLibraryAccessors = new OpentelemetrySdkLibraryAccessors(owner);

        public OpentelemetryLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>io.opentelemetry:opentelemetry-api</b> coordinates and
         * with version <b>1.46.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("opentelemetry.api");
        }

        /**
         * Group of libraries at <b>opentelemetry.exporter</b>
         */
        public OpentelemetryExporterLibraryAccessors getExporter() {
            return laccForOpentelemetryExporterLibraryAccessors;
        }

        /**
         * Group of libraries at <b>opentelemetry.gcp</b>
         */
        public OpentelemetryGcpLibraryAccessors getGcp() {
            return laccForOpentelemetryGcpLibraryAccessors;
        }

        /**
         * Group of libraries at <b>opentelemetry.sdk</b>
         */
        public OpentelemetrySdkLibraryAccessors getSdk() {
            return laccForOpentelemetrySdkLibraryAccessors;
        }

    }

    public static class OpentelemetryExporterLibraryAccessors extends SubDependencyFactory {

        public OpentelemetryExporterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>prometheus</b> with <b>io.opentelemetry:opentelemetry-exporter-prometheus</b> coordinates and
         * with version <b>1.46.0-alpha</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPrometheus() {
            return create("opentelemetry.exporter.prometheus");
        }

    }

    public static class OpentelemetryGcpLibraryAccessors extends SubDependencyFactory {

        public OpentelemetryGcpLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>resources</b> with <b>io.opentelemetry.contrib:opentelemetry-gcp-resources</b> coordinates and
         * with version <b>1.43.0-alpha</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getResources() {
            return create("opentelemetry.gcp.resources");
        }

    }

    public static class OpentelemetrySdkLibraryAccessors extends SubDependencyFactory {
        private final OpentelemetrySdkExtensionLibraryAccessors laccForOpentelemetrySdkExtensionLibraryAccessors = new OpentelemetrySdkExtensionLibraryAccessors(owner);

        public OpentelemetrySdkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>testing</b> with <b>io.opentelemetry:opentelemetry-sdk-testing</b> coordinates and
         * with version <b>1.46.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTesting() {
            return create("opentelemetry.sdk.testing");
        }

        /**
         * Group of libraries at <b>opentelemetry.sdk.extension</b>
         */
        public OpentelemetrySdkExtensionLibraryAccessors getExtension() {
            return laccForOpentelemetrySdkExtensionLibraryAccessors;
        }

    }

    public static class OpentelemetrySdkExtensionLibraryAccessors extends SubDependencyFactory {

        public OpentelemetrySdkExtensionLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>autoconfigure</b> with <b>io.opentelemetry:opentelemetry-sdk-extension-autoconfigure</b> coordinates and
         * with version <b>1.46.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAutoconfigure() {
            return create("opentelemetry.sdk.extension.autoconfigure");
        }

    }

    public static class PerfmarkLibraryAccessors extends SubDependencyFactory {

        public PerfmarkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>io.perfmark:perfmark-api</b> coordinates and
         * with version <b>0.27.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("perfmark.api");
        }

    }

    public static class ProtobufLibraryAccessors extends SubDependencyFactory {
        private final ProtobufJavaLibraryAccessors laccForProtobufJavaLibraryAccessors = new ProtobufJavaLibraryAccessors(owner);

        public ProtobufLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>javalite</b> with <b>com.google.protobuf:protobuf-javalite</b> coordinates and
         * with version reference <b>protobuf</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJavalite() {
            return create("protobuf.javalite");
        }

        /**
         * Dependency provider for <b>protoc</b> with <b>com.google.protobuf:protoc</b> coordinates and
         * with version reference <b>protobuf</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getProtoc() {
            return create("protobuf.protoc");
        }

        /**
         * Group of libraries at <b>protobuf.java</b>
         */
        public ProtobufJavaLibraryAccessors getJava() {
            return laccForProtobufJavaLibraryAccessors;
        }

    }

    public static class ProtobufJavaLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public ProtobufJavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>java</b> with <b>com.google.protobuf:protobuf-java</b> coordinates and
         * with version reference <b>protobuf</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("protobuf.java");
        }

        /**
         * Dependency provider for <b>util</b> with <b>com.google.protobuf:protobuf-java-util</b> coordinates and
         * with version reference <b>protobuf</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getUtil() {
            return create("protobuf.java.util");
        }

    }

    public static class SignatureLibraryAccessors extends SubDependencyFactory {

        public SignatureLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>android</b> with <b>net.sf.androidscents.signature:android-api-level-21</b> coordinates and
         * with version <b>5.0.1_r2</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroid() {
            return create("signature.android");
        }

        /**
         * Dependency provider for <b>java</b> with <b>org.codehaus.mojo.signature:java18</b> coordinates and
         * with version <b>1.0</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJava() {
            return create("signature.java");
        }

    }

    public static class TomcatLibraryAccessors extends SubDependencyFactory {
        private final TomcatEmbedLibraryAccessors laccForTomcatEmbedLibraryAccessors = new TomcatEmbedLibraryAccessors(owner);

        public TomcatLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>tomcat.embed</b>
         */
        public TomcatEmbedLibraryAccessors getEmbed() {
            return laccForTomcatEmbedLibraryAccessors;
        }

    }

    public static class TomcatEmbedLibraryAccessors extends SubDependencyFactory {

        public TomcatEmbedLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>org.apache.tomcat.embed:tomcat-embed-core</b> coordinates and
         * with version <b>10.1.31</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("tomcat.embed.core");
        }

        /**
         * Dependency provider for <b>core9</b> with <b>org.apache.tomcat.embed:tomcat-embed-core</b> coordinates and
         * with version <b>9.0.89</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore9() {
            return create("tomcat.embed.core9");
        }

    }

    public static class UndertowLibraryAccessors extends SubDependencyFactory {

        public UndertowLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>servlet</b> with <b>io.undertow:undertow-servlet</b> coordinates and
         * with version <b>2.3.18.Final</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getServlet() {
            return create("undertow.servlet");
        }

        /**
         * Dependency provider for <b>servlet22</b> with <b>io.undertow:undertow-servlet</b> coordinates and
         * with version <b>2.2.32.Final</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getServlet22() {
            return create("undertow.servlet22");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>netty</b> with value <b>4.1.110.Final</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getNetty() { return getVersion("netty"); }

        /**
         * Version alias <b>nettytcnative</b> with value <b>2.0.70.Final</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getNettytcnative() { return getVersion("nettytcnative"); }

        /**
         * Version alias <b>opencensus</b> with value <b>0.31.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getOpencensus() { return getVersion("opencensus"); }

        /**
         * Version alias <b>protobuf</b> with value <b>3.25.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getProtobuf() { return getVersion("protobuf"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
