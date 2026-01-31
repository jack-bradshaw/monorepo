load("@rules_jvm_external//:defs.bzl", "java_export")

def maven_release(
        name,
        coordinates,
        project_name,
        project_description,
        project_url = None,
        scm_url = None,
        license_name = "MIT License",
        license_url = "http://www.opensource.org/licenses/mit-license.php",
        developer_name = "Jack Bradshaw",
        developer_email = "jack@jackbradshaw.io",
        developer_url = "https://jackbradshaw.io",
        deps = [],
        srcs = ["Stub.java"],
        **kwargs):
    """Defines a JVM release target for publication to Maven Central.

    This macro wraps java_export to provide a standardized publication interface. It ensures
    correct POM generation and handles publication boilerplate.

    Args:
        name: The name of the target, string, required.
        coordinates: The Maven coordinates in groupId:artifactId:version format, string, required.
        project_name: The human-readable name of the project, string, required.
        project_description: A brief description of the project, string, required.
        project_url: The URL of the project homepage, string, optional, defaults to a GitHub URL based on the artifactId.
        scm_url: The URL of the SCM repository, string, optional, defaults to a GitHub URL based on the artifactId.
        license_name: The name of the license, string, optional, defaults to "MIT License".
        license_url: The URL of the license text, string, optional, defaults to the MIT license URL.
        developer_name: The name of the developer, string, optional, defaults to "Jack Bradshaw".
        developer_email: The email of the developer, string, optional, defaults to "jack@jackbradshaw.io".
        developer_url: The URL of the developer's homepage, string, optional, defaults to "https://jackbradshaw.io".
        deps: Dependencies to include in the release, list of labels, optional, defaults to [].
        srcs: Source files to include, list of labels, optional, defaults to ["Stub.java"].
        **kwargs: Arbitrary arguments to forward to the underlying java_export, dictionary, optional.
    """

    artifact_id = coordinates.split(":")[1]
    if not project_url:
        project_url = "https://github.com/jack-bradshaw/monorepo/tree/main/first_party/%s" % artifact_id
    if not scm_url:
        scm_url = "https://github.com/jack-bradshaw/monorepo/tree/main/first_party/%s" % artifact_id

    pom_target = name + "_pom_gen"
    native.genrule(
        name = pom_target,
        srcs = ["//first_party/releasing/maven:pom_template.xml"],
        outs = [name + "_pom.xml"],
        cmd = ("sed " +
               "'s/{name}/%s/g; " +
               "s/{description}/%s/g; " +
               "s|{project_url}|%s|g; " +
               "s|{scm_url}|%s|g; " +
               "s/{license_name}/%s/g; " +
               "s|{license_url}|%s|g; " +
               "s/{developer_name}/%s/g; " +
               "s/{developer_email}/%s/g; " +
               "s|{developer_url}|%s|g' " +
               "$(location //first_party/releasing/maven:pom_template.xml) > $@") % (
            project_name.replace("'", "'\\''"),
            project_description.replace("'", "'\\''"),
            project_url.replace("'", "'\\''"),
            scm_url.replace("'", "'\\''"),
            license_name.replace("'", "'\\''"),
            license_url.replace("'", "'\\''"),
            developer_name.replace("'", "'\\''"),
            developer_email.replace("'", "'\\''"),
            developer_url.replace("'", "'\\''"),
        ),
    )

    java_export(
        name = name,
        srcs = srcs,
        maven_coordinates = coordinates,
        pom_template = ":" + pom_target,
        deps = deps,
        **kwargs
    )

    native.sh_binary(
        name = name + ".release",
        srcs = ["//first_party/releasing/maven:release_launcher_with_runfiles.sh"],
        args = ["//" + native.package_name() + ":" + name],
        deps = ["//first_party/releasing/maven:release_launcher_lib"],
        **kwargs
    )
