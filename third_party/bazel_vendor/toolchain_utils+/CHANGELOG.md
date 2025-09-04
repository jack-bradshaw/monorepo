## [1.0.2](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.1...v1.0.2) (2025-02-06)

### Bug Fixes

- allow four-part `uname -r` versions ([555a674](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/555a67407e3639c71356950f2d2151860ba2fb4f))
- **toolchain_test:** use `$@` rather than `${@}` ([c402079](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/c4020797351cd3f968c286e834b6a9720a579511))

## [1.0.1](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0...v1.0.1) (2025-01-21)

### Bug Fixes

- license propagation ([0c43803](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/0c4380302e0025c503b3f03a39cab2228dce8c85))

# 1.0.0 (2025-01-17)

### Bug Fixes

- add missing stub file ([fdcc9c0](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fdcc9c013c99bf6c0fb8c29270f4412f83400a83))
- add support for `--incompatible_use_plus_in_repo_names` ([fc7d485](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fc7d485887292676b5a87eb51c40f92aa6343b6b))
- be graceful when `uname` is not found ([6f4242c](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6f4242c19284b424ffd4079c0a02ed6a2bdef103))
- better `uname` processing ([65ff62a](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/65ff62a27f8d3fdbc2d8165402031248dbbdfaf1))
- bump minimum Bazel version to `7.1.0` ([66d1a24](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/66d1a24dc741f55dc90df61da9a358ea10e3dc92))
- bump to `7.1.0+` due to `rctx.getenv` ([3228e10](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/3228e1007aa69e9e6d41dc8cea0f57e3246b2501))
- correct `{{basename}}` in `which` ([892c623](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/892c6233cce27d1ff2a6b950d3ddca1e34b807ff))
- correct executable permissions of repository files ([7e3b586](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/7e3b58635c7c375269e1643195f0a829ec59dcf1))
- correct MacOS constraint ([4259c2d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/4259c2deb70d7c4aa4ac40f274a460e9be35675b))
- create hardlinks on Windows ([374f38d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/374f38ddb93b74c48ea00f222c2c871cf7821539))
- create hardlinks on Windows ([fb7691d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fb7691df6a7948dd19cddceca2a169480ed40913))
- detect `musl` ([3abcf22](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/3abcf22ca864679d329cf9a75d329c2be8c17b7e))
- detect both `WORKSPACE` and `REPO.bazel` in repositories ([f1e46c3](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f1e46c3f142c0665f90119996f0ebb8048dfb0c7))
- **e2e:** add `amd64-macos-darwin` fixture ([6d650a9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6d650a95fee3b0fb65ecc210c38fac28971506fc))
- **export.symlink:** support multiple identical exports ([07a9c2c](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/07a9c2c832997967deaed6a8257789a03bf3450b))
- **export:** mark extension as reproducible ([8d61b2e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/8d61b2e0a76c64393fc5c43428c653f1f4fa5e5b))
- fail fast when not three Linux version parts ([e298c3e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e298c3e5e0674809bd62def624aaf0104089468e))
- find POSIX shell with `/usr/bin/env` ([2294100](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/2294100e1efa4dee95bf61542cca3730bc618aa2))
- forward basename through `toolchain_info` ([2b1924c](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/2b1924ca698827e6ed7b8eba84be7027a8922e9d))
- **info:** correct variable name ([4001e7d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/4001e7d4fae977f98827df024ad9b4a9d8695fac))
- **info:** default variable to binary basename ([f2a8725](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f2a8725acf98020af7f536a016048c98ecfcda26))
- **info:** make var matches info ([f82eb10](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f82eb1082dc5858231ab937d4cec4698678e5f6e))
- **info:** remove `ToolchainInfo#env` ([06e2061](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/06e206176cac5836a774c78dfebb3cadceb55b9c))
- **info:** remove `variables` from `ToolchainInfo` ([21edd34](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/21edd3486770c124d51d3107b3095e2923f3bee1))
- **libc/gnu:** add `2.{39,40}` versions ([d6221c9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d6221c917e3f48594bbfcf3cdcc579b99651145a))
- **local:** correct failure message ([2c40116](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/2c40116c2c037e4c7b961a5a6c0c5aa6d842cffd))
- **local:** grab first part of `uname -r` ([e040928](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e040928dc9ba3ba87e6feedbe3850d751a0896a2))
- **local:** parse msys2 `uname` output ([bca66bb](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/bca66bb8cf8b96b374d149a04301c16d06f8c1af))
- **local:** provide `/etc/os-release` defaults ([177738d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/177738d71b8b50b0c13a02c494f6cf04d93bd316))
- make sure symlinks have `.exe` extension on Windows ([85f15d1](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/85f15d1b590237374c59c8037eca3ea56777c8ee))
- need to foward on `.cmd` not `.com` ([49d7c9f](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/49d7c9fd03763b135cf2e19db16ddfe30e00c8a5))
- pin Bazel version ([585e5f4](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/585e5f4168736c050687be6e59c359ab3a50f8ae))
- **release:** add `metadata.json` to the release ([d8557b5](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d8557b55d92bdba0adb4724278842868daa09430))
- **release:** correct source archive upload ([05f5f20](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/05f5f208f15461ad28bcc3699ce9a8d6c838121a))
- **release:** tidy up `metadata.json` naming in GitLab UI ([d5029b9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d5029b95ddd3cda4556bad52f2b985df0abdc23b))
- remove `ToolchainInfo#inherited` ([7608c42](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/7608c42b57f6c2b7b9db4b61aed54a3fc27557d7))
- **resolved:** correctly forward on providers ([dc423f6](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/dc423f6bf4b593ecd4f648815dae3ef0e9c0a308))
- **resolved:** default to the correct `basename` ([3e2fc92](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/3e2fc92eb112f1925a21e74cc179a2e3abbe2743))
- **resolved:** forward on `basename` attribute ([e71e807](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e71e807b6ded13a3baf2d430900f675a1f8849fd))
- **resolved:** forward on `RunEnvironmentInfo` ([a41e887](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a41e8878edfb0b9e71d3c504111a956bfd6ddc14))
- **resolved:** propagate `.bat`/`.com` extensions ([b0a4176](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/b0a4176194fcf379e4ab4a8ac9f213f34bc9771c))
- **rules_toolchain:** add `amd64`/`arm64` aliases ([332bb9b](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/332bb9b839176d7ad211f7e2f499b80716d37500))
- **rules_toolchain:** add `no-remote` to local binary symlinks ([254a3d5](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/254a3d5d9c78772ffc681f7e5ea099e754bbb3b9))
- **rules_toolchain:** add `x86_64`/`aarch64` CPU targets ([bed5756](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/bed5756126fa64764d80a61cb78afada41d09de0))
- **rules_toolchain:** add locally detect OS version to constraints ([a1eae55](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a1eae55624fb1b2253a3c254943dddb3a98ddc3b))
- **rules_toolchain:** all multiple files to `symlink` ([843f6cc](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/843f6cc729a213a6d367f54bd11d9d8f87daf842))
- **rules_toolchain:** correct GNU libc check ([1689f92](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/1689f9201b70e6f9a22bebb879d67175421d51a3))
- **rules_toolchain:** do a better job of processing `/etc/release` ([561463a](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/561463ab90b43341d4f0b66145a3816d64231c41))
- **rules_toolchain:** local binary not found error ([eb0dcd8](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/eb0dcd8b3ea19c7a414486a6a1e00935ebee6a32))
- **rules_toolchain:** only expose unversioned triplets ([6f60764](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6f6076467613bba30c3bdefe92a7450d266c60c4))
- **rules_toolchain:** respect `target` attribute ([b577305](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/b577305399516eaf21d341cf7e687a25ef20ed68))
- **rules_toolchain:** simplify Bazel run commands ([cb5ae50](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/cb5ae5022da29042416443699c82a65d3fdfd955))
- **rules_toolchain:** support no legacy runfiles ([fdc609e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fdc609e4aeda92fadf8c1154b0f012556cdfb133))
- **select:** add `map` to the error string ([727be3b](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/727be3b7570aebda46b78560cd98cbaa74368b99))
- set minimum version to Bazel 7 ([0db2cc7](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/0db2cc79ca316ef76fb02c33baf35810479dbc59))
- strip output of `uname -r` ([4de6058](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/4de60581787b6ae1a8694d673080042ac8cc7bac))
- **sw_vers:** support single and double Darwin OS versions ([bb887b9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/bb887b98b601be790b3a1cc95486de9ed30f9a83))
- switch to `REPO.bazel` from `WORKSPACE` ([0fbf9c6](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/0fbf9c6fd718736a6dc9c590b6657c5c0e693e7f))
- **symlink_target:** propagate `.bat`/`.com` extensions ([78a6265](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/78a62657e5dc911a75418afb28243e0d29f3db93))
- **symlink_target:** use `ctx.executable` ([fdf8d73](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fdf8d732de0a1137172d0cbd311277834760dbe8))
- **symlink/target:** accept extensions without period `.` ([ab12aa8](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/ab12aa8c9d22d80d949b3c8e213a5a4b4cc9e121))
- **symlink/target:** add `files_to_run` property on `ToolchainInfo` provider ([2966744](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/2966744e1328bef9143dbb5c9c1d32fc756e0219))
- **symlink/target:** add `run` property on `ToolchainInfo` provider ([0f18fef](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/0f18fef6742b133d2da0d0c920bc0fda30e14214))
- **symlink/target:** add target to runfiles ([4ec2331](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/4ec23317cba14be99c3eec807e9b3873fc5636b0))
- **symlink/target:** forward on target runfiles ([8b64cee](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/8b64cee2c9b323bc94def46806f81f7de00b88f3))
- **symlink/target:** merge all data runfiles ([753b43a](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/753b43a44ff14e768dd98f9fd9234d4f30eb3d0d))
- **test:** add executable to runfiles ([57e2621](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/57e2621953afaa8762609527b24eb89ae7788c1b))
- **test:** correct TAP output for diff comparison ([e965777](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e965777bb2c9854d1e022b45ddc4d3f35596855b))
- **test:** correctly output `stderr` from toolchain executable on error ([22cd108](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/22cd108126e8917d7142d7f447fdc61f69ea2521))
- **test:** forward on runfiles ([e832606](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e832606d803aeb654a876ff53ba56a7b35df952a))
- **test:** simplify POSIX variable substitution ([82a4eca](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/82a4ecaa71dbd06c98f24ef5fb4dc40ad58a40a6))
- **toolchain_local_select:** use `label_keyed_string_dict` for `map` attribute ([1d48d2e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/1d48d2e43ff1cbe1d2746aa7c7b7b86363fad2a0))
- **toolchain/test:** break `diff` loop correctly ([d57cc71](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d57cc71913ae936d26ed4a385ad3eb24bde6e8d9))
- upload release files to generic package registry ([d5deccf](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d5deccfa9dc35482c23b7376fb368f8037d51845))
- use `[@bazel](https://git.gitlab.arm.com/bazel)_skylib` to split path extensions ([e5feff7](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e5feff7cedb6aaa168ffdf912c0efc6b794c6f6c))
- **windows:** add `.exe` extension to symlinks when necessary ([6587ffd](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6587ffdb79990b9f186612ae9fd9bff14b97c520))

