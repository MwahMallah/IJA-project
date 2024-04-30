package org.vut.ija_project.ApplicationLayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.vut.ija_project.ApplicationLayer.SelectedView.SelectedView;
import org.vut.ija_project.BusinessLayer.EnvironmentManager;

public class InformationView extends VBox {
    private final EnvironmentManager environmentManager;
    private SelectedView selectedView;

    public InformationView(EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;
        this.setSpacing(10);
    }

    public void setSelected(SelectedView selectedView) {
        this.selectedView = selectedView;
        update();
    }

    public void update() {
        this.getChildren().clear();
        //if there is no selected view don't display anything
        if (selectedView == null) return;

        selectedView.update();
        this.getChildren().add(selectedView);
    }

}
