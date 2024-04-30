package org.vut.ija_project.ApplicationLayer.SelectedView;

import javafx.scene.layout.VBox;

/**
 * Adapter interface, that adapts chosen view to needs of InformationView
 */
public abstract class  SelectedView extends VBox {

    public abstract void update();
}