### Features

- add `{net,free,open}bsd` operating system aliases ([a805889](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a8058893f1d95afe23c9f0d64cb3b81e69fec4dd))
- add `//toolchain/constraint/{cpu,os,libc}:local` aliases ([e67679b](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e67679b18e22c0837f69c213d6b43618c713f454))
- add `//toolchain/constraint/libc:ucrt` ([4deb57e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/4deb57e881ad3fd4e43b49bc5a6724ad0db68ffd))
- add `//toolchain/constraint/local:{cpu,os,libc}` aliases ([a384e64](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a384e6491bbad9975e4cf9ce5e16758e8e6b7c01))
- add `//toolchain/constraint/os:windows` ([064ecaf](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/064ecafcc9b0a1ed3afc3c62c459f0b541438b27))
- add `//toolchain/local/constraint:{cpu,os,libc}` targets ([cf24d76](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/cf24d7609f95b765698181a24a370fc4b5c160ac))
- add `export` extension ([35bae07](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/35bae07323b7ac4a9ddeab60909d9c411970fab2))
- add `msvc` C library constraint ([f638f1f](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f638f1fa59df687302443216857d6353a48e9f64))
- add `toolchain_info` rule ([11ea241](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/11ea241647f87759bebd2c5ce7a7b0bdba55953a))
- add a `resolved` target to local binary repositories ([7ad9806](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/7ad98062778c17c7b143a2a366b18361ba950d09))
- add a plain string value to local triplet repository ([e41e05f](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e41e05ffec1208b535ccfdcb4d70bc5d4c9311af))
- add couplet platforms ([27ba292](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/27ba292a92948f25e8b429d25b463f47727c8597))
- add launcher ([96823ab](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/96823abd1096cb17d81b554072916450f6944fa7))
- add MacOS triplet detection ([fbc8e8e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fbc8e8ea6dc7c422cd33bbb4106c923cab3e7bd0))
- add simple Windows OS detection ([a090eab](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a090eabe2dae4e52eb611c610c617437a31a46f0))
- add Universal C runtime detection ([cd8b097](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/cd8b097bd731403b3502214fffefb15089a975be))
- add Windows version detection ([b2d0c53](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/b2d0c53752ca2488a7ae9c70697591098bdb06b3))
- **info:** add `variable` to `ToolchainInfo` ([f51317b](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f51317b81fe3ffd2a30b214891ed45313b06ec1a))
- **info:** forward on `RunEnvironmentInfo` through `ToolchainInfo` ([08baa79](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/08baa79e8464d31cfaaa24ad199cde0a819f1d64))
- **local_which:** make local binaries non-mandatory ([5d9a292](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/5d9a292cfd4a7f9dd5b952ca33c34b01da0fc796))
- **local/triplet:** override detection with `BAZEL_TOOLCHAIN_UTILS_LOCAL_TRIPLET` ([d0c7234](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d0c72342657a67939d95371d725e2c79191a1942))
- **local/which:** support Windows ([1398d38](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/1398d3869c59f6500def3894c4065ef2d9a0d9a1))
- make `which` mandatory ([274d2c9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/274d2c96991ea897a76055527561e5346402792a))
- **rules_toolchain:** add `:entrypoint` target to local repositories ([a995bd4](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a995bd4702de985b14cd99ce1f896f1833342b29))
- **rules_toolchain:** add `DataInfo.executable` ([6cc16f2](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6cc16f24832cd3b1948929d52fb738d76be90f2c))
- **rules_toolchain:** add `echo` toolchain end-to-end test ([9309dd1](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/9309dd1acd992fe6be15faaef180a43febcc94c0))
- **rules_toolchain:** add `host` platform and constraint ([9f8ae2c](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/9f8ae2c728914d7d8adbed93c75338e962ffdd2f))
- **rules_toolchain:** add `local.select` extension ([97b4386](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/97b4386fdbc416ba7b2b701ac6a56f1ed9abf187))
- **rules_toolchain:** add `toolchain_symlink_path` rule ([f03782a](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f03782a088727dba597915d65aa83ed8830eb87b))
- **rules_toolchain:** add `toolchain_symlink_target` rule ([f8987a4](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f8987a4989363331142df10bce7b6269456da14f))
- **rules_toolchain:** add `toolchain_test` rule ([550b3b1](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/550b3b1fc183bed745366320ba26632ecb055f9f))
- **rules_toolchain:** add `uname` release detection ([23260b3](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/23260b3baa8d6e157390e6656dea2309454017cd))
- **rules_toolchain:** add customising the basename for symlink rules ([9ad523d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/9ad523dfe0e31f0c5bdf17163741befca139e519))
- **rules_toolchain:** add extension for finding local binaries ([2ed07d3](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/2ed07d3b5ec30b7fceac760c6249d6a9f35bc15d))
- **rules_toolchain:** add host triplet detection ([0e2e211](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/0e2e211668df6fc8af0ccb0fe1e6bf4ac25566c4))
- **rules_toolchain:** add local toolchain information rule ([d6b279d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d6b279dc9aeb5522746d944b3ee59ae6ae3f067d))
- **rules_toolchain:** add triplet constraints ([5220511](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/522051114a772fb7a878e5f6df44446c944dcba6))
- **rules_toolchain:** add triplet platforms ([14aba1d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/14aba1de9d9c97ab71b49c2a832960ed64640d7e))
- **rules_toolchain:** add triplet targets and information ([6187133](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6187133091be4f6f6fc5b24444d380033ba244eb))
- **rules_toolchain:** expose `glibc` `VERSIONS` collection ([5db2957](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/5db2957e727342dfc00e000c34310b1a08a1f088))
- **rules_toolchain:** generate LTS version variants ([6deceb4](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6deceb4619a4a0fd04565d2403ac60297eeae931))
- **rules_toolchain:** generate versioned triplets ([63c9436](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/63c943658fdce7fbb7a151374c60bdb615261da2))
- **rules_toolchain:** test anything protocol for testing ([191ce97](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/191ce97e2c613e849f803dc1c7ff958f0ff9f021))
- **select:** add more selection options ([22e5104](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/22e5104a50b8fcfdffed77a2c8af82bdb79c512f))
- support detecting triplet on MinGW ([ff280ae](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/ff280ae0b1aed1bfca9b667b07ddbb657e98cfc6))
- switch to `rules_license` ([5036f0f](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/5036f0f9e600d4c28845253030158cf5560df9c7))
- **test:** add Batch script for Windows ([ea1a75d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/ea1a75db5b906e31646d0e41adad7b8ad8a63381))
- **test:** allow custom exit codes ([d0fc499](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d0fc4991421a494975cc47a2857e3d78a6d356a1))
- **toolchain_triplet:** add Windows support ([32e2220](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/32e2220df6254455713d331f9cbd4509fb9d9bc8))

