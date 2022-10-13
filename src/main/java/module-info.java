module com.example.javafxendassignement2022 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxendassignement2022 to javafx.fxml;
    exports com.example.javafxendassignement2022;
    exports com.example.javafxendassignement2022.controller;
    opens com.example.javafxendassignement2022.controller to javafx.fxml;
    exports com.example.javafxendassignement2022.model;
    opens com.example.javafxendassignement2022.model to javafx.fxml;
    exports com.example.javafxendassignement2022.enums;
    opens com.example.javafxendassignement2022.enums to javafx.fxml;
}