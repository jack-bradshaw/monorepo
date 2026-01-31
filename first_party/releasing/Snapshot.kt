package com.jackbradshaw.releasing

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * Orchestrates an Atomic Snapshot release for the monorepo.
 */
fun main(args: Array<String>) {
    val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss"))
    println("Starting Atomic Snapshot release: $timestamp")

    val workspaceRoot = System.getenv("BUILD_WORKSPACE_DIRECTORY") ?: "."
    val firstParty = File(workspaceRoot, "first_party")

    val dryRun = args.contains("--dry-run")

    // 1. Update all BUILD files with the new version
    if (!dryRun) {
        println("Updating version strings in first_party...")
        firstParty.walkTopDown().filter { it.name == "BUILD" }.forEach { file ->
            val content = file.readText()
            val newContent = content.replace(Regex("""coordinates = "com\.jackbradshaw:([^:]+):[^"]+""""), "coordinates = \"com.jackbradshaw:$1:$timestamp\"")
            if (content != newContent) {
                println("Updating ${file.relativeTo(File(workspaceRoot))}")
                file.writeText(newContent)
            }
        }
    } else {
        println("Dry-run mode: skipping version updates.")
    }

    // 2. Discover and run release targets
    println("Discovering release targets...")
    val releaseTargets = runBazelQuery("attr(name, '.*\\.release$', //first_party/...)")
    val mirrorTargets = runBazelQuery("filter(':mirror$', //first_party/...)")

    println("Identified ${releaseTargets.size} Maven release targets and ${mirrorTargets.size} Mirror targets.")

    if (dryRun) {
        println("Dry-run mode: skipping execution.")
        return
    }

    println("Beginning publication...")
    releaseTargets.forEach { target ->
        println("Running $target")
        // Note: This will prompt for password once per target if not managed via env.
        runCommandVerbose(listOf("bazel", "run", target))
    }

    println("Beginning mirroring...")
    mirrorTargets.forEach { target ->
        println("Running $target")
        runCommandVerbose(listOf("bazel", "run", target))
    }

    println("Atomic Snapshot release complete: $timestamp")
}

fun runBazelQuery(query: String): List<String> {
    println("Executing: bazel query '$query'")
    val process = ProcessBuilder("bazel", "query", query)
        .directory(File(System.getenv("BUILD_WORKSPACE_DIRECTORY") ?: "."))
        .start()
    val output = process.inputStream.bufferedReader().readLines()
    val errorOutput = process.errorStream.bufferedReader().readLines()
    val exitCode = process.waitFor()
    if (exitCode != 0) {
        println("Warning: Query failed with exit code $exitCode")
        errorOutput.forEach { println("Stderr: $it") }
    }
    return output.filter { it.isNotBlank() }
}

fun runCommandVerbose(command: List<String>) {
    val process = ProcessBuilder(command)
        .inheritIO()
        .start()
    process.waitFor()
}