### BREAKING CHANGES

- **info:** `toolchain_info#variable` now defaults to the basename of the target executable.

Previously the variable defaulted to the `name` attribute:

```py
go_binary(
    name = "abc,
)

toolchain_info(
    name = "def",
    target = ":abc",
    # variable = "DEF",
)
```

Now the rule defaults the basename of the target executable:

```py
go_binary(
    name = "abc,
)

toolchain_info(
    name = "def",
    target = ":abc",
    # variable = "ABC",
)
```

In most situations, this is the prefered default.

In cases where the name is the variable name wanted, the `variable` now needs to be added:

```py
go_binary(
    name = "abc,
)

toolchain_info(
    name = "def",
    target = ":abc",
    variable = "ABC",
)
```

- **info:** `toolchain_info` removes `ToochainInfo#env`.

Allowing access to the original `env` attribute of the `toolchain_info` target can result in unexpected errors. `env` is usual subject to Make variable and expand location expansion. The expansion is done in the scope of the original rule. Re-using the environment results in errors with miscalculated paths, etc.

If it is required to capture the environment variables for a binary, it is much better to use a rule that bakes the environment variables into the execution of the binary. This can be done with launcher scripts that write out the environment variables for later use.

- **info:** `variables` is no longer available on the `ToolchainInfo` generated by `toolchain_info`.

