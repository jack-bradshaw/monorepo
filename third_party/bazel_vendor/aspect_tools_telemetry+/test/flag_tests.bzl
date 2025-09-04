load("@bazel_skylib//lib:unittest.bzl", "unittest", "asserts")
load("//:extension.bzl", "parse_opt_out")

def _parse_opt_out_test(ctx):
  env = unittest.begin(ctx)

  features = ["id", "user", "shell"]
  groups = {"all": features}

  asserts.equals(env, features, parse_opt_out("all", features, groups), "all should mean all")
  asserts.equals(env, features, parse_opt_out("", features, groups), "empty string is also all")
  asserts.equals(env, [], parse_opt_out("-all", features, groups), "-all disables all features")
  asserts.equals(env, ["id"], parse_opt_out("id", features, groups), "An unqualified term masks defaults")
  asserts.equals(env, [], parse_opt_out("all,+all,-all", features, groups), "Removals win")

  return unittest.end(env)

parse_opt_out_test = unittest.make(_parse_opt_out_test)

def test_suite():
  unittest.suite(
      "flag_suite",
      parse_opt_out_test,
  )
