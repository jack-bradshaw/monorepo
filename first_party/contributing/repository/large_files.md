# Large File Directives

Directives for handling large files in this repository.

## Definitions

Large file: Any file greater than or equal to 100MB in size.

## Scope

All large files and use of Git LFS in this repository must conform to these directives, including
the contents of [third_party](/third_party), since they are checked in to version control.

## Standard: No Git LFS

Git LFS must not be used. This ensures the repository remains the single source of truth for all
content.

## Practice: File Decomposition

Large files should be decomposed into smaller files before submission.