The `TemplateVariableInfo` is created in the scope of the `toolchain_info` rule. Using the provider in downstream rules that access the `ToolchainInfo` will no longer be relevant.

A `ToolchainInfo#variable` attribute is provided to reconstruct a `TemplateVariableInfo` in the scope of a downstream rule.

- **resolved:** The `resolved` repository rule has removed the `basename` attribute in favour or symlinking the binary directly.

This change prevents unwanted symlink basename changes which can prevent multi-tool binaries from functioning correctly.

To set the basename of a symlink use the `toolchain_symlink_target` rule.

- **toolchain_local_select:** `toolchain_local_select#map` is now flipped taking the labels as they keys rather than as the values.

Previous releases had the following setup:

```py
download_file(
    name = "abc-arm64-linux-gnu",
    output = "abc",
)

download_file(
    name = "abc-arm64-linux-musl",
    output = "abc",
)

toolchain_local_select(
    name = "abc",
    map = {
        "arm64-linux-gnu": "@abc-arm64-linux-gnu",
        "arm64-linux-musl": "@abc-arm64-linux-musl",
    },
)
```

This prevented eagarly overfetching all the repositories that would be
selected. As of Bazel 7.4.0+[1], it no longer overfetches so the `map`
attribute has been switched to a `label_keyed_string_dict`:

```py
toolchain_local_select(
    name = "abc",
    map = {
        "@abc-arm64-linux-gnu": "arm64-linux-gnu",
        "@abc-arm64-linux-musl": "arm64-linux-musl",
    },
)
```

The repository rule will _only_ fetch the repostory label that has been selected due
to the triplet value matching.

Whilst this is a breaking change, it will introduce a regression on Bazel 7.3 and below
which will overfetch the repositories. It is _highly_ recommended to use Bazel 7.4 and above.

[1]: https://github.com/bazelbuild/bazel/commit/6fabb1fc6869a204373e5ee0adde696a659415dd

- `ToolchainInfo#inherited` removed

It is not possible to use these in an action as the host
environment variables cannot be read host environment variables.

- The `toolchain_symlink_target` no longer exports
  `ToolchainInfo`/`TemplateVariableInfo`.

The `toolchain_info` can provide those providers. The `toolchain_symlink_target`
is purely for creating a symlink to a previous target. This still enables
changing the basename for multi-call binaries.

To gain the old functionality, if `basename` attribute is not being used,
`toolchain_symlink_target` can be switched directly for `toolchain_info`. If
`basename` is being used, an extra `toolchain_info` rule will need to be created
to wrap the `toolchain_symlink_target` rule.

- **symlink/target:** the `files_to_run` property has been removed from the `ToolchainInfo` provider. Use `info.default.files_to_run` instead.

