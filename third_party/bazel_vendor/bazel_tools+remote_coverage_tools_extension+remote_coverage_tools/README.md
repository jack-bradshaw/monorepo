This coverage_output_generator version was built from the Bazel repository
at commit hash ee12906c5d9a48924db6fc3aba36ccd6d5c7f69e using Bazel version 8.0.1.
To build the same zip from source, run the commands:

$ git clone https://github.com/bazelbuild/bazel.git
$ git checkout ee12906c5d9a48924db6fc3aba36ccd6d5c7f69e
$ bazel build --java_language_version=8 --tool_java_language_version=8 //tools/test/CoverageOutputGenerator/java/com/google/devtools/coverageoutputgenerator:coverage_output_generator.zip
