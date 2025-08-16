// Copyright 2016 The Bazel Authors. All rights reserved.
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
package com.google.devtools.build.android.resources;

import com.google.common.base.MoreObjects;
import com.google.devtools.build.android.DependencyInfo;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

/** Models an int field initializer. */
public final class IntFieldInitializer implements FieldInitializer {

  private static final String DESC = "I";

  private final DependencyInfo dependencyInfo;
  private final Visibility visibility;
  private final String fieldName;
  private final int value;

  private IntFieldInitializer(
      DependencyInfo dependencyInfo, Visibility visibility, String fieldName, int value) {
    this.dependencyInfo = dependencyInfo;
    this.visibility = visibility;
    this.fieldName = fieldName;
    this.value = value;
  }

  public static FieldInitializer of(
      DependencyInfo dependencyInfo, Visibility visibility, String fieldName, String value) {
    // aapt2 --package-id 0x80 (or higher) will produce R.txt values that are outside the range of
    // Integer.decode, e.g. 0x80001000.  javac interprets them as negative integers, do the same
    // here by decoding as a Long and then performing a narrowing primitive conversion to int.
    int intValue = Long.decode(value).intValue();
    return of(dependencyInfo, visibility, fieldName, intValue);
  }

  public static IntFieldInitializer of(
      DependencyInfo dependencyInfo, Visibility visibility, String fieldName, int value) {
    return new IntFieldInitializer(dependencyInfo, visibility, fieldName, value);
  }

  @Override
  public boolean writeFieldDefinition(
      ClassWriter cw, boolean isFinal, boolean annotateTransitiveFields, RPackageId rPackageId) {
    int accessLevel = Opcodes.ACC_STATIC;
    if (visibility != Visibility.PRIVATE) {
      accessLevel |= Opcodes.ACC_PUBLIC;
    }
    if (isFinal) {
      accessLevel |= Opcodes.ACC_FINAL;
    }

    boolean deffered = !isFinal || (rPackageId != null && rPackageId.owns(value));
    FieldVisitor fv = cw.visitField(accessLevel, fieldName, DESC, null, deffered ? null : value);
    if (annotateTransitiveFields
        && dependencyInfo.dependencyType() == DependencyInfo.DependencyType.TRANSITIVE) {
      AnnotationVisitor av =
          fv.visitAnnotation(
              RClassGenerator.PROVENANCE_ANNOTATION_CLASS_DESCRIPTOR, /*visible=*/ true);
      av.visit(RClassGenerator.PROVENANCE_ANNOTATION_LABEL_KEY, dependencyInfo.label());
      av.visitEnd();
    }
    fv.visitEnd();
    return deffered;
  }

  @Override
  public void writeCLInit(InstructionAdapter insts, String className, RPackageId rPackageId) {
    if (rPackageId != null && rPackageId.owns(value)) {
      insts.iconst(value - rPackageId.getPackageId());
      insts.load(1, Type.INT_TYPE);
      insts.add(Type.INT_TYPE);
    } else {
      insts.iconst(value);
    }
    insts.putstatic(className, fieldName, DESC);
  }

  @Override
  public void writeInitSource(Writer writer, boolean finalFields) throws IOException {
    writer.write(
        String.format(
            "        %s static %sint %s = 0x%x;\n",
            visibility != Visibility.PRIVATE ? "public" : "",
            finalFields ? "final " : "",
            fieldName,
            value));
  }

  @Override
  public String getFieldName() {
    return fieldName;
  }

  @Override
  public int getMaxBytecodeSize(boolean withRPackage) {
    if (withRPackage) {
      // LDC_W(3)
      // ILOAD_1(1)
      // IADD(1)
      // PUTSTATIC(3)
      return 8;
    } else {
      // LDC_W(3)
      // PUTSTATIC(3)
      return 6;
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(getClass()).add("value", value).toString();
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IntFieldInitializer) {
      IntFieldInitializer other = (IntFieldInitializer) obj;
      return Objects.equals(dependencyInfo, other.dependencyInfo)
          && Objects.equals(visibility, other.visibility)
          && Objects.equals(fieldName, other.fieldName)
          && value == other.value;
    }
    return false;
  }
}