A new `info.run` has been added that is `info.default.files_to_run` or `info.default.executable`.
This is a convenience property that can be passed to `ctx.actions.run#execute`.

- **symlink/target:** `toolchain_symlink_path` no longer provides `ToolchainInfo`/`TemplateVariableInfo`.

It is required to use `toolchain_symlink_target` to the `toolchain_symlink_path`.

This is due to `toolchain_symlink_target` needing to export the `DefaultInfo.file_to_run` from the
symlinked target. That is not possible with a filesystem path.

This should not affect most users as `toolchain_symlink_path` is mainly used in `toolchain_local_which`.

If `toolchain_symlink_path` is being used, then to gain the previous functionality, create a
`toolchain_symlink_target` to the `toolchain_symlink_path` label.

# [1.0.0-beta.19](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.18...v1.0.0-beta.19) (2024-12-04)

### Bug Fixes

- strip output of `uname -r` ([4de6058](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/4de60581787b6ae1a8694d673080042ac8cc7bac))

# [1.0.0-beta.18](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.17...v1.0.0-beta.18) (2024-11-20)

### Bug Fixes

- bump to `7.1.0+` due to `rctx.getenv` ([3228e10](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/3228e1007aa69e9e6d41dc8cea0f57e3246b2501))
- **toolchain_local_select:** use `label_keyed_string_dict` for `map` attribute ([1d48d2e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/1d48d2e43ff1cbe1d2746aa7c7b7b86363fad2a0))

### BREAKING CHANGES

- **toolchain_local_select:** `toolchain_local_select#map` is now flipped taking the labels as they keys rather than as the values.

Previous releases had the following setup:

```py
download_file(
    name = "abc-arm64-linux-gnu",
    output = "abc",
)

download_file(
    name = "abc-arm64-linux-musl",
    output = "abc",
)

toolchain_local_select(
    name = "abc",
    map = {
        "arm64-linux-gnu": "@abc-arm64-linux-gnu",
        "arm64-linux-musl": "@abc-arm64-linux-musl",
    },
)
```

This prevented eagarly overfetching all the repositories that would be
selected. As of Bazel 7.4.0+[1], it no longer overfetches so the `map`
attribute has been switched to a `label_keyed_string_dict`:

```py
toolchain_local_select(
    name = "abc",
    map = {
        "@abc-arm64-linux-gnu": "arm64-linux-gnu",
        "@abc-arm64-linux-musl": "arm64-linux-musl",
    },
)
```

The repository rule will _only_ fetch the repostory label that has been selected due
to the triplet value matching.

Whilst this is a breaking change, it will introduce a regression on Bazel 7.3 and below
which will overfetch the repositories. It is _highly_ recommended to use Bazel 7.4 and above.

[1]: https://github.com/bazelbuild/bazel/commit/6fabb1fc6869a204373e5ee0adde696a659415dd

# [1.0.0-beta.17](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.16...v1.0.0-beta.17) (2024-10-28)

### Bug Fixes

- detect both `WORKSPACE` and `REPO.bazel` in repositories ([f1e46c3](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f1e46c3f142c0665f90119996f0ebb8048dfb0c7))

# [1.0.0-beta.16](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.15...v1.0.0-beta.16) (2024-10-21)

### Bug Fixes

- bump minimum Bazel version to `7.1.0` ([66d1a24](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/66d1a24dc741f55dc90df61da9a358ea10e3dc92))
- **libc/gnu:** add `2.{39,40}` versions ([d6221c9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d6221c917e3f48594bbfcf3cdcc579b99651145a))
- **local:** correct failure message ([2c40116](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/2c40116c2c037e4c7b961a5a6c0c5aa6d842cffd))
- **local:** parse msys2 `uname` output ([bca66bb](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/bca66bb8cf8b96b374d149a04301c16d06f8c1af))
- switch to `REPO.bazel` from `WORKSPACE` ([0fbf9c6](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/0fbf9c6fd718736a6dc9c590b6657c5c0e693e7f))

### Features

- **local/triplet:** override detection with `BAZEL_TOOLCHAIN_UTILS_LOCAL_TRIPLET` ([d0c7234](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d0c72342657a67939d95371d725e2c79191a1942))

# [1.0.0-beta.15](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.14...v1.0.0-beta.15) (2024-09-20)

### Bug Fixes

- **resolved:** forward on `RunEnvironmentInfo` ([a41e887](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a41e8878edfb0b9e71d3c504111a956bfd6ddc14))

# [1.0.0-beta.14](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.13...v1.0.0-beta.14) (2024-09-06)

### Bug Fixes

- **local:** grab first part of `uname -r` ([e040928](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e040928dc9ba3ba87e6feedbe3850d751a0896a2))
- **local:** provide `/etc/os-release` defaults ([177738d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/177738d71b8b50b0c13a02c494f6cf04d93bd316))
- remove `ToolchainInfo#inherited` ([7608c42](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/7608c42b57f6c2b7b9db4b61aed54a3fc27557d7))

### Features

- add `//toolchain/constraint/local:{cpu,os,libc}` aliases ([a384e64](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a384e6491bbad9975e4cf9ce5e16758e8e6b7c01))
- add `//toolchain/local/constraint:{cpu,os,libc}` targets ([cf24d76](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/cf24d7609f95b765698181a24a370fc4b5c160ac))
- add couplet platforms ([27ba292](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/27ba292a92948f25e8b429d25b463f47727c8597))

### BREAKING CHANGES

- `ToolchainInfo#inherited` removed

