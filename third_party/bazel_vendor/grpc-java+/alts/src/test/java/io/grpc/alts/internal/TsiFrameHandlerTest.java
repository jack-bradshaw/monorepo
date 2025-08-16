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

package io.grpc.alts.internal;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import java.security.GeneralSecurityException;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit tests for {@link TsiFrameHandler}. */
@RunWith(JUnit4.class)
public class TsiFrameHandlerTest {

  @Rule
  public final TestRule globalTimeout = new DisableOnDebug(Timeout.seconds(5));

  private final TsiFrameHandler tsiFrameHandler = new TsiFrameHandler(new IdentityFrameProtector());
  private final EmbeddedChannel channel = new EmbeddedChannel(tsiFrameHandler);

  @Test
  public void writeAndFlush_handshakeSucceed() throws InterruptedException {
    ByteBuf msg = Unpooled.copiedBuffer("message after handshake finished", CharsetUtil.UTF_8);

    channel.writeAndFlush(msg);
    Object actual = channel.readOutbound();

    assertThat(actual).isEqualTo(msg);
    channel.close().sync();
    channel.checkException();
  }

  @Test
  public void writeAndFlush_shouldBeIgnoredAfterClose() throws InterruptedException {
    channel.close().sync();
    ByteBuf msg = Unpooled.copiedBuffer("message after closed", CharsetUtil.UTF_8);

    channel.writeAndFlush(msg);

    assertThat(channel.outboundMessages()).isEmpty();
    try {
      channel.checkException();
    } catch (Exception e) {
      throw new AssertionError(
          "Any attempt after close should be ignored without out exception", e);
    }
  }

  @Test
  public void close_shouldFlushRemainingMessage() throws InterruptedException {
    ByteBuf msg = Unpooled.copiedBuffer("message after handshake failed", CharsetUtil.UTF_8);
    channel.write(msg);

    assertThat(channel.outboundMessages()).isEmpty();

    channel.close().sync();
    Object actual = channel.readOutbound();

    assertWithMessage("pending write should be flushed on close").that(actual).isEqualTo(msg);
    channel.checkException();
  }

  @Test
  public void flushAfterCloseShouldWork() throws InterruptedException {
    ByteBuf msg = Unpooled.copiedBuffer("message after handshake failed", CharsetUtil.UTF_8);
    channel.write(msg);

    channel.pipeline().addFirst(new ChannelOutboundHandlerAdapter() {
      @Override
      public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        // We have to call flush while doing a close, since close() tears down the pipeline
        // immediately after.
        channel.flush();
        super.close(ctx, promise);
      }
    });

    assertThat(channel.outboundMessages()).isEmpty();

    channel.close().sync();
    Object actual = channel.readOutbound();

    assertWithMessage("pending write should be flushed on close").that(actual).isEqualTo(msg);
    channel.checkException();
  }

  private static final class IdentityFrameProtector implements TsiFrameProtector {

    @Override
    public void protectFlush(List<ByteBuf> unprotectedBufs, Consumer<ByteBuf> ctxWrite,
        ByteBufAllocator alloc) throws GeneralSecurityException {
      for (ByteBuf unprotectedBuf : unprotectedBufs) {
        ctxWrite.accept(unprotectedBuf);
      }
    }

    @Override
    public void unprotect(ByteBuf in, List<Object> out, ByteBufAllocator alloc)
        throws GeneralSecurityException {
      out.add(in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void destroy() {}
  }
}
