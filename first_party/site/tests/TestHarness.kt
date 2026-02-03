package com.jackbradshaw.site.tests

import com.google.common.truth.Truth.assertWithMessage
import com.google.devtools.build.runfiles.Runfiles
import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.uri
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking

/**
 * Runs the site in a local server and provides instrumentation for accessing it.
 *
 * The [setup] function must be called before using the harness, and the [tearDown] function must be
 * called when the harness is no longer needed.
 */
class TestHarness {

  /** The root directory of the site in [runfiles]. */
  private lateinit var site: File

  /** A local server that serves the site from [site]. */
  private lateinit var server: ApplicationEngine

  /**
   * The port the server is running on, assigned a meaningful value once the [server] has been
   * started (-1 until then).
   */
  private var port: Int = -1

  /** Instrumentation to access/control the site for testing. */
  private lateinit var playwright: Playwright

  /** The browser that renders the site for testing. */
  private lateinit var browser: Browser

  /** The context associated with [browser]. */
  private lateinit var browserContext: com.microsoft.playwright.BrowserContext

  /**
   * Starts the server, sets the browser screen width to [screenWidth], and optionally grants cookie
   * consent.
   */
  fun setup(screenWidth: ScreenWidth = ScreenWidth.MEDIUM, cookieConsentGranted: Boolean = true) {
    setupSite()
    setupInstrumentation(screenWidth)
    setupServer()

    if (cookieConsentGranted) grantCookieConsent()
  }

  /**
   * Releases the resources used by this harness. This function is safe to call even if [setup] was
   * never called.
   */
  fun tearDown() {
    if (::browserContext.isInitialized) browserContext.close()
    if (::browser.isInitialized) browser.close()
    if (::playwright.isInitialized) playwright.close()
    if (::server.isInitialized) server.stop(0, 0)
  }

  /** Opens a new browser page at [page] (relative to the site root). */
  fun openPage(page: URI): Page =
      browserContext.newPage().apply {
        navigate(getServerEndpoint().resolve(page).toString())
        waitForLoad()
      }

  /** Returns the runfile at [path] (relative to the runfiles root). */
  fun getRunfile(path: Path): Path = Paths.get(runfiles.rlocation(path.toString()))

  /** Returns the endpoint of the server, as a URI containing protocol, host, and port. */
  fun getServerEndpoint(): URI = URI("http", null, HOSTNAME, port, null, null, null)

  /**
   * Captures a screenshot of [page] and verifies it matches the golden screenshot.
   *
   * If the comparison fails, the new screenshot is saved to the test outputs (details in logs).
   *
   * Expects to be run in a sharded test (i.e. shard_count set in Bazel test rule) and fails if not.
   *
   * @param page The page to screenshot.
   * @param goldenName The unique name of the golden file (e.g., "TestClass_testMethod.png").
   */
  fun checkScreendiff(page: Page, goldenName: String) {
    val screenshot = page.captureScreenshot()
    val goldenPath = getRunfile(GOLDEN_DIR.resolve(goldenName))
    val goldenFile = goldenPath.toFile()

    check(goldenFile.exists()) {
      val savePath = saveScreenshot(screenshot, goldenName)
      "Golden file $goldenPath does not exist. Latest saved to $savePath"
    }

    if (!goldenFile.readBytes().contentEquals(screenshot)) {
      val savePath = saveScreenshot(screenshot, goldenName)
      assertWithMessage("Screenshot and golden do not match. Latest saved to $savePath").fail()
    }
  }

  /** Loads [site]. */
  private fun setupSite() {
    site =
        File(runfiles.rlocation("_main/first_party/site/site")).also {
          check(it.exists()) { "Site not found. Expected at $it in runfiles." }
        }
  }

  /** Initializes [playwright], [browser], and [browserContext]. */
  private fun setupInstrumentation(width: ScreenWidth) {
    // Find the browser executable to use it directly, bypassing Playwright's internal
    // browser resolution which often fails in hermetic Bazel environments.
    // The path below is derived from the rules_playwright bzlmod canonical name.
    val browserExecutable = runfiles.rlocation("rules_playwright++playwright+playwright/browsers/mac14-arm64/chromium_headless_shell-1148/chrome-mac/headless_shell")
        ?: runfiles.rlocation("rules_playwright++playwright+playwright/browsers/mac14-x64/chromium_headless_shell-1148/chrome-mac/headless_shell")
        ?: runfiles.rlocation("rules_playwright++playwright+playwright/browsers/linux-x64/chromium_headless_shell-1148/headless_shell")

    val env = System.getenv().toMutableMap()
    if (browserExecutable != null) {
      env["PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD"] = "1"
      val browsersDir = File(browserExecutable).parentFile.parentFile
      env["PLAYWRIGHT_BROWSERS_PATH"] = browsersDir.absolutePath
    }

    playwright = Playwright.create(Playwright.CreateOptions().setEnv(env))

    // Reduce test flakiness by disabling GPU acceleration and other non-deterministic features.
    val launchOptions =
        BrowserType.LaunchOptions()
            .setHeadless(true)
            .setArgs(
                listOf(
                    "--disable-gpu",
                    "--disable-font-subpixel-positioning",
                    "--disable-lcd-text",
                    "--disable-threaded-animation",
                    "--disable-threaded-scrolling",
                    "--disable-in-process-stack-traces",
                    "--disable-checker-imaging",
                    "--force-color-profile=srgb"))
    
    if (browserExecutable != null) {
      launchOptions.setExecutablePath(Paths.get(browserExecutable))
    }

    browser = playwright.chromium().launch(launchOptions)

    browserContext =
        browser.newContext(
            Browser.NewContextOptions().setViewportSize(width.asPixels, VIEW_PORT_HEIGHT_PX))
    browserContext.setupTimeouts()
  }

