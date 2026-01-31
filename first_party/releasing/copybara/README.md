# Copybara Releasing

Standardized infrastructure for mirroring monorepo directories to standalone repositories using
Google Copybara.

## Overview

This toolset enables the segmentation of the monorepo into independent projects by automating the
promotion of subdirectories to root-level repositories while preserving commit history and
rewriting internal references.

The following tools are provided:

1. [`copybara_release`](/first_party/releasing/copybara/defs.bzl): A Starlark macro to define mirroring targets.
