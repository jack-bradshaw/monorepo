load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

def kt_jvm_library_with_dagger(name, srcs = [], plugins = [], deps = [], **kwargs):
    """Wrapper for kt_jvm_library that ensures Dagger KSP compilation triggers correctly.
    
    All necessary dagger deps and processors are installed. A dummy java file is auto-generated and
    compiled to force javac execution (KSP doesn't automatically trigger compilation when there are
    no Java sources).
    
    Args:
        name: The name of the target, string, required.
        srcs: A list of source files, list of labels, optional, defaults to [].
        plugins: Additional KSP plugins, list of labels, optional, defaults to [].
        deps: Dependencies, list of labels, optional, defaults to [].
        **kwargs: Arbitrary arguments to forward to the underlying kt_jvm_library, dictionary, optional.
    """
    
    dummy_name = name + "_dummy"
    dummy_file = dummy_name + ".java"
    
    # Generate a unique dummy Java file to trigger javac
    native.genrule(
        name = dummy_name,
        outs = [dummy_file],
        cmd = """
            echo 'package com.jackbradshaw.dagger.generated;' > $@ \
            && echo 'public class %s {}' >> $@
        """ % dummy_name,
    )
    
    kt_jvm_library(
        name = name,
        srcs = srcs + [dummy_file],
        plugins = plugins + ["//first_party/dagger:plugin"],
        deps = deps + [
            "@com_jackbradshaw_maven//:com_google_dagger_dagger",
            "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        ],
        **kwargs
    )

def kt_jvm_test_with_dagger(name, srcs = [], plugins = [], deps = [], **kwargs):
    """Wrapper for kt_jvm_test that ensures Dagger KSP compilation triggers correctly.

    See kt_jvm_library_with_dagger for implementation details.
    """
    
    dummy_name = name + "_dummy"
    dummy_file = dummy_name + ".java"
    
    native.genrule(
        name = dummy_name,
        outs = [dummy_file],
        cmd = """
            echo 'package com.jackbradshaw.dagger.generated;' > $@ \
            && echo 'public class %s {}' >> $@
        """ % dummy_name,
    )
    
    kt_jvm_test(
        name = name,
        srcs = srcs + [dummy_file],
        plugins = plugins + ["//first_party/dagger:plugin"],
        deps = deps + [
            "@com_jackbradshaw_maven//:com_google_dagger_dagger",
            "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        ],
        **kwargs
    )