It is not possible to use these in an action as the host
environment variables cannot be read host environment variables.

# [1.0.0-beta.13](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.12...v1.0.0-beta.13) (2024-07-30)

### Bug Fixes

- add support for `--incompatible_use_plus_in_repo_names` ([fc7d485](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fc7d485887292676b5a87eb51c40f92aa6343b6b))

# [1.0.0-beta.12](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.11...v1.0.0-beta.12) (2024-07-09)

### Features

- **info:** forward on `RunEnvironmentInfo` through `ToolchainInfo` ([08baa79](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/08baa79e8464d31cfaaa24ad199cde0a819f1d64))

# [1.0.0-beta.11](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.10...v1.0.0-beta.11) (2024-06-21)

### Bug Fixes

- **export:** mark extension as reproducible ([8d61b2e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/8d61b2e0a76c64393fc5c43428c653f1f4fa5e5b))

# [1.0.0-beta.10](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.9...v1.0.0-beta.10) (2024-05-22)

### Bug Fixes

- **info:** make var matches info ([f82eb10](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f82eb1082dc5858231ab937d4cec4698678e5f6e))

# [1.0.0-beta.9](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.8...v1.0.0-beta.9) (2024-05-03)

### Features

- add `toolchain_info` rule ([11ea241](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/11ea241647f87759bebd2c5ce7a7b0bdba55953a))

### BREAKING CHANGES

- The `toolchain_symlink_target` no longer exports
  `ToolchainInfo`/`TemplateVariableInfo`.

The `toolchain_info` can provide those providers. The `toolchain_symlink_target`
is purely for creating a symlink to a previous target. This still enables
changing the basename for multi-call binaries.

To gain the old functionality, if `basename` attribute is not being used,
`toolchain_symlink_target` can be switched directly for `toolchain_info`. If
`basename` is being used, an extra `toolchain_info` rule will need to be created
to wrap the `toolchain_symlink_target` rule.

# [1.0.0-beta.8](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.7...v1.0.0-beta.8) (2024-05-03)

### Bug Fixes

- **symlink/target:** add `run` property on `ToolchainInfo` provider ([0f18fef](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/0f18fef6742b133d2da0d0c920bc0fda30e14214))

### BREAKING CHANGES

- **symlink/target:** the `files_to_run` property has been removed from the `ToolchainInfo` provider. Use `info.default.files_to_run` instead.

A new `info.run` has been added that is `info.default.files_to_run` or `info.default.executable`.
This is a convenience property that can be passed to `ctx.actions.run#execute`.

# [1.0.0-beta.7](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.6...v1.0.0-beta.7) (2024-05-03)

### Bug Fixes

- **symlink/target:** add `files_to_run` property on `ToolchainInfo` provider ([2966744](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/2966744e1328bef9143dbb5c9c1d32fc756e0219))
- **symlink/target:** add target to runfiles ([4ec2331](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/4ec23317cba14be99c3eec807e9b3873fc5636b0))
- **symlink/target:** merge all data runfiles ([753b43a](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/753b43a44ff14e768dd98f9fd9234d4f30eb3d0d))
- **toolchain/test:** break `diff` loop correctly ([d57cc71](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d57cc71913ae936d26ed4a385ad3eb24bde6e8d9))

### BREAKING CHANGES

- **symlink/target:** `toolchain_symlink_path` no longer provides `ToolchainInfo`/`TemplateVariableInfo`.

It is required to use `toolchain_symlink_target` to the `toolchain_symlink_path`.

This is due to `toolchain_symlink_target` needing to export the `DefaultInfo.file_to_run` from the
symlinked target. That is not possible with a filesystem path.

This should not affect most users as `toolchain_symlink_path` is mainly used in `toolchain_local_which`.

If `toolchain_symlink_path` is being used, then to gain the previous functionality, create a
`toolchain_symlink_target` to the `toolchain_symlink_path` label.

# [1.0.0-beta.6](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.5...v1.0.0-beta.6) (2024-04-24)

### Bug Fixes

- **symlink/target:** accept extensions without period `.` ([ab12aa8](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/ab12aa8c9d22d80d949b3c8e213a5a4b4cc9e121))

# [1.0.0-beta.5](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.4...v1.0.0-beta.5) (2024-04-24)

### Features

- support detecting triplet on MinGW ([ff280ae](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/ff280ae0b1aed1bfca9b667b07ddbb657e98cfc6))

# [1.0.0-beta.4](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.3...v1.0.0-beta.4) (2024-03-25)

### Bug Fixes

- **export.symlink:** support multiple identical exports ([07a9c2c](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/07a9c2c832997967deaed6a8257789a03bf3450b))

# [1.0.0-beta.3](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.2...v1.0.0-beta.3) (2024-02-23)

### Features

- add `{net,free,open}bsd` operating system aliases ([a805889](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a8058893f1d95afe23c9f0d64cb3b81e69fec4dd))

# [1.0.0-beta.2](https://git.gitlab.arm.com/bazel/toolchain_utils/compare/v1.0.0-beta.1...v1.0.0-beta.2) (2024-02-21)

### Bug Fixes

- find POSIX shell with `/usr/bin/env` ([2294100](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/2294100e1efa4dee95bf61542cca3730bc618aa2))
- **resolved:** default to the correct `basename` ([3e2fc92](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/3e2fc92eb112f1925a21e74cc179a2e3abbe2743))

# 1.0.0-beta.1 (2024-01-23)

### Bug Fixes

