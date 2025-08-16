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

package dagger.hilt.processor.internal.kotlin;



import androidx.room.compiler.processing.XElement;
import androidx.room.compiler.processing.XTypeElement;
import dagger.hilt.processor.internal.ClassNames;
import dagger.internal.codegen.xprocessing.XElements;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory creating Kotlin metadata data objects.
 *
 * <p>The metadata is cache since it can be expensive to parse the information stored in a proto
 * binary string format in the metadata annotation values.
 */
@Singleton
public final class KotlinMetadataFactory {
  private final Map<XTypeElement, KotlinMetadata> metadataCache = new HashMap<>();

  @Inject
  KotlinMetadataFactory() {}

  /**
   * Parses and returns the {@link KotlinMetadata} out of a given element.
   *
   * @throws IllegalStateException if the element has no metadata or is not enclosed in a type
   *     element with metadata. To check if an element has metadata use {@link
   *     KotlinMetadataUtil#hasMetadata(XElement)}
   */
  public KotlinMetadata create(XElement element) {
    XTypeElement enclosingElement = XElements.closestEnclosingTypeElement(element);
    if (!enclosingElement.hasAnnotation(ClassNames.KOTLIN_METADATA)) {
      throw new IllegalStateException("Missing @Metadata for: " + enclosingElement);
    }
    return metadataCache.computeIfAbsent(enclosingElement, KotlinMetadata::from);
  }
}
