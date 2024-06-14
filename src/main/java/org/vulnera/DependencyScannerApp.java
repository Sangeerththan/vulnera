package org.vulnera;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.io.File;

public class DependencyScannerApp extends Application {

    private TextArea outputArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dependency Scanner");

        Label label = new Label("Select project directory:");
        Button button = new Button("Choose Directory");
        outputArea = new TextArea();
        outputArea.setEditable(false);

        button.setOnAction(event -> chooseDirectory(primaryStage)); // No return statement here

        VBox vbox = new VBox(10, label, button, outputArea);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void chooseDirectory(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Project Directory");
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            scanDependencies(selectedDirectory.getAbsolutePath());
        }
    }

    private void scanDependencies(String projectPath) {
        new Thread(() -> {
            DependencyScanner scanner = new DependencyScanner();
            scanner.scanDependencies(projectPath, this::updateOutput);
        }).start();
    }

    private void updateOutput(String message) {
        javafx.application.Platform.runLater(() -> outputArea.appendText(message + "\n"));
    }
}
