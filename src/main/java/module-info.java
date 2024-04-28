module org.vut.ija_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;
    exports org.vut.ija_project.DataLayer.Environment;
    exports org.vut.ija_project.DataLayer.Robot;
    exports org.vut.ija_project.DataLayer.Common;
    exports org.vut.ija_project.DataLayer.Obstacle;
    
    exports org.vut.ija_project.ApplicationLayer;
    opens org.vut.ija_project.ApplicationLayer to javafx.fxml;
}
