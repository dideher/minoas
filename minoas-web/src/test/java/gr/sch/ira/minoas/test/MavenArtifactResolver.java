package gr.sch.ira.minoas.test;

import java.io.File;

/**
 * A temporary resolver that converts a Maven artifact reference into a {@link java.io.File} object.
 * 
 * <p>
 * This approach is an interim solution for Maven projects until the open feature request to add formally add artifacts to a
 * test (<a href="https://jira.jboss.org/browse/ARQ-66">ARQ-66</a>) is implementated.
 * </p>
 * 
 * <p>
 * The testCompile goal will resolve any test dependencies and put them in your local Maven repository. By the time the test
 * executes, you can be sure that the JAR files you need will be in your local repository.
 * </p>
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * WebArchive war = ShrinkWrap.create(&quot;test.war&quot;, WebArchive.class).addLibrary(
 *         MavenArtifactResolver.resolve(&quot;commons-lang:commons-lang:2.5&quot;));
 * </pre>
 * 
 * <p>
 * If you are using an alternate local Maven repository, you need to pass it to the Maven surefire plugin using the following
 * stanza in the plugin configuration element:
 * </p>
 * 
 * <pre>
 * &lt;systemProperties&gt;
 *    &lt;property&gt;
 *       &lt;name&gt;maven.repo.local&lt;/name&gt;
 *       &lt;value&gt;${maven.repo.local}&lt;/value&gt;
 *    &lt;/property&gt;
 * &lt;/systemProperties&gt;
 * </pre>
 * 
 * <p>
 * Another approach to pull in a library is to add packages recursively from the root library package.
 * </p>
 * 
 * @author Dan Allen
 */
public class MavenArtifactResolver {
    private static final String LOCAL_MAVEN_REPO = System.getProperty("maven.repo.local") != null ? System
            .getProperty("maven.repo.local") : (System.getProperty("user.home") + File.separatorChar + ".m2"
            + File.separatorChar + "repository");

    public static File resolve(final String groupId, final String artifactId, final String version) {
        return resolve(groupId, artifactId, version, null);
    }

    public static File resolve(final String groupId, final String artifactId, final String version, final String classifier) {
        return new File(LOCAL_MAVEN_REPO + File.separatorChar + groupId.replace(".", File.separator) + File.separatorChar
                + artifactId + File.separatorChar + version + File.separatorChar + artifactId + "-" + version
                + (classifier != null ? ("-" + classifier) : "") + ".jar");
    }

    public static File resolve(final String qualifiedArtifactId) {
        String[] segments = qualifiedArtifactId.split(":");
        if (segments.length == 3) {
            return resolve(segments[0], segments[1], segments[2]);
        } else if (segments.length == 4) {
            return resolve(segments[0], segments[1], segments[2], segments[3]);
        }
        throw new IllegalArgumentException("Invalid format for qualified artifactId: " + qualifiedArtifactId);
    }

    public static File[] resolveAll(final String... qualifiedArtifactIds) {
        int n = qualifiedArtifactIds.length;
        File[] artifacts = new File[n];
        for (int i = 0; i < n; i++) {
            artifacts[i] = resolve(qualifiedArtifactIds[i]);
        }

        return artifacts;
    }
}
