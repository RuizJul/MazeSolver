module com.mycompany.mazesolver {
    requires javafx.controls;
    requires javafx.fxml;

    opens Controllers to javafx.fxml;
    opens mazesolver to javafx.fxml;

    exports Controllers;
    exports mazesolver;
}