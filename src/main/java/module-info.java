module my.scc.clockwise.clockwise {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.myscc.clockwise to javafx.fxml;
    exports ca.myscc.clockwise;
}