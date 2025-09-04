## [1.0.1](https://git.gitlab.arm.com/bazel/download_utils/compare/v1.0.0...v1.0.1) (2025-02-12)

### Bug Fixes

- upgrade to `toolchain_utils@1.0.2` ([c02f469](https://git.gitlab.arm.com/bazel/download_utils/commit/c02f469c5c424e183b25368e85a65cea215bc0d9))

# 1.0.0 (2025-01-22)

### Bug Fixes

- **commands:** add Bazel 8 `+` separator support ([8f191be](https://git.gitlab.arm.com/bazel/download_utils/commit/8f191be671e12e9e6485179b27f4b462be4a0a61))
- **commands:** support Bazel 8 naming of `use_repo_rule` ([f1aee87](https://git.gitlab.arm.com/bazel/download_utils/commit/f1aee877e10375658a10b0afe215b62482c03023))
- **e2e:** change to BCR `toolchain_utils` ([4e72ac5](https://git.gitlab.arm.com/bazel/download_utils/commit/4e72ac597ca564cef6dddd5195cb2d09adbacf0e))
- embed download data into `WORKSPACE` to fix cache invalidation ([e475caf](https://git.gitlab.arm.com/bazel/download_utils/commit/e475cafa5535e39e87b71179bfd36213c9ff365a))
- **rules_download:** make intergrity optional ([24fdc84](https://git.gitlab.arm.com/bazel/download_utils/commit/24fdc840fa1d156219795b6167b4068bcb3a4547))
- **rules_download:** remove need for `http_archive` ([cd6d57c](https://git.gitlab.arm.com/bazel/download_utils/commit/cd6d57c6039e072920e6845ad1de85c3760372fd))
- scope tool labels to project ([dc65387](https://git.gitlab.arm.com/bazel/download_utils/commit/dc65387a7394d23278b13e8ff05409d08d9a9fbb))
- set minimum version to Bazel 7 ([62659e4](https://git.gitlab.arm.com/bazel/download_utils/commit/62659e4177e853fb2695f31b4b126f5112906566))
- update dependencies for Bazel 8 support ([eef0671](https://git.gitlab.arm.com/bazel/download_utils/commit/eef0671705d34b6693ca3bf909e2337b5ea8a351))
- update dependencies for Bazel 8 support ([39f3718](https://git.gitlab.arm.com/bazel/download_utils/commit/39f37182454a4d1137d1554f205ad45aa0f616a5))
- upgrade `rules_toolchain` ([8c8b033](https://git.gitlab.arm.com/bazel/download_utils/commit/8c8b03393a45f7f9a643413cb2095ad4f663b150))
- upgrade to `toolchain_utils@1.0.1` ([8e8498b](https://git.gitlab.arm.com/bazel/download_utils/commit/8e8498be4ae9363c1768661c7423dca72b1f30b1))
- use `rules_license` to define license ([68c1b83](https://git.gitlab.arm.com/bazel/download_utils/commit/68c1b831cbab394253727387eb0e6e68c8e1c0b2))
- use `rules_toolchain` pre-release registry ([f449dcf](https://git.gitlab.arm.com/bazel/download_utils/commit/f449dcff560b61644212f9f2be8406066fe30482))

### Code Refactoring

- change `symlinks` to `links` ([cf1ebd8](https://git.gitlab.arm.com/bazel/download_utils/commit/cf1ebd8fe1fd0e9cfdf90e9e3ac2504f959d0f51))
- name changed to `download_utils` ([8e583ab](https://git.gitlab.arm.com/bazel/download_utils/commit/8e583abe439ae26d61c34fb9a56397ae7b5b2f2e))

### Features

- add `extension` attribute to `download_archive` ([c476a81](https://git.gitlab.arm.com/bazel/download_utils/commit/c476a8105c623a23996421b68bcbf08c20b6a5ac))
- add `symlinks` attribute to rules ([7d0d27a](https://git.gitlab.arm.com/bazel/download_utils/commit/7d0d27a5dd4fed84a36285eda4020d6b7003e70e))
- add Windows support ([8934621](https://git.gitlab.arm.com/bazel/download_utils/commit/8934621f44cf7a44788886917e0795da92d4f922))
- added semantic release to bazel template ([a8a6634](https://git.gitlab.arm.com/bazel/download_utils/commit/a8a663411461dfd66fcc36635b1659a1beb3bd6b))
- remove `coreutils` ([6d3dccc](https://git.gitlab.arm.com/bazel/download_utils/commit/6d3dccccb46c6d71f10a0b0e92d0b5dec6af4f01))
- rules for downloading ([0ebae87](https://git.gitlab.arm.com/bazel/download_utils/commit/0ebae87f7901be40f8a157a3ea5f74395f1548be))
- **rules_download:** add `deb` extension ([cd44830](https://git.gitlab.arm.com/bazel/download_utils/commit/cd44830e04648b5210f00a63548d669688434090))
- **rules_download:** add `file` extension ([bf98fbe](https://git.gitlab.arm.com/bazel/download_utils/commit/bf98fbe86f1a8e8bfa5ba7d37c5d07f44fd4b716))
- **rules_download:** hermetic `commands` ([51c84b1](https://git.gitlab.arm.com/bazel/download_utils/commit/51c84b1d2d60c5b5504321a24a04decf500059f9))

### BREAKING CHANGES

- Project renamed to `download_utils` for BCR upstreaming.

A graceful switch can be done by renaming the module in `MODULE.bazel`:

```
bazel_dep(name = "download_utils", repo_name = "rules_download")
```

- `symlinks` in the rule is now `links`

```diff
 download_archive(
-    symlinks = {
+    links = {
         "abc.txt": "def.txt",
     }
 )
```

# [1.0.0-beta.5](https://git.gitlab.arm.com/bazel/download_utils/compare/v1.0.0-beta.4...v1.0.0-beta.5) (2024-11-27)

### Bug Fixes

- **commands:** support Bazel 8 naming of `use_repo_rule` ([f1aee87](https://git.gitlab.arm.com/bazel/download_utils/commit/f1aee877e10375658a10b0afe215b62482c03023))
- update dependencies for Bazel 8 support ([eef0671](https://git.gitlab.arm.com/bazel/download_utils/commit/eef0671705d34b6693ca3bf909e2337b5ea8a351))

# [1.0.0-beta.4](https://git.gitlab.arm.com/bazel/download_utils/compare/v1.0.0-beta.3...v1.0.0-beta.4) (2024-11-04)

### Bug Fixes

- **commands:** add Bazel 8 `+` separator support ([8f191be](https://git.gitlab.arm.com/bazel/download_utils/commit/8f191be671e12e9e6485179b27f4b462be4a0a61))

# [1.0.0-beta.3](https://git.gitlab.arm.com/bazel/download_utils/compare/v1.0.0-beta.2...v1.0.0-beta.3) (2024-11-04)

### Bug Fixes

- update dependencies for Bazel 8 support ([39f3718](https://git.gitlab.arm.com/bazel/download_utils/commit/39f37182454a4d1137d1554f205ad45aa0f616a5))

# [1.0.0-beta.2](https://git.gitlab.arm.com/bazel/download_utils/compare/v1.0.0-beta.1...v1.0.0-beta.2) (2024-03-12)

### Bug Fixes

- embed download data into `WORKSPACE` to fix cache invalidation ([e475caf](https://git.gitlab.arm.com/bazel/download_utils/commit/e475cafa5535e39e87b71179bfd36213c9ff365a))

# 1.0.0-beta.1 (2024-02-13)

### Bug Fixes

- **e2e:** change to BCR `toolchain_utils` ([4e72ac5](https://git.gitlab.arm.com/bazel/download_utils/commit/4e72ac597ca564cef6dddd5195cb2d09adbacf0e))
- **rules_download:** make intergrity optional ([24fdc84](https://git.gitlab.arm.com/bazel/download_utils/commit/24fdc840fa1d156219795b6167b4068bcb3a4547))
- **rules_download:** remove need for `http_archive` ([cd6d57c](https://git.gitlab.arm.com/bazel/download_utils/commit/cd6d57c6039e072920e6845ad1de85c3760372fd))
- scope tool labels to project ([dc65387](https://git.gitlab.arm.com/bazel/download_utils/commit/dc65387a7394d23278b13e8ff05409d08d9a9fbb))
- set minimum version to Bazel 7 ([62659e4](https://git.gitlab.arm.com/bazel/download_utils/commit/62659e4177e853fb2695f31b4b126f5112906566))
- upgrade `rules_toolchain` ([8c8b033](https://git.gitlab.arm.com/bazel/download_utils/commit/8c8b03393a45f7f9a643413cb2095ad4f663b150))
- use `rules_toolchain` pre-release registry ([f449dcf](https://git.gitlab.arm.com/bazel/download_utils/commit/f449dcff560b61644212f9f2be8406066fe30482))

### Code Refactoring

- change `symlinks` to `links` ([cf1ebd8](https://git.gitlab.arm.com/bazel/download_utils/commit/cf1ebd8fe1fd0e9cfdf90e9e3ac2504f959d0f51))
- name changed to `download_utils` ([8e583ab](https://git.gitlab.arm.com/bazel/download_utils/commit/8e583abe439ae26d61c34fb9a56397ae7b5b2f2e))

### Features

- add `extension` attribute to `download_archive` ([c476a81](https://git.gitlab.arm.com/bazel/download_utils/commit/c476a8105c623a23996421b68bcbf08c20b6a5ac))
- add `symlinks` attribute to rules ([7d0d27a](https://git.gitlab.arm.com/bazel/download_utils/commit/7d0d27a5dd4fed84a36285eda4020d6b7003e70e))
- add Windows support ([8934621](https://git.gitlab.arm.com/bazel/download_utils/commit/8934621f44cf7a44788886917e0795da92d4f922))
- added semantic release to bazel template ([a8a6634](https://git.gitlab.arm.com/bazel/download_utils/commit/a8a663411461dfd66fcc36635b1659a1beb3bd6b))
- remove `coreutils` ([6d3dccc](https://git.gitlab.arm.com/bazel/download_utils/commit/6d3dccccb46c6d71f10a0b0e92d0b5dec6af4f01))
- rules for downloading ([0ebae87](https://git.gitlab.arm.com/bazel/download_utils/commit/0ebae87f7901be40f8a157a3ea5f74395f1548be))
- **rules_download:** add `deb` extension ([cd44830](https://git.gitlab.arm.com/bazel/download_utils/commit/cd44830e04648b5210f00a63548d669688434090))
- **rules_download:** add `file` extension ([bf98fbe](https://git.gitlab.arm.com/bazel/download_utils/commit/bf98fbe86f1a8e8bfa5ba7d37c5d07f44fd4b716))
- **rules_download:** hermetic `commands` ([51c84b1](https://git.gitlab.arm.com/bazel/download_utils/commit/51c84b1d2d60c5b5504321a24a04decf500059f9))

### BREAKING CHANGES

- Project renamed to `download_utils` for BCR upstreaming.

A graceful switch can be done by renaming the module in `MODULE.bazel`:

```
bazel_dep(name = "download_utils", repo_name = "rules_download")
```

- `symlinks` in the rule is now `links`

```diff
 download_archive(
-    symlinks = {
+    links = {
         "abc.txt": "def.txt",
     }
 )
```
