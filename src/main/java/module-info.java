module my.scc.clockwise.clockwise {
    requires javafx.controls;
    requires javafx.fxml;


    opens my.scc.clockwise.clockwise to javafx.fxml;
    exports my.scc.clockwise.clockwise;
}