package org.vulnera;

import org.owasp.dependencycheck.Engine;
import org.owasp.dependencycheck.dependency.Dependency;
import org.owasp.dependencycheck.dependency.Vulnerability;
import org.owasp.dependencycheck.reporting.ReportGenerator;
import org.owasp.dependencycheck.utils.Settings;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class DependencyScanner {

    public void scanDependencies(String projectPath, Consumer<String> outputCallback, Consumer<Double> progressCallback) {
        Settings settings = new Settings();
        settings.setString(Settings.KEYS.AUTO_UPDATE, "true");

        try (Engine engine = new Engine(settings)) {
            outputCallback.accept("Scanning started...");
            engine.scan(new File(projectPath));
            engine.analyzeDependencies();

            List<Dependency> dependencies = List.of(engine.getDependencies());
            int totalDependencies = dependencies.size();
            if (totalDependencies == 0) {
                outputCallback.accept("No dependencies found.");
                progressCallback.accept(1.0);
                return;
            }

            int scannedDependencies = 0;
            for (Dependency dependency : dependencies) {
                scannedDependencies++;
                double progress = (double) scannedDependencies / totalDependencies;
                progressCallback.accept(progress);

                outputCallback.accept("Scanning dependency: " + dependency.getFileName());

                if (!dependency.getVulnerabilities().isEmpty()) {
                    outputCallback.accept("  - Vulnerable dependency: " + dependency.getFileName());
                    for (Vulnerability vulnerability : dependency.getVulnerabilities()) {
                        outputCallback.accept("    - Vulnerability: " + vulnerability.getName());
                        outputCallback.accept("      Description: " + vulnerability.getDescription());
                    }
                }
            }

            File reportFile = new File("dependency-check-report.html");
            engine.writeReports("dependency-check", new File(reportFile.getParent()), ReportGenerator.Format.ALL.name());
            outputCallback.accept("Report generated: " + reportFile.getAbsolutePath());
            progressCallback.accept(1.0); // Ensure progress is set to 100% at the end
        } catch (Exception e) {
            outputCallback.accept("Error: " + e.getMessage());
        } finally {
            settings.cleanup();
        }
    }
}