- add missing stub file ([fdcc9c0](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fdcc9c013c99bf6c0fb8c29270f4412f83400a83))
- be graceful when `uname` is not found ([6f4242c](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6f4242c19284b424ffd4079c0a02ed6a2bdef103))
- better `uname` processing ([65ff62a](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/65ff62a27f8d3fdbc2d8165402031248dbbdfaf1))
- correct `{{basename}}` in `which` ([892c623](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/892c6233cce27d1ff2a6b950d3ddca1e34b807ff))
- correct executable permissions of repository files ([7e3b586](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/7e3b58635c7c375269e1643195f0a829ec59dcf1))
- correct MacOS constraint ([4259c2d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/4259c2deb70d7c4aa4ac40f274a460e9be35675b))
- create hardlinks on Windows ([374f38d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/374f38ddb93b74c48ea00f222c2c871cf7821539))
- create hardlinks on Windows ([fb7691d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fb7691df6a7948dd19cddceca2a169480ed40913))
- **e2e:** add `amd64-macos-darwin` fixture ([6d650a9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6d650a95fee3b0fb65ecc210c38fac28971506fc))
- fail fast when not three Linux version parts ([e298c3e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e298c3e5e0674809bd62def624aaf0104089468e))
- make sure symlinks have `.exe` extension on Windows ([85f15d1](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/85f15d1b590237374c59c8037eca3ea56777c8ee))
- need to foward on `.cmd` not `.com` ([49d7c9f](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/49d7c9fd03763b135cf2e19db16ddfe30e00c8a5))
- pin Bazel version ([585e5f4](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/585e5f4168736c050687be6e59c359ab3a50f8ae))
- **release:** add `metadata.json` to the release ([d8557b5](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d8557b55d92bdba0adb4724278842868daa09430))
- **release:** correct source archive upload ([05f5f20](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/05f5f208f15461ad28bcc3699ce9a8d6c838121a))
- **release:** tidy up `metadata.json` naming in GitLab UI ([d5029b9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d5029b95ddd3cda4556bad52f2b985df0abdc23b))
- **resolved:** forward on `basename` attribute ([e71e807](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e71e807b6ded13a3baf2d430900f675a1f8849fd))
- **resolved:** propagate `.bat`/`.com` extensions ([b0a4176](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/b0a4176194fcf379e4ab4a8ac9f213f34bc9771c))
- **rules_toolchain:** add `amd64`/`arm64` aliases ([332bb9b](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/332bb9b839176d7ad211f7e2f499b80716d37500))
- **rules_toolchain:** add `no-remote` to local binary symlinks ([254a3d5](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/254a3d5d9c78772ffc681f7e5ea099e754bbb3b9))
- **rules_toolchain:** add `x86_64`/`aarch64` CPU targets ([bed5756](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/bed5756126fa64764d80a61cb78afada41d09de0))
- **rules_toolchain:** add locally detect OS version to constraints ([a1eae55](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a1eae55624fb1b2253a3c254943dddb3a98ddc3b))
- **rules_toolchain:** all multiple files to `symlink` ([843f6cc](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/843f6cc729a213a6d367f54bd11d9d8f87daf842))
- **rules_toolchain:** correct GNU libc check ([1689f92](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/1689f9201b70e6f9a22bebb879d67175421d51a3))
- **rules_toolchain:** do a better job of processing `/etc/release` ([561463a](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/561463ab90b43341d4f0b66145a3816d64231c41))
- **rules_toolchain:** local binary not found error ([eb0dcd8](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/eb0dcd8b3ea19c7a414486a6a1e00935ebee6a32))
- **rules_toolchain:** only expose unversioned triplets ([6f60764](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6f6076467613bba30c3bdefe92a7450d266c60c4))
- **rules_toolchain:** respect `target` attribute ([b577305](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/b577305399516eaf21d341cf7e687a25ef20ed68))
- **rules_toolchain:** simplify Bazel run commands ([cb5ae50](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/cb5ae5022da29042416443699c82a65d3fdfd955))
- **rules_toolchain:** support no legacy runfiles ([fdc609e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fdc609e4aeda92fadf8c1154b0f012556cdfb133))
- **select:** add `map` to the error string ([727be3b](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/727be3b7570aebda46b78560cd98cbaa74368b99))
- set minimum version to Bazel 7 ([0db2cc7](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/0db2cc79ca316ef76fb02c33baf35810479dbc59))
- **sw_vers:** support single and double Darwin OS versions ([bb887b9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/bb887b98b601be790b3a1cc95486de9ed30f9a83))
- **symlink_target:** propagate `.bat`/`.com` extensions ([78a6265](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/78a62657e5dc911a75418afb28243e0d29f3db93))
- **symlink_target:** use `ctx.executable` ([fdf8d73](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fdf8d732de0a1137172d0cbd311277834760dbe8))
- **symlink/target:** forward on target runfiles ([8b64cee](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/8b64cee2c9b323bc94def46806f81f7de00b88f3))
- **test:** add executable to runfiles ([57e2621](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/57e2621953afaa8762609527b24eb89ae7788c1b))
- **test:** correct TAP output for diff comparison ([e965777](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e965777bb2c9854d1e022b45ddc4d3f35596855b))
- **test:** correctly output `stderr` from toolchain executable on error ([22cd108](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/22cd108126e8917d7142d7f447fdc61f69ea2521))
- **test:** forward on runfiles ([e832606](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e832606d803aeb654a876ff53ba56a7b35df952a))
- **test:** simplify POSIX variable substitution ([82a4eca](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/82a4ecaa71dbd06c98f24ef5fb4dc40ad58a40a6))
- upload release files to generic package registry ([d5deccf](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d5deccfa9dc35482c23b7376fb368f8037d51845))
- use `[@bazel](https://git.gitlab.arm.com/bazel)_skylib` to split path extensions ([e5feff7](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e5feff7cedb6aaa168ffdf912c0efc6b794c6f6c))

