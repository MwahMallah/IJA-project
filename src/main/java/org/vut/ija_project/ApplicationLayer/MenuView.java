package org.vut.ija_project.ApplicationLayer;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;

import java.io.File;

public class MenuView extends MenuBar {
    private final Stage stage;
    private File chosenFile;
    private EnvironmentManager environmentManager;
    public MenuView(EnvironmentManager environmentManager, Stage stage) {
        this.environmentManager = environmentManager;
        this.stage = stage;

        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open existing environment");
        MenuItem exitItem = new MenuItem("Save current environment");
        fileMenu.getItems().addAll(openItem, exitItem);

        openItem.setOnAction(this::openFile);

        this.getMenus().add(fileMenu);
    }

    private void openFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Environment File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ENV files (*.env)", "*.env*");
        fileChooser.getExtensionFilters().add(extFilter);

        chosenFile = fileChooser.showOpenDialog(stage);
        if (chosenFile == null) return;
        
        environmentManager.getEnvironmentFromFile(chosenFile);
    }
}
