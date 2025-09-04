load("@bazel_skylib//lib:unittest.bzl", "unittest", "asserts")
load("//:sha1.bzl", hash="sha1")

CASES = [
  ["nonvulcanizable", "c071b3ac4b057871f0059d6866f392400f76ce2a"],
  ["scruto", "8ab147eb765ddc533c45b215ce78f84f11d9d510"],
  ["bohawn", "9fd97e45f30c21b2356937930ea5dc64ff90126c"],
  ["sicklied", "ad55409e8def78a70622b4466e1b0767643bac8b"],
  ["omniformity", "c0003580f97389c1d24f9cd35c859c248bca469b"],
  ["pathwayed", "877ccc8d227ece94df33014da9c74e8e5fb7a8b9"],
  ["studiable", "2bbf878e364c0d76a918ece363dcba1d6f9a8ece"],
  ["unacclimated", "b935b16be37badca2fa292e1cc33c7380e1de2b0"],
  ["cranny", "892e1f50221f33ec3f85ccaf029e601806252db8"],
  ["outlawry", "3e820f6a700e5a979cc4a733e4903c83c000cf2f"],
  ["sangei", "9453b8583f13ac3e8333f1a7c4d16fe73346765c"],
  ["superguarantee", "c63566140381a7462f19d5685812dd60ae5088be"],
  ["didelphine", "dcf03589e6dd7b8cf8805e3b75d2ce7ce4a18a14"],
  ["alien", "519df3ed50bc30d365cd68a8a5537718b4dfeaa3"],
  ["polyacanthus", "a18857ede959d6b567304ec42b02ddb13fd7c48e"],
  ["precyclonic", "3d09c4c0eefbb15367da105df717dec45e2655f9"],
  ["brushbush", "61b98cc7745dac0c22c41ef4d322c0fbda41bc7f"],
  ["matfelon", "1da2c303ada793ae4046da2b9e1d54483e9fe66d"],
  ["aguishly", "7dde950252cc521bc5eb5fc9b035e8045de3498f"],
  ["cumbrousness", "07f1bfd700a2547ad3c4b799c59ecc31cc91929d"],
  ["Xinca", "6a5230329122a696c7f161c01716450d4406d5cc"],
  ["leapable", "31067e5315cb74686cb1d0e01085c768ea6ad16d"],
  ["adversity", "d6d85ecf7255cc41f9fcb46be81fcfa74b5a81a9"],
  ["philippizer", "5f4c50e5a76bf49608b8fcb04379ba147cd60f01"],
  ["isocrymic", "df353240073e1c3e9a6794a29856afae7ccee710"],
  ["heliograph", "c45d4983f3d66978286fc52046579001d392b7f9"],
  ["unsympathized", "efb33a726b23604326ff62d4a1b2b7ed3ebe7252"],
  ["flatland", "a55686261d91e4619a273a338e16be7188d1b6f7"],
  ["unbarricade", "29abfafef25167738a77880ede8233d2b5442298"],
  ["trumpetry", "d4e1da140c3a5351c250b078dfcc10158179887a"],
  ["asperity", "210a30cb307ef5159bb6dfde92627a7ed323420e"],
  ["maladministrator", "6b47b48d7abd9b34cde5af2eaff91261956f4f74"],
  ["tephromalacia", "493b5804c3c527d214891b9f00924fa70377d5b4"],
  ["biquintile", "ca68689d2a7a32a137385fe83d53a95d08450c27"],
  ["surroundedly", "c83d1c8e8a5df15014e988f07080385a03156200"],
  ["Romansh", "ae9337425d6c70a8f5cfc645583fa02ec1885609"],
  ["antianopheline", "00642bdeaf2aa6b38bf7e0cf0d8794b5aa63894c"],
  ["tubicornous", "22c25f0fe1adee88a6f52d6adf0f3a786b6c86ec"],
  ["Athanasian", "b28ab3061b2448dc993aa9c8548da9946bf5f752"],
  ["antinomic", "0ce6612749f1a06ba9b0ccf09cddb89d58dd0958"],
  ["mucic", "bc19b361f1d990c319837763fbd871652e7131fc"],
  ["arthrophlogosis", "97216d3964ad71cd7ecc434f4d4e7363594a0244"],
  ["tightwire", "7620075191687971b23bc6d607712df0341412f5"],
  ["Kluxer", "4737cb434eba6d052bb4bba8170e76001ab574fe"],
  ["supertension", "35ad655845d698093f854d2fd0a685e0242da229"],
  ["metacresol", "45784651a57e22ff8476de035f6f0f719c5d9691"],
  ["enfold", "499f1342da156699e1b876994a0f006a73737bd9"],
  ["resinolic", "19591b593d4397fb24f0dd00e0d1aac16fd5f456"],
  ["refusion", "652da28b0ec5b723e7f06eaad0544575603781c1"],
  ["Pholidota", "0ee99e5de2133a0f1c41b024c9d7a0143cd7237c"],
]

def _sha1_test(ctx):
  env = unittest.begin(ctx)

  for input, expected in CASES:
      asserts.equals(env, expected, hash(input + "\n"), "Sha1 missmatch")

  return unittest.end(env)

sha1_test = unittest.make(_sha1_test)

def test_suite():
  unittest.suite(
      "sha1_suite",
      sha1_test,
  )
