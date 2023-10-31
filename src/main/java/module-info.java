module my.scc.clockwise.clockwise {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ca.myscc.clockwise to javafx.fxml;
    exports ca.myscc.clockwise;
    exports ca.myscc.clockwise.database;
    opens ca.myscc.clockwise.database to javafx.fxml;
}