module org.vut.ija_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;
    exports org.vut.ija_project.Environment;
    exports org.vut.ija_project.Robot;
    exports org.vut.ija_project.Common;
    exports org.vut.ija_project.Obstacle;
    
    exports org.vut.ija_project.GUI;
    opens org.vut.ija_project.GUI to javafx.fxml;
}
