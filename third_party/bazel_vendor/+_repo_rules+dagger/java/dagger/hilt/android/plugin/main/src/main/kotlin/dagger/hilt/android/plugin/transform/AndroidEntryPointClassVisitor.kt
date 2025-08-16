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

package dagger.hilt.android.plugin.transform

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * ASM Adapter that transforms @AndroidEntryPoint-annotated classes to extend the Hilt
 * generated android class, including the @HiltAndroidApp application class.
 */
class AndroidEntryPointClassVisitor(
  private val apiVersion: Int,
  nextClassVisitor: ClassVisitor,
  private val classContext: ClassContext
) : ClassVisitor(apiVersion, nextClassVisitor) {

  abstract class Factory : AsmClassVisitorFactory<InstrumentationParameters.None> {
    override fun createClassVisitor(
      classContext: ClassContext,
      nextClassVisitor: ClassVisitor
    ): ClassVisitor {
      return AndroidEntryPointClassVisitor(
        apiVersion = instrumentationContext.apiVersion.get(),
        nextClassVisitor = nextClassVisitor,
        classContext = classContext
      )
    }

    /**
     * Check if a class should be transformed.
     *
     * Only classes that are an Android entry point should be transformed.
     */
    override fun isInstrumentable(classData: ClassData) =
      classData.classAnnotations.any { ANDROID_ENTRY_POINT_ANNOTATIONS.contains(it) }
  }

  // The name of the Hilt generated superclass in it internal form.
  // e.g. "my/package/Hilt_MyActivity"
  lateinit var newSuperclassName: String

  lateinit var oldSuperclassName: String

  override fun visit(
    version: Int,
    access: Int,
    name: String,
    signature: String?,
    superName: String?,
    interfaces: Array<out String>?
  ) {
    val packageName = name.substringBeforeLast('/')
    val className = name.substringAfterLast('/')
    newSuperclassName =
      packageName + "/Hilt_" + className.replace("$", "_")
    oldSuperclassName = superName ?: error { "Superclass of $name is null!" }
    val newSignature = signature?.replaceFirst(oldSuperclassName, newSuperclassName)
    super.visit(version, access, name, newSignature, newSuperclassName, interfaces)
  }

  override fun visitMethod(
    access: Int,
    name: String,
    descriptor: String,
    signature: String?,
    exceptions: Array<out String>?
  ): MethodVisitor {
    val nextMethodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
    val invokeSpecialVisitor = InvokeSpecialAdapter(
      apiVersion = apiVersion,
      nextClassVisitor = nextMethodVisitor,
      isConstructor = name == "<init>"
    )
    if (name == ON_RECEIVE_METHOD_NAME &&
      descriptor == ON_RECEIVE_METHOD_DESCRIPTOR &&
      hasOnReceiveBytecodeInjectionMarker()
    ) {
      return OnReceiveAdapter(apiVersion, invokeSpecialVisitor)
    }
    return invokeSpecialVisitor
  }

  /**
   * Adapter for super calls (e.g. super.onCreate()) that rewrites the owner reference of the
   * invokespecial instruction to use the new superclass.
   *
   * The invokespecial instruction is emitted for code that between other things also invokes a
   * method of a superclass of the current class. The opcode invokespecial takes two operands, each
   * of 8 bit, that together represent an address in the constant pool to a method reference. The
   * method reference is computed at compile-time by looking the direct superclass declaration, but
   * at runtime the code behaves like invokevirtual, where as the actual method invoked is looked up
   * based on the class hierarchy.
   *
   * However, it has been observed that on APIs 19 to 22 the Android Runtime (ART) jumps over the
   * direct superclass and into the method reference class, causing unexpected behaviours.
   * Therefore, this method performs the additional transformation to rewrite direct super call
   * invocations to use a method reference whose class in the pool is the new superclass.
   *
   * @see: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html#jvms-6.5.invokespecial
   * @see: https://source.android.com/devices/tech/dalvik/dalvik-bytecode
   */
  inner class InvokeSpecialAdapter(
    apiVersion: Int,
    nextClassVisitor: MethodVisitor,
    private val isConstructor: Boolean
  ) : MethodVisitor(apiVersion, nextClassVisitor) {

    // Flag to know that we have visited the first invokespecial instruction in a constructor call
    // which corresponds to the `super()` constructor call required as the first statement of an
    // overridden constructor body.
    private var visitedSuperConstructorInvokeSpecial = false

    override fun visitMethodInsn(
      opcode: Int,
      owner: String,
      name: String,
      descriptor: String,
      isInterface: Boolean
    ) {
      if (opcode == Opcodes.INVOKESPECIAL && owner == oldSuperclassName) {
        // Update the owner of INVOKESPECIAL instructions, including those found in constructors.
        super.visitMethodInsn(opcode, getAdaptedOwner(name) ?: owner, name, descriptor, isInterface)
      } else {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
      }
    }

    // Gets the updated owner of an INVOKESPECIAL found in the method being visited.
    private fun getAdaptedOwner(methodRefName: String): String? {
      // If the method reference is a constructor and we are visiting a constructor then only the
      // first INVOKESPECIAL instruction found should be transformed since that correponds to the
      // super constructor call.
      if (methodRefName == "<init>" && isConstructor && !visitedSuperConstructorInvokeSpecial) {
        visitedSuperConstructorInvokeSpecial = true
        return newSuperclassName
      }
      // If the method reference is not a constructor then the instruction for a super call that
      // should be transformed.
      if (methodRefName != "<init>") {
        return newSuperclassName
      }
      return null
    }
  }

  /**
   * Method adapter for a BroadcastReceiver's onReceive method to insert a super call since with
   * its new superclass, onReceive will no longer be abstract (it is implemented by Hilt generated
   * receiver).
   */
  inner class OnReceiveAdapter(
    apiVersion: Int,
    nextClassVisitor: MethodVisitor
  ) : MethodVisitor(apiVersion, nextClassVisitor) {
    override fun visitCode() {
      super.visitCode()
      super.visitIntInsn(Opcodes.ALOAD, 0) // Load 'this'
      super.visitIntInsn(Opcodes.ALOAD, 1) // Load method param 1 (Context)
      super.visitIntInsn(Opcodes.ALOAD, 2) // Load method param 2 (Intent)
      super.visitMethodInsn(
        Opcodes.INVOKESPECIAL,
        newSuperclassName,
        ON_RECEIVE_METHOD_NAME,
        ON_RECEIVE_METHOD_DESCRIPTOR,
        false
      )
    }
  }

  /**
   * Check if Hilt generated class is a BroadcastReceiver with the marker annotation which means
   * a super.onReceive invocation has to be inserted in the implementation.
   */
  private fun hasOnReceiveBytecodeInjectionMarker(): Boolean {
    val newSuperclassFQName = newSuperclassName.toFQName()
    return classContext.loadClassData(newSuperclassFQName)
      ?.classAnnotations?.contains(ON_RECEIVE_MARKER_ANNOTATION)
      ?: error("Cannot load class $newSuperclassFQName!")
  }

  /**
   * Return a fully qualified name from an internal name.
   * See https://asm.ow2.io/javadoc/org/objectweb/asm/Type.html#getInternalName()
   */
  private fun String.toFQName() = this.replace('/', '.')

  companion object {
    val ANDROID_ENTRY_POINT_ANNOTATIONS = setOf(
      "dagger.hilt.android.AndroidEntryPoint",
      "dagger.hilt.android.HiltAndroidApp"
    )
    const val ON_RECEIVE_METHOD_NAME = "onReceive"
    const val ON_RECEIVE_METHOD_DESCRIPTOR =
      "(Landroid/content/Context;Landroid/content/Intent;)V"
    const val ON_RECEIVE_MARKER_ANNOTATION = "dagger.hilt.android.internal.OnReceiveBytecodeInjectionMarker"
  }
}
