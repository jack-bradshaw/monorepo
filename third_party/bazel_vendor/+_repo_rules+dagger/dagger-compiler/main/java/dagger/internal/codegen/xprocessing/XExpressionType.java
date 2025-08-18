/*
 * Copyright (C) 2022 The Dagger Authors.
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

package dagger.internal.codegen.xprocessing;

import static com.google.common.base.Preconditions.checkState;

import androidx.room.compiler.codegen.XClassName;
import androidx.room.compiler.codegen.XTypeName;
import androidx.room.compiler.processing.XProcessingEnv;
import androidx.room.compiler.processing.XRawType;
import androidx.room.compiler.processing.XType;
import androidx.room.compiler.processing.compat.XConverters;
import com.squareup.javapoet.TypeName;
import java.util.Optional;

/** The type of an {@link XExpression} that can represent both {@link XType} or {@link XRawType}. */
// TODO(bcorso): It would be nice if XType and XRawType shared some basic interface with some of
// the common methods so that we wouldn't need to create this XExpressionType to make things work.
public final class XExpressionType {
  public static XExpressionType create(XType type) {
    return new XExpressionType(
        Optional.of(type), Optional.empty(), XConverters.getProcessingEnv(type));
  }

  static XExpressionType create(XRawType type, XProcessingEnv processingEnv) {
    return new XExpressionType(Optional.empty(), Optional.of(type), processingEnv);
  }

  public static XExpressionType createRawType(XType type) {
    return create(type.getRawType(), XConverters.getProcessingEnv(type));
  }

  private final Optional<XType> optionalType;
  private final Optional<XRawType> optionalRawType;
  private final XProcessingEnv processingEnv;

  private XExpressionType(
      Optional<XType> optionalType,
      Optional<XRawType> optionalRawType,
      XProcessingEnv processingEnv) {
    this.optionalType = optionalType;
    this.optionalRawType = optionalRawType;
    this.processingEnv = processingEnv;
    checkState(optionalType.isPresent() || optionalRawType.isPresent());
  }

  public XExpressionType unwrapType() {
    return optionalType.isPresent() && !XTypes.isRawParameterizedType(optionalType.get())
        ? XExpressionType.create(XProcessingEnvs.unwrapType(optionalType.get()))
        : XExpressionType.create(processingEnv.requireType(TypeName.OBJECT));
  }

  public XExpressionType wrapType(XClassName wrapper) {
    return optionalType.isPresent()
        ? XExpressionType.create(
            XProcessingEnvs.wrapType(wrapper, optionalType.get(), processingEnv))
        // If the current type is a raw type then we just return the wrapper type as a raw type too.
        // This isn't really accurate, but it's the best we can do with XProcessing's type system.
        // For example, if the current type is a raw type, Foo, then Provider<Foo> is not allowed so
        // we return the raw Provider type.
        : XExpressionType.createRawType(processingEnv.requireType(wrapper));
  }

  public XExpressionType rewrapType(XClassName wrapper) {
    return optionalType.isPresent()
        ? XExpressionType.create(XTypes.rewrapType(optionalType.get(), wrapper))
        : XExpressionType.createRawType(processingEnv.requireType(wrapper));
  }

  public TypeName getTypeName() {
    return optionalType.isPresent()
        ? optionalType.get().getTypeName()
        : optionalRawType.get().getTypeName();
  }

  public XTypeName asTypeName() {
    return optionalType.isPresent()
        ? optionalType.get().asTypeName()
        : optionalRawType.get().asTypeName();
  }

  public boolean isSameType(XType type) {
    return optionalType.isPresent()
        ? optionalType.get().isSameType(type)
        : XTypes.isRawParameterizedType(type)
            && getTypeName().equals(type.getTypeName());
  }

  public boolean isSameType(XRawType type) {
    return getTypeName().equals(type.getTypeName());
  }

  public boolean isAssignableTo(XType type) {
    return optionalType.isPresent()
        ? type.isAssignableFrom(optionalType.get())
        : type.getRawType().isAssignableFrom(optionalRawType.get());
  }

  public boolean isAssignableTo(XRawType rawType) {
    return optionalType.isPresent()
        ? rawType.isAssignableFrom(optionalType.get())
        : rawType.isAssignableFrom(optionalRawType.get());
  }

  Optional<XType> asType() {
    return optionalType;
  }

  Optional<XRawType> asRawType() {
    return optionalRawType;
  }

  XProcessingEnv getProcessingEnv() {
    return processingEnv;
  }
}