  /** Initializes [server] and assigns its port to [port]. */
  private fun setupServer() {
    server =
        embeddedServer(Netty, port = RANDOM_PORT_SELECTOR, host = HOSTNAME) {
          routing {
            get("/{...}") {
              val responseFile = call.request.toSiteFile()
              if (responseFile.exists() && responseFile.isFile) {
                val contentType =
                    when {
                      responseFile.name.endsWith(".html") -> ContentType.Text.Html
                      responseFile.name.endsWith(".css") -> ContentType.Text.CSS
                      responseFile.name.endsWith(".js") -> ContentType.Application.JavaScript
                      responseFile.name.endsWith(".png") -> ContentType.Image.PNG
                      responseFile.name.endsWith(".jpg") -> ContentType.Image.JPEG
                      else -> ContentType.Application.OctetStream
                    }
                call.respondBytes(responseFile.readBytes(), contentType)
              } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
              }
            }
          }
        }

    server.start(wait = false)

    runBlocking { port = server.resolvedConnectors().first().port }
  }

  /** Sets the cookie consent state to `true`. */
  private fun grantCookieConsent() {
    browserContext.addInitScript(
        "window.localStorage.setItem('analytics_consent_granted', 'true');")
  }

  /** Resolves the file in [site] that satisfies this request. */
  private fun ApplicationRequest.toSiteFile(): File {
    val path = if (uri == "/" || uri.isEmpty()) "index.html" else uri.removePrefix("/")
    val location = File(site, path)

    return if (location.isDirectory) File(location, "index.html") else location
  }

  /**
   * Sets the default navigation and timeout for this context to 60 seconds to prevent tests from
   * running indefinitely when blocked.
   */
  private fun BrowserContext.setupTimeouts() {
    setDefaultNavigationTimeout(TimeUnit.SECONDS.toMillis(60).toDouble())
    setDefaultTimeout(TimeUnit.SECONDS.toMillis(60).toDouble())
  }

  /**
   * Saves [screenshot] to the during-test output directory in a file named [goldenName], and
   * returns the absolute path to the file in the post-test output directory.
   */
  private fun saveScreenshot(screenshot: ByteArray, goldenName: String): Path {
    getDuringTestScreenshotOutputLocation(goldenName).toFile().writeBytes(screenshot)
    return getPostTestScreenshotOutputLocation(goldenName)
  }

  /**
   * The directory where [goldenName] can be saved during test execution to keep it after the test
   * completes.
   */
  private fun getDuringTestScreenshotOutputLocation(goldenName: String): Path =
      Paths.get(System.getenv("TEST_UNDECLARED_OUTPUTS_DIR"))
          .also {
            check(it != null) { "TEST_UNDECLARED_OUTPUTS_DIR environmental variable not set." }
          }
          .resolve(goldenName)

  /**
   * Gets the path to the test output directory where Bazel moves the screenshot named [goldenName]
   * after the test completes.
   */
  private fun getPostTestScreenshotOutputLocation(goldenName: String): Path =
      Paths.get("bazel-testlogs/first_party/site/tests")
          .resolve(generateTestShardPathString())
          .resolve("test.outputs")
          .resolve(goldenName)

  /**
   * Generates a path string for the current test shard (e.g. "shard_1_of_2").
   *
   * Throws an [IllegalStateException] if the TEST_SHARD_INDEX or TEST_TOTAL_SHARDS environment
   * variables are not set.
   */
  private fun generateTestShardPathString(): String {
    val shardIndexEnv = System.getenv("TEST_SHARD_INDEX")
    val shardCountEnv = System.getenv("TEST_TOTAL_SHARDS")

    if (shardIndexEnv == null || shardCountEnv == null) {
      return "shard_1_of_1"
    }

    // Add 1 because the index is 0-based, but the test output dir is named using a 1-based index.
    val shardIndex = shardIndexEnv.toInt().plus(1)
    val shardCount = shardCountEnv.toInt()

    return "shard_${shardIndex}_of_${shardCount}"
  }

  companion object {
    /** Files from Bazel dependencies. */
    private val runfiles = Runfiles.preload().unmapped()

    /** The hostname for the server. */
    private const val HOSTNAME = "localhost"

    /** The value which requests a random port when passed to [embeddedServer]. */
    private const val RANDOM_PORT_SELECTOR = 0

    /** The height of the browser viewport, measured in pixels. */
    private const val VIEW_PORT_HEIGHT_PX = 1080

    /** The directory where screendiff goldens are stored, relative to the runfiles root. */
    private val GOLDEN_DIR = Paths.get("_main/first_party/site/tests/goldens")
  }
}

/** The horizontal width of a browser viewport. */
enum class ScreenWidth(val asPixels: Int) {
  /** A small screen (e.g. phone). */
  SMALL(480),

  /** A medium screen (e.g. tablet). */
  MEDIUM(1024),

  /** A large screen (e.g. desktop). */
  LARGE(1920)
}
