module br.edu.ifsp.exemplo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires javafx.graphics;

    opens br.edu.ifsp.exemplo to javafx.fxml;
    exports br.edu.ifsp.exemplo;
    exports br.edu.ifsp.exemplo.ui;
    opens br.edu.ifsp.exemplo.ui to javafx.fxml;
}