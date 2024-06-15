# Dependency Scanner

The Dependency Scanner is a Java application designed to scan project directories for dependencies and identify potential vulnerabilities. It utilizes OWASP Dependency Check to analyze dependencies and detect security issues that may exist within them.

## Features

- **Dependency Scanning**: Scan project directories to identify and analyze dependencies.
  
- **Vulnerability Detection**: Detect vulnerabilities such as outdated libraries, CVEs, and other security issues.
  
- **User Interface**: Provides a simple JavaFX-based user interface for selecting project directories and displaying scan results.

## Usage

To use the Dependency Scanner:

1. Clone the repository or download the latest release.
2. Run the application by executing `java -jar DependencyScanner.jar` or directly from an IDE.
3. Choose the project directory you want to scan using the user interface.
4. View scan progress and results in the UI output area.

## Getting Started

To get started with development or contribute to the project:

- Fork the repository and clone it locally.
- Explore the codebase to understand the implementation.
- Make improvements, add features, or fix issues.
- Submit a pull request with your changes for review.

## Technologies Used

- **Java**: JDK 20
- **JavaFX**: 17.0.2
- **SLF4J API**: 2.0.7
- **Logback Classic**: 1.5.6
- **OWASP Dependency Check Core**: 6.5.0
- **Maven Compiler Plugin**: 3.8.1
- **JavaFX Maven Plugin**: 0.0.8

## Build
- `mvn clean package`

## Run Configuration
![image](https://github.com/Sangeerththan/vulnera/assets/25486160/ae3c68fa-b0aa-43e4-9687-54c0e97bd331)
![image](https://github.com/Sangeerththan/vulnera/assets/25486160/7edfb5de-b662-48fe-bbd9-702efe42852a)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
