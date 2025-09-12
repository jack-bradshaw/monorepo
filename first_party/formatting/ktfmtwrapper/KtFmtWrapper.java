package com.jackbradshaw.formatting.ktfmtwrapper;

import com.facebook.ktfmt.cli.Main;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Wraps {@link https://github.com/facebook/ktfmt} and removes unnecessary incremental update
 * statements to avoid spamming the console. All arguments passed to main are directly passed
 * through to ktfmt.
 */
public class KtFmtWrapper {

  private static String INTERMEDIATE_UPDATE_PREFIX = "Done formatting";

  public static void main(String[] args) {
    // KtFmt prints incremenetal updates to System.err (odd).
    new Main(System.in, System.out, createFilteredErrorStream(System.err), args).run();
  }

  /**
   * Creates a print stream which filters out lines containing "Done formatting" by buffering them
   * then forwarding any which remain to {@code sink}. Lines are buffered so a delay between input
   * and output is expected.
   *
   * <p>The implemented does not override the array-based write functions, because the super
   * implementation loops over their contents and passes the values to {@code write} already.
   */
  private static PrintStream createFilteredErrorStream(OutputStream sink) {
    FilterOutputStream filter =
        new FilterOutputStream(sink) {

          private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

          @Override
          public void write(int b) throws IOException {
            buffer.write(b);
            if (b == '\n') {
              filterAndForward();
            }
          }

          @Override
          public void flush() throws IOException {
            filterAndForward();
            super.flush();
          }

          @Override
          public void close() throws IOException {
            flush();
            super.close();
          }

          /** Filters the buffer and forwards what remains to {@code sink}. */
          private void filterAndForward() throws IOException {
            if (buffer.toString(StandardCharsets.UTF_8).contains(INTERMEDIATE_UPDATE_PREFIX)) {
              buffer.reset();
            }

            if (buffer.size() > 0) {
              this.out.write(buffer.toByteArray());
              buffer.reset();
            }
          }
        };

    return new PrintStream(filter);
  }
}