### Features

- add `//toolchain/constraint/libc:ucrt` ([4deb57e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/4deb57e881ad3fd4e43b49bc5a6724ad0db68ffd))
- add `//toolchain/constraint/os:windows` ([064ecaf](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/064ecafcc9b0a1ed3afc3c62c459f0b541438b27))
- add `export` extension ([35bae07](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/35bae07323b7ac4a9ddeab60909d9c411970fab2))
- add `msvc` C library constraint ([f638f1f](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f638f1fa59df687302443216857d6353a48e9f64))
- add a `resolved` target to local binary repositories ([7ad9806](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/7ad98062778c17c7b143a2a366b18361ba950d09))
- add a plain string value to local triplet repository ([e41e05f](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/e41e05ffec1208b535ccfdcb4d70bc5d4c9311af))
- add launcher ([96823ab](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/96823abd1096cb17d81b554072916450f6944fa7))
- add MacOS triplet detection ([fbc8e8e](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/fbc8e8ea6dc7c422cd33bbb4106c923cab3e7bd0))
- add simple Windows OS detection ([a090eab](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a090eabe2dae4e52eb611c610c617437a31a46f0))
- add Universal C runtime detection ([cd8b097](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/cd8b097bd731403b3502214fffefb15089a975be))
- add Windows version detection ([b2d0c53](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/b2d0c53752ca2488a7ae9c70697591098bdb06b3))
- **local_which:** make local binaries non-mandatory ([5d9a292](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/5d9a292cfd4a7f9dd5b952ca33c34b01da0fc796))
- **local/which:** support Windows ([1398d38](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/1398d3869c59f6500def3894c4065ef2d9a0d9a1))
- make `which` mandatory ([274d2c9](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/274d2c96991ea897a76055527561e5346402792a))
- **rules_toolchain:** add `:entrypoint` target to local repositories ([a995bd4](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/a995bd4702de985b14cd99ce1f896f1833342b29))
- **rules_toolchain:** add `DataInfo.executable` ([6cc16f2](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6cc16f24832cd3b1948929d52fb738d76be90f2c))
- **rules_toolchain:** add `echo` toolchain end-to-end test ([9309dd1](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/9309dd1acd992fe6be15faaef180a43febcc94c0))
- **rules_toolchain:** add `host` platform and constraint ([9f8ae2c](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/9f8ae2c728914d7d8adbed93c75338e962ffdd2f))
- **rules_toolchain:** add `local.select` extension ([97b4386](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/97b4386fdbc416ba7b2b701ac6a56f1ed9abf187))
- **rules_toolchain:** add `toolchain_symlink_path` rule ([f03782a](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f03782a088727dba597915d65aa83ed8830eb87b))
- **rules_toolchain:** add `toolchain_symlink_target` rule ([f8987a4](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/f8987a4989363331142df10bce7b6269456da14f))
- **rules_toolchain:** add `toolchain_test` rule ([550b3b1](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/550b3b1fc183bed745366320ba26632ecb055f9f))
- **rules_toolchain:** add `uname` release detection ([23260b3](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/23260b3baa8d6e157390e6656dea2309454017cd))
- **rules_toolchain:** add customising the basename for symlink rules ([9ad523d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/9ad523dfe0e31f0c5bdf17163741befca139e519))
- **rules_toolchain:** add extension for finding local binaries ([2ed07d3](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/2ed07d3b5ec30b7fceac760c6249d6a9f35bc15d))
- **rules_toolchain:** add host triplet detection ([0e2e211](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/0e2e211668df6fc8af0ccb0fe1e6bf4ac25566c4))
- **rules_toolchain:** add local toolchain information rule ([d6b279d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d6b279dc9aeb5522746d944b3ee59ae6ae3f067d))
- **rules_toolchain:** add triplet constraints ([5220511](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/522051114a772fb7a878e5f6df44446c944dcba6))
- **rules_toolchain:** add triplet platforms ([14aba1d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/14aba1de9d9c97ab71b49c2a832960ed64640d7e))
- **rules_toolchain:** add triplet targets and information ([6187133](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6187133091be4f6f6fc5b24444d380033ba244eb))
- **rules_toolchain:** expose `glibc` `VERSIONS` collection ([5db2957](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/5db2957e727342dfc00e000c34310b1a08a1f088))
- **rules_toolchain:** generate LTS version variants ([6deceb4](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/6deceb4619a4a0fd04565d2403ac60297eeae931))
- **rules_toolchain:** generate versioned triplets ([63c9436](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/63c943658fdce7fbb7a151374c60bdb615261da2))
- **rules_toolchain:** test anything protocol for testing ([191ce97](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/191ce97e2c613e849f803dc1c7ff958f0ff9f021))
- **select:** add more selection options ([22e5104](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/22e5104a50b8fcfdffed77a2c8af82bdb79c512f))
- **test:** add Batch script for Windows ([ea1a75d](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/ea1a75db5b906e31646d0e41adad7b8ad8a63381))
- **test:** allow custom exit codes ([d0fc499](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/d0fc4991421a494975cc47a2857e3d78a6d356a1))
- **toolchain_triplet:** add Windows support ([32e2220](https://git.gitlab.arm.com/bazel/toolchain_utils/commit/32e2220df6254455713d331f9cbd4509fb9d9bc8))
