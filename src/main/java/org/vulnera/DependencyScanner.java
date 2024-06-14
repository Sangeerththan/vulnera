package org.vulnera;

import org.owasp.dependencycheck.Engine;
import org.owasp.dependencycheck.dependency.Dependency;
import org.owasp.dependencycheck.dependency.Vulnerability;
import org.owasp.dependencycheck.reporting.ReportGenerator;
import org.owasp.dependencycheck.utils.Settings;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class DependencyScanner {

    public void scanDependencies(String projectPath, Consumer<String> outputCallback) {
        Settings settings = new Settings();
        settings.setString(Settings.KEYS.AUTO_UPDATE, "true");

        try (Engine engine = new Engine(settings)) {
            engine.scan(new File(projectPath));
            engine.analyzeDependencies();

            // Convert the array to a list
            List<Dependency> dependencies = Arrays.asList(engine.getDependencies());
            for (Dependency dependency : dependencies) {
                if (!dependency.getVulnerabilities().isEmpty()) {
                    outputCallback.accept("Vulnerable dependency: " + dependency.getFileName());
                    for (Vulnerability vulnerability : dependency.getVulnerabilities()) {
                        outputCallback.accept("  - Vulnerability: " + vulnerability.getName());
                        outputCallback.accept("    Description: " + vulnerability.getDescription());
                    }
                }
            }

            // Generate the report
            File reportFile = new File("dependency-check-report.html");
            engine.writeReports("dependency-check", new File(reportFile.getParent()), ReportGenerator.Format.ALL.name());
            outputCallback.accept("Report generated: " + reportFile.getAbsolutePath());
        } catch (Exception e) {
            outputCallback.accept("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            settings.cleanup();
        }
    }
}
