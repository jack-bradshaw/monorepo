package com.jackbradshaw.publicity.conformance.packagechecker

import com.jackbradshaw.publicity.conformance.WorkspaceRoot
import com.jackbradshaw.publicity.conformance.packagechecker.PackageChecker.Result
import java.io.File
import javax.inject.Inject
import net.starlark.java.syntax.Argument
import net.starlark.java.syntax.AssignmentStatement
import net.starlark.java.syntax.CallExpression
import net.starlark.java.syntax.Identifier
import net.starlark.java.syntax.LoadStatement
import net.starlark.java.syntax.ParserInput
import net.starlark.java.syntax.StarlarkFile
import net.starlark.java.syntax.StringLiteral

/** [PackageChecker] that uses a Starlark AST. */
class PackageCheckerImpl @Inject constructor(@WorkspaceRoot private val workspaceRoot: File) :
    PackageChecker {

  override fun validate(packageDir: File): Result {
    val publicityFile = findPublicityFile(packageDir)

    if (publicityFile == null) {
      return Result.Failure(
          "${packageDir.path} must contain a file named publicity.bzl, but none exists")
    }

    val publicityFileContents =
        StarlarkFile.parse(ParserInput.fromUTF8(publicityFile.readBytes(), publicityFile.path))
    val publicityFunctionsByLoadAlias = parseLoadedPublicityFunctions(publicityFileContents)

    val publicityDeclaration =
        publicityFileContents.statements.filterIsInstance<AssignmentStatement>().find {
          (it.lhs as? Identifier)?.name == "PUBLICITY"
        }

    if (publicityDeclaration == null) {
      return Result.Failure(
          "${publicityFile.path} must contain a variable named PUBLICITY, but none exists")
    }

    return validateAssignment(
        publicityDeclaration, publicityFile, packageDir, publicityFunctionsByLoadAlias)
  }

  private fun findPublicityFile(packageDir: File) =
      packageDir.listFiles()?.find { it.name == "publicity.bzl" }

  private fun parseLoadedPublicityFunctions(file: StarlarkFile): Map<String, Publicity> {
    return file.statements
        .filterIsInstance<LoadStatement>()
        .filter { it.getImport().value.contains("first_party/publicity:defs.bzl") }
        .flatMap { it.bindings }
        .mapNotNull { binding ->
          Publicity.from(binding.originalName.name)?.let { binding.localName.name to it }
        }
        .toMap()
  }

  /** Checks [assignment] for conformance and returns the result. */
  private fun validateAssignment(
      assignment: AssignmentStatement,
      publicityFile: File,
      packageDir: File,
      typesByAlias: Map<String, Publicity>
  ): Result {
    val callExpression = assignment.rhs as? CallExpression
    if (callExpression == null) {
      return Result.Failure(
          "PUBLICITY in ${publicityFile.path} must be assigned a function call, but found " +
              "${assignment.rhs}.")
    }

    val functionName: String? = (callExpression.function as? Identifier)?.name
    if (functionName == null) {
      return Result.Failure(
          "PUBLICITY in ${publicityFile.path} must be assigned a direct function call, but found " +
              "${callExpression.function}.")
    }

    val publicityType = typesByAlias[functionName]
    if (publicityType == null) {
      return Result.Failure(
          "PUBLICITY in ${publicityFile.path} must be assigned a direct call to `public`, " +
              "`internal`, `restricted` or `quarantined` (or a load alias), but found " +
              "\'$functionName\'.")
    }

    return when (publicityType) {
      Publicity.Public -> Result.Success
      Publicity.Internal -> Result.Success
      Publicity.Restricted -> Result.Success
      Publicity.Quarantined -> validateQuarantinedCall(callExpression, packageDir, publicityFile)
    }
  }

  /** Checks [call] for compliance with the quarantine-specific rules and returns the result. */
  private fun validateQuarantinedCall(
      call: CallExpression,
      packageDir: File,
      publicityFile: File
  ): Result {
    val packageIdentifier = getBazelPackageIdentifier(packageDir)
    val packageArg =
        ((call.arguments.firstOrNull() as? Argument.Positional)?.value as? StringLiteral)?.value

    if (packageArg != packageIdentifier) {
      return Result.Failure(
          "quarantined() in ${publicityFile.path} must be passed the enclosing package (\'$packageIdentifier\') but \'$packageArg\' was found.")
    }

    return Result.Success
  }

  /** Gets the bazel package identifier of the package at [packageDir] (e.g. //foo/bar). */
  private fun getBazelPackageIdentifier(packageDir: File): String {
    val relativePath = packageDir.relativeTo(workspaceRoot).path
    return "//$relativePath"
  }

  /** The supported publicity options. */
  private enum class Publicity(val starlarkFunctionName: String) {
    Public("public"),
    Internal("internal"),
    Restricted("restricted"),
    Quarantined("quarantined");

    companion object {
      /** Gets the value equivalent to [starlarkFunctionName] (with reference to defs.bzl). */
      fun from(starlarkFunctionName: String): Publicity? {
        return values().find { it.starlarkFunctionName == starlarkFunctionName }
      }
    }
  }
}
