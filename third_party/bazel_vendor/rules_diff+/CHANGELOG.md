# 1.0.0 (2025-02-12)

### Bug Fixes

- **ape:** switch to `@ape//ape/toolchain/info:*` ([6ac7678](https://git.gitlab.arm.com/bazel/rules_diff/commit/6ac7678d720ea277653b2ffc52b9bccddc076f93))
- **ape:** upgrade to support remote execution ([5af242c](https://git.gitlab.arm.com/bazel/rules_diff/commit/5af242c6a55d7f4db231cd1e208d987df8c580c4))
- bump dependencies ([d6efe79](https://git.gitlab.arm.com/bazel/rules_diff/commit/d6efe798b3ffc870ac800bd4ba5a85bdfa524e8a))
- only register APE toolchains for compatible platforms ([83f9994](https://git.gitlab.arm.com/bazel/rules_diff/commit/83f9994159fb707226294fd53bb12a7853ec9fd9))
- support Apple Silicon ([508b82a](https://git.gitlab.arm.com/bazel/rules_diff/commit/508b82a2b94476e4f25e13f8635568f962b80201))
- switch to `rules_license` for licensing ([50b2503](https://git.gitlab.arm.com/bazel/rules_diff/commit/50b250389651a78667f6211aee7dfead66fd986a))
- update dependencies for Bazel 8 support ([10342df](https://git.gitlab.arm.com/bazel/rules_diff/commit/10342df031320f4d413c2b1c7e35fa9edaabb272))
- upgrade dependencies to get Bazel 8 support ([e7a4e33](https://git.gitlab.arm.com/bazel/rules_diff/commit/e7a4e33f6178f97bb2817854ba4762221bfab9b1))
- upgrade to fixed stable dependency versions ([a442aa9](https://git.gitlab.arm.com/bazel/rules_diff/commit/a442aa91998666d6065dd0b0593e867fab32ce6b))
- upgrade to stable dependency versions ([e1d6740](https://git.gitlab.arm.com/bazel/rules_diff/commit/e1d674012207351dab365e20da254c2e25c312e2))

### chore

- remove `cmp` toolchain ([d3bfca6](https://git.gitlab.arm.com/bazel/rules_diff/commit/d3bfca6b26307b315c92766be68526544e2ce8c8))
- remove `diff3` toolchain ([7c2becd](https://git.gitlab.arm.com/bazel/rules_diff/commit/7c2becdb1d1076b0f310753fd8268c57a643cf97))
- remove `sdiff` toolchain ([7ce398b](https://git.gitlab.arm.com/bazel/rules_diff/commit/7ce398b08dc0be25e63a4bb7a250d7a4b97719fd))

### Code Refactoring

- remove `cli` toolchain ([f5cf3d3](https://git.gitlab.arm.com/bazel/rules_diff/commit/f5cf3d314f862f45b7fa7cee9d512855fd91fb45))

### Features

- add `diff_directory_test` rule ([48646dd](https://git.gitlab.arm.com/bazel/rules_diff/commit/48646dd837cb2e63f0bf0c7e70bf87ca98502e3f))
- add `diff_file_test` rule ([f9d3cac](https://git.gitlab.arm.com/bazel/rules_diff/commit/f9d3cac0539fd47b2dd05d32c655e378654c7f0d))
- add `diff` toolchain ([52318d6](https://git.gitlab.arm.com/bazel/rules_diff/commit/52318d6a9824576c9d0dc4f30adaab8fc89bc727))
- add `diff3`/`sdiff`/`cmp` toolchains ([6738901](https://git.gitlab.arm.com/bazel/rules_diff/commit/67389012deb837f57eec2b403cdf7855db57073e))
- add Batch support for `diff_{file,directory}_test` ([d7ec834](https://git.gitlab.arm.com/bazel/rules_diff/commit/d7ec83401f8c8238906f8c895495df0e8a88ce0c))
- add hermetic `diff` CLI toolchain ([58fd465](https://git.gitlab.arm.com/bazel/rules_diff/commit/58fd465d0f7a3cd73ccc6d921a96fdfe19c185a6))
- added semantic release to bazel template ([a8a6634](https://git.gitlab.arm.com/bazel/rules_diff/commit/a8a663411461dfd66fcc36635b1659a1beb3bd6b))

### BREAKING CHANGES

- `sdiff` toolchain has been removed.

The module no longer supports the `cmp` tool as there are no rules
in the module that use it.

- `diff3` toolchain has been removed.

The module no longer supports the `cmp` tool as there are no rules
in the module that use it.

- `cmp` toolchain has been removed.

The module no longer supports the `cmp` tool as there are no rules
in the module that use it.

- `@rules_diff//diff/toolchain/cli:type` removed.

Use `@rules_diff//diff/toolchain/diff:type` instead.

# [1.0.0-beta.6](https://git.gitlab.arm.com/bazel/rules_diff/compare/v1.0.0-beta.5...v1.0.0-beta.6) (2024-11-26)

### Bug Fixes

- update dependencies for Bazel 8 support ([10342df](https://git.gitlab.arm.com/bazel/rules_diff/commit/10342df031320f4d413c2b1c7e35fa9edaabb272))

# [1.0.0-beta.5](https://git.gitlab.arm.com/bazel/rules_diff/compare/v1.0.0-beta.4...v1.0.0-beta.5) (2024-10-30)

### Bug Fixes

- upgrade dependencies to get Bazel 8 support ([e7a4e33](https://git.gitlab.arm.com/bazel/rules_diff/commit/e7a4e33f6178f97bb2817854ba4762221bfab9b1))

# [1.0.0-beta.4](https://git.gitlab.arm.com/bazel/rules_diff/compare/v1.0.0-beta.3...v1.0.0-beta.4) (2024-07-31)

### Bug Fixes

- **ape:** switch to `@ape//ape/toolchain/info:*` ([6ac7678](https://git.gitlab.arm.com/bazel/rules_diff/commit/6ac7678d720ea277653b2ffc52b9bccddc076f93))

# [1.0.0-beta.3](https://git.gitlab.arm.com/bazel/rules_diff/compare/v1.0.0-beta.2...v1.0.0-beta.3) (2024-05-08)

### Bug Fixes

- bump dependencies ([d6efe79](https://git.gitlab.arm.com/bazel/rules_diff/commit/d6efe798b3ffc870ac800bd4ba5a85bdfa524e8a))

# [1.0.0-beta.2](https://git.gitlab.arm.com/bazel/rules_diff/compare/v1.0.0-beta.1...v1.0.0-beta.2) (2024-05-02)

### Bug Fixes

- **ape:** upgrade to support remote execution ([5af242c](https://git.gitlab.arm.com/bazel/rules_diff/commit/5af242c6a55d7f4db231cd1e208d987df8c580c4))

# 1.0.0-beta.1 (2024-04-25)

### Bug Fixes

- only register APE toolchains for compatible platforms ([83f9994](https://git.gitlab.arm.com/bazel/rules_diff/commit/83f9994159fb707226294fd53bb12a7853ec9fd9))
- support Apple Silicon ([508b82a](https://git.gitlab.arm.com/bazel/rules_diff/commit/508b82a2b94476e4f25e13f8635568f962b80201))

### Code Refactoring

- remove `cli` toolchain ([f5cf3d3](https://git.gitlab.arm.com/bazel/rules_diff/commit/f5cf3d314f862f45b7fa7cee9d512855fd91fb45))

### Features

- add `diff_directory_test` rule ([48646dd](https://git.gitlab.arm.com/bazel/rules_diff/commit/48646dd837cb2e63f0bf0c7e70bf87ca98502e3f))
- add `diff_file_test` rule ([f9d3cac](https://git.gitlab.arm.com/bazel/rules_diff/commit/f9d3cac0539fd47b2dd05d32c655e378654c7f0d))
- add `diff` toolchain ([52318d6](https://git.gitlab.arm.com/bazel/rules_diff/commit/52318d6a9824576c9d0dc4f30adaab8fc89bc727))
- add `diff3`/`sdiff`/`cmp` toolchains ([6738901](https://git.gitlab.arm.com/bazel/rules_diff/commit/67389012deb837f57eec2b403cdf7855db57073e))
- add Batch support for `diff_{file,directory}_test` ([d7ec834](https://git.gitlab.arm.com/bazel/rules_diff/commit/d7ec83401f8c8238906f8c895495df0e8a88ce0c))
- add hermetic `diff` CLI toolchain ([58fd465](https://git.gitlab.arm.com/bazel/rules_diff/commit/58fd465d0f7a3cd73ccc6d921a96fdfe19c185a6))
- added semantic release to bazel template ([a8a6634](https://git.gitlab.arm.com/bazel/rules_diff/commit/a8a663411461dfd66fcc36635b1659a1beb3bd6b))

### BREAKING CHANGES

- `@rules_diff//diff/toolchain/cli:type` removed.

Use `@rules_diff//diff/toolchain/diff:type` instead.
